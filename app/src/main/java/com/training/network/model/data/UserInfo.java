package com.training.network.model.data;

import java.io.Serializable;

public class UserInfo implements Serializable {

	private String at;
	private String headImage;
	private String nickname;
	private String phoneNum;
	private String gender;
	private int age;
	private String myopia;
	/**
	 * 0:未绑定 1:已绑定
	 */
	private String qq;
	/**
	 * 0:未绑定 1:已绑定
	 */
	private String wechat;

	public String getAt() {
		return at;
	}

	public void setAt(String at) {
		this.at = at;
	}

	public String getHeadImage() {
		return headImage;
	}

	public void setHeadImage(String headImgUrl) {
		this.headImage = headImgUrl;
	}

	public String getNickname() {
		return nickname;
	}

	public void setNickname(String nickname) {
		this.nickname = nickname;
	}

	public String getPhoneNum() {
		return phoneNum;
	}

	public void setPhoneNum(String phoneNum) {
		this.phoneNum = phoneNum;
	}

	public String getGender() {
		return gender;
	}

	public void setGender(String gender) {
		this.gender = gender;
	}

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public String getMyopia() {
		return myopia;
	}

	public void setMyopia(String myopia) {
		this.myopia = myopia;
	}

	public String getId() {
		return at;
	}

	public void setId(String id) {
		this.at = id;
	}

	public String getQq() {
		return qq;
	}

	public void setQq(String qq) {
		this.qq = qq;
	}

	public String getWechat() {
		return wechat;
	}

	public void setWechat(String wechat) {
		this.wechat = wechat;
	}
}
