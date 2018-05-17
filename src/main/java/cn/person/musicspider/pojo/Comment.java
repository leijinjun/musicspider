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
	private Long id;
	
	@Column(name="comment_id")
	private Long commentId;
	
	@Column(name="content")
	private String content;
	
	@Column(name="relation_id")
	private Long relationId;
	
	@Column(name="relation_type")
	private RelationType relationType;
	
	@Column(name="is_hot_comment")
	private Boolean isHotComment;
	
	@Column(name="user_id")
	private Long userId;
	
	@Column(name="user_photoURL")
	private String userPhotoURL;
	
	@Column(name="comment_time")
	private Date commentTime;
	
	@Column(name="praise_count")
	private Integer praiseCount;
	
	
	/**
	 * 其它用户回复的评论
	 */
	@Column(name="be_replied")
	private String beReplied;
	
	@Column(name="nickname")
	private String nickname;
	@Column(name = "update_time")
	private Date updateTime;
	@Transient
	private List<Comment> comments;
	
	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	/**
	 * @return the commentId
	 */
	public Long getCommentId() {
		return commentId;
	}
	/**
	 * @param commentId the commentId to set
	 */
	public void setCommentId(Long commentId) {
		this.commentId = commentId;
	}
	/**
	 * @return the content
	 */
	public String getContent() {
		return content;
	}
	/**
	 * @param content the content to set
	 */
	public void setContent(String content) {
		this.content = content;
	}
	/**
	 * @return the relationId
	 */
	public Long getRelationId() {
		return relationId;
	}
	/**
	 * @param relationId the relationId to set
	 */
	public void setRelationId(Long relationId) {
		this.relationId = relationId;
	}
	/**
	 * @return the isHotComment
	 */
	public Boolean getIsHotComment() {
		return isHotComment;
	}
	/**
	 * @param isHotComment the isHotComment to set
	 */
	public void setIsHotComment(Boolean isHotComment) {
		this.isHotComment = isHotComment;
	}
	/**
	 * @return the userId
	 */
	public Long getUserId() {
		return userId;
	}
	/**
	 * @param userId the userId to set
	 */
	public void setUserId(Long userId) {
		this.userId = userId;
	}
	/**
	 * @return the userPhotoURL
	 */
	public String getUserPhotoURL() {
		return userPhotoURL;
	}
	/**
	 * @param userPhotoURL the userPhotoURL to set
	 */
	public void setUserPhotoURL(String userPhotoURL) {
		this.userPhotoURL = userPhotoURL;
	}
	/**
	 * @return the commentTime
	 */
	public Date getCommentTime() {
		return commentTime;
	}
	/**
	 * @param commentTime the commentTime to set
	 */
	public void setCommentTime(Date commentTime) {
		this.commentTime = commentTime;
	}
	/**
	 * @return the praiseCount
	 */
	public Integer getPraiseCount() {
		return praiseCount;
	}
	/**
	 * @param praiseCount the praiseCount to set
	 */
	public void setPraiseCount(Integer praiseCount) {
		this.praiseCount = praiseCount;
	}
	/**
	 * @return the comments
	 */
	public List<Comment> getComments() {
		return comments;
	}
	/**
	 * @param comments the comments to set
	 */
	public void setComments(List<Comment> comments) {
		this.comments = comments;
	}
	/**
	 * @return the relationType
	 */
	public RelationType getRelationType() {
		return relationType;
	}
	/**
	 * @param relationType the relationType to set
	 */
	public void setRelationType(RelationType relationType) {
		this.relationType = relationType;
	}
	/**
	 * @return the beReplied
	 */
	public String getBeReplied() {
		return beReplied;
	}
	/**
	 * @param beReplied the beReplied to set
	 */
	public void setBeReplied(String beReplied) {
		this.beReplied = beReplied;
	}
	/**
	 * @return the nickname
	 */
	public String getNickname() {
		return nickname;
	}
	/**
	 * @param nickname the nickname to set
	 */
	public void setNickname(String nickname) {
		this.nickname = nickname;
	}

	public Date getUpdateTime() {
		return updateTime;
	}

	public void setUpdateTime(Date updateTime) {
		this.updateTime = updateTime;
	}

	/* (non-Javadoc)
             * @see java.lang.Object#toString()
             */
	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("Comment [commentId=");
		builder.append(commentId);
		builder.append(", content=");
		builder.append(content);
		builder.append(", relationId=");
		builder.append(relationId);
		builder.append(", isHotComment=");
		builder.append(isHotComment);
		builder.append(", userId=");
		builder.append(userId);
		builder.append(", userPhotoURL=");
		builder.append(userPhotoURL);
		builder.append(", commentTime=");
		builder.append(commentTime);
		builder.append(", praiseCount=");
		builder.append(praiseCount);
		builder.append(", comments=");
		builder.append(comments);
		builder.append("]");
		return builder.toString();
	}
}
