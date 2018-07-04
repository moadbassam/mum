package business;

import java.io.Serializable;
import java.util.Calendar;
import java.util.Date;

public class CheckoutEntry implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = -8580728583028206660L;
	/**
	 * 
	 */

	private BookCopy bookCopy;
	// private LocalDateTime checkoutDate;
	private Date checkoutDate;
	private Date dueDate;
	private String x = "Test";
	public String getX() {
		return x;
	}

	public void setX(String x) {
		this.x = x;
	}

	private LibraryMember libraryMember;

	public CheckoutEntry(BookCopy bookCopy, LibraryMember libraryMember, int maxCheckoutLength) {
		this.bookCopy = bookCopy;
		this.libraryMember = libraryMember;

		// Date dt = new Date();
		/*
		 * LocalDateTime localDate =
		 * LocalDateTime.from(dt.toInstant()).plusDays(maxCheckoutLength);
		 * dueDate = new Date(localDate.now().getYear(),
		 * localDate.now().getMonthValue(), localDate.now().getDayOfMonth());
		 * 
		 * checkoutDate = LocalDateTime.from(dt.toInstant());
		 */

		// DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
		Calendar cal = Calendar.getInstance();
		checkoutDate = cal.getTime();
		cal.add(Calendar.DATE, maxCheckoutLength);
		dueDate = cal.getTime();
	}

	public BookCopy getBookCopy() {
		return bookCopy;
	}

	public void setBookCopy(BookCopy bookCopy) {
		this.bookCopy = bookCopy;
	}

	public Date getCheckoutDate() {
		return checkoutDate;
	}

	public void setCheckoutDate(Date checkoutDate) {
		this.checkoutDate = checkoutDate;
	}

	public Date getDueDate() {
		return dueDate;
	}

	public void setDueDate(Date dueDate) {
		this.dueDate = dueDate;
	}

	public LibraryMember getLibraryMember() {
		return libraryMember;
	}

	public void setLibraryMember(LibraryMember libraryMember) {
		this.libraryMember = libraryMember;
	}

}
