import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class UsersController {

    @FXML
    private Button btnUsrClear;

    @FXML
    private Button btnUsrRestPas;

    @FXML
    private TableColumn<DataGsAndSs, String> colUsrEntryD;

    @FXML
    private ComboBox<String> cmbUsrActivi;

    @FXML
    private TextField txtUsrUsrname;

    @FXML
    private Button btnUsrSearchUpd;

    @FXML
    private TableColumn<DataGsAndSs, String> colUsrActivity;

    @FXML
    private TextField txtUsrSearch;

    @FXML
    private Button btnUsrAdd;

    @FXML
    private ComboBox<String> cmbUsrUtype;

    @FXML
    private Button btnUsrSearch;

    @FXML
    private TableColumn<DataGsAndSs, Integer> colUsrID;

    @FXML
    private TableView<DataGsAndSs> tbleUsrReco;

    @FXML
    private TableColumn<DataGsAndSs, String> colUsrUsernam;

    @FXML
    private TableColumn<DataGsAndSs, String> colUsrUtype;

    @FXML
    private Button btnUsrDeactv;

    @FXML
    private TextField txtUsrStfID;

    @FXML
    private Button btnUsrRefresh;

    Connection con = null;
    PreparedStatement preparedStatement = null;
    ResultSet resultSet = null;

    public UsersController(){
        con = DBcon.conDB();
    }

    ObservableList<String> utype = FXCollections.observableArrayList("Principle","Admin","Teacher","Secretary");
    ObservableList<String> uactivity = FXCollections.observableArrayList("Active","Deactivated");

    private static ObservableList<DataGsAndSs> getUsrObjct(ResultSet rs) throws SQLException, ClassNotFoundException{
        ObservableList<DataGsAndSs> usrList = FXCollections.observableArrayList();
        while (rs.next()){
            DataGsAndSs usrDet = new DataGsAndSs();
            usrDet.setUsrIdProp(rs.getInt("iduser"));
            usrDet.setUsrUsrnamProp(rs.getString("username"));
            usrDet.setUsrUtypeProp(rs.getString("usrtype"));
            usrDet.setUsrActivtyProp(rs.getString("activity"));
            usrDet.setUsrEntryDatProp(rs.getString("entrydate"));
            usrList.add(usrDet);
        }
        return usrList;
    }

    private static ObservableList<DataGsAndSs> getUsrRec() throws SQLException, ClassNotFoundException{
        String sql = "Select * from user";
        ResultSet resS = DBcon.dbExc(sql);
        ObservableList<DataGsAndSs> usrList = getUsrObjct(resS);
        return usrList;
    }

    private void clearAll(){
        txtUsrStfID.clear();
        txtUsrUsrname.clear();
        cmbUsrUtype.setValue("");
        cmbUsrActivi.setValue("Active");
    }

    private boolean confirmEntry(){
        if(txtUsrStfID.getText().isEmpty() || txtUsrUsrname.getText().isEmpty() || cmbUsrUtype.getValue().isEmpty()){
            return false;
        }else {
            return true;
        }
    }

    private void saveFunc(){
        if(confirmEntry()){
            try {
                String st = "INSERT INTO user ( iduser, username, usrtype, activity, entrydate) VALUES (?,?,?,?,?)";
                preparedStatement = (PreparedStatement) con.prepareStatement(st);
                preparedStatement.setString(1, txtUsrStfID.getText());
                preparedStatement.setString(2, txtUsrUsrname.getText());
                preparedStatement.setString(3, cmbUsrUtype.getValue());
                preparedStatement.setString(4, cmbUsrActivi.getValue());
                preparedStatement.setString(5, String.valueOf(java.time.LocalDate.now()));

                preparedStatement.executeUpdate();
                //ConfirmBox.display("Confirmation","Successfully added");
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Information Dialog");
                alert.setHeaderText(null);
                alert.setContentText("Successfully added");
                alert.showAndWait();

                clearAll();

                ObservableList<DataGsAndSs> usrLst = getUsrRec();
                populateTable(usrLst);

            } catch (SQLException | ClassNotFoundException ex) {
                System.out.println(ex.getMessage());
            }
        } else {
            //ConfirmBox.display("Input error","Please fill in all text areas.");
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Something is missing");
            alert.setHeaderText(null);
            alert.setContentText("Please fill in all text areas.");
            alert.showAndWait();
        }
    }

    private void usrSearchToUp() throws SQLException, ClassNotFoundException{
        //clearAll();
        String sql = "SELECT * FROM user Where iduser = ?";
        preparedStatement = con.prepareStatement(sql);
        preparedStatement.setString(1, txtUsrStfID.getText());
        resultSet = preparedStatement.executeQuery();
        if (resultSet.next()) {
            txtUsrUsrname.setText(resultSet.getString("username"));
            cmbUsrUtype.setValue(resultSet.getString("usrtype"));
            cmbUsrActivi.setValue(resultSet.getString("activity"));
        } else {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Search");
            alert.setHeaderText(null);
            alert.setContentText("No result found on inputted search data.");
            alert.showAndWait();
        }
    }

    private boolean doesUsrnameExst() throws SQLException, ClassNotFoundException{
        String sql = "SELECT * FROM user Where username = ?";
        preparedStatement = con.prepareStatement(sql);
        preparedStatement.setString(1, txtUsrUsrname.getText());
        resultSet = preparedStatement.executeQuery();
        if (resultSet.next()) {
            return true;
        } else {
            return false;
        }
    }

    public void userBtnAct() throws SQLException, ClassNotFoundException{
        btnUsrClear.setOnAction(event -> clearAll());
        btnUsrAdd.setOnAction(event -> {
            try {
                if (doesUsrnameExst()){
                    Alert alert = new Alert(Alert.AlertType.INFORMATION);
                    alert.setTitle("Conflict");
                    alert.setHeaderText(null);
                    alert.setContentText("Username is in use.");
                    alert.showAndWait();
                }else {
                    saveFunc();
                    ObservableList<DataGsAndSs> usrLst = getUsrRec();
                    populateTable(usrLst);
                }
            } catch (SQLException | ClassNotFoundException e) {
                e.printStackTrace();
            }
        });
        btnUsrSearchUpd.setOnAction(event -> {
            try {
                usrSearchToUp();
            } catch (SQLException | ClassNotFoundException e) {
                e.printStackTrace();
            }
        });
        btnUsrRestPas.setOnAction(event -> {
            if(confirmEntry()){
                try {
                    String st = "update user set password = ? where iduser = ?";
                    PreparedStatement ps;
                    ps = (PreparedStatement) con.prepareStatement(st);
                    ps.setString(1, "");
                    ps.setString(2, txtUsrStfID.getText());
                    ps.executeUpdate();

                    Alert alert = new Alert(Alert.AlertType.INFORMATION);
                    alert.setTitle("Information Dialog");
                    alert.setHeaderText(null);
                    alert.setContentText("Successfully reset");
                    alert.showAndWait();

                    clearAll();
                } catch (SQLException ex) {
                    System.out.println(ex.getMessage());
                }
            } else {
                Alert alert = new Alert(Alert.AlertType.WARNING);
                alert.setTitle("Something is missing");
                alert.setHeaderText(null);
                alert.setContentText("Please fill in all text areas.");
                alert.showAndWait();
            }
        });
        btnUsrDeactv.setOnAction(event -> {
            if(confirmEntry()){
                try {
                    String st = "update user set activity = ? where iduser = ?";
                    PreparedStatement ps;
                    ps = (PreparedStatement) con.prepareStatement(st);
                    ps.setString(1, "Deactivated");
                    ps.setString(2, txtUsrStfID.getText());
                    ps.executeUpdate();

                    Alert alert = new Alert(Alert.AlertType.INFORMATION);
                    alert.setTitle("Information Dialog");
                    alert.setHeaderText(null);
                    alert.setContentText("Successfully deactivated");
                    alert.showAndWait();

                    clearAll();
                    ObservableList<DataGsAndSs> usrLst = getUsrRec();
                    populateTable(usrLst);
                } catch (SQLException | ClassNotFoundException ex) {
                    System.out.println(ex.getMessage());
                }
            } else {
                Alert alert = new Alert(Alert.AlertType.WARNING);
                alert.setTitle("Something is missing");
                alert.setHeaderText(null);
                alert.setContentText("Please fill in all text areas.");
                alert.showAndWait();
            }
        });
        btnUsrSearch.setOnAction(event -> {
            try {
                if(txtUsrSearch.getText().isEmpty()){
                    Alert alert = new Alert(Alert.AlertType.WARNING);
                    alert.setTitle("Something is missing");
                    alert.setHeaderText(null);
                    alert.setContentText("Please input search data.");
                    alert.showAndWait();
                }else {
                    ObservableList<DataGsAndSs> list = searchUser(txtUsrSearch.getText());
                    if (list.size() > 0){
                        populateTable(list);
                    }else {
                        Alert alert = new Alert(Alert.AlertType.INFORMATION);
                        alert.setTitle("Search");
                        alert.setHeaderText(null);
                        alert.setContentText("No result found on inputted search data.");
                        alert.showAndWait();
                    }
                }
            } catch (SQLException | ClassNotFoundException e) {
                e.printStackTrace();
            }
        });
        btnUsrRefresh.setOnAction(event -> {
            try {
                ObservableList<DataGsAndSs> usrLst = getUsrRec();
                populateTable(usrLst);
            } catch (SQLException | ClassNotFoundException e) {
                e.printStackTrace();
            }
        });
    }

    private static ObservableList<DataGsAndSs> searchUser(String serV) throws SQLException, ClassNotFoundException{
        String sql = "Select * from psms.user where '"+serV+"' IN(iduser, username, usrtype, activity, entrydate)";
        ResultSet rs = DBcon.dbExc(sql);
        return getUsrObjct(rs);
    }

    @FXML
    public void initialize() throws SQLException, ClassNotFoundException {
        colUsrID.setCellValueFactory(cellData -> cellData.getValue().usrIdPropProperty().asObject());
        colUsrUsernam.setCellValueFactory(cellData -> cellData.getValue().usrUsrnamPropProperty());
        colUsrUtype.setCellValueFactory(cellData -> cellData.getValue().usrUtypePropProperty());
        colUsrActivity.setCellValueFactory(cellData -> cellData.getValue().usrActivtyPropProperty());
        colUsrEntryD.setCellValueFactory(cellData -> cellData.getValue().usrEntryDatPropProperty());

        ObservableList<DataGsAndSs> usrLst = getUsrRec();
        populateTable(usrLst);

        cmbUsrActivi.setItems(uactivity);
        cmbUsrUtype.setItems(utype);
        cmbUsrActivi.setValue("Active");

        userBtnAct();
    }

    private void populateTable(ObservableList<DataGsAndSs> usrLst) {
        tbleUsrReco.setItems(usrLst);
    }
}

