package com.xxxx.crm;

import com.alibaba.fastjson.JSON;
import com.xxxx.crm.base.ResultInfo;
import com.xxxx.crm.exceptions.AuthException;
import com.xxxx.crm.exceptions.NoLoginException;
import com.xxxx.crm.exceptions.ParamsException;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerExceptionResolver;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.lang.reflect.Method;

/**
 * @author WongFaaCoi
 * @project CRM
 * @user 彬彬
 * @date 2022年12月03日 01:57:20
 */
@Component
public class GlobalExceptionResolver implements HandlerExceptionResolver {

    /**
     * 重写全局异常处理方法
     *  对于每个handler的返回值，都有所不同，而不同的返回值，全局异常对其处理方式又不经相同，
     *  所以我们需要先获取到handle的返回值，再对其进行全局异常处理
     *      返回值1；json格式数据
     *          通过反射将字符串结果相应出去
     *      返回值2：视图
     *          可直接响应视图
     *
     1、默认将视图作为异常处理的返回对象
     2、判断handler对象是否是方法对象
     3、如果handler是方法对象，将其强转为方法对象HandlerMethod
     4、通过HandlerMethod使用反射获取到注解对象ResponseBody
     5、判断ResponseBody对象是否为空
     6、如果ResponseBody为空，说明该handler返回值是视图
     判断异常是否所属自定义异常，如果是将其强转为自定义异常，并获取自定义异常状态码和信息存储到视图中，return视图对象
     7、如果ResponseBody不为空，说明该handler返回值是json
     既然是json格式，那就一般是返回消息响应模型ResultInfo，先创建对象，设置默认响应信息
     判断异常是否所属自定义异常，如果是将其强转为自定义异常，使用响应流将ResultInfo对象转换成json格式并输出
     8、返回视图
     * @param request
     * @param response
     * @param handler
     * @param ex
     * @return
     */
    @Override
    public ModelAndView resolveException(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) {
        //后来定义了未登录拦截器，其中进行了抛异常的处理，因此需要在全局异常处理类中 进行 异常处理
        if(ex instanceof NoLoginException){
            //如果该异常是NoLoginException，也就是拦截器抛出的此时用户未登录的异常
            //在此要进行捕获，并进行页面挑战，因为用户未登录，所以页面跳转到（重定向）登录页面
            return new ModelAndView("redirect:/index");
        }

        //1、默认将视图作为异常处理的返回对象
        ModelAndView modelAndView = new ModelAndView("error");
        modelAndView.addObject("code", 300);
        modelAndView.addObject("msg", "系统异常，请重试");
        //2、判断handler对象是否是方法对象
        if(handler instanceof HandlerMethod){
            //3、如果handler是方法对象，将其强转为方法对象HandlerMethod
            HandlerMethod handlerMethod = (HandlerMethod) handler;
            //4、通过HandlerMethod使用反射获取到注解对象ResponseBody
            Method method = handlerMethod.getMethod();
            ResponseBody annotation = method.getAnnotation(ResponseBody.class);
            //5、判断ResponseBody对象是否为空
            if (annotation == null) {
                //6、如果ResponseBody为空，说明该handler返回值是视图
                //判断异常是否所属自定义异常，如果是将其强转为自定义异常，并获取自定义异常状态码和信息存储到视图中，return视图对象
                if(ex instanceof ParamsException){
                    ParamsException paramsException = (ParamsException) ex;
                    modelAndView.addObject("code", paramsException.getCode());
                    modelAndView.addObject("msg", paramsException.getMsg());
                }else if(ex instanceof AuthException){  //认证异常
                    AuthException authException = (AuthException) ex;
                    modelAndView.addObject("code", authException.getCode());
                    modelAndView.addObject("msg", authException.getMsg());
                }
                return modelAndView;
            }else {
                //7、如果ResponseBody不为空，说明该handler返回值是json
                //既然是json格式，那就一般是返回消息响应模型ResultInfo，先创建对象，设置默认响应信息
                ResultInfo resultInfo = new ResultInfo();
                resultInfo.setCode(500);
                resultInfo.setMsg("系统异常，请重试");
                //判断异常是否所属自定义异常，如果是将其强转为自定义异常，使用响应流将ResultInfo对象转换成json格式并输出
                if(ex instanceof ParamsException){
                    ParamsException paramsException = (ParamsException) ex;
                    resultInfo.setCode(paramsException.getCode());
                    resultInfo.setMsg(paramsException.getMsg());

                }else  if(ex instanceof AuthException){   //认证异常
                    AuthException authException = (AuthException) ex;
                    resultInfo.setCode(authException.getCode());
                    resultInfo.setMsg(authException.getMsg());
                }
                String string = JSON.toJSONString(resultInfo);
                response.setContentType("application/json;charset=UTF-8");
                try (PrintWriter writer = response.getWriter()) {
                    writer.write(string);
                } catch (IOException e) {
                    e.printStackTrace();
                }
                return null;
            }
        }
        //8、返回视图
        return modelAndView;
    }
}