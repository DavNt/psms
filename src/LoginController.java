import javafx.application.Platform;
import javafx.embed.swing.JFXPanel;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;

import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;

public class LoginController implements Initializable {

    @FXML
    private Button exitBtn;
    @FXML
    private Button loginButton;
    @FXML
    private TextField passInput;
    @FXML
    private TextField nameInput;
    @FXML
    private Label lblErrors;
    @FXML
    private Button btnLgNwUsr;
    @FXML
    private Label lblLgForgotPas;

    Connection con = null;
    PreparedStatement preparedStatement = null;
    ResultSet resultSet = null;

    private static LoginController inst;

    public static LoginController getInstance(){
        return inst;
    }

    public String username(){
        return nameInput.getText();
    }

    public LoginController(){
        con = DBcon.conDB();
        inst = this;
    }

    @FXML public void loginBtnAct(ActionEvent event) throws SQLException {
        if(logsin().equals("Success")){
            try {
                String sql0 = "select activity from psms.user where username = ? and activity='Active'";
                preparedStatement = con.prepareStatement(sql0);
                preparedStatement.setString(1, nameInput.getText());
                resultSet = preparedStatement.executeQuery();
                if (resultSet.next()) {
                    Node node = (Node) event.getSource();
                    Stage stage = (Stage) node.getScene().getWindow();
                    //stage.setMaximized(true);
                    stage.close();

                    FXMLLoader loader = new FXMLLoader(getClass().getResource("FXMLHome.fxml"));
                    Parent root = loader.load();

                    String sql = "select usrtype from psms.user where username = ? and usrtype='Secretary'";
                    preparedStatement = con.prepareStatement(sql);
                    preparedStatement.setString(1, nameInput.getText());
                    resultSet = preparedStatement.executeQuery();
                    if (resultSet.next()) {
                        //Get controller of scene2
                        HomeController homContr = loader.getController();
                        //Pass whatever data you want. You can have multiple method calls here
                        homContr.hideSomethings();
                    }
                    String sql2 = "select usrtype from psms.user where username = ? and usrtype='Teacher'";
                    preparedStatement = con.prepareStatement(sql2);
                    preparedStatement.setString(1, nameInput.getText());
                    resultSet = preparedStatement.executeQuery();
                    if (resultSet.next()) {
                        //Get controller of scene2
                        HomeController homContr = loader.getController();
                        //Pass whatever data you want. You can have multiple method calls here
                        homContr.hideSomethings();
                    }
                    Scene scene = null;
                    scene = new Scene(root);
                    stage.setScene(scene);
                    stage.setOnCloseRequest(event1 -> Platform.exit());
                    stage.setTitle("DGMC Primary School Management");
                    stage.show();
                }else {
                    Alert alert = new Alert(Alert.AlertType.WARNING);
                    alert.setTitle("Account Problem");
                    alert.setHeaderText(null);
                    alert.setContentText("Account is deactivated.");
                    alert.showAndWait();
                }
            } catch (IOException | SQLException e) {
                e.printStackTrace();
            }
        }
    }
    @FXML public void lginBtnExit(ActionEvent event){
            Node node = (Node) event.getSource();
            Stage stage = (Stage) node.getScene().getWindow();
            stage.close();
        Platform.exit();
    }

    private String logsin() throws SQLException {
        String status = "Success";
        String usrnam = nameInput.getText();
        String password = passInput.getText();
        if(usrnam.isEmpty() || password.isEmpty()) {
            setLblError(Color.TOMATO, "Empty credentials");
            status = "Error";
        } else {
            try {
                    //query
                    String sql = "SELECT * FROM user Where username = ? and password = ?";
                    preparedStatement = con.prepareStatement(sql);
                    preparedStatement.setString(1, usrnam);
                    preparedStatement.setString(2, password);
                    resultSet = preparedStatement.executeQuery();
                    if (!resultSet.next()) {
                        setLblError(Color.TOMATO, "Enter Correct Username/Password");
                        status = "Error";
                    } else {
                        setLblError(Color.GREEN, "Login Successful..Redirecting..");
                    }
            } catch (SQLException ex) {
                System.err.println(ex.getMessage());
                status = "Exception";
            }
        }

        return status;
    }

    private void setLblError(Color color, String text) {
        lblErrors.setTextFill(color);
        lblErrors.setText(text);
        System.out.println(text);
    }

    @FXML public void newUsrBtn(ActionEvent event) throws IOException {
        //FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("FXMLNewUsr.fxml"));
        //Parent root1 = (Parent) fxmlLoader.load();
        Stage stage = new Stage();
        stage.setScene(new Scene(FXMLLoader.load(getClass().getResource("FXMLNewUsr.fxml"))));
        stage.setTitle("New User");
        stage.show();

        /*Node node = (Node) event.getSource();
        Stage stage = (Stage) node.getScene().getWindow();
        //stage.setMaximized(true);
        stage.close();

        Scene scene = null;
        scene = new Scene(FXMLLoader.load(getClass().getResource("FXMLHome.fxml")));
        stage.setScene(scene);
        stage.show();*/
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        if (con == null) {
            lblErrors.setTextFill(Color.TOMATO);
            lblErrors.setText("Server Error : Check");
        } else {
            lblErrors.setTextFill(Color.GREEN);
            lblErrors.setText("Server is up : Good to go");
        }

        lblLgForgotPas.setOnMouseClicked(event -> {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Information Dialog");
            alert.setHeaderText(null);
            alert.setContentText("Contact your system administrator for assistance.");
            alert.showAndWait();
        });
        /*Stage stage = new Stage();
        stage.getScene().getWindow().setOnCloseRequest(event -> {
            event.consume();
            Platform.exit();
        });*/
    }
}
