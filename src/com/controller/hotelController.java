package com.controller;

import java.util.*;
import com.model.*;
import com.dao.*;
import com.service.*;
import java.sql.*;
import java.sql.Date;

public class hotelController {
	public static void main(String args[]) throws SQLException {
		Scanner sc = new Scanner(System.in);

		// Service layer objects
		guestService guestService = new guestService();
		roomService roomService = new roomService();
		bookingService bookingService = new bookingService();

		while (true) {
			System.out.println("\n====== Hotel Management System (ROOMIO) ======");
			System.out.println("1. Guest Operations ");
			System.out.println("2. Room Operations");
			System.out.println("3. Booking And Payment Operations ");
			System.out.println("4. Exit");
			System.out.print("Enter your choice: ");
			int choice = sc.nextInt();
			sc.nextLine();

			if (choice == 1) {
				System.out.println("\n------ Guest Operations ------");
				System.out.println("1. Add Guest");
				System.out.println("2. View All Guests");
				System.out.println("3. Update Guest");
				System.out.println("4. Delete Guest");
				System.out.println("5. Get Guest by ID");
				System.out.println("6. Get Guest by Name");
				System.out.println("7. Get Guest by Gender");
				System.out.println("8. Get Guest by Contact");
				System.out.println("9. Count Total Guests");
				System.out.print("Enter your guest choice: ");
				int gchoice = sc.nextInt();
				sc.nextLine();

				try {
					if (gchoice == 1) {
						System.out.print("Enter Name: ");
						String name = sc.nextLine();
						System.out.print("Enter Age: ");
						int age = sc.nextInt();
						sc.nextLine();
						System.out.print("Enter Gender: ");
						String gender = sc.nextLine();
						System.out.print("Enter Contact: ");
						String contact = sc.nextLine();

						guest g = new guest(name, age, gender, contact);
						guestService.addGuest(g);
						System.out.println("Guest Added Successfully");
					} else if (gchoice == 2) {
						List<guest> list = guestService.getAllGuest();
						for (guest g : list)
							System.out.println(g);
					} else if (gchoice == 3) {
						System.out.print("Enter Guest ID to Update: ");
						int id = sc.nextInt();
						sc.nextLine();
						System.out.print("Enter New Name: ");
						String name = sc.nextLine();
						System.out.print("Enter New Age: ");
						int age = sc.nextInt();
						sc.nextLine();
						System.out.print("Enter New Gender: ");
						String gender = sc.nextLine();
						System.out.print("Enter New Contact: ");
						String contact = sc.nextLine();

						guest g = new guest(id, name, age, gender, contact);
						guestService.updateGuest(g);
						System.out.println("Guest Updated Successfully");
					} else if (gchoice == 4) {
						System.out.print("Enter Guest ID to Delete: ");
						int id = sc.nextInt();
						sc.nextLine();
						guestService.deletGuest(id);
						System.out.println("Guest Deleted Successfully");
					} else if (gchoice == 5) {
						System.out.print("Enter Guest ID: ");
						int id = sc.nextInt();
						sc.nextLine();
						System.out.println(guestService.getGuestById(id));
					} else if (gchoice == 6) {
						System.out.print("Enter Guest Name: ");
						String name = sc.nextLine();
						System.out.println(guestService.getGuestByName(name));
					} else if (gchoice == 7) {
						System.out.print("Enter Gender: ");
						String gender = sc.nextLine();
						List<guest> list = guestService.getGuestByGender(gender);
						for (guest g : list)
							System.out.println(g);
					} else if (gchoice == 8) {
						System.out.print("Enter Contact: ");
						String contact = sc.nextLine();
						System.out.println(guestService.getGuestByContact(contact));
					} else if (gchoice == 9) {
						System.out.println("Total Guests: " + guestService.countGuest());
					}
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}

			else if (choice == 2) {
				System.out.println("\n--- Room Operations ---");
				System.out.println("1. Add Room");
				System.out.println("2. View All Rooms");
				System.out.println("3. Update Room");
				System.out.println("4. Delete Room");
				System.out.println("5. Get Room by ID");
				System.out.println("6. Get Rooms by Type");
				System.out.println("7. View Available Rooms");
				System.out.print("Enter your room choice: ");
				int rchoice = sc.nextInt();
				sc.nextLine();

				try {
					if (rchoice == 1) {
						System.out.print("Enter Room Type: ");
						String type = sc.nextLine();
						System.out.print("Enter Room Price: ");
						double price = sc.nextDouble();
						sc.nextLine();
						System.out.print("Is Available (true/false): ");
						boolean available = sc.nextBoolean();

						room r = new room(type, price, available);
						roomService.addRoom(r);
						System.out.println("Room Added Successfully");
					} else if (rchoice == 2) {
						List<room> list = roomService.getAllRooms();
						for (room r : list)
							System.out.println(r);
					} else if (rchoice == 3) {
						System.out.print("Enter Room ID: ");
						int id = sc.nextInt();
						sc.nextLine();
						System.out.print("Enter Type: ");
						String type = sc.nextLine();
						System.out.print("Enter Price: ");
						double price = sc.nextDouble();
						sc.nextLine();
						System.out.print("Is Available (true/false): ");
						boolean available = sc.nextBoolean();

						room r = new room(id, type, price, available);
						roomService.updateRoom(r);
						System.out.println("Room Updated");
					} else if (rchoice == 4) {
						System.out.print("Enter Room ID: ");
						int id = sc.nextInt();
						roomService.deleteRoom(id);
						System.out.println("Room Deleted");
					} else if (rchoice == 5) {
						System.out.print("Enter Room ID: ");
						int id = sc.nextInt();
						System.out.println(roomService.getRoomByID(id));
					} else if (rchoice == 6) {
						System.out.print("Enter Room Type: ");
						String type = sc.nextLine();
						List<room> list = roomService.getRoomByRoomType(type);
						for (room r : list)
							System.out.println(r);
					} else if (rchoice == 7) {
						List<room> list = roomService.getAvailableRoom();
						for (room r : list)
							System.out.println(r);
					}
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}

			else if (choice == 3) {
				System.out.println("\n------ Booking & Payment Operations ------");
				System.out.println("1. Book Room");
				System.out.println("2. View All Bookings");
				System.out.println("3. Update Booking");
				System.out.println("4. Cancel Booking");
				System.out.println("5. View Booking by ID");
				System.out.println("6. View Booking by Guest ID");
				System.out.println("7. View Booking with Details");
				System.out.println("8. Update Payment Status");
//				System.out.println("9. View Pending Payments");
				System.out.println("9. View Total Revenue");
				System.out.print("Enter your booking choice: ");
				int bchoice = sc.nextInt();
				sc.nextLine();

				try {
					if (bchoice == 1) {
						System.out.print("Enter Guest ID: ");
						int guestId = sc.nextInt();
						System.out.print("Enter Room ID: ");
						int roomId = sc.nextInt();
						sc.nextLine();

						System.out.print("Enter Check-In Date (yyyy-mm-dd): ");
						Date checkin = Date.valueOf(sc.nextLine());
						System.out.print("Enter Check-Out Date (yyyy-mm-dd): ");
						Date checkout = Date.valueOf(sc.nextLine());
						System.out.print("Enter Payment Mode:(UPI/CARD/CASH/NET BANKING) ");
						String mode = sc.nextLine();
						System.out.print("Enter Payment Status:(PAID/UNPAID) ");
						String status = sc.nextLine();

						System.out.print("Enter Total Amount: ");
						double amount = sc.nextDouble();

						booking b = new booking(guestId, roomId, checkin, checkout, mode, status, amount);
						bookingService.addBooking(b);
						System.out.println("Room Booked Successfully");
					} else if (bchoice == 2) {
						List<booking> list = bookingService.getAllBookings();
						for (booking b : list)
							System.out.println(b);
					} else if (bchoice == 3) {
						System.out.print("Enter Booking ID: ");
						int id = sc.nextInt();
						System.out.print("Enter New Guest ID: ");
						int guestId = sc.nextInt();
						System.out.print("Enter New Room ID: ");
						int roomId = sc.nextInt();
						sc.nextLine();
						System.out.print("Enter Check-In Date (yyyy-mm-dd): ");
						Date in = Date.valueOf(sc.nextLine());
						System.out.print("Enter Check-Out Date (yyyy-mm-dd): ");
						Date out = Date.valueOf(sc.nextLine());
						System.out.print("Enter Payment Mode: ");
						String mode = sc.nextLine();
						System.out.print("Enter Payment Status: ");
						String status = sc.nextLine();
						System.out.print("Enter Amount: ");
						double amt = sc.nextDouble();

						booking b = new booking(id, guestId, roomId, in, out, mode, status, amt);
						bookingService.updateBooking(b);
						System.out.println("Booking Updated");
					} else if (bchoice == 4) {
						System.out.print("Enter Booking ID: ");
						int id = sc.nextInt();
						bookingService.deleteBooking(id);
						System.out.println("Booking Cancelled");
					} else if (bchoice == 5) {
						System.out.print("Enter Booking ID: ");
						int id = sc.nextInt();
						System.out.println(bookingService.getBookingById(id));
					} else if (bchoice == 6) {
						System.out.print("Enter Guest ID: ");
						int gid = sc.nextInt();
						List<booking> list = bookingService.getBookingByGuestId(gid);
						for (booking b : list)
							System.out.println(b);
					} else if (bchoice == 7) {
						bookingService.viewBookingDetailsWithJoin();
					} else if (bchoice == 8) {
						System.out.print("Enter Booking ID: ");
						int id = sc.nextInt();
						sc.nextLine();
						System.out.print("Enter New Payment Status: ");
						String status = sc.nextLine();
						bookingService.updatePaymentStatus(id, status);
						System.out.println("Payment Status Updated");
					} 
//					else if (bchoice == 9) {
//						List<booking> list = bookingService.getUnpaidBookings();
//						for (booking b : list)
//							System.out.println(b);
//					} 
					else if (bchoice == 9) {
						System.out.println("Total Revenue: " + bookingService.calculateTotalRevenue());
					}
				} catch (SQLException e) {
					System.out.println("Error: " + e.getMessage());
				}
			}

			else if (choice == 4) {
				System.out.println("Exiting... Thank you!");
				break;
			}
		}
		sc.close();
	}
}
