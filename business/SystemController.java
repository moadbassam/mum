package business;

import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import dataaccess.Auth;
import dataaccess.DataAccess;
import dataaccess.DataAccessFacade;
import dataaccess.User;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class SystemController implements ControllerInterface {

	public static Auth currentAuth = null;

	private static SystemController Instance = null;

	private SystemController() {

	}

	public static SystemController getInstance() {
		if (Instance == null)
			Instance = new SystemController();
		return Instance;

	}

	public void login(String id, String password) throws LoginException {
		DataAccess da = new DataAccessFacade();
		HashMap<String, User> map = da.readUserMap();
		if (!map.containsKey(id)) {
			throw new LoginException("ID " + id + " not found");
		}
		String passwordFound = map.get(id).getPassword();
		if (!passwordFound.equals(password)) {
			throw new LoginException("Passord does not match password on record");
		}
		currentAuth = map.get(id).getAuthorization();

	}
	/**
	 * This method checks if memberId already exists -- if so, it cannot be added as
	 * a new member, and an exception is thrown. If new, creates a new LibraryMember
	 * based on input data and uses DataAccess to store it.
	 * 
	 */
	// public void addNewMember(String memberId, String firstName, String
	// lastName,
	// String telNumber, Address addr) throws LibrarySystemException {

	/**
	 * Reads data store for a library member with specified id. Ids begin at 1001...
	 * Returns a LibraryMember if found, null otherwise
	 * 
	 */
	// public LibraryMember search(String memberId) {

	/**
	 * Same as creating a new member (because of how data is stored)
	 */
	// public void updateMemberInfo(String memberId, String firstName, String
	// lastName,

	/**
	 * Looks up Book by isbn from data store. If not found, an exception is thrown.
	 * If no copies are available for checkout, an exception is thrown. If found and
	 * a copy is available, member's checkout record is updated and copy of this
	 * publication is set to "not available"
	 */
	// public void checkoutBook(String memberId, String isbn) throws
	// LibrarySystemException {

	/**
	 * Looks up book by isbn to see if it exists, throw exceptioni. Else add the
	 * book to storage
	 * 
	 * @throws Exception
	 */

	public boolean addBook(String isbn, String title, int maxCheckoutLength, String authors, int noOfCopies)
			throws Exception {

		DataAccess da = new DataAccessFacade();
		List<Author> authorList = new ArrayList<Author>();

		List<String> commaSeparatedList = Arrays.asList(authors.split(","));
		for (String a : commaSeparatedList) {
			authorList.add(new Author(a, "", "", null, ""));
		}

		Book book = new Book(isbn, title, 1, maxCheckoutLength, authorList);
		int count = 0;
		while (count != noOfCopies) {
			book.addCopy();
			count++;
		}
		try {
			da.saveNewBook(book);
			return true;
		} catch (Exception e) {
			throw new Exception("Cannot save this book now , please try again later!");
		}

	}

	public boolean addBookCopy(String isbn) throws LibrarySystemException {
		DataAccess da = new DataAccessFacade();
		Book book = searchBook(isbn);
		if (book == null)
			throw new LibrarySystemException("No book with isbn " + isbn + " is in the library collection!");
		else
			book.addCopy();
		da.saveNewBook(book);
		return true;
	}

	public boolean deleteBook(String isbn) throws LibrarySystemException {
		DataAccess da = new DataAccessFacade();
		Book book = searchBook(isbn);
		if (book == null)
			throw new LibrarySystemException("No book with isbn " + isbn + " is in the library collection!");
		else
			da.deleteBook(isbn);

		return true;
	}

	public static void main(String[] args) throws LibrarySystemException {

	}

	@Override
	/*
	 * public ObservableList<CheckoutEntry> checkoutBook(String memberId, String
	 * isbn) throws LibrarySystemException {
	 */
	public ObservableList<CheckoutRecordView.CheckoutRecordEntryView> checkoutBook(String memberId, String isbn)
			throws LibrarySystemException {
		DataAccess da = new DataAccessFacade();
		HashMap<String, LibraryMember> mapLibraryMember = da.readLibraryMemberMap();
		HashMap<String, Book> mapBook = da.readBooksMap();
		Book book = mapBook.get(isbn);

		if (!mapLibraryMember.containsKey(memberId) && !mapBook.containsKey(isbn)) {
			throw new LibrarySystemException("ID " + memberId + "and ISBN" + isbn + " not found");
		} else if (!mapBook.containsKey(isbn)) {
			throw new LibrarySystemException("ISBN " + isbn + " not found");
		} else if (!mapLibraryMember.containsKey(memberId)) {
			throw new LibrarySystemException("ID " + memberId + " not found");
		} else if (!book.getisCopyAvailable()) {
			throw new LibrarySystemException("No copies of ISBN " + isbn + " are available");
		}

		BookCopy copy = book.getAvailableBookCopy();
		CheckoutEntry chkEntry = new CheckoutEntry(copy, mapLibraryMember.get(memberId), book.getMaxCheckoutLength());
		copy.setAvailable(false);
		da.updateBookCopy(copy);

		CheckoutRecord chkRecord;
		HashMap<String, CheckoutRecord> recordMap = da.readCheckoutRecordMap();
		if (recordMap.isEmpty())
			chkRecord = new CheckoutRecord(mapLibraryMember.get(memberId));
		else
			chkRecord = new CheckoutRecord(mapLibraryMember.get(memberId));

		chkRecord.addEntry(chkEntry);
		da.saveNewRecord(chkRecord);

		// ---------------rmove latter below part
		for (CheckoutEntry chk : chkRecord.getEntries()) {
			BookCopy cop = chk.getBookCopy();
			Book bk = cop.getBook();

			for (BookCopy b : bk.getCopies()) {
				System.out.println(b.isAvailable());
			}
		}

		System.out.println("=================================================================");

		CheckoutRecord chkRecord2 = new CheckoutRecord(mapLibraryMember.get(memberId));
		for (CheckoutEntry chk : chkRecord2.getEntries()) {
			BookCopy cop = chk.getBookCopy();
			Book bk = cop.getBook();

			for (BookCopy b : bk.getCopies()) {
				System.out.println(b.isAvailable());
			}
		}
		ObservableList<CheckoutRecordView.CheckoutRecordEntryView> list = FXCollections.observableArrayList();
		for (CheckoutEntry entry : chkRecord.getEntries()) {
			CheckoutRecordView.CheckoutRecordEntryView chkView = new CheckoutRecordView.CheckoutRecordEntryView();
			chkView.checkoutDate = entry.getCheckoutDate().toString();
			chkView.dueDate = entry.getDueDate().toString();
			chkView.title = entry.getBookCopy().getBook().getTitle();
			chkView.isbnIssueNum = entry.getBookCopy().getBook().getIsbn();
			list.add(chkView);
		}
		return list;

	}

	@Override
	public LibraryMember search(String memberId) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void updateMemberInfo(String memberId, String firstName, String lastName, String telNumber, Address addr)
			throws LibrarySystemException {
		DataAccess dataaccess = new DataAccessFacade();
		HashMap<String, LibraryMember> chkMemberIdMap = dataaccess.readLibraryMemberMap();
		// System.out.println(chkMemberIdMap);
		LibraryMember b = new LibraryMember(memberId, firstName, lastName, telNumber, addr);
		chkMemberIdMap.put(memberId, b);
		dataaccess.saveNewMember(b);
		b.setAddress(addr);

	}

	@Override
	public boolean addBook(String isbn, String title, int maxCheckoutLength, List<Author> authors) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public void printCheckoutRecord(String memberId) throws LibrarySystemException {
		if (memberId.equals(""))
			throw new LibrarySystemException("No Member Id specefied , please specifiy one!");

		DataAccess da = new DataAccessFacade();
		HashMap<String, CheckoutRecord> Map = da.readCheckoutRecordMap();

		if (!Map.containsKey(memberId))
			throw new LibrarySystemException("There is no CheckoutRecord Memberid with: " + memberId);
		CheckoutRecord checkrecord = Map.get(memberId);
		System.out.println("Checkout Record");
		System.out.println("--------------------------------------------------------");
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd");

		System.out.printf("%s\t | %s | %s\t | %s", "Book title", "Checkout Date", "Due date", "Checkout days");
		System.out.println();

		for (int i = 0; i < checkrecord.getEntries().size(); i++) {

			System.out.printf("%s\t | %s\t | %s\t | %s",
					checkrecord.getEntries().get(i).getBookCopy().getBook().getTitle(),
					sdf.format(checkrecord.getEntries().get(i).getCheckoutDate()),
					sdf.format(checkrecord.getEntries().get(i).getDueDate()),
					checkrecord.getEntries().get(i).getBookCopy().getBook().getMaxCheckoutLength());
			System.out.println();
		}

	}

	@Override
	public boolean isAvailable(BookCopy copy) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public Book searchBook(String isbn) {
		DataAccess da = new DataAccessFacade();
		return da.searchBook(isbn);
	}

	@Override
	public ObservableList<Book> readBooksMap() {
		ObservableList<Book> booklist = FXCollections.observableArrayList();
		DataAccess da = new DataAccessFacade();
		HashMap<String, Book> bookmap = da.readBooksMap();

		for (Book book : bookmap.values())
			booklist.add(book);
		return booklist;
	}

	@Override
	public void addNewMember(String memberId, String firstName, String lastName, String telNumber, Address addr)
			throws LibrarySystemException {
		DataAccess dataaccess = new DataAccessFacade();
		LibraryMember lm = new LibraryMember(memberId, firstName, lastName, telNumber, addr);
		HashMap<String, LibraryMember> chkMemberIdMap = dataaccess.readLibraryMemberMap();
		// if (!chkMemberIdMap.containsKey(memberId))
		// if(checkkMemberId(memberId))
		// {
		dataaccess.saveNewMember(lm);

		// }

		// else
		// throw new LibrarySystemException("ID found");
	}

	public boolean checkMemberId(String memberId) throws LibrarySystemException {

		DataAccess dataaccess = new DataAccessFacade();
		HashMap<String, LibraryMember> chkMemberIdMap = dataaccess.readLibraryMemberMap();
		if (!chkMemberIdMap.containsKey(memberId)) {
			return true;
		}
		return false;
	}

	public void deleteMember(LibraryMember p)// ,String memberId, String
												// firstName, String lastName,
												// String telNumber, Address
												// addr)
			throws LibrarySystemException {
		// System.out.println(p.getMemberId() + "new menber");
		DataAccessFacade da = new DataAccessFacade();
		da.deleteMember(p.getMemberId());
		// addNewMember( memberId, firstName, lastName, telNumber, );
		// DataAccessFacade s= new DataAccessFacade();

	}

	@Override
	public ObservableList<CheckoutRecordView.CheckoutRecordEntryView> readCheckoutRecordByMemberId(String memberId)
			throws LibrarySystemException {
		if (memberId.equals(""))
			throw new LibrarySystemException("No Member Id specefied , please specifiy one!");

		DataAccess da = new DataAccessFacade();
		HashMap<String, CheckoutRecord> Map = da.readCheckoutRecordMap();

		if (!Map.containsKey(memberId))
			throw new LibrarySystemException("There is no CheckoutRecord Memberid with: " + memberId);
		CheckoutRecord checkrecord = Map.get(memberId);

		ObservableList<CheckoutRecordView.CheckoutRecordEntryView> list = FXCollections.observableArrayList();
		for (CheckoutEntry entry : checkrecord.getEntries()) {
			CheckoutRecordView.CheckoutRecordEntryView chkView = new CheckoutRecordView.CheckoutRecordEntryView();
			chkView.checkoutDate = entry.getCheckoutDate().toString();
			chkView.dueDate = entry.getDueDate().toString();
			chkView.title = entry.getBookCopy().getBook().getTitle();
			chkView.isbnIssueNum = entry.getBookCopy().getBook().getIsbn();
			list.add(chkView);
		}
		return list;
	}

	@SuppressWarnings("deprecation")
	@Override
	public ObservableList<CheckoutRecordView.CheckoutRecordEntryView> readCheckoutRecordByISBN(String ISBNId)
			throws LibrarySystemException {
		if (ISBNId.equals(""))
			throw new LibrarySystemException("No ISBN specefied , please specifiy one!");

		DataAccess da = new DataAccessFacade();
		HashMap<String, CheckoutRecord> Map = da.readCheckoutRecordMap();
		ObservableList<CheckoutRecordView.CheckoutRecordEntryView> list = FXCollections.observableArrayList();
		Iterator it = Map.entrySet().iterator();
		while (it.hasNext()) {
			Map.Entry pair = (Map.Entry) it.next();
			System.out.println(pair.getKey() + " = " + pair.getValue());

			CheckoutRecord checkrecord = (CheckoutRecord) pair.getValue();
			for (CheckoutEntry entry : checkrecord.getEntries()) {
				if (entry.getBookCopy().getBook().getIsbn().equals(ISBNId)) {
					CheckoutRecordView.CheckoutRecordEntryView chkView = new CheckoutRecordView.CheckoutRecordEntryView();
					chkView.checkoutDate = entry.getCheckoutDate().toString();
					chkView.dueDate = entry.getDueDate().toString();
					chkView.title = entry.getBookCopy().getBook().getTitle();
					chkView.isbnIssueNum = entry.getBookCopy().getBook().getIsbn();
					chkView.copyNum = String.valueOf(entry.getBookCopy().getCopyNum());
					chkView.MemberName = entry.getLibraryMember().getFirstName();

					if (entry.getDueDate().compareTo(java.sql.Date.valueOf(LocalDate.now())) < 0)
						chkView.Status = "Overdue";

					list.add(chkView);
				}
			}

		}

		if (list.size()==0)
			throw new LibrarySystemException("There is no CheckoutRecord ISBN with: " + ISBNId);

		return list;
	}

	public void updateBookInfo(String isbn, String title, int maxCheckoutLength, List<Author> authors, int noOfCopies)
			throws LibrarySystemException {
		DataAccess dataaccess = new DataAccessFacade();
		HashMap<String, Book> chkMemberIdMap = dataaccess.readBooksMap();

		Book bo = new Book(isbn, title, maxCheckoutLength, noOfCopies, authors);
		chkMemberIdMap.put(isbn, bo);
		dataaccess.saveNewBook(bo);
	}

}
