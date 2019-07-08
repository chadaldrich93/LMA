package com.st.lma.main;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Scanner;

import com.st.lma.models.Author;
import com.st.lma.models.Book;
import com.st.lma.models.BookCopy;
import com.st.lma.models.BookLoan;
import com.st.lma.models.Borrower;
import com.st.lma.models.LibraryBranch;
import com.st.lma.models.Publisher;

public class Menu{
	
	private final int INVALID_INT = -1;
	private final String INVALID_STRING = "InvalidString";
	
	private static Menu instance;
	
	private Menu() {}
	
	public static Menu getInstance() {
		if (instance == null) {
			instance = new Menu();
		}
		return instance;
	}
	
	public static void main(String[] args) {
		System.out.println("Welcome to the Smoothstack Library Management" +
	                       " System");
		AuthorJDBC aj = AuthorJDBC.getInstance();
		aj.loadAuthorList();
		BookJDBC bj = BookJDBC.getInstance();
		bj.loadBookList();
		BookCopyJDBC bcj = BookCopyJDBC.getInstance();
		bcj.loadBookCopyList();
		BookLoanJDBC blj = BookLoanJDBC.getInstance();
		blj.loadBookLoanList();
		BorrowerJDBC boj = BorrowerJDBC.getInstance();
		boj.loadBorrowerList();
		LibraryBranchJDBC lbj = LibraryBranchJDBC.getInstance();
		lbj.loadLibraryBranchList();
		PublisherJDBC pj = PublisherJDBC.getInstance();
		pj.loadPublisherList();
		Scanner input = new Scanner(System.in);
		Menu menu = getInstance();
		menu.userPrompt(input);
	}
	
	private void userPrompt(Scanner input) {
		Menu menu = getInstance();
		System.out.println("Which category of a user are you?");
		System.out.println("1) Librarian");
		System.out.println("2) Administrator");
		System.out.println("3) Borrower");
		System.out.println("4) Exit\n");
		switch (getIntFromInput(input)) {
		    case 1:
		    	menu.librarianPrompt1(input);
		    case 2:
		    	menu.adminPrompt1(input);
		    case 3:
		    	menu.borrowerPrompt1(input);
		    case 4:
		    	System.out.println("Smoothstack LibraryManagement System" + 
		                           " is closing down. Goodbye.");
		    	System.exit(0);
		    default:
		    	System.out.println("Invalid input, enter one of the listed" + 
		                           "integers\n");
		    	userPrompt(input);
		}
	}
	
	private void librarianPrompt1(Scanner input) {
		System.out.println("1) Enter branch you manage");
		System.out.println("2) Return to previous menu\n");
		switch(getIntFromInput(input)) {
		    case 1:
		    	librarianPrompt2(input);
		    case 2:
		    	userPrompt(input);
		    default:
		    	System.out.println("Invalid input, enter one of the listed" +
		                           " integers\n");
		    	librarianPrompt1(input);
		}
	}
	
	private void librarianPrompt2(Scanner input) {
		int maxValidInput = printBranches();
		int selection = getIntFromInput(input);
		if (selection <= 0 || selection > maxValidInput ) {
			System.out.println("Invalid input, enter one of the listed" +
		                       " integers\n");
			librarianPrompt2(input);
		}
		else if (selection == maxValidInput) {
			librarianPrompt1(input);
		}
		else {
			librarianPrompt3(input, selection-1);
		}
	}
	
	private void librarianPrompt3(Scanner input, int branchIndex) {
		System.out.println("1) Update the details of the library");
		System.out.println("2) Add copies of Book to the Branch");
		System.out.println("3) Return to previous menu\n");
		switch (getIntFromInput(input)) {
		    case 1:
		    	updateLibraryPrompt(input, branchIndex);
		    case 2:
		    	addBookCopyPrompt(input, branchIndex);
		    case 3:
		    	librarianPrompt2(input);
		    default:
		    	System.out.println("Invalid input, enter one of the listed" +
		                           " integers\n");
		    	librarianPrompt3(input, branchIndex);
		}
	}
	
