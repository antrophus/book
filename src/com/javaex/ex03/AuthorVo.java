package com.javaex.ex03;

public class AuthorVo {

	//필드
	private int authrId;
	private String authorName;
	private String authorDesc;

	//생성자
	public AuthorVo() {
	}

	//메소드-gs
	public AuthorVo(int authrId, String authorName, String authorDesc) {
		this.authrId = authrId;
		this.authorName = authorName;
		this.authorDesc = authorDesc;
	}

	public int getAuthrId() {
		return authrId;
	}

	public void setAuthrId(int authrId) {
		this.authrId = authrId;
	}

	public String getAuthorName() {
		return authorName;
	}

	public void setAuthorName(String authorName) {
		this.authorName = authorName;
	}

	public String getAuthorDesc() {
		return authorDesc;
	}

	public void setAuthorDesc(String authorDesc) {
		this.authorDesc = authorDesc;
	}

	//메소드-일반
	@Override
	public String toString() {
		return "AuthorVo [authrId=" + authrId + ", authorName=" + authorName + ", authorDesc=" + authorDesc + "]";
	}
	
}