package business;

import java.text.SimpleDateFormat;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import dataaccess.DataAccess;
import dataaccess.DataAccessFacade;

public class CheckoutRecordView {
	private static final String BOOK = "Book";
	private static final String PERIODICAL = "Periodical";

	public static class CheckoutRecordEntryView {
		String index;

		public String getIndex() {
			return index;
		}

		String checkoutDate;

		public String getCheckoutDate() {
			return checkoutDate;
		}

		String dueDate;

		public String getDueDate() {
			return dueDate;
		}

		String pubType;

		public String getPubType() {
			return pubType;
		}

		String title;

		public String getTitle() {
			return title;
		}

		String isbnIssueNum;

		public String getIsbnIssueNum() {
			return isbnIssueNum;
		}

		int copyNum;

		public int getCopyNum() {
			return copyNum;
		}

		String day;

		public String getDay() {
			return day;
		}

	}

	private List<CheckoutEntry> entries;
	private Map<CheckoutEntry, Integer> indexMap = new HashMap<>();

	public CheckoutRecordView(LibraryMember member) {
		DataAccess da = new DataAccessFacade();
		HashMap<String, CheckoutRecord> d = da.readCheckoutRecordMap();
		entries = d.get(1).getEntries();
		for (int i = 0; i < entries.size(); ++i) {
			indexMap.put(entries.get(i), i + 1);
		}
	}

	public List<CheckoutRecordEntryView> getEntryViews() {

		List<CheckoutRecordEntryView> retval = new ArrayList<>();
		entries.forEach(e -> {
			CheckoutRecordEntryView view = null;
			view = new CheckoutRecordEntryView();
			view.index = indexMap.get(e).toString();
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd");
			view.checkoutDate = sdf.format(e.getCheckoutDate());// format(DateTimeFormatter.ofPattern(DataAccessFacade.DATE_PATTERN));
			view.dueDate = e.getDueDate().toString();// .format(DateTimeFormatter.ofPattern(DataAccessFacade.DATE_PATTERN));
			view.day = String.valueOf(e.getBookCopy().getBook().getMaxCheckoutLength());
			view.title = e.getBookCopy().getBook().getTitle();
			view.isbnIssueNum = e.getBookCopy().getBook().getIsbn();
			view.copyNum = e.getBookCopy().getCopyNum();
			retval.add(view);
		});
		return retval;
	}

}