	private void updateLibraryPrompt(Scanner input, int branchIndex) {
		LibrarianService instance = LibrarianService.getInstance();
		LibraryBranch branch = indexLibraryBranch(branchIndex);
		int branchId = branch.getBranchId();
		String inputString = "";
		String newBranchName = "";
		String newBranchAddress = "";
		System.out.println("You have chosen to update the branch with " +
	                       "name " + branch.getBranchName());
		System.out.println("Enter Q at any prompt to cancel");
		System.out.println("Please enter a new branch name or enter N/A" +
		                   " for no change:");
		switch (inputString = getStringFromInput(input)) {
		    case "N/A":
		    	newBranchName = branch.getBranchName();
		    	break;
		    case "Q":
		    	librarianPrompt3(input, branchIndex);
		    case INVALID_STRING:
		    	System.out.println("Error with string input, please try again");
		    	updateLibraryPrompt(input, branchIndex);
		    default:
		    	newBranchName = inputString; 
		}
		System.out.println("Please enter a new branch address or enter N/A" +
		                   " for no change:");
		switch (inputString = getStringFromInput(input)) {
	    case "N/A":
	    	newBranchAddress = branch.getBranchName();
	    	break;
	    case "Q":
	    	librarianPrompt3(input, branchIndex);
	    case INVALID_STRING:
	    	System.out.println("Error with string input, please try again");
	    	updateLibraryPrompt(input, branchIndex);
	    default:
	    	newBranchName = inputString; 
		}
		LibraryBranch newBranch = new LibraryBranch(branchId, newBranchName, 
				                                    newBranchAddress);
		instance.updateLibraryBranch(newBranch);
		librarianPrompt3(input, branchIndex);
	}
	
	private void addBookCopyPrompt(Scanner input, int branchIndex) {
		LibrarianService instance = LibrarianService.getInstance();
		int value;
		int index = 0;
		System.out.println("Which book would you like to add copies of?");
        int maxValidInput = printBooks();
		value = getIntFromInput(input);
		if (value <= 0 || value > maxValidInput) {
			System.out.println("Invalid input, enter one of the listed" +
		                       " integers\n");
			addBookCopyPrompt(input, branchIndex);
		}
		else if (value == maxValidInput) {
			librarianPrompt3(input, branchIndex);
		}
		else {
			index = value-1;
		}
		BookCopy copy = indexBookCopy(index);
		System.out.println("Existing number of copies: " + copy.getNoOfCopies());
		System.out.println("Enter new number of copies: ");
		while (true) {
		    value = getIntFromInput(input);
		    if (value < 0) {
			    System.out.println("Invalid input, new number of book copies" +
		                           " must not be negative");
			    continue;
		    }
		    break;
		}
		BookCopy newCopy = new BookCopy(copy.getBookId(), copy.getBranchId(), value);
		instance.updateBookCopy(newCopy);
		librarianPrompt3(input, branchIndex);
	}
	
	private void borrowerPrompt1(Scanner input) {
		int cardNo;
		boolean validated = false;
		System.out.println("Enter your card number");
		System.out.println("Or press 1) to return to main menu");
		switch (cardNo = getIntFromInput(input)) {
		case 1:
			userPrompt(input);
		case INVALID_INT:
			System.out.println("Invalid input, enter one of the listed" +
		                       " integers\n");
			borrowerPrompt1(input);
		default:
			validated = validateCardNumber(cardNo);
		}
		if (validated) {
			borrowerPrompt2(input, cardNo);
		}
		System.out.println("Borrower's card number was not found, please try again");
		borrowerPrompt1(input);
	}
	
	private void borrowerPrompt2(Scanner input, int cardNo) {
		System.out.println("1) Check out a book");
		System.out.println("2) Return a book");
		System.out.println("3) Return to main menu");
		switch (getIntFromInput(input)) {
		    case 1:
		    	borrowerCheckOut1(input, cardNo);
		    case 2:
		    	borrowerReturn1(input, cardNo);
		    case 3:
		    	userPrompt(input);
		    default:
		    	System.out.println("Invalid input, enter one of the listed" +
		                           " integers\n");
		    	borrowerPrompt2(input, cardNo);
		}
	}
	
