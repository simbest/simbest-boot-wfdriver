package com.simbest.boot.wfdriver.process.operate;

import cn.hutool.core.map.MapUtil;
import com.google.common.collect.Maps;
import com.simbest.boot.wf.process.service.IProcessInstanceService;
import com.simbest.boot.wfdriver.api.CallFlowableProcessApi;
import com.simbest.boot.wfdriver.exceptions.FlowableDriverBusinessException;
import com.simbest.boot.wfdriver.exceptions.WorkFlowBusinessRuntimeException;
import com.simbest.boot.wfdriver.process.bussiness.service.IActBusinessStatusService;
import com.simbest.boot.wfdriver.process.listener.service.IActCommentModelService;
import com.simbest.boot.wfdriver.util.MapRemoveNullUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.InputStream;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 *@ClassName WfProcessManager
 *@Description 实例接口实现
 *@Author Administrator
 *@Date 2019/12/01 16:03
 *@Version 1.0
 **/
@Slf4j
@Service(value = "wfProcessManager")
public class WfProcessManager implements IProcessInstanceService {

    @Autowired
    private IActBusinessStatusService actBusinessStatusService;

    @Autowired
    private CallFlowableProcessApi callFlowableProcessApi;

    @Autowired
    private IActCommentModelService actCommentModelService;

    public static String creatorIdentity = "";

    /**
     * 启动流程并设置相关数据
     * @param processDefName        流程定义名称
     * @param processInstName       流程实例名称
     * @param transcationSpan       是否分割事务 true开启，false关闭
     * @param param                 相关数据键值对
     * @return
     */
    @Override
    public long startProcessAndSetRelativeData ( String processDefName, String processInstName, String processInstDesc, boolean transcationSpan, Map<String, Object> param ) {
        return 0;
    }

    @Override
    public long startProcessAndSetRelativeDataNormal ( String processDefName, String processInstName, String processInstDesc, boolean transcationSpan, Map<String, Object> param ) {
        return 0;
    }

    @Override
    public long startProcessAndTran ( String processDefName, String processInstName, String processInstDesc, boolean transcationSpan ) {
        return 0;
    }

    @Override
    public long startProcess ( String processDefName, String processInstName, String processInstDesc ) {
        return 0;
    }

    /**
     * 启动流程并进行流程部署
     * @param startParam            流程参数
     * @return
     */
    @Override
    public Map<String,Object> startProcessAndDeployProcessAndNoSetRelativeData ( Map<String, Object> startParam ) {
        return null;
    }

    /**
     * 启动流程并进行流程部署和根据流程参数进行启动流程实例 (默认跳过第一个环节)
     * @param startParam            流程参数
     * @return
     */
    @Override
    public Map<String, Object> startProcessAndDeployProcessAndSetRelativeData ( Map<String, Object> startParam ) {
        Map<String,Object> processInstanceDataMap = Maps.newConcurrentMap();
        String startProcessFlag = MapUtil.getStr( startParam,"startProcessFlag" );
        String currentUserCode = MapUtil.getStr( startParam, "currentUserCode" );
        String orgCode = MapUtil.getStr(startParam, "orgCode" );
        String postId = MapUtil.getStr(startParam, "postId" );
        String message = MapUtil.getStr( startParam, "message" );
        String idValue = MapUtil.getStr( startParam, "idValue" );  //流程的定义ID
        String nextUser = MapUtil.getStr( startParam, "nextUser" );
        String nextUserOrgCode = MapUtil.getStr( startParam, "nextUserOrgCode" );
        String nextUserPostId =  MapUtil.getStr( startParam, "nextUserPostId" );
        String outcome = MapUtil.getStr( startParam, "outcome");
        String businessKey = MapUtil.getStr( startParam, "businessKey" );
        String messageNameValue = MapUtil.getStr( startParam, "messageNameValue" );
        creatorIdentity = currentUserCode.concat( "#" ).concat( orgCode ).concat( "#" ).concat( postId );
        try {
            String processInstanceId = null;
            Map<String, String> variables = Maps.newConcurrentMap();
            if ( !StringUtils.isEmpty( startProcessFlag ) ){
                switch ( startProcessFlag ){
                    case "KEY":
                        variables.put( "inputUserId", currentUserCode);
                        variables.put( "businessKey", businessKey);
                        variables.put( "orgCode", orgCode);
                        variables.put( "postId", postId);
                        MapRemoveNullUtil.removeNullEntry(variables);
                        processInstanceDataMap =  callFlowableProcessApi.instancesStartByKey(idValue,variables);
                        break;
                    case "MESSAGE":
                        variables.put( "inputUserId", nextUser);
                        variables.put( "businessKey", businessKey);
                        MapRemoveNullUtil.removeNullEntry(variables);
                        processInstanceDataMap = callFlowableProcessApi.instancesStartByMessage(messageNameValue,variables);
                        break;
                    default:
                        break;
                }
            }
            if(processInstanceDataMap!=null){
                processInstanceId = MapUtil.getStr( processInstanceDataMap,"processInstanceId" );

                //保存流程业务数据
                actBusinessStatusService.saveActBusinessStatusData(processInstanceId,startParam);
            }
            /**
             * 查询起草环节
             */
            Map<String,String> taskQueryMap = Maps.newHashMap();
            taskQueryMap.put("processInstanceId",processInstanceId);
            taskQueryMap.put("assignee",currentUserCode);
            List<Map<String,Object>> taskQueryDataMap = callFlowableProcessApi.tasksQueryNoPage(taskQueryMap);
            if(taskQueryDataMap!=null){
                Map<String,Object> firstTask = taskQueryDataMap.get(0);
                String firstTaskId = MapUtil.getStr( firstTask,"id" );
                /**
                 * 添加起草环节评论
                 */
                Map<String,String> taskAddCommentMap = Maps.newHashMap();
                taskAddCommentMap.put("currentUserCode",currentUserCode);
                taskAddCommentMap.put("taskId", firstTaskId);
                taskAddCommentMap.put("processInstanceId", (String) firstTask.get("processInstanceId"));
                taskAddCommentMap.put("comment",message);
                callFlowableProcessApi.tasksAddComment(taskAddCommentMap);
                /**
                 * 完成起草环节
                 */
                Map<String,Object> tasksCompleteMap = Maps.newHashMap();
                tasksCompleteMap.put( "outcome",outcome );
                tasksCompleteMap.put( "inputUserId",nextUser );
                String participantIdentity = nextUser.concat( "#" ).concat( nextUserOrgCode ).concat( "#" ).concat( nextUserPostId );
                tasksCompleteMap.put( "participantIdentity",participantIdentity);
                tasksCompleteMap.put( "fromTaskId", firstTaskId);
                callFlowableProcessApi.tasksComplete(firstTaskId,tasksCompleteMap);
                actCommentModelService.create(currentUserCode,message,(String) firstTask.get("processInstanceId"),firstTaskId,businessKey);
            }
           /* //保存流程业务数据
            actBusinessStatusService.saveActBusinessStatusData(processInstanceId,startParam);*/
            return processInstanceDataMap;
        }catch ( WorkFlowBusinessRuntimeException e){
            FlowableDriverBusinessException.printException( e );
            throw new WorkFlowBusinessRuntimeException("Exception Cause is submit workItem data failure,code:WF000001");
        }
    }

