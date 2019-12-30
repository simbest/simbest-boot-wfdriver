package com.simbest.boot.wfdriver.process.listener.web;

import com.google.common.collect.Maps;
import com.simbest.boot.base.web.response.JsonResponse;
import com.simbest.boot.wfdriver.process.bussiness.service.IActBusinessStatusService;
import com.simbest.boot.wfdriver.process.listener.model.ActProcessInstModel;
import com.simbest.boot.wfdriver.process.listener.service.IActProcessInstModelService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.Map;
/**
 *@ClassName ActProcessInstListener
 *@Description 流程实例监听，对wfengine提供接口
 *@Author Administrator
 *@Date 2019/12/01 16:03
 *@Version 1.0
 **/
@Slf4j
@Controller
@RequestMapping(value = {"/action/flowable/processlistener"})
public class ActProcessInstListener {
	
	@Autowired
	private IActProcessInstModelService actProcessInstModelService;
	
    @Autowired
    private IActBusinessStatusService statusService;

	/**
	 * 流程启动
	 */
	@PostMapping(value = {"/start","/sso/start","/api/start","/anonymous/start"})
	public JsonResponse start(@RequestBody ActProcessInstModel actProcessInstModel){
		Map<String, Object> o = Maps.newHashMap();
		int ret = 0;
		try{
			ret = actProcessInstModelService.start(actProcessInstModel);
		}catch(Exception e){
		}
        o.put("mes", ret > 0 ? "操作成功!" : "操作失败!");
        o.put("ret", ret);
        o.put("data", null);
		return JsonResponse.success(o);
	}
	
	/**
	 * 流程完成
	 */
	@PostMapping(value = {"/completed","/sso/completed","/api/completed","/anonymous/completed"})
	public JsonResponse completed(@RequestBody ActProcessInstModel actProcessInstModel){
        Map<String, Object> o = Maps.newHashMap();
		int ret = 0;
		try{
			/*流程结束更新结束时间*/
			ret = actProcessInstModelService.updateByProcessInstanceId(actProcessInstModel);
			/*流程结束更新结束时间*/
			ret = statusService.updateListenerByProcess(actProcessInstModel);
			log.debug(String.valueOf(ret));
		}catch(Exception e){
		}
        o.put("mes", ret > 0 ? "操作成功!" : "操作失败!"); // 返回值兼容批量更新
        o.put("ret", ret);
        o.put("data", null);
		return JsonResponse.success(o);
	}
	

}
