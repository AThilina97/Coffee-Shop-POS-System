package lk.ijse.dep10.app.controler;

import javafx.embed.swing.SwingFXUtils;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
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
import lk.ijse.dep10.app.model.Gender;

import javax.imageio.ImageIO;
import javax.sql.rowset.serial.SerialBlob;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.sql.*;
import java.util.ArrayList;

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
                Image noCupImg = new Image("/images/Nocup.jpg",80,80,true,true);
                ImageView noCupImgView = new ImageView(noCupImg);
                coffee.setImageView(noCupImgView);

                stmCoffeePic.setString(1,code);
                ResultSet rstPic = stmCoffeePic.executeQuery();
                if(rstPic.next()){
                    Blob coffeePic = rstPic.getBlob("coffee_pic");
                    coffee.setImgBlob(coffeePic);
                    Image image1 = new Image(coffeePic.getBinaryStream(), 80, 80, true, true);
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
        if(!isDatavalid()) return;

        String code = txtCode.getText();
        String name = txtName.getText();
        int price = Integer.parseInt(txtPrice.getText());
//        ArrayList<String> contactList = new ArrayList<>(lstContact.getItems());

        Connection connection = DBConnection.getInstance().getConnection();
        try {
            PreparedStatement stm = connection.prepareStatement("INSERT INTO Coffee (code, name, price) VALUES (?,?,?)");
            stm.setString(1,code);
            stm.setString(2,name);
            stm.setInt(3,price);
            stm.executeUpdate();

//            PreparedStatement stmContact = connection.prepareStatement("INSERT INTO Contact (employee_id, contact) VALUES (?,?)");
//            for (String contact: contactList) {
//                PreparedStatement stm1 = connection.prepareStatement("SELECT * From Contact WHERE contact=?");
//                stm1.setString(1,contact);
//                ResultSet rst1 = stm1.executeQuery();
//                if(rst1.next()){
//                    new Alert(Alert.AlertType.ERROR,"Incorrect Contact number").show();
//                    return;
//                }
//                stmContact.setString(1,code);
//                stmContact.setString(2,contact);
//                stmContact.executeUpdate();
//            }
            Coffee coffee = new Coffee(code, name, price, null, null);

            Image noImg = new Image("/images/Nocup.jpg");
            ImageView imageView = new ImageView(noImg);
            coffee.setImageView(imageView); //........................

            PreparedStatement stmPicture = connection.prepareStatement("INSERT INTO CoffeePic(coffee_code, coffee_pic) VALUES (?,?)");
            stmPicture.setString(1,code);

            Image image = imgViewCoffee.getImage();
            if(image!=null){
                BufferedImage bufferedImage = SwingFXUtils.fromFXImage(image, null);
                ByteArrayOutputStream baos = new ByteArrayOutputStream();
                ImageIO.write(bufferedImage, "png", baos);
                byte[] bytes = baos.toByteArray();
                Blob blobImage = new SerialBlob(bytes);
                stmPicture.setBlob(2,blobImage);
                Image img1 = new Image(blobImage.getBinaryStream(), 200, 200, true, true);
                coffee.setImageView(new ImageView(img1));
                coffee.setImgBlob(blobImage);
                stmPicture.executeUpdate();

            }
            tblCoffee.getItems().add(coffee);
            btnNew.fire();

        } catch (Exception e) {
            e.printStackTrace();
            new Alert(Alert.AlertType.ERROR,"Faild to save the Coffee").showAndWait();
        }

    }
    private boolean isDatavalid() {
        boolean dataValid =true;
        if(!txtPrice.getText().matches("\\d{2,}")){
            txtPrice.requestFocus();
            txtPrice.selectAll();
            txtPrice.getStyleClass().add("invalid");
            dataValid=false;
        }
        if (!txtName.getText().matches("[A-za-z ]{2,}")){
            txtName.requestFocus();
            txtName.selectAll();
            txtName.getStyleClass().add("invalid");
        }
        return dataValid;
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
        if(tblCoffee.getSelectionModel().getSelectedItem()!=null){
            Connection connection = DBConnection.getInstance().getConnection();
            Coffee selectEmployee = tblCoffee.getSelectionModel().getSelectedItem();
            String code = selectEmployee.getCode();
            try {
                PreparedStatement stmPic = connection.prepareStatement("DELETE FROM CoffeePic WHERE coffee_code=?");
                stmPic.setString(1,code);
                stmPic.executeUpdate();
                PreparedStatement stm = connection.prepareStatement("DELETE FROM Coffee WHERE code=?");
                stm.setString(1,code);
                stm.executeUpdate();

                tblCoffee.getItems().remove(selectEmployee);
                btnNew.fire();
                //tblEmployee.getSelectionModel().clearSelection();
            } catch (SQLException e) {
                e.printStackTrace();
                new Alert(Alert.AlertType.ERROR,"Failed to Delete Coffee ");
            }

        }

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
