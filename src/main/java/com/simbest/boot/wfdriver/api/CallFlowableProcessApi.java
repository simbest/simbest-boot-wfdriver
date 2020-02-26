package com.simbest.boot.wfdriver.api;/**
 * @author Administrator
 * @create 2019/12/5 17:09.
 */

import cn.hutool.core.map.MapUtil;
import com.mzlion.easyokhttp.response.HttpResponse;
import com.simbest.boot.util.json.JacksonUtils;
import com.simbest.boot.wfdriver.exceptions.WorkFlowBusinessRuntimeException;
import com.simbest.boot.wfdriver.http.utils.ConstansURL;
import com.simbest.boot.wfdriver.http.utils.ConstantsUtils;
import com.simbest.boot.wfdriver.http.utils.HttpConfig;
import com.simbest.boot.wfdriver.http.utils.WqqueryHttpService;
import lombok.extern.slf4j.Slf4j;
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
 *@ClassName CallFlowableProcessApi
 *@Description driver项目调用wfengine项目统一代码
 *@Author Administrator
 *@Date 2019/12/5 17:09
 *@Version 1.0
 **/
@Slf4j
@Component
public class CallFlowableProcessApi {
    @Autowired
    private WqqueryHttpService wqqueryHttpService;
    @Autowired
    private HttpConfig httpConfig;

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
     * 根据流程定义ID启动流程实例
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
     * 根据消息启动流程实例
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
        HttpResponse httpResponse = wqqueryHttpService.callInterfaceOutPut(ConstansURL.GET_DIAGRAM_BY_PROCESS_INSTANCEID,processDefinitionId,processInstanceId);
        return httpResponse;
    }
}
