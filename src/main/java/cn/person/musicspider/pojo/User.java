package cn.person.musicspider.pojo;

import cn.person.musicspider.base.pojo.BasePO;
import cn.person.musicspider.enums.Gender;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;
import java.sql.Timestamp;

/**
 * Created by lei2j on 2018/5/12.
 */
@Table(name = "cs_user")
public class User extends BasePO implements Serializable{

    @Id
    @GeneratedValue(generator = "JDBC",strategy = GenerationType.AUTO)
    private Long userId;

    private String nickname;

    private String name;

    public String photoUrl;

    public Integer userType;

    public Integer vipType;

    private Integer level;

    private Integer isAuth;

    private String title;

    private Gender gender;

    private String area;

    private String tag;

    private String descipt;

    private Integer fansCount;

    private Integer attentionCount;

    private Timestamp update_time;

    public User() {
    }

    public User(String nickname, String name, String photoUrl, Integer userType, Integer vipType, Integer level, Integer isAuth, String title,
                Gender gender, String area, String tag, String descipt, Integer fansCount, Integer attentionCount, Timestamp update_time) {
        this.nickname = nickname;
        this.name = name;
        this.photoUrl = photoUrl;
        this.userType = userType;
        this.vipType = vipType;
        this.level = level;
        this.isAuth = isAuth;
        this.title = title;
        this.gender = gender;
        this.area = area;
        this.tag = tag;
        this.descipt = descipt;
        this.fansCount = fansCount;
        this.attentionCount = attentionCount;
        this.update_time = update_time;
    }

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

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getLevel() {
        return level;
    }

    public void setLevel(Integer level) {
        this.level = level;
    }

    public Integer getIsAuth() {
        return isAuth;
    }

    public void setIsAuth(Integer isAuth) {
        this.isAuth = isAuth;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Gender getGender() {
        return gender;
    }

    public void setGender(Gender gender) {
        this.gender = gender;
    }

    public String getArea() {
        return area;
    }

    public void setArea(String area) {
        this.area = area;
    }

    public String getTag() {
        return tag;
    }

    public void setTag(String tag) {
        this.tag = tag;
    }

    public String getDescipt() {
        return descipt;
    }

    public void setDescipt(String descipt) {
        this.descipt = descipt;
    }

    public Integer getFansCount() {
        return fansCount;
    }

    public void setFansCount(Integer fansCount) {
        this.fansCount = fansCount;
    }

    public Integer getAttentionCount() {
        return attentionCount;
    }

    public void setAttentionCount(Integer attentionCount) {
        this.attentionCount = attentionCount;
    }

    public Timestamp getUpdate_time() {
        return update_time;
    }

    public void setUpdate_time(Timestamp update_time) {
        this.update_time = update_time;
    }
}
