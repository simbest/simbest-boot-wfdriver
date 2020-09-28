package com.simbest.boot.wfdriver.api;

import cn.hutool.core.map.MapUtil;
import cn.hutool.core.util.StrUtil;
import com.google.common.collect.Maps;
import com.mzlion.easyokhttp.response.HttpResponse;
import com.simbest.boot.base.exception.Exceptions;
import com.simbest.boot.base.web.response.JsonResponse;
import com.simbest.boot.util.json.JacksonUtils;
import com.simbest.boot.wfdriver.exceptions.WorkFlowBusinessRuntimeException;
import com.simbest.boot.wfdriver.http.utils.ConstansURL;
import com.simbest.boot.wfdriver.http.utils.ConstantsUtils;
import com.simbest.boot.wfdriver.http.utils.HttpConfig;
import com.simbest.boot.wfdriver.http.utils.WqqueryHttpService;
import com.simbest.boot.wfdriver.process.bussiness.service.IActBusinessStatusService;
import com.simbest.boot.wfdriver.process.listener.model.ActProcessInstModel;
import com.simbest.boot.wfdriver.process.listener.model.ActTaskInstModel;
import com.simbest.boot.wfdriver.process.listener.service.IActProcessInstModelService;
import com.simbest.boot.wfdriver.process.listener.service.IActTaskInstModelService;
import com.simbest.boot.wfdriver.util.MapRemoveNullUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.compress.utils.Lists;
import org.apache.commons.io.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
/**
 * <strong>Title : CallFlowableProcessApi</strong><br>
 * <strong>Description : driver项目调用wfengine项目统一代码</strong><br>
 * <strong>Create on : 2019/01/13</strong><br>
 * <strong>Modify on : 2019/01/13</strong><br>
 * <strong>Copyright (C) Ltd.</strong><br>
 *
 * @author LJW lijianwu@simbest.com.cn
 * @version <strong>V1.0.0</strong><br>
 * <strong>修改历史:</strong><br>
 * 修改人 修改日期 修改描述<br>
 * -------------------------------------------<br>
 */
@Slf4j
@Component
@SuppressWarnings("ALL")
public class CallFlowableProcessApi {

    @Autowired
    private WqqueryHttpService wqqueryHttpService;

    @Autowired
    private HttpConfig httpConfig;

    @Autowired
    private IActBusinessStatusService actBusinessStatusService;

    @Autowired
    private IActProcessInstModelService actProcessInstModelService;

    @Autowired
    private IActTaskInstModelService actTaskInstModelService;


    /**
     * 1.自动部署流程定义
     * @param file 部署文件
     * @param filename 部署文件名称
     * @param name 创建部署名称
     * @param category 流程分类
     * @return
     * @throws WorkFlowBusinessRuntimeException 接口调用失败，将错返回给客户端处理
     */
    public Map<String,Object> deployments_Add(File file, String  filename , String name, String category) throws WorkFlowBusinessRuntimeException{
        Map<String,Object> data =null;
        Map<String,String> para = new HashMap<String,String>();
        para.put("name",name);
        para.put("category",category);
        /*租户ID必填*/
        para.put("tenantId",httpConfig.sourcesystemid);
        Map<String,Object> map = wqqueryHttpService.callInterfaceFile(ConstansURL.DEPLOYMENTS_ADD,file,filename,para);
        if(map!=null){
            if(map.get("state").equals(ConstantsUtils.FAILE)){
                log.error("Flowable-Engine接口异常:"+map.get("message") );
                throw new WorkFlowBusinessRuntimeException("Flowable-Engine接口异常:"+map.get("message") );
            }
            data = (Map<String, Object>) map.get("data");
        }
        return data;
    }

    /**
     * 2.根据流程定义ID启动流程实例
     * @param processDefinitionId 流程定义ID
     * @param variables 相关参数
     * @return
     * @throws WorkFlowBusinessRuntimeException 接口调用失败，将错返回给客户端处理
     */
    public Map<String,Object> instancesStartById(String processDefinitionId,Map<String,String> variables) throws WorkFlowBusinessRuntimeException{
        Map<String,Object> data =null;
        Map<String,String> para = new HashMap<String,String>();
        para.put("processDefinitionId",processDefinitionId);
        para.put("stringJson",JacksonUtils.obj2json(variables));
        Map<String,Object> map = wqqueryHttpService.callInterfaceString(ConstansURL.INSTANCES_START,para);
        if(map!=null){
            if(map.get("state").equals(ConstantsUtils.FAILE)){
                log.error("Flowable-Engine接口异常:"+map.get("message") );
                throw new WorkFlowBusinessRuntimeException("Flowable-Engine接口异常:"+map.get("message") );
            }
            data = (Map<String, Object>) map.get("data");
        }
        return data;
    }

