package Utilities;

import Model.*;
import javafx.beans.Observable;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import Utilities.DBConnect.*;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.TimeZone;

public class DBQuery {


    public static ObservableList<Customer> getAllCustomers() {

        ObservableList<Customer> customersDataList = FXCollections.observableArrayList();

        try {
            PreparedStatement prepState;
            Connection conn = DBConnect.conn;

            String selectStatement = "SELECT * FROM customers;";

            prepState = conn.prepareStatement(selectStatement);
            ResultSet rs = prepState.executeQuery();

            while (rs.next()) {
                int Customer_ID = rs.getInt("Customer_ID");
                String Customer_Name = rs.getString("Customer_Name");
                String Address = rs.getString("Address");
                String Postal_Code = rs.getString("Postal_Code");
                String Phone = rs.getString("Phone");
                int Division_ID = rs.getInt("Division_ID");

                Customer newCustomer = new Customer(Customer_ID, Customer_Name, Address, Postal_Code, Phone, Division_ID );
                customersDataList.add(newCustomer);

            }

        } catch (Exception e){
            System.out.println(e.getMessage());
        }
        return customersDataList;

    }

    public static void addNewCustomer(int customerID, String customerName, String address, String postalCode, String phoneNumber, String createdBy,
                                      int firstLevelDivID, String lastUpdateBy){

        try {
            PreparedStatement prepState;
            Connection conn = DBConnect.conn;
            String insertStatement = "INSERT INTO customers (Customer_ID, Customer_Name, Address, Postal_Code, " +
                    "Phone, Create_Date, Created_By, Last_Update, Last_Updated_By, Division_ID) VALUES (?, ?, ?, ?, ?, now(), ?, now(), ?, ?)";

            prepState = conn.prepareStatement(insertStatement);

            prepState.setInt(1, customerID);
            prepState.setString(2, customerName);
            prepState.setString(3, address);
            prepState.setString(4, postalCode);
            prepState.setString(5, phoneNumber);
            prepState.setString(6, createdBy);
            prepState.setString(7, lastUpdateBy);
            prepState.setInt(8, firstLevelDivID);

            prepState.executeUpdate();

        } catch(Exception e) {
            System.out.println(e.getMessage());
        }
    }

    public static void removeCustomer(int customerID){

        try {
            PreparedStatement prepState;
            Connection conn = DBConnect.conn;
            String deleteStatement = "DELETE FROM customers WHERE Customer_ID = ?";
            prepState = conn.prepareStatement(deleteStatement);
            prepState.setInt(1, customerID);

            prepState.executeUpdate();

        } catch (Exception e){
            System.out.println(e.getMessage());
        }

    }

    public static void updateCustomer(int customerID, String customerName, String address, String postalCode, String phoneNumber,
                                      int firstLevelDivID, String lastUpdateBy ){

        try{
            PreparedStatement prepState;
            Connection conn = DBConnect.conn;
            String updateStatement = "UPDATE customers SET Customer_Name = ?, Address = ?, Postal_Code = ?, " +
                                       "Phone = ?, Last_Update = now(), Last_Updated_By = ?, Division_ID = ? " +
                                        "WHERE Customer_ID = ?";

            prepState = conn.prepareStatement(updateStatement);

            prepState.setString(1, customerName);
            prepState.setString(2, address);
            prepState.setString(3, postalCode);
            prepState.setString(4, phoneNumber);
            prepState.setString(5, lastUpdateBy);
            prepState.setInt(6, firstLevelDivID);
            prepState.setInt(7, customerID);

            prepState.executeUpdate();



        } catch (Exception e){
                System.out.println(e.getMessage());
        }
    }

    public static ObservableList<Country> getAllCountries(){
        ObservableList<Country> countriesDataList = FXCollections.observableArrayList();
        try {
            PreparedStatement prepState;
            Connection conn = DBConnect.conn;
            String selectStatement = "SELECT * FROM countries;";
            prepState = conn.prepareStatement(selectStatement);
            ResultSet rs = prepState.executeQuery();

            while (rs.next()) {
                int Country_ID = rs.getInt("Country_ID");
                String Country = rs.getString("Country");
                String Create_Date = rs.getString("Create_Date");
                String Created_By = rs.getString("Created_By");
                String Last_Update = rs.getString("Last_Update");
                String Last_Updated_By = rs.getString("Last_Updated_By");

                Country newCountry = new Country(Country_ID, Country, Create_Date, Created_By, Last_Update, Last_Updated_By);
                countriesDataList.add(newCountry);
            }

        } catch (Exception e){
            System.out.println(e.getMessage());
        }
        return countriesDataList;
    }

