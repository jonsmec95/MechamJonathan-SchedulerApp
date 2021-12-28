package Views;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.Locale;
import java.util.Optional;
import java.util.ResourceBundle;
import static Views.LoginScreenController.rb;

public class MainReportsController implements Initializable {

    @FXML private Button appointmentsButton;
    @FXML private Button customersButton;
    @FXML private Button reportsButton;
    @FXML private Button exitButton;
    @FXML private Button report1;
    @FXML private Button report2;
    @FXML private Button report3;

    @FXML private Label titleLabel;
    @FXML private Label reportsTitleLabel;


    @FXML void appointmentsButtonPushed(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("MainAppointments.fxml"));
        Parent screen = loader.load();
        Scene scene = new Scene(screen);
        Stage stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        stage.setScene(scene);
        stage.show();
    }

    @FXML void customersButtonPushed(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("MainCustomer.fxml"));
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
            reportsTitleLabel.setText(rb.getString(reportsTitleLabel.getId()));
            appointmentsButton.setText(rb.getString(appointmentsButton.getId()));
            customersButton.setText(rb.getString(customersButton.getId()));
            reportsButton.setText(rb.getString(reportsButton.getId()));
            exitButton.setText(rb.getString(exitButton.getId()));
            report1.setText(rb.getString(report1.getId()));
            report2.setText(rb.getString(report2.getId()));
            report3.setText(rb.getString(report3.getId()));


        }
    }



    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        setLanguage();
    }
}
