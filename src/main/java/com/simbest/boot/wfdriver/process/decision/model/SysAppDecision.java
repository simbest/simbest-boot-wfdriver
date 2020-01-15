package com.simbest.boot.wfdriver.process.decision.model;

import com.simbest.boot.base.annotations.EntityIdPrefix;
import com.simbest.boot.base.model.LogicModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.*;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;

/**
 * 应用决策项 实体类
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "SYS_APP_DECISION")
public class SysAppDecision extends LogicModel{

    @Id
    @Column(name = "id", length = 40)
    @GeneratedValue(generator = "snowFlakeId")
    @GenericGenerator(name = "snowFlakeId", strategy = "com.simbest.boot.util.distribution.id.SnowflakeId")
    @EntityIdPrefix(prefix = "AD") //主键前缀，此为可选项注解
    private String id;

    @Column(name = "group_id", nullable = true, length = 20)
    @ApiModelProperty(value = "群组表的sid字段")
    @Getter
    @Setter
    private String groupId;

    @Column(name = "app_code", nullable = true, length = 100)
    @ApiModelProperty(value = "应用的code")
    @Getter
    @Setter
    private String appCode;

    @Column(name = "process_def_id", nullable = true, length = 100)
    @ApiModelProperty(value = "流程id")
    @Getter
    @Setter
    private String processDefId;

    @Column(name = "process_def_name", nullable = true, length = 256)
    @ApiModelProperty(value = "流程名")
    @Getter
    @Setter
    private String processDefName;

    @Column(name = "activity_def_id", nullable = true, length = 100)
    @ApiModelProperty(value = "活动id")
    @Getter
    @Setter
    private String activityDefId;

    @Column(name = "activity_def_name", nullable = true, length = 256)
    @ApiModelProperty(value = "活动名")
    @Getter
    @Setter
    private String activityDefName;

    @Column(name = "decision_id", nullable = true, length = 100)
    @ApiModelProperty(value = "决策id")
    @Getter
    @Setter
    private String decisionId;

    @Column(name = "decision_name", nullable = true, length = 256)
    @ApiModelProperty(value = "决策名")
    @Getter
    @Setter
    private String decisionName;

    @Column(name = "decision_config", nullable = true, length = 4000)
    @ApiModelProperty(value = "决策需要的各种类型")
    @Getter
    @Setter
    private String decisionConfig;

    @Column(name = "display_order", nullable = true, length = 60)
    @ApiModelProperty(value = "决策排序")
    @Getter
    @Setter
    private Integer displayOrder;

    @Column(name = "opinion", nullable = true, length = 100)
    @ApiModelProperty(value = "决策审批意见")
    @Getter
    @Setter
    private String opinion;

    @Column(name = "spare2", nullable = true, length = 8)
    @ApiModelProperty(value = "保留字段2")
    @Getter
    @Setter
    private String spare2;

    @Column(name = "decision_type", nullable = true)
    @ApiModelProperty(value = "决策项类型")
    @Getter
    @Setter
    private String decisionType;

    @Column(name = "multi", nullable = false, columnDefinition = "int default 0")
    private Integer multi;//该决策项是否支持多选人，0不支持，1支持。默认为0

    /**根据决策项字段来判断，如果multi是1，就进行判断，是0忽略*/
    @Column(name="minimum",nullable=true,columnDefinition="int default 0")
    private Integer minimum;//决策项支撑多人审批时，至少要选几个

    /**根据决策项字段来判断，如果multi是1，就进行判断，是0忽略*/
    @Column(name="biggest",nullable=true,columnDefinition="int default 0")
    private Integer biggest;//决策项支撑多人审批时，最多可以选多少个


}
