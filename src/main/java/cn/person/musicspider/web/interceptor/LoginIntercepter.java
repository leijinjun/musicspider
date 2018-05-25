package cn.person.musicspider.web.interceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import cn.person.musicspider.result.Response;
import com.alibaba.fastjson.JSONObject;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

public class LoginIntercepter extends HandlerInterceptorAdapter{

	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
		HttpSession session = request.getSession();
		Boolean login = (Boolean) session.getAttribute("login");
		if(login==null||!login) {
			response.getOutputStream().write(JSONObject.toJSONString(Response.UNAUTHORIZED).getBytes("utf-8"));
			return false;
		}
		return super.preHandle(request, response, handler);
	}
}
