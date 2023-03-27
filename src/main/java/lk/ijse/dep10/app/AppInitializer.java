package lk.ijse.dep10.app;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import lk.ijse.dep10.app.db.DBConnection;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.HashSet;
import java.util.Set;

public class AppInitializer extends Application {

    public static void main(String[] args) {
        Runtime.getRuntime().addShutdownHook(new Thread(()->{
            try {
                if(DBConnection.getInstance().getConnection() != null && !DBConnection.getInstance().getConnection().isClosed()) {
                    System.out.println("Database connection is about to close");
                    DBConnection.getInstance().getConnection().close();
                }
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }));
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws IOException {
        generateTables();
        boolean adminExist =adminExist();
        String url = adminExist ? "/view/UserLogin.fxml": "/view/SignUpView.fxml";
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource(url));
        AnchorPane root =fxmlLoader.load();
        primaryStage.setScene(new Scene(root));
        primaryStage.centerOnScreen();
        primaryStage.setTitle(url.equals("/view/SignUpView.fxml")? "Sign up Window to the system":"Login Window to the system");
        primaryStage.show();

    }

    private boolean adminExist() {
        try {
            Connection connection = DBConnection.getInstance().getConnection();
            Statement stm = connection.createStatement();
            return stm.executeQuery("SELECT * FROM User").next();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }


    private void generateTables() {
        Connection connection = DBConnection.getInstance().getConnection();

        try {
            Statement stm = connection.createStatement();
            ResultSet rst = stm.executeQuery("SHOW TABLES ");
            HashSet<String> tableSet = new HashSet<>();

            while(rst.next()){
                tableSet.add(rst.getString(1));
            }
            boolean tableExists = tableSet.containsAll(Set.of("User","Employers", "Picture","Contact"));

            if(!tableExists) stm.execute(readDBScript());

        } catch (SQLException e) {
            new Alert(Alert.AlertType.ERROR, "Failed to create tables").showAndWait();
            e.printStackTrace();
        }
    }

    private String readDBScript(){
        System.out.println("gggggg");
        try {
            InputStream is = getClass().getResourceAsStream("/schema.sql");
            BufferedReader br = new BufferedReader(new InputStreamReader(is));
            String line;
            StringBuilder dbScript = new StringBuilder();

            while ((line = br.readLine()) != null) {
                dbScript.append(line).append("\n");
            }
            br.close();
            return dbScript.toString();

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

}