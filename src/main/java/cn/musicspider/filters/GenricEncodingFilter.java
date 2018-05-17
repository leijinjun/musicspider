package cn.musicspider.filters;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletRequestWrapper;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.Map;

/**
 * Created by lei2j on 2018/5/6.
 */
public class GenricEncodingFilter implements Filter{

    public GenricEncodingFilter() {
    }

    public void destroy() {
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        HttpServletRequest httprequest =(HttpServletRequest)request;
        HttpServletRequest myrequest=new MyRequest(httprequest);
        chain.doFilter(myrequest, response);
    }

    public void init(FilterConfig fConfig) throws ServletException {
    }


    class MyRequest extends HttpServletRequestWrapper {
        private HttpServletRequest httprequest;
        private boolean isaccessGetMap=false;
        public MyRequest(HttpServletRequest request) {
            super(request);
            this.httprequest=request;
        }

        @Override
        public String getParameter(String name) {
            Map<String, String[]> map = getParameterMap();
            return map.get(name)!=null?map.get(name)[0]:null;
        }

        @Override
        public Map<String, String[]> getParameterMap() {
            Map<String, String[]> map=null;
            String method=httprequest.getMethod();
            if(method.equalsIgnoreCase("post")){
                try {
                    httprequest.setCharacterEncoding("utf-8");
                    map = httprequest.getParameterMap();
                    return map;
                } catch (UnsupportedEncodingException e) {
                    e.printStackTrace();
                }
            }else if(method.equalsIgnoreCase("get")){
                map=httprequest.getParameterMap();
                if(!isaccessGetMap){
                    for (Map.Entry<String, String[]> entry : map.entrySet()) {
                        String[] valArr = entry.getValue();
                        if (valArr != null) {
                            for (int i = 0; i < valArr.length; i++) {
                                try {
                                    valArr[i] = new String(valArr[i].getBytes("iso-8859-1"), "utf-8");
                                } catch (UnsupportedEncodingException e) {
                                    e.printStackTrace();
                                }
                            }
                        }
                    }
                    isaccessGetMap=true;
                    return map;
                }
            }
            return map;
        }

        @Override
        public String[] getParameterValues(String name) {
            Map<String, String[]> map = getParameterMap();
            return map.get(name);
        }

    }
}
