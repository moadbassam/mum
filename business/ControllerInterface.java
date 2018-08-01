package business;

import java.util.List;

import business.CheckoutRecordView.CheckoutRecordEntryView;
import javafx.collections.ObservableList;

public interface ControllerInterface {

	public ObservableList<Book> readBooksMap();

	public void addNewMember(String memberId, String firstName, String lastName, String telNumber, Address addr)
			throws LibrarySystemException;

	public LibraryMember search(String memberId);

	public void updateMemberInfo(String memberId, String firstName, String lastName, String telNumber, Address addr)
			throws LibrarySystemException;

	public ObservableList<CheckoutRecordEntryView> checkoutBook(String memberId, String isbn) throws LibrarySystemException;

	public boolean addBook(String isbn, String title, int maxCheckoutLength, List<Author> authors);

	public boolean addBookCopy(String isbn) throws LibrarySystemException;

	public void printCheckoutRecord(String memberId) throws LibrarySystemException;

	public boolean isAvailable(BookCopy copy);

	public Book searchBook(String isbn);

	public boolean deleteBook(String isbn) throws LibrarySystemException;

	ObservableList<CheckoutRecordEntryView> readCheckoutRecordByMemberId(String memberId) throws LibrarySystemException;

	ObservableList<CheckoutRecordEntryView> readCheckoutRecordByISBN(String ISBNId) throws LibrarySystemException;

}
