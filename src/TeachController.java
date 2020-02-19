import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.paint.Color;

import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;

public class TeachController {

    @FXML
    private RadioButton rbATfemale;

    @FXML
    private TableColumn<DataGsAndSs, String> colTstafDescr;

    @FXML
    private Button btnATclear;

    @FXML
    private TableColumn<DataGsAndSs, String> colTupdatDat;

    @FXML
    private TextField txtATnationID;

    @FXML
    private Button btnATsav;

    @FXML
    private TextField txtSearchTeach;

    @FXML
    private TableColumn<DataGsAndSs, Integer> colTID;

    @FXML
    private TextField txtATsurname;

    @FXML
    private RadioButton rbATmale;

    @FXML
    private TableColumn<DataGsAndSs, String> colTentryDat;

    @FXML
    private TableColumn<DataGsAndSs, String> colTname;

    @FXML
    private Button btnATsearch;

    @FXML
    private TextField txtATcontactN;

    @FXML
    private TableColumn<DataGsAndSs, String> colTsurnam;

    @FXML
    private TableColumn<DataGsAndSs, String> colTaddres;

    @FXML
    private TableView<DataGsAndSs> tbleTeacher;

    @FXML
    private DatePicker dtpATdob;

    @FXML
    private TextField txtATsidSearch;

    @FXML
    private Button btnATupdate;

    @FXML
    private TextField txtATfname;

    @FXML
    private TableColumn<DataGsAndSs, String> colTdob;

    @FXML
    private TableColumn<DataGsAndSs, String> colTgender;

    @FXML
    private TableColumn<DataGsAndSs, String> colTcontctN;

    @FXML
    private Button btnVSTsearch;

    @FXML
    private TableColumn<DataGsAndSs, String> colTnatioId;

    @FXML
    private TextArea txtATaddres;

    @FXML
    private TextField txtATjobDescr;

    private String fulName;
    private String surname;
    private String addres;
    private String jbDescr;
    private String natioID;
    private String contctNo;
    private String sidID;
    private String gender = "";


    Connection con = null;
    PreparedStatement preparedStatement = null;
    ResultSet resultSet = null;

    public TeachController(){
        con = DBcon.conDB();
    }

    public void settGen(){
        if(rbATfemale.isSelected()){
            gender = "Female";
        }else if (rbATmale.isSelected()){
            gender = "Male";
        }
    }

    public boolean confirmEntry(){
        settGen();
        fulName = txtATfname.getText();
        surname = txtATsurname.getText();
        addres = txtATaddres.getText();
        jbDescr = txtATjobDescr.getText();
        natioID = txtATnationID.getText();
        contctNo = txtATcontactN.getText();
        sidID = txtATsidSearch.getText();
        if(fulName.isEmpty() || surname.isEmpty() || addres.isEmpty() || jbDescr.isEmpty() || natioID.isEmpty() || contctNo.isEmpty() || gender.isEmpty() || dtpATdob.getEditor().getText().isEmpty()){
            return false;
        }else {
            return true;
        }
    }

    private static ObservableList<DataGsAndSs> getStaffObjct(ResultSet rs) throws SQLException, ClassNotFoundException{

            ObservableList<DataGsAndSs> stafList = FXCollections.observableArrayList();
            while (rs.next()){
                DataGsAndSs stafDet = new DataGsAndSs();
                stafDet.setStafID(rs.getInt("idstuff"));
                stafDet.setStfName(rs.getString("name"));
                stafDet.setStfSurname(rs.getString("surname"));
                stafDet.setStfGender(rs.getString("gender"));
                stafDet.setStfDob(rs.getString("dob"));
                stafDet.setStfNationID(rs.getString("nationID"));
                stafDet.setStfAddress(rs.getString("address"));
                stafDet.setStfContactNo(rs.getString("contactNo"));
                stafDet.setStuffDiscr(rs.getString("stuffDiscr"));
                stafDet.setStfEntryDate(rs.getString("entryDate"));
                stafDet.setStfUpdateD(rs.getString("updateDate"));
                stafList.add(stafDet);
            }
            return stafList;

    }

