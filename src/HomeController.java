import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;

public class HomeController implements Initializable {

    @FXML
    private Button btnClasSchew;

    @FXML
    private Button btnTeachw;

    @FXML
    private Button btnSubjw;

    @FXML
    private Button btnLogout;

    @FXML
    private Label lblUserLog;

    @FXML
    private Button btnStudw;

    @FXML
    private Button btnStaffw;

    @FXML
    private Button btnStudResw;

    @FXML
    private Button btnClasw;

    @FXML
    private Label lblStatus;

    @FXML
    private Button btnUserw;

    Connection con = null;
    PreparedStatement preparedStatement = null;
    ResultSet resultSet = null;

    public HomeController(){ con = DBcon.conDB(); }

    @FXML
    public void homBtnAction(){
        btnClasw.setOnAction(event -> {
            try {
                Stage stage = new Stage();
                stage.setScene(new Scene(FXMLLoader.load(getClass().getResource("FXMLClasses.fxml"))));
                stage.setTitle("Manage Classes");
                stage.show();
            } catch (IOException e) {
                e.printStackTrace();
            }
        });
        btnStudResw.setOnAction(event -> {
            try {
                Stage stage = new Stage();
                stage.setScene(new Scene(FXMLLoader.load(getClass().getResource("FXMLReportCard.fxml"))));
                stage.setTitle("Manage Student Results");
                stage.show();
            } catch (IOException e) {
                e.printStackTrace();
            }
        });
        btnStudw.setOnAction(event -> {
            try {
                logdNam = LoginController.getInstance().username();

                FXMLLoader loader = new FXMLLoader(getClass().getResource("FXMLStudent.fxml"));
                Parent root = loader.load();
                Stage stage = new Stage();
                String sql2 = "select usrtype from psms.user where username = ? and usrtype='Teacher'";
                preparedStatement = con.prepareStatement(sql2);
                preparedStatement.setString(1, logdNam);
                resultSet = preparedStatement.executeQuery();
                if (resultSet.next()) {
                    //Get controller of scene2
                    //Pass whatever data you want. You can have multiple method calls here
                    StudentController studContr = loader.getController();
                    studContr.teachMustNotC();
                }
                Scene scene = null;
                scene = new Scene(root);
                stage.setScene(scene);
                stage.setTitle("Manage Students");
                stage.show();
            } catch (IOException | SQLException e) {
                e.printStackTrace();
            }
        });
        btnSubjw.setOnAction(event -> {
            try {
                Stage stage = new Stage();
                stage.setScene(new Scene(FXMLLoader.load(getClass().getResource("FXMLSubjcts.fxml"))));
                stage.setTitle("Manage Subjects");
                stage.show();
            } catch (IOException e) {
                e.printStackTrace();
            }
        });
        btnTeachw.setOnAction(event -> {
            try {
                Stage stage = new Stage();
                stage.setScene(new Scene(FXMLLoader.load(getClass().getResource("FXMLTeach.fxml"))));
                stage.setTitle("Manage Staff");
                stage.show();
            } catch (IOException e) {
                e.printStackTrace();
            }
        });
        btnUserw.setOnAction(event -> {
            try {
                Stage stage = new Stage();
                stage.setScene(new Scene(FXMLLoader.load(getClass().getResource("FXMLUsers.fxml"))));
                stage.setTitle("Manage Users");
                stage.show();
            } catch (IOException e) {
                e.printStackTrace();
            }
        });
        btnLogout.setOnAction(event -> {
            try {
                Node node = (Node) event.getSource();
                Stage stage = (Stage) node.getScene().getWindow();
                //stage.setMaximized(true);
                stage.close();

                Scene scene = null;
                scene = new Scene(FXMLLoader.load(getClass().getResource("FXMLLogin.fxml")));
                stage.setScene(scene);
                stage.show();
            } catch (IOException e) {
                e.printStackTrace();
            }
        });
    }

    public void hideSomethings(){
        btnTeachw.setVisible(false);
        btnUserw.setVisible(false);
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        if (con == null) {
            lblStatus.setTextFill(Color.TOMATO);
            lblStatus.setText("Server Error : Check");
        } else {
            lblStatus.setTextFill(Color.GREEN);
            lblStatus.setText("Server is up : Good to go");
        }

        setUsername(LoginController.getInstance().username());

        homBtnAction();

        btnStudResw.setVisible(false);
    }

    String logdNam;

    public void setUsername(String user){
        this.lblUserLog.setText("Logged in as: " + user);
    }
}

