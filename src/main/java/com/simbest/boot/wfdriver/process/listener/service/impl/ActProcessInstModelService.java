package com.simbest.boot.wfdriver.process.listener.service.impl;

import com.simbest.boot.base.exception.Exceptions;
import com.simbest.boot.base.service.impl.LogicService;
import com.simbest.boot.util.DateUtil;
import com.simbest.boot.uums.api.user.UumsSysUserinfoApi;
import com.simbest.boot.wfdriver.process.bussiness.service.IActBusinessStatusService;
import com.simbest.boot.wfdriver.process.listener.mapper.ActProcessInstModelMapper;
import com.simbest.boot.wfdriver.process.listener.model.ActProcessInstModel;
import com.simbest.boot.wfdriver.process.listener.service.IActCommentModelService;
import com.simbest.boot.wfdriver.process.listener.service.IActProcessInstModelService;
import com.simbest.boot.wfdriver.process.listener.service.IActTaskInstModelService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;


@Slf4j
@Service(value="actProcessInstModelService")
public class ActProcessInstModelService extends LogicService<ActProcessInstModel,String> implements IActProcessInstModelService {

	private ActProcessInstModelMapper actProcessInstModelMapper;

	@Autowired
	private IActBusinessStatusService actBusinessStatusService;

	@Autowired
	private IActTaskInstModelService actTaskInstModelService;

	@Autowired
	private IActCommentModelService actCommentModelService;

    @Autowired
    private UumsSysUserinfoApi uumsSysUserinfoApi;
	
	@Autowired
    public ActProcessInstModelService ( ActProcessInstModelMapper mapper ) {
        super(mapper);
        this.actProcessInstModelMapper = mapper;
    }

    /**
     * 流程开始 - 提交
     * @return
     */
	@Override
	public int start(ActProcessInstModel actProcessInstMode) {
        int ret = 0;
        try {
            actProcessInstMode.setCreator("hadmin");
            actProcessInstMode.setModifier( "hadmin" );
            actProcessInstMode.setEnabled(true);
            actProcessInstModelMapper.save(actProcessInstMode);
            ret = 1;
        }catch (Exception e){
            ret = 0;
            log.error(Exceptions.getStackTraceAsString(e));
        }
        return ret;
	}

    /**
     * 流程结束更新结束时间
     */
    @Override
    public int updateByProcessInstanceId(ActProcessInstModel actProcessInstModel) {
        int ret = 0;
        try {
            actProcessInstModel.setEnabled(true);
            actProcessInstModel.setModifier( "hadmin" );
            actProcessInstModel.setModifiedTime(DateUtil.date2LocalDateTime(new Date()));
            actProcessInstModelMapper.updateByProcessInstanceId(actProcessInstModel);
            ret = 1;
        }catch (Exception e){
            ret = 0;
            log.error(Exceptions.getStackTraceAsString(e));
        }
        return ret;
    }

	/**
	 * 删除流程实例
	 */
	@Override
	public void deleteByProcessInstanceId(String processInstanceId)  {
		/*删除流程实体*/
		ActProcessInstModel o = getByProcessInstanceId(processInstanceId);
		delete(o);
		/*删除工作项实体*/
		actTaskInstModelService.deleteByProInsId(processInstanceId);
		
	}

    /**
     * 根据流程实例ID查询流程实例信息
     * @param processInstanceId         流程实例ID
     * @return
     */
	@Override
	public ActProcessInstModel getByProcessInstanceId( String processInstanceId) {
		return actProcessInstModelMapper.getByProcessInstanceId(processInstanceId);
	}



    /**
     * 根据流程实例ID和数据有效状态查询流程实例数据
     * @param processInstanceId       流程实例ID
     * @return
     */
    @Override
    public ActProcessInstModel queryByProcessInstanceIdAndEnabled ( String processInstanceId)  {
        try {
            return actProcessInstModelMapper.queryByProcessInstanceIdAndEnabled( processInstanceId,true);
        }catch ( Exception e ){
            return null;
        }
    }

}
