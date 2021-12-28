package Views;

import Model.*;
import Utilities.DBQuery;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Rectangle2D;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Screen;
import javafx.stage.Stage;


import java.io.IOException;
import java.net.URL;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.Locale;
import java.util.Optional;
import java.util.ResourceBundle;
import java.util.TimeZone;
import static Views.LoginScreenController.rb;

public class MainAppointmentsController implements Initializable {


    @FXML private TableView<Appointment> appointmentTableView;
    @FXML private TableColumn<Appointment, Integer> appIdCol;
    @FXML private TableColumn<Appointment, String> appTitleCol;
    @FXML private TableColumn<Appointment, String> appDescriptionCol;
    @FXML private TableColumn<Appointment, String> appLocationCol;
    @FXML private TableColumn<Appointment, Integer> appContactCol;
    @FXML private TableColumn<Appointment, String> appTypeCol;
    @FXML private TableColumn<Appointment, String> appStartCol;
    @FXML private TableColumn<Appointment, String> appEndCol;
    @FXML private TableColumn<Appointment, Integer> appCustIDCol;
    @FXML private TableColumn<Appointment, String> appUserCol;
    @FXML private ToggleGroup filterToggleGroup;
    @FXML private RadioButton allRB;
    @FXML private RadioButton monthRB;
    @FXML private RadioButton weekRB;

    @FXML private Label filterLabel;
    @FXML private Button appointmentsButton;
    @FXML private Button customersButton;
    @FXML private Button reportsButton;
    @FXML private Button exitButton;
    @FXML private Label titleLabel;
    @FXML private Label appTitleLabel;
    @FXML private  Button addButton;
    @FXML private Button updateButton;
    @FXML private Button deleteButton;

    private ObservableList<Appointment> allAppointments = DBQuery.getAllAppointments();
    private ObservableList<Contact> allContacts = DBQuery.getAllContacts();
    private ObservableList<User> allUsers = DBQuery.getAllUsers();





