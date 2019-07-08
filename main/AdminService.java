package com.st.lma.main;

import java.util.ArrayList;

import com.st.lma.models.Author;
import com.st.lma.models.Book;
import com.st.lma.models.Borrower;
import com.st.lma.models.LibraryBranch;
import com.st.lma.models.Publisher;

public class AdminService{
	
	private static AdminService instance;
	
	private AdminService() {}
	
	public static AdminService getInstance() {
		if (instance == null) {
			instance = new AdminService();
		}
		return instance;
	}
	
	public void addAuthor(Author newAuthor) {
		AuthorJDBC instance = AuthorJDBC.getInstance();
		ArrayList<Author> authorList = instance.getAuthorList();
		authorList.add(newAuthor);
		instance.setAuthorList(authorList);
		instance.insertAuthorList();
	}
	
	public void deleteAuthor(int authorId) {
		AuthorJDBC instance = AuthorJDBC.getInstance();
		ArrayList<Author> authorList = instance.getAuthorList();
		authorList.remove(authorId);
		instance.setAuthorList(authorList);
		instance.insertAuthorList();
	}
	
	public void addBook(Book newBook) {
	    BookJDBC instance = BookJDBC.getInstance();
		ArrayList<Book> bookList = instance.getBookList();
		bookList.add(newBook);
		instance.setBookList(bookList);
		instance.insertBookList();
	}
	
	public void deleteBook(int bookId) {
	    BookJDBC instance = BookJDBC.getInstance();
		ArrayList<Book> bookList = instance.getBookList();
		bookList.remove(bookId);
		instance.setBookList(bookList);
		instance.insertBookList();
	}
	
	public void addBorrower(Borrower newBorrower) {
	    BorrowerJDBC instance = BorrowerJDBC.getInstance();
		ArrayList<Borrower> borrowerList = instance.getBorrowerList();
		borrowerList.add(newBorrower);
		instance.setBookList(borrowerList);
		instance.insertBorrowerList();
	}
	
	public void deleteBorrower(int cardNo) {
	    BorrowerJDBC instance = BorrowerJDBC.getInstance();
		ArrayList<Borrower> borrowerList = instance.getBorrowerList();
		borrowerList.remove(cardNo);
		instance.setBookList(borrowerList);
		instance.insertBorrowerList();
	}
	
	public void addLibraryBranch(LibraryBranch newBranch) {
		LibraryBranchJDBC instance = LibraryBranchJDBC.getInstance();
		ArrayList<LibraryBranch> libraryBranchList = instance.getLibraryBranchList();
		libraryBranchList.add(newBranch);
		instance.setLibraryBranchList(libraryBranchList);
		instance.insertLibraryBranchList();
	}
	
	public void deleteLibraryBranch(int branchId) {
		LibraryBranchJDBC instance = LibraryBranchJDBC.getInstance();
		ArrayList<LibraryBranch> libraryBranchList = instance.getLibraryBranchList();
		libraryBranchList.remove(branchId);
		instance.setLibraryBranchList(libraryBranchList);
		instance.insertLibraryBranchList();
	}
	
	public void addPublisher(Publisher newPublisher) {
		PublisherJDBC instance = PublisherJDBC.getInstance();
		ArrayList<Publisher> publisherList = instance.getPublisherList();
		publisherList.add(newPublisher);
		instance.setPublisherList(publisherList);
		instance.insertPublisherList();
	}
	
	public void deletePublisher(int publisherId) {
		PublisherJDBC instance = PublisherJDBC.getInstance();
		ArrayList<Publisher> publisherList = instance.getPublisherList();
		publisherList.remove(publisherId);
		instance.setPublisherList(publisherList);
		instance.insertPublisherList();
	}
}