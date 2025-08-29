package com.model;

public class guest {
	private int guestId;
	private String guestName;
	private int guestAge;
	private String gender;
	private String contact;

	public guest(int guestId, String guestName, int guestAge, String gender, String contact) {
		super();
		this.guestId = guestId;
		this.guestName = guestName;
		this.guestAge = guestAge;
		this.gender = gender;
		this.contact = contact;
	}
	
	// Constructor used while inserting new guest (ID is auto-increment)
		public guest(String guestName, int guestAge, String gender, String contact) {
			this.guestName = guestName;
			this.guestAge = guestAge;
			this.gender = gender;
			this.contact = contact;
		}

//	public guest(int guestId2, String newname, String newgender, String newcontact) {
//		// TODO Auto-generated constructor stub
//	}
//
//	public guest(int id) {
//		// TODO Auto-generated constructor stub
//	}
//
//	public guest(String name) {
//		// TODO Auto-generated constructor stub
//	}

	public int getGuestId() {
		return guestId;
	}

	public void setGuestId(int guestId) {
		this.guestId = guestId;
	}

	public String getGuestName() {
		return guestName;
	}

	public void setGuestName(String guestName) {
		this.guestName = guestName;
	}

	public int getGuestAge() {
		return guestAge;
	}

	public void setGuestAge(int guestAge) {
		this.guestAge = guestAge;
	}

	public String getGender() {
		return gender;
	}

	public void setGender(String gender) {
		this.gender = gender;
	}

	public String getContact() {
		return contact;
	}

	public void setContact(String contact) {
		this.contact = contact;
	}

	@Override
	public String toString() {
		return "guest [guestId=" + guestId + ", guestName=" + guestName + ", guestAge=" + guestAge + ", gender="
				+ gender + ", contact=" + contact + "]";
	}

}
