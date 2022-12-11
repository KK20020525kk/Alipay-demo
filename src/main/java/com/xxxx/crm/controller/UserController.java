package com.xxxx.crm.controller;


import com.xxxx.crm.base.BaseController;
import com.xxxx.crm.base.ResultInfo;
import com.xxxx.crm.exceptions.ParamsException;
import com.xxxx.crm.model.UserModel;
import com.xxxx.crm.query.UserQuery;
import com.xxxx.crm.service.UserService;
import com.xxxx.crm.utils.LoginUserUtil;
import com.xxxx.crm.vo.User;
import org.omg.CORBA.OBJ_ADAPTER;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Map;

/**
 * @author：柯彬彬
 * @Description:
 * @Date：2022/12/2 11 :24
 * @Version:v1.0
 */
@Controller
@RequestMapping("user")
public class UserController extends BaseController {

    @Resource
    private UserService userService;

    @PostMapping("login")
    @ResponseBody
        public ResultInfo userLogin(String userName,String userPwd){
            ResultInfo resultInfo = new ResultInfo();
        //调用Service层的方法，得到返回的用户对象
        UserModel userModel=userService.userLogin(userName,userPwd);
        // 将返回的UserModel对象设置到 ResultInfo 对象中
        resultInfo.setResult(userModel);
     /*       //通过 try catch捕获Service 层抛出的异常
        try {
            //调用Service层的方法，得到返回的用户对象
            UserModel userModel=userService.userLogin(userName,userPwd);
            resultInfo.setResult(userModel);
        }catch (ParamsException e){//自定义异常
            e.printStackTrace();
            //设置状态码和提示信息
            resultInfo.setCode(e.getCode());
            resultInfo.setMsg(e.getMsg());
        }catch (Exception e){
            e.printStackTrace();
            resultInfo.setCode(500);
            resultInfo.setMsg("操作失败");
        }*/
        return resultInfo;
        }

    /**
     * ⽤户密码修改
     * @param request
     * @param oldPassword
     * @param newPassword
     * @param confirmPassword
     * @return
     */
        @PostMapping("updatePassword")
        @ResponseBody
        public ResultInfo updateUserPassword(HttpServletRequest request,String oldPassword,String newPassword,String confirmPassword) {
            ResultInfo resultInfo = new ResultInfo();

            //获取userId
            Integer userId = LoginUserUtil.releaseUserIdFromCookie(request);
            //调用Service层的密码修改方法
            userService.updateUserPassword(userId, oldPassword, newPassword, confirmPassword);

           /* //通过try catch捕获 Service层抛出的异常
            try {
                //获取userId
                Integer userId = LoginUserUtil.releaseUserIdFromCookie(request);
                //调用Service层的密码修改方法
                userService.updateUserPassword(userId, oldPassword, newPassword, confirmPassword);
            } catch (ParamsException e) {
                e.printStackTrace();
                //设置状态码和提示信息
                resultInfo.setCode(e.getCode());
                resultInfo.setMsg(e.getMsg());
            }catch (Exception e){
                e.printStackTrace();
                resultInfo.setCode(500);
                resultInfo.setMsg("操作失败");
            }*/
            return resultInfo;
        }

    @RequestMapping("toPasswordPage")
    public String toPasswordPage(){
        return "user/password";
    }

    @RequestMapping("queryAllSales")
    @ResponseBody
    public List<Map<String,Object>> queryAllSales(){

        return userService.queryAllSales();
    }

    /**
     * 分页多条件查询用户列表
     * @param userQuery
     * @return
     */
    @RequestMapping("list")
    @ResponseBody
    public Map<String, Object> selectByParams(UserQuery userQuery){
            return userService.queryByParamsForTable(userQuery);
    }


    /**
     * 进入用户列表页面
     * @return
     */
    @RequestMapping("index")
    public String index(){
        return "user/user";
    }


    /**
     * 添加用户
     * @param user
     * @return
     */
    @PostMapping("add")
    @ResponseBody
    public ResultInfo addUser(User user){
        userService.addUser(user);
        return success("用户添加成功");
    }

    /**
     * 更新用户
     * @param user
     * @return
     */
    @PostMapping("update")
    @ResponseBody
    public ResultInfo update(User user){
        userService.updateUser(user);
        return success("用户更新成功");
    }

    /**
     * 打开添加或修改用户页面
     * @return
     */
    @RequestMapping("toAddOrUpdateUserPage")
    public String toAddOrUpdateUserPage(Integer id,HttpServletRequest request){
        //判断id是否为空 不为空表示更新，查询用户对象
        if (id !=null) {
            //通过id查询用户对象
            User user=userService.selectByPrimaryKey(id);
            //将数据设置到请求域中
            request.setAttribute("userInfo",user);
        }

        return "user/add_update";
    }

    /**
     * 用户删除
     * @param ids
     * @return
     */
    @PostMapping("delete")
    @ResponseBody
    public ResultInfo deleteUser(Integer[] ids){
        userService.deleteByIds(ids);

        return success("用户删除成功！！！");
    }


}
