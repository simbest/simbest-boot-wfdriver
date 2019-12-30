package com.simbest.boot.wfdriver.process.operate;

import com.simbest.boot.wf.process.service.IActivityManagerService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;


@Slf4j
@Component
public class WfBackActivityManager implements IActivityManagerService {

    @Override
    public boolean backProcessActivityByWorkItemId ( Long processInstId, Long currWorkItemID, Long destWorkItemID, String rollBackStrategy ) {
        return false;
    }
}
