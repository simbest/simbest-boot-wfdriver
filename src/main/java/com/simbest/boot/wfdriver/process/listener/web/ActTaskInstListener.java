package com.simbest.boot.wfdriver.process.listener.web;

import com.google.common.collect.Maps;
import com.simbest.boot.base.exception.Exceptions;
import com.simbest.boot.base.web.response.JsonResponse;
import com.simbest.boot.wfdriver.process.bussiness.service.IActBusinessStatusService;
import com.simbest.boot.wfdriver.process.listener.model.ActTaskInstModel;
import com.simbest.boot.wfdriver.process.listener.service.IActTaskInstModelService;
import com.simbest.boot.wfdriver.task.UserTaskSubmit;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.Map;
/**
 *@ClassName ActTaskInstListener
 *@Description 流程任务监听，对wfengine提供接口
 *@Author Administrator
 *@Date 2019/12/01 16:03
 *@Version 1.0
 **/
@Slf4j
@Controller
@RequestMapping(value = {"/action/flowable/tasklistener"})
public class ActTaskInstListener {

	@Autowired
	private IActTaskInstModelService actTaskInstModelService;

	@Autowired
	private IActBusinessStatusService statusService;

    @Autowired
    private UserTaskSubmit userTaskSubmit;


	/**
	 * 工作项创建完成 -- 插入
	 */
    @PostMapping(value = {"/created","/sso/created","/api/created","/anonymous/created"})
    @ResponseBody
	public JsonResponse created(@RequestBody ActTaskInstModel actTaskInstModel){
        log.warn( "流程环节创建完成回调后接收的参数：【{}】",actTaskInstModel.toString() );
        Map<String, Object> o = Maps.newHashMap();
		int ret = 0;
		try{
			ret = actTaskInstModelService.created(actTaskInstModel);
		}catch(Exception e){
		    log.error( "回调插入流程环节实例数据异常！" );
            Exceptions.printException( e );
        }
        o.put("mes", ret > 0 ? "操作成功!" : "操作失败!");
        o.put("ret", ret);
        o.put("data", null);
		return JsonResponse.success(o);
	}
	
	/**
	 * 工作项完成后 -- 更新
	 */
    @PostMapping(value = {"/completed","/sso/completed","/api/completed","/anonymous/completed"})
    @ResponseBody
	public JsonResponse completed(@RequestBody ActTaskInstModel actTaskInstModel){
        log.warn( "流程环节完成后回调后接收的参数：【{}】",actTaskInstModel.toString() );
        Map<String, Object> o = Maps.newHashMap();
        int ret = 0;
		try{
			ret = actTaskInstModelService.updateByTaskId(actTaskInstModel);
		}catch(Exception e){
            log.error( "回调更新流程环节实例数据异常！" );
            Exceptions.printException( e );
        }
        o.put("mes", ret > 0 ? "操作成功!" : "操作失败!");
        o.put("ret", ret);
        o.put("data", null);
		return JsonResponse.success(o);
	}

}
