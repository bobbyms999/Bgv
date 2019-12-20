package com.accredilink.bgv.pojo;

import java.io.Serializable;

public class Image implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private int bgDateBaseId;
	private String imageData;
	private String bgvDescription;

	public int getBgDateBaseId() {
		return bgDateBaseId;
	}

	public String getBgvDescription() {
		return bgvDescription;
	}

	public void setBgvDescription(String bgvDescription) {
		this.bgvDescription = bgvDescription;
	}

	public void setBgDateBaseId(int bgDateBaseId) {
		this.bgDateBaseId = bgDateBaseId;
	}

	public String getImageData() {
		return imageData;
	}

	public void setImageData(String imageData) {
		this.imageData = imageData;
	}

}
