package com.model;

public class room {
	private int roomId;
	private String roomType;
	private double pricePerDay;
	private boolean isAvailable;

	public room(int roomId, String roomType, double pricePerDay, boolean isAvailable) {
		super();
		this.roomId = roomId;
		this.roomType = roomType;
		this.pricePerDay = pricePerDay;
		this.isAvailable = isAvailable;
	}

	public room() {
	}

	// Constructor used when adding new room (ID auto-increment)
	public room(String roomType, double pricePerDay, boolean isAvailable) {
		this.roomType = roomType;
		this.pricePerDay = pricePerDay;
		this.isAvailable = isAvailable;
	}



	public int getRoomId() {
		return roomId;
	}

	public void setRoomId(int roomId) {
		this.roomId = roomId;
	}

	public String getRoomType() {
		return roomType;
	}

	public void setRoomType(String roomType) {
		this.roomType = roomType;
	}

	public double getPricePerDay() {
		return pricePerDay;
	}

	public void setPricePerDay(double pricePerDay) {
		this.pricePerDay = pricePerDay;
	}

	public boolean getisAvailable() {
		return isAvailable;
	}

	public void setAvailable(boolean isAvailable) {
		this.isAvailable = isAvailable;
	}

	@Override
	public String toString() {
		return "room [roomId=" + roomId + ", roomType=" + roomType + ", pricePerDay=" + pricePerDay + ", isAvailable="
				+ isAvailable + "]";
	}

}