    public static ObservableList<FirstLevelDivision> getAllDivisions(){
        ObservableList<FirstLevelDivision> countriesDataList = FXCollections.observableArrayList();
        try {
            PreparedStatement prepState;
            Connection conn = DBConnect.conn;
            String selectStatement = "SELECT * FROM first_level_divisions;";
            prepState = conn.prepareStatement(selectStatement);
            ResultSet rs = prepState.executeQuery();

            while (rs.next()) {
                int Division_ID = rs.getInt("Division_ID");
                String Division = rs.getString("Division");
                String Create_Date = rs.getString("Create_Date");
                String Created_By = rs.getString("Created_By");
                String Last_Update = rs.getString("Last_Update");
                String Last_Updated_By = rs.getString("Last_Updated_By");
                int COUNTRY_ID = rs.getInt("COUNTRY_ID");

                FirstLevelDivision division = new FirstLevelDivision(Division_ID, Division, Create_Date, Created_By, Last_Update, Last_Updated_By, COUNTRY_ID);
                countriesDataList.add(division);
            }

        } catch (Exception e){
            System.out.println(e.getMessage());
        }
        return countriesDataList;
    }

    public static ObservableList<Contact> getAllContacts() {

        ObservableList<Contact> contactDataList = FXCollections.observableArrayList();

        try {
            PreparedStatement prepState;
            Connection conn = DBConnect.conn;

            String selectStatement = "SELECT * FROM contacts;";

            prepState = conn.prepareStatement(selectStatement);
            ResultSet rs = prepState.executeQuery();

            while (rs.next()) {
                int Contact_ID = rs.getInt("Contact_ID");
                String Contact_Name = rs.getString("Contact_Name");
                String Email = rs.getString("Email");


                Contact addContact = new Contact(Contact_ID, Contact_Name, Email);
                contactDataList.add(addContact);

            }

        } catch (Exception e){
            System.out.println(e.getMessage());
        }
        return contactDataList;

    }

    public static ObservableList<User> getAllUsers() {

        ObservableList<User> userDataList = FXCollections.observableArrayList();

        try {
            PreparedStatement prepState;
            Connection conn = DBConnect.conn;

            String selectStatement = "SELECT * FROM users;";

            prepState = conn.prepareStatement(selectStatement);
            ResultSet rs = prepState.executeQuery();

            while (rs.next()) {
                int User_ID = rs.getInt("User_ID");
                String User_Name = rs.getString("User_Name");
                String Password = rs.getString("Password");
                String Create_Date = rs.getString("Create_Date");
                String Created_By = rs.getString("Created_By");
                String Last_Update = rs.getString("Last_Update");
                String Last_Updated_By = rs.getString("Last_Updated_By");


                User addUser = new User(User_ID, User_Name, Password, Create_Date, Created_By, Last_Update, Last_Updated_By);
                userDataList.add(addUser);

            }

        } catch (Exception e){
            System.out.println(e.getMessage());
        }
        return userDataList;

    }

