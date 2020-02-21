package com.simbest.boot.wfdriver.process.listener.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateTimeDeserializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateTimeSerializer;
import com.simbest.boot.base.annotations.EntityIdPrefix;
import com.simbest.boot.base.model.LogicModel;
import com.simbest.boot.constants.ApplicationConstants;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.Date;

/**
 *@ClassName ActTaskInstModel 任务表
 *@Description 流程任务表
 * 提供监听接口，wfengine触发时调用driver的接口完成流程任务数据进入项目库。
 *@Author Administrator
 *@Date 2019/12/01 16:03
 *@Version 1.0
 **/
@Data
@EqualsAndHashCode (callSuper = true)
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table ( name = "flowable_task_inst_model",
         indexes = {
                    @Index(columnList = "processInstId",name = "IDX_TASK_INST_PROCESS_INST_ID"),
                    @Index(columnList = "taskId",name = "IDX_TASK_INST_TASK_ID"),
                    @Index(columnList = "enabled",name = "IDX_TASK_INST_ENABLED")
                  }
        )
public class ActTaskInstModel extends LogicModel {

    @Id
    @Column ( name = "id", length = 40 )
    @GeneratedValue ( generator = "snowFlakeId" )
    @GenericGenerator ( name = "snowFlakeId", strategy = "com.simbest.boot.util.distribution.id.SnowflakeId" )
    @EntityIdPrefix ( prefix = "FAT" ) //主键前缀，此为可选项注解
    protected String id;

    @Column ( unique = true )
    private String taskId;

    private String parentTaskId;

    private String taskDefinitionId;

    private Integer revision;

    private String executionId;

    private String processInstId;

    private String taskDefinitionKey;

    private String processDefinitionId;

    private String scopeId;

    private String subScopeId;

    private String scopeType;

    private String name;

    private String description;

    private String owner;

    private String assignee;

    private String delegationState;

    private Integer priority;

    @JsonFormat ( pattern = ApplicationConstants.FORMAT_DATE_TIME, timezone = ApplicationConstants.FORMAT_TIME_ZONE )
    @JsonDeserialize ( using = LocalDateTimeDeserializer.class )
    @JsonSerialize ( using = LocalDateTimeSerializer.class )
    private LocalDateTime taskCreateTime;

    @JsonFormat ( pattern = ApplicationConstants.FORMAT_DATE_TIME, timezone = ApplicationConstants.FORMAT_TIME_ZONE )
    @JsonDeserialize ( using = LocalDateTimeDeserializer.class )
    @JsonSerialize ( using = LocalDateTimeSerializer.class )
    private LocalDateTime dueDate;

    private String category;

    private Integer suspensionState;

    private String tenantId;

    private String formKey;

    @JsonFormat ( pattern = ApplicationConstants.FORMAT_DATE_TIME, timezone = ApplicationConstants.FORMAT_TIME_ZONE )
    @JsonDeserialize ( using = LocalDateTimeDeserializer.class )
    @JsonSerialize ( using = LocalDateTimeSerializer.class )
    private LocalDateTime claimTime;

    @JsonFormat ( pattern = ApplicationConstants.FORMAT_DATE_TIME, timezone = ApplicationConstants.FORMAT_TIME_ZONE )
    @JsonDeserialize ( using = LocalDateTimeDeserializer.class )
    @JsonSerialize ( using = LocalDateTimeSerializer.class )
    private LocalDateTime endTime;

    @Column(length = 500)
    @ApiModelProperty (value = "当前工单办理人的身份标识,使用#分隔,身份串=userId#orgCode#postId")
    private String participantIdentity;

    @Column (length = 40)
    @ApiModelProperty (value = "源ID,起草环节该ID为-1 ")
    private String fromTaskId;
}
