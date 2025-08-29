package com.gui;

import com.model.booking;
import com.model.guest;
import com.model.room;
import com.service.bookingService;
import com.service.guestService;
import com.service.roomService;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.sql.Date;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class HotelGUI extends JFrame {

	// Services (reuse your existing backend)
	private final guestService guestSvc = new guestService();
	private final roomService roomSvc = new roomService();
	private final bookingService bookingSvc = new bookingService();

	// ======== Guest UI ========
	private JTable tblGuest;
	private DefaultTableModel guestModel;
	private JTextField gId, gName, gAge, gGender, gContact, gSearchId, gSearchName, gSearchGender, gSearchContact;

	// ======== Room UI ========
	private JTable tblRoom;
	private DefaultTableModel roomModel;
	private JTextField rId, rType, rPrice, rAvail, rSearchId, rSearchType;

	// ======== Booking UI ========
	private JTable tblBooking;
	private DefaultTableModel bookingModel;
	private JTextField bId, bGuestId, bRoomId, bIn, bOut, bMode, bStatus, bAmount, bSearchId, bSearchGuestId;

	public HotelGUI() {
		super("ROOMIO — Hotel Management (Swing GUI)");
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		setSize(1100, 700);
		setLocationRelativeTo(null);

		JTabbedPane tabs = new JTabbedPane();
		tabs.addTab("Guest", buildGuestTab());
		tabs.addTab("Room", buildRoomTab());
		tabs.addTab("Booking", buildBookingTab());

		setContentPane(tabs);

		// Initial loads
		loadAllGuests();
		loadAllRooms();
		loadAllBookings();
	}

	// ===================== Guest Tab =====================
	// ===================== Guest Tab =====================
	private JPanel buildGuestTab() {
		JPanel root = new JPanel(new BorderLayout(10, 10));
		root.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

		guestModel = new DefaultTableModel(new String[] { "ID", "Name", "Age", "Gender", "Contact" }, 0) {
			public boolean isCellEditable(int r, int c) {
				return false;
			}
		};
		tblGuest = new JTable(guestModel);
		root.add(new JScrollPane(tblGuest), BorderLayout.CENTER);

		// Form panel
		JPanel form = new JPanel(new GridLayout(2, 1, 10, 10));

		JPanel row1 = new JPanel(new GridLayout(2, 5, 10, 6));
		gId = new JTextField();
		gId.setEnabled(false); // Auto-increment field, user cannot edit
		gName = new JTextField();
		gAge = new JTextField();
		gGender = new JTextField();
		gContact = new JTextField();

		row1.add(labeled("ID (for Update/Delete/Search by ID)", gId));
		row1.add(labeled("Name", gName));
		row1.add(labeled("Age", gAge));
		row1.add(labeled("Gender", gGender));
		row1.add(labeled("Contact", gContact));

		JPanel row2 = new JPanel(new FlowLayout(FlowLayout.LEFT, 10, 0));
		JButton add = new JButton("Add");
		JButton update = new JButton("Update");
		JButton delete = new JButton("Delete by ID");
		JButton showAll = new JButton("Show All");
		JButton count = new JButton("Count");

		add.addActionListener(this::onGuestAdd);
		update.addActionListener(this::onGuestUpdate);
		delete.addActionListener(this::onGuestDelete);
		showAll.addActionListener(e -> loadAllGuests());
		count.addActionListener(e -> {
			try {
				int c = guestSvc.countGuest();
				JOptionPane.showMessageDialog(this, "Total Guests: " + c);
			} catch (SQLException ex) {
				showError(ex);
			}
		});

		row2.add(add);
		row2.add(update);
		row2.add(delete);
		row2.add(showAll);
		row2.add(count);

		form.add(row1);
		form.add(row2);

		root.add(form, BorderLayout.NORTH);

		// Search panel
		JPanel search = new JPanel(new GridLayout(2, 4, 10, 6));
		gSearchId = new JTextField();
		gSearchName = new JTextField();
		gSearchGender = new JTextField();
		gSearchContact = new JTextField();

		JButton sById = new JButton("Search by ID");
		JButton sByName = new JButton("Search by Name");
		JButton sByGender = new JButton("Search by Gender");
		JButton sByContact = new JButton("Search by Contact");

		sById.addActionListener(e -> {
			try {
				guest g = guestSvc.getGuestById(parseInt(gSearchId, "Guest ID"));
				if (g == null)
					setGuests(new ArrayList<>());
				else
					setGuests(new ArrayList<>(Arrays.asList(g)));
			} catch (Exception ex) {
				showError(ex);
			}
		});

		sByName.addActionListener(e -> {
			try {
				guest g = guestSvc.getGuestByName(gSearchName.getText().trim());
				if (g == null)
					setGuests(new ArrayList<>());
				else
					setGuests(new ArrayList<>(Arrays.asList(g)));
			} catch (Exception ex) {
				showError(ex);
			}
		});

		sByGender.addActionListener(e -> {
			try {
				List<guest> list = guestSvc.getGuestByGender(gSearchGender.getText().trim());
				if (list == null)
					setGuests(new ArrayList<>());
				else
					setGuests(list);
			} catch (Exception ex) {
				showError(ex);
			}
		});

		sByContact.addActionListener(e -> {
			try {
				guest g = guestSvc.getGuestByContact(gSearchContact.getText().trim());
				if (g == null)
					setGuests(new ArrayList<>());
				else
					setGuests(new ArrayList<>(Arrays.asList(g)));
			} catch (Exception ex) {
				showError(ex);
			}
		});

		search.add(labeled("Search ID", gSearchId));
		search.add(labeled("Search Name", gSearchName));
		search.add(labeled("Search Gender", gSearchGender));
		search.add(labeled("Search Contact", gSearchContact));
		search.add(sById);
		search.add(sByName);
		search.add(sByGender);
		search.add(sByContact);

		root.add(search, BorderLayout.SOUTH);

		// Table row click -> fill fields
		tblGuest.getSelectionModel().addListSelectionListener(e -> {
			int r = tblGuest.getSelectedRow();
			if (r >= 0) {
				gId.setText(val(tblGuest, r, 0));
				gName.setText(val(tblGuest, r, 1));
				gAge.setText(val(tblGuest, r, 2));
				gGender.setText(val(tblGuest, r, 3));
				gContact.setText(val(tblGuest, r, 4));
			}
		});

		return root;
	}

	private void onGuestAdd(ActionEvent e) {
		try {
			// ID ignored because auto-increment
			String name = gName.getText().trim();
			int age = parseInt(gAge, "Age");
			String gender = gGender.getText().trim();
			String contact = gContact.getText().trim();
			guest g = new guest(name, age, gender, contact); // ID auto-increment in DB
			guestSvc.addGuest(g);
			loadAllGuests();
			clear(gId, gName, gAge, gGender, gContact);
			toast("Guest Added");
		} catch (Exception ex) {
			showError(ex);
		}
	}

	private void onGuestUpdate(ActionEvent e) {
		try {
			int id = parseInt(gId, "Guest ID");
			String name = gName.getText().trim();
			int age = parseInt(gAge, "Age");
			String gender = gGender.getText().trim();
			String contact = gContact.getText().trim();
			guest g = new guest(id, name, age, gender, contact);
			guestSvc.updateGuest(g);
			loadAllGuests();
			toast("Guest Updated");
		} catch (Exception ex) {
			showError(ex);
		}
	}

	private void onGuestDelete(ActionEvent e) {
		try {
			int id = parseInt(gId, "Guest ID");
			guestSvc.deletGuest(id);
			loadAllGuests();
			clear(gId, gName, gAge, gGender, gContact);
			toast("Guest Deleted");
		} catch (Exception ex) {
			showError(ex);
		}
	}

	private void loadAllGuests() {
		try {
			setGuests(guestSvc.getAllGuest());
		} catch (Exception ex) {
			showError(ex);
		}
	}

	private void setGuests(List<guest> list) {
		guestModel.setRowCount(0);
		for (guest g : list) {
			guestModel.addRow(
					new Object[] { g.getGuestId(), g.getGuestName(), g.getGuestAge(), g.getGender(), g.getContact() });
		}
	}

	// ===================== Room Tab =====================
	private JPanel buildRoomTab() {
		JPanel root = new JPanel(new BorderLayout(10, 10));
		root.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

		roomModel = new DefaultTableModel(new String[] { "ID", "Type", "Price/Day", "Available" }, 0) {
			public boolean isCellEditable(int r, int c) {
				return false;
			}
		};
		tblRoom = new JTable(roomModel);
		root.add(new JScrollPane(tblRoom), BorderLayout.CENTER);

		JPanel form = new JPanel(new GridLayout(2, 1, 10, 10));
		JPanel row1 = new JPanel(new GridLayout(2, 4, 10, 6));

		rId = new JTextField();
		rId.setEnabled(false); // Auto-increment ID, user cannot edit
		rType = new JTextField();
		rPrice = new JTextField();
		rAvail = new JTextField();

		row1.add(labeled("Room ID (for Update/Delete/Search)", rId));
		row1.add(labeled("Room Type", rType));
		row1.add(labeled("Price/Day", rPrice));
		row1.add(labeled("Available (true/false)", rAvail));

		JPanel row2 = new JPanel(new FlowLayout(FlowLayout.LEFT, 10, 0));
		JButton add = new JButton("Add");
		JButton update = new JButton("Update");
		JButton delete = new JButton("Delete by ID");
		JButton showAll = new JButton("Show All");
		JButton available = new JButton("Show Available");
		JButton byType = new JButton("Search by Type");
		JButton byId = new JButton("Search by ID");

		add.addActionListener(this::onRoomAdd);
		update.addActionListener(this::onRoomUpdate);
		delete.addActionListener(e -> {
			try {
				int id = parseInt(rId, "Room ID");
				roomSvc.deleteRoom(id);
				loadAllRooms();
				clear(rId, rType, rPrice, rAvail);
				toast("Room Deleted");
			} catch (Exception ex) {
				showError(ex);
			}
		});
		showAll.addActionListener(e -> loadAllRooms());
		available.addActionListener(e -> {
			try {
				setRooms(roomSvc.getAvailableRoom());
			} catch (Exception ex) {
				showError(ex);
			}
		});
		byType.addActionListener(e -> {
			try {
				setRooms(roomSvc.getRoomByRoomType(rType.getText().trim()));
			} catch (Exception ex) {
				showError(ex);
			}
		});
		byId.addActionListener(e -> {
			try {
				room r = roomSvc.getRoomByID(parseInt(rId, "Room ID"));
				if (r == null)
					setRooms(new ArrayList<>());
				else
					setRooms(new ArrayList<>(Arrays.asList(r)));
			} catch (Exception ex) {
				showError(ex);
			}
		});

		row2.add(add);
		row2.add(update);
		row2.add(delete);
		row2.add(showAll);
		row2.add(available);
		row2.add(byType);
		row2.add(byId);

		form.add(row1);
		form.add(row2);
		root.add(form, BorderLayout.NORTH);

		tblRoom.getSelectionModel().addListSelectionListener(e -> {
			int r = tblRoom.getSelectedRow();
			if (r >= 0) {
				rId.setText(val(tblRoom, r, 0));
				rType.setText(val(tblRoom, r, 1));
				rPrice.setText(val(tblRoom, r, 2));
				rAvail.setText(val(tblRoom, r, 3));
			}
		});

		// Search strip
		JPanel search = new JPanel(new GridLayout(1, 4, 10, 6));
		rSearchId = new JTextField();
		rSearchType = new JTextField();
		JButton sId = new JButton("Find by ID");
		JButton sType = new JButton("Find by Type");

		sId.addActionListener(e -> {
			try {
				room r = roomSvc.getRoomByID(parseInt(rSearchId, "Room ID"));
				if (r == null)
					setRooms(new ArrayList<>());
				else
					setRooms(new ArrayList<>(Arrays.asList(r)));
			} catch (Exception ex) {
				showError(ex);
			}
		});

		sType.addActionListener(e -> {
			try {
				setRooms(roomSvc.getRoomByRoomType(rSearchType.getText().trim()));
			} catch (Exception ex) {
				showError(ex);
			}
		});

		search.add(labeled("Search ID", rSearchId));
		search.add(sId);
		search.add(labeled("Search Type", rSearchType));
		search.add(sType);
		root.add(search, BorderLayout.SOUTH);

		return root;
	}

	private void onRoomAdd(ActionEvent e) {
		try {
			String type = rType.getText().trim();
			double price = Double.parseDouble(rPrice.getText().trim());
			boolean avail = Boolean.parseBoolean(rAvail.getText().trim());
			room r = new room(type, price, avail); // ID ignored, auto-increment in DB
			roomSvc.addRoom(r);
			loadAllRooms();
			clear(rId, rType, rPrice, rAvail);
			toast("Room Added");
		} catch (Exception ex) {
			showError(ex);
		}
	}

	private void onRoomUpdate(ActionEvent e) {
		try {
			int id = parseInt(rId, "Room ID");
			String type = rType.getText().trim();
			double price = parseDouble(rPrice, "Price/Day");
			boolean available = parseBoolean(rAvail, "Available");
			room r = new room(id, type, price, available);
			roomSvc.updateRoom(r);
			loadAllRooms();
			toast("Room Updated");
		} catch (Exception ex) {
			showError(ex);
		}
	}

	private boolean parseBoolean(JTextField tf, String name) {
		String v = tf.getText().trim().toLowerCase();
		if ("true".equals(v))
			return true;
		if ("false".equals(v))
			return false;
		throw new IllegalArgumentException(name + " must be true or false");
	}

	private void loadAllRooms() {
		try {
			setRooms(roomSvc.getAllRooms());
		} catch (Exception ex) {
			showError(ex);
		}
	}

	private void setRooms(List<room> list) {
		roomModel.setRowCount(0);
		for (room r : list) {
			roomModel.addRow(new Object[] { r.getRoomId(), r.getRoomType(), r.getPricePerDay(), r.getisAvailable() });
		}
	}

	
	
	// ===================== Booking Tab =====================
	private JPanel buildBookingTab() {
		JPanel root = new JPanel(new BorderLayout(10, 10));
		root.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

		bookingModel = new DefaultTableModel(
				new String[] { "BookingID", "GuestID", "RoomID", "CheckIn", "CheckOut", "Mode", "Status", "Amount" },
				0) {
			public boolean isCellEditable(int r, int c) {
				return false;
			}
		};
		tblBooking = new JTable(bookingModel);
		root.add(new JScrollPane(tblBooking), BorderLayout.CENTER);

		JPanel form = new JPanel(new GridLayout(3, 1, 10, 10));

		// ---------- Row 1 ----------
		JPanel row1 = new JPanel(new GridLayout(2, 6, 10, 6));
		bId = new JTextField();
		bId.setEnabled(false); // Booking ID auto
		bGuestId = new JTextField();
		bRoomId = new JTextField();
		bIn = new JTextField();
		bOut = new JTextField();
		bAmount = new JTextField();

		row1.add(labeled("Booking ID (Auto)", bId));
		row1.add(labeled("Guest ID", bGuestId));
		row1.add(labeled("Room ID", bRoomId));
		row1.add(labeled("Check-In (yyyy-MM-dd)", bIn));
		row1.add(labeled("Check-Out (yyyy-MM-dd)", bOut));
		row1.add(labeled("Amount", bAmount));

		// ---------- Row 2 ----------
		JPanel row2 = new JPanel(new GridLayout(1, 4, 10, 6));
		bMode = new JTextField();
		bStatus = new JTextField();
		row2.add(labeled("Payment Mode (UPI/CARD/CASH/NET BANKING)", bMode));
		row2.add(labeled("Payment Status (PAID/UNPAID/Pending)", bStatus));

		// ---------- Row 3 ----------
		JPanel row3 = new JPanel(new FlowLayout(FlowLayout.LEFT, 10, 0));
		JButton add = new JButton("Book (Add)");
		JButton update = new JButton("Update");
		JButton delete = new JButton("Cancel (Delete)");
		JButton showAll = new JButton("Show All");
		JButton byId = new JButton("Find by Booking ID");
		JButton byGuestId = new JButton("Find by Guest ID");
		JButton joinView = new JButton("View Join Details (console)");
		JButton updatePay = new JButton("Update Payment Status");
		JButton unpaid = new JButton("Show Pending Payments");
		JButton revenue = new JButton("Total Revenue");

		// ---------- Actions ----------
		add.addActionListener(e -> onBookingAdd(e));
		update.addActionListener(e -> onBookingUpdate(e));

		delete.addActionListener(e -> {
			try {
				int id = parseInt(bId, "Booking ID");
				bookingSvc.deleteBooking(id);
				loadAllBookings();
				clear(bId, bGuestId, bRoomId, bIn, bOut, bMode, bStatus, bAmount);
				toast("Booking Cancelled");
			} catch (Exception ex) {
				showError(ex);
			}
		});

		showAll.addActionListener(e -> loadAllBookings());

		// Search Fields
		bSearchId = new JTextField();
		bSearchGuestId = new JTextField();

		byId.addActionListener(e -> {
			try {
				booking b = bookingSvc.getBookingById(parseInt(bSearchId, "Booking ID"));
				if (b == null)
					setBookings(new ArrayList<>());
				else
					setBookings(new ArrayList<>(Arrays.asList(b)));
			} catch (Exception ex) {
				showError(ex);
			}
		});

		byGuestId.addActionListener(e -> {
			try {
				setBookings(bookingSvc.getBookingByGuestId(parseInt(bSearchGuestId, "Guest ID")));
			} catch (Exception ex) {
				showError(ex);
			}
		});

		joinView.addActionListener(e -> {
			try {
				bookingSvc.viewBookingDetailsWithJoin();
				JOptionPane.showMessageDialog(this, "Joined details printed in console.");
			} catch (Exception ex) {
				showError(ex);
			}
		});

		updatePay.addActionListener(e -> {
			try {
				int id = parseInt(bId, "Booking ID");
				String newStatus = bStatus.getText().trim();
				bookingSvc.updatePaymentStatus(id, newStatus);
				loadAllBookings();
				toast("Payment Status Updated");
			} catch (Exception ex) {
				showError(ex);
			}
		});

//		unpaid.addActionListener(e -> {
//			try {
//				setBookings(bookingSvc.getUnpaidBookings());
//			} catch (Exception ex) {
//				showError(ex);
//			}
//		});

		revenue.addActionListener(e -> {
			try {
				double total = bookingSvc.calculateTotalRevenue();
				JOptionPane.showMessageDialog(this, "Total Revenue (Paid): " + total);
			} catch (Exception ex) {
				showError(ex);
			}
		});

		row3.add(add);
		row3.add(update);
		row3.add(delete);
		row3.add(showAll);
		row3.add(byId);
		row3.add(byGuestId);
		row3.add(joinView);
		row3.add(updatePay);
		row3.add(unpaid);
		row3.add(revenue);

		form.add(row1);
		form.add(row2);
		form.add(row3);
		root.add(form, BorderLayout.NORTH);

		// ---------- Search strip ----------
		JPanel search = new JPanel(new GridLayout(1, 4, 10, 6));
		search.add(labeled("Search Booking ID", bSearchId));
		JButton sIdBtn = new JButton("Search");
		sIdBtn.addActionListener(e -> {
			try {
				booking b = bookingSvc.getBookingById(parseInt(bSearchId, "Booking ID"));
				if (b == null)
					setBookings(new ArrayList<>());
				else
					setBookings(new ArrayList<>(Arrays.asList(b)));
			} catch (Exception ex) {
				showError(ex);
			}
		});
		search.add(sIdBtn);

		search.add(labeled("Search by Guest ID", bSearchGuestId));
		JButton sGuestBtn = new JButton("Search");
		sGuestBtn.addActionListener(e -> {
			try {
				setBookings(bookingSvc.getBookingByGuestId(parseInt(bSearchGuestId, "Guest ID")));
			} catch (Exception ex) {
				showError(ex);
			}
		});
		search.add(sGuestBtn);

		root.add(search, BorderLayout.SOUTH);

		// Table selection
		tblBooking.getSelectionModel().addListSelectionListener(e -> {
			int r = tblBooking.getSelectedRow();
			if (r >= 0) {
				bId.setText(val(tblBooking, r, 0));
				bGuestId.setText(val(tblBooking, r, 1));
				bRoomId.setText(val(tblBooking, r, 2));
				bIn.setText(val(tblBooking, r, 3));
				bOut.setText(val(tblBooking, r, 4));
				bMode.setText(val(tblBooking, r, 5));
				bStatus.setText(val(tblBooking, r, 6));
				bAmount.setText(val(tblBooking, r, 7));
			}
		});

		return root;
	}

	// ---------- Add/Update Methods ----------
	private void onBookingAdd(ActionEvent e) {
	    try {
	        int guestId = parseInt(bGuestId, "Guest ID");
	        int roomId = parseInt(bRoomId, "Room ID");
	        Date in = parseDate(bIn, "Check-In");
	        Date out = parseDate(bOut, "Check-Out");
	        String mode = bMode.getText().trim();
	        String status = bStatus.getText().trim();
	        double amount = parseDouble(bAmount, "Amount");

	        booking b = new booking(guestId, roomId, in, out, mode, status, amount);
	        bookingSvc.addBooking(b);

	        // Force fresh data load
	        List<booking> updatedList = bookingSvc.getAllBookings();
	        setBookings(updatedList);

	        clear(bId, bGuestId, bRoomId, bIn, bOut, bMode, bStatus, bAmount);
	        toast("Room Booked");
	    } catch (Exception ex) {
	        showError(ex);
	    }
	}


	private void onBookingUpdate(ActionEvent e) {
		try {
			int id = parseInt(bId, "Booking ID");
			int guestId = parseInt(bGuestId, "Guest ID");
			int roomId = parseInt(bRoomId, "Room ID");
			Date in = parseDate(bIn, "Check-In");
			Date out = parseDate(bOut, "Check-Out");
			String mode = bMode.getText().trim();
			String status = bStatus.getText().trim();
			double amount = parseDouble(bAmount, "Amount");

			booking b = new booking(guestId, roomId, in, out, mode, status, amount);
			bookingSvc.addBooking(b);
			loadAllBookings();
			toast("Booking Updated");
		} catch (Exception ex) {
			showError(ex);
		}
	}

	// ---------- Helpers ----------
	private void loadAllBookings() {
	    try {
	        setBookings(bookingSvc.getAllBookings());
	    } catch (Exception ex) {
	        showError(ex);
	    }
	}


	private void setBookings(List<booking> list) {
		bookingModel.setRowCount(0);
		for (booking b : list) {
			bookingModel.addRow(new Object[] { b.getBookingId(), b.getGuestId(), b.getRoomId(), b.getCheckInDate(),
					b.getCheckOutDate(), b.getPaymentMode(), b.getPaymentStatus(), b.getTotalAmount() });
		}
	}

	private JPanel labeled(String label, JComponent field) {
		JPanel p = new JPanel(new BorderLayout(5, 3));
		p.add(new JLabel(label), BorderLayout.NORTH);
		p.add(field, BorderLayout.CENTER);
		return p;
	}

	private String val(JTable t, int r, int c) {
		Object o = t.getValueAt(r, c);
		return o == null ? "" : String.valueOf(o);
	}

	private void clear(JTextField... f) {
		for (JTextField x : f)
			x.setText("");
	}

	private int parseInt(JTextField tf, String name) {
	    try {
	        return Integer.parseInt(tf.getText().trim());
	    } catch (Exception e) {
	        throw new IllegalArgumentException(name + " must be an integer");
	    }
	}

	private double parseDouble(JTextField tf, String name) {
	    try {
	        return Double.parseDouble(tf.getText().trim());
	    } catch (Exception e) {
	        throw new IllegalArgumentException(name + " must be a number");
	    }
	}

	private Date parseDate(JTextField tf, String name) {
	    try {
	        return Date.valueOf(tf.getText().trim());
	    } catch (Exception e) {
	        throw new IllegalArgumentException(name + " must be yyyy-MM-dd");
	    }
	}
private void toast(String msg) {
		JOptionPane.showMessageDialog(this, msg);
	}

	private void showError(Exception ex) {
		ex.printStackTrace();
		JOptionPane.showMessageDialog(this, ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
	}

	public static void main(String[] args) {
		SwingUtilities.invokeLater(() -> new HotelGUI().setVisible(true));
	}
}
