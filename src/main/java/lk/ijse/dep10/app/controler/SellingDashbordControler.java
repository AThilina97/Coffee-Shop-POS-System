package lk.ijse.dep10.app.controler;

import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.embed.swing.SwingFXUtils;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.scene.Cursor;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableView;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.TextAlignment;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.util.Duration;
import lk.ijse.dep10.app.db.DBConnection;

import javax.imageio.ImageIO;
import javax.sql.rowset.serial.SerialBlob;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.net.URL;
import java.sql.*;
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
        loadBookDetails();
        LocalDateTime now = LocalDateTime.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        String formattedDateTime = now.format(formatter);
        lblDate.setText(formattedDateTime);

        Timeline timeline = new Timeline(new KeyFrame(Duration.seconds(1), event -> updateTime()));
        timeline.setCycleCount(Animation.INDEFINITE);
        timeline.play();

        System.out.println(quantity);

    }
    public void loadBookDetails() {
        Connection connection = DBConnection.getInstance().getConnection();
        try {
            Statement stm = connection.createStatement();
            ResultSet rs = stm.executeQuery("SELECT * FROM Coffee");
            PreparedStatement stmCoffeePic = connection.prepareStatement("SELECT * FROM CoffeePic WHERE coffee_code=?");
            while (rs.next()){
                String code = rs.getString("code");
                String coffeeName = rs.getString("name");
                int price = rs.getInt("price");
                stmCoffeePic.setString(1,code);
                ResultSet rstPic = stmCoffeePic.executeQuery();
                if(rstPic.next()) {
                    Blob coffeePic = rstPic.getBlob("coffee_pic");
                    ImageView preview = new ImageView();
                    preview.setFitHeight(250);
                    preview.setFitWidth(200);
                    preview.setImage(new Image(coffeePic.getBinaryStream(), 200.0, 200.0, true, true));
                    Button btnBook = new Button();
                    Label label = new Label(coffeeName);
                    label.setTextFill(Color.WHITE);
                    label.setMinWidth(100.0);
                    label.setTextAlignment(TextAlignment.CENTER);
                    VBox vBox = new VBox();
                    vBox.getChildren().addAll(btnBook, label);
//                    btnBook.setText(coffeeName);
                    btnBook.setPadding(new Insets(20.0, 20.0, 20.0, 20.0));
                    btnBook.setGraphic(preview);
                    btnBook.setCursor(Cursor.HAND);
                    btnBook.setOnAction(actionEvent -> {
                        lblCoffeCode.setText(code);
                        lblCoffeeName.setText(coffeeName);
                        lblPrice.setText(price+"");
                    });
                    flowPane.getChildren().add(vBox);
                }
            }

        } catch (SQLException e) {
            new Alert(Alert.AlertType.ERROR,"Something went wrong. Failed to load Coffees").showAndWait();
            e.printStackTrace();
        }
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
