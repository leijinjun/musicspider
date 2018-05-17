package cn.person.musicspider.web.vo;

import java.util.Date;

import cn.person.musicspider.enums.RelationType;

public class CommentsVo {
	
	private Long commentId;
	private String content;
	private String replied;
	private RelationType relationType;
	private Long relationId;
	private Integer isHot;
	private Long userId;
	private String userPhoto;
	private Date commentTime;
	private Integer praiseCount;
	private String nickname;
	private SongVo song;
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
	public String getReplied() {
		return replied;
	}
	public void setReplied(String replied) {
		this.replied = replied;
	}
	public RelationType getRelationType() {
		return relationType;
	}
	public void setRelationType(RelationType relationType) {
		this.relationType = relationType;
	}
	public Long getRelationId() {
		return relationId;
	}
	public void setRelationId(Long relationId) {
		this.relationId = relationId;
	}
	public Integer getIsHot() {
		return isHot;
	}
	public void setIsHot(Integer isHot) {
		this.isHot = isHot;
	}
	public Long getUserId() {
		return userId;
	}
	public void setUserId(Long userId) {
		this.userId = userId;
	}
	public String getUserPhoto() {
		return userPhoto;
	}
	public void setUserPhoto(String userPhoto) {
		this.userPhoto = userPhoto;
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
	public String getNickname() {
		return nickname;
	}
	public void setNickname(String nickname) {
		this.nickname = nickname;
	}
	public SongVo getSong() {
		return song;
	}
	public void setSong(SongVo song) {
		this.song = song;
	}
	
}
