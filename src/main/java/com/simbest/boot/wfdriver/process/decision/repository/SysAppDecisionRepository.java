package com.simbest.boot.wfdriver.process.decision.repository;

import com.simbest.boot.base.repository.LogicRepository;
import com.simbest.boot.wfdriver.process.decision.model.SysAppDecision;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by Administrator on 2018/5/23/023.
 */
@Repository
public interface SysAppDecisionRepository extends LogicRepository<SysAppDecision, String>{

    //根据appcode以及其下的流程id及活动id获取全部决策信息
    String sql1 = " SELECT ga.*" +
            " FROM sys_app_decision ga where ga.app_code=:appCode and ga.process_def_id=:processDefId and ga.activity_def_id=:activityDefId" +
            " and ga.enabled=1  and ga.removed_time is null " +
            " order by case ga.display_order when null then 10000000 end,ga.display_order asc " ;
    @Query (value = sql1, nativeQuery = true)
    List<SysAppDecision> findDecisions(@Param("appCode") String appCode, @Param("processDefId") String processDefId, @Param("activityDefId") String activityDefId);

    String sql2= " SELECT ga.*" +
            " FROM sys_app_decision as ga where ga.activity_def_id=:activityDefId" +
            " and ga.enabled=1  and ga.removed_time is null " +
            " order by case ga.display_order when null then 10000000 end,ga.display_order asc " ;
    @Query(value = sql2,nativeQuery = true )
    List<SysAppDecision> findDecisionsActivityDefId(@Param("activityDefId") String activityDefId);
}
