package com.dao;

import java.util.*;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.ResultSet;

import com.model.*;

public class roomDAO {
	private final String URL = "jdbc:mysql://localhost:3306/roomie";
	private final String USER = "root";
	private final String PASS = "pooja@#_2202";

	private Connection getConnection() throws SQLException {
		return DriverManager.getConnection(URL, USER, PASS);
	}

	// 1. addRoom
	public void addRoom(room room) throws SQLException {

		String sql = "insert into room(roomId, roomtype, pricePerDay, isAvailable) values(?,?,?,?)";

		try (Connection con = getConnection(); PreparedStatement ps = con.prepareStatement(sql)) {
			ps.setInt(1, room.getRoomId());
			ps.setString(2, room.getRoomType());
			ps.setDouble(3, room.getPricePerDay());
			ps.setBoolean(4, room.getisAvailable());

			ps.executeUpdate();

		}

		catch (SQLException s) {
			s.printStackTrace();
		}

	}

	// 2. get All rooms(select all)
	public List<room> getAllRooms() throws SQLException {
		List<room> list = new ArrayList<>();
		String sql = "select *  from room";

		try (Connection con = getConnection(); Statement st = con.createStatement()) {
			ResultSet rs = st.executeQuery(sql);
			while (rs.next()) {
				int roomId = rs.getInt(1);
				String roomType = rs.getString(2);
				double pricePerDay = rs.getDouble(3);
				boolean isAvailable = rs.getBoolean(4);

				room room = new room(roomId, roomType, pricePerDay, isAvailable);
				list.add(room);
			}
		} catch (SQLException s) {
			s.printStackTrace();
		}

		return list;
	}

	// 3. update
	public void updateRoom(room room) throws SQLException {
		String sql = "UPDATE room SET roomType = ?, pricePerDay = ?, isAvailable = ? WHERE roomId = ?";

		try (Connection con = getConnection(); PreparedStatement ps = con.prepareStatement(sql)) {

			ps.setString(1, room.getRoomType());
			ps.setDouble(2, room.getPricePerDay());
			ps.setBoolean(3, room.getisAvailable());
			ps.setInt(4, room.getRoomId());

			ps.executeUpdate();

		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	// 4. DELETE Room by Room Number
	public void deleteRoom(int roomId) throws SQLException {
		String sql = "DELETE FROM room WHERE roomId = ?";

		try (Connection con = getConnection(); PreparedStatement ps = con.prepareStatement(sql)) {

			ps.setInt(1, roomId);
			ps.executeUpdate();

		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	// 5. get room by room id
	public room getRoomByID(int roomId) throws SQLException {
		room r = null;
		String sql = "select * from room where roomId = ?";
		try (Connection con = getConnection(); PreparedStatement ps = con.prepareStatement(sql)) {
			ps.setInt(1, roomId);
			ResultSet rs = ps.executeQuery();

			while (rs.next()) {
				int roomid = rs.getInt("roomId");
				String roomType = rs.getString("roomType");
				double pricePerDay = rs.getDouble("pricePerDay");
				boolean isAvailable = rs.getBoolean("isAvailable");

				r = new room(roomid, roomType, pricePerDay, isAvailable);
			}
		} catch (SQLException s) {

			s.printStackTrace();
		}

		return r;

	}

	// 6. get by room type
	public List<room> getRoomByRoomType(String roomType) throws SQLException {
		List<room> list = new ArrayList();
		String sql = "select * from room where roomType = ?";

		try (Connection con = getConnection(); PreparedStatement ps = con.prepareStatement(sql)) {
			ps.setString(1, roomType);
			ResultSet rs = ps.executeQuery();

			while (rs.next()) {
				int roomId = rs.getInt("roomId");
				String roomtype = rs.getString("roomType");
				double pricePerDay = rs.getDouble("pricePerDay");
				boolean isAvailable = rs.getBoolean("isAvailable");

				room room = new room(roomId, roomtype, pricePerDay, isAvailable);
				list.add(room);
			}

		} catch (SQLException s) {
			s.printStackTrace();
		}

		return list;

	}

	// 7. Get Available Rooms
	public List<room> getAvailableRoom() throws SQLException {
		List<room> list = new ArrayList<>();
		String sql = "select * from room where isAvailable = true";

		try (Connection con = getConnection(); Statement st = con.createStatement()) {
			ResultSet rs = st.executeQuery(sql);
			while (rs.next()) {
				int roomId = rs.getInt("roomId");
				String roomType = rs.getString("roomType");
				double pricePerDay = rs.getDouble("pricePerDay");
				boolean isAvailable = rs.getBoolean("isAvailable");

				room room = new room(roomId, roomType, pricePerDay, isAvailable);
				list.add(room);
			}
		} catch (SQLException s) {
			s.printStackTrace();
		}
		return list;
	}

}

//Add Room
//
//Get All Rooms
//
//update
//
//delete
//
//Get Room By Room Number
//
//Get Rooms By Type (AC/Non-AC)
//
//Get Available Rooms
