import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class StudentController {

    @FXML
    private TableView<DataGsAndSs> stdTable;

    @FXML
    private TextField txtStdAnokDescr;

    @FXML
    private RadioButton rbStdAsxMale;

    @FXML
    private Button btnStdAsave;

    @FXML
    private TableColumn<DataGsAndSs, String> colStdTnokDescr;

    @FXML
    private TableColumn<DataGsAndSs, String> colStdTnokNatID;

    @FXML
    private TextField txtStdAnokNatID;

    @FXML
    private TableColumn<DataGsAndSs, String> colStdTupdateD;

    @FXML
    private TextField txtStdAnokSurnam;

    @FXML
    private TableColumn<DataGsAndSs, String> colStdTnokContct;

    @FXML
    private DatePicker dtpStdAdob;

    @FXML
    private TextArea txtStdAaddres;

    @FXML
    private TextField txtStdAnam;

    @FXML
    private Button btnStdSearch;

    @FXML
    private TextField txtStdSearch;

    @FXML
    private TableColumn<DataGsAndSs, String> colStdTnam;

    @FXML
    private TableColumn<DataGsAndSs, String> colStdTentryD;

    @FXML
    private TableColumn<DataGsAndSs, String> colStdTnokAddres;

    @FXML
    private TableColumn<DataGsAndSs, String> colStdTsurnam;

    @FXML
    private RadioButton rbStdAsxFemale;

    @FXML
    private TableColumn<DataGsAndSs, Integer> colStdTid;

    @FXML
    private TableColumn<DataGsAndSs, String> colStdTnokNam;

    @FXML
    private TextField txtStdAnatID;

    @FXML
    private TableColumn<DataGsAndSs, String> colStdTaddres;

    @FXML
    private Button btnStdUpdUpdate;

    @FXML
    private TextField txtStdAnokContct;

    @FXML
    private TableColumn<DataGsAndSs, String> colStdTnokSurnam;

    @FXML
    private TableColumn<DataGsAndSs, String> colStdTgender;

    @FXML
    private TableColumn<DataGsAndSs, String> colStdTdob;

    @FXML
    private Button btnStdUpdSearch;

    @FXML
    private Button btnStdTrefresh;

    @FXML
    private TableColumn<DataGsAndSs, String> colStdTnatID;

    @FXML
    private Button btnStdAclear;

    @FXML
    private TextField txtStdAnokNam;

    @FXML
    private TextArea txtStdAnokAddres;

    @FXML
    private TextField txtStdAsurnam;

    @FXML
    private TextField txtStdSeUpsearc;

    private String gender = "";

    @FXML
    private Tab tabAUstd;
    @FXML
    private Tab tabVSstd;


    Connection con = null;
    PreparedStatement preparedStatement = null;
    ResultSet resultSet = null;

    public StudentController(){
        con = DBcon.conDB();
    }

    private void settGen(){
        if(rbStdAsxFemale.isSelected()){
            gender = "Female";
        }else if (rbStdAsxMale.isSelected()){
            gender = "Male";
        }
    }

    private boolean confirmEntry(){
        settGen();

        if(txtStdAnam.getText().isEmpty() || txtStdAsurnam.getText().isEmpty() || txtStdAaddres.getText().isEmpty() || txtStdAnatID.getText().isEmpty() || txtStdAnokNam.getText().isEmpty() || txtStdAnokSurnam.getText().isEmpty() || gender.isEmpty() || txtStdAnokAddres.getText().isEmpty() || txtStdAnokContct.getText().isEmpty() || txtStdAnokDescr.getText().isEmpty() || txtStdAnokNatID.getText().isEmpty() || dtpStdAdob.getEditor().getText().isEmpty()){
            return false;
        }else {
            return true;
        }
    }

    private static ObservableList<DataGsAndSs> getStudObjct(ResultSet rs) throws SQLException, ClassNotFoundException{

        ObservableList<DataGsAndSs> studList = FXCollections.observableArrayList();
        while (rs.next()){
            DataGsAndSs studDet = new DataGsAndSs();
            studDet.setStdIdProp(rs.getInt("idstudents"));
            studDet.setStdNamProp(rs.getString("name"));
            studDet.setStdSurnamProp(rs.getString("surname"));
            studDet.setStdGendProp(rs.getString("gender"));
            studDet.setStdDobProp(rs.getString("dob"));
            studDet.setStdNatidProp(rs.getString("nationID"));
            studDet.setStdAddresProp(rs.getString("address"));
            studDet.setStdNokNamProp(rs.getString("nxtOfKe_name"));
            studDet.setStdNokSurnamProp(rs.getString("nxtOfKe_surname"));
            studDet.setStdNokContctProp(rs.getString("nxtOfKe_contact"));
            studDet.setStdNokNatidProp(rs.getString("nxtOfKe_NID"));
            studDet.setStdNokAddresProp(rs.getString("nxtOfKe_address"));
            studDet.setStdNokDescrProp(rs.getString("nxtOfKe_discr"));
            studDet.setStdEntrydProp(rs.getString("entryDate"));
            studDet.setStdUpdatdProp(rs.getString("updateDat"));
            studList.add(studDet);
        }
        return studList;
    }

    public static ObservableList<DataGsAndSs> getStudRec() throws SQLException, ClassNotFoundException{
        String sql = "Select * from students";
        ResultSet resS = DBcon.dbExc(sql);
        ObservableList<DataGsAndSs> studList = getStudObjct(resS);
        return studList;
    }

    private void saveFunc(){
        if(confirmEntry()){
            try {
                String st = "INSERT INTO students ( name, surname, gender, dob, nationID, address, nxtOfKe_name, nxtOfKe_surname, nxtOfKe_contact, nxtOfKe_NID, nxtOfKe_address, nxtOfKe_discr, entryDate) VALUES (?,?,?,?,?,?,?,?,?,?,?,?,?)";
                preparedStatement = (PreparedStatement) con.prepareStatement(st);
                preparedStatement.setString(1, txtStdAnam.getText());
                preparedStatement.setString(2, txtStdAsurnam.getText());
                preparedStatement.setString(3, gender);
                preparedStatement.setString(4, String.valueOf(dtpStdAdob.getValue()));
                preparedStatement.setString(5, txtStdAnatID.getText());
                preparedStatement.setString(6, txtStdAaddres.getText());
                preparedStatement.setString(7, txtStdAnokNam.getText());
                preparedStatement.setString(8, txtStdAnokSurnam.getText());
                preparedStatement.setString(9, txtStdAnokContct.getText());
                preparedStatement.setString(10, txtStdAnokNatID.getText());
                preparedStatement.setString(11, txtStdAnokAddres.getText());
                preparedStatement.setString(12, txtStdAnokDescr.getText());
                preparedStatement.setString(13, String.valueOf(java.time.LocalDate.now()));

                preparedStatement.executeUpdate();
                //ConfirmBox.display("Confirmation","Successfully added");
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Information Dialog");
                alert.setHeaderText(null);
                alert.setContentText("Successfully added");
                alert.showAndWait();

                clearAll();

                ObservableList<DataGsAndSs> studLst = getStudRec();
                populateTable(studLst);

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

    private void updateFunc(){
        if(confirmEntry()){
            if(!txtStdSeUpsearc.getText().isEmpty()){
                if (rbStdAsxFemale.isSelected()){
                    gender = "Female";
                }if (rbStdAsxMale.isSelected()){
                    gender = "Male";
                }
                try {
                    String st = "update students set name = ?, surname = ?, gender = ?, dob = ?, nationID = ?, address = ?, nxtOfKe_name = ?, nxtOfKe_surname = ?, nxtOfKe_contact = ?, nxtOfKe_NID = ?, nxtOfKe_address = ?, nxtOfKe_discr = ?, updateDat = ? where idstudents = ?";
                    PreparedStatement ps;
                    ps = (PreparedStatement) con.prepareStatement(st);
                    ps.setString(1, txtStdAnam.getText());
                    ps.setString(2, txtStdAsurnam.getText());
                    ps.setString(3, gender);
                    ps.setString(4, dtpStdAdob.getEditor().getText());
                    ps.setString(5, txtStdAnatID.getText());
                    ps.setString(6, txtStdAaddres.getText());
                    ps.setString(7, txtStdAnokNam.getText());
                    ps.setString(8, txtStdAnokSurnam.getText());
                    ps.setString(9, txtStdAnokContct.getText());
                    ps.setString(10, txtStdAnokNatID.getText());
                    ps.setString(11, txtStdAnokAddres.getText());
                    ps.setString(12, txtStdAnokDescr.getText());
                    ps.setString(13, String.valueOf(java.time.LocalDate.now()));
                    ps.setString(14, txtStdSeUpsearc.getText());

                    ps.executeUpdate();
                    //ConfirmBox.display("Confirmation","Successfully added");
                    Alert alert = new Alert(Alert.AlertType.INFORMATION);
                    alert.setTitle("Information Dialog");
                    alert.setHeaderText(null);
                    alert.setContentText("Successfully updated");
                    alert.showAndWait();

                    clearAll();
                    txtStdSeUpsearc.clear();

                } catch (SQLException ex) {
                    System.out.println(ex.getMessage());
                }
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

    private void clearAll(){
        txtStdAnokNatID.clear();
        txtStdAnokDescr.clear();
        txtStdAnokContct.clear();
        txtStdAnokAddres.clear();
        txtStdAnokSurnam.clear();
        //txtStdSeUpsearc.clear();
        txtStdAnokNam.clear();
        txtStdAnatID.clear();
        txtStdAaddres.clear();
        txtStdAsurnam.clear();
        txtStdAnam.clear();
        dtpStdAdob.getEditor().clear();
        rbStdAsxMale.setSelected(false);
        rbStdAsxFemale.setSelected(false);
    }

    public static ObservableList<DataGsAndSs> searchStud(String serV) throws SQLException, ClassNotFoundException{
        String sql = "Select * from psms.students where '"+serV+"' IN(idstudents,name, surname, gender, dob, nationID, address, nxtOfKe_name, nxtOfKe_surname, nxtOfKe_contact, nxtOfKe_NID, nxtOfKe_address, nxtOfKe_discr, entryDate)";
        ResultSet rs = DBcon.dbExc(sql);
        return getStudObjct(rs);
    }

    private void stdSearchToUp() throws SQLException, ClassNotFoundException{
        clearAll();
        String sex;
        String sql = "SELECT * FROM students Where idstudents = ?";
        preparedStatement = con.prepareStatement(sql);
        preparedStatement.setString(1, txtStdSeUpsearc.getText());
        resultSet = preparedStatement.executeQuery();
        if (resultSet.next()) {
            sex = resultSet.getString("gender");
            if (sex.equalsIgnoreCase("male")){
                rbStdAsxMale.setSelected(true);
            }if (sex.equalsIgnoreCase("female")){
                rbStdAsxFemale.setSelected(true);
            }
            txtStdAnam.setText(resultSet.getString("name"));
            txtStdAsurnam.setText(resultSet.getString("surname"));
            dtpStdAdob.getEditor().setText(resultSet.getString("dob"));
            txtStdAnatID.setText(resultSet.getString("nationID"));
            txtStdAaddres.setText(resultSet.getString("address"));
            txtStdAnokNam.setText(resultSet.getString("nxtOfKe_name"));
            txtStdAnokSurnam.setText(resultSet.getString("nxtOfKe_surname"));
            txtStdAnokContct.setText(resultSet.getString("nxtOfKe_contact"));
            txtStdAnokNatID.setText(resultSet.getString("nxtOfKe_NID"));
            txtStdAnokAddres.setText(resultSet.getString("nxtOfKe_address"));
            txtStdAnokDescr.setText(resultSet.getString("nxtOfKe_discr"));
        } else {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Search");
            alert.setHeaderText(null);
            alert.setContentText("No result found on inputted search data.");
            alert.showAndWait();
        }
    }

    public void studBtnAct() throws SQLException, ClassNotFoundException{
        btnStdAclear.setOnAction(event -> clearAll());
        btnStdAsave.setOnAction(event -> saveFunc());
        btnStdSearch.setOnAction(event -> {
            try {
                if(txtStdSearch.getText().isEmpty()){
                    Alert alert = new Alert(Alert.AlertType.WARNING);
                    alert.setTitle("Something is missing");
                    alert.setHeaderText(null);
                    alert.setContentText("Please input search data.");
                    alert.showAndWait();
                }else {
                    ObservableList<DataGsAndSs> list = searchStud(txtStdSearch.getText());
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
        btnStdTrefresh.setOnAction(event -> {
            txtStdSearch.clear();
            try {
                ObservableList<DataGsAndSs> studLst = getStudRec();
                populateTable(studLst);
            } catch (SQLException | ClassNotFoundException e) {
                e.printStackTrace();
            }
        });
        btnStdUpdSearch.setOnAction(event -> {
            try {
                stdSearchToUp();
            } catch (SQLException | ClassNotFoundException e) {
                e.printStackTrace();
            }
        });
        btnStdUpdUpdate.setOnAction(event -> updateFunc());
    }

    public void teachMustNotC(){
        tabAUstd.setDisable(true);
    }

    @FXML
    public void initialize() throws SQLException, ClassNotFoundException {
        colStdTid.setCellValueFactory(cellData -> cellData.getValue().stdIdPropProperty().asObject());
        colStdTnam.setCellValueFactory(cellData -> cellData.getValue().stdNamPropProperty());
        colStdTsurnam.setCellValueFactory(cellData -> cellData.getValue().stdSurnamPropProperty());
        colStdTgender.setCellValueFactory(cellData -> cellData.getValue().stdGendPropProperty());
        colStdTdob.setCellValueFactory(cellData -> cellData.getValue().stdDobPropProperty());
        colStdTnatID.setCellValueFactory(cellData -> cellData.getValue().stdNatidPropProperty());
        colStdTaddres.setCellValueFactory(cellData -> cellData.getValue().stdAddresPropProperty());
        colStdTnokNam.setCellValueFactory(cellData -> cellData.getValue().stdNokNamPropProperty());
        colStdTnokSurnam.setCellValueFactory(cellData -> cellData.getValue().stdNokSurnamPropProperty());
        colStdTnokContct.setCellValueFactory(cellData -> cellData.getValue().stdNokContctPropProperty());
        colStdTnokNatID.setCellValueFactory(cellData -> cellData.getValue().stdNokNatidPropProperty());
        colStdTnokAddres.setCellValueFactory(cellData -> cellData.getValue().stdNokAddresPropProperty());
        colStdTnokDescr.setCellValueFactory(cellData -> cellData.getValue().stdNokDescrPropProperty());
        colStdTentryD.setCellValueFactory(cellData -> cellData.getValue().stdEntrydPropProperty());
        colStdTupdateD.setCellValueFactory(cellData -> cellData.getValue().stdUpdatdPropProperty());

        ObservableList<DataGsAndSs> studLst = getStudRec();
        populateTable(studLst);

        studBtnAct();
    }

    private void populateTable(ObservableList<DataGsAndSs> studLst) {
        stdTable.setItems(studLst);
    }
}

