package com.st.lma.models;

public class BookCopy{
	
	private int bookId;
	private int branchId;
	private int noOfCopies;
	
	public BookCopy(int bookId, int branchId, int noOfCopies) {
		this.bookId = bookId;
		this.branchId = branchId;
		this.noOfCopies = noOfCopies;
	}
	
	public void setBookId(int bookId) {
		this.bookId = bookId;
	}
	
	public void setBranchId(int branchId) {
		this.branchId = branchId;
	}
	
	public void setNoOfCopies(int noOfCopies) {
		this.noOfCopies = noOfCopies;
	}
	
	public int getBookId() {
		return this.bookId;
	}
	
	public int getBranchId() {
		return this.branchId;
	}
	
	public int getNoOfCopies() {
		return this.noOfCopies;
	}
}