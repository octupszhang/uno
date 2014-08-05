/*
 * Copyright (C), 2002-2014, 鑻忓畞鏄撹喘鐢靛瓙鍟嗗姟鏈夐檺鍏徃
 * FileName: PageController.java
 * Author:   13071604
 * Date:     2014-8-5 涓嬪崍3:01:12
 * Description: //妯″潡鐩殑銆佸姛鑳芥弿杩?     
 * History: //淇敼璁板綍
 * <author>      <time>      <version>    <desc>
 * 淇敼浜哄鍚?            淇敼鏃堕棿            鐗堟湰鍙?                 鎻忚堪
 */
package com.wormchaos.controller;

import java.io.IOException;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.wormchaos.module.User;
import com.wormchaos.service.UserService;
import com.wormchaos.util.UserUtils;
import com.wormchaos.util.filter.LoginCheckFilter;

/**
 * 銆堜竴鍙ヨ瘽鍔熻兘绠?堪銆?br> 
 * 銆堝姛鑳借缁嗘弿杩般?
 *
 * @author 13073004
 * @see [鐩稿叧绫?鏂规硶]锛堝彲閫夛級
 * @since [浜у搧/妯″潡鐗堟湰] 锛堝彲閫夛級
 */
@Controller
public class UserController {
    private UserService userService;
    @RequestMapping("login")
    public void login(HttpServletRequest request, HttpServletResponse response) throws Exception{
        Cookie[] cookies=request.getCookies();
        String userId = null;
        String token = null;
        for (Cookie cookie : cookies) {
            if(cookie.getName().equals("userId")){
            	userId=UserUtils.queryUserId(cookie.getValue());
            }
            if(cookie.getName().equals("token")){
            	token=cookie.getValue();
            }
        }
        if(userId==null||token==null){
        	 response.getWriter().write("null username or password !");
        }
        User u=userService.login(userId,token);
        if(u==null){
        	response.getWriter().write("wrong username or password !");
        }
        response.getWriter().write(u.getUserId()+"login success !,your token is" +UserUtils.createToken(u.getUserId()));
    }

}