    @FXML void addButtonPushed(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("AddAppointment.fxml"));
        Parent screen = loader.load();
        Scene scene = new Scene(screen);
        Stage stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        stage.setScene(scene);
        stage.show();
    }

    @FXML void updateButtonPushed(ActionEvent event) throws IOException {
        Appointment selectedApp = appointmentTableView.getSelectionModel().getSelectedItem();

        if (selectedApp != null) {

            FXMLLoader loader = new FXMLLoader(getClass().getResource("UpdateAppointment.fxml"));
            Parent screen = loader.load();

            UpdateAppointmentController updateAppointmentController = loader.getController();
            updateAppointmentController.getAppointmentData(selectedApp);

            Scene scene = new Scene(screen);
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            stage.setScene(scene);
            stage.show();
        }
        else {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setContentText(rb.getString("noAptSelected"));
            alert.showAndWait();
        }
    }

    @FXML void deleteButtonPushed(ActionEvent event) throws IOException {

        if (appointmentTableView.getSelectionModel().isEmpty()){
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setContentText(rb.getString("noAptSelected"));
            alert.showAndWait();
        } else {
            int appToDelete = appointmentTableView.getSelectionModel().getSelectedItem().getAppId();

            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setHeaderText(rb.getString("deleteAptErrorTitle"));
            alert.setContentText(rb.getString("deleteAptError"));

            Optional<ButtonType> result = alert.showAndWait();
            if (result.get() == ButtonType.OK) {
                DBQuery.removeAppointment(appToDelete);
                ObservableList<Appointment> updatedAppointments = DBQuery.getAllAppointments();
                appointmentTableView.setItems(updatedAppointments);

            }
        }
    }

    // changes to customers screen
    @FXML void customersButtonPushed(ActionEvent event) throws IOException{
        FXMLLoader loader = new FXMLLoader(getClass().getResource("MainCustomer.fxml"));
        Parent screen = loader.load();
        Scene scene = new Scene(screen);
        Stage stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        stage.setScene(scene);
        stage.show();
    }

    //changes to reports screen
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


    @FXML void filterButtonsPushed(ActionEvent event){
         DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

        if (allRB.isSelected()){
            appointmentTableView.setItems(allAppointments);
        }
        if (monthRB.isSelected()){
            ObservableList<Appointment> filteredAppointments = FXCollections.observableArrayList();
            LocalDateTime yesterday = LocalDateTime.now().minusDays(1);
            LocalDateTime upcomingMonth = yesterday.plusMonths(1);

            for (int i = 0; i < allAppointments.size(); i ++) {
                LocalDateTime appStart = LocalDateTime.parse(allAppointments.get(i).getAppStart(), formatter);

                if (appStart.isAfter(yesterday) & appStart.isBefore(upcomingMonth)) {
                    filteredAppointments.add(allAppointments.get(i));
                }
                appointmentTableView.setItems(filteredAppointments);
            }

        }
        if (weekRB.isSelected()) {
            ObservableList<Appointment> filteredAppointments = FXCollections.observableArrayList();
            LocalDateTime yesterday = LocalDateTime.now().minusDays(1);
            LocalDateTime upcomingWeek = yesterday.plusWeeks(1);

            for (int i = 0; i < allAppointments.size(); i++) {
                LocalDateTime appStart = LocalDateTime.parse(allAppointments.get(i).getAppStart(), formatter);

                if (appStart.isAfter(yesterday) & appStart.isBefore(upcomingWeek)) {
                    filteredAppointments.add(allAppointments.get(i));
                }
                appointmentTableView.setItems(filteredAppointments);
            }
        }
    }

    public void setLanguage(){

        if (Locale.getDefault().getLanguage().equals("fr") || Locale.getDefault().getLanguage().equals("en")) {
            titleLabel.setText(rb.getString(titleLabel.getId()));
            appTitleLabel.setText(rb.getString(appTitleLabel.getId()));
            appointmentsButton.setText(rb.getString(appointmentsButton.getId()));
            customersButton.setText(rb.getString(customersButton.getId()));
            reportsButton.setText(rb.getString(reportsButton.getId()));
            exitButton.setText(rb.getString(exitButton.getId()));

            //table
            appIdCol.setText(rb.getString(appIdCol.getId()));
            appTitleCol.setText(rb.getString(appTitleCol.getId()));
            appDescriptionCol.setText(rb.getString(appDescriptionCol.getId()));
            appLocationCol.setText(rb.getString(appLocationCol.getId()));
            appContactCol.setText(rb.getString(appContactCol.getId()));
            appTypeCol.setText(rb.getString(appTypeCol.getId()));
            appStartCol.setText(rb.getString(appStartCol.getId()));
            appEndCol.setText(rb.getString(appEndCol.getId()));
            appCustIDCol.setText(rb.getString(appCustIDCol.getId()));
            appUserCol.setText(rb.getString(appUserCol.getId()));
            filterLabel.setText(rb.getString(filterLabel.getId()));
            allRB.setText(rb.getString(allRB.getId()));
            monthRB.setText(rb.getString(monthRB.getId()));
            weekRB.setText(rb.getString(weekRB.getId()));

            addButton.setText(rb.getString(addButton.getId()));
            updateButton.setText(rb.getString(updateButton.getId()));
            deleteButton.setText(rb.getString(deleteButton.getId()));

        }
    }


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        setLanguage();
        ObservableList<Appointment> allAppointments = DBQuery.getAllAppointments();

        appIdCol.setCellValueFactory(new PropertyValueFactory<>("appId"));
        appTitleCol.setCellValueFactory(new PropertyValueFactory<>("appTitle"));
        appDescriptionCol.setCellValueFactory(new PropertyValueFactory<>("appDescription"));
        appLocationCol.setCellValueFactory(new PropertyValueFactory<>("appLocation"));
        appContactCol.setCellValueFactory(new PropertyValueFactory<>("appContactId"));
        appTypeCol.setCellValueFactory(new PropertyValueFactory<>("appType"));
        appStartCol.setCellValueFactory(new PropertyValueFactory<>("appStart"));
        appEndCol.setCellValueFactory(new PropertyValueFactory<>("appEnd"));
        appCustIDCol.setCellValueFactory(new PropertyValueFactory<>("appCustId"));
        appUserCol.setCellValueFactory(new PropertyValueFactory<>("appUserId"));
        appointmentTableView.setItems(allAppointments);

        filterToggleGroup = new ToggleGroup();
        allRB.setToggleGroup(filterToggleGroup);
        monthRB.setToggleGroup(filterToggleGroup);
        weekRB.setToggleGroup(filterToggleGroup);
        allRB.setSelected(true);

    }
}
