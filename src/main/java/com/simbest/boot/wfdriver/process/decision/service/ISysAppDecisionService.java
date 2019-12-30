package com.simbest.boot.wfdriver.process.decision.service;

import com.simbest.boot.base.service.ILogicService;
import com.simbest.boot.wfdriver.process.decision.model.SysAppDecision;

import java.util.List;

/**
 * Created by Administrator on 2018/5/23/023.
 */
public interface ISysAppDecisionService extends ILogicService<SysAppDecision,String> {

    List<SysAppDecision> findDecisionsActivityDefId(String activityDefId);
}
