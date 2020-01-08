package com.simbest.boot.wfdriver.process.bussiness.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateTimeDeserializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateTimeSerializer;
import com.simbest.boot.base.annotations.EntityIdPrefix;
import com.simbest.boot.base.model.GenericModel;
import com.simbest.boot.constants.ApplicationConstants;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import java.time.LocalDateTime;

/**
 *@ClassName ActBusinessStatus
 *@Description 业务流程数据 businessKey processInstId processDefKey 保存了流程和业务的关系
 *@Author Administrator
 *@Date 2019/12/01 16:03
 *@Version 1.0
 **/
@Data
@EqualsAndHashCode (callSuper = true)
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "act_business_status")
public class ActBusinessStatus extends GenericModel {

    @Id
    @Column (length = 40 )
    @GeneratedValue ( generator = "snowFlakeId" )
    @GenericGenerator ( name = "snowFlakeId", strategy = "com.simbest.boot.util.distribution.id.SnowflakeId" )
    @EntityIdPrefix ( prefix = "BWF" ) //主键前缀，此为可选项注解
    private String id;

	@Column(nullable = false, columnDefinition = "int default 1")
	protected Boolean enabled;

	@Column(nullable = false, columnDefinition = "int default 0")
	protected Boolean removed;

    private String receiptTitle; //单据标题

    private String receiptCode; //单据编码

    @Column(columnDefinition = "int default 0")
    protected Boolean iscg;

    private String businessKey; //业务流程主键

    private String processInstId; //流程实例Id

    private Integer currentState;//BPS当前状态  未启动（1）、运行（2）、挂起（3）、完成（7）、终止（8）

    private String createUserId; //业务创建人Id

    private String createUserName; //业务创建人名称

    private String createOrgCode;  //业务创建组织编码

    private String createOrgName; //业务创建组织名称

    private String previousAssistant; //上一环节处理人SysUser表中username

    private String previousAssistantName; //上一环节处理人中文名称

    @CreationTimestamp
    @JsonFormat ( pattern = ApplicationConstants.FORMAT_DATE_TIME, timezone = ApplicationConstants.FORMAT_TIME_ZONE )
    @JsonDeserialize ( using = LocalDateTimeDeserializer.class )
    @JsonSerialize ( using = LocalDateTimeSerializer.class )
    private LocalDateTime previousAssistantDate;//上一环节处理时间

    private String previousAssistantOrgCode;     //上一环节处理人所属组织机构id

    private String previousAssistantOrgName;    //上一环节处理人所属组织机构名称

    @CreationTimestamp
    @JsonFormat(pattern = ApplicationConstants.FORMAT_DATE_TIME, timezone = ApplicationConstants.FORMAT_TIME_ZONE)
    @JsonDeserialize (using = LocalDateTimeDeserializer.class)
    @JsonSerialize (using = LocalDateTimeSerializer.class)
    private LocalDateTime startTime; //流程启动时间

    @UpdateTimestamp
    @JsonFormat(pattern = ApplicationConstants.FORMAT_DATE_TIME, timezone = ApplicationConstants.FORMAT_TIME_ZONE)
    @JsonDeserialize (using = LocalDateTimeDeserializer.class)
    @JsonSerialize (using = LocalDateTimeSerializer.class)
    private LocalDateTime endTime; //流程结束时间

    private Long duration; //流程持续时间

    @Column(length = 3)
    private String pmInstType;   //流程单据类型

    private String parentProcessInstId; //父流程实例ID

    private String processDefKey;      //流程定义key
}
