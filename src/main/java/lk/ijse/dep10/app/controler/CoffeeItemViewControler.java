package lk.ijse.dep10.app.controler;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;

public class CoffeeItemViewControler {

    @FXML
    private Button btnBack;

    @FXML
    private Button btnBrows;

    @FXML
    private Button btnClear;

    @FXML
    private Button btnDelete;

    @FXML
    private Button btnNew;

    @FXML
    private Button btnSave;

    @FXML
    private ImageView imgViewCoffee;

    @FXML
    private TableView<?> tblCoffee;

    @FXML
    private TextField txtId;

    @FXML
    private TextField txtName;

    @FXML
    private TextField txtPrice;

    @FXML
    void btnBrowsOnAction(ActionEvent event) {

    }

    @FXML
    void btnClearOnAction(ActionEvent event) {

    }

    @FXML
    void btnDeleteOnAction(ActionEvent event) {

    }

    @FXML
    void btnNewOnAction(ActionEvent event) {

    }

    @FXML
    void btnSaveOnAction(ActionEvent event) {

    }

    @FXML
    void btnBackOnAction(ActionEvent event) throws IOException {
        Stage stage =(Stage) btnBack.getScene().getWindow();
        URL url = getClass().getResource("/view/HomeView.fxml");
        Scene scene = new Scene(FXMLLoader.load(url));
        stage.setScene(scene);
        stage.show();
        stage.setTitle("Home Window");


    }

}
