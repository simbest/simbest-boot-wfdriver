package com.simbest.boot.wfdriver.process.operate;

import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.core.map.MapUtil;
import cn.hutool.core.util.StrUtil;
import com.google.common.collect.Maps;
import com.simbest.boot.security.IOrg;
import com.simbest.boot.security.IUser;
import com.simbest.boot.util.json.JacksonUtils;
import com.simbest.boot.util.redis.RedisUtil;
import com.simbest.boot.util.security.SecurityUtils;
import com.simbest.boot.wf.process.service.IProcessInstanceService;
import com.simbest.boot.wfdriver.api.CallFlowableProcessApi;
import com.simbest.boot.wfdriver.constants.AppConstants;
import com.simbest.boot.wfdriver.constants.ProcessConstants;
import com.simbest.boot.wfdriver.enums.ProcessSateEnum;
import com.simbest.boot.wfdriver.exceptions.FlowableDriverBusinessException;
import com.simbest.boot.wfdriver.exceptions.WorkFlowBusinessRuntimeException;
import com.simbest.boot.wfdriver.process.bussiness.model.ActBusinessStatus;
import com.simbest.boot.wfdriver.process.bussiness.service.IActBusinessStatusService;
import com.simbest.boot.wfdriver.process.listener.model.ActCommentModel;
import com.simbest.boot.wfdriver.process.listener.model.ActProcessInstModel;
import com.simbest.boot.wfdriver.process.listener.model.ActTaskInstModel;
import com.simbest.boot.wfdriver.process.listener.service.IActCommentModelService;
import com.simbest.boot.wfdriver.process.listener.service.IActProcessInstModelService;
import com.simbest.boot.wfdriver.process.listener.service.IActTaskInstModelService;
import com.simbest.boot.wfdriver.util.MapRemoveNullUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.InputStream;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.Optional;
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
    private IActProcessInstModelService actProcessInstModelService;

    @Autowired
    private IActTaskInstModelService actTaskInstModelService;

    @Autowired
    private IActCommentModelService actCommentModelService;

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
     * 只启动流程，不进行流转第一个节点
     * @param startParam            流程参数
     * @return
     */
    @Override
    public Map<String,Object> startProcessAndDeployProcessAndNoSetRelativeData ( Map<String, Object> startParam ) {
        getActDataObject(startParam);
        Map<String,Object> cacheStartMapParam = CollectionUtil.newHashMap();
        Map<String,Object> processInstanceDataMap = Maps.newConcurrentMap();
        String flowDirection = MapUtil.getStr( startParam,"flowDirection" );
        String startProcessFlag = MapUtil.getStr( startParam,"startProcessFlag" );
        String currentUserCode = MapUtil.getStr( startParam, "currentUserCode" );
        String currentUserName = MapUtil.getStr( startParam, "currentUserName" );
        String orgCode = MapUtil.getStr(startParam, "orgCode" );
        String postId = MapUtil.getStr(startParam, "postId" );
        String message = MapUtil.getStr( startParam, "message" );
        String idValue = MapUtil.getStr( startParam, "idValue" );  //流程的定义ID
        String outcome = MapUtil.getStr( startParam, "outcome");
        String businessKey = MapUtil.getStr( startParam, "receiptCode" );
        String messageNameValue = MapUtil.getStr( startParam, "messageNameValue" );
        cacheStartMapParam.put( "staticNextUserName",currentUserName );
        cacheStartMapParam.put( "staticNextUser",currentUserCode );
        cacheStartMapParam.put( "currentUserName", currentUserName);
        cacheStartMapParam.put( "currentUserCode",currentUserCode);
        cacheStartMapParam.put( "creatorIdentity",currentUserCode.concat( "#" ).concat( orgCode ).concat( "#" ).concat( postId ));
        log.warn( "正常打印打印流程启动提交的候选中文名称：【{}】", JacksonUtils.obj2json( cacheStartMapParam ) );
        RedisUtil.setBean( businessKey.concat( ProcessConstants.PROCESS_START_REDIS_SUFFIX ),cacheStartMapParam );
        Map<String,Object> cacheStartMapParamRedis = RedisUtil.getBean( businessKey.concat(ProcessConstants.PROCESS_START_REDIS_SUFFIX),Map.class);
        log.warn( "获取redis中流程启动参数：【{}】",JacksonUtils.obj2json( cacheStartMapParamRedis ) );
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
                        variables.put( "inputUserId", currentUserCode);
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
                StringBuilder countUserKye = new StringBuilder();
                countUserKye.append( flowDirection ).append( "_" ).append( processInstanceId ).append( "_" ).append( currentUserCode );
                RedisUtil.setBean( countUserKye.toString(),1 );
                //保存流程业务数据
                actBusinessStatusService.saveActBusinessStatusData(processInstanceId,startParam);
            }
            /**
             * 查询起草环节
             */
            String firstTaskId = "";
            String processDefinitionId = "";
            Map<String,String> taskQueryMap = Maps.newHashMap();
            taskQueryMap.put("processInstanceId",processInstanceId);
            taskQueryMap.put("assignee",currentUserCode);
            List<Map<String,Object>> taskQueryDataMap = callFlowableProcessApi.tasksQueryNoPage(taskQueryMap);
            if(taskQueryDataMap!=null) {
                Map<String, Object> firstTask = taskQueryDataMap.get( 0 );
                firstTaskId = MapUtil.getStr( firstTask, "id" );
                processDefinitionId = MapUtil.getStr( firstTask, "processDefinitionId" );
            }
            processInstanceDataMap.put( "firstTaskId",firstTaskId );
            processInstanceDataMap.put( "processDefinitionId",processDefinitionId );
            return processInstanceDataMap;
        }catch (Exception e){
            FlowableDriverBusinessException.printException( e );
            throw new WorkFlowBusinessRuntimeException("Exception Cause is submit workItem data failure,code:WF000001");
        }
    }

    private void getActDataObject(Map<String,Object>startMap){
        ActBusinessStatus actBusinessStatus = new ActBusinessStatus();
        String currentUserCode = MapUtil.getStr(startMap,"currentUserCode" );
        String receipTitle = MapUtil.getStr(startMap, "receipTitle" );
        Boolean iscg = MapUtil.getBool(startMap, "iscg" );
        String processDefKey = MapUtil.getStr(startMap, "idValue" );
        String orgCode = MapUtil.getStr(startMap, "orgCode" );
        String postId = MapUtil.getStr(startMap, "postId" );
        String businessKey = MapUtil.getStr( startMap, "businessKey" );
        String receiptCode = MapUtil.getStr( startMap, "receiptCode" );
        IUser user = SecurityUtils.getCurrentUser();
        Optional<IOrg> orgOptional = ( Optional<IOrg> ) user.getAuthOrgs().stream().
                filter( org -> org.getOrgCode().equals( orgCode ) ).findFirst();
        String createOrgName = "";
        if ( orgOptional.isPresent() ){//如果存在
            createOrgName = orgOptional.get().getOrgName();
        }
        actBusinessStatus.setCreateUserId( currentUserCode );
        actBusinessStatus.setCreateUserName( user.getTruename() );
        actBusinessStatus.setPreviousAssistant(currentUserCode);
        actBusinessStatus.setPreviousAssistantName(user.getTruename());
        actBusinessStatus.setCreateUserId( currentUserCode );
        actBusinessStatus.setCreateOrgCode( orgCode );
        actBusinessStatus.setCreateOrgName( createOrgName );
        actBusinessStatus.setEnabled(true);
        actBusinessStatus.setRemoved(false);
        actBusinessStatus.setReceiptTitle(receipTitle);
        actBusinessStatus.setReceiptCode(receiptCode);
        actBusinessStatus.setIscg(iscg);
        actBusinessStatus.setBusinessKey(businessKey);
        //actBusinessStatus.setProcessInstId(processInstanceId);
        actBusinessStatus.setStartTime( LocalDateTime.now());
        actBusinessStatus.setProcessDefKey( processDefKey );
        actBusinessStatus.setCurrentState( ProcessSateEnum.RUNNING.getNum() );
        actBusinessStatus.setPmInstType( StrUtil.sub( receiptCode,0,1 ) );
        actBusinessStatus.setCreatorIdentity( currentUserCode.concat( "#" ).concat( orgCode ).concat( "#" ).concat( postId ) );
        RedisUtil.setBean( AppConstants.APP_CODE.concat( "_act" ),actBusinessStatus );
    }

    /**
     * 启动流程并进行流程部署和根据流程参数进行启动流程实例 (默认跳过第一个环节)
     * @param startParam            流程参数
     * @return
     */
    @Override
    public Map<String, Object> startProcessAndDeployProcessAndSetRelativeData ( Map<String, Object> startParam ) {
        Map<String,Object> cacheStartMapParam = CollectionUtil.newHashMap();
        Map<String,Object> processInstanceDataMap = CollectionUtil.newHashMap();
        String startProcessFlag = MapUtil.getStr( startParam,"startProcessFlag" );
        String currentUserCode = MapUtil.getStr( startParam, "currentUserCode" );
        String currentUserName = MapUtil.getStr( startParam, "currentUserName" );
        String orgCode = MapUtil.getStr(startParam, "orgCode" );
        String postId = MapUtil.getStr(startParam, "postId" );
        String message = MapUtil.getStr( startParam, "message" );
        String idValue = MapUtil.getStr( startParam, "idValue" );  //流程的定义ID
        String nextUser = MapUtil.getStr( startParam, "nextUser" );
        String nextUserName = MapUtil.getStr( startParam, "nextUserName" );
        String nextUserOrgCode = MapUtil.getStr( startParam, "nextUserOrgCode" );
        String nextUserPostId =  MapUtil.getStr( startParam, "nextUserPostId" );
        String outcome = MapUtil.getStr( startParam, "outcome");
        String businessKey = MapUtil.getStr( startParam, "receiptCode" );
        String messageNameValue = MapUtil.getStr( startParam, "messageNameValue" );
        cacheStartMapParam.put( "staticNextUserName",nextUserName );
        cacheStartMapParam.put( "staticNextUser",nextUser );
        cacheStartMapParam.put( "currentUserName", currentUserName);
        cacheStartMapParam.put( "currentUserCode",currentUserCode);
        cacheStartMapParam.put( "creatorIdentity",currentUserCode.concat( "#" ).concat( orgCode ).concat( "#" ).concat( postId ));
        log.warn( "正常打印打印流程启动提交的候选中文名称：【{}】", JacksonUtils.obj2json( cacheStartMapParam ) );
        RedisUtil.setBean( businessKey.concat( ProcessConstants.PROCESS_START_REDIS_SUFFIX ),cacheStartMapParam );
        Map<String,Object> cacheStartMapParamRedis = RedisUtil.getBean( businessKey.concat(ProcessConstants.PROCESS_START_REDIS_SUFFIX),Map.class);
        log.warn( "获取redis中流程启动参数：【{}】",JacksonUtils.obj2json( cacheStartMapParamRedis ) );
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
                String processInstanceIdTmp = MapUtil.getStr( firstTask,"processInstanceId" );

                //保存审批意见
                if ( StrUtil.isNotEmpty( message ) ) {
                    Map<String, String> taskAddCommentMap = Maps.newHashMap( );
                    taskAddCommentMap.put( "currentUserCode", currentUserCode );
                    taskAddCommentMap.put( "taskId", firstTaskId );
                    taskAddCommentMap.put( "processInstanceId",  processInstanceId);
                    taskAddCommentMap.put( "comment", message );
                    callFlowableProcessApi.tasksAddComment( taskAddCommentMap );
                    actCommentModelService.create( currentUserCode, message, processInstanceId, firstTaskId, businessKey );
                }

                //流转下一步
                Map<String,Object> tasksCompleteMap = Maps.newHashMap();
                tasksCompleteMap.put( "outcome",outcome );
                tasksCompleteMap.put( "inputUserId",nextUser );
                String participantIdentity = nextUser.concat( "#" ).concat( nextUserOrgCode ).concat( "#" ).concat( nextUserPostId );
                tasksCompleteMap.put( "participantIdentity",participantIdentity);
                tasksCompleteMap.put( "fromTaskId", firstTaskId);
                Map<String,Object> cacheSubmitMapParam = CollectionUtil.newHashMap();
                cacheSubmitMapParam.put( "staticNextUserName",nextUserName );
                cacheSubmitMapParam.put( "staticNextUser",nextUser );
                RedisUtil.setBean( processInstanceId.concat( ProcessConstants.PROCESS_SUBMIT_REDIS_SUFFIX ),cacheSubmitMapParam );
                callFlowableProcessApi.tasksComplete(firstTaskId,tasksCompleteMap);
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
        try {
            String processInstanceId = MapUtil.getStr( delParam,"processInstanceId" );
            //删除act
            ActBusinessStatus actBusinessStatus = new ActBusinessStatus();
            actBusinessStatus.setProcessInstId( processInstanceId );
            actBusinessStatusService.deletActBusinessStatusData(actBusinessStatus);
            //删除流程实例
            ActProcessInstModel actProcessInstModel = new ActProcessInstModel();
            actProcessInstModel.setProcessInstanceId( processInstanceId );
            actProcessInstModelService.delete( actProcessInstModel );
            //删除环节实例
            ActTaskInstModel actTaskInstModel = new ActTaskInstModel();
            actTaskInstModel.setProcessInstId( processInstanceId );
            actTaskInstModelService.delete( actTaskInstModel );
            //删除意见
            ActCommentModel actCommentModel = new ActCommentModel();
            actCommentModel.setProcessInstId( processInstanceId );
            actCommentModelService.delete( actCommentModel );
            //调用API删除流程
            callFlowableProcessApi.deleteProcessInstance( processInstanceId );
        }catch (Exception e){
            FlowableDriverBusinessException.printException( e );
            throw new WorkFlowBusinessRuntimeException("Exception Cause is delete workItem data failure,code:WF000002");
        }
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
//        InputStream inputStream = callFlowableProcessApi.getDiagramByProcessInstanceId(null,processInstanceId);
        return null;
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

    /**
     * 升级指定流程的流程定义（升级指定流程的流程定义,流程实例ID如果有多个，逗号分割；流程定义ID和版本号填一个就可以，如果都填写，以流程定义ID为准）
     * 另外本地库中的实例表和任务表请在调用接口成功后更新流程定义ID
     * @param processInstanceIds 实例ID
     */
    @Override
    public void upgradeProcessInstanceVersion(String processInstanceIds) {
        callFlowableProcessApi.upgradeProcessInstanceVersion (processInstanceIds , null , null);
    }
}
