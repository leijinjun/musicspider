package cn.person.musicspider.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;

/**
 * Created by lei2j on 2018/5/23.
 */
@Configuration
@Component("loginConfig")
public class LoginConifg {

    @Value("${login.username}")
    private  String username;

    @Value("${login.password}")
    private String password;

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }
}
