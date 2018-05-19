package cn.person.musicspider.web.vo;

import java.util.List;

import cn.person.musicspider.pojo.Comment;

public class CommentsVo extends Comment{
	
	private SongVo song;
	private List<Comment> comments;
	public SongVo getSong() {
		return song;
	}
	public void setSong(SongVo song) {
		this.song = song;
	}

	public List<Comment> getComments() {
		return comments;
	}

	public void setComments(List<Comment> comments) {
		this.comments = comments;
	}
}
