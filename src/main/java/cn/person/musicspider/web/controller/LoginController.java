package cn.person.musicspider.web.controller;

import cn.person.musicspider.base.controller.BaseController;
import cn.person.musicspider.config.LoginConifg;
import cn.person.musicspider.result.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpSession;

/**
 * Created by lei2j on 2018/5/23.
 */
@Controller
@RequestMapping("/api/auth")
public class LoginController extends BaseController{

    @Autowired
    private LoginConifg loginConifg;


    @RequestMapping(value = "/login",method = {RequestMethod.POST})
    @ResponseBody
    public Response login(@RequestParam("username")String username, @RequestParam("password")String password, HttpSession session){
        if(loginConifg.getUsername().equals(username)&&loginConifg.getPassword().equals(password)){
            session.setAttribute("login",true);
            return Response.OK;
        }
        return Response.FORBIDDEN;
    }
}
