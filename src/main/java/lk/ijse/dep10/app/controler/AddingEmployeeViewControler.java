package lk.ijse.dep10.app.controler;

import javafx.collections.ObservableList;
import javafx.embed.swing.SwingFXUtils;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.paint.Color;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.util.converter.LocalDateStringConverter;
import lk.ijse.dep10.app.db.DBConnection;
import lk.ijse.dep10.app.model.Employee;
import lk.ijse.dep10.app.model.Gender;

import javax.imageio.ImageIO;
import javax.sql.rowset.serial.SerialBlob;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.lang.reflect.Array;
import java.net.MalformedURLException;
import java.net.URL;
import java.sql.*;
import java.util.ArrayList;

public class AddingEmployeeViewControler {

    public ToggleGroup tglGender;

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
    private ListView<String> lstContact;

    @FXML
    private RadioButton rdoFemale;

    @FXML
    private RadioButton rdoMale;

    @FXML
    private TableView<Employee> tblEmployee;

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


    public void initialize(){
        loadEmployee();

        tblEmployee.getColumns().get(0).setCellValueFactory(new PropertyValueFactory<>("id"));
        tblEmployee.getColumns().get(1).setCellValueFactory(new PropertyValueFactory<>("name"));
        tblEmployee.getColumns().get(2).setCellValueFactory(new PropertyValueFactory<>("gender"));
        tblEmployee.getColumns().get(3).setCellValueFactory(new PropertyValueFactory<>("dob"));
        tblEmployee.getColumns().get(4).setCellValueFactory(new PropertyValueFactory<>("contactList"));
        tblEmployee.getColumns().get(5).setCellValueFactory(new PropertyValueFactory<>("designation"));
        tblEmployee.getColumns().get(6).setCellValueFactory(new PropertyValueFactory<>("salary"));
        tblEmployee.getColumns().get(7).setCellValueFactory(new PropertyValueFactory<>("address"));
        tblEmployee.getColumns().get(8).setCellValueFactory(new PropertyValueFactory<>("image"));




        lstContact.getSelectionModel().selectedItemProperty().addListener((observableValue, s, current) ->{
            btnRemove.setDisable(current==null);
        } );

        tblEmployee.getSelectionModel().selectedItemProperty().addListener((observableValue, employee, currentEmp) ->{
            if(currentEmp!=null){
                txtId.setText(currentEmp.getId());
                txtName.setText(currentEmp.getName());
                txtAddress.setText(currentEmp.getAddress());
                txtDesign.setText(currentEmp.getDesignation());
                txtSallary.setText(currentEmp.getSalary());
                imgPropic.setImage(currentEmp.getImage().getImage());
                dtp.setValue(currentEmp.getDob());
                lstContact.getItems().clear();
                for (String contact:currentEmp.getContactList()) {
                    lstContact.getItems().add(contact);
                }
                tglGender.selectToggle((currentEmp.getGender()==Gender.MALE)? rdoMale:rdoFemale);
            }
        } );

    }

