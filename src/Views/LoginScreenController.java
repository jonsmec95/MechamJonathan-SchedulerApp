package Views;

import Model.User;
import Utilities.DBQuery;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Rectangle2D;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Screen;
import javafx.stage.Stage;

import java.io.FileWriter;
import java.io.IOException;
import java.net.URL;
import java.sql.Timestamp;
import java.time.ZoneId;
import java.util.Date;
import java.util.Locale;
import java.util.ResourceBundle;
import java.util.TimeZone;
import java.util.concurrent.atomic.AtomicBoolean;

public class LoginScreenController implements Initializable {


    @FXML private TextField userNameTF;
    @FXML private TextField passwordTF;
    @FXML private Label titleLabel;
    @FXML private Label userNameLabel;
    @FXML private Label passwordLabel;
    @FXML private Label userLocationLabel;
    @FXML private Button loginButton;
    @FXML private Button exitButton;


    public static String currentUser = "";

    private ObservableList<User> allUsers = DBQuery.getAllUsers();
    public static ResourceBundle rb = ResourceBundle.getBundle("Resources/Nat", Locale.getDefault());


    @FXML void loginButtonPushed(ActionEvent event) throws IOException {

        String userName = userNameTF.getText();
        String password = passwordTF.getText();

        Date date = new Date();
        long time = date.getTime();
        Timestamp timestamp = new Timestamp(time);
        FileWriter fileWriter = new FileWriter("login_activity.txt",true);
        String format = "%-20s %-40s %-10s%n";

        if (validateLogin(userName, password)) {
            fileWriter.write(String.format(format, "USER: " + userNameTF.getText(),  "LOGIN AT: " + timestamp , "SUCCESS"));
            fileWriter.close();

            double width = 1300;
            double height = 600;
            FXMLLoader loader = new FXMLLoader(getClass().getResource("MainAppointments.fxml"));
            Parent screen = loader.load();
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            Rectangle2D screenBounds = Screen.getPrimary().getVisualBounds();
            stage.setX((screenBounds.getWidth() - width) / 2);
            stage.setY((screenBounds.getHeight() - height) / 2);

            Scene scene = new Scene(screen, width, height);
            stage.setScene(scene);
            stage.show();
        }
        else {

            fileWriter.write(String.format(format, "USER: " + userNameTF.getText(),  "LOGIN AT: " + timestamp , "FAILED"));
            fileWriter.close();

            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setHeaderText(rb.getString("loginErrorMessageTitle"));
            alert.setContentText(rb.getString("loginErrorMessage"));
            alert.showAndWait();
        }
    }

    @FXML void exitButtonPushed(ActionEvent event) {
        System.exit(0);
    }


    public boolean validateLogin(String userName, String password) {
        String testUserName = new String("test");
        String testPassword = new String("test");

        AtomicBoolean valid = new AtomicBoolean(false);

        //Lambda expression 1
        allUsers.forEach( (n) -> {if (userName.equals(n.getUserName()) && password.equals(n.getUserPassword())) {valid.set(true);currentUser = n.getUserName();} });

        if (userName.equals(testUserName) && password.equals(testPassword)) {
            valid.set(true);
        }

        return valid.get();
    }

    public void setLanguage(){

        if (Locale.getDefault().getLanguage().equals("fr") || Locale.getDefault().getLanguage().equals("en")) {
           // ResourceBundle rb = ResourceBundle.getBundle("Resources/Nat", Locale.getDefault());
            Locale locale = Locale.getDefault();
            String country = locale.getDisplayCountry();
            ZoneId localZoneId = ZoneId.of(TimeZone.getDefault().getID());

            titleLabel.setText(rb.getString(titleLabel.getId()));
            userNameLabel.setText(rb.getString(userNameLabel.getId()));
            passwordLabel.setText(rb.getString(passwordLabel.getId()));
            loginButton.setText(rb.getString(loginButton.getId()));
            exitButton.setText(rb.getString(exitButton.getId()));
            userLocationLabel.setText(rb.getString(userLocationLabel.getId()) + ": " + country + ", " + localZoneId);

        }
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        setLanguage();
    }
}