    /**
     * 2.根据流程定义KEY启动项目
     * @param processDefinitionKey 流程定义KEY
     * @param variables 相关参数
     * @return
     * @throws WorkFlowBusinessRuntimeException 接口调用失败，将错返回给客户端处理
     */
    public Map<String,Object> instancesStartByKey(String processDefinitionKey,Map<String,String> variables) throws WorkFlowBusinessRuntimeException{
        Map<String,Object> data =null;
        Map<String,String> para = new HashMap<String,String>();
        para.put("processDefinitionKey",processDefinitionKey);
        /*租户ID必填*/
        para.put("tenantId",httpConfig.sourcesystemid);
        para.put("stringJson",JacksonUtils.obj2json(variables));
        Map<String,Object> map = wqqueryHttpService.callInterfaceString(ConstansURL.INSTANCES_START,para);
        if(map!=null){
            if(map.get("state").equals(ConstantsUtils.FAILE)){
                log.error("Flowable-Engine接口异常:"+map.get("message") );
                throw new WorkFlowBusinessRuntimeException("Flowable-Engine接口异常:"+map.get("message") );
            }
            data = (Map<String, Object>) map.get("data");
        }
        return data;
    }

    /**
     * 2.根据消息启动流程实例
     * @param message 消息
     * @param variables 相关参数
     * @return
     * @throws WorkFlowBusinessRuntimeException 接口调用失败，将错返回给客户端处理
     */
    public Map<String,Object> instancesStartByMessage(String message,Map<String,String> variables) throws WorkFlowBusinessRuntimeException{
        Map<String,Object> data =null;
        Map<String,String> para = new HashMap<String,String>();
        para.put("message",message);
        /*租户ID必填*/
        para.put("tenantId",httpConfig.sourcesystemid);
        para.put("stringJson",JacksonUtils.obj2json(variables));
        Map<String,Object> map = wqqueryHttpService.callInterfaceString(ConstansURL.INSTANCES_START,para);
        if(map!=null){
            if(map.get("state").equals(ConstantsUtils.FAILE)){
                log.error("Flowable-Engine接口异常:"+map.get("message") );
                throw new WorkFlowBusinessRuntimeException("Flowable-Engine接口异常:"+map.get("message") );
            }
            data = (Map<String, Object>) map.get("data");
        }
        return data;
    }

    /**
     * 3.根据相关参数查询Task实例
     * @param variables 相关参数
     * @return
     * @throws WorkFlowBusinessRuntimeException 接口调用失败，将错返回给客户端处理
     */
    public List<Map<String,Object>> tasksQueryNoPage(Map<String,String> variables) throws WorkFlowBusinessRuntimeException{
        List<Map<String,Object>> data =null;
        Map<String,Object> map = wqqueryHttpService.callInterfaceString(ConstansURL.tasksQueryNoPage,variables);
        if(map!=null){
            if(map.get("state").equals(ConstantsUtils.FAILE)){
                log.error("Flowable-Engine接口异常:"+map.get("message") );
                throw new WorkFlowBusinessRuntimeException("Flowable-Engine接口异常:"+map.get("message") );
            }
            data = (List<Map<String, Object>>) map.get("data");
        }
        return data;
    }

    /**
     * 4.办理时提交审批意见
     * @param variables 相关参数
     * @return
     * @throws WorkFlowBusinessRuntimeException 接口调用失败，将错返回给客户端处理
     */
    public Map<String,Object> tasksAddComment(Map<String,String> variables) throws WorkFlowBusinessRuntimeException{
        Map<String,Object> map = wqqueryHttpService.callInterfaceString(ConstansURL.TASKS_ADD_COMMENT,variables);
        if(map.get("state").equals(ConstantsUtils.FAILE)){
            log.error("Flowable-Engine接口异常:"+map.get("message") );
            throw new WorkFlowBusinessRuntimeException("Flowable-Engine接口异常:"+map.get("message") );
        }
        return map;
    }

    /**
     * 5.办理任务
     * @param taskId 任务ID
     * @param variables 相关参数
     * @return
     * @throws WorkFlowBusinessRuntimeException 接口调用失败，将错返回给客户端处理
     */
    public Map<String,Object> tasksComplete(String taskId,Map<String,Object> variables) throws WorkFlowBusinessRuntimeException{
        Map<String,String> para = new HashMap<String,String>();
        para.put("taskId",taskId);
        para.put("stringJson",JacksonUtils.obj2json(variables));
        Map<String,Object> map = wqqueryHttpService.callInterfaceString(ConstansURL.TASKS_COMPLETE,para);
        if(map.get("state").equals(ConstantsUtils.FAILE)){
            log.error("Flowable-Engine接口异常:"+map.get("message") );
            throw new WorkFlowBusinessRuntimeException("Flowable-Engine接口异常:"+map.get("message") );
        }
        return map;
    }

