package Views;

import Model.*;
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
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.sql.Timestamp;
import java.time.*;
import java.time.format.DateTimeFormatter;
import java.util.*;

import static Views.LoginScreenController.rb;

public class AddAppointmentController implements Initializable {
    @FXML private Label addAppTitleLabel;
    @FXML private Label addAppTitleLbl;
    @FXML private Label appIdLabel;
    @FXML private Label appDescriptionLabel;
    @FXML private Label appLocationLabel;
    @FXML private Label appContactLabel;
    @FXML private Label appTypeLabel;
    @FXML private Label appStartTimeLabel;
    @FXML private Label appEndTimeLabel;
    @FXML private Label appCustomerLabel;
    @FXML private Label appUserLabel;
    @FXML private Button saveButton;
    @FXML private Button cancelButton;



    @FXML private TextField appIDTF;
    @FXML private TextField appTitleTF;
    @FXML private TextField appDescriptionTF;
    @FXML private TextField appLocationTF;
    @FXML private TextField appTypeTF;
    @FXML private DatePicker startDP;
    @FXML private DatePicker endDP;

    @FXML private ComboBox<String> startTimeCB;
    @FXML private ComboBox<String> endTimeCB;
    @FXML private ComboBox<Contact> contactCB;
    @FXML private ComboBox<Customer> customerCB;
    @FXML private ComboBox<User> userCB;

    private ObservableList<Contact> allContacts = DBQuery.getAllContacts();
    private ObservableList<User> allUsers = DBQuery.getAllUsers();
    private ObservableList<Customer> allCustomers = DBQuery.getAllCustomers();

