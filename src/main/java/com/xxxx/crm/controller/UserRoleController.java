package com.xxxx.crm.controller;


import com.xxxx.crm.base.BaseController;
import com.xxxx.crm.service.UserRoleService;
import org.springframework.stereotype.Controller;

import javax.annotation.Resource;

/**
 * @author：柯彬彬
 * @Description:
 * @Date：2022/12/6 16 :04
 * @Version:v1.0
 */
@Controller
public class UserRoleController extends BaseController {
    @Resource
    private UserRoleService userRoleService;
}
