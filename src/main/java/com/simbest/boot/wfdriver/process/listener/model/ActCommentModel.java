package com.simbest.boot.wfdriver.process.listener.model;


import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateTimeDeserializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateTimeSerializer;
import com.simbest.boot.base.annotations.EntityIdPrefix;
import com.simbest.boot.base.model.LogicModel;
import com.simbest.boot.constants.ApplicationConstants;
import lombok.*;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.Date;


/**
 *@ClassName ActCommentModel 审批意见
 *@Description 1.该表同flowable下act_hi_comment，act_hi_comment行为类型有AddUserLink、DeleteUserLink、AddGroupLink、DeleteGroupLink、AddComment、AddAttachment、DeleteAttachment，该表仅保存AddComment
 *              2.该表没有监听器获取engine返回的数据，而是在添加意见审批时，同步插入该表
 *@Author Administrator
 *@Date 2019/12/01 16:03
 *@Version 1.0
 **/
@Builder
@Data
@EqualsAndHashCode (callSuper = true)
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table( name = "flowable_comment_model",
        indexes = {
                @Index(columnList = "processInstId",name = "IDX_COMMENT_PROCESSINSTID"),
                @Index(columnList = "taskId",name = "IDX_COMMENT_TASKIDID"),
                @Index(columnList = "enabled",name = "IDX_COMMENT_ENABLED")
        }
)
public class ActCommentModel extends LogicModel {

    @Id
    @Column (length = 40 )
    @GeneratedValue ( generator = "snowFlakeId" )
    @GenericGenerator ( name = "snowFlakeId", strategy = "com.simbest.boot.util.distribution.id.SnowflakeId" )
    @EntityIdPrefix ( prefix = "FAC" ) //主键前缀，此为可选项注解
    protected String id;

    @Column (length = 40 )
	private String currentUserCode;//办理人

    @Column (length = 4000 )
	private String content;//内容

    @Column (length = 40 )
	private String processInstId;//实例id

    @Column (length = 40 )
	private String taskId; //任务id

    @JsonFormat ( pattern = ApplicationConstants.FORMAT_DATE_TIME, timezone = ApplicationConstants.FORMAT_TIME_ZONE )
    @JsonDeserialize ( using = LocalDateTimeDeserializer.class )
    @JsonSerialize ( using = LocalDateTimeSerializer.class )
    @Column(nullable = false)
    private LocalDateTime time;
}