    public static ObservableList<DataGsAndSs> getStaffRec() throws SQLException, ClassNotFoundException{
        String sql = "Select * from stuff";
        try {
            ResultSet resS = DBcon.dbExc(sql);
            ObservableList<DataGsAndSs> stafList = getStaffObjct(resS);
            return stafList;
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        }
        return null;
    }

    private void saveFunc(){
        if(confirmEntry()){
            try {
                String st = "INSERT INTO stuff ( name, surname, gender, dob, nationID, address, contactNo, stuffDiscr, entryDate) VALUES (?,?,?,?,?,?,?,?,?)";
                preparedStatement = (PreparedStatement) con.prepareStatement(st);
                preparedStatement.setString(1, fulName);
                preparedStatement.setString(2, surname);
                preparedStatement.setString(3, gender);
                preparedStatement.setString(4, String.valueOf(dtpATdob.getValue()));
                preparedStatement.setString(5, natioID);
                preparedStatement.setString(6, addres);
                preparedStatement.setString(7, contctNo);
                preparedStatement.setString(8, jbDescr);
                preparedStatement.setString(9, String.valueOf(java.time.LocalDate.now()));

                preparedStatement.executeUpdate();
                //ConfirmBox.display("Confirmation","Successfully added");
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Information Dialog");
                alert.setHeaderText(null);
                alert.setContentText("Successfully added");
                alert.showAndWait();

                clearAll();

                ObservableList<DataGsAndSs> stafLst = getStaffRec();
                populateTable(stafLst);

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
           if(!txtATsidSearch.getText().isEmpty()){
               if (rbATfemale.isSelected()){
                   gender = "Female";
               }if (rbATmale.isSelected()){
                   gender = "Male";
               }
               try {
                    String st = "update stuff set name = ?, surname = ?, gender = ?, dob = ?, nationID = ?, address = ?, contactNo = ?, stuffDiscr = ?, updateDate = ? where idstuff = ?";
                    PreparedStatement ps;
                    ps = (PreparedStatement) con.prepareStatement(st);
                    ps.setString(1, txtATfname.getText());
                    ps.setString(2, txtATsurname.getText());
                    ps.setString(3, gender);
                    ps.setString(4, dtpATdob.getEditor().getText());
                    ps.setString(5, txtATnationID.getText());
                    ps.setString(6, txtATaddres.getText());
                    ps.setString(7, txtATcontactN.getText());
                    ps.setString(8, txtATjobDescr.getText());
                    ps.setString(9, String.valueOf(java.time.LocalDate.now()));
                    ps.setString(10, txtATsidSearch.getText());

                    ps.executeUpdate();
                    //ConfirmBox.display("Confirmation","Successfully added");
                    Alert alert = new Alert(Alert.AlertType.INFORMATION);
                    alert.setTitle("Information Dialog");
                    alert.setHeaderText(null);
                    alert.setContentText("Successfully updated");
                    alert.showAndWait();

                    clearAll();
                    txtATsidSearch.clear();

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

    public void clearAll(){
        txtATaddres.clear();
        txtATcontactN.clear();
        txtATfname.clear();
        txtATjobDescr.clear();
        txtATnationID.clear();
        //txtATsidSearch.clear();
        txtATsurname.clear();
        dtpATdob.getEditor().clear();
        rbATmale.setSelected(false);
        rbATfemale.setSelected(false);
    }

    /*@FXML public void teachBtnAct(){
        btnATclear.setOnAction(event -> clearAll());
        btnATsav.setOnAction(event -> saveFunc());
        btnATupdate.setOnAction(event -> updateFunc());
    }*/
    @FXML public void staffBtnClear(ActionEvent event){
        clearAll();
    }
    @FXML public void staffBtnSav(ActionEvent event){
        saveFunc();
    }
    @FXML public void staffBtnUpdate(ActionEvent event){
        updateFunc();
    }

    @FXML
    public void searchTablBtn(ActionEvent event) throws SQLException, ClassNotFoundException{
        //String searcTxt = txtSearchTeach.getText();
        if(txtSearchTeach.getText().isEmpty()){
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Something is missing");
            alert.setHeaderText(null);
            alert.setContentText("Please input search data.");
            alert.showAndWait();
        }else {
            ObservableList<DataGsAndSs> list = searchStaf(txtSearchTeach.getText());
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
    }

    public static ObservableList<DataGsAndSs> searchStaf(String serV) throws SQLException, ClassNotFoundException{
        String sql = "Select * from psms.stuff where '"+serV+"' IN(idstuff,name,surname,gender,dob,nationID,address,contactNo,stuffDiscr,entryDate)";
        ResultSet rs = DBcon.dbExc(sql);
        return getStaffObjct(rs);
    }

    @FXML private Button btnVSTrefresh;

    @FXML public void refreshTabl(ActionEvent event) throws SQLException, ClassNotFoundException{
        txtSearchTeach.clear();
        ObservableList<DataGsAndSs> stafLst = getStaffRec();
        populateTable(stafLst);

    }

    @FXML public void searchToUpd(ActionEvent event) throws SQLException, ClassNotFoundException{
        clearAll();
        String sex;
        String sql = "SELECT * FROM stuff Where idstuff = ?";
        preparedStatement = con.prepareStatement(sql);
        preparedStatement.setString(1, txtATsidSearch.getText());
        resultSet = preparedStatement.executeQuery();
        if (resultSet.next()) {
            sex = resultSet.getString("gender");
            if (sex.equalsIgnoreCase("male")){
                rbATmale.setSelected(true);
            }if (sex.equalsIgnoreCase("female")){
                rbATfemale.setSelected(true);
            }
            txtATfname.setText(resultSet.getString("name"));
            txtATsurname.setText(resultSet.getString("surname"));
            dtpATdob.getEditor().setText(resultSet.getString("dob"));
            txtATnationID.setText(resultSet.getString("nationID"));
            txtATaddres.setText(resultSet.getString("address"));
            txtATcontactN.setText(resultSet.getString("contactNo"));
            txtATjobDescr.setText(resultSet.getString("stuffDiscr"));
        } else {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Search");
            alert.setHeaderText(null);
            alert.setContentText("No result found on inputted search data.");
            alert.showAndWait();
        }
    }

    @FXML
    public void initialize() throws SQLException, ClassNotFoundException {
        colTID.setCellValueFactory(cellData -> cellData.getValue().getStaffID().asObject());
        colTname.setCellValueFactory(cellData -> cellData.getValue().getStaffNam());
        colTsurnam.setCellValueFactory(cellData -> cellData.getValue().getStaffSurnam());
        colTgender.setCellValueFactory(cellData -> cellData.getValue().getStaffGender());
        colTdob.setCellValueFactory(cellData -> cellData.getValue().getStaffDob());
        colTnatioId.setCellValueFactory(cellData -> cellData.getValue().getStaffNationId());
        colTaddres.setCellValueFactory(cellData -> cellData.getValue().getStaffAddres());
        colTcontctN.setCellValueFactory(cellData -> cellData.getValue().getStaffContctN());
        colTstafDescr.setCellValueFactory(cellData -> cellData.getValue().getStaffDescri());
        colTentryDat.setCellValueFactory(cellData -> cellData.getValue().getStaffEntryD());
        colTupdatDat.setCellValueFactory(cellData -> cellData.getValue().getStaffUpdateD());

        ObservableList<DataGsAndSs> stafLst = getStaffRec();
        populateTable(stafLst);

    }

    private void populateTable(ObservableList<DataGsAndSs> stafLst) {
        tbleTeacher.setItems(stafLst);
    }
}

