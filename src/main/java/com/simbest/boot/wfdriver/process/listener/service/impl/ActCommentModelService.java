package com.simbest.boot.wfdriver.process.listener.service.impl;


import com.simbest.boot.base.service.impl.LogicService;
import com.simbest.boot.util.DateUtil;
import com.simbest.boot.wfdriver.exceptions.FlowableDriverBusinessException;
import com.simbest.boot.wfdriver.process.listener.mapper.ActCommentModelMapper;
import com.simbest.boot.wfdriver.process.listener.model.ActCommentModel;
import com.simbest.boot.wfdriver.process.listener.service.IActCommentModelService;
import com.simbest.boot.wfdriver.process.listener.service.IActProcessInstModelService;
import com.simbest.boot.wfdriver.process.listener.service.IActTaskInstModelService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;


@Slf4j
@Service(value="actCommentModelService")
public class ActCommentModelService extends LogicService<ActCommentModel,String> implements IActCommentModelService {

	private ActCommentModelMapper actCommentModelMapper;

	@Autowired
	private IActTaskInstModelService actTaskInstModelService;

	@Autowired
	private IActProcessInstModelService actProcessInstModelService;

	@Autowired
    public ActCommentModelService ( ActCommentModelMapper actCommentModelMapper ) {
        super( actCommentModelMapper );
        this.actCommentModelMapper = actCommentModelMapper;
    }

    /**
     * 保存审批意见
     * @param currentUserCode 办理人
     * @param comment  审批意见
     * @param processInstId 程实例Id
     * @param taskId 任务ID
     * @param businessKey 实体ID
     */
    @Override
	public void create( String currentUserCode, String comment,String processInstId,String taskId,  String businessKey) {
        try {
            ActCommentModel actCommentModel = new ActCommentModel();
            actCommentModel.setCurrentUserCode(currentUserCode);
            actCommentModel.setContent(comment);
            actCommentModel.setProcessInstId(processInstId);
            actCommentModel.setTaskId(taskId);
            actCommentModel.setTime(DateUtil.getCurrent());
            actCommentModel.setEnabled(true);
            this.wrapCreateInfo(actCommentModel);
            actCommentModelMapper.save(actCommentModel);
        }catch ( Exception e ){
            FlowableDriverBusinessException.printException( e );
        }
	}

    /**
     * 根据流程实例ID删除流程信息
     * @param processInstID
     */
	@Override
	public void deleteByProInstID(String processInstID) {
	    try {
            actCommentModelMapper.deleteByInstID(processInstID);
        }catch ( Exception e ){
        }
	}

    /**
     * 根据流程实例ID和工作项ID 更新审批意见
     * @param actCommentModel    审批意见对象
     * @return
     */
    @Override
    public int updateByPInstIDAndWkID ( ActCommentModel actCommentModel ) {
        int ret = 0;
        try {
            ActCommentModel actCommentModelNew = actCommentModelMapper.getOneData( actCommentModel.getProcessInstId(),actCommentModel.getTaskId() );
            if(actCommentModelNew == null){
                return 0;
            }
            boolean flag = actCommentModelMapper.updateNotNullField(actCommentModel,actCommentModelNew);
            if ( flag ){
                ret = 1;
            }
        }catch ( Exception e ){
            ret = 0;
        }
        return ret;
    }

    /**
     * 根据流程实例ID查询意见信息
     * @param processInId   流程实例ID
     * @return
     */
    @Override
    public List<ActCommentModel> getByProcessinstid ( String processInId ) {
        try {
            return actCommentModelMapper.getByProcessInstId( processInId );
        }catch ( Exception e ){
        }
        return null;
    }

    /**
     * 根据流程实例ID修改流程审批意见无无效状态
     * @param processInstId     流程实例ID
     * @return
     */
    @Override
    public int updateOptMsgtateByProInstId ( String processInstId )  {
        int ret = 0;
        try {
            List<ActCommentModel> ActCommentModelList = actCommentModelMapper.queryAllByProcessInstIdAndEnabled( processInstId,true );
            for ( ActCommentModel ActCommentModel:ActCommentModelList ){
                ActCommentModel.setEnabled( false );
                actCommentModelMapper.save( ActCommentModel );
            }
            ret = 1;
        }catch (Exception e){
        }
        return ret;
    }

    /**
     * 根据流程实例ID和工作项ID 查询审批意见数据
     * @param processInId           流程实例ID
     * @param taskId            工作项ID
     * @return
     */
    @Override
    public ActCommentModel getOneData ( String processInId, String taskId )  {
        ActCommentModel actCommentModel = null;
        try {
            actCommentModel = actCommentModelMapper.getOneData( processInId, taskId );
        }catch (Exception e){
        }
        return actCommentModel;
    }

}
