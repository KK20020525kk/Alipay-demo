package com.xxxx.crm.query;


import com.xxxx.crm.base.BaseQuery;

/**
 * @author：柯彬彬
 * @Description:
 * @Date：2022/12/5 21 :28
 * @Version:v1.0
 */
public class UserQuery extends BaseQuery {
    private String userName;    //用户名
    private String email;   //邮箱
    private String phone;   //手机号

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }
}
