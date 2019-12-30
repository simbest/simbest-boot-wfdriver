package com.simbest.boot.wfdriver.task.service.impl;

import com.simbest.boot.base.service.impl.GenericService;
import com.simbest.boot.wfdriver.task.model.TaskCallbackRetry;
import com.simbest.boot.wfdriver.task.repository.TaskCallbackRetryRepository;
import com.simbest.boot.wfdriver.task.service.ITaskCallBackRetyService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * <strong>Title : TaskCallBackRetyServiceImpl</strong><br>
 * <strong>Description : 业务回调任务</strong><br>
 * <strong>Create on : $date$</strong><br>
 * <strong>Modify on : $date$</strong><br>
 * <strong>Copyright (C) Ltd.</strong><br>
 *
 * @author LJW lijianwu@simbest.com.cn
 * @version <strong>V1.0.0</strong><br>
 * <strong>修改历史:</strong><br>
 * 修改人 修改日期 修改描述<br>
 * -------------------------------------------<br>
 */
@Slf4j
@Service
public class TaskCallBackRetyServiceImpl extends GenericService<TaskCallbackRetry,String> implements ITaskCallBackRetyService {

    private TaskCallbackRetryRepository taskCallbackRetryRepository;

    @Autowired
    public TaskCallBackRetyServiceImpl(TaskCallbackRetryRepository retryRepository){
        super(retryRepository);
        this.taskCallbackRetryRepository = retryRepository;
    }

}
