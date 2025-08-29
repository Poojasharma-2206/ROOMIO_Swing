package com.service;
import java.sql.SQLException;
import java.util.*;
import com.model.*;
import com.dao.*;
public class guestService {
private guestDAO dao = new guestDAO();

public void addGuest(guest guest) throws SQLException{
	dao.addGuest(guest);
}

public List <guest> getAllGuest() throws SQLException{
	return dao.getAllGuest();
}

public void updateGuest(guest guest) throws SQLException {
	 dao.updateGuest(guest);
}


public void deletGuest(int guestId) throws SQLException {
	 dao.deletGuest(guestId);
}


public guest getGuestById(int guestId) throws SQLException{
	return dao.getGuestById(guestId);
}

public guest  getGuestByName(String guestName) throws SQLException{
	return dao.getGuestByName(guestName);
}


public List<guest> getGuestByGender(String gender) throws SQLException{
	return dao.getGuestByGender(gender);
}


public guest getGuestByContact(String contact) throws SQLException{
	return dao.getGuestByContact(contact);
}

public int countGuest() throws SQLException{
	return dao.countGuest();
}




}
