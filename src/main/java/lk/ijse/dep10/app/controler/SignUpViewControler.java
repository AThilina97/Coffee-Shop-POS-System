package lk.ijse.dep10.app.controler;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import lk.ijse.dep10.app.PasswordEncoder;
import lk.ijse.dep10.app.db.DBConnection;

import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.util.regex.Pattern;

public class SignUpViewControler {

    @FXML
    private Button btnCreateAdmin;

    @FXML
    private TextField txtConfirmPassword;

    @FXML
    private TextField txtFullName;

    @FXML
    private TextField txtPassword;

    @FXML
    private TextField txtUsername;

    @FXML
    void btnCreateAdminOnAction(ActionEvent event) {

        if (!isDataValid()) return;

        try {
            Connection connection = DBConnection.getInstance().getConnection();
            String sql = "INSERT INTO User (fullname,username,password) VALUES (?, ?, ?)";
            PreparedStatement stm = connection.prepareStatement(sql);
            stm.setString(1,txtFullName.getText());
            stm.setString(2,txtUsername.getText());
            stm.setString(3, PasswordEncoder.encode(txtPassword.getText()));
            stm.executeUpdate();

            URL loginView = getClass().getResource("/view/UserLogin.fxml");
            var loginScene = new Scene(FXMLLoader.load(loginView));
            Stage stage = (Stage) btnCreateAdmin.getScene().getWindow();
            stage.setScene(loginScene);
            stage.sizeToScene();
            stage.centerOnScreen();
        } catch (Exception e) {
            e.printStackTrace();
            new Alert(Alert.AlertType.ERROR, "Failed to create the admin user account, try again!").show();
        }

    }

    private boolean isDataValid(){
        boolean dataValid = true;
        for (Node node : new Node[]{txtFullName, txtUsername, txtPassword, txtConfirmPassword}) {
            node.getStyleClass().remove("invalid");
        }

        String fullName = txtFullName.getText();
        String username = txtUsername.getText();
        String password = txtPassword.getText();
        String confirmPassword = txtConfirmPassword.getText();

        Pattern regEx4UpperCaseLetters = Pattern.compile("[A-Z]");
        Pattern regEx4LowerCaseLetters = Pattern.compile("[a-z]");
        Pattern regEx4Digits = Pattern.compile("[0-9]");
        Pattern regEx4Symbols = Pattern.compile("[~!@#$%^&*()_+]");

        if (password.isEmpty() || !password.equals(confirmPassword) ){
            txtConfirmPassword.requestFocus();
            txtConfirmPassword.selectAll();
            txtConfirmPassword.getStyleClass().add("invalid");
            dataValid = false;
        }

        if (!(regEx4UpperCaseLetters.matcher(password).find() &&
                regEx4LowerCaseLetters.matcher(password).find() &&
                regEx4Digits.matcher(password).find() &&
                regEx4Symbols.matcher(password).find() &&
                password.length() >= 5)){
            txtPassword.requestFocus();
            txtPassword.selectAll();
            txtPassword.getStyleClass().add("invalid");
            dataValid = false;
        }

        if (!username.matches("[A-Za-z0-9]{3,}")){
            txtUsername.requestFocus();
            txtUsername.selectAll();
            txtUsername.getStyleClass().add("invalid");
            dataValid = false;
        }

        if (!fullName.matches("[A-Za-z ]+")){
            txtFullName.requestFocus();
            txtFullName.selectAll();
            txtFullName.getStyleClass().add("invalid");
            dataValid = false;
        }
        return dataValid;
    }

}