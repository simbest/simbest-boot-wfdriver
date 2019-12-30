package com.simbest.boot.wfdriver.process.decision.service.impl;

import com.simbest.boot.base.service.impl.LogicService;
import com.simbest.boot.wfdriver.process.decision.model.SysAppDecision;
import com.simbest.boot.wfdriver.process.decision.repository.SysAppDecisionRepository;
import com.simbest.boot.wfdriver.process.decision.service.ISysAppDecisionService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by Administrator on 2018/5/23/023.
 */
@Slf4j
@Service(value = "sysAppDecisionService")
public class SysAppDecisionService extends LogicService<SysAppDecision,String> implements ISysAppDecisionService {

    private SysAppDecisionRepository sysAppDecisionRepository;


    @Autowired
    public SysAppDecisionService(SysAppDecisionRepository sysAppDecisionRepositorySuper) {
        super(sysAppDecisionRepositorySuper);
        this.sysAppDecisionRepository = sysAppDecisionRepositorySuper;
    }

    @Override
    public List<SysAppDecision> findDecisionsActivityDefId(String activityDefId) {
        return sysAppDecisionRepository.findDecisionsActivityDefId(activityDefId);
    }
}
