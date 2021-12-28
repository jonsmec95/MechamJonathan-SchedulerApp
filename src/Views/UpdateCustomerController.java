package Views;

import Model.Country;
import Model.Customer;
import Model.FirstLevelDivision;
import Utilities.DBQuery;
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
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.Locale;
import java.util.Optional;
import java.util.ResourceBundle;

import static Views.LoginScreenController.rb;

public class UpdateCustomerController implements Initializable {
    @FXML private TextField customerNameTF;
    @FXML private TextField customerAddressTF;
    @FXML private TextField customerPostalTF;
    @FXML private TextField customerPhoneTF;
    @FXML private TextField customerIDTF;
    @FXML private ComboBox<FirstLevelDivision> firstLevelDivCB;
    @FXML private ComboBox<Country> countryCB;

    @FXML private Label customerNameLabel;
    @FXML private Label customerAddressLabel;
    @FXML private Label customerPostalLabel;
    @FXML private Label customerPhoneLabel;
    @FXML private Label customerIDLabel;
    @FXML private Label customerCountryLabel;
    @FXML private Label customerFLDLabel;
    @FXML private Label updateCustomerTitleLabel;
    @FXML private Button saveButton;
    @FXML private Button cancelButton;

    private Country selectedCountry;
    private FirstLevelDivision selectedFLD;

    private ObservableList<Country> countries = DBQuery.getAllCountries();
    private ObservableList<FirstLevelDivision> divisions = DBQuery.getAllDivisions();

    @FXML void saveButtonPushed(ActionEvent event) throws IOException {
        if (customerNameTF.getText().isEmpty() || customerAddressTF.getText().isEmpty() || customerPostalTF.getText().isEmpty() || customerPhoneTF.getText().isEmpty()) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setContentText("Please fill out all fields.");
            alert.showAndWait();
        }
        else {

            selectedCountry = countryCB.getSelectionModel().getSelectedItem();
            selectedFLD = firstLevelDivCB.getSelectionModel().getSelectedItem();
            String currentUser = LoginScreenController.currentUser;

            DBQuery.updateCustomer(Integer.parseInt(customerIDTF.getText()), customerNameTF.getText(), customerAddressTF.getText(), customerPostalTF.getText(), customerPhoneTF.getText(), selectedFLD.getDivisionID(), currentUser);

            Parent parent = FXMLLoader.load(getClass().getResource("MainCustomer.fxml"));
            Scene scene = new Scene(parent);
            Stage window = (Stage)((Node)event.getSource()).getScene().getWindow();
            window.setScene(scene);
            window.show();
        }
    }


    @FXML void cancelButtonPushed(ActionEvent event) throws IOException {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setHeaderText("Confirm Cancel?");
        alert.setContentText("Press OK to cancel updating customer");
        alert.showAndWait();

        if (alert.getResult() == ButtonType.OK) {
            //back to main screen
            Parent addPartParent = FXMLLoader.load(getClass().getResource("MainCustomer.fxml"));
            Scene addPartScene = new Scene(addPartParent);
            Stage window = (Stage)((Node)event.getSource()).getScene().getWindow();
            window.setScene(addPartScene);
            window.show();
        }
        else {
            alert.close();
        }
    }

    @FXML void countryCBChanged(){
        selectedCountry = countryCB.getSelectionModel().getSelectedItem();
        firstLevelDivCB.setItems(getSelectedCountryDiv(selectedCountry,divisions));
        firstLevelDivCB.setValue(getSelectedCountryDiv(selectedCountry,divisions).get(0));
    }

    //gets the selected country's divisions to display
    public ObservableList<FirstLevelDivision> getSelectedCountryDiv(Country selectedCountry, ObservableList<FirstLevelDivision> divisionsList) {
        ObservableList<FirstLevelDivision> selectedCountryDiv = FXCollections.observableArrayList();

        try {
            for (int i = 1; i < divisionsList.size(); i++) {
                if (divisionsList.get(i).getCountryId() == selectedCountry.getCountryID()) {
                    selectedCountryDiv.add(divisionsList.get(i));
                }
            }
        } catch (NullPointerException e){ }

        return selectedCountryDiv;
    }


    public  void getCustomerData(Customer customer){
        customerNameTF.setText(customer.getCustomerName());
        customerAddressTF.setText(customer.getAddress());
        customerPostalTF.setText(customer.getPostalCode());
        customerPhoneTF.setText(customer.getPhoneNumber());
        customerIDTF.setText("" + customer.getCustomerID());


        for (int i = 0; i < divisions.size(); i++) {
            if (divisions.get(i).getDivisionID() == customer.getFirstLevelDiv()){
                firstLevelDivCB.setValue(divisions.get(i));
                FirstLevelDivision customerDiv = divisions.get(i);

                for ( int j = 0; j < countries.size(); j++) {
                    if (countries.get(j).getCountryID() == customerDiv.countryId){
                        countryCB.setValue(countries.get(j));
                    }
                }
            }
        }
    }

    public void setLanguage(){

        if (Locale.getDefault().getLanguage().equals("fr") || Locale.getDefault().getLanguage().equals("en")) {
            updateCustomerTitleLabel.setText(rb.getString(updateCustomerTitleLabel.getId()));

            //table
            customerNameLabel.setText(rb.getString(customerNameLabel.getId()) + ":");
            customerAddressLabel.setText(rb.getString(customerAddressLabel.getId()) + ":");
            customerPostalLabel.setText(rb.getString(customerPostalLabel.getId()) + ":");
            customerPhoneLabel.setText(rb.getString(customerPhoneLabel.getId()) + ":");
            customerIDLabel.setText(rb.getString(customerIDLabel.getId()) + ":");
            customerCountryLabel.setText(rb.getString(customerCountryLabel.getId()) + ":");
            customerFLDLabel.setText(rb.getString(customerFLDLabel.getId()) + ":");

            customerNameTF.setPromptText(rb.getString(customerNameLabel.getId()));
            customerAddressTF.setPromptText(rb.getString(customerAddressLabel.getId()));
            customerPostalTF.setPromptText(rb.getString(customerPostalLabel.getId()));
            customerPhoneTF.setPromptText(rb.getString(customerPhoneLabel.getId()));
            customerIDTF.setPromptText(rb.getString(customerIDLabel.getId()));
            countryCB.setPromptText(rb.getString(customerCountryLabel.getId()));
            firstLevelDivCB.setPromptText(rb.getString(customerFLDLabel.getId()));

            saveButton.setText(rb.getString(saveButton.getId()));
            cancelButton.setText(rb.getString(cancelButton.getId()));

        }
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        setLanguage();
        countryCB.setItems(countries);
        selectedCountry = countryCB.getSelectionModel().getSelectedItem();

    }
}
