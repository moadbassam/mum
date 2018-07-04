package business;

public class LibraryMember extends Person {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String memberId;

	public LibraryMember(String memberId, String f, String l, String t, Address a) {
		super(f, l, t, a);
		this.memberId = memberId;
	}

	public String getMemberId() {
		return memberId;
	}

	public void setMemberId(String memberId) {
		this.memberId = memberId;
	}

	public void setAddress(Address address) {
	}

	public Address getAddress() {
		return super.getAddress();
	}

	public String getStreetAddress() {
		return getAddress().getStreet();
	}

	public String getCityAddress() {
		return getAddress().getCity();
	}
//
	public String getStateAddress() {
		return getAddress().getState();
	}
//
	public String getZipAddress() {
		return getAddress().getZip();
	}

	@Override
	public String toString() {
		return "ID:" + getMemberId() + " Name:" + super.getFirstName() + " " + super.getLastName() + " Phone:"
				+ super.getTelephone() + "\nAddress:" + super.getAddress();

	}

}
