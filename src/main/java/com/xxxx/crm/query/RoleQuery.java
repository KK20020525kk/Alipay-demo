package com.xxxx.crm.query;


import com.xxxx.crm.base.BaseQuery;

/**
 * @author：柯彬彬
 * @Description:
 * @Date：2022/12/6 17 :22
 * @Version:v1.0
 */
public class RoleQuery extends BaseQuery {

    private String roleName;  //角色名

    public String getRoleName() {
        return roleName;
    }

    public void setRoleName(String roleName) {
        this.roleName = roleName;
    }
}
