package com.st.lma.main;

import java.util.ArrayList;

import com.st.lma.models.BookCopy;
import com.st.lma.models.LibraryBranch;

public class LibrarianService{
	
	private static LibrarianService instance;
	
	private LibrarianService() {}
	
	public static LibrarianService getInstance() {
		if (instance == null) {
			instance = new LibrarianService();
		}
		return instance;
	}
	
	public void updateLibraryBranch(LibraryBranch newBranch) {
		LibraryBranchJDBC instance = LibraryBranchJDBC.getInstance();
		ArrayList<LibraryBranch> libraryBranchList = instance.getLibraryBranchList();
		libraryBranchList.add(newBranch);
		instance.setLibraryBranchList(libraryBranchList);
		instance.insertLibraryBranchList();
	}
	
	public void updateBookCopy(BookCopy newBookCopy) {
	    BookCopyJDBC instance = BookCopyJDBC.getInstance();
		ArrayList<BookCopy> bookCopyList = instance.getBookCopyList();
		bookCopyList.add(newBookCopy);
		instance.setBookCopyList(bookCopyList);
		instance.insertBookCopyList();
	}
}