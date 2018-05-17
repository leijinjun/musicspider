package cn.person.musicspider.pojo;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import cn.person.musicspider.base.pojo.BasePO;

/**
 * 歌曲信息
 * @author 98611
 * @date 2017年9月10日 下午12:55:27
 */
@Table(name="cs_song")
public class Song extends BasePO implements Serializable{

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(generator="JDBC",strategy=GenerationType.AUTO)
	private Long id;
	
	@Column(name="song_id")
	private Long songId;
	
	@Column(name="song_name")
	private String songName;
	
	@Column(name="title")
	private String title;
	
	@Column(name="lyric")
	private String lyric;
	
	@Column(name="comment_count")
	private Integer commentCount;
	
	@Column(name="duration")
	private Integer duration;
	
	@Column(name="score")
	private Integer score;
	
	@Column(name="alias")
	private String alias;
	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	/**
	 * @return the songId
	 */
	public Long getSongId() {
		return songId;
	}
	/**
	 * @param songId the songId to set
	 */
	public void setSongId(Long songId) {
		this.songId = songId;
	}
	/**
	 * @return the songName
	 */
	public String getSongName() {
		return songName;
	}
	/**
	 * @param songName the songName to set
	 */
	public void setSongName(String songName) {
		this.songName = songName;
	}
	/**
	 * @return the title
	 */
	public String getTitle() {
		return title;
	}
	/**
	 * @param title the title to set
	 */
	public void setTitle(String title) {
		this.title = title;
	}
	/**
	 * @return the lyric
	 */
	public String getLyric() {
		return lyric;
	}
	/**
	 * @param lyric the lyric to set
	 */
	public void setLyric(String lyric) {
		this.lyric = lyric;
	}
	/**
	 * @return the commentCount
	 */
	public Integer getCommentCount() {
		return commentCount;
	}
	/**
	 * @param commentCount the commentCount to set
	 */
	public void setCommentCount(Integer commentCount) {
		this.commentCount = commentCount;
	}
	/**
	 * @return the duration
	 */
	public Integer getDuration() {
		return duration;
	}
	/**
	 * @param duration the duration to set
	 */
	public void setDuration(Integer duration) {
		this.duration = duration;
	}
	/**
	 * @return the score
	 */
	public Integer getScore() {
		return score;
	}
	/**
	 * @param score the score to set
	 */
	public void setScore(Integer score) {
		this.score = score;
	}
	/**
	 * @return the alias
	 */
	public String getAlias() {
		return alias;
	}
	/**
	 * @param alias the alias to set
	 */
	public void setAlias(String alias) {
		this.alias = alias;
	}

	@Override
	public String toString() {
		final StringBuffer sb = new StringBuffer("SongVo{");
		sb.append("id=").append(id);
		sb.append(", songId=").append(songId);
		sb.append(", songName='").append(songName).append('\'');
		sb.append(", title='").append(title).append('\'');
		sb.append(", lyric='").append(lyric).append('\'');
		sb.append(", commentCount=").append(commentCount);
		sb.append(", duration=").append(duration);
		sb.append(", score=").append(score);
		sb.append(", alias='").append(alias).append('\'');
		sb.append('}');
		return sb.toString();
	}
}