    private void loadEmployee() {
        Connection connection = DBConnection.getInstance().getConnection();
        try {
            Statement stm = connection.createStatement();
            ResultSet rst = stm.executeQuery("SELECT * FROM Employee");
            PreparedStatement stmPic = connection.prepareStatement("SELECT *FROM Picture WHERE employee_id=?");
            PreparedStatement stmContact = connection.prepareStatement("SELECT *FROM Contact WHERE employee_id=?");
            while (rst.next()){
                String id = rst.getString("id");
                String name = rst.getString("name");
                String address = rst.getString("address");
                String gender = rst.getString("gender");
                Date dob = rst.getDate("dob");
                String salary = rst.getString("salary");
                String designation = rst.getString("designation");
                Employee employee = new Employee(id, name, address, Gender.valueOf(gender),
                        dob.toLocalDate(),null,designation,salary,null,null);

                Image image = new Image("/images/noimage.png",200,200,true,true);
                ImageView imageView = new ImageView(image);
                employee.setImage(imageView);

                stmPic.setString(1,id);
                ResultSet rstPic = stmPic.executeQuery();
                if(rstPic.next()){
                    Blob picture = rstPic.getBlob("picture");
                    employee.setPicture(picture);
                    Image image1 = new Image(picture.getBinaryStream(), 200, 200, true, true);
                    ImageView imageView1 = new ImageView(image1);
                    employee.setImage(imageView1);
                }
                stmContact.setString(1,id);
                ResultSet rstContact = stmContact.executeQuery();
                ArrayList<String> arrayContact =new ArrayList<>();
                while (rstContact.next()){
                    String contact = rstContact.getString("contact");
                    arrayContact.add(contact);
                }
                employee.setContactList(arrayContact);
                tblEmployee.getItems().add(employee);

            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @FXML
    void btnNewEmployeeOnAction(ActionEvent event) {
        if(tblEmployee.getItems().size()==0){
            txtId.setText("E001");
        }else {
            Employee employee = tblEmployee.getItems().get(tblEmployee.getItems().size() - 1);
            int nextID = Integer.parseInt(employee.getId().substring(1))+1;
            String strID =String.format("E%03d",nextID);
            txtId.setText(strID);
        }
        Node[] node =new Node[]{txtName,txtAddress,txtContact,txtSallary,txtDesign};
        for (Node txt:node) {
            TextField txtField =(TextField) txt;
            txtField.clear();
            txtField.getStyleClass().remove("invalid");

        }
        tglGender.selectToggle(null);
        dtp.setValue(null);
        Image image = new Image("/images/noimage.png");
        imgPropic.setImage(image);
        lstContact.getItems().clear();
        tblEmployee.getSelectionModel().clearSelection();



    }

    @FXML
    void btnAddOnAction(ActionEvent event) {
        if(!txtContact.getText().matches("\\d{3}-\\d{7}") || lstContact.getItems().contains(txtContact.getText().strip())) {
            txtContact.requestFocus();
            txtContact.selectAll();
            txtContact.getStyleClass().add("invalid");

        }else {
            txtContact.getStyleClass().remove("invalid");
            lstContact.getItems().add(txtContact.getText());
            txtContact.clear();
            txtContact.requestFocus();

        }
    }



    @FXML
    void btnRemoveOnAction(ActionEvent event) {
        if(lstContact.getSelectionModel().getSelectedItem()!=null){
            lstContact.getItems().remove(lstContact.getSelectionModel().getSelectedItem());
            lstContact.getSelectionModel().clearSelection();

        }

    }

    @FXML
    void btnBrowsOnAction(ActionEvent event) throws MalformedURLException {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Select Profile Picture");
        fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("Image Files","*.jpg","*.png","*.jpeg","*.gif","*.bmp"));
        File file = fileChooser.showOpenDialog(btnAdd.getScene().getWindow());
        if(file!=null){
            Image image = new Image(file.toURI().toURL().toString());
            imgPropic.setImage(image);

        }

    }

    @FXML
    void btnClearOnAction(ActionEvent event) {
        Image image = new Image("/images/noimage.png");
        imgPropic.setImage(image);


    }

    @FXML
    void btnSaveOnAction(ActionEvent event) {
        if(!isDatavalid()) return;

        String id = txtId.getText();
        String name = txtName.getText();
        String address = txtAddress.getText();
        Gender gender=(tglGender.getSelectedToggle()==rdoMale)? Gender.MALE:Gender.FEMALE;
        String salary = txtSallary.getText();
        String designation = txtDesign.getText();
        Date dob = Date.valueOf(dtp.getValue().toString());
        ArrayList<String> contactList = new ArrayList<>(lstContact.getItems());


        Connection connection = DBConnection.getInstance().getConnection();
        try {
            PreparedStatement stm = connection.prepareStatement("INSERT INTO Employee (id, name, address, gender, dob, salary, designation) VALUES (?,?,?,?,?,?,?)");
            stm.setString(1,id);
            stm.setString(2,name);
            stm.setString(3,address);
            stm.setString(4,gender.name());
            stm.setDate(5,dob);
            stm.setString(6,salary);
            stm.setString(7,designation);
            stm.executeUpdate();

            PreparedStatement stmContact = connection.prepareStatement("INSERT INTO Contact (employee_id, contact) VALUES (?,?)");
            for (String contact: contactList) {
                PreparedStatement stm1 = connection.prepareStatement("SELECT * From Contact WHERE contact=?");
                stm1.setString(1,contact);
                ResultSet rst1 = stm1.executeQuery();
                if(rst1.next()){
                    new Alert(Alert.AlertType.ERROR,"Incorrect Contact number").show();
                    return;
                }
                stmContact.setString(1,id);
                stmContact.setString(2,contact);
                stmContact.executeUpdate();
            }
            Employee employee = new Employee(id, name, address, gender, dob.toLocalDate(), contactList, designation, salary, null, null);

            Image noImg = new Image("/images/noimage.png");
            ImageView imageView = new ImageView(noImg);
            employee.setImage(imageView);

            PreparedStatement stmPicture = connection.prepareStatement("INSERT INTO Picture (employee_id, picture) VALUES (?,?)");
            stmPicture.setString(1,id);

            Image image = imgPropic.getImage();
            if(image!=null){
                BufferedImage bufferedImage = SwingFXUtils.fromFXImage(image, null);
                ByteArrayOutputStream baos = new ByteArrayOutputStream();
                ImageIO.write(bufferedImage, "png", baos);
                byte[] bytes = baos.toByteArray();
                Blob blobImage = new SerialBlob(bytes);
                stmPicture.setBlob(2,blobImage);
                Image img1 = new Image(blobImage.getBinaryStream(), 200, 200, true, true);
                employee.setImage(new ImageView(img1));
                employee.setPicture(blobImage);
                stmPicture.executeUpdate();

            }
            tblEmployee.getItems().add(employee);
            btnNewEmployee.fire();

        } catch (Exception e) {
            e.printStackTrace();
            new Alert(Alert.AlertType.ERROR,"Faild to save the Employee").showAndWait();
        }


    }

    private boolean isDatavalid() {
        boolean dataValid =true;
        if(!txtSallary.getText().matches("\\d{3,}")){
            txtSallary.requestFocus();
            txtSallary.selectAll();
            txtSallary.getStyleClass().add("invalid");
            dataValid=false;
        }
        if(!txtDesign.getText().matches("[A-za-z ]{3,}")){
            txtDesign.requestFocus();
            txtDesign.selectAll();
            txtSallary.getStyleClass().add("invalid");
            dataValid=false;

        }
        if(lstContact.getItems().size()==0){
            txtContact.requestFocus();
            txtContact.selectAll();
            txtContact.getStyleClass().add("invalid");
            dataValid=false;
        }
        if(dtp.getValue()==null){
            //lblDob.setTextFill(Color.RED);
            dtp.requestFocus();
            dtp.getStyleClass().add("invalid");
            dataValid=false;
        }
        if(tglGender.getSelectedToggle()==null){
            //lblGender.setTextFill(Color.RED);
            rdoMale.requestFocus();
            rdoMale.getStyleClass().add("invalid");
            dataValid=false;

        }
        if(txtAddress.getText().strip().length()<3){
            txtAddress.requestFocus();
            txtAddress.selectAll();
            txtAddress.getStyleClass().add("invalid");
            dataValid=false;
        }
        if (!txtName.getText().matches("[A-za-z]{3,}")){
            txtName.requestFocus();
            txtName.selectAll();
            txtName.getStyleClass().add("invalid");
        }
        return dataValid;
    }

    @FXML
    void btnDeleteOnAction(ActionEvent event) {
        if(tblEmployee.getSelectionModel().getSelectedItem()!=null){
            Connection connection = DBConnection.getInstance().getConnection();
            Employee selectEmployee = tblEmployee.getSelectionModel().getSelectedItem();
            String id = selectEmployee.getId();
            try {
                PreparedStatement stmPic = connection.prepareStatement("DELETE FROM Picture WHERE employee_id=?");
                stmPic.setString(1,id);
                stmPic.executeUpdate();
                PreparedStatement stmContact = connection.prepareStatement("DELETE FROM Contact WHERE employee_id=?");
                stmContact.setString(1,id);
                stmContact.executeUpdate();
                PreparedStatement stm = connection.prepareStatement("DELETE FROM Employee WHERE id=?");
                stm.setString(1,id);
                stm.executeUpdate();

                tblEmployee.getItems().remove(selectEmployee);
                btnNewEmployee.fire();
                //tblEmployee.getSelectionModel().clearSelection();
            } catch (SQLException e) {
                e.printStackTrace();
                new Alert(Alert.AlertType.ERROR,"Failed to Delete employee ");
            }

        }

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
