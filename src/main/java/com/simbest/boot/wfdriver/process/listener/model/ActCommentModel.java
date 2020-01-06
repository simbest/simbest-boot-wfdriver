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
 *@ClassName ActCommentModel 审批意见
 *@Description 1.该表同flowable下act_hi_comment，act_hi_comment行为类型有AddUserLink、DeleteUserLink、AddGroupLink、DeleteGroupLink、AddComment、AddAttachment、DeleteAttachment，该表仅保存AddComment
 *              2.该表没有监听器获取engine返回的数据，而是在添加意见审批时，同步插入该表
 *@Author Administrator
 *@Date 2019/12/01 16:03
 *@Version 1.0
 **/
@Data
@EqualsAndHashCode (callSuper = true)
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table( name = "flowable_act_comment_model",
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

    @Column (length = 100)
    private String businessKey;//单据id

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    @Column(nullable = false)
    private Date time;

}