    /**
     * 6.获取流程图
     * @param processDefinitionId
     * @param processInstanceId
     * @return
     * @throws WorkFlowBusinessRuntimeException 接口调用失败，将错返回给客户端处理
     */
    public InputStream getDiagramByProcessInstanceId(String processDefinitionId,String processInstanceId) throws WorkFlowBusinessRuntimeException{
        Map<String,String> para = new HashMap<String,String>();
        para.put("processInstanceId",processInstanceId);
        para.put("processDefinitionId",processDefinitionId);
        Map<String,Object> map = wqqueryHttpService.callInterfaceString(ConstansURL.GET_DIAGRAM_BY_PROCESS_INSTANCEID,para);
        String data = null;
        if(map!=null){
            if(map.get("state").equals(ConstantsUtils.FAILE)){
                log.error("Flowable-Engine接口异常:"+ MapUtil.getStr(map,"message") );
                throw new WorkFlowBusinessRuntimeException("Flowable-Engine接口异常:"+map.get("message") );
            }

            data = (String) map.get("data");
            InputStream targetStream = null;
            try {
                targetStream = IOUtils.toInputStream(data, StandardCharsets.UTF_8.name());
            } catch (IOException e) {
                e.printStackTrace();
            }
            return targetStream;

            //data = (InputStream) map.get("data");
            //data = IoUtil.toStream( MapUtil.getStr( map,"data" ), Charset.defaultCharset() );
//            data = IoUtil.toStream( MapUtil.getStr( map,"data" ), Charset.defaultCharset()  );

        }
        return null;
    }

    /**
     * 6.获取流程图
     * @param processDefinitionId
     * @param processInstanceId
     * @return
     * @throws WorkFlowBusinessRuntimeException 接口调用失败，将错返回给客户端处理
     */
    public HttpResponse getDiagramByProcessInstanceIdOutPut(String processDefinitionId, String processInstanceId) {
        String constantsUrl = ConstansURL.GET_DIAGRAM_BY_PROCESS_INSTANCEID;
        HttpResponse httpResponse;
        if ( StrUtil.isNotEmpty( processDefinitionId )){
            httpResponse = wqqueryHttpService.callInterfaceOutPut(ConstansURL.GET_DIAGRAM_BY_PROCESS_KEY,processDefinitionId,processInstanceId);
        }else{
            httpResponse = wqqueryHttpService.callInterfaceOutPut(ConstansURL.GET_DIAGRAM_BY_PROCESS_INSTANCEID,processDefinitionId,processInstanceId);
        }
        return httpResponse;
    }

    /**
     * 7.手动创建任务(单人)
     * @param taskId 当前任务id
     * @param processInstanceId 实例id
     * @param targetNodeId 目标节点
     * @param inputUserId 下一办理人
     * @return
     * @throws WorkFlowBusinessRuntimeException 接口调用失败，将错返回给客户端处理
     */
    public void freeFlow(String taskId,String processInstanceId,String targetNodeId,String inputUserId) {
        Map<String,String> para = new HashMap<String,String>();
        para.put("taskId",taskId);
        para.put("processInstanceId",processInstanceId);
        para.put("targetNodeId",targetNodeId);
        para.put("inputUserId",inputUserId);
        wqqueryHttpService.callInterfaceString(ConstansURL.FREE_FLOW,para);
    }

    /**
     * 8.手动创建任务(多人)
     * @param sourceTaskDefinitionKey 上一个任务办理环节 如果填错或者不填，流程图没有连线高亮
     * @param assignees 办理人
     * @param taskName 办理环节名称
     * @param taskDefinitionKey 办理环节key
     * @param processInstanceId 流程实例ID
     * @param processDefinitionId 流程定义ID
     * @param tenantId 租户ID
     * @return
     * @throws WorkFlowBusinessRuntimeException 接口调用失败，将错返回给客户端处理
     */
    public void createTaskEntityImpls(String sourceTaskDefinitionKey,List<String> assignees,String taskName,String taskDefinitionKey,String processInstanceId,String processDefinitionId,String tenantId,Map<String,Object> variables) {
        Map<String,Object> para = new HashMap<String,Object>();
        para.put("sourceTaskDefinitionKey",sourceTaskDefinitionKey);
        para.put("assignees",assignees);
        para.put("taskName",taskName);
        para.put("taskDefinitionKey",taskDefinitionKey);
        para.put("processDefinitionId",processDefinitionId );
        para.put("processInstanceId",processInstanceId);
        para.put("tenantId",tenantId);
        para.put("variables",variables);
        wqqueryHttpService.callInterfaceJson(ConstansURL.CREATE_TASK_ENTITYIMPLS,JacksonUtils.obj2json(para));
    }

