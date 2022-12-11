package com.xxxx.crm.controller;


import com.xxxx.crm.annoation.RequiredPermission;
import com.xxxx.crm.base.BaseController;
import com.xxxx.crm.base.ResultInfo;
import com.xxxx.crm.enums.StateStatus;
import com.xxxx.crm.query.SaleChanceQuery;
import com.xxxx.crm.service.SaleChanceService;
import com.xxxx.crm.utils.CookieUtil;
import com.xxxx.crm.utils.LoginUserUtil;
import com.xxxx.crm.vo.SaleChance;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Map;

/**
 * @author：柯彬彬
 * @Description:
 * @Date：2022/12/3 10 :27
 * @Version:v1.0
 */

@Controller
@RequestMapping("sale_chance")
public class SaleChanceController extends BaseController {

    @Resource
    private SaleChanceService saleChanceService;

    /**
     * 营销机会数据查询（分页多条件查询）
     *      如果flag不为空 且值为1 表示当前查询的是客户开发计划，否则查询营销机会数据
     * @param query
     * @return
     */
    @RequiredPermission(code = "101001")
    @RequestMapping("list")
    @ResponseBody
    public Map<String,Object> querySaleChanceByParams(SaleChanceQuery query,Integer flag,HttpServletRequest request) {
        //判断flag的值
        if(flag !=null && flag==1){
            //查询客户开发计划
            //设置分配状态
            query.setState(StateStatus.STATED.getType());
            //设置指派人（当前登录用户的Id）
            //从cookie中获取当前登录用户的Id
            Integer userId= LoginUserUtil.releaseUserIdFromCookie(request);
            query.setAssignMan(userId);
        }
            return saleChanceService.querySaleChanceByParams(query);
    }

    /**
     * 进入营销机会管理页面
     * @return
     */
    @RequiredPermission(code = "1010")
    @RequestMapping("index")
    public String index(){
        return "saleChance/sale_chance";
    }

    @RequiredPermission(code = "101002")
    @PostMapping("add")
    @ResponseBody
    public ResultInfo addSaleChance(SaleChance saleChance, HttpServletRequest request){
        //从Cookie中获取当前登录的用户名
        String userName= CookieUtil.getCookieValue(request,"userName");
        //设置用户名到营销机会对象
        saleChance.setCreateMan(userName);
        //调用Service层的添加方法
        saleChanceService.addSaleChance(saleChance);
        return success("营销机会数据添加成功");
    }

    /**
     * 进入添加营销机会数据页面
     * @return
     */
    @RequestMapping("toSaleChancePage")
    public String toSaleChancePage(Integer saleChanceId,HttpServletRequest request) {
        if(saleChanceId!=null){
            SaleChance saleChance=saleChanceService.selectByPrimaryKey(saleChanceId);
            request.setAttribute("saleChance",saleChance);
        }
        return "saleChance/add_update";
    }

    @RequiredPermission(code = "101004")
    @PostMapping("update")
    @ResponseBody
    public ResultInfo updateSaleChance(SaleChance saleChance){
        System.out.println("66666");
        //调用Service层的添加方法
        saleChanceService.updateSaleChance(saleChance);
        return success("营销机会数据更新成功");
    }

    @RequiredPermission(code = "101003")
    @RequestMapping("delete")
    @ResponseBody
    public ResultInfo deleteSaleChance(Integer[] ids){
        saleChanceService.deleteBatch(ids);
        return success("营销机会数据删除成功");
    }

    /**
     * 更新营销机会的开发状态
     * @param id
     * @param devResult
     * @return
     */
    @PostMapping("updateSaleChanceDevResult")
    @ResponseBody
    public ResultInfo updateSaleChanceDevResult(Integer id,Integer devResult){
        saleChanceService.updateSaleChanceDevResult(id,devResult);
        return success("开发状态更新成功");
    }

}
