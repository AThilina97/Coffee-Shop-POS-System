package lk.ijse.dep10.app.controler;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import lk.ijse.dep10.app.db.DBConnection;
import lk.ijse.dep10.app.model.Coffee;
import lk.ijse.dep10.app.model.Employee;

import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.sql.*;

public class CoffeeItemViewControler {

    public TextField txtCode;
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
    private TableView<Coffee> tblCoffee;

    @FXML
    private TextField txtName;

    @FXML
    private TextField txtPrice;

    public void initialize(){
        loadCoffeeItems();
        tblCoffee.getColumns().get(0).setCellValueFactory(new PropertyValueFactory<>("code"));
        tblCoffee.getColumns().get(1).setCellValueFactory(new PropertyValueFactory<>("name"));
        tblCoffee.getColumns().get(2).setCellValueFactory(new PropertyValueFactory<>("price"));
        tblCoffee.getColumns().get(3).setCellValueFactory(new PropertyValueFactory<>("imageView"));


    }

    private void loadCoffeeItems() {
        Connection connection = DBConnection.getInstance().getConnection();
        try {
            Statement stm = connection.createStatement();
            ResultSet rst = stm.executeQuery("SELECT * FROM Coffee");
            PreparedStatement stmCoffeePic = connection.prepareStatement("SELECT * FROM CoffeePic WHERE coffee_code=?");
            
            while (rst.next()){
                String code = rst.getString("code");
                String name = rst.getString("name");
                int price = rst.getInt("price");

                Coffee coffee = new Coffee(code, name, price, null, null);
                Image noCupImg = new Image("/images/Nocup.jpg",200,200,true,true);
                ImageView noCupImgView = new ImageView(noCupImg);
                coffee.setImageView(noCupImgView);

                stmCoffeePic.setString(1,code);
                ResultSet rstPic = stmCoffeePic.executeQuery();
                if(rstPic.next()){
                    Blob coffeePic = rstPic.getBlob("coffee_pic");
                    coffee.setImgBlob(coffeePic);
                    Image image1 = new Image(coffeePic.getBinaryStream(), 200, 200, true, true);
                    ImageView imageView1 = new ImageView(image1);
                    coffee.setImageView(imageView1);

                }
                tblCoffee.getItems().add(coffee);

            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @FXML
    void btnBrowsOnAction(ActionEvent event) throws MalformedURLException {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Select Coffee Picture");
        fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("Image Files","*.jpg","*.png","*.jpeg","*.gif","*.bmp"));
        File file = fileChooser.showOpenDialog(btnNew.getScene().getWindow());
        if(file!=null){
            Image image = new Image(file.toURI().toURL().toString());
            imgViewCoffee.setImage(image);

        }
    }
    @FXML
    void btnClearOnAction(ActionEvent event) {
        Image image = new Image("/images/Nocup.jpg");
        imgViewCoffee.setImage(image);


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

    @FXML
    void btnDeleteOnAction(ActionEvent event) {

    }

    @FXML
    void btnNewOnAction(ActionEvent event) {
        if(tblCoffee.getItems().size()==0){
            txtCode.setText("C001");
        }else {
            Coffee coffee = tblCoffee.getItems().get(tblCoffee.getItems().size() - 1);
            int nextCode = Integer.parseInt(coffee.getCode().substring(1))+1;
            String strCode =String.format("C%03d",nextCode);
            txtCode.setText(strCode);
        }
        txtName.clear();
        txtName.getStyleClass().remove("invalid");
        txtPrice.clear();
        txtPrice.getStyleClass().remove("invalid");
        Image image = new Image("/images/Nocup.jpg");
        imgViewCoffee.setImage(image);
        tblCoffee.getSelectionModel().clearSelection();
        txtName.requestFocus();

    }

}
