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
import javafx.scene.control.TableView;
import javafx.scene.layout.FlowPane;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.io.IOException;
import java.net.URL;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

public class SellingDashbordControler {

    public Button btnBack;
    @FXML
    private Button btn0;

    @FXML
    private Button btn1;

    @FXML
    private Button btn2;

    @FXML
    private Button btn3;

    @FXML
    private Button btn4;

    @FXML
    private Button btn5;

    @FXML
    private Button btn6;

    @FXML
    private Button btn7;

    @FXML
    private Button btn8;

    @FXML
    private Button btn9;

    @FXML
    private Button btnAdd;

    @FXML
    private Button btnBill;

    @FXML
    private Button btnClear;

    @FXML
    private Button btnRemove;

    @FXML
    private Button btnX;

    @FXML
    private FlowPane flowPane;

    @FXML
    private Label lblCoffeCode;

    @FXML
    private Label lblCoffeeName;

    @FXML
    private Label lblDate;

    @FXML
    private Label lblPrice;

    @FXML
    private Label lblQuantity;

    @FXML
    private Label lblTime;

    @FXML
    private Label lblTotalPrice;

    @FXML
    private TableView<?> tblCoffee;
    private String quantity="";

    public void initialize(){

        LocalDateTime now = LocalDateTime.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        String formattedDateTime = now.format(formatter);
        lblDate.setText(formattedDateTime);

        Timeline timeline = new Timeline(new KeyFrame(Duration.seconds(1), event -> updateTime()));
        timeline.setCycleCount(Animation.INDEFINITE);
        timeline.play();

        System.out.println(quantity);

    }
    private void updateTime() {
        LocalTime now = LocalTime.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("hh:mm:ss a");
        String formattedTime = now.format(formatter);
        lblTime.setText(formattedTime);
    }


    @FXML
    void btnAddOnAction(ActionEvent event) {

    }

    @FXML
    void btnClearOnAction(ActionEvent event) {
        lblCoffeCode.setText("CXXX");
        lblCoffeeName.setText("No Item");
        lblPrice.setText("Rs :000.00");
        lblQuantity.setText("00");
        quantity="";

    }

    @FXML
    void btnRemoveOnAction(ActionEvent event) {

    }

    @FXML
    void btn0OnAction(ActionEvent event) {
        quantity=quantity+0;
        lblQuantity.setText(quantity);

    }

    @FXML
    void btn1OnAction(ActionEvent event) {
        quantity=quantity+1;
        lblQuantity.setText(quantity);

    }

    @FXML
    void btn2OnAction(ActionEvent event) {
        quantity=quantity+2;
        lblQuantity.setText(quantity);

    }

    @FXML
    void btn3OnAction(ActionEvent event) {
        quantity=quantity+3;
        lblQuantity.setText(quantity);

    }

    @FXML
    void btn4OnAction(ActionEvent event) {
        quantity=quantity+4;
        lblQuantity.setText(quantity);

    }

    @FXML
    void btn5OnAction(ActionEvent event) {
        quantity=quantity+5;
        lblQuantity.setText(quantity);

    }

    @FXML
    void btn6OnAction(ActionEvent event) {
        quantity=quantity+6;
        lblQuantity.setText(quantity);

    }

    @FXML
    void btn7OnAction(ActionEvent event) {
        quantity=quantity+7;
        lblQuantity.setText(quantity);

    }

    @FXML
    void btn8OnAction(ActionEvent event) {
        quantity=quantity+8;
        lblQuantity.setText(quantity);

    }

    @FXML
    void btn9OnAction(ActionEvent event) {
        quantity=quantity+9;
        lblQuantity.setText(quantity);

    }

    @FXML
    void btnXOnAction(ActionEvent event) {
        lblQuantity.setText("00");
        quantity="";
    }

    @FXML
    void btnBillOnAction(ActionEvent event) {
        btnClear.fire();

    }


    public void btnBackOnAction(ActionEvent actionEvent) throws IOException {
        Stage stage =(Stage) btnAdd.getScene().getWindow();
        URL url = getClass().getResource("/view/HomeView.fxml");
        Scene scene = new Scene(FXMLLoader.load(url));
        stage.setScene(scene);
        stage.centerOnScreen();
        stage.show();
        stage.setTitle("Home Window");
    }

}
