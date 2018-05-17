package cn.person.musicspider.pojo;

import cn.person.musicspider.base.pojo.BasePO;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * Created by lei2j on 2018/5/12.
 */
@Table(name = "cs_user")
public class User extends BasePO {

    @Id
    @GeneratedValue(generator = "JDBC",strategy = GenerationType.AUTO)
    private Long userId;

    private String nickname;

    public String photoUrl;

    public Integer userType;

    public Integer vipType;

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public String getPhotoUrl() {
        return photoUrl;
    }

    public void setPhotoUrl(String photoUrl) {
        this.photoUrl = photoUrl;
    }

    public Integer getUserType() {
        return userType;
    }

    public void setUserType(Integer userType) {
        this.userType = userType;
    }

    public Integer getVipType() {
        return vipType;
    }

    public void setVipType(Integer vipType) {
        this.vipType = vipType;
    }
}