    /**
     * 8.手动创建任务(单人)
     * @param sourceTaskDefinitionKey 上一个任务办理环节 如果填错或者不填，流程图没有连线高亮
     * @param assignee 办理人
     * @param taskName 办理环节名称
     * @param taskDefinitionKey 办理环节key
     * @param processInstanceId 流程实例ID
     * @param processDefinitionId 流程定义ID
     * @param tenantId 租户ID
     * @return
     * @throws WorkFlowBusinessRuntimeException 接口调用失败，将错返回给客户端处理
     */
    public void createTaskEntityImpl(String sourceTaskDefinitionKey,String assignee,String taskName,String taskDefinitionKey,String processInstanceId,String processDefinitionId,String tenantId,Map<String,Object> variables) {
        Map<String,Object> para = new HashMap<String,Object>();
        para.put("sourceTaskDefinitionKey",sourceTaskDefinitionKey);
        para.put("assignee",assignee);
        para.put("taskName",taskName);
        para.put("taskDefinitionKey",taskDefinitionKey);
        para.put("processInstanceId",processInstanceId);
        para.put("processDefinitionId",processDefinitionId);
        para.put("tenantId",tenantId);
        para.put("variables",variables);
        wqqueryHttpService.callInterfaceJson(ConstansURL.CREATE_TASK_ENTITYIMPL,JacksonUtils.obj2json(para));
    }

    /**
     * 9.完成当前节点，不再流程下一步
     * 只指定多实例是可以这样完结
     * success 成功
     * fail 失败（单实例，请常规办理）
     * @param taskId
     */
    public void finshTask(String taskId) {
        Map<String,String> para = new HashMap<String,String>();
        para.put("taskId",taskId);
        wqqueryHttpService.callInterfaceString(ConstansURL.FINSH_TASK,para);
    }

    /**
     * 10.多实例加签
     * @param activityId 活动节点ID
     * @param processInstanceId 父实例ID
     * @param vars 变量
     */
    public void addMultiInstanceExecution(String activityId,String processInstanceId,Map<String,Object> vars) {
        String stringJson = JacksonUtils.obj2json(vars);
        Map<String,String> para = new HashMap<String,String>();
        para.put("activityId",activityId);
        para.put("processInstanceId",processInstanceId);
        para.put("stringJson",stringJson);
        wqqueryHttpService.callInterfaceString(ConstansURL.ADD_MULTI_INSTANCE_EXECUTION,para);
    }

    /**
     * 11.多实例减签
     * @param taskId 任务ID
     */
    public void deleteMultiInstanceExecution(String taskId) {
        Map<String,String> para = new HashMap<String,String>();
        para.put("taskId",taskId);
        wqqueryHttpService.callInterfaceString(ConstansURL.DELETE_MULTI_INSTANCE_EXECUTION,para);
    }

    /**
     * 12.删除流程实例（已经结束的也可以删除）
     * @param processInstanceId 实例ID
     */
    public void deleteProcessInstance(String processInstanceId) {
        Map<String,String> para = new HashMap<String,String>();
        para.put("processInstanceId",processInstanceId);
        wqqueryHttpService.callInterfaceString(ConstansURL.DELETE_PROCESS_INSTANCE,para);
    }

    /**
     * 13.升级指定流程的流程定义（升级指定流程的流程定义,流程实例ID如果有多个，逗号分割；流程定义ID和版本号填一个就可以，如果都填写，以流程定义ID为准）
     * 另外本地库中的实例表和任务表请在调用接口成功后更新流程定义ID
     * @param processInstanceIds 实例ID
     * @param processDefinitionId 流程定义ID
     * @param version 版本号
     */
    public void upgradeProcessInstanceVersion(String processInstanceIds,String processDefinitionId,String version , String tenantId) {
        Map<String,String> para = new HashMap<String,String>();
        para.put("processInstanceIds",processInstanceIds);
        para.put("processDefinitionId",processDefinitionId);
        para.put("version",version);
        para.put("tenantId",tenantId);
        MapRemoveNullUtil.removeNullEntry(para);
        wqqueryHttpService.callInterfaceString(ConstansURL.UPGRADE_PROCESS_INSTANCE_VERSION,para);
    }

