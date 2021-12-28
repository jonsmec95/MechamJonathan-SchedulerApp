package Main;


import Utilities.DBConnect;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.Locale;

public class Main extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception{
        Parent root = FXMLLoader.load(getClass().getResource("/Views/LoginScreen.fxml"));
        primaryStage.setTitle("Scheduler");
        //400 x 300
        primaryStage.setScene(new Scene(root, 600, 400));
        primaryStage.show();
    }


    public static void main(String[] args) throws SQLException {
        //Locale.setDefault(new Locale("fr"));

        Connection conn = DBConnect.startConnection();
        launch(args);
        DBConnect.closeConnection();
    }
}
