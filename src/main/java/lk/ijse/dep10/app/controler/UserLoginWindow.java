package lk.ijse.dep10.app.controler;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.stage.Stage;
import lk.ijse.dep10.app.PasswordEncoder;
import lk.ijse.dep10.app.db.DBConnection;

import java.net.URL;
import java.sql.*;

public class UserLoginWindow {

    @FXML
    private Button btnLogin;

    @FXML
    private TextField txtPassword;

    @FXML
    private TextField txtUserName;

    public  void initialize(){
        txtUserName.setText("thili97");
        txtPassword.setText("@Thili97");
    }

    @FXML
    void btnLoginOnAction(ActionEvent event) {
        String username = txtUserName.getText();
        String password = txtPassword.getText();
        Connection connection = DBConnection.getInstance().getConnection();
        try {
            Statement stm = connection.createStatement();
            ResultSet rst = stm.executeQuery("SELECT * FROM User");

            if(!rst.next()){
                txtUserName.getStyleClass().add("invalid");
                txtPassword.getStyleClass().add("invalid");

                txtUserName.selectAll();
                txtUserName.requestFocus();
            }else{
                if(!username.matches(rst.getString("username"))){
                    txtUserName.getStyleClass().add("invalid");
                    txtUserName.requestFocus();
                    txtUserName.selectAll();
                    return;
                }
                if (!(PasswordEncoder.matches(password, rst.getString("password")))){
                    txtPassword.getStyleClass().add("invalid");
                    txtPassword.requestFocus();
                    txtPassword.selectAll();
                    return;
                }
                Stage stage = (Stage) btnLogin.getScene().getWindow();
                URL url = getClass().getResource("/view/HomeView.fxml");
                Scene scene = new Scene(FXMLLoader.load(url));
                stage.setScene(scene);
                stage.setTitle("Home Window");
                stage.setMaximized(true);


                /////////////////////
            }
        } catch (Exception e) {
            e.printStackTrace();
            new Alert(Alert.AlertType.ERROR,"Failed to user login system").showAndWait();
            System.exit(1);
        }

    }

    public void btnLoginOnKeyReleased(KeyEvent event) {
        if(event.getCode()== KeyCode.ENTER){
            btnLogin.fire();
        }

    }
}
