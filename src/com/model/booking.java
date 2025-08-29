package com.model;

import java.sql.Date;

public class booking {

    private int bookingId;
    private int guestId;
    private int roomId;
    private Date checkInDate;
    private Date checkOutDate;
    private String paymentMode;
    private String paymentStatus;
    private double totalAmount;

    // 1. Full constructor (for fetching from DB)
    public booking(int bookingId, int guestId, int roomId, Date checkInDate, Date checkOutDate,
                   String paymentMode, String paymentStatus, double totalAmount) {
        this.bookingId = bookingId;
        this.guestId = guestId;
        this.roomId = roomId;
        this.checkInDate = checkInDate;
        this.checkOutDate = checkOutDate;
        this.paymentMode = paymentMode;
        this.paymentStatus = paymentStatus;
        this.totalAmount = totalAmount;
    }

    // 2. Constructor without bookingId (for inserting new booking)
    public booking(int guestId, int roomId, Date checkInDate, Date checkOutDate,
                   String paymentMode, String paymentStatus, double totalAmount) {
        this.guestId = guestId;
        this.roomId = roomId;
        this.checkInDate = checkInDate;
        this.checkOutDate = checkOutDate;
        this.paymentMode = paymentMode;
        this.paymentStatus = paymentStatus;
        this.totalAmount = totalAmount;
    }

    // 3. Constructor using java.util.Date (auto-converts to java.sql.Date)
    public booking(int guestId, int roomId, java.util.Date checkInDate, java.util.Date checkOutDate,
                   String paymentMode, String paymentStatus, double totalAmount) {
        this.guestId = guestId;
        this.roomId = roomId;
        this.checkInDate = new Date(checkInDate.getTime());
        this.checkOutDate = new Date(checkOutDate.getTime());
        this.paymentMode = paymentMode;
        this.paymentStatus = paymentStatus;
        this.totalAmount = totalAmount;
    }

    // Getters and setters
    public int getBookingId() {
        return bookingId;
    }

    public void setBookingId(int bookingId) {
        this.bookingId = bookingId;
    }

    public int getGuestId() {
        return guestId;
    }

    public void setGuestId(int guestId) {
        this.guestId = guestId;
    }

    public int getRoomId() {
        return roomId;
    }

    public void setRoomId(int roomId) {
        this.roomId = roomId;
    }

    public Date getCheckInDate() {
        return checkInDate;
    }

    public void setCheckInDate(Date checkInDate) {
        this.checkInDate = checkInDate;
    }

    public Date getCheckOutDate() {
        return checkOutDate;
    }

    public void setCheckOutDate(Date checkOutDate) {
        this.checkOutDate = checkOutDate;
    }

    public String getPaymentMode() {
        return paymentMode;
    }

    public void setPaymentMode(String paymentMode) {
        this.paymentMode = paymentMode;
    }

    public String getPaymentStatus() {
        return paymentStatus;
    }

    public void setPaymentStatus(String paymentStatus) {
        this.paymentStatus = paymentStatus;
    }

    public double getTotalAmount() {
        return totalAmount;
    }

    public void setTotalAmount(double totalAmount) {
        this.totalAmount = totalAmount;
    }

    @Override
    public String toString() {
        return "Booking [bookingId=" + bookingId + ", guestId=" + guestId + ", roomId=" + roomId +
               ", checkInDate=" + checkInDate + ", checkOutDate=" + checkOutDate +
               ", paymentMode=" + paymentMode + ", paymentStatus=" + paymentStatus +
               ", totalAmount=" + totalAmount + "]";
    }
}
