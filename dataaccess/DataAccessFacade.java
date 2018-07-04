package dataaccess;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.HashMap;
import java.util.List;


import business.Book;
import business.BookCopy;
import business.CheckoutRecord;
import business.LibraryMember;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class DataAccessFacade implements DataAccess {

	enum StorageType {
		BOOKS, MEMBERS, USERS, CheckoutRecord;
	}

	public static final String OUTPUT_DIR = System.getProperty("user.dir") + "\\dataaccess\\storage";
	public static final String DATE_PATTERN = "MM/dd/yyyy";

	//// specialized lookup methods
	// public LibraryMember searchMember(String memberId) {
	// implement
	// }

	static void loadLibraryMemberMap(List<LibraryMember> libraryMemberList) {
		HashMap<String, LibraryMember> libraryMembers = new HashMap<String, LibraryMember>();
		libraryMemberList.forEach(member -> libraryMembers.put(member.getMemberId(), member));
		saveToStorage(StorageType.MEMBERS, libraryMembers);
	}

	static void loadCheckoutRecordMap(List<CheckoutRecord> checkoutRecordList) {
		HashMap<String, CheckoutRecord> checkoutRecords = new HashMap<String, CheckoutRecord>();
		checkoutRecordList.forEach(checkoutRecord -> checkoutRecords.put("1", checkoutRecord));
		saveToStorage(StorageType.CheckoutRecord, checkoutRecords);
	}

	public Book searchBook(String isbn) {
		HashMap<String, Book> booksMap = readBooksMap();
		Book b = booksMap.get(isbn);
		return b;
	}

	public LibraryMember searchMember(String id) {
		HashMap<String, LibraryMember> membersMap = readMemberMap();
		LibraryMember b = membersMap.get(id);
		return b;
	}

	public Auth login(String id, String pwd) {
		HashMap<String, User> userMap = readUserMap();
		if (!userMap.containsKey(id))
			return null;
		User user = userMap.get(id);
		if (!pwd.equals(user.getPassword())) {
			return null;
		}
		return user.getAuthorization();
	}

	/////// save methods
	// saveNewMember
	// public void saveNewMember(LibraryMember member)

	// public void updateMember(LibraryMember member)
	// save new lendable item
	public void saveNewBook(Book book) {
		HashMap<String, Book> bookMap = readBooksMap();
		String isbn = book.getIsbn();
		bookMap.put(isbn, book);
		saveToStorage(StorageType.BOOKS, bookMap);
	}

	////// read methods that return full maps

	@SuppressWarnings("unchecked")
	public HashMap<String, Book> readBooksMap() {
		return (HashMap<String, Book>) readFromStorage(StorageType.BOOKS);
	}

	@SuppressWarnings("unchecked")
	public HashMap<String, LibraryMember> readMemberMap() {
		return (HashMap<String, LibraryMember>) readFromStorage(StorageType.MEMBERS);
	}

	// public HashMap<String, LibraryMember> readMemberMap() {

	@SuppressWarnings("unchecked")
	public HashMap<String, User> readUserMap() {
		return (HashMap<String, User>) readFromStorage(StorageType.USERS);
	}

	///// load methods - these place test data into the storage area
	///// - used just once at startup
	static void loadMemberMap(List<LibraryMember> memberList) {
		HashMap<String, LibraryMember> members = new HashMap<String, LibraryMember>();
		memberList.forEach(member -> members.put(member.getMemberId(), member));
		saveToStorage(StorageType.MEMBERS, members);
	}

	static void loadBookMap(List<Book> bookList) {
		HashMap<String, Book> books = new HashMap<String, Book>();
		//HashMap<String, CheckoutRecord> CheckoutRecords = new HashMap<String, CheckoutRecord>();
		bookList.forEach(book -> books.put(book.getIsbn(), book));
		saveToStorage(StorageType.BOOKS, books);
		//saveToStorage(StorageType.CheckoutRecord, CheckoutRecords);
	}

	static void loadUserMap(List<User> userList) {
		HashMap<String, User> users = new HashMap<String, User>();
		userList.forEach(user -> users.put(user.getId(), user));
		saveToStorage(StorageType.USERS, users);
	}

	static void saveToStorage(StorageType type, Object ob) {
		ObjectOutputStream out = null;
		try {
			Path path = FileSystems.getDefault().getPath(OUTPUT_DIR, type.toString());
			out = new ObjectOutputStream(Files.newOutputStream(path));
			out.writeObject(ob);
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			if (out != null) {
				try {
					out.close();
				} catch (Exception e) {
				}
			}
		}
	}
	
	static Object readFromStorage(StorageType type) {
		ObjectInputStream in = null;
		Object retVal = null;
		try {
			Path path = FileSystems.getDefault().getPath(OUTPUT_DIR, type.toString());
			in = new ObjectInputStream(Files.newInputStream(path));
			retVal = in.readObject();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (in != null) {
				try {
					in.close();
				} catch (Exception e) {
				}
			}
		}
		return retVal;
	}

	final static class Pair<S, T> implements Serializable {

		S first;
		T second;

		Pair(S s, T t) {
			first = s;
			second = t;
		}

		@Override
		public boolean equals(Object ob) {
			if (ob == null)
				return false;
			if (this == ob)
				return true;
			if (ob.getClass() != getClass())
				return false;
			@SuppressWarnings("unchecked")
			Pair<S, T> p = (Pair<S, T>) ob;
			return p.first.equals(first) && p.second.equals(second);
		}

		@Override
		public int hashCode() {
			return first.hashCode() + 5 * second.hashCode();
		}

		@Override
		public String toString() {
			return "(" + first.toString() + ", " + second.toString() + ")";
		}

		private static final long serialVersionUID = 5399827794066637059L;
	}

	@Override
	public boolean deleteBook(String isbn) {
		try {
			HashMap<String, Book> bookMap = readBooksMap();
			bookMap.remove(isbn);

			saveToStorage(StorageType.BOOKS, bookMap);
			return true;
		} catch (Exception e) {
			return false;
		}
	}

	@SuppressWarnings("unchecked")
	public HashMap<String, CheckoutRecord> readCheckoutRecordMap() {
		return (HashMap<String, CheckoutRecord>) readFromStorage(StorageType.CheckoutRecord);
	}


	@Override
	public void saveNewRecord(CheckoutRecord record) {
		HashMap<String, CheckoutRecord> recordMap = readCheckoutRecordMap();
		recordMap.put(record.getMember().getMemberId(), record);
		saveToStorage(StorageType.CheckoutRecord, recordMap);
	}

	@Override
	public void updateBookCopy(BookCopy copy) {
		HashMap<String, Book> bookMap = readBooksMap();
		Book book = copy.getBook();
		bookMap.put(book.getIsbn(), book);
		saveToStorage(StorageType.BOOKS, bookMap);
	}

	@SuppressWarnings("unchecked")
	public  HashMap<String, LibraryMember>  readLibraryMemberMap()
	{
		return (HashMap<String, LibraryMember>) readFromStorage(StorageType.MEMBERS);
	}
	//// to be used in memberList addAll
	public ObservableList<LibraryMember> readLibraryMemberMapList() {
		ObservableList<LibraryMember> libraryMemberlist = FXCollections.observableArrayList();
		HashMap<String, LibraryMember> libraryMemberMap = readLibraryMemberMap();
		for(LibraryMember libraryMember : libraryMemberMap.values())
			libraryMemberlist.add(libraryMember);
		return libraryMemberlist;
	}
	
	@Override
	public void saveNewMember(LibraryMember member) {
		HashMap<String, LibraryMember> memberMap = readLibraryMemberMap();
		String memberId = member.getMemberId();
		memberMap.put(memberId, member);
		saveToStorage(StorageType.MEMBERS, memberMap);
	}

	public boolean deleteMember(String id) {
		try {
			HashMap<String, LibraryMember> memberMap = readLibraryMemberMap();
			memberMap.remove(id);
			saveToStorage(StorageType.MEMBERS, memberMap);
			return true;
		} catch (Exception e) {
			return false;
		}
	}

}