	private void borrowerCheckOut1(Scanner input, int cardNo) {
		System.out.println("Pick the branch you want to check out from");
		int maxValidInput = printBranches();
		int value = getIntFromInput(input);
		if (value <= 0 || value > maxValidInput) {
			System.out.println("Invalid input, enter one of the listed" +
		                       " integers\n");
			borrowerCheckOut1(input, cardNo);
		}
		else if (value == maxValidInput) {
			borrowerPrompt2(input, cardNo);
		}
		else {
			int index = value-1;
			LibraryBranch branch = indexLibraryBranch(index);
			int branchId = branch.getBranchId();
			borrowerCheckOut2(input, cardNo, branchId);
		}
	}
	
	private void borrowerCheckOut2(Scanner input, int cardNo, int branchId) {
		BorrowerService instance = BorrowerService.getInstance();
		System.out.println("Pick the book that you want to check out");
		int maxValidInput = printBooks();
		int value = getIntFromInput(input);
		if (value <= 0 || value > maxValidInput) {
			System.out.println("Invalid input, enter one of the listed" +
		                       " integers\n");
			borrowerCheckOut2(input, cardNo, branchId);
		}
		else if (value == maxValidInput) {
			borrowerPrompt2(input, cardNo);
		}
		else {
			int index = value-1;
			Book book = indexBook(index);
			int bookId = book.getBookId();
			LocalDateTime dateTimeIn = LocalDateTime.now();
			LocalDate dateIn = dateTimeIn.toLocalDate();
			LocalDate dateOut = dateIn.plusDays(7);
			BookLoan newLoan = new BookLoan(bookId, branchId, cardNo, 
					                        dateIn, dateOut);
			instance.addBookLoan(newLoan);
			borrowerPrompt2(input, cardNo);
		}
	}
	
	private void borrowerReturn1(Scanner input, int cardNo) {
		System.out.println("Pick the branch you want to check out from");
		int maxValidInput = printBranches();
		int value = getIntFromInput(input);
		if (value <= 0 || value > maxValidInput) {
			System.out.println("Invalid input, enter one of the listed" +
		                       " integers\n");
			borrowerReturn1(input, cardNo);
		}
		else if (value == maxValidInput) {
			borrowerPrompt2(input, cardNo);
		}
		else {
			int index = value-1;
			LibraryBranch branch = indexLibraryBranch(index);
			int branchId = branch.getBranchId();
			borrowerReturn2(input, cardNo, branchId);
		}
	}
	
	private void borrowerReturn2(Scanner input, int cardNo, int branchId) {
		BorrowerService instance = BorrowerService.getInstance();
		System.out.println("Pick the book that you want to check out");
		int maxValidInput = printBooks();
		int value = getIntFromInput(input);
		if (value <= 0 || value > maxValidInput) {
			System.out.println("Invalid input, enter one of the listed" +
		                       " integers\n");
			borrowerReturn2(input, cardNo, branchId);
		}
		else if (value == maxValidInput) {
			borrowerPrompt2(input, cardNo);
		}
		else {
			int index = value-1;
			Book book = indexBook(index);
			int bookId = book.getBookId();
			instance.deleteBookLoan(bookId, branchId, cardNo);
			borrowerPrompt2(input, cardNo);
		}
	}
	
