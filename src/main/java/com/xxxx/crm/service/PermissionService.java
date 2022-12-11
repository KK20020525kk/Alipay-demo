package com.xxxx.crm.service;


import com.xxxx.crm.base.BaseService;
import com.xxxx.crm.dao.PermissionMapper;
import com.xxxx.crm.vo.Permission;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * @author：柯彬彬
 * @Description:
 * @Date：2022/12/7 17 :22
 * @Version:v1.0
 */
@Service
public class PermissionService extends BaseService<Permission,Integer> {

    @Resource
    private PermissionMapper permissionMapper;

    /**
     * 通过查询用户拥有的角色 角色拥有的资源 得到用户的资源列表（资源权限码）
     * @param userId
     * @return
     */
    public List<String> queryUserHasRoleHasPermissionByUserId(Integer userId) {
        return permissionMapper.queryUserHasRoleHasPermissionByUserId(userId);
    }
}
