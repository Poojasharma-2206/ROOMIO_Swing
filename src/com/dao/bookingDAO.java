package com.dao;

import com.model.*;

import java.sql.Connection;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.*;

public class bookingDAO {
	private final String URL = "jdbc:mysql://localhost:3306/roomie";
	private final String USER = "root";
	private final String PASS = "pooja@#_2202";

	private Connection getConnection() throws SQLException {
		return DriverManager.getConnection(URL, USER, PASS);
	}

//Add booking
	public void addBooking(booking booking) throws SQLException {
		String sql = "insert into booking(guestId, roomId, checkInDate, checkOutDate, paymentMode, paymentStatus, totalAmount) values(?,?,?,?,?,?,?)";
		try (Connection con = getConnection(); PreparedStatement ps = con.prepareStatement(sql)) {
			ps.setInt(1, booking.getGuestId());
			ps.setInt(2, booking.getRoomId());
			ps.setDate(3, booking.getCheckInDate());
			ps.setDate(4, booking.getCheckOutDate());
			ps.setString(5, booking.getPaymentMode());
			ps.setString(6, booking.getPaymentStatus());
			ps.setDouble(7, booking.getTotalAmount());
			ps.executeUpdate();
		}
	}

	// Select all
	public List<booking> getAllBookings() throws SQLException {
	    List<booking> list = new ArrayList<>();

	    String sql = "SELECT * FROM booking";
	    try (Connection con = getConnection();
	         PreparedStatement ps = con.prepareStatement(sql);
	         ResultSet rs = ps.executeQuery()) {

	        while (rs.next()) {
	            int bookingId = rs.getInt("bookingId");
	            int guestId = rs.getInt("guestId");
	            int roomId = rs.getInt("roomId");
	            Date checkInDate = rs.getDate("checkInDate");
	            Date checkOutDate = rs.getDate("checkOutDate");
	            String paymentMode = rs.getString("paymentMode");
	            String paymentStatus = rs.getString("paymentStatus");
	            double totalAmount = rs.getDouble("totalAmount");

	            booking book = new booking(bookingId, guestId, roomId, checkInDate, checkOutDate,
	                                       paymentMode, paymentStatus, totalAmount);
	            list.add(book);
	        }

	    } catch (SQLException e) {
	        throw new SQLException("Error fetching bookings from DB", e);
	    }

	    return list;
	}

	// 3.updatebooking
	public void updateBooking(booking booking) throws SQLException {

		String sql = "update booking set guestId=?, roomId=?, checkInDate=?, checkOutDate=?, paymentMode=?, paymentStatus=?, totalAmount=? where bookingId=?";
		try (Connection con = getConnection(); PreparedStatement ps = con.prepareStatement(sql)) {
			ps.setInt(1, booking.getGuestId());
			ps.setInt(2, booking.getRoomId());
			ps.setDate(3, booking.getCheckInDate());
			ps.setDate(4, booking.getCheckOutDate());
			ps.setString(5, booking.getPaymentMode());
			ps.setString(6, booking.getPaymentStatus());
			ps.setDouble(7, booking.getTotalAmount());
			ps.setInt(8, booking.getBookingId());
			ps.executeUpdate();
		} catch (SQLException e) {
			System.out.println("SQL Error in bookingDAO: " + e.getMessage());
		}

	}

	// Delete
	public void deleteBooking(int bookingId) throws SQLException {
		String sql = "delete from booking where bookingId = ?";
		try (Connection con = getConnection(); PreparedStatement ps = con.prepareStatement(sql)) {
			ps.setInt(1, bookingId);
			ps.executeUpdate();
		} catch (SQLException e) {
			System.out.println("SQL Error in bookingDAO: " + e.getMessage());
		}

	}

	// getBookByID
	public booking getBookingById(int bookingId) throws SQLException {
		booking b = null;
		String sql = "select * from booking where bookingId = ?";
		try (Connection con = getConnection(); PreparedStatement ps = con.prepareStatement(sql)) {
			ps.setInt(1, bookingId);
			ResultSet rs = ps.executeQuery();
			while (rs.next()) {
				int bookingid = rs.getInt("bookingId");
				int guestId = rs.getInt("guestId");
				int roomId = rs.getInt("roomId");
				Date checkInDate = rs.getDate("checkInDate");
				Date checkOutDate = rs.getDate("checkOutDate");
				String paymentMode = rs.getString("paymentMode");
				String paymentStatus = rs.getString("paymentStatus");
				double totalAmount = rs.getDouble("totalAmount");

				b = new booking(bookingid, guestId, roomId, checkInDate, checkOutDate, paymentMode, paymentStatus,
						totalAmount);
			}

		} catch (SQLException e) {
			System.out.println("SQL Error in bookingDAO: " + e.getMessage());
		}

		return b;
	}