	private void adminPrompt1(Scanner input) {
		System.out.println("Welcome administrator, would you like to");
		System.out.println("1) Add an author");
		System.out.println("2) Update an author");
		System.out.println("3) Delete an author");
		System.out.println("4) Add a book");
		System.out.println("5) Update a book");
		System.out.println("6) Delete a book");
		System.out.println("7) Add a borrower");
		System.out.println("8) Update a borrower");
		System.out.println("9) Delete a borrower");
		System.out.println("10) Add a library branch");
		System.out.println("11) Update a library branch");
		System.out.println("12) Delete a library branch");
		System.out.println("13) Add a publisher");
		System.out.println("14) Update a publisher");
		System.out.println("15) Delete a publisher");
		System.out.println("16) Return to main menu");
		int value = getIntFromInput(input);
		switch (value) {
		    case 1: case 2: case 3:
		    	adminAuthorPrompt(input, value);
		    case 4: case 5: case 6:
		    	adminBookPrompt(input, value);
		    case 7: case 8: case 9:
		    	adminBorrowerPrompt(input, value);
		    case 10: case 11: case 12:
		    	adminLibraryBranchPrompt(input, value);
		    case 13: case 14: case 15:
		    	adminPublisherPrompt(input, value);
		    case 16:
		    	userPrompt(input);
		    default:
		    	System.out.println("Invalid input, enter one of the listed integers\n");
		    	adminPrompt1(input);
		}
	}
	
	private void adminAuthorPrompt(Scanner input, int value) {
		AdminService adminInstance = AdminService.getInstance();
		if (value == 1) {
			System.out.println("Enter an authorId");
			int authorId = getIntFromInput(input);
			if (authorId == INVALID_INT) {
				System.out.println("Invalid value entered for integer" +
			                       " please try again");
				adminAuthorPrompt(input, value);
			}
			System.out.println("Enter an author name");
			String authorName = getStringFromInput(input);
			if (authorName == "INVALID_STRING") {
				System.out.println("Invalid value entered for String" +
			                       " please try again");
				adminAuthorPrompt(input, value);
			}
			Author newAuthor = new Author(authorId, authorName);
			adminInstance.addAuthor(newAuthor);
			adminPrompt1(input);
		}
		else {
		    System.out.println("Choose an author");
		    int maxValidInput = printAuthors();
		    int selection = getIntFromInput(input);
		    if (selection <= 0 || selection > maxValidInput) {
		    	System.out.println("Invalid input, enter one of the listed integers\n");
		    	adminAuthorPrompt(input, value);
		    }
		    else if (selection == maxValidInput) {
		    	adminPrompt1(input);
		    }
		    else {
		    	int index = selection-1;
		    	Author target = indexAuthor(index);
		    	if (value == 3) {
		    		adminInstance.deleteAuthor(target.getAuthorId());
		    		adminPrompt1(input);
		    	}
		    	//enter values for update
		    	int authorId = target.getAuthorId();
				System.out.println("Enter an author name");
				String authorName = getStringFromInput(input);
				if (authorName == "INVALID_STRING") {
					System.out.println("Invalid value entered for String" +
				                       " please try again");
					adminAuthorPrompt(input, value);
				}
				Author newAuthor = new Author(authorId, authorName);
				adminInstance.addAuthor(newAuthor);
				adminPrompt1(input);
		    }
		}
	}
	
