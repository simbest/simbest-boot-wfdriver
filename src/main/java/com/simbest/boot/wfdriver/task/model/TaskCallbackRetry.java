package com.simbest.boot.wfdriver.task.model;

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
import lombok.NoArgsConstructor;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.time.LocalDateTime;

/**
 * <strong>Title : TaskCallbackRetry</strong><br>
 * <strong>Description : 业务回调任务异常信息</strong><br>
 * <strong>Create on : 2018/7/17</strong><br>
 * <strong>Modify on : 2018/7/17</strong><br>
 * <strong>Copyright (C) Ltd.</strong><br>
 *
 * @author LJW lijianwu@simbest.com.cn
 * @version <strong>V1.0.0</strong><br>
 *          <strong>修改历史:</strong><br>
 *          修改人 修改日期 修改描述<br>
 *          -------------------------------------------<br>
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table (name = "act_task_callback_retry")
public class TaskCallbackRetry extends GenericModel {

    @Id
    @Column ( name = "id", length = 40 )
    @GeneratedValue ( generator = "snowFlakeId" )
    @GenericGenerator ( name = "snowFlakeId", strategy = "com.simbest.boot.util.distribution.id.SnowflakeId" )
    @EntityIdPrefix ( prefix = "E" ) //主键前缀，此为可选项注解
    private String id;

    private String actBusinessStatusId;                           //业务操作关联ID

    @Column (name = "taskJobClass", nullable = false)
	private String taskJobClass;                                //业务调用操作对象

    @JsonFormat (pattern = ApplicationConstants.FORMAT_DATE_TIME, timezone = ApplicationConstants.FORMAT_TIME_ZONE)
    @JsonDeserialize (using = LocalDateTimeDeserializer.class)
    @JsonSerialize (using = LocalDateTimeSerializer.class)
    @Column (name = "lastExecuteDate", nullable = false)
    private LocalDateTime lastExecuteDate;                      //最后执行时间

    @Column (name = "executeTimes", nullable = false)
    private Integer executeTimes;                               //执行次数

    @Column (name = "callbackType", nullable = false)
    private String callbackType;                                //业务回调类型

    @Column (name = "userName", nullable = false)
    private String userName;                                    //业务操作人
}