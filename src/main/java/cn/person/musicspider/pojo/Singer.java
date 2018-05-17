package cn.person.musicspider.pojo;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import cn.person.musicspider.base.pojo.BasePO;

/**
 * 歌手信息
 * @author 98611
 * @date 2017年9月10日 下午12:55:36
 */
@Table(name="cs_singer")
public class Singer extends BasePO implements Serializable {

	private static final long serialVersionUID = 9216295777297544872L;

	@Id
	@GeneratedValue(generator="mysql",strategy=GenerationType.IDENTITY)
	private Long singerId;
	
	@Column(name="singer_name")
	private String singerName;
	
	@Column(name="title")
	private String title;
	
	@Column(name="is_hot")
	private Boolean isHot;
	/**
	 * 歌手封面
	 */
	@Column(name="photoURL")
	private String photoURL;
	
	/**
	 * 歌手标签
	 */
	@Column(name="tag")
	private String tag;
	
	@Column(name="descipt")
	private String descipt;
	
	@Column(name="alia")
	private String alia;
	/**
	 * @return the singerId
	 */
	public Long getSingerId() {
		return singerId;
	}

	/**
	 * @param singerId the singerId to set
	 */
	public void setSingerId(Long singerId) {
		this.singerId = singerId;
	}

	/**
	 * @return the singerName
	 */
	public String getSingerName() {
		return singerName;
	}

	/**
	 * @param singerName the singerName to set
	 */
	public void setSingerName(String singerName) {
		this.singerName = singerName;
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
	 * @return the isHot
	 */
	public Boolean getIsHot() {
		return isHot;
	}

	/**
	 * @param isHot the isHot to set
	 */
	public void setIsHot(Boolean isHot) {
		this.isHot = isHot;
	}

	/**
	 * @return the photoURL
	 */
	public String getPhotoURL() {
		return photoURL;
	}

	/**
	 * @param photoURL the photoURL to set
	 */
	public void setPhotoURL(String photoURL) {
		this.photoURL = photoURL;
	}

	/**
	 * @return the tag
	 */
	public String getTag() {
		return tag;
	}

	/**
	 * @param tag the tag to set
	 */
	public void setTag(String tag) {
		this.tag = tag;
	}


	/**
	 * @return the descipt
	 */
	public String getDescipt() {
		return descipt;
	}

	/**
	 * @param descipt the descipt to set
	 */
	public void setDescipt(String descipt) {
		this.descipt = descipt;
	}

	/**
	 * @return the alia
	 */
	public String getAlia() {
		return alia;
	}

	/**
	 * @param alia the alia to set
	 */
	public void setAlia(String alia) {
		this.alia = alia;
	}

	@Override
	public String toString() {
		final StringBuffer sb = new StringBuffer("Singer{");
		sb.append("singerId=").append(singerId);
		sb.append(", singerName='").append(singerName).append('\'');
		sb.append(", title='").append(title).append('\'');
		sb.append(", isHot=").append(isHot);
		sb.append(", photoURL='").append(photoURL).append('\'');
		sb.append(", tag='").append(tag).append('\'');
		sb.append(", descipt='").append(descipt).append('\'');
		sb.append(", alia='").append(alia).append('\'');
		sb.append('}');
		return sb.toString();
	}
}