	private void adminBookPrompt(Scanner input, int value) {
		AdminService adminInstance = AdminService.getInstance();
		if (value == 4) {
			System.out.println("Enter a bookId");
			int bookId = getIntFromInput(input);
			if (bookId == INVALID_INT) {
				System.out.println("Invalid value entered for integer" +
			                       " please try again");
				adminBookPrompt(input, value);
			}
			System.out.println("Enter a book title");
			String bookTitle = getStringFromInput(input);
			if (bookTitle == "INVALID_STRING") {
				System.out.println("Invalid value entered for String" +
			                       " please try again");
				adminBookPrompt(input, value);
			}
			System.out.println("Enter an authorId");
			int authorId = getIntFromInput(input);
			if (authorId == INVALID_INT) {
				System.out.println("Invalid value entered for integer" +
			                       " please try again");
				adminBookPrompt(input, value);
			}
			System.out.println("Enter a publisherId");
			int publisherId = getIntFromInput(input);
			if (publisherId == INVALID_INT) {
				System.out.println("Invalid value entered for integer" +
			                       " please try again");
				adminBookPrompt(input, value);
			}
			Book newBook = new Book(bookId, bookTitle, authorId, publisherId);
			adminInstance.addBook(newBook);
			adminPrompt1(input);
		}
		else {
		    System.out.println("Choose a book");
		    int maxValidInput = printBooks();
		    int selection = getIntFromInput(input);
		    if (selection <= 0 || selection > maxValidInput) {
		    	System.out.println("Invalid input, enter one of the listed integers\n");
		    	adminBookPrompt(input, value);
		    }
		    else if (selection == maxValidInput) {
		    	adminPrompt1(input);
		    }
		    else {
		    	int index = selection-1;
		    	Book target = indexBook(index);
		    	if (value == 6) {
		    		adminInstance.deleteBook(target.getBookId());
		    		adminPrompt1(input);
		    	}
		    	//enter values for update
		    	int bookId = target.getBookId();
		    	int authId = target.getAuthId();
		    	int pubId = target.getPubId();
				System.out.println("Enter a book title");
				String bookTitle = getStringFromInput(input);
				if (bookTitle == "INVALID_STRING") {
					System.out.println("Invalid value entered for String" +
				                       " please try again");
					adminBookPrompt(input, value);
				}
				Book newBook = new Book(bookId, bookTitle, authId, pubId);
				adminInstance.addBook(newBook);
				adminPrompt1(input);
		    }
		}
	}
	
	private void adminBorrowerPrompt(Scanner input, int value) {
		AdminService adminInstance = AdminService.getInstance();
		if (value == 7) {
			System.out.println("Enter a cardNo");
			int cardNo = getIntFromInput(input);
			if (cardNo == INVALID_INT) {
				System.out.println("Invalid value entered for integer" +
			                       " please try again");
				adminBorrowerPrompt(input, value);
			}
			System.out.println("Enter a borrower name");
			String name = getStringFromInput(input);
			if (name == INVALID_STRING) {
				System.out.println("Invalid value entered for string" +
			                       " please try again");
				adminBorrowerPrompt(input, value);
			}
			System.out.println("Enter a borrower address");
			String address = getStringFromInput(input);
			if (address == INVALID_STRING) {
				System.out.println("Invalid value entered for string" +
			                       " please try again");
				adminBorrowerPrompt(input, value);
			}
			System.out.println("Enter a borrower phone number");
			String phoneNumber = getStringFromInput(input);
			if (phoneNumber == INVALID_STRING) {
				System.out.println("Invalid value entered for string" +
			                       " please try again");
				adminBorrowerPrompt(input, value);
			}
			Borrower newBorrower = new Borrower(cardNo, name, address, phoneNumber);
			adminInstance.addBorrower(newBorrower);
			adminPrompt1(input);
		}
		else {
		    System.out.println("Choose a borrower");
		    int maxValidInput = printBorrowers();
		    int selection = getIntFromInput(input);
		    if (selection <= 0 || selection > maxValidInput) {
		    	System.out.println("Invalid input, enter one of the listed integers\n");
		    	adminBorrowerPrompt(input, value);
		    }
		    else if (selection == maxValidInput) {
		    	adminPrompt1(input);
		    }
		    else {
		    	int index = selection-1;
		    	Borrower target = indexBorrower(index);
		    	if (value == 9) {
		    		adminInstance.deleteBorrower(target.getCardNo());
		    		adminPrompt1(input);
		    	}
		    	//enter values for update
		    	int cardNo = target.getCardNo();
				System.out.println("Enter a borrower name");
				String borrowerName = getStringFromInput(input);
				if (borrowerName == "INVALID_STRING") {
					System.out.println("Invalid value entered for String" +
				                       " please try again");
					adminBorrowerPrompt(input, value);
				}
				System.out.println("Enter a borrower address");
				String borrowerAddress = getStringFromInput(input);
				if (borrowerAddress == "INVALID_STRING") {
					System.out.println("Invalid value entered for String" +
				                       " please try again");
					adminBorrowerPrompt(input, value);
				}
				System.out.println("Enter a borrower phone number");
				String borrowerPhone = getStringFromInput(input);
				if (borrowerPhone == "INVALID_STRING") {
					System.out.println("Invalid value entered for String" +
				                       " please try again");
					adminBorrowerPrompt(input, value);
				}
				Borrower newBorrower = new Borrower(cardNo, borrowerName, borrowerAddress, borrowerPhone);
				adminInstance.addBorrower(newBorrower);
				adminPrompt1(input);
		    }
		}
	}
	
