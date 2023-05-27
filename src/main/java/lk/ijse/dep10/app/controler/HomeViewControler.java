package lk.ijse.dep10.app.controler;

import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.stage.Stage;
import javafx.stage.Window;
import javafx.util.Duration;

import java.io.IOException;
import java.net.URL;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

public class HomeViewControler {

    @FXML
    private Button btnCoffeeItem;

    @FXML
    private Button btnEmployeeRecords;

    @FXML
    private Button btnLogout;

    @FXML
    private Button btnSellingDash;

    @FXML
    private Label lblDate;

    @FXML
    private Label lblTime;

    public void initialize(){

        LocalDateTime now = LocalDateTime.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        String formattedDateTime = now.format(formatter);
        lblDate.setText(formattedDateTime);

        Timeline timeline = new Timeline(new KeyFrame(Duration.seconds(1), event -> updateTime()));
        timeline.setCycleCount(Animation.INDEFINITE);
        timeline.play();

    }

    private void updateTime() {
        LocalTime now = LocalTime.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("hh:mm:ss a");
        String formattedTime = now.format(formatter);
        lblTime.setText(formattedTime);
    }

    @FXML
    void btnCoffeeItemOnAction(ActionEvent event) throws IOException {
        Stage stage =(Stage) btnLogout.getScene().getWindow();
        URL url = getClass().getResource("/view/CoffeeItemView.fxml");
        Scene scene = new Scene(FXMLLoader.load(url));
        stage.setScene(scene);
        stage.setTitle("Add Coffee Window");
        stage.centerOnScreen();
        stage.show();


    }

    @FXML
    void btnEmployeeRecordsOnAction(ActionEvent event) throws IOException {
        Stage stage =(Stage) btnLogout.getScene().getWindow();
        URL url = getClass().getResource("/view/AddingEmployeeView.fxml");
        Scene scene = new Scene(FXMLLoader.load(url));
        stage.setScene(scene);
        stage.setTitle("Employee Details");
        stage.centerOnScreen();
        stage.show();
        stage.setMaximized(true);

    }

    @FXML
    void btnLogoutOnAction(ActionEvent event) throws IOException {
        Window window = btnLogout.getScene().getWindow();
        window.setWidth(500);
        window.setHeight(500);
        Stage stage =(Stage) window;
        URL url = getClass().getResource("/view/UserLogin.fxml");
        Scene scene = new Scene(FXMLLoader.load(url));
        stage.setScene(scene);
        stage.setTitle("Login Window to the System");
        stage.centerOnScreen();
        stage.setMaximized(false);
        stage.show();


    }

    @FXML
    void btnSellingDashOnAction(ActionEvent event) throws IOException {
        Stage stage =(Stage) btnLogout.getScene().getWindow();
        URL url = getClass().getResource("/view/SellingDashbord.fxml");
        Scene scene = new Scene(FXMLLoader.load(url));
        stage.setScene(scene);
        stage.setTitle("Selling Dash Bord");
        stage.centerOnScreen();
        stage.show();
        stage.setMaximized(true);
    }

}
