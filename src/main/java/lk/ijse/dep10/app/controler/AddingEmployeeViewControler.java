package lk.ijse.dep10.app.controler;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.ListView;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;

public class AddingEmployeeViewControler {

    @FXML
    private ToggleGroup Gender;

    @FXML
    private Button btnAdd;

    @FXML
    private Button btnBrows;

    @FXML
    private Button btnClear;

    @FXML
    private Button btnDelete;

    @FXML
    private Button btnHome;

    @FXML
    private Button btnNewEmployee;

    @FXML
    private Button btnRemove;

    @FXML
    private Button btnSave;

    @FXML
    private DatePicker dtp;

    @FXML
    private ImageView imgPropic;

    @FXML
    private ListView<?> lstContact;

    @FXML
    private RadioButton rdoFemale;

    @FXML
    private RadioButton rdoMale;

    @FXML
    private TableView<?> tblEmployee;

    @FXML
    private TextField txtAddress;

    @FXML
    private TextField txtContact;

    @FXML
    private TextField txtDesign;

    @FXML
    private TextField txtId;

    @FXML
    private TextField txtName;

    @FXML
    private TextField txtSallary;

    @FXML
    private TextField txtSearch;

    @FXML
    void btnAddOnAction(ActionEvent event) {

    }

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
    void btnNewEmployeeOnAction(ActionEvent event) {

    }

    @FXML
    void btnRemoveOnAction(ActionEvent event) {

    }

    @FXML
    void btnSaveOnAction(ActionEvent event) {

    }

    @FXML
    void btnHomeOnAction(ActionEvent event) throws IOException {
        Stage stage =(Stage) btnNewEmployee.getScene().getWindow();
        URL url = getClass().getResource("/view/HomeView.fxml");
        Scene scene = new Scene(FXMLLoader.load(url));
        stage.setScene(scene);
        stage.centerOnScreen();
        stage.show();
        stage.setTitle("Home Window");


    }

}
