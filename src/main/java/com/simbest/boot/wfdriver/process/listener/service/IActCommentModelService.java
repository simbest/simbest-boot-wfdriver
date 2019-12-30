package com.simbest.boot.wfdriver.process.listener.service;

import com.simbest.boot.base.service.ILogicService;
import com.simbest.boot.wfdriver.process.listener.model.ActCommentModel;

import java.util.List;

/**
 *@ClassName IActCommentModelService
 *@Description
 *@Author Administrator
 *@Date 2019/12/01 16:03
 *@Version 1.0
 **/
public interface IActCommentModelService extends ILogicService<ActCommentModel,String> {

	void create(String currentUserCode, String comment,String processInstId,String taskId,  String businessKey);

    /**
     * 根据流程实例ID和工作项ID 更新审批意见
     * @param wfOptMsgModel    审批意见对象
     * @return
     */
    int updateByPInstIDAndWkID ( ActCommentModel wfOptMsgModel );

    /**
     * 根据流程实例ID 删除BPS流程审批意见
     * @param processInstID    流程实例ID
     */
    void deleteByProInstID(String processInstID);

    /**
     * 根据流程实例ID查询意见信息
     * @param processInId   流程实例ID
     * @return
     */
    List<ActCommentModel> getByProcessinstid(String processInId);

    /**
     * 根据流程实例ID修改流程审批意见无无效状态
     * @param processInstId     流程实例ID
     * @return
     */
    int updateOptMsgtateByProInstId(String processInstId) ;

    /**
     * 根据流程实例ID和工作项ID 查询审批意见数据
     * @param processInId           流程实例ID
     * @param taskId            任务ID
     * @return
     */
    ActCommentModel getOneData(String processInId, String taskId) ;

}