	private void adminLibraryBranchPrompt(Scanner input, int value) {
		AdminService adminInstance = AdminService.getInstance();
		if (value == 10) {
			System.out.println("Enter a branchId");
			int branchId = getIntFromInput(input);
			if (branchId == INVALID_INT) {
				System.out.println("Invalid value entered for integer" +
			                       " please try again");
				adminLibraryBranchPrompt(input, value);
			}
			System.out.println("Enter a branch name");
			String branchName = getStringFromInput(input);
			if (branchName == "INVALID_STRING") {
				System.out.println("Invalid value entered for String" +
			                       " please try again");
				adminLibraryBranchPrompt(input, value);
			}
			System.out.println("Enter a branch address");
			String branchAddress = getStringFromInput(input);
			if (branchAddress == "INVALID_STRING") {
				System.out.println("Invalid value entered for String" +
			                       " please try again");
				adminLibraryBranchPrompt(input, value);
			}
			LibraryBranch newBranch = new LibraryBranch(branchId, branchName, branchAddress);
			adminInstance.addLibraryBranch(newBranch);
			adminPrompt1(input);
		}
		else {
		    System.out.println("Choose a library branch");
		    int maxValidInput = printBranches();
		    int selection = getIntFromInput(input);
		    if (selection <= 0 || selection > maxValidInput) {
		    	System.out.println("Invalid input, enter one of the listed integers\n");
		    	adminLibraryBranchPrompt(input, value);
		    }
		    else if (selection == maxValidInput) {
		    	adminPrompt1(input);
		    }
		    else {
		    	int index = selection-1;
		    	LibraryBranch target = indexLibraryBranch(index);
		    	if (value == 12) {
		    		adminInstance.deleteLibraryBranch(target.getBranchId());
		    		adminPrompt1(input);
		    	}
		    	//enter values for update
		    	int branchId = target.getBranchId();
				System.out.println("Enter a branch name");
				String branchName = getStringFromInput(input);
				if (branchName == "INVALID_STRING") {
					System.out.println("Invalid value entered for String" +
				                       " please try again");
					adminLibraryBranchPrompt(input, value);
				}
				System.out.println("Enter a branch name");
				String branchAddress = getStringFromInput(input);
				if (branchAddress == "INVALID_STRING") {
					System.out.println("Invalid value entered for String" +
				                       " please try again");
					adminLibraryBranchPrompt(input, value);
				}
				LibraryBranch newBranch = new LibraryBranch(branchId, branchName, branchAddress);
				adminInstance.addLibraryBranch(newBranch);
				adminPrompt1(input);
		    }
		}
	}
	
