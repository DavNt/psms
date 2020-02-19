import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class ReportCardController {

    @FXML
    private TableColumn<DataGsAndSs, String> colVRclass;

    @FXML
    private TextField txtVRtblSrch;

    @FXML
    private TextField txtASRmark;

    @FXML
    private TableColumn<DataGsAndSs, String> colVRyear;

    @FXML
    private ComboBox<String> cmbASRstudID;

    @FXML
    private TextField txtASRremark;

    @FXML
    private Button btnASReditSrch;

    @FXML
    private TableColumn<DataGsAndSs, Integer> colVRid;

    @FXML
    private ComboBox<String> cmbASRterm;

    @FXML
    private TextField txtASRyear;

    @FXML
    private TableColumn<DataGsAndSs, String> colVRterm;

    @FXML
    private Button btnASRupdate;

    @FXML
    private Button btnVRtblRefrsh;

    @FXML
    private TableColumn<DataGsAndSs, String> colVRentryD;

    @FXML
    private TextField txtASRtchID;

    @FXML
    private TableColumn<DataGsAndSs, String> colVRstudID;

    @FXML
    private ComboBox<String> cmbASRgrade;

    @FXML
    private TableColumn<DataGsAndSs, String> colVRremark;

    @FXML
    private TableView<DataGsAndSs> tbleViewRes;

    @FXML
    private TextField txtASReditSrch;

    @FXML
    private Button btnASRclr;

    @FXML
    private Button btnASRsave;

    @FXML
    private TableColumn<DataGsAndSs, String> colVRteachID;

    @FXML
    private TableColumn<DataGsAndSs, String> colVRmark;

    @FXML
    private ComboBox<String> cmbASRsubjCod;

    @FXML
    private Button btnVRsrch;

    @FXML
    private TableColumn<DataGsAndSs, String> colVRsubjct;

    Connection con = null;
    PreparedStatement preparedStatement = null;
    ResultSet resultSet = null;

    public ReportCardController(){
        con = DBcon.conDB();
    }

    private static ObservableList<DataGsAndSs> getSrcObjct(ResultSet rs) throws SQLException, ClassNotFoundException{
        ObservableList<DataGsAndSs> srcList = FXCollections.observableArrayList();
        while (rs.next()){
            DataGsAndSs srcDet = new DataGsAndSs();
            srcDet.setSrcIdProp(rs.getInt("idreportCard"));
            srcDet.setSrcSubjCProp(rs.getString("subjectName"));
            srcDet.setSrcStudIdProp(rs.getString("studentID"));
            srcDet.setSrcMarkProp(rs.getString("gradeMark"));
            srcDet.setSrcRemarkProp(rs.getString("remark"));
            srcDet.setSrcGradeProp(rs.getString("className"));
            srcDet.setSrcTchIdProp(rs.getString("teacherID"));
            srcDet.setSrcTermProp(rs.getString("term"));
            srcDet.setSrcYearProp(rs.getString("year"));
            srcDet.setSrcEntrydProp(rs.getString("entryDate"));
            srcList.add(srcDet);
        }
        return srcList;
    }

    private static ObservableList<DataGsAndSs> getSrcRec() throws SQLException, ClassNotFoundException{
        String sql = "Select * from reportcard";
        ResultSet resS = DBcon.dbExc(sql);
        ObservableList<DataGsAndSs> srcList = getSrcObjct(resS);
        return srcList;
    }

    private final ObservableList<String> srcGrade = FXCollections.observableArrayList();
    public void fillSRCgrade() throws SQLException, ClassNotFoundException {
        String sql = "SELECT grade FROM classes";
        preparedStatement = con.prepareStatement(sql);
        resultSet = preparedStatement.executeQuery();
        while (resultSet.next()) {
            srcGrade.add(resultSet.getString("grade"));
        }
    }

    private final ObservableList<String> srcSubjC = FXCollections.observableArrayList();
    public void fillSRCsubjC() throws SQLException, ClassNotFoundException {
        String sql = "SELECT idsubjucts FROM subjects";
        preparedStatement = con.prepareStatement(sql);
        resultSet = preparedStatement.executeQuery();
        while (resultSet.next()) {
            srcSubjC.add(resultSet.getString("idsubjucts"));
        }
    }

    private final ObservableList<String> srcStdid = FXCollections.observableArrayList();
    public void fillSRCstdid() throws SQLException, ClassNotFoundException {
        String sql = "SELECT idstudents FROM students";
        preparedStatement = con.prepareStatement(sql);
        resultSet = preparedStatement.executeQuery();
        while (resultSet.next()) {
            srcStdid.add(resultSet.getString("idstudents"));
        }
    }
    ObservableList<String> srcTerm = FXCollections.observableArrayList("2","3");

    @FXML
    public void initialize() throws SQLException, ClassNotFoundException {
        //src record
        colVRid.setCellValueFactory(cellData -> cellData.getValue().srcIdPropProperty().asObject());
        colVRsubjct.setCellValueFactory(cellData -> cellData.getValue().srcSubjCPropProperty());
        colVRstudID.setCellValueFactory(cellData -> cellData.getValue().srcStudIdPropProperty());
        colVRmark.setCellValueFactory(cellData -> cellData.getValue().srcMarkPropProperty());
        colVRremark.setCellValueFactory(cellData -> cellData.getValue().srcRemarkPropProperty());
        colVRclass.setCellValueFactory(cellData -> cellData.getValue().srcGradePropProperty());
        colVRteachID.setCellValueFactory(cellData -> cellData.getValue().srcTchIdPropProperty());
        colVRterm.setCellValueFactory(cellData -> cellData.getValue().srcTermPropProperty());
        colVRyear.setCellValueFactory(cellData -> cellData.getValue().srcYearPropProperty());
        colVRentryD.setCellValueFactory(cellData -> cellData.getValue().srcEntrydPropProperty());

        refreshTable();
        cmbASRsubjCod.setItems(srcSubjC);
        cmbASRstudID.setItems(srcStdid);
        cmbASRgrade.setItems(srcGrade);
        cmbASRterm.setItems(srcTerm);
    }

    private void refreshTable() throws SQLException, ClassNotFoundException {
        ObservableList<DataGsAndSs> srcLst = getSrcRec();
        populateSrcTable(srcLst);
    }

    private void populateSrcTable(ObservableList<DataGsAndSs> srcLst) {
        tbleViewRes.setItems(srcLst);
    }

}

