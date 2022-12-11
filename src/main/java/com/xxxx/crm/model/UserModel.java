package com.xxxx.crm.model;


/**
 * @author：柯彬彬
 * @Description:
 * @Date：2022/12/2 14 :18
 * @Version:v1.0
 */
public class UserModel {
    /*private Integer userId;*/
    private String userName;
    private String trueName;

    public String getUserIdStr() {
        return userIdStr;
    }

    public void setUserIdStr(String userIdStr) {
        this.userIdStr = userIdStr;
    }

    private String userIdStr;   //加密后的id

 /*   public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }*/

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getTrueName() {
        return trueName;
    }

    public void setTrueName(String trueName) {
        this.trueName = trueName;
    }
}