    public static ObservableList<Appointment> getAllAppointments() {

        ObservableList<Appointment> appointmentDataList = FXCollections.observableArrayList();

        try {
            PreparedStatement prepState;
            Connection conn = DBConnect.conn;

            String selectStatement = "SELECT * FROM appointments;";

            prepState = conn.prepareStatement(selectStatement);
            ResultSet rs = prepState.executeQuery();

            while (rs.next()) {
                ZoneId zoneId = ZoneId.of(TimeZone.getDefault().getID());
                DateTimeFormatter localFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss").withZone(zoneId);

                LocalDate startDate = LocalDate.parse(rs.getString("Start").substring(0,10));
                LocalTime startTime = LocalTime.parse(rs.getString("Start").substring(11, 19));
                LocalDate endDate = LocalDate.parse(rs.getString("End").substring(0, 10));
                LocalTime endTime = LocalTime.parse(rs.getString("End").substring(11, 19));

                ZonedDateTime startDateUTC = ZonedDateTime.of(startDate, startTime, ZoneId.of("UTC"));
                ZonedDateTime endDateUTC = ZonedDateTime.of(endDate, endTime, ZoneId.of("UTC"));

                String startDateLocal = localFormatter.format(startDateUTC);
                String endDateLocal = localFormatter.format(endDateUTC);




                int Appointment_ID = rs.getInt("Appointment_ID");
                String Title = rs.getString("Title");
                String Description = rs.getString("Description");
                String Location = rs.getString("Location");
                String Type = rs.getString("Type");
                String Create_Date = rs.getString("Create_Date");
                String Created_By = rs.getString("Created_By");
                String Last_Update = rs.getString("Last_Update");
                String Last_Updated_By = rs.getString("Last_Updated_By");
                int Customer_ID = rs.getInt("Customer_ID");
                int User_ID = rs.getInt("User_ID");
                int Contact_ID = rs.getInt("Contact_ID");


                Appointment addAppointment = new Appointment(Appointment_ID, Title, Description, Location, Type, startDateLocal, endDateLocal, Create_Date, Created_By, Last_Update,
                        Last_Updated_By, Customer_ID, User_ID, Contact_ID);
                appointmentDataList.add(addAppointment);

            }

        } catch (Exception e){
            System.out.println(e.getMessage());
        }
        return appointmentDataList;

    }

    public static void addNewAppointment(int appId, String appTitle, String appDescription, String location, String appType, String appStart, String appEnd,
                                         String appCreatedBy, String appLastUpdatedBy, int appCustId, int appUserId, int appContactId){

        try {
            PreparedStatement prepState;
            Connection conn = DBConnect.conn;
            String insertStatement = "INSERT INTO appointments (Appointment_ID, Title, Description, Location, Type, " +
                    "Start, End, Create_Date, Created_By, Last_Update, Last_Updated_By, Customer_ID, User_ID, Contact_ID) " +
                    "VALUES (?, ?, ?, ?, ?, ?, ?, now(), ?, now(), ?, ?, ?, ?)";

            prepState = conn.prepareStatement(insertStatement);

            prepState.setInt(1, appId);
            prepState.setString(2, appTitle);
            prepState.setString(3, appDescription);
            prepState.setString(4, location);
            prepState.setString(5, appType);
            prepState.setString(6, appStart);
            prepState.setString(7, appEnd);
            prepState.setString(8, appCreatedBy);
            prepState.setString(9, appLastUpdatedBy);
            prepState.setInt(10, appCustId);
            prepState.setInt(11, appUserId);
            prepState.setInt(12, appContactId);


            prepState.executeUpdate();

        } catch(Exception e) {
            System.out.println(e.getMessage());
        }
    }

    public static void updateAppointment(int appId, String appTitle, String appDescription, String location, String appType, String appStart, String appEnd,
                                          String appLastUpdatedBy, int appCustId, int appUserId, int appContactId){

        try {
            PreparedStatement prepState;
            Connection conn = DBConnect.conn;
            String updateStatement =
                    "UPDATE appointments SET Customer_ID = ?," +
                    "User_ID = ?," +
                    "Contact_ID = ?, " +
                    "Title = ?, Description = ?, Location = ?, Type = ?, Start = ?, End = ?, Last_Update = now(), Last_Updated_By = ?" +
                    "WHERE Appointment_ID = ?";

            prepState = conn.prepareStatement(updateStatement);

            prepState.setInt(1, appCustId);
            prepState.setInt(2, appUserId);
            prepState.setInt(3, appContactId);
            prepState.setString(4, appTitle);
            prepState.setString(5, appDescription);
            prepState.setString(6, location);
            prepState.setString(7, appType);
            prepState.setString(8, appStart);
            prepState.setString(9, appEnd);
            prepState.setString(10, appLastUpdatedBy);
            prepState.setInt(11, appId);

            prepState.executeUpdate();


        } catch (Exception e) {
            System.out.println(e.getMessage());
        }

    }

    public static void removeAppointment(int appointmentID){

        try {
            PreparedStatement prepState;
            Connection conn = DBConnect.conn;
            String deleteStatement = "DELETE FROM appointments WHERE Appointment_ID = ?";
            prepState = conn.prepareStatement(deleteStatement);
            prepState.setInt(1, appointmentID);

            prepState.executeUpdate();

        } catch (Exception e){
            System.out.println(e.getMessage());
        }

    }

}