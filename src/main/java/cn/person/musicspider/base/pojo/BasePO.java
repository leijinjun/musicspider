package cn.person.musicspider.base.pojo;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Transient;

public class BasePO {

	@Column(name="update_time")
	@Transient
	private Date updateTime;

	/**
	 * @return the updateTime
	 */
	public Date getUpdateTime() {
		return updateTime;
	}

	/**
	 * @param updateTime the updateTime to set
	 */
	public void setUpdateTime(Date updateTime) {
		this.updateTime = updateTime;
	}

}
