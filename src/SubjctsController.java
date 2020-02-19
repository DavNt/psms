import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class SubjctsController {

    @FXML
    private TextField txtASubjNam;

    @FXML
    private TextField txtASubjCod;

    @FXML
    private TableColumn<DataGsAndSs, String> colSubjNam;

    @FXML
    private Button btnSubjSearch;

    @FXML
    private Button btnSubjRefr;

    @FXML
    private TextField txtASubjDescr;

    @FXML
    private Button btnASubjClr;

    @FXML
    private Button btnASubjSrch;

    @FXML
    private Button btnASubjSav;

    @FXML
    private TableColumn<DataGsAndSs, String> colSubjDescr;

    @FXML
    private TextField txtASubjCodSrch;

    @FXML
    private TableColumn<DataGsAndSs, String> colSubjCod;

    @FXML
    private TextField txtSubjSrch;

    @FXML
    private TableColumn<DataGsAndSs, String> colSubjEntry;

    @FXML
    private Button btnASubjUpdat;

    @FXML
    private TableView<DataGsAndSs> tableSubj;

    Connection con = null;
    PreparedStatement preparedStatement = null;
    ResultSet resultSet = null;

    public SubjctsController(){
        con = DBcon.conDB();
    }

    private static ObservableList<DataGsAndSs> getSubjObjct(ResultSet rs) throws SQLException, ClassNotFoundException{
        ObservableList<DataGsAndSs> subjList = FXCollections.observableArrayList();
        while (rs.next()){
            DataGsAndSs subjDet = new DataGsAndSs();
            subjDet.setSubjCodProp(rs.getString("idsubjucts"));
            subjDet.setSubjNamProp(rs.getString("subname"));
            subjDet.setSubjDescrProp(rs.getString("subdescr"));
            subjDet.setSubjEntryDProp(rs.getString("entrydate"));
            subjList.add(subjDet);
        }
        return subjList;
    }

    private static ObservableList<DataGsAndSs> getSubjRec() throws SQLException, ClassNotFoundException{
        String sql = "Select * from subjects";
        ResultSet resS = DBcon.dbExc(sql);
        ObservableList<DataGsAndSs> subjList = getSubjObjct(resS);
        return subjList;
    }

    private void clearAll(){
        txtASubjCod.setEditable(true);
        txtASubjCod.clear();
        txtASubjNam.clear();
        txtASubjDescr.clear();
    }

    private boolean confirmEntry(){
        if(txtASubjDescr.getText().isEmpty() || txtASubjNam.getText().isEmpty() || txtASubjCod.getText().isEmpty()){
            return false;
        }else {
            return true;
        }
    }

    private void saveFunc(){
        if(confirmEntry()) {
            try {
                String sql = "SELECT * FROM subjects Where idsubjucts = ?";
                preparedStatement = con.prepareStatement(sql);
                preparedStatement.setString(1, txtASubjCod.getText());
                resultSet = preparedStatement.executeQuery();
                if (resultSet.next()) {
                    Alert alert = new Alert(Alert.AlertType.WARNING);
                    alert.setTitle("Information Dialog");
                    alert.setHeaderText(null);
                    alert.setContentText("Duplicate subject code");
                    alert.showAndWait();
                } else {
                    String st = "INSERT INTO subjects ( idsubjucts, subname, subdescr, entrydate) VALUES (?,?,?,?)";
                    preparedStatement = (PreparedStatement) con.prepareStatement(st);
                    preparedStatement.setString(1, txtASubjCod.getText());
                    preparedStatement.setString(2, txtASubjNam.getText());
                    preparedStatement.setString(3, txtASubjDescr.getText());
                    preparedStatement.setString(4, String.valueOf(java.time.LocalDate.now()));
                    preparedStatement.executeUpdate();

                    Alert alert = new Alert(Alert.AlertType.INFORMATION);
                    alert.setTitle("Information Dialog");
                    alert.setHeaderText(null);
                    alert.setContentText("Successfully added");
                    alert.showAndWait();

                    clearAll();

                    ObservableList<DataGsAndSs> subjLst = getSubjRec();
                    populateTable(subjLst);
                }
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
    }

    private void updateFunc(){
        if(confirmEntry()){
            if(!txtASubjCodSrch.getText().isEmpty()){
                try {
                    String st = "update students set subname = ?, subdescr = ?, updateDat = ? where idsubjucts = ?";
                    PreparedStatement ps;
                    ps = (PreparedStatement) con.prepareStatement(st);
                    ps.setString(1, txtASubjNam.getText());
                    ps.setString(2, txtASubjDescr.getText());
                    ps.setString(3, String.valueOf(java.time.LocalDate.now()));
                    ps.setString(4, txtASubjCodSrch.getText());
                    ps.executeUpdate();

                    Alert alert = new Alert(Alert.AlertType.INFORMATION);
                    alert.setTitle("Information Dialog");
                    alert.setHeaderText(null);
                    alert.setContentText("Successfully updated");
                    alert.showAndWait();

                    clearAll();
                    txtASubjCodSrch.clear();

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

    private void subjSearchToUp() throws SQLException, ClassNotFoundException{
        //clearAll();
        String sql = "SELECT * FROM subjects Where idsubjucts = ?";
        preparedStatement = con.prepareStatement(sql);
        preparedStatement.setString(1, txtASubjCodSrch.getText());
        resultSet = preparedStatement.executeQuery();
        if (resultSet.next()) {
            txtASubjCod.setEditable(false);
            txtASubjCod.setText(resultSet.getString("idsubjucts"));
            txtASubjNam.setText(resultSet.getString("subname"));
            txtASubjDescr.setText(resultSet.getString("subdescr"));
        } else {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Search");
            alert.setHeaderText(null);
            alert.setContentText("No result found on inputted search data.");
            alert.showAndWait();
        }
    }

    private static ObservableList<DataGsAndSs> searchSubj(String serV) throws SQLException, ClassNotFoundException{
        String sql = "Select * from psms.subjects where '"+serV+"' IN(idsubjucts, subname, subdescr, entrydate)";
        ResultSet rs = DBcon.dbExc(sql);
        return getSubjObjct(rs);
    }

    public void subjBtnAct(){
        btnASubjClr.setOnAction(event -> clearAll());
        btnASubjSav.setOnAction(event -> saveFunc());
        btnASubjSrch.setOnAction(event -> {
            try {
                subjSearchToUp();
            } catch (SQLException | ClassNotFoundException e) {
                e.printStackTrace();
            }
        });
        btnASubjUpdat.setOnAction(event -> updateFunc());
        btnSubjSearch.setOnAction(event -> {
            try {
                if(txtSubjSrch.getText().isEmpty()){
                    Alert alert = new Alert(Alert.AlertType.WARNING);
                    alert.setTitle("Something is missing");
                    alert.setHeaderText(null);
                    alert.setContentText("Please input search data.");
                    alert.showAndWait();
                }else {
                    ObservableList<DataGsAndSs> list = searchSubj(txtSubjSrch.getText());
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
        btnSubjRefr.setOnAction(event -> {
            try {
                ObservableList<DataGsAndSs> subjLst = getSubjRec();
                populateTable(subjLst);
                txtSubjSrch.clear();
            } catch (SQLException | ClassNotFoundException e) {
                e.printStackTrace();
            }
        });
    }
    @FXML
    public void initialize() throws SQLException, ClassNotFoundException {
        colSubjCod.setCellValueFactory(cellData -> cellData.getValue().subjCodPropProperty());
        colSubjNam.setCellValueFactory(cellData -> cellData.getValue().subjNamPropProperty());
        colSubjDescr.setCellValueFactory(cellData -> cellData.getValue().subjDescrPropProperty());
        colSubjEntry.setCellValueFactory(cellData -> cellData.getValue().subjEntryDPropProperty());

        ObservableList<DataGsAndSs> subjLst = getSubjRec();
        populateTable(subjLst);

        subjBtnAct();
    }

    private void populateTable(ObservableList<DataGsAndSs> subjLst) {
        tableSubj.setItems(subjLst);
    }

}

