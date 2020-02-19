import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class DataGsAndSs {
    //staff table details
    private IntegerProperty idProprty;
    private StringProperty nameProp, surnamProp, gendrProp, dobProp, natidProp, addresProp, contctProp, descriProp, entrydtProp, updatedtProp;

    //student table details
    private IntegerProperty stdIdProp;
    private StringProperty stdNamProp, stdSurnamProp, stdGendProp, stdDobProp, stdNatidProp, stdAddresProp, stdNokNamProp, stdNokSurnamProp, stdNokContctProp, stdNokNatidProp, stdNokDescrProp, stdNokAddresProp, stdEntrydProp, stdUpdatdProp;

    //users table details
    private IntegerProperty usrIdProp;
    private StringProperty usrUsrnamProp, usrUtypeProp, usrActivtyProp, usrEntryDatProp;

    //subjcts table
    private  StringProperty subjCodProp, subjNamProp, subjDescrProp, subjEntryDProp;

    //classes table
    private IntegerProperty clsIDProp, clsStdNumProp;
    private StringProperty clsGradProp, clsTchIDProp, clsTchSurnProp, clsEntrydProp, clsUpdatedProp;

    //class allocation table
    private IntegerProperty calloStdidProp;
    private StringProperty calloStdNamProp, calloGradProp, calloUpdatedProp;

    //student report card table
    private IntegerProperty srcIdProp;
    private StringProperty srcSubjCProp, srcStudIdProp, srcMarkProp, srcRemarkProp, srcGradeProp, srcTchIdProp, srcTermProp, srcYearProp, srcEntrydProp;

    public DataGsAndSs(){
        //staff table details
        this.idProprty = new SimpleIntegerProperty();
        this.nameProp = new SimpleStringProperty();
        this.surnamProp = new SimpleStringProperty();
        this.gendrProp = new SimpleStringProperty();
        this.dobProp = new SimpleStringProperty();
        this.natidProp = new SimpleStringProperty();
        this.addresProp = new SimpleStringProperty();
        this.contctProp = new SimpleStringProperty();
        this.descriProp = new SimpleStringProperty();
        this.entrydtProp = new SimpleStringProperty();
        this.updatedtProp = new SimpleStringProperty();

        //student table details
        this.stdIdProp = new SimpleIntegerProperty();
        this.stdNamProp = new SimpleStringProperty();
        this.stdSurnamProp = new SimpleStringProperty();
        this.stdGendProp = new SimpleStringProperty();
        this.stdDobProp = new SimpleStringProperty();
        this.stdNatidProp = new SimpleStringProperty();
        this.stdAddresProp = new SimpleStringProperty();
        this.stdNokNamProp = new SimpleStringProperty();
        this.stdNokSurnamProp = new SimpleStringProperty();
        this.stdNokContctProp = new SimpleStringProperty();
        this.stdNokAddresProp = new SimpleStringProperty();
        this.stdNokNatidProp = new SimpleStringProperty();
        this.stdNokDescrProp = new SimpleStringProperty();
        this.stdEntrydProp = new SimpleStringProperty();
        this.stdUpdatdProp = new SimpleStringProperty();

        //users table
        this.usrIdProp = new SimpleIntegerProperty();
        this.usrUsrnamProp = new SimpleStringProperty();
        this.usrUtypeProp = new SimpleStringProperty();
        this.usrActivtyProp = new SimpleStringProperty();
        this.usrEntryDatProp = new SimpleStringProperty();

        //subjct table
        this.subjCodProp = new SimpleStringProperty();
        this.subjNamProp = new SimpleStringProperty();
        this.subjDescrProp = new SimpleStringProperty();
        this.subjEntryDProp = new SimpleStringProperty();

        //classes table
        this.clsIDProp = new SimpleIntegerProperty();
        this.clsGradProp = new SimpleStringProperty();
        this.clsTchIDProp = new SimpleStringProperty();
        this.clsTchSurnProp = new SimpleStringProperty();
        this.clsStdNumProp = new SimpleIntegerProperty();
        this.clsEntrydProp = new SimpleStringProperty();
        this.clsUpdatedProp = new SimpleStringProperty();

        //class alloc table
        this.calloStdidProp = new SimpleIntegerProperty();
        this.calloStdNamProp = new SimpleStringProperty();
        this.calloGradProp = new SimpleStringProperty();
        this.calloUpdatedProp = new SimpleStringProperty();

        //student report card
        this.srcIdProp = new SimpleIntegerProperty();
        this.srcSubjCProp = new SimpleStringProperty();
        this.srcStudIdProp = new SimpleStringProperty();
        this.srcMarkProp = new SimpleStringProperty();
        this.srcRemarkProp = new SimpleStringProperty();
        this.srcGradeProp = new SimpleStringProperty();
        this.srcTchIdProp = new SimpleStringProperty();
        this.srcTermProp = new SimpleStringProperty();
        this.srcYearProp = new SimpleStringProperty();
        this.srcEntrydProp = new SimpleStringProperty();
    }

    public int getStafID() {
        return idProprty.get();
    }

    public void setStafID(Integer stafID) {
        this.idProprty.set(stafID);
    }

    public IntegerProperty getStaffID(){
        return idProprty;
    }

    public String getStfName() {
        return nameProp.get();
    }

    public void setStfName(String stfName) {
        this.nameProp.set(stfName);
    }

    public StringProperty getStaffNam(){
        return nameProp;
    }

    public String getStfSurname() {
        return surnamProp.get();
    }

    public void setStfSurname(String stfSurname) {
        this.surnamProp.set(stfSurname);
    }

    public StringProperty getStaffSurnam(){
        return surnamProp;
    }

    public String getStfGender() {
        return gendrProp.get();
    }

    public void setStfGender(String stfGender) {
        this.gendrProp.set(stfGender);
    }

    public StringProperty getStaffGender(){
        return gendrProp;
    }

    public String getStfDob() {
        return dobProp.get();
    }

    public void setStfDob(String stfDob) {
        this.dobProp.set(stfDob);
    }

    public StringProperty getStaffDob(){
        return dobProp;
    }

    public String getStfNationID() {
        return natidProp.get();
    }

    public void setStfNationID(String stfNationID) {
        this.natidProp.set(stfNationID);
    }

    public StringProperty getStaffNationId(){
        return natidProp;
    }

    public String getStfAddress() {
        return addresProp.get();
    }

    public void setStfAddress(String stfAddress) {
        this.addresProp.set(stfAddress);
    }

    public StringProperty getStaffAddres(){
        return addresProp;
    }

    public String getStfContactNo() {
        return contctProp.get();
    }

    public void setStfContactNo(String stfContactNo) {
        this.contctProp.set(stfContactNo);
    }

    public StringProperty getStaffContctN(){
        return contctProp;
    }

    public String getStuffDiscr() {
        return descriProp.get();
    }

    public void setStuffDiscr(String stuffDiscr) {
        this.descriProp.set(stuffDiscr);
    }

    public StringProperty getStaffDescri(){
        return descriProp;
    }

    public String getStfEntryDate() {
        return entrydtProp.get();
    }

    public void setStfEntryDate(String stfEntryDate) {
        this.entrydtProp.set(stfEntryDate);
    }

    public StringProperty getStaffEntryD(){
        return entrydtProp;
    }

    public String getStfUpdateD() {
        return updatedtProp.get();
    }

    public void setStfUpdateD(String stfUpdateD) {
        this.updatedtProp.set(stfUpdateD);
    }

    public StringProperty getStaffUpdateD(){
        return updatedtProp;
    }

    //for students table view
    public int getStdIdProp() {
        return stdIdProp.get();
    }

    public IntegerProperty stdIdPropProperty() {
        return stdIdProp;
    }

    public void setStdIdProp(int stdIdProp) {
        this.stdIdProp.set(stdIdProp);
    }

    public String getStdNamProp() {
        return stdNamProp.get();
    }

    public StringProperty stdNamPropProperty() {
        return stdNamProp;
    }

    public void setStdNamProp(String stdNamProp) {
        this.stdNamProp.set(stdNamProp);
    }

    public String getStdSurnamProp() {
        return stdSurnamProp.get();
    }

    public StringProperty stdSurnamPropProperty() {
        return stdSurnamProp;
    }

    public void setStdSurnamProp(String stdSurnamProp) {
        this.stdSurnamProp.set(stdSurnamProp);
    }

    public String getStdGendProp() {
        return stdGendProp.get();
    }

    public StringProperty stdGendPropProperty() {
        return stdGendProp;
    }

    public void setStdGendProp(String stdGendProp) {
        this.stdGendProp.set(stdGendProp);
    }

    public String getStdDobProp() {
        return stdDobProp.get();
    }

    public StringProperty stdDobPropProperty() {
        return stdDobProp;
    }

    public void setStdDobProp(String stdDobProp) {
        this.stdDobProp.set(stdDobProp);
    }

    public String getStdNatidProp() {
        return stdNatidProp.get();
    }

    public StringProperty stdNatidPropProperty() {
        return stdNatidProp;
    }

    public void setStdNatidProp(String stdNatidProp) {
        this.stdNatidProp.set(stdNatidProp);
    }

    public String getStdAddresProp() {
        return stdAddresProp.get();
    }

    public StringProperty stdAddresPropProperty() {
        return stdAddresProp;
    }

    public void setStdAddresProp(String stdAddresProp) {
        this.stdAddresProp.set(stdAddresProp);
    }

    public String getStdNokNamProp() {
        return stdNokNamProp.get();
    }

    public StringProperty stdNokNamPropProperty() {
        return stdNokNamProp;
    }

    public void setStdNokNamProp(String stdNokNamProp) {
        this.stdNokNamProp.set(stdNokNamProp);
    }

    public String getStdNokSurnamProp() {
        return stdNokSurnamProp.get();
    }

    public StringProperty stdNokSurnamPropProperty() {
        return stdNokSurnamProp;
    }

    public void setStdNokSurnamProp(String stdNokSurnamProp) {
        this.stdNokSurnamProp.set(stdNokSurnamProp);
    }

    public String getStdNokContctProp() {
        return stdNokContctProp.get();
    }

    public StringProperty stdNokContctPropProperty() {
        return stdNokContctProp;
    }

    public void setStdNokContctProp(String stdNokContctProp) {
        this.stdNokContctProp.set(stdNokContctProp);
    }

    public String getStdNokNatidProp() {
        return stdNokNatidProp.get();
    }

    public StringProperty stdNokNatidPropProperty() {
        return stdNokNatidProp;
    }

    public void setStdNokNatidProp(String stdNokNatidProp) {
        this.stdNokNatidProp.set(stdNokNatidProp);
    }

    public String getStdNokDescrProp() {
        return stdNokDescrProp.get();
    }

    public StringProperty stdNokDescrPropProperty() {
        return stdNokDescrProp;
    }

    public void setStdNokDescrProp(String stdNokDescrProp) {
        this.stdNokDescrProp.set(stdNokDescrProp);
    }

    public String getStdNokAddresProp() {
        return stdNokAddresProp.get();
    }

    public StringProperty stdNokAddresPropProperty() {
        return stdNokAddresProp;
    }

    public void setStdNokAddresProp(String stdNokAddresProp) {
        this.stdNokAddresProp.set(stdNokAddresProp);
    }

    public String getStdEntrydProp() {
        return stdEntrydProp.get();
    }

    public StringProperty stdEntrydPropProperty() {
        return stdEntrydProp;
    }

    public void setStdEntrydProp(String stdEntrydProp) {
        this.stdEntrydProp.set(stdEntrydProp);
    }

    public String getStdUpdatdProp() {
        return stdUpdatdProp.get();
    }

    public StringProperty stdUpdatdPropProperty() {
        return stdUpdatdProp;
    }

    public void setStdUpdatdProp(String stdUpdatdProp) {
        this.stdUpdatdProp.set(stdUpdatdProp);
    }

    //for users table
    public int getUsrIdProp() {
        return usrIdProp.get();
    }

    public IntegerProperty usrIdPropProperty() {
        return usrIdProp;
    }

    public void setUsrIdProp(int usrIdProp) {
        this.usrIdProp.set(usrIdProp);
    }

    public String getUsrUsrnamProp() {
        return usrUsrnamProp.get();
    }

    public StringProperty usrUsrnamPropProperty() {
        return usrUsrnamProp;
    }

    public void setUsrUsrnamProp(String usrUsrnamProp) {
        this.usrUsrnamProp.set(usrUsrnamProp);
    }

    public String getUsrUtypeProp() {
        return usrUtypeProp.get();
    }

    public StringProperty usrUtypePropProperty() {
        return usrUtypeProp;
    }

    public void setUsrUtypeProp(String usrUtypeProp) {
        this.usrUtypeProp.set(usrUtypeProp);
    }

    public String getUsrActivtyProp() {
        return usrActivtyProp.get();
    }

    public StringProperty usrActivtyPropProperty() {
        return usrActivtyProp;
    }

    public void setUsrActivtyProp(String usrActivtyProp) {
        this.usrActivtyProp.set(usrActivtyProp);
    }

    public String getUsrEntryDatProp() {
        return usrEntryDatProp.get();
    }

    public StringProperty usrEntryDatPropProperty() {
        return usrEntryDatProp;
    }

    public void setUsrEntryDatProp(String usrEntryDatProp) {
        this.usrEntryDatProp.set(usrEntryDatProp);
    }

    //subjct gs and s
    public int getIdProprty() {
        return idProprty.get();
    }

    public IntegerProperty idProprtyProperty() {
        return idProprty;
    }

    public void setIdProprty(int idProprty) {
        this.idProprty.set(idProprty);
    }

    public String getSubjCodProp() {
        return subjCodProp.get();
    }

    public StringProperty subjCodPropProperty() {
        return subjCodProp;
    }

    public void setSubjCodProp(String subjCodProp) {
        this.subjCodProp.set(subjCodProp);
    }

    public String getSubjNamProp() {
        return subjNamProp.get();
    }

    public StringProperty subjNamPropProperty() {
        return subjNamProp;
    }

    public void setSubjNamProp(String subjNamProp) {
        this.subjNamProp.set(subjNamProp);
    }

    public String getSubjDescrProp() {
        return subjDescrProp.get();
    }

    public StringProperty subjDescrPropProperty() {
        return subjDescrProp;
    }

    public void setSubjDescrProp(String subjDescrProp) {
        this.subjDescrProp.set(subjDescrProp);
    }

    public String getSubjEntryDProp() {
        return subjEntryDProp.get();
    }

    public StringProperty subjEntryDPropProperty() {
        return subjEntryDProp;
    }

    public void setSubjEntryDProp(String subjEntryDProp) {
        this.subjEntryDProp.set(subjEntryDProp);
    }

    //classes gs and s
    public int getClsIDProp() {
        return clsIDProp.get();
    }

    public IntegerProperty clsIDPropProperty() {
        return clsIDProp;
    }

    public void setClsIDProp(int clsIDProp) {
        this.clsIDProp.set(clsIDProp);
    }

    public int getClsStdNumProp() {
        return clsStdNumProp.get();
    }

    public IntegerProperty clsStdNumPropProperty() {
        return clsStdNumProp;
    }

    public void setClsStdNumProp(int clsStdNumProp) {
        this.clsStdNumProp.set(clsStdNumProp);
    }

    public String getClsGradProp() {
        return clsGradProp.get();
    }

    public StringProperty clsGradPropProperty() {
        return clsGradProp;
    }

    public void setClsGradProp(String clsGradProp) {
        this.clsGradProp.set(clsGradProp);
    }

    public String getClsTchIDProp() {
        return clsTchIDProp.get();
    }

    public StringProperty clsTchIDPropProperty() {
        return clsTchIDProp;
    }

    public void setClsTchIDProp(String clsTchIDProp) {
        this.clsTchIDProp.set(clsTchIDProp);
    }

    public String getClsTchSurnProp() {
        return clsTchSurnProp.get();
    }

    public StringProperty clsTchSurnPropProperty() {
        return clsTchSurnProp;
    }

    public void setClsTchSurnProp(String clsTchSurnProp) {
        this.clsTchSurnProp.set(clsTchSurnProp);
    }

    public String getClsEntrydProp() {
        return clsEntrydProp.get();
    }

    public StringProperty clsEntrydPropProperty() {
        return clsEntrydProp;
    }

    public void setClsEntrydProp(String clsEntrydProp) {
        this.clsEntrydProp.set(clsEntrydProp);
    }

    public String getClsUpdatedProp() {
        return clsUpdatedProp.get();
    }

    public StringProperty clsUpdatedPropProperty() {
        return clsUpdatedProp;
    }

    public void setClsUpdatedProp(String clsUpdatedProp) {
        this.clsUpdatedProp.set(clsUpdatedProp);
    }

    //class alloc gs and s
    public int getCalloStdidProp() {
        return calloStdidProp.get();
    }

    public IntegerProperty calloStdidPropProperty() {
        return calloStdidProp;
    }

    public void setCalloStdidProp(int calloStdidProp) {
        this.calloStdidProp.set(calloStdidProp);
    }

    public String getCalloStdNamProp() {
        return calloStdNamProp.get();
    }

    public StringProperty calloStdNamPropProperty() {
        return calloStdNamProp;
    }

    public void setCalloStdNamProp(String calloStdNamProp) {
        this.calloStdNamProp.set(calloStdNamProp);
    }

    public String getCalloGradProp() {
        return calloGradProp.get();
    }

    public StringProperty calloGradPropProperty() {
        return calloGradProp;
    }

    public void setCalloGradProp(String calloGradProp) {
        this.calloGradProp.set(calloGradProp);
    }

    public String getCalloUpdatedProp() {
        return calloUpdatedProp.get();
    }

    public StringProperty calloUpdatedPropProperty() {
        return calloUpdatedProp;
    }

    public void setCalloUpdatedProp(String calloUpdatedProp) {
        this.calloUpdatedProp.set(calloUpdatedProp);
    }

    //student report card
    public int getSrcIdProp() {
        return srcIdProp.get();
    }

    public IntegerProperty srcIdPropProperty() {
        return srcIdProp;
    }

    public void setSrcIdProp(int srcIdProp) {
        this.srcIdProp.set(srcIdProp);
    }

    public String getSrcSubjCProp() {
        return srcSubjCProp.get();
    }

    public StringProperty srcSubjCPropProperty() {
        return srcSubjCProp;
    }

    public void setSrcSubjCProp(String srcSubjCProp) {
        this.srcSubjCProp.set(srcSubjCProp);
    }

    public String getSrcStudIdProp() {
        return srcStudIdProp.get();
    }

    public StringProperty srcStudIdPropProperty() {
        return srcStudIdProp;
    }

    public void setSrcStudIdProp(String srcStudIdProp) {
        this.srcStudIdProp.set(srcStudIdProp);
    }

    public String getSrcMarkProp() {
        return srcMarkProp.get();
    }

    public StringProperty srcMarkPropProperty() {
        return srcMarkProp;
    }

    public void setSrcMarkProp(String srcMarkProp) {
        this.srcMarkProp.set(srcMarkProp);
    }

    public String getSrcRemarkProp() {
        return srcRemarkProp.get();
    }

    public StringProperty srcRemarkPropProperty() {
        return srcRemarkProp;
    }

    public void setSrcRemarkProp(String srcRemarkProp) {
        this.srcRemarkProp.set(srcRemarkProp);
    }

    public String getSrcGradeProp() {
        return srcGradeProp.get();
    }

    public StringProperty srcGradePropProperty() {
        return srcGradeProp;
    }

    public void setSrcGradeProp(String srcGradeProp) {
        this.srcGradeProp.set(srcGradeProp);
    }

    public String getSrcTchIdProp() {
        return srcTchIdProp.get();
    }

    public StringProperty srcTchIdPropProperty() {
        return srcTchIdProp;
    }

    public void setSrcTchIdProp(String srcTchIdProp) {
        this.srcTchIdProp.set(srcTchIdProp);
    }

    public String getSrcTermProp() {
        return srcTermProp.get();
    }

    public StringProperty srcTermPropProperty() {
        return srcTermProp;
    }

    public void setSrcTermProp(String srcTermProp) {
        this.srcTermProp.set(srcTermProp);
    }

    public String getSrcYearProp() {
        return srcYearProp.get();
    }

    public StringProperty srcYearPropProperty() {
        return srcYearProp;
    }

    public void setSrcYearProp(String srcYearProp) {
        this.srcYearProp.set(srcYearProp);
    }

    public String getSrcEntrydProp() {
        return srcEntrydProp.get();
    }

    public StringProperty srcEntrydPropProperty() {
        return srcEntrydProp;
    }

    public void setSrcEntrydProp(String srcEntrydProp) {
        this.srcEntrydProp.set(srcEntrydProp);
    }
}