    /**
     * 根据流程实例ID删除流程实例信息
     * @param processInstID         流程实例ID
     * @return  0:失败  1:成功
     */
    @Override
    public int deleteProcessInstance ( long processInstID ) {
        return 0;
    }

    /**
     * 根据流程实例ID删除本地流程实例信息
     * @param processInstID         流程实例ID
     * @return
     */
    @Override
    public int deleteLocalDataByProInsId ( long processInstID ) {
        return 0;
    }

    /**
     * 根据流程实例ID删除流程
     * @param delParam      参数
     * @return
     */
    @Override
    public int deleteProcessInstance ( Map<String, Object> delParam ) {
        return 0;
    }

    @Override
    public List<? extends Object> getByParentProcId ( Long parentProcessInstID ) {
        return null;
    }

    @Override
    public int updateTitleByBusinessKey ( String businessKey, String title ) {
        return 0;
    }

    @Override
    public List<Map<String, Object>> getProInstDataByProInstIdLocal ( Long processInstId ) {
        return null;
    }

    /**
     * 注销流程 逻辑删除,注销后的流程不能恢复
     * @param processInstId          流程实例ID
     * @return
     */
    @Override
    public int cancelProcessInst ( Long processInstId ) {
        return 0;
    }

    /**
     * 注销流程 逻辑删除,注销后的流程不能恢复   （Activity6）
     * @param proParam          参数
     * @return
     */
    @Override
    public int cancelProcessInst ( Map<String, Object> proParam ) {
        return 0;
    }

    /**
     * 终止流程
     *     实际是把当前正在运行的工作项状态变为终止状态（13），在已办中可以正常查询到    （Activity6）
     * @param processInstId     流程实例ID
     * @return
     */
    @Override
    public int terminateProcessInst ( Long processInstId ) {
        return 0;

    }

    /**
     * 根据流程ID查询流程图片，并返回页面
     * @return
     */
    @Override
    public InputStream getDiagram ( String processInstanceId ) {
        InputStream inputStream = callFlowableProcessApi.getDiagramByProcessInstanceId(null,processInstanceId);
        return inputStream;
    }

    @Override
    public List<? extends Object> queryProcessInstacesDataByProInsIdApi ( Set<String> processInstIds ) {
        return null;
    }

    /**
     * 根据流程定义KEY查询流程实例信息  多个    （Activity6）
     * @param processDefKey    流程实例ID
     * @return
     */
    @Override
    public List<? extends Object> queryProcessInstacesDataByProDefKeyApi ( String processDefKey ) {
        return null;
    }

    /**
     * 根据流程活动状态查询流程实例信息  多个   （Activity6）
     * @return
     */
    @Override
    public List<? extends Object> queryProcessInstacesDataByActiveApi ( ) {
        return null;
    }

    /**
     * 根据流程活动状态查询流程实例信息  多个     （Activity6）
     * @param proBusinessKey       流程业务主键key
     * @return
     */
    @Override
    public List<? extends Object> queryProcessInstacesDataByActiveApi ( String proBusinessKey ) {
        return null;
    }

    /**
     * @param processInstanceId     流程实例ID
     * @return
     */
    @Override
    public boolean queryProcessInstaceEndStateByProInsIdApi ( String processInstanceId ) {
        return false;
    }


}
