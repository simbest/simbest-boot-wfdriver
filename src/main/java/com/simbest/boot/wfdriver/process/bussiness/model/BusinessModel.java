/**
 * 版权所有 © 北京晟壁科技有限公司 2008-2016。保留一切权利!
 */
package com.simbest.boot.wfdriver.process.bussiness.model;


import com.simbest.boot.base.model.LogicModel;
import lombok.Data;

import javax.persistence.Column;

@Data
public class BusinessModel extends LogicModel {

    protected String processDefKey; //流程定义英文名称

    @Column(columnDefinition = "int default 0")
    protected Boolean iscg;

    protected String code;

    protected String receiptTitle;
    
}
