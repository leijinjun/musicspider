package cn.person.musicspider.enums;

import cn.person.musicspider.core.EnumType;

/**
 * Created by lei2j on 2018/5/20.
 */
public enum AuthType implements EnumType{
    AUTH_NO(0,"未认证"),
    AUTH_V(1,"认证"),
    AUTH_MUSICIAN(2,"音乐人"),
    AUTH_PLAYERS(3,"音乐达人");

    private int code;
    private String text;

    AuthType(int code, String text) {
        this.code = code;
        this.text = text;
    }

    @Override
    public int getCode() {
        return code;
    }

    @Override
    public String getText() {
        return text;
    }

    public static AuthType getAuthType(int code){
        AuthType[] authTypes = AuthType.values();
        for (AuthType authType:
             authTypes) {
            if(code==authType.getCode()){
                return authType;
            }
        }
        throw new IllegalArgumentException("valid code:"+code);
    }
}
