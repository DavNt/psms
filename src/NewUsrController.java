import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;

public class NewUsrController implements Initializable {

    @FXML
    private TextField txtNewUsrUnam;

    @FXML
    private PasswordField txtNwUsrConfmPass;

    @FXML
    private PasswordField txtNwUsrPasswrd;

    @FXML
    private Button btnNwUsrSave;

    Connection con = null;
    PreparedStatement preparedStatement = null;
    ResultSet resultSet = null;

    public NewUsrController(){
        con = DBcon.conDB();
    }

    private void clear(){
        txtNewUsrUnam.clear();
        txtNwUsrPasswrd.clear();
        txtNwUsrConfmPass.clear();
    }

    public void nwUsrBtnAct(){
        btnNwUsrSave.setOnAction(event -> {
            if (txtNewUsrUnam.getText().isEmpty() || txtNwUsrPasswrd.getText().isEmpty() || txtNwUsrConfmPass.getText().isEmpty()){
                Alert alert = new Alert(Alert.AlertType.WARNING);
                alert.setTitle("Missing Data");
                alert.setHeaderText(null);
                alert.setContentText("Please fill in all fields.");
                alert.showAndWait();
            } else {
                if (txtNwUsrPasswrd.getText().equals(txtNwUsrConfmPass.getText())){
                    try {
                        String sql = "SELECT * FROM user Where username = ?";
                        preparedStatement = con.prepareStatement(sql);
                        preparedStatement.setString(1, txtNewUsrUnam.getText());
                        resultSet = preparedStatement.executeQuery();
                        if (resultSet.next()) {
                            String st = "update user set password = ? where username = ?";
                            PreparedStatement ps;
                            ps = (PreparedStatement) con.prepareStatement(st);
                            ps.setString(1, txtNwUsrConfmPass.getText());
                            ps.setString(2, txtNewUsrUnam.getText());
                            ps.executeUpdate();

                            Alert alert = new Alert(Alert.AlertType.INFORMATION);
                            alert.setTitle("Information Dialog");
                            alert.setHeaderText(null);
                            alert.setContentText("Successfully updated");
                            alert.showAndWait();

                            clear();
                            Node node = (Node) event.getSource();
                            Stage stage = (Stage) node.getScene().getWindow();
                            stage.close();
                        } else {
                            Alert alert = new Alert(Alert.AlertType.WARNING);
                            alert.setTitle("User");
                            alert.setHeaderText(null);
                            alert.setContentText("Username entered is not recognised.");
                            alert.showAndWait();
                        }

                    } catch (SQLException e) {
                        e.printStackTrace();
                    }
                }else {
                    Alert alert = new Alert(Alert.AlertType.WARNING);
                    alert.setTitle("Password Error");
                    alert.setHeaderText(null);
                    alert.setContentText("Passwords do not match.");
                    alert.showAndWait();
                }
            }
        });
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        nwUsrBtnAct();
    }
}