	// bookingByGuestId
	public List<booking> getBookingByGuestId(int guestId) throws SQLException {
		List<booking> list = new ArrayList<>();

		String sql = "select * from booking where guestId = ?";
		try (Connection con = getConnection(); PreparedStatement ps = con.prepareStatement(sql)) {
			ps.setInt(1, guestId);
			ResultSet rs = ps.executeQuery();
			while (rs.next()) {
				int bookingid = rs.getInt("bookingId");
				int guestid = rs.getInt("guestId");
				int roomId = rs.getInt("roomId");
				Date checkInDate = rs.getDate("checkInDate");
				Date checkOutDate = rs.getDate("checkOutDate");
				String paymentMode = rs.getString("paymentMode");
				String paymentStatus = rs.getString("paymentStatus");
				double totalAmount = rs.getDouble("totalAmount");

				booking book = new booking(bookingid, guestid, roomId, checkInDate, checkOutDate, paymentMode,
						paymentStatus, totalAmount);

				list.add(book);
			}

		} catch (SQLException e) {
			System.out.println("SQL Error in bookingDAO: " + e.getMessage());
		}

		return list;
	}

	// countBooking
	public int countBooking() throws SQLException {
		int count = 0;
		String sql = "select count(*) from booking";

		try (Connection con = getConnection(); PreparedStatement ps = con.prepareStatement(sql)) {
			ResultSet rs = ps.executeQuery();

			while (rs.next()) {
				count = rs.getInt(1);
			}
		} catch (SQLException e) {
			System.out.println("SQL Error in bookingDAO: " + e.getMessage());
		}

		return count;
	}

	// viewBookingDetailsWithJoin()
	public void viewBookingDetailsWithJoin() throws SQLException {
		String sql = "select b.bookingId, g.guestId, g.guestName, r.roomId, r.roomType, b.checkInDate, b.checkOutDate, b.paymentMode, b.paymentStatus, b.totalAmount from booking b join guest g on b.guestId = g.guestId join room r on b.roomId = r.roomId";
		try (Connection con = getConnection();
				PreparedStatement ps = con.prepareStatement(sql);
				ResultSet rs = ps.executeQuery();) {

			boolean found = false;
			while (rs.next()) {
				found = true;
				System.out.println("Booking ID: " + rs.getInt("bookingId"));
				System.out.println("Guest ID: " + rs.getInt("guestId"));
				System.out.println("Guest Name: " + rs.getString("guestName"));
				System.out.println("Room ID: " + rs.getInt("roomId"));
				System.out.println("Room Type: " + rs.getString("roomType"));
				System.out.println("Check-In Date: " + rs.getDate("checkInDate"));
				System.out.println("Check-Out Date: " + rs.getDate("checkOutDate"));
				System.out.println("Payment Mode: " + rs.getString("paymentMode"));
				System.out.println("Payment Status: " + rs.getString("paymentStatus"));
				System.out.println("Total Amount: " + rs.getDouble("totalAmount"));
				System.out.println("--------------------------------------------------");
			}

			if (!found) {
				System.out.println("No booking records found with join");
			}

		} catch (SQLException e) {
			System.out.println("SQL Error in bookingDAO: " + e.getMessage());
		}

	}

//	updatePaymentStatus()
	public void updatePaymentStatus(int bookingId, String newStatus) throws SQLException {
		String sql = "UPDATE booking SET paymentStatus = ? WHERE bookingId = ?";
		try (Connection con = getConnection(); PreparedStatement ps = con.prepareStatement(sql)) {
			ps.setString(1, newStatus);
			ps.setInt(2, bookingId);
			ps.executeUpdate();
		}
	}

////	getUnpaidBookings()
//	public List<booking> getUnpaidBookings() throws SQLException {
//		List<booking> list = new ArrayList<>();
//		String sql = "SELECT * FROM booking WHERE paymentStatus = 'Pending'";
//		try (Connection con = getConnection(); PreparedStatement ps = con.prepareStatement(sql)) {
//			ResultSet rs = ps.executeQuery();
//			while (rs.next()) {
//				// Same logic as getAllBookings()
//				// Create booking object and add to list
//			}
//		}
//		return list;
//	}

//	calculateTotalRevenue():
	public double calculateTotalRevenue() throws SQLException {
		double total = 0;
		String sql = "SELECT SUM(totalAmount) FROM booking WHERE paymentStatus = 'Paid'";
		try (Connection con = getConnection(); PreparedStatement ps = con.prepareStatement(sql)) {
			ResultSet rs = ps.executeQuery();
			if (rs.next()) {
				total = rs.getDouble(1);
			}

		} catch (SQLException e) {
			System.out.println("SQL Error in bookingDAO: " + e.getMessage());
		}

		return total;
	}

}

//addBooking()
//
//getAllBookings()
//
//updateBooking()
//
//deleteBooking()
//
//getBookingById()
//
//getBookingsByGuestId()
//
//countBookings()
//
//viewBookingDetailsWithJoin()
