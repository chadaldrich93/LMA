package com.st.lma.models;

import java.time.LocalDate;

public class BookLoan{
	
	private int bookId;
	private int branchId;
	private int cardNo;
	private LocalDate dateOut;
	private LocalDate dueDate;
	
	public BookLoan(int bookId, int branchId, int cardNo, 
			         LocalDate dateOut, LocalDate dueDate) {
		this.bookId = bookId;
		this.branchId = branchId;
		this.cardNo = cardNo;
		this.dateOut = dateOut;
		this.dueDate = dueDate;
	}
	
	public void setBookId(int bookId) {
		this.bookId = bookId;
	}
	
	public void setBranchId(int branchId) {
		this.branchId = branchId;
	}
	
	public void setCardNo(int cardNo) {
		this.cardNo = cardNo;
	}
	
	public void setDateOut(LocalDate dateOut) {
		this.dateOut = dateOut;
	}
	
	public void setDueDate(LocalDate dueDate) {
		this.dueDate = dueDate;
	}
	
	public int getBookId() {
		return this.bookId;
	}
	
	public int getBranchId() {
		return this.branchId;
	}
	
	public int getCardNo() {
		return this.cardNo;
	}
	
	public LocalDate getDateOut() {
		return this.dateOut;
	}
	
	public LocalDate getDueDate() {
		return this.dueDate;
	}
	
}