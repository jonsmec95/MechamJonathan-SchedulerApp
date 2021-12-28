package Views;

import Model.Appointment;
import Model.Customer;
import Utilities.DBConnect;
import Utilities.DBQuery;
import javafx.beans.Observable;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.Locale;
import java.util.Optional;
import java.util.ResourceBundle;

import static Views.LoginScreenController.rb;

public class MainCustomerController implements Initializable  {
    //variables
    @FXML private TableView<Customer> customerTableView;
    @FXML private TableColumn<Customer, String> customerNameCol;
    @FXML private TableColumn<Customer, String> customerAddressCol;
    @FXML private TableColumn<Customer, String> customerPostalCol;
    @FXML private TableColumn<Customer, String> customerPhoneCol;
    @FXML private TableColumn<Customer, Integer> customerIDCol;
    @FXML private Button appointmentsButton;
    @FXML private Button customersButton;
    @FXML private Button reportsButton;
    @FXML private Button exitButton;
    @FXML private Button addButton;
    @FXML private Button deleteButton;
    @FXML private Button updateButton;
    @FXML private Label titleLabel;
    @FXML private Label customerTitleLabel;




    private ObservableList<Appointment> allAppointments = DBQuery.getAllAppointments();


    @FXML void addButtonPushed(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("AddCustomer.fxml"));
        Parent screen = loader.load();
        Scene scene = new Scene(screen);
        Stage stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        stage.setScene(scene);
        stage.show();
    }

    @FXML void updateButtonPushed(ActionEvent event) throws IOException {
        Customer customer = customerTableView.getSelectionModel().getSelectedItem();

        if (customer != null) {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("UpdateCustomer.fxml"));
            Parent screen = loader.load();

            UpdateCustomerController updateCustomerController = loader.getController();
            updateCustomerController.getCustomerData(customer);

            Scene scene = new Scene(screen);
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            stage.setScene(scene);
            stage.show();
        } else{
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setContentText(rb.getString("noCustSelected"));
            alert.showAndWait();
        }
    }

    @FXML void deleteButtonPushed(ActionEvent event) throws IOException {


        if (customerTableView.getSelectionModel().isEmpty()){
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setContentText(rb.getString("noCustSelected"));
            alert.showAndWait();
        } else {
            int customerToDelete = customerTableView.getSelectionModel().getSelectedItem().getCustomerID();

            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setHeaderText(rb.getString("deleteCustErrorTitle"));
            alert.setContentText(rb.getString("deleteCustError"));

            Optional<ButtonType> result = alert.showAndWait();
            if (result.get() == ButtonType.OK) {

                //lambda expression 2
                allAppointments.forEach((n) -> { if (n.getAppCustId() == customerToDelete) DBQuery.removeAppointment(n.appId);});
                DBQuery.removeCustomer(customerToDelete);

                ObservableList<Customer> updatedCustomers = DBQuery.getAllCustomers();
                customerTableView.setItems(updatedCustomers);
            }

        }
    }




    @FXML void appointmentsButtonPushed(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("MainAppointments.fxml"));
        Parent screen = loader.load();
        Scene scene = new Scene(screen);
        Stage stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        stage.setScene(scene);
        stage.show();
    }

    @FXML void reportsButtonPushed(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("MainReports.fxml"));
        Parent screen = loader.load();
        Scene scene = new Scene(screen);
        Stage stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        stage.setScene(scene);
        stage.show();
    }



    @FXML void exitButtonPushed(ActionEvent event) {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setHeaderText(rb.getString("exitProgramTitle"));
        alert.setContentText(rb.getString("exitProgram"));

        Optional<ButtonType> result = alert.showAndWait();
        if (result.get() == ButtonType.OK) {
            System.exit(0);
        }
    }

    public void setLanguage(){

        if (Locale.getDefault().getLanguage().equals("fr") || Locale.getDefault().getLanguage().equals("en")) {
            titleLabel.setText(rb.getString(titleLabel.getId()));
            customerTitleLabel.setText(rb.getString(customerTitleLabel.getId()));
            appointmentsButton.setText(rb.getString(appointmentsButton.getId()));
            customersButton.setText(rb.getString(customersButton.getId()));
            reportsButton.setText(rb.getString(reportsButton.getId()));
            exitButton.setText(rb.getString(exitButton.getId()));

            //table
            customerNameCol.setText(rb.getString(customerNameCol.getId()));
            customerAddressCol.setText(rb.getString(customerAddressCol.getId()));
            customerPhoneCol.setText(rb.getString(customerPhoneCol.getId()));
            customerPostalCol.setText(rb.getString(customerPostalCol.getId()));
            customerIDCol.setText(rb.getString(customerIDCol.getId()));


            addButton.setText(rb.getString(addButton.getId()));
            updateButton.setText(rb.getString(updateButton.getId()));
            deleteButton.setText(rb.getString(deleteButton.getId()));

        }
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        setLanguage();
        ObservableList<Customer> customerData = DBQuery.getAllCustomers();

        customerNameCol.setCellValueFactory(new PropertyValueFactory<>("customerName"));
        customerAddressCol.setCellValueFactory(new PropertyValueFactory<>("address"));
        customerPostalCol.setCellValueFactory(new PropertyValueFactory<>("postalCode"));
        customerPhoneCol.setCellValueFactory(new PropertyValueFactory<>("phoneNumber"));
        customerIDCol.setCellValueFactory(new PropertyValueFactory<>("customerID"));

        customerTableView.setItems(customerData);


    }
}
