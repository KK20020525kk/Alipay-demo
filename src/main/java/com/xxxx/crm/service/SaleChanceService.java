package com.xxxx.crm.service;


import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.xxxx.crm.base.BaseService;
import com.xxxx.crm.dao.SaleChanceMapper;
import com.xxxx.crm.enums.DevResult;
import com.xxxx.crm.enums.StateStatus;
import com.xxxx.crm.query.SaleChanceQuery;
import com.xxxx.crm.utils.AssertUtil;
import com.xxxx.crm.utils.PhoneUtil;
import com.xxxx.crm.vo.SaleChance;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * @author：柯彬彬
 * @Description:
 * @Date：2022/12/3 10 :23
 * @Version:v1.0
 */
@Service
public class SaleChanceService extends BaseService<SaleChance,Integer> {

    @Resource
    private SaleChanceMapper saleChanceMapper;

    /**
     * 多条件查询营销机会(返回的数据格式必须满足Layui中数据表格要求的格式)
     * @param saleChanceQuery
     * @return
     */
    public Map<String,Object> querySaleChanceByParams(SaleChanceQuery saleChanceQuery){
        Map<String, Object> map = new HashMap<>();

        //开启分页
        PageHelper.startPage(saleChanceQuery.getPage(), saleChanceQuery.getLimit());
        //得到对应的分页对象
        PageInfo<SaleChance> pageInfo=new PageInfo<>(saleChanceMapper.selectByParams(saleChanceQuery));

        //设置map对象
        map.put("code",0);
        map.put("msg", "success");
        map.put("count",pageInfo.getTotal());
        //设置分页好的列表
        map.put("data",pageInfo.getList());

        return map;
    }

    /**
     * 添加营销机会
     *      1.参数校验
     *          customerName客户名称    非空
     *          linkMan 联系人          非空
     *          linkPhone   联系号码    非空
     *      2.设置相关参数默认值
     *                state 默认未分配   如果选择分配人  state 为已分配状态
     *                assignTime 默认空   如果选择分配人  分配时间为系统当前时间
     *                devResult  默认未开发  如果选择分配人 devResult 为开发中 0-未开发  1-开发中 2-开发成功 3-开发失败
     *                isValid  默认有效(1-有效  0-无效)
     *                createDate  updateDate:默认系统当前时间
     *     3.执行添加 判断添加结果
     * @param saleChance
     */
    @Transactional(propagation = Propagation.REQUIRED)
    public void addSaleChance(SaleChance saleChance){
        /*校验参数*/
        checkSaleChanceParams(saleChance.getCustomerName(),saleChance.getLinkMan(),saleChance.getLinkPhone());
        /*设置相关字段的默认值*/

        saleChance.setIsValid(1);
        //createdate创建时间 默认是系统当前时间
        saleChance.setCreateDate(new Date());
        //updateDate 默认是系统当前时间
        saleChance.setUpdateDate(new Date());
        //判断是否设置了指派人
        if(StringUtils.isBlank(saleChance.getAssignMan())){
            saleChance.setState(StateStatus.UNSTATE.getType());
            //assignTime指派时间设置为null
            saleChance.setAssignTime(null);
            //开发状态
            saleChance.setDevResult(DevResult.UNDEV.getStatus());
        }else {
            ///如果不为空，则表示设置了指派人
            //state分配状态
            saleChance.setState(StateStatus.STATED.getType());
            //assignTime指派时间 系统当前时间
            saleChance.setAssignTime(new Date());
            //devResult开发状态
            saleChance.setDevResult(DevResult.DEVING.getStatus());
        }
        //执行添加操作，返回受影响的行数
        AssertUtil.isTrue(saleChanceMapper.insertSelective(saleChance)!=1,"添加营销机会失败");

    }

    @Transactional(propagation = Propagation.REQUIRED)
    public void updateSaleChance(SaleChance saleChance){
        //营销机会Id 非空 数据库中对应的记录存在
        AssertUtil.isTrue(null == saleChance.getId(), "待更新记录不存在");
        //主键查询对象
        SaleChance temp=saleChanceMapper.selectByPrimaryKey(saleChance.getId());
        //判断数据库中对应的记录
        AssertUtil.isTrue(temp == null,"待更新的记录不存在");
        //参数校验
        checkSaleChanceParams(saleChance.getCustomerName(),saleChance.getLinkMan(),saleChance.getLinkPhone());
        //更新时间
        saleChance.setUpdateDate(new Date());
        //assignMan指派人
        //判断原始数据是否存在
        if(StringUtils.isBlank(temp.getAssignMan())){//不存在
            //判断修改后的值是否存在
            if (!StringUtils.isBlank(saleChance.getAssignMan())) {  //修改前为空 修改后有值
                //assignTime指派时间 设置为系统当前时间
                saleChance.setAssignTime(new Date());
                //分配状态
                saleChance.setState(StateStatus.STATED.getType());
                //开发状态
                saleChance.setDevResult(DevResult.DEVING.getStatus());
            }
        }else {
            //判断修改后的值是否存在
            if(StringUtils.isBlank(saleChance.getAssignMan())){
                saleChance.setAssignTime(null);
                saleChance.setState(StateStatus.UNSTATE.getType());
                saleChance.setDevResult(DevResult.UNDEV.getStatus());
            }else {
                if(!saleChance.getAssignMan().equals(temp.getAssignMan())){
                    //更新指派时间
                    saleChance.setAssignTime(new Date());
                }else {
                    saleChance.setAssignTime(temp.getAssignTime());
                }
            }
        }
        /*执行 更新操作 判断受影响的行数*/
        AssertUtil.isTrue(saleChanceMapper.updateByPrimaryKeySelective(saleChance) !=1,"更新营销机会失败！！");
    }

    private void checkSaleChanceParams(String customerName, String linkMan, String linkPhone) {
        //customerName客户名称 非空
        AssertUtil.isTrue(StringUtils.isBlank(customerName),"客户名称不能为空");
        //linkMan联系人    非空
        AssertUtil.isTrue(StringUtils.isBlank(linkMan), "联系人不能为空");
        //linkPhone联系号码 非空
        AssertUtil.isTrue(StringUtils.isBlank(linkPhone), "联系号码不能为空");
        //校验联系号码
        AssertUtil.isTrue(!PhoneUtil.isMobile(linkPhone),"联系号码格式不正确");
    }

    @Transactional(propagation = Propagation.REQUIRED)
    public void deleteSaleChance(Integer[] ids){
        AssertUtil.isTrue(null==ids&& ids.length==0,"待删除记录不存在");
        AssertUtil.isTrue(saleChanceMapper.deleteBatch(ids)<0,"营销机会数据删除失败");
    }

    /**
     * 更新营销机会的开发 状态
     * @param id
     * @param devResult
     */
    @Transactional(propagation = Propagation.REQUIRED)
    public void updateSaleChanceDevResult(Integer id, Integer devResult) {
            //判断Id是否为空
        AssertUtil.isTrue(null==id,"待更新记录不存在");
        //通过id查询营销机会数据
        SaleChance saleChance = saleChanceMapper.selectByPrimaryKey(id);
        //判断对象是否为空
        AssertUtil.isTrue(null==saleChance, "待更新记录不存在");
        //设置开发状态
        saleChance.setDevResult(devResult);
        //执行更新操作 判断受影响的行数
        AssertUtil.isTrue(saleChanceMapper.updateByPrimaryKeySelective(saleChance)!=1,"开发状态更新失败");
    }
}
