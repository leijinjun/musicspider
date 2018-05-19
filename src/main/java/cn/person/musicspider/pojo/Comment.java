package cn.person.musicspider.pojo;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;

import cn.person.musicspider.enums.RelationType;

@Table(name="cs_comment")
public class Comment implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(generator="JDBC",strategy=GenerationType.AUTO)
	private Long commentId;
	
	private String content;
	
	private Long relationId;
	
	private RelationType relationType;
	
	private Long userId;
	
	@Column(name="user_photoURL")
	private String userPhotoURL;
	
	private Date commentTime;
	
	private Integer praiseCount;
	
	
	/**
	 * 其它用户回复的评论
	 */
	private String beReplied;
	
	private String nickname;

	private Date updateTime;

	public Long getCommentId() {
		return commentId;
	}

	public void setCommentId(Long commentId) {
		this.commentId = commentId;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public Long getRelationId() {
		return relationId;
	}

	public void setRelationId(Long relationId) {
		this.relationId = relationId;
	}

	public RelationType getRelationType() {
		return relationType;
	}

	public void setRelationType(RelationType relationType) {
		this.relationType = relationType;
	}

	public Long getUserId() {
		return userId;
	}

	public void setUserId(Long userId) {
		this.userId = userId;
	}

	public String getUserPhotoURL() {
		return userPhotoURL;
	}

	public void setUserPhotoURL(String userPhotoURL) {
		this.userPhotoURL = userPhotoURL;
	}

	public Date getCommentTime() {
		return commentTime;
	}

	public void setCommentTime(Date commentTime) {
		this.commentTime = commentTime;
	}

	public Integer getPraiseCount() {
		return praiseCount;
	}

	public void setPraiseCount(Integer praiseCount) {
		this.praiseCount = praiseCount;
	}

	public String getBeReplied() {
		return beReplied;
	}

	public void setBeReplied(String beReplied) {
		this.beReplied = beReplied;
	}

	public String getNickname() {
		return nickname;
	}

	public void setNickname(String nickname) {
		this.nickname = nickname;
	}

	public Date getUpdateTime() {
		return updateTime;
	}

	public void setUpdateTime(Date updateTime) {
		this.updateTime = updateTime;
	}
}
