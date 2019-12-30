package com.simbest.boot.wfdriver.process.listener.service;

import com.simbest.boot.base.service.ILogicService;
import com.simbest.boot.wfdriver.process.listener.model.ActProcessInstModel;

/**
 *@ClassName IActProcessInstModelService
 *@Description
 *@Author Administrator
 *@Date 2019/12/01 16:03
 *@Version 1.0
 **/
public interface IActProcessInstModelService extends ILogicService<ActProcessInstModel,String> {

    /**
     * 流程开始是执行
     */
	int start(ActProcessInstModel actProcessInstMode);

    /**
     * 流程结束更新结束时间
     */
	int updateByProcessInstanceId(ActProcessInstModel actProcessInstModel);

    /**
     * 根据流程实例ID 查询流程信息
     * @param processInstanceId         流程实例ID
     * @return
     */
    ActProcessInstModel getByProcessInstanceId(String processInstanceId);

    /**
     * 根据流程实例ID 删除流程相关信息
     * @param processInstanceId     流程实例ID
     */
	void deleteByProcessInstanceId(String processInstanceId);

    /**
     * 根据流程实例ID和数据有效状态查询流程实例数据
     * @param processInstanceId       流程实例ID
     * @return
     */
    ActProcessInstModel queryByProcessInstanceIdAndEnabled(String processInstanceId);

}
