package com.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.*;
import com.model.*;

public class guestDAO {
	private final String URL = "jdbc:mysql://localhost:3306/roomie";
	private final String USER = "root";
	private final String PASS = "pooja@#_2202";

	private Connection getConnection() throws SQLException {
		return DriverManager.getConnection(URL, USER, PASS);
	}

//1. add(INSERT)
	public void addGuest(guest guest) throws SQLException {
		String sql = "insert into guest( guestName, guestAge, gender, contact) values(?,?,?,?)";
		Connection con = getConnection();

		PreparedStatement ps = con.prepareStatement(sql);

		String guestName = guest.getGuestName();
		int guestAge = guest.getGuestAge();
		String gender = guest.getGender();
		String contact = guest.getContact();

		ps.setString(1, guestName);
		ps.setInt(2, guestAge);
		ps.setString(3, gender);
		ps.setString(4, contact);
		ps.executeUpdate();
	}

// 2. select All
	public List<guest> getAllGuest() throws SQLException {
		List<guest> list = new ArrayList<>();
		String sql = "select * from guest";
		Connection con = getConnection();

		Statement st = con.createStatement();
		ResultSet rs = st.executeQuery(sql);
		while (rs.next()) {
			int guestId = rs.getInt(1);
			String guestName = rs.getString(2);
			int guestAge = rs.getInt(3);
			String gender = rs.getString(4);
			String contact = rs.getString(5);

			guest guest = new guest(guestId, guestName, guestAge, gender, contact);
			list.add(guest);
		}

		return list;
	}

	// 3. Update guest
	public void updateGuest(guest guest) throws SQLException {
		String sql = "update guest set guestName=?, guestAge=?, gender=?, contact=? where guestId=?";

		try (Connection con = getConnection(); PreparedStatement ps = con.prepareStatement(sql)) {
			ps.setString(1, guest.getGuestName());
			ps.setInt(2, guest.getGuestAge());
			ps.setString(3, guest.getGender());
			ps.setString(4, guest.getContact());
			ps.setInt(5, guest.getGuestId());
			ps.executeUpdate();
		} catch (SQLException s) {
			s.printStackTrace();
		}

	}

	// 4. DELETE guest by id
	public void deletGuest(int guestId) throws SQLException {
		String sql = "delete from guest where guestId=?";
		try (Connection con = getConnection(); PreparedStatement ps = con.prepareStatement(sql)) {
			ps.setInt(1, guestId);
			ps.executeUpdate();
		}

		catch (SQLException s) {
			s.printStackTrace();
		}
	}

	// 5. SEARCH by Id
//	
//	guest g = dao.getGuestById(1);
//	if (g != null) {
//	    System.out.println(g.getGuestId() + " " + g.getGuestName());
//	} else {
//	    System.out.println("No guest found.");
//	}

	public guest getGuestById(int guestId) throws SQLException {
		guest g = null;
		String sql = "select * from guest where guestId = ?";
		try (Connection con = getConnection(); PreparedStatement ps = con.prepareStatement(sql)) {
			ps.setInt(1, guestId);
			ResultSet rs = ps.executeQuery();
			while (rs.next()) {
				int guestid = rs.getInt("guestId");
				String guestName = rs.getString("guestName");
				int guestAge = rs.getInt("guestAge");
				String gender = rs.getString("gender");
				String contact = rs.getString("contact");
				g = new guest(guestid, guestName, guestAge, gender, contact);

			}

		} catch (SQLException s) {
			s.printStackTrace();
		}

		return g;
	}

	// 6.SEARCH by name
	public guest getGuestByName(String guestName) throws SQLException {
		guest g = null;

		String sql = "select * from guest where guestName = ?";
		try (Connection con = getConnection(); PreparedStatement ps = con.prepareStatement(sql)) {
			ps.setString(1, guestName);
			ResultSet rs = ps.executeQuery();
			while (rs.next()) {
				int guestId = rs.getInt("guestId");
				String guestname = rs.getString("guestName");
				int guestAge = rs.getInt("guestAge");
				String gender = rs.getString("gender");
				String contact = rs.getString("contact");

				g = new guest(guestId, guestname, guestAge, gender, contact);
			}

		} catch (SQLException s) {
			s.printStackTrace();
		}

		return g;
	}

	// 7. fetch All guests by gender

	public List<guest> getGuestByGender(String gender) throws SQLException {
		List<guest> list = new ArrayList<>();
		String sql = "select * from guest where gender = ?";

		try (Connection con = getConnection(); PreparedStatement ps = con.prepareStatement(sql)) {
			ps.setString(1, gender);
			ResultSet rs = ps.executeQuery();

			while (rs.next()) {
				int guestId = rs.getInt("guestId");
				String guestName = rs.getString("guestName");
				int guestAge = rs.getInt("guestAge");
				String guestGender = rs.getString("gender");
				String contact = rs.getString("contact");

				guest g = new guest(guestId, guestName, guestAge, guestGender, contact);
				list.add(g);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}

		return list;
	}



	// 8. search bye contact number

	public guest getGuestByContact(String contact) throws SQLException {
		guest g = null;
		String sql = "select * from guest where contact = ?";
		try (Connection con = getConnection(); PreparedStatement ps = con.prepareStatement(sql)) {
			ps.setString(1, contact);
			ResultSet rs = ps.executeQuery();
			while (rs.next()) {

				int guestId = rs.getInt("guestId");
				String guestname = rs.getString("guestName");
				int guestAge = rs.getInt("guestAge");
				String gender = rs.getString("gender");
				String contact1 = rs.getString("contact");

				g = new guest(guestId, guestname, guestAge, gender, contact1);
			}
		} catch (SQLException s) {
			s.printStackTrace();
		}
		return g;
	}
	
	// 9. count total guest
	public int countGuest() {

		int count = 0;
		try {
			Connection con = getConnection();
			String sql = "select count(*) from guest";
			PreparedStatement ps = con.prepareStatement(sql);
			ResultSet rs = ps.executeQuery();

			while (rs.next()) {
				count = rs.getInt(1);
			}
		} catch (SQLException s) {
			s.printStackTrace();
		}

		return count;

	}

}

// Add
//
//Select all
//
//Update Guest 
//
//Delete Guest
//
//Search Guest by ID
//
//Search Guest by Name
//
//fetch All guests by gender
//
//search by contact number
//
//Count Total Guests

