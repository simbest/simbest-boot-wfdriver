package com.simbest.boot.wfdriver.task.service.impl;

import com.simbest.boot.base.service.impl.GenericService;
import com.simbest.boot.wfdriver.task.model.TaskCallbackLog;
import com.simbest.boot.wfdriver.task.repository.TaskCallbackLogRepository;
import com.simbest.boot.wfdriver.task.service.ITaskCallBackLogService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * <strong>Title : TaskCallBackLogServiceImpl</strong><br>
 * <strong>Description : 业务回调操作日志</strong><br>
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
public class TaskCallBackLogServiceImpl extends GenericService<TaskCallbackLog,String> implements ITaskCallBackLogService {

    private TaskCallbackLogRepository taskCallbackLogRepository;

    @Autowired
    public TaskCallBackLogServiceImpl(TaskCallbackLogRepository repository){
        super(repository);
        this.taskCallbackLogRepository = repository;
    }
}
