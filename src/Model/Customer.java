package Model;

import Utilities.DBQuery;
import javafx.collections.ObservableList;

public class Customer {

    public String customerName;
    public String address;
    public String postalCode;
    public String phoneNumber;
    public int customerID;
    public int firstLevelDivID;
    public String country;

    public static int newCustomerId;


    //took out country for testing
    public Customer(int customerID, String customerName, String address, String postalCode, String phoneNumber, int firstLevelDivID ){
        this.customerID = customerID;
        this.customerName = customerName;
        this.address = address;
        this.postalCode = postalCode;
        this.phoneNumber = phoneNumber;
        this.firstLevelDivID= firstLevelDivID;
        this.country = country;
    }

    //getters
    public String getCustomerName() {
        return this.customerName;
    }
    public String getAddress() {
        return this.address;
    }
    public String getPostalCode() {
        return this.postalCode;
    }
    public String getPhoneNumber() {
        return this.phoneNumber;
    }
    public int getCustomerID() {
        return this.customerID;
    }
    public int getFirstLevelDiv() {
        return this.firstLevelDivID;
    }
    public String getCountry() {
        return this.country;
    }
    public int getNewCustomerId(){return newCustomerId;}

    //setters
    public void setCustomerName(String name){
        this.customerName = name;
    }
    public void setAddress(String address){
        this.address = address;
    }
    public void setPostalCode(String postalCode){
        this.postalCode = postalCode;
    }
    public void setPhoneNumber(String phone){
        this.phoneNumber = phone;
    }
    public void setCustomerID(int ID){
        this.customerID = ID;
    }
    public void setFirstLevelDivID(int firstLevelDiv){
        this.firstLevelDivID = firstLevelDiv;
    }
    public void setCountry(String country){
        this.country = country;
    }


    public static int generateNextId(){
        ObservableList<Customer> allCustomers = DBQuery.getAllCustomers();

        for (int i = 0; i < allCustomers.size(); i++) {
            newCustomerId = allCustomers.get(i).getCustomerID();
        }


        return newCustomerId += 1;
    }

    //for the combo box
    @Override
    public String toString(){
        return (getCustomerName());
    }
}