	private void adminPublisherPrompt(Scanner input, int value) {
		AdminService adminInstance = AdminService.getInstance();
		if (value == 13) {
			System.out.println("Enter a publisher id");
			int publisherId = getIntFromInput(input);
			if (publisherId == INVALID_INT) {
				System.out.println("Invalid value entered for integer" +
			                       " please try again");
				adminPublisherPrompt(input, value);
			}
			System.out.println("Enter a publisher name");
			String name = getStringFromInput(input);
			if (name == INVALID_STRING) {
				System.out.println("Invalid value entered for string" +
			                       " please try again");
				adminPublisherPrompt(input, value);
			}
			System.out.println("Enter a publisher address");
			String address = getStringFromInput(input);
			if (address == INVALID_STRING) {
				System.out.println("Invalid value entered for string" +
			                       " please try again");
				adminPublisherPrompt(input, value);
			}
			System.out.println("Enter a publisher phone number");
			String phoneNumber = getStringFromInput(input);
			if (phoneNumber == INVALID_STRING) {
				System.out.println("Invalid value entered for string" +
			                       " please try again");
				adminPublisherPrompt(input, value);
			}
			Publisher newPublisher = new Publisher(publisherId, name, address, phoneNumber);
			adminInstance.addPublisher(newPublisher);
			adminPrompt1(input);
		}
		else {
		    System.out.println("Choose a publisher");
		    int maxValidInput = printPublishers();
		    int selection = getIntFromInput(input);
		    if (selection <= 0 || selection > maxValidInput) {
		    	System.out.println("Invalid input, enter one of the listed integers\n");
		    	adminPublisherPrompt(input, value);
		    }
		    else if (selection == maxValidInput) {
		    	adminPrompt1(input);
		    }
		    else {
		    	int index = selection-1;
		    	Publisher target = indexPublisher(index);
		    	if (value == 15) {
		    		adminInstance.deletePublisher(target.getPublisherId());
		    		adminPrompt1(input);
		    	}
		    	//enter values for update
		    	int publisherId = target.getPublisherId();
				System.out.println("Enter a publisher name");
				String publisherName = getStringFromInput(input);
				if (publisherName == "INVALID_STRING") {
					System.out.println("Invalid value entered for String" +
				                       " please try again");
					adminPublisherPrompt(input, value);
				}
				System.out.println("Enter a publisher address");
				String publisherAddress = getStringFromInput(input);
				if (publisherAddress == "INVALID_STRING") {
					System.out.println("Invalid value entered for String" +
				                       " please try again");
					adminPublisherPrompt(input, value);
				}
				System.out.println("Enter a publisher phone number");
				String publisherPhone = getStringFromInput(input);
				if (publisherPhone == "INVALID_STRING") {
					System.out.println("Invalid value entered for String" +
				                       " please try again");
					adminPublisherPrompt(input, value);
				}
				Publisher newPublisher = new Publisher(publisherId, publisherName,
						                               publisherAddress, publisherPhone);
				adminInstance.addPublisher(newPublisher);
				adminPrompt1(input);
		    }
		}
	}
	
	//starting here are helper methods
	//that are outside the directory of
	//the user interface
	private int printAuthors() {
		int index = 1;
		AuthorJDBC instance = AuthorJDBC.getInstance();
		ArrayList<Author> authors = instance.getAuthorList();
		for (Author a: authors) {
			String authorName = a.getAuthorName();
			System.out.println(index + ") " + authorName);
			index++;
		}
		System.out.println(index + ") Quit to previous menu");
		return index;
	}
	
	private int printBooks() {
		int index = 1;
		BookJDBC instance = BookJDBC.getInstance();
		ArrayList<Book> books = instance.getBookList();
		String bookName = "";
		//String authorName = "";
		for (Book b: books) {
			bookName = b.getBookTitle();
			System.out.println(index + ") " + bookName);  // + " by " + branchAddress);
			index++;
		}
		System.out.println(index + ") Quit to previous menu");
		return index;
	}
	
	/*private int printBooksBranchHas(int bookId, int branchId) {
		int index = 1;
		BookJDBC instance = BookJDBC.getInstance();
		ArrayList<Book> books = instance.getBookList();
		String bookName = "";
		//String authorName = "";
		for (Book b: books) {
			if (b.getBook)
			bookName = b.getBookTitle();
			System.out.println(index + ") " + bookName);  // + " by " + branchAddress);
			index++;
		}
		System.out.println(index + ") Quit to previous menu");
		return index;
	}*/
	
	private int printBorrowers() {
		int index = 1;
		BorrowerJDBC instance = BorrowerJDBC.getInstance();
		ArrayList<Borrower> borrowers = instance.getBorrowerList();
		for (Borrower b: borrowers) {
			String borrowerName = b.getName();
			System.out.println(index + ") " + borrowerName);
			index++;
		}
		System.out.println(index + ") Quit to previous menu");
		return index;
	}
	
