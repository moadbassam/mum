package dataaccess;

import java.util.HashMap;

import business.Book;
import business.BookCopy;
import business.CheckoutRecord;
import business.LibraryMember;

public interface DataAccess {
	// public LibraryMember searchMember(String memberId);
	public Book searchBook(String isbn);

	public LibraryMember searchMember(String id);

	/////// save methods
	// public void saveNewMember(LibraryMember member);
	// public void updateMember(LibraryMember member);

	// save new book
	public void saveNewBook(Book book);

	public HashMap<String, CheckoutRecord> readCheckoutRecordMap();

	public HashMap<String, LibraryMember> readLibraryMemberMap();

	////// read methods
	public HashMap<String, Book> readBooksMap();

	public HashMap<String, User> readUserMap();
	// public HashMap<String, LibraryMember> readMemberMap();

	public boolean deleteBook(String isbn);

	void updateBookCopy(BookCopy copy);

	void saveNewRecord(CheckoutRecord record);

	public void saveNewMember(LibraryMember b);

	boolean deleteMember(String memberId);
}
