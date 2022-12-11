package com.xxxx.crm.query;


import com.fasterxml.jackson.annotation.JsonFormat;
import com.xxxx.crm.base.BaseQuery;

/**
 * @author：柯彬彬
 * @Description:
 * @Date：2022/12/5 16 :20
 * @Version:v1.0
 */
public class CusDevPlanQuery extends BaseQuery {
    private Integer saleChanceId; //营销机会的主键

    public Integer getSaleChanceId() {
        return saleChanceId;
    }

    public void setSaleChanceId(Integer saleChanceId) {

        this.saleChanceId = saleChanceId;

    }
}
