package com.st.lma.models;

public class Book{
	
	private int bookId;
	private String title;
	private int authId;
	private int pubId;
	
	public Book(int bookId, String title, int authId, int pubId) {
		this.bookId = bookId;
		this.title = title;
		this.authId = authId;
		this.pubId = pubId;
	}
	
	public void setBookId(int bookId) {
		this.bookId = bookId;
	}
	
	public void setBookTitle(String title) {
		this.title = title;
	}
	
	public void setAuthId(int authId) {
		this.authId = authId;
	}
	
	public void setPubId(int pubId) {
		this.pubId = pubId;
	}
	
	public int getBookId() {
		return this.bookId;
	}
	
	public String getBookTitle() {
		return this.title;
	}
	
	public int getAuthId() {
		return this.authId;
	}
	
	public int getPubId() {
		return this.pubId;
	}
}