	private int printBranches() {
		int index = 1;
		LibraryBranchJDBC instance = LibraryBranchJDBC.getInstance();
		ArrayList<LibraryBranch> branches = instance.getLibraryBranchList();
		String branchName = "";
		String branchAddress = "";
		for (LibraryBranch b: branches) {
			branchName = b.getBranchName();
			branchAddress = b.getBranchAddress();
			System.out.println(index + ") " + branchName + ", " + branchAddress);
			index++;
		}
		System.out.println(index + ") Quit to previous menu");
		return index;
	}
	
	private int printPublishers() {
		int index = 1;
		PublisherJDBC instance = PublisherJDBC.getInstance();
		ArrayList<Publisher> publishers = instance.getPublisherList();
		for (Publisher p: publishers) {
			String publisherName = p.getPublisherName();
			System.out.println(index + ") " + publisherName);
			index++;
		}
		System.out.println(index + ") Quit to previous menu");
		return index;
	}
	
	private Author indexAuthor(int authorIndex) {
		AuthorJDBC instance = AuthorJDBC.getInstance();
		ArrayList<Author> authors = instance.getAuthorList();
		return authors.get(authorIndex);
	}
	
	private Book indexBook(int bookIndex) {
		BookJDBC instance = BookJDBC.getInstance();
		ArrayList<Book> books = instance.getBookList();
		return books.get(bookIndex);
	}
	
	private BookCopy indexBookCopy(int bookCopyIndex) {
		BookCopyJDBC instance = BookCopyJDBC.getInstance();
		ArrayList<BookCopy> copies = instance.getBookCopyList();
		return copies.get(bookCopyIndex);
	}
	
	private BookLoan indexBookLoan(int bookLoanIndex) {
		BookLoanJDBC instance = BookLoanJDBC.getInstance();
		ArrayList<BookLoan> loans = instance.getBookLoanList();
		return loans.get(bookLoanIndex);
	}
	
	private Borrower indexBorrower(int borrowerIndex) {
		BorrowerJDBC instance = BorrowerJDBC.getInstance();
		ArrayList<Borrower> borrowers = instance.getBorrowerList();
		return borrowers.get(borrowerIndex);
	}
	
	private LibraryBranch indexLibraryBranch(int branchIndex) {
		LibraryBranchJDBC instance = LibraryBranchJDBC.getInstance();
		ArrayList<LibraryBranch> branches = instance.getLibraryBranchList();
		return branches.get(branchIndex);
	}
	
	private Publisher indexPublisher(int publisherIndex) {
		PublisherJDBC instance = PublisherJDBC.getInstance();
		ArrayList<Publisher> publishers = instance.getPublisherList();
		return publishers.get(publisherIndex);
	}
	
	private boolean validateCardNumber(int cardNo) {
		boolean validated = false;
		BorrowerJDBC instance = BorrowerJDBC.getInstance();
		ArrayList<Borrower> borrowerList = instance.getBorrowerList();
		for (Borrower b: borrowerList) {
			if (b.getCardNo() == cardNo) {
				validated = true;
			}
		}
		return validated;
	}
	
	private boolean validateBranchHasCopies(int bookId, int branchId) {
		boolean validated = false;
		BookCopyJDBC instance = BookCopyJDBC.getInstance();
		ArrayList<BookCopy> bookCopyList = instance.getBookCopyList();
		for (BookCopy b: bookCopyList) {
			if (b.getBookId() == bookId && b.getBranchId() == branchId) {
				validated = true;
			}
		}
		return validated;
	}
	
	private int getIntFromInput(Scanner input) {
		int value;
		try {
			value = input.nextInt();
		}
		catch (Exception e) {
			value = INVALID_INT;
		}
		return value;
	}
	
	private String getStringFromInput(Scanner input) {
		String value;
		try {
			value = input.next();
		}
		catch (Exception e) {
			value = INVALID_STRING;
		}
		return value;
	}
}