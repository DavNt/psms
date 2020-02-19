import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class ClassesController {

    @FXML
    private TextField txtAClsTchNam;

    @FXML
    private TableView<DataGsAndSs> tbleClsARecrd;

    @FXML
    private Button btnAClsClear;

    @FXML
    private TableColumn<DataGsAndSs, Integer> colCARstdID;

    @FXML
    private Button btnClsAclear;

    @FXML
    private Button btnAClsAdd;

    @FXML
    private TableColumn<DataGsAndSs, String> colCARstdNam;

    @FXML
    private Button btnClsDAdeallocte;

    @FXML
    private TableColumn<DataGsAndSs, String> colClsRentryD;

    @FXML
    private TableColumn<DataGsAndSs, Integer> colClsRid;

    @FXML
    private TableColumn<DataGsAndSs, Integer> colClsRstdNo;

    @FXML
    private ComboBox<String> cmbClsAgrade;

    @FXML
    private TextField txtClsAstdNam;

    @FXML
    private Button btnAClsSrch;

    @FXML
    private TextField txtAClsGrdSrch;

    @FXML
    private TableColumn<DataGsAndSs, String> colClsRteachid;

    @FXML
    private Button btnAClsUpdate;

    @FXML
    private TableColumn<DataGsAndSs, String> colClsRteachSurn;

    @FXML
    private TableView<DataGsAndSs> tbleClsRecords;

    @FXML
    private TextField txtAClsTchID;

    @FXML
    private TextField txtClsAstdID;

    @FXML
    private TableColumn<DataGsAndSs, String> colClsRgrade;

    @FXML
    private Button btnClsDAsrch;

    @FXML
    private TextField txtClsDAsrch;

    @FXML
    private Button btnClsAallocte;

    @FXML
    private TableColumn<DataGsAndSs, String> colCARgrade;

    @FXML
    private TableColumn<DataGsAndSs, String> colClsRupdateD;

    @FXML
    private TableColumn<DataGsAndSs, String> colCARupdateD;

    @FXML
    private Button btnClsAreallo;

    @FXML
    private TextField txtAClsGrade;

    Connection con = null;
    PreparedStatement preparedStatement = null;
    ResultSet resultSet = null;

    public ClassesController(){
        con = DBcon.conDB();
    }

    private void clsBtnAct(){
        //class buttons
        btnAClsClear.setOnAction(event -> adclsClear());
        btnClsAclear.setOnAction(event -> clsAclear());
        btnClsAallocte.setOnAction(event -> clsAsaveFunc());
        btnClsDAdeallocte.setOnAction(event -> {
            clsDealloFunc();
            btnClsAallocte.setDisable(false);
        });
        btnClsDAsrch.setOnAction(event -> {
            try {
                clsSrchToDeallo();
                String sql = "SELECT clasnam FROM classallo Where idstud = ? and clasnam = 'DE-ALLOCATED'";
                preparedStatement = con.prepareStatement(sql);
                preparedStatement.setString(1, txtClsAstdID.getText());
                resultSet = preparedStatement.executeQuery();
                if (resultSet.next()) {
                    btnClsAreallo.setDisable(false);
                    btnClsDAdeallocte.setDisable(true);
                    btnClsAallocte.setDisable(true);
                } else {
                    btnClsAallocte.setDisable(true);
                    btnClsAreallo.setDisable(true);
                    btnClsDAdeallocte.setDisable(false);
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        });
        btnAClsAdd.setOnAction(event -> aClsAdd());
        txtAClsTchID.setOnKeyReleased(event -> {
            try {
                String sql = "SELECT * FROM stuff Where idstuff = ?";
                preparedStatement = con.prepareStatement(sql);
                preparedStatement.setString(1, txtAClsTchID.getText());
                resultSet = preparedStatement.executeQuery();
                while (resultSet.next()) {
                    txtAClsTchNam.setEditable(false);
                    txtAClsTchNam.setText(resultSet.getString("surname"));
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        });
        btnAClsSrch.setOnAction(event -> {
            try {
                aClsSrchToUpdat();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        });
        btnAClsUpdate.setOnAction(event -> aClsUpdateFunc());
        btnClsAreallo.setOnAction(event -> {
            if(cmbClsAgrade.getValue().equalsIgnoreCase("DE-ALLOCATED")){
                Alert alert = new Alert(Alert.AlertType.WARNING);
                alert.setTitle("No class selected");
                alert.setHeaderText(null);
                alert.setContentText("Please select a class to reallocate to.");
                alert.showAndWait();
            }else {
                clsAReallo();
            }

        });
    }

    private final ObservableList<String> clsGrade = FXCollections.observableArrayList();
    public void fillGrade() throws SQLException, ClassNotFoundException {
        String sql = "SELECT grade FROM classes";
        preparedStatement = con.prepareStatement(sql);
        resultSet = preparedStatement.executeQuery();
        while (resultSet.next()) {
            clsGrade.add(resultSet.getString("grade"));
        }
    }

    private void clsAclear(){
        txtClsAstdID.clear();
        txtClsAstdNam.clear();
        cmbClsAgrade.setValue("");
    }
    private void adclsClear(){
        txtAClsGrade.clear();
        txtAClsTchID.clear();
        txtAClsTchNam.clear();
    }

    private boolean clsAconfirmEntry(){
        if(txtClsAstdID.getText().isEmpty() || txtClsAstdNam.getText().isEmpty()){
            return false;
        }else {
            return true;
        }
    }
    private boolean adclsConfirmEntry(){
        if(txtAClsGrade.getText().isEmpty() || txtAClsTchID.getText().isEmpty() || txtAClsTchNam.getText().isEmpty()){
            return false;
        }else {
            return true;
        }
    }

    private void clsAsaveFunc(){
        if(clsAconfirmEntry()) {
            try {
                /*String sql = "SELECT clasnam FROM classallo Where idstud = ? and clasnam = 'DE-ALLOCATED'";
                preparedStatement = con.prepareStatement(sql);
                preparedStatement.setString(1, txtClsAstdID.getText());
                resultSet = preparedStatement.executeQuery();
                if (resultSet.next()) {
                    Alert alert = new Alert(Alert.AlertType.WARNING);
                    alert.setTitle("Information Dialog");
                    alert.setHeaderText(null);
                    alert.setContentText("Duplicate student for allocation ");
                    alert.showAndWait();
                } else {*/
                    String st = "INSERT INTO classallo ( idstud, studnam, clasnam, updatedate) VALUES (?,?,?,?)";
                    preparedStatement = con.prepareStatement(st);
                    preparedStatement.setString(1, txtClsAstdID.getText());
                    preparedStatement.setString(2, txtClsAstdNam.getText());
                    preparedStatement.setString(3, cmbClsAgrade.getValue());
                    preparedStatement.setString(4, String.valueOf(java.time.LocalDate.now()));
                    preparedStatement.executeUpdate();

                    String st2 = "update classes set studNo = studNo + 1, updatedate = ? where grade = ?";
                    preparedStatement = con.prepareStatement(st2);
                    preparedStatement.setString(1, String.valueOf(java.time.LocalDate.now()));
                    preparedStatement.setString(2, cmbClsAgrade.getValue());
                    preparedStatement.executeUpdate();

                    Alert alert = new Alert(Alert.AlertType.INFORMATION);
                    alert.setTitle("Information Dialog");
                    alert.setHeaderText(null);
                    alert.setContentText("Successfully allocated");
                    alert.showAndWait();

                    clsAclear();
                    refreshTables();
            //    }
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
    }

    private void clsDealloFunc(){
        if(clsAconfirmEntry()){
            if(!txtClsDAsrch.getText().isEmpty()){
                try {
                    String st2 = "update classes set studNo = studNo - 1 where grade = ?";
                    preparedStatement = con.prepareStatement(st2);
                    preparedStatement.setString(1, cmbClsAgrade.getValue());
                    preparedStatement.executeUpdate();

                    String st = "update classallo set clasnam = ?, updatedate = ? where idstud = ?";
                    PreparedStatement ps;
                    ps = con.prepareStatement(st);
                    ps.setString(1, "DE-ALLOCATED");
                    ps.setString(2, String.valueOf(java.time.LocalDate.now()));
                    ps.setString(3, txtClsDAsrch.getText());
                    ps.executeUpdate();

                    Alert alert = new Alert(Alert.AlertType.INFORMATION);
                    alert.setTitle("Information Dialog");
                    alert.setHeaderText(null);
                    alert.setContentText("Successfully de-allocated");
                    alert.showAndWait();

                    clsAclear();
                    txtClsDAsrch.clear();
                    txtClsAstdID.setEditable(true);
                    refreshTables();
                } catch (SQLException ex) {
                    System.out.println(ex.getMessage());
                }
            }
        } else {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Something is missing");
            alert.setHeaderText(null);
            alert.setContentText("Please fill in all text areas.");
            alert.showAndWait();
        }
    }

    private void clsSrchToDeallo() throws SQLException {
        String sql = "SELECT * FROM classallo Where idstud = ?";
        preparedStatement = con.prepareStatement(sql);
        preparedStatement.setString(1, txtClsDAsrch.getText());
        resultSet = preparedStatement.executeQuery();
        if (resultSet.next()) {
            txtClsAstdID.setEditable(false);
            txtClsAstdID.setText(resultSet.getString("idstud"));
            txtClsAstdNam.setText(resultSet.getString("studnam"));
            cmbClsAgrade.setValue(resultSet.getString("clasnam"));
        } else {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Search");
            alert.setHeaderText(null);
            alert.setContentText("No result found on inputted search data.");
            alert.showAndWait();
        }
    }

    private void clsAReallo(){
        if(clsAconfirmEntry()){
            try {
                String st = "update classallo set clasnam = ?, updatedate = ? where idstud = ?";
                PreparedStatement ps;
                ps = con.prepareStatement(st);
                ps.setString(1, cmbClsAgrade.getValue());
                ps.setString(2, String.valueOf(java.time.LocalDate.now()));
                ps.setString(3, txtClsDAsrch.getText());
                ps.executeUpdate();

                String st2 = "update classes set studNo = studNo + 1, updatedate = ? where grade = ?";
                preparedStatement = con.prepareStatement(st2);
                preparedStatement.setString(1, String.valueOf(java.time.LocalDate.now()));
                preparedStatement.setString(2, cmbClsAgrade.getValue());
                preparedStatement.executeUpdate();

                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Information Dialog");
                alert.setHeaderText(null);
                alert.setContentText("Successfully Re-allocated");
                alert.showAndWait();

                clsAclear();
                txtClsDAsrch.clear();
                refreshTables();
                btnClsDAdeallocte.setDisable(false);
                btnClsAallocte.setDisable(false);
                btnClsAreallo.setDisable(true);
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
    }

    private void aClsAdd(){
        if(adclsConfirmEntry()) {
            try {
                String sql = "SELECT grade FROM classes Where grade = ?";
                preparedStatement = con.prepareStatement(sql);
                preparedStatement.setString(1, txtAClsGrade.getText());
                resultSet = preparedStatement.executeQuery();
                if (resultSet.next()) {
                    Alert alert = new Alert(Alert.AlertType.WARNING);
                    alert.setTitle("Information Dialog");
                    alert.setHeaderText(null);
                    alert.setContentText("Duplicate, class exists. ");
                    alert.showAndWait();
                } else {
                    String st = "INSERT INTO classes ( grade, clasTechID, clasTechName, entrydate) VALUES (?,?,?,?)";
                    preparedStatement = con.prepareStatement(st);
                    preparedStatement.setString(1, txtAClsGrade.getText());
                    preparedStatement.setString(2, txtAClsTchID.getText());
                    preparedStatement.setString(3, txtAClsTchNam.getText());
                    preparedStatement.setString(4, String.valueOf(java.time.LocalDate.now()));
                    preparedStatement.executeUpdate();

                    Alert alert = new Alert(Alert.AlertType.INFORMATION);
                    alert.setTitle("Information Dialog");
                    alert.setHeaderText(null);
                    alert.setContentText("Successfully allocated");
                    alert.showAndWait();

                    adclsClear();
                    refreshTables();
                }
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
    }

    private void aClsSrchToUpdat() throws SQLException {
        String sql = "SELECT * FROM classes Where grade = ?";
        preparedStatement = con.prepareStatement(sql);
        preparedStatement.setString(1, txtAClsGrdSrch.getText());
        resultSet = preparedStatement.executeQuery();
        if (resultSet.next()) {
            txtAClsGrade.setEditable(false);
            txtAClsGrade.setText(resultSet.getString("grade"));
            txtAClsTchID.setText(resultSet.getString("clasTechID"));
            txtAClsTchNam.setText(resultSet.getString("clasTechName"));
        } else {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Search");
            alert.setHeaderText(null);
            alert.setContentText("No result found on inputted search data.");
            alert.showAndWait();
        }
    }

    private void aClsUpdateFunc(){
        if(adclsConfirmEntry()){
            if(!txtClsDAsrch.getText().isEmpty()){
                try {
                    String st = "update classes set clasTechID = ?, clasTechName = ? ,updatedate = ? where grade = ?";
                    PreparedStatement ps;
                    ps = con.prepareStatement(st);
                    ps.setString(3, txtAClsTchID.getText());
                    ps.setString(3, txtAClsTchNam.getText());
                    ps.setString(2, String.valueOf(java.time.LocalDate.now()));
                    ps.setString(3, txtAClsGrdSrch.getText());
                    ps.executeUpdate();

                    Alert alert = new Alert(Alert.AlertType.INFORMATION);
                    alert.setTitle("Information Dialog");
                    alert.setHeaderText(null);
                    alert.setContentText("Successfully updated ");
                    alert.showAndWait();

                    adclsClear();
                    txtAClsGrdSrch.clear();
                    //txtAClsTchNam.setEditable(true);
                } catch (SQLException ex) {
                    System.out.println(ex.getMessage());
                }
            }
        } else {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Something is missing");
            alert.setHeaderText(null);
            alert.setContentText("Please fill in all text areas.");
            alert.showAndWait();
        }
    }

    private static ObservableList<DataGsAndSs> getClasObjct(ResultSet rs) throws SQLException {
        ObservableList<DataGsAndSs> clsList = FXCollections.observableArrayList();
        while (rs.next()){
            DataGsAndSs clsDet = new DataGsAndSs();
            //clsDet.setClsIDProp(rs.getInt("idclasses"));
            clsDet.setClsGradProp(rs.getString("grade"));
            clsDet.setClsTchIDProp(rs.getString("clasTechID"));
            clsDet.setClsTchSurnProp(rs.getString("clasTechName"));
            clsDet.setClsStdNumProp(rs.getInt("studNo"));
            clsDet.setClsEntrydProp(rs.getString("entrydate"));
            clsDet.setClsUpdatedProp(rs.getString("updatedate"));
            clsList.add(clsDet);
        }
        return clsList;
    }

    private static ObservableList<DataGsAndSs> getClasRec() throws SQLException {
        String sql = "Select * from classes";
        ResultSet resS = DBcon.dbExc(sql);
        ObservableList<DataGsAndSs> clsList = getClasObjct(resS);
        return clsList;
    }

    private static ObservableList<DataGsAndSs> getCAlloObjct(ResultSet rs) throws SQLException {
        ObservableList<DataGsAndSs> calloList = FXCollections.observableArrayList();
        while (rs.next()){
            DataGsAndSs calloDet = new DataGsAndSs();
            calloDet.setCalloStdidProp(rs.getInt("idstud"));
            calloDet.setCalloStdNamProp(rs.getString("studnam"));
            calloDet.setCalloGradProp(rs.getString("clasnam"));
            calloDet.setCalloUpdatedProp(rs.getString("updatedate"));
            calloList.add(calloDet);
        }
        return calloList;
    }

    private static ObservableList<DataGsAndSs> getCAlloRec() throws SQLException {
        String sql = "Select * from classallo";
        ResultSet resS = DBcon.dbExc(sql);
        ObservableList<DataGsAndSs> calloList = getCAlloObjct(resS);
        return calloList;
    }

    @FXML
    public void initialize() throws SQLException, ClassNotFoundException {
        //class record
        //colClsRid.setCellValueFactory(cellData -> cellData.getValue().clsIDPropProperty().asObject());
        colClsRgrade.setCellValueFactory(cellData -> cellData.getValue().clsGradPropProperty());
        colClsRteachid.setCellValueFactory(cellData -> cellData.getValue().clsTchIDPropProperty());
        colClsRteachSurn.setCellValueFactory(cellData -> cellData.getValue().clsTchSurnPropProperty());
        colClsRstdNo.setCellValueFactory(cellData -> cellData.getValue().clsStdNumPropProperty().asObject());
        colClsRentryD.setCellValueFactory(cellData -> cellData.getValue().clsEntrydPropProperty());
        colClsRupdateD.setCellValueFactory(cellData -> cellData.getValue().clsUpdatedPropProperty());
        //class alloc
        colCARstdID.setCellValueFactory(cellData -> cellData.getValue().calloStdidPropProperty().asObject());
        colCARstdNam.setCellValueFactory(cellData -> cellData.getValue().calloStdNamPropProperty());
        colCARgrade.setCellValueFactory(cellData -> cellData.getValue().calloGradPropProperty());
        colCARupdateD.setCellValueFactory(cellData -> cellData.getValue().calloUpdatedPropProperty());

        refreshTables();

        clsBtnAct();

        fillGrade();
        cmbClsAgrade.setItems(clsGrade);
        txtAClsTchNam.setEditable(false);
    }

    private void refreshTables() throws SQLException {
        //class record c
        ObservableList<DataGsAndSs> clsLst = getClasRec();
        populateClasTable(clsLst);
        //class alloc
        ObservableList<DataGsAndSs> calloLst = getCAlloRec();
        populateCAlloTable(calloLst);
    }

    private void populateClasTable(ObservableList<DataGsAndSs> clsLst) {
        tbleClsRecords.setItems(clsLst);
    }

    private void populateCAlloTable(ObservableList<DataGsAndSs> calloLst) {
        tbleClsARecrd.setItems(calloLst);
    }

}