    @FXML void saveButtonPushed(ActionEvent event) throws IOException {
        Contact selectedContact = contactCB.getSelectionModel().getSelectedItem();
        User selectedUser = userCB.getSelectionModel().getSelectedItem();
        Customer selectedCustomer = customerCB.getSelectionModel().getSelectedItem();

        ZoneId localZoneId = ZoneId.of(TimeZone.getDefault().getID());
        LocalDate startDate = startDP.getValue();
        LocalDate endDate = endDP.getValue();
        LocalTime startTime = LocalTime.parse(startTimeCB.getSelectionModel().getSelectedItem());
        LocalTime endTime = LocalTime.parse(endTimeCB.getSelectionModel().getSelectedItem());

        //time formatters
        DateTimeFormatter UTCFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm").withZone(ZoneId.of("UTC"));
        DateTimeFormatter ESTFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm").withZone(ZoneId.of("America/New_York"));
        DateTimeFormatter LocalFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm").withZone(localZoneId);

        //local time conversions
        ZonedDateTime localStartDateTime = ZonedDateTime.of(startDate, startTime, localZoneId);
        ZonedDateTime localEndDateTime = ZonedDateTime.of(endDate, endTime, localZoneId);
        ZonedDateTime ESTStartDateTime = localStartDateTime.withZoneSameInstant(ZoneId.of("America/New_York"));
        ZonedDateTime ESTEndDateTime = localEndDateTime.withZoneSameInstant(ZoneId.of("America/New_York"));

        //local formatted times
        String UTCStartDateString = UTCFormatter.format(localStartDateTime);
        String UTCEndDateString = UTCFormatter.format(localEndDateTime);

        //variables to check logic
        Timestamp businessOpen = Timestamp.valueOf("2000-01-01 08:00:00");
        Timestamp businessClose = Timestamp.valueOf("2000-01-01 022:00:00");
        ZonedDateTime ESTBusinessOpen = businessOpen.toLocalDateTime().atZone(ZoneId.of("America/New_York"));
        ZonedDateTime localBusinessOpen = ESTBusinessOpen.withZoneSameInstant(localZoneId);
        ZonedDateTime ESTBusinessClose = businessClose.toLocalDateTime().atZone(ZoneId.of("America/New_York"));
        ZonedDateTime localBusinessClose = ESTBusinessClose.withZoneSameInstant(localZoneId);

        LocalTime localOpening = localBusinessOpen.toLocalTime();
        LocalTime localClosing = localBusinessClose.toLocalTime();



        int ESTStartTimeValue = ESTStartDateTime.toLocalTime().compareTo(LocalTime.parse("08:00"));
        int ESTEndTimeValue = ESTEndDateTime.toLocalTime().compareTo(LocalTime.parse("22:00"));
        int ESTStartAfterEnd = ESTEndDateTime.toLocalTime().compareTo(ESTStartDateTime.toLocalTime());
        int ESTStartDateAfterEnd = ESTEndDateTime.toLocalDate().compareTo(ESTStartDateTime.toLocalDate());

        //Checks for errors in logic and creates alert
        if (ESTStartTimeValue < 0 || localStartDateTime.getDayOfWeek() == DayOfWeek.SATURDAY || localStartDateTime.getDayOfWeek() == DayOfWeek.SUNDAY) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setContentText(rb.getString("outOfBusinessHours") + localOpening + " - " + localClosing + ")");
            alert.showAndWait();
        }
        else if (ESTEndTimeValue > 0 || localEndDateTime.getDayOfWeek() == DayOfWeek.SATURDAY || localEndDateTime.getDayOfWeek() == DayOfWeek.SUNDAY) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setContentText(rb.getString("outOfBusinessHours") + localOpening + " - " + localClosing + ")");
            alert.showAndWait();
        }
        else if (ESTStartAfterEnd <= 0 || ESTStartDateAfterEnd < 0 ) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setContentText(rb.getString("startAfterEnd"));
            alert.showAndWait();
        }
        else if (checkForOverlaps() == true) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setContentText(rb.getString("overlap") + selectedCustomer);
            alert.showAndWait();
        }
        else {
            DBQuery.addNewAppointment(Integer.parseInt(appIDTF.getText()), appTitleTF.getText(), appDescriptionTF.getText(), appLocationTF.getText(), appTypeTF.getText(), UTCStartDateString, UTCEndDateString,
                    LoginScreenController.currentUser, LoginScreenController.currentUser, selectedCustomer.getCustomerID(), selectedUser.getUserId(), selectedContact.getContactId());

            Parent addPartParent = FXMLLoader.load(getClass().getResource("MainAppointments.fxml"));
            Scene addPartScene = new Scene(addPartParent);
            Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();
            window.setScene(addPartScene);
            window.show();
        }
    }

    @FXML void cancelButtonPushed(ActionEvent event) throws IOException {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setHeaderText(rb.getString("addAptErrorTitle"));
        alert.setContentText(rb.getString("addAptError"));
        alert.showAndWait();

        if (alert.getResult() == ButtonType.OK) {
            //back to main screen
            Parent addPartParent = FXMLLoader.load(getClass().getResource("MainAppointments.fxml"));
            Scene addPartScene = new Scene(addPartParent);
            Stage window = (Stage)((Node)event.getSource()).getScene().getWindow();
            window.setScene(addPartScene);
            window.show();
        }
        else {
            alert.close();
        }
    }

    public boolean checkForOverlaps(){
        ObservableList<Appointment> allAppointments = DBQuery.getAllAppointments();
        Customer selectedCustomer = customerCB.getSelectionModel().getSelectedItem();

        LocalDate selectedStartDate = startDP.getValue();
        LocalDate selectedEndDate = endDP.getValue();
        LocalTime selectedStartTime = LocalTime.parse(startTimeCB.getSelectionModel().getSelectedItem());
        LocalTime selectedEndTime = LocalTime.parse(endTimeCB.getSelectionModel().getSelectedItem());

        for (int i = 0; i < allAppointments.size(); i++) {
            if (allAppointments.get(i).getAppCustId() == selectedCustomer.getCustomerID()) {

                DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
                Appointment appointment = allAppointments.get(i);

                LocalDateTime startDate = LocalDateTime.parse(appointment.getAppStart(), dateTimeFormatter);
                LocalDateTime endDate = LocalDateTime.parse(appointment.getAppEnd(), dateTimeFormatter);

                LocalDateTime selectedStart = LocalDateTime.of(selectedStartDate, selectedStartTime);
                LocalDateTime selectedEnd = LocalDateTime.of(selectedEndDate, selectedEndTime);

                int compareStartDateTimes = selectedStart.compareTo(startDate);
                int compareEndDateTimes = selectedEnd.compareTo(endDate);
                int compareStartEnd = selectedStart.compareTo(endDate);
                int compareEndStart = selectedEnd.compareTo(startDate);

                if (compareStartDateTimes >= 0 && compareStartEnd <= 0 || compareEndDateTimes <= 0 && compareEndStart >= 0) {
                    return true;
                }
            }
        }
        return false;
    }


    public ObservableList<String> getAppointmentTimes(){
        ObservableList<String> appointmentTimes = FXCollections.observableArrayList();

        //local opening hour
        Timestamp businessOpen = Timestamp.valueOf("2000-01-01 08:00:00");
        ZonedDateTime ESTBusinessOpen = businessOpen.toLocalDateTime().atZone(ZoneId.of("America/New_York"));
        ZonedDateTime localBusinessOpen = ESTBusinessOpen.withZoneSameInstant(ZoneId.of(TimeZone.getDefault().getID()));
        LocalTime localOpening = localBusinessOpen.toLocalTime();

        //local closing hour
        Timestamp businessClose = Timestamp.valueOf("2000-01-01 022:00:00");
        ZonedDateTime ESTBusinessClose = businessClose.toLocalDateTime().atZone(ZoneId.of("America/New_York"));
        ZonedDateTime localBusinessClose = ESTBusinessClose.withZoneSameInstant(ZoneId.of(TimeZone.getDefault().getID()));
        LocalTime localClosing = localBusinessClose.toLocalTime();


        LocalTime timeIncrement = localOpening;

        while (timeIncrement.minusHours(1).isBefore(localClosing.plusHours(1))) {
            appointmentTimes.add(String.valueOf(timeIncrement));
            LocalTime increment = timeIncrement.plusMinutes(30);
            timeIncrement = increment;
        }
        return  appointmentTimes;
    }

    public void setLanguage(){

        if (Locale.getDefault().getLanguage().equals("fr") || Locale.getDefault().getLanguage().equals("en")) {
            addAppTitleLabel.setText(rb.getString(addAppTitleLabel.getId()) + ":");
            addAppTitleLbl.setText(rb.getString(addAppTitleLbl.getId()) + ":");
            appIdLabel.setText(rb.getString(appIdLabel.getId()) + ":");
            appDescriptionLabel.setText(rb.getString(appDescriptionLabel.getId()) + ":");
            appLocationLabel.setText(rb.getString(appLocationLabel.getId()) + ":");
            appContactLabel.setText(rb.getString(appContactLabel.getId()) + ":");
            appTypeLabel.setText(rb.getString(appTypeLabel.getId()) + ":");
            appStartTimeLabel.setText(rb.getString(appStartTimeLabel.getId()) + ":");
            appEndTimeLabel.setText(rb.getString(appEndTimeLabel.getId()) + ":");
            appCustomerLabel.setText(rb.getString(appCustomerLabel.getId()) + ":");
            appUserLabel.setText(rb.getString(appUserLabel.getId()) + ":");

            contactCB.setPromptText(rb.getString(contactCB.getId()));
            startTimeCB.setPromptText(rb.getString(startTimeCB.getId()));
            endTimeCB.setPromptText(rb.getString(endTimeCB.getId()));
            customerCB.setPromptText(rb.getString(customerCB.getId()));
            userCB.setPromptText(rb.getString(userCB.getId()));
            saveButton.setText(rb.getString(saveButton.getId()));
            cancelButton.setText(rb.getString(cancelButton.getId()));

            appIDTF.setPromptText(rb.getString(appIdLabel.getId()));
            appTitleTF.setPromptText(rb.getString(appTypeLabel.getId()));
            appDescriptionTF.setPromptText(rb.getString(appDescriptionLabel.getId()));
            appLocationTF.setPromptText(rb.getString(appLocationLabel.getId()));
            appTypeTF.setPromptText(rb.getString(appTypeLabel.getId()));
            startDP.setPromptText(rb.getString(startDP.getId()));
            endDP.setPromptText(rb.getString(endDP.getId()));

        }
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        setLanguage();
        appIDTF.setText("" + Appointment.generateNextId());
        appIDTF.setEditable(false);


        contactCB.setItems(allContacts);
        customerCB.setItems(allCustomers);
        userCB.setItems(allUsers);
        startTimeCB.setItems(getAppointmentTimes());
        endTimeCB.setItems(getAppointmentTimes());

    }
}
