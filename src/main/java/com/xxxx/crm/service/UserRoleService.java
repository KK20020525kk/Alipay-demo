package com.xxxx.crm.service;


import com.xxxx.crm.base.BaseService;
import com.xxxx.crm.dao.UserRoleMapper;
import com.xxxx.crm.vo.UserRole;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * @author：柯彬彬
 * @Description:
 * @Date：2022/12/6 16 :02
 * @Version:v1.0
 */
@Service
public class UserRoleService extends BaseService<UserRole,Integer> {

    @Resource
    private UserRoleMapper userRoleMapper;
}
