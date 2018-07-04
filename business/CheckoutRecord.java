package business;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class CheckoutRecord implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 5386133569718687635L;
	private List<CheckoutEntry> entries;
	public List<CheckoutEntry> getEntries() {
		return entries;
	}

	private LibraryMember member;
	private String memberId;
	private String title;
	
	public String getTitle() {
		title = entries.get(0).getBookCopy().getBook().getTitle();
		return title;
	}
	public CheckoutRecord(){
		
	}
	public CheckoutRecord(LibraryMember member){
		entries = new ArrayList<CheckoutEntry>();
		this.member = member;
		memberId = member.getMemberId();
	}
	
	public LibraryMember getMember() {
		return member;
	}

	public void setMember(LibraryMember member) {
		this.member = member;
	}

	public void addEntry(CheckoutEntry entry){
		entries.add(entry);
	}
	
	public String getMemberId() {
		return memberId;
	}
}
