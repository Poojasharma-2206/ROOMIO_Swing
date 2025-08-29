package com.service;

import java.util.*;
import com.model.*;
import com.dao.*;
import java.sql.SQLException;

public class roomService {
	private roomDAO dao = new roomDAO();

	public void addRoom(room room) throws SQLException {
		dao.addRoom(room);
	}

	public List<room> getAllRooms() throws SQLException {
		return dao.getAllRooms();
	}

	public void updateRoom(room room) throws SQLException {
		dao.updateRoom(room);
	}

	public void deleteRoom(int roomId) throws SQLException {
		dao.deleteRoom(roomId);
	}

	public room getRoomByID(int roomId) throws SQLException {
		return dao.getRoomByID(roomId);
	}

	public List<room> getRoomByRoomType(String roomType) throws SQLException {
		return dao.getRoomByRoomType(roomType);
	}

	public List<room> getAvailableRoom() throws SQLException{
		return dao.getAvailableRoom();
	}

}
