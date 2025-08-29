package com.service;

import java.sql.SQLException;
import java.util.*;
import com.model.*;
import com.dao.*;

public class bookingService {
	private bookingDAO dao = new bookingDAO();

	public void addBooking(booking booking) throws SQLException {
		dao.addBooking(booking);
	}

	public List<booking> getAllBookings() throws SQLException {
		return dao.getAllBookings();
	}

	public void updateBooking(booking booking) throws SQLException {
		dao.updateBooking(booking);
	}

	public void deleteBooking(int bookingId) throws SQLException {
		dao.deleteBooking(bookingId);
	}

	public booking getBookingById(int bookingId) throws SQLException {
		return dao.getBookingById(bookingId);
	}

	public List<booking> getBookingByGuestId(int guestId) throws SQLException {
		return dao.getBookingByGuestId(guestId);
	}

	public int countBooking() throws SQLException {
		return dao.countBooking();
	}

	public void viewBookingDetailsWithJoin() throws SQLException {
		dao.viewBookingDetailsWithJoin();
	}

	public void updatePaymentStatus(int bookingId, String status) throws SQLException {
		dao.updatePaymentStatus(bookingId, status);
	}

	public double calculateTotalRevenue() throws SQLException {
		return dao.calculateTotalRevenue();
	}

}
