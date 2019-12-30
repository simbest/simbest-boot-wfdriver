package com.simbest.boot.wfdriver.process.operate;

import com.simbest.boot.wf.process.service.IWfOptMsgService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;


@Slf4j
@Service(value = "wfOptMsgManager")
public class WfOptMsgManager implements IWfOptMsgService {

    /**
     * 根据流程实例ID查询流程审批意见  存在子流程 流程审批意见
     * @param processInId       流程实例ID
     * @return
     */
    @Override
    public List<?> getByProInsIdOptMsgs ( Long processInId ) {
        return null;
    }

    @Override
    public List<Map<String, Object>> queryProcessOptMsgDataMap ( Map<String, Object> paramMap ) {
        return null;
    }

    /**
     * 根据流程实例ID查询流程审批意见信息 存在子流程   流程审批意见
     * @param parentProcessInId       父流程实例ID
     * @return
     */
    @Override
    public List<?> getByProInsIdOptMsgsSubFlow ( Long parentProcessInId ) {
        return null;
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

    @Override
    public Object getOneData ( Long processInId, Long workItemId ) {
        return null;
    }

    @Override
    public Object updateWorkOptMstInfo ( Map<String, Object> workItemMsg ) {
        return null;
    }

    @Override
    public int updateOptMsgByProInsIdWorkItemId ( Long processInstId, Long workItemId ) {
        return 0;
    }

    /**
     * 根据任务ID添加任务审批意见
     * @param workItemMsg       参数
     * @return
     */
    @Override
    public int submitApprovalMsg ( Map<String, Object> workItemMsg ) {
        return 0;
    }

    /**
     * 查询流程审批意见
     * @param queryParam       查询参数
     * @return
     */
    @Override
    public List<?> queryComments ( Map<String, Object> queryParam ) {
        return null;
    }
}
