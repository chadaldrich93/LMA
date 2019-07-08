package com.st.lma.models;

public class Author{
	
	private int authorId;
	private String authorName;
	
	public Author(int authorId, String authorName){
		this.authorId = authorId;
		this.authorName = authorName;
	}
	
	public void setAuthorId(int authorId) {
		this.authorId = authorId;
	}
	
	public void setAuthorName(String authorName) {
		this.authorName = authorName;
	}
	
	public int getAuthorId() {
		return this.authorId;
	}
	
	public String getAuthorName() {
		return this.authorName;
	}
	
}