package cst135n.milestone.contactbook;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Scanner;

public class AddressBook {

	Scanner sc = new Scanner(System.in);

	public ArrayList<BaseContact> bc = new ArrayList<>();

	static final String DB_URL = "jdbc:mysql://127.0.0.1/greendragon";
	static final String USER = "root";
	static final String PASS = "1mP0$$1bl3";

	static Connection connection;

	AddressBook() {
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
			connection = DriverManager.getConnection(DB_URL, USER, PASS);

		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public void displayMenu() {
		menu: do {
			System.out.println("*******************");
			System.out.println("** CONTACT MENU ***");
			System.out.println("*******************");
			System.out.println("1] ADD PERSONAL CONTACT");
			System.out.println("2] ADD BUSINESS CONTACT");
			System.out.println("3] REMOVE CONTACT");
			System.out.println("4] SEARCH CONTACT");
			System.out.println("5] DISPLAY CONTACT");
			System.out.println("6] SORT CONTACT");
			System.out.println("7] LIST BUSINESS CONTACT");
			System.out.println("8] UPDATE USER PASSWORD CONTACT");
			System.out.println("9] EDIT CONTACT");
			System.out.println("10} REPORT");
			System.out.println("0] EXIT");

			System.out.println("Select your option");
			switch (Integer.parseInt(sc.nextLine())) {
			case 1:
				addPersonContact();
				break;
			case 2:
				addBusinessContact();
				break;
			case 3:
				removeContact();
				break;
			case 4:
				searchContact();
				break;
			case 5:
				displayContact();
				break;
			case 6:
				sortContact();
				break;
			case 7:
				editContact();
				break;
			case 8:
				listBusiness();
				break;
			case 9:
				updateUserPw();
				break;
			case 10:
				report();
				break;
			default:
				System.out.println("EXITING MENU");
				break menu;

			}

		} while (true);

	}

	private void addPersonContact() {
		ArrayList<Photo> photo = new ArrayList();

		// will count and numbe photos
		int countPhotoID = 0;

		// will allow user to add addtional photos
		boolean addAnother = true;

		System.out.println("****************************");
		System.out.println("*** ADD PERSONAL CONTACT ***");
		System.out.println("****************************");
		try {
			// System.out.println("Select: " + p + " Personal or " + b + " Business");
			// if (sc.nextLine().toUpperCase().equals(p)) {
			System.out.println("Contact Name : ");
			String name = sc.nextLine();
			System.out.println("Contact Number : ");
			long number = Long.parseLong(sc.nextLine());
			System.out.println("Contact Phone Type : ");
			String phone = sc.nextLine();
			System.out.println("Contact Birthday : ");
			System.out.println("Format : YYYY-MM-DD ");
			String date = sc.nextLine();
			LocalDate dob = LocalDate.parse(date);
			System.out.println("Contact Description : ");
			String description = sc.nextLine();
			System.out.println("Contact List : ");
			String list = sc.nextLine();
			System.out.println("Contact Relative : ");
			String relative = sc.nextLine();

			do {
				System.out.println("+++++++++++++++++++");
				System.out.println("+++Contact Photo+++");
				System.out.println("+++++++++++++++++++");
				System.out.println("Contact Photo ID : " + ++countPhotoID);
				System.out.println("Format : YYYY-MM-DD ");
				String date2 = sc.nextLine();
				LocalDate dop = LocalDate.parse(date2);
				System.out.println("Contact Photo Description : ");
				String notes = sc.nextLine();
				photo.add(new Photo(countPhotoID, dop, notes));
				System.out.println("Add another photo? (Y/N)");
				String ans = sc.nextLine().toUpperCase();
				if (ans.equals("N")) {
					addAnother = false;
				} else {
					addAnother = true;
				}

			} while (addAnother == true);

			System.out.println("++++++++++++++++++++++");
			System.out.println("+++Contact Location+++");
			System.out.println("++++++++++++++++++++++");

			System.out.println("Contact Street : ");
			String street = sc.nextLine();
			System.out.println("Contact City : ");
			String city = sc.nextLine();
			System.out.println("Contact State : ");
			String state = sc.nextLine();

			bc.add(new PersonContact(number, name, phone, photo, new Location(street, city, state), dob, description,
					list, relative));

		} catch (Exception e) {
			System.out.println("Invalid input. Try again.");
			addPersonContact();
		}

	}

	public void setBc(PersonContact p) {
		bc.add(p);

	}

	private void addBusinessContact() {
		ArrayList<Photo> photo = new ArrayList();
		// will count and numbe photos
		int countPhotoID = 0;

		// will allow user to add addtional photos
		boolean addAnother = true;

		try {
			String sql = "INSERT INTO contact (type, name, phone_type, phone_num, email, hours, website, description, photo_id, user_id) VALUES (?,?,?,?,?,?,?,?,?,?)";

			PreparedStatement stmt = connection.prepareStatement(sql);

			// if (sc.nextLine().toUpperCase().equals(b)) {
			System.out.println("Contact Type : ");
			String type = "B";
			System.out.println("Contact Name : ");
			String name = sc.nextLine();
			System.out.println("Contact Phone Type : ");
			String phone_type = sc.nextLine();
			System.out.println("Contact Phone Number : ");
			long phone_num = Long.parseLong(sc.nextLine());
			System.out.println("Contact Email : ");
			String email = sc.nextLine();
			System.out.println("Contact Hours : ");
			String hours = sc.nextLine();
			System.out.println("Contact Website : ");
			String website = sc.nextLine();
			System.out.println("Contact Description : ");
			String description = sc.nextLine();
			System.out.println("Contact Photo Id : ");
			int photo_id = Integer.parseInt(sc.nextLine());
			System.out.println("Contact User id : ");
			int user_id = Integer.parseInt(sc.nextLine());

			stmt.setString(1, type);
			stmt.setString(2, name);
			stmt.setString(3, phone_type);
			stmt.setLong(4, phone_num);
			stmt.setString(5, email);
			stmt.setString(6, hours);
			stmt.setString(7, website);
			stmt.setString(8, description);
			stmt.setInt(9, photo_id);
			stmt.setInt(10, user_id);

			stmt.execute();

//			do {
//				System.out.println("+++++++++++++++++++");
//				System.out.println("+++Contact Photo+++");
//				System.out.println("+++++++++++++++++++");
//				System.out.println("Contact Photo ID : " + ++countPhotoID);
//				System.out.println("Format : YYYY-MM-DD ");
//				String date2 = sc.nextLine();
//				LocalDate dop = LocalDate.parse(date2);
//				System.out.println("Contact Photo Description : ");
//				String notes = sc.nextLine();
//				photo.add(new Photo(countPhotoID, dop, notes));
//				System.out.println("Add another photo? (Y/N)");
//				String ans = sc.nextLine().toUpperCase();
//				if (ans.equals("N")) {
//					addAnother = false;
//				} else {
//					addAnother = true;
//				}
//
//			} while (addAnother == true);
//
//			System.out.println("++++++++++++++++++++++");
//			System.out.println("+++Contact Location+++");
//			System.out.println("++++++++++++++++++++++");
//			
//			System.out.println("Contact Street : ");
//			String street = sc.nextLine();
//			System.out.println("Contact City : ");
//			String city = sc.nextLine();
//			System.out.println("Contact State : ");
//			String state = sc.nextLine();
//
//			bc.add(new BusinessContact(number, name, phone, photo,
//					new Location(street, city, state), hours, website));

		} catch (SQLException e) {
			System.out.println("Invalid input. Try again.");
			addBusinessContact();
		}
	}

	public void removeContact() {
		boolean removeAnother = true;
		try {
			do {
				displayContact();
				System.out.println("**********************");
				System.out.println("*** REMOVE CONTACT ***");
				System.out.println("**********************");
				System.out.println("Which contact to remove : ");

				int contactid = Integer.parseInt(sc.nextLine()) - 1;
				System.out.println(bc.get(contactid) + "\n");
				bc.remove(contactid);

				System.out.println("Remove another contact? (Y/N)");
				String ans = sc.nextLine().toUpperCase();
				if (ans.equals("N")) {
					removeAnother = false;
				} else {
					removeAnother = true;
				}

			} while (removeAnother == true);
		} catch (Exception e) {
			System.out.println("Invalid input. Try again.");
			removeContact();
		}
	}

	public void searchContact() {
		System.out.println("**********************");
		System.out.println("*** SEARCH CONTACT ***");
		System.out.println("**********************");
		System.out.println("Which contact to search : ");

		String name = sc.nextLine();
		for (BaseContact c : bc) {
			if (c.getName().equals(name)) {
				System.out.println(c);
			}
		}
	}

	public void displayContact() {

		int counter = 1;
		System.out.println("***********************");
		System.out.println("*** DISPLAY CONTACT ***");
		System.out.println("***********************");

//		for(BaseContact contact: bc) {
//			System.out.println(counter++ + "|" + contact);
//		}

		try {

			String sql = "SELECT last_name, name FROM contact ORDER BY last_name DESC";

			Statement statement = connection.createStatement();
			ResultSet results = statement.executeQuery(sql);
			System.out.println("Contact");
			System.out.println("==========");
			while (results.next()) {
				String last = results.getString("last_name");
				String first = results.getString("name");
				System.out.println(last + ", " + first);
			}

		} catch (SQLException e) {

			e.printStackTrace();
		}

	}

	public void sortContact() {
		sort: do {
			System.out.println("****************");
			System.out.println("** SORT MENU ***");
			System.out.println("****************");
			System.out.println("1] NAME");
			System.out.println("2] RELATIVE");
			System.out.println("3] LOCATION");
			System.out.println("4] ?????");
			System.out.println("0] EXIT");

			System.out.println("Select your option");
			switch (Integer.parseInt(sc.nextLine())) {
			case 1:

				Collections.sort(bc, (b1, b2) -> b1.getName().compareTo(b2.getName()));
				for (BaseContact contact : bc) {
					System.out.println(contact);
				}
				break;
			case 2:
				System.out.println("Sort by Relative group");
				System.out.println(bc);

				break;
			case 3:
				System.out.println("Sort by Location group");
				System.out.println(bc);
				// Collections.sort(bc, (b1, b2) ->
				// b1.getLocation().compareTo(b2.getLocation()));
				break;
			case 4:
				System.out.println("Sort by ????? group");
				break;
			default:
				System.out.println("EXITING MENU");
				break sort;
			}
		} while (true);

	}

	public void editContact() {
		displayContact();
		System.out.println("********************");
		System.out.println("*** EDIT CONTACT ***");
		System.out.println("********************");
		System.out.println("Which contact to edit : ");

		int contactid = Integer.parseInt(sc.nextLine()) - 1;
		System.out.println("Editting contact now: ");
		System.out.println(bc.get(contactid));
		if (bc.get(contactid).getClass().getSimpleName().equals("PersonContact"))
			editPersonContact((PersonContact) bc.get(contactid));

		else
			editBusinessContact((BusinessContact) bc.get(contactid));
	}

	private void editPersonContact(PersonContact bc) {
		System.out.println("Editting Personal Contact: ");

		try {

			System.out.println("Contact Name : ");
			bc.setName(sc.nextLine());
			System.out.println("Contact Number : ");
			bc.setNumber(Integer.parseInt(sc.nextLine()));
			System.out.println("Contact Phone Type : ");
			bc.setPhone(sc.nextLine());
			System.out.println("Contact Birthday : ");
			System.out.println("Format : YYYY-MM-DD ");
			String date = sc.nextLine();
			bc.setDob(LocalDate.parse(date));
			System.out.println("Contact List : ");
			bc.setList(sc.nextLine());
			System.out.println("Contact Relative : ");
			bc.setRelative(sc.nextLine());

			System.out.println("++++++++++++++++++++++");
			System.out.println("+++Contact Location+++");
			System.out.println("++++++++++++++++++++++");

			System.out.println("Contact Street : ");
			String street = sc.nextLine();
			System.out.println("Contact City : ");
			String city = sc.nextLine();
			System.out.println("Contact State : ");
			String state = sc.nextLine();

			bc.setLocation(new Location(street, city, state));
		} catch (Exception e) {
			System.out.println("Invalid input. Try again.");
			addPersonContact();
		}
	}

	private void editBusinessContact(BusinessContact bc) {
		System.out.println("Editting Business Contact: ");

		try {

			System.out.println("Contact Name : ");
			bc.setName(sc.nextLine());
			System.out.println("Contact Number : ");
			bc.setNumber(Integer.parseInt(sc.nextLine()));
			System.out.println("Contact Phone Type : ");
			bc.setPhone(sc.nextLine());
			System.out.println("Contact Hours : ");
			bc.setHours(sc.nextLine());
			System.out.println("Contact Website : ");
			bc.setWebsite(sc.nextLine());

			System.out.println("++++++++++++++++++++++");
			System.out.println("+++Contact Location+++");
			System.out.println("++++++++++++++++++++++");

			System.out.println("Contact Street : ");
			String street = sc.nextLine();
			System.out.println("Contact City : ");
			String city = sc.nextLine();
			System.out.println("Contact State : ");
			String state = sc.nextLine();

			bc.setLocation(new Location(street, city, state));
		} catch (Exception e) {
			System.out.println("Invalid input. Try again.");
			addPersonContact();
		}

	}

	public void listBusiness() {

		int counter = 1;
		System.out.println("*********************");
		System.out.println("*** List Business ***");
		System.out.println("*********************");

//		for(BaseContact contact: bc) {
//			System.out.println(counter++ + "|" + contact);
//		}

		try {

			String sql = "SELECT contact_id, name FROM contact WHERE type = 'B'";

			Statement statement = connection.createStatement();
			ResultSet results = statement.executeQuery(sql);
			System.out.println("Business");
			System.out.println("==========");
			while (results.next()) {

				int id = results.getInt("contact_id");
				String first = results.getString("name");
				System.out.println(id + "]" + first);
			}

		} catch (SQLException e) {

			e.printStackTrace();
		}

	}

	public void updateUserPw() {

		int counter = 1;
		System.out.println("****************************");
		System.out.println("*** Update User Password ***");
		System.out.println("****************************");

//	for(BaseContact contact: bc) {
//		System.out.println(counter++ + "|" + contact);
//	}

		try {

			String sql = "SELECT user_id, user_name FROM user WHERE business_role = 'user'";

			Statement statement = connection.createStatement();
			ResultSet results = statement.executeQuery(sql);
			System.out.println("User Info");
			System.out.println("==========");
			while (results.next()) {

				int id = results.getInt("user_id");
				String user = results.getString("user_name");

				System.out.println(id + "]" + user);

			}
			

			System.out.println("Which User's Password to Update?");
			int opt = sc.nextInt();
			sc.nextLine();

			
			sql = "SELECT password FROM user WHERE user_id = ?";
			PreparedStatement stmt = connection.prepareStatement(sql);
			stmt.setInt(1, opt);
			results = stmt.executeQuery();
			while (results.next()) {

				// Allow the user to make changes
				System.out.println("Password [" + results.getString("password") + "]: ");
				String password = sc.nextLine();
				sc.nextLine();
				

				// Update the table
				sql = "UPDATE user SET password = ? WHERE user_id = ?";
				stmt = connection.prepareStatement(sql);
				stmt.setString(1, password);
				stmt.setInt(2, opt);
				stmt.execute();
			}

		} catch (SQLException e) {

			e.printStackTrace();
		}

	}
	
	public void report () {
	System.out.println("**************");
	System.out.println("*** REPORT ***");
	System.out.println("**************");

//	for(BaseContact contact: bc) {
//		System.out.println(counter++ + "|" + contact);
//	}

	try {

		String sql = "SELECT user_id, user_name, business_role FROM user ORDER BY user_id DESC";

		Statement statement = connection.createStatement();
		ResultSet results = statement.executeQuery(sql);
		System.out.println("Report");
		System.out.println("==========");
		while (results.next()) {
			int id = results.getInt("user_id");
			String first = results.getString("user_name");
			String role = results.getString("business_role");
			System.out.println(id + "] " + first + ", " + role);
		}

	} catch (SQLException e) {

		e.printStackTrace();
	}

}

}
