package cn.person.musicspider.pojo;

import cn.person.musicspider.base.pojo.BasePO;
import cn.person.musicspider.enums.AuthType;
import cn.person.musicspider.enums.Gender;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;
import java.sql.Timestamp;
import java.util.Date;

/**
 * Created by lei2j on 2018/5/12.
 */
@Table(name = "cs_user")
public class User extends BasePO implements Serializable{

    @Id
    @GeneratedValue(generator = "JDBC",strategy = GenerationType.AUTO)
    private Long userId;

    private String nickname;

    public String photoUrl;

    public Integer userType;

    public Integer vipType;

    private Integer level;

    private AuthType authType;

    private String title;

    private Gender gender;

    private String area;

    private Date birthDay;

    private String tag;

    private Integer fansCount;

    private Integer attentionCount;

    private Timestamp updateTime;

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

    public Integer getLevel() {
        return level;
    }

    public void setLevel(Integer level) {
        this.level = level;
    }

    public AuthType getAuthType() {
        return authType;
    }

    public void setAuthType(AuthType authType) {
        this.authType = authType;
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

    @Override
    public Timestamp getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Timestamp updateTime) {
        this.updateTime = updateTime;
    }

    public Date getBirthDay() {
        return birthDay;
    }

    public void setBirthDay(Date birthDay) {
        this.birthDay = birthDay;
    }

    @Override
    public String toString() {
        final StringBuffer sb = new StringBuffer("User{");
        sb.append("userId=").append(userId);
        sb.append(", nickname='").append(nickname).append('\'');
        sb.append(", photoUrl='").append(photoUrl).append('\'');
        sb.append(", userType=").append(userType);
        sb.append(", vipType=").append(vipType);
        sb.append(", level=").append(level);
        sb.append(", authType=").append(authType);
        sb.append(", title='").append(title).append('\'');
        sb.append(", gender=").append(gender);
        sb.append(", area='").append(area).append('\'');
        sb.append(", birthDay=").append(birthDay);
        sb.append(", tag='").append(tag).append('\'');
        sb.append(", fansCount=").append(fansCount);
        sb.append(", attentionCount=").append(attentionCount);
        sb.append(", updateTime=").append(updateTime);
        sb.append('}');
        return sb.toString();
    }
}