    /**
     * 根据流程实例ID升级业务库中的流程版本
     * @param processDefinitionKey    流程定义KEY
     * @param processInstanceIds    流程实例ID
     * @param tenantId              租户ID
     */
    public Boolean upgradeBusProcessInstanceVersion(String processDefinitionKey,String processInstanceIds,String tenantId){
        try {
            if (StrUtil.isBlank(tenantId)){
                log.error("租户ID为空！");
                return false;
            }
            if (StrUtil.isBlank(processDefinitionKey)){
                log.error("流程定义KEY为空！");
                return false;
            }
            if (StrUtil.isBlankIfStr(processInstanceIds)){
                log.error("流程实例ID为空！");
                return false;
            }
            Map<String,Object> retMap = definitionsGetByKey(processDefinitionKey,null,tenantId);
            Map<String,Object> data = MapUtil.get(retMap,"data",Map.class);
            String processDefinitionId = MapUtil.getStr(data,"id");
            String deploymentId = MapUtil.getStr(data,"deploymentId");
            String [] processInstIdsArr = processInstanceIds.split(",");
            for(String processInstId:processInstIdsArr){
                //升级流程实例中的流程定义信息
                ActProcessInstModel actProcessInstModel = actProcessInstModelService.getByProcessInstanceId(processInstId);
                actProcessInstModel.setDeploymentId(deploymentId);
                actProcessInstModel.setProcessDefinitionId(processDefinitionId);
                actProcessInstModel = actProcessInstModelService.update(actProcessInstModel);
                if (StrUtil.isEmptyIfStr(actProcessInstModel)){
                    return false;
                }

                //升级流程环节实例中的流程定义信息
                ActTaskInstModel actTaskInstModel = new ActTaskInstModel();
                actTaskInstModel.setProcessDefinitionId(processDefinitionId);
                actTaskInstModel.setProcessInstId(processInstId);
                int ret = actTaskInstModelService.updateProcessDefByProcessInstId(actTaskInstModel);
                if (ret == 0){
                    return false;
                }
            }
            return true;
        }catch (Exception e){
            log.error("业务流程升级失败！");
            Exceptions.printException( e );
            return false;
        }
    }

    /**
     * 14.根据key获得一个流程定义 ,version可以不填，如果不填，获取最新的返回。
     * @param processDefinitionKey 流程定义Key
     * @param version 版本号 （version可以不填，如果不填，获取最新的返回）
     * @param tenantId 租户ID
     */
    public Map<String,Object> definitionsGetByKey(String processDefinitionKey, String version, String tenantId) {
        Map<String,String> para = new HashMap<String,String>();
        para.put("key",processDefinitionKey);
        para.put("version",version);
        para.put("tenantId",tenantId);
        MapRemoveNullUtil.removeNullEntry(para);
        Map<String,Object> map = wqqueryHttpService.callInterfaceString(ConstansURL.DEFINITIONS_GETBY_KEY,para);
        return map;
    }

    public Boolean checkIsLastVersion (String processInstanceId , String tenantId) {
        Map<String,String> para = new HashMap<String,String>();
        para.put("processInstanceId",processInstanceId);
        para.put("tenantId",tenantId);
        MapRemoveNullUtil.removeNullEntry(para);
        Map<String,Object> map  = wqqueryHttpService.callInterfaceString(ConstansURL.CHECK_IS_LAST_VERSION , para);
        Map data = MapUtil.get(map, "data", Map.class);
        return MapUtil.getBool(data , "isLastVersion");
    }

    /**
     * 获取当前环节出去的连线
     * @param taskId    任务ID
     * @return
     */
    public List<Map<String, Object>> getNextFlowNodes(String taskId) {
        List<Map<String, Object>> curTaskOutLines = Lists.newArrayList();
        try {
            Map<String, String> stringParam = Maps.newConcurrentMap();
            stringParam.put("taskId",taskId);
            Map<String,Object> map = wqqueryHttpService.callInterfaceString(ConstansURL.QUERY_NEXT_NODES,stringParam);
            if(StrUtil.isEmptyIfStr(map)){
                if(map.get("state").equals(ConstantsUtils.FAILE)){
                    log.error("Flowable-Engine接口异常:"+map.get("message") );
                    throw new WorkFlowBusinessRuntimeException("Flowable-Engine接口异常:"+map.get("message") );
                }
                curTaskOutLines = (List<Map<String, Object>>) map.get("data");
            }
            return curTaskOutLines;
        }catch (Exception e){
            Exceptions.printException( e );
        }
        return null;
    }
}
