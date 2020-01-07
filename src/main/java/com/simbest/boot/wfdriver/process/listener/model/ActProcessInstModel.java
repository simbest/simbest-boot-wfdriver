package com.simbest.boot.wfdriver.process.listener.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.simbest.boot.base.annotations.EntityIdPrefix;
import com.simbest.boot.base.model.LogicModel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.util.Date;

/**
 *@ClassName ActProcessInstModel 实例表
 *@Description 流程实例表
 * 提供监听接口，wfengine触发时调用driver的接口完成流程实例数据进入项目库。
 *@Author Administrator
 *@Date 2019/12/01 16:03
 *@Version 1.0
 **/
@Data
@EqualsAndHashCode (callSuper = true)
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table( name = "flowable_process_inst_model",
        indexes = {
                @Index(columnList = "processInstanceId",name = "IDX_PROINST_INSTID"),
                @Index(columnList = "processDefinitionKey",name = "IDX_PROINST_DEFKEY_STATE"),
                @Index(columnList = "enabled",name = "IDX_PROINST_ENABLED")
        }
)
public class ActProcessInstModel extends LogicModel {

    @Id
    @Column ( name = "id", length = 40 )
    @GeneratedValue ( generator = "snowFlakeId" )
    @GenericGenerator ( name = "snowFlakeId", strategy = "com.simbest.boot.util.distribution.id.SnowflakeId" )
    @EntityIdPrefix ( prefix = "FAP" ) //主键前缀，此为可选项注解
    protected String id;

    private String processInstanceId;
    private String name;
    private String description;
    private String tenantId;
    private String businessKey;
    private String callbackId;
    private String callbackType;
    private String deleteReason;
    private String deploymentId;
    private Long durationInMillis;
    private String endActivityId;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date endTime;
    private String processDefinitionId;
    private String processDefinitionKey;
    private String processDefinitionName;
    private Integer processDefinitionVersion;
    private Integer revision;
    private String startActivityId;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date startTime;
    private String startUserId;
    private String superProcessInstanceId;
}
