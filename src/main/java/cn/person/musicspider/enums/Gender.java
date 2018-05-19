package cn.person.musicspider.enums;

import cn.person.musicspider.core.EnumType;

/**
 * Created by lei2j on 2018/5/18.
 */
public enum Gender implements EnumType{

    MALE(1,"男"),
    FEMALE(2,"女"),
    UNKNOWN(0,"未知");
    private int code;
    private String text;

    Gender(int code, String text) {
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

    public static Gender gender(int code){
        Gender[] genders = Gender.values();
        for (Gender gender:
             genders) {
            if(gender.getCode()==code){
                return gender;
            }
        }
        throw new IllegalArgumentException("valid code :{}"+code);
    }
}
