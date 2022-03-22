package sampleGUI.services;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.ButtonBar;
import javafx.scene.control.ButtonType;
import sampleGUI.AnnounceFactory;
import sampleGUI.models.CustomerGroupDb;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.Optional;

public class SQLCustomerGroupServices extends SQLServerService{
    PreparedStatement preStatement=null;
    public ObservableList<CustomerGroupDb> readAllCustomerGroup (){
        ObservableList<CustomerGroupDb> customerGroups = FXCollections.observableArrayList();
        try {
            String sql="select * from CustomerGroup where IsDeleted=0";
            Statement statement=connection.createStatement();
            ResultSet results = statement.executeQuery(sql);
            while(results.next()){
                CustomerGroupDb customerGroup=new CustomerGroupDb();
                customerGroup.setCodeCustomerGroup(results.getString(1));   //lấy từ số 1, k phải số 0
                customerGroup.setNameCustomerGroup(results.getString(2));
                customerGroup.setIsDelete(0);
                customerGroups.add(customerGroup);
            }
        }catch(Exception ex){
            ex.printStackTrace();
        }
        return customerGroups;
    }

    public boolean checkCodeCustomerGroup(String codeCustomerGroup){
        try {
            String sql="Select * from CustomerGroup where CodeGroup=?";
            preStatement=connection.prepareStatement(sql);
            preStatement.setString(1,codeCustomerGroup);
            return preStatement.executeQuery().next();
        }catch(Exception ex){
            ex.printStackTrace();
        }
        return false;
    }

    public int saveCustomerGroup(CustomerGroupDb customerGroup){
        if(checkCodeCustomerGroup(customerGroup.getCodeCustomerGroup())){  //check nếu mã tồn tại rồi thì update, còn ko thì insert
            Optional<ButtonType> ret= AnnounceFactory.alertYesNo("Confirm to update","Update Customer Group",
                    "Code Customer Group is existed, Do you want to update ","YES","NO", ButtonBar.ButtonData.YES, ButtonBar.ButtonData.NO);
            if(ret.get().getButtonData()==ButtonBar.ButtonData.NO) return -1;
            try {
                String sql="update CustomerGroup set NameGroup=?, IsDeleted=? where CodeGroup=?";
                preStatement=connection.prepareStatement(sql);
                preStatement.setString(1, customerGroup.getNameCustomerGroup());
                preStatement.setInt(2,0);
                preStatement.setString(3, customerGroup.getCodeCustomerGroup());
                return preStatement.executeUpdate();
            }catch(Exception ex){
                ex.printStackTrace();
            }
        }
        else {
            try {
                String sql = "insert into CustomerGroup values(?,?,?)";
                preStatement = connection.prepareStatement(sql);
                preStatement.setString(1, customerGroup.getCodeCustomerGroup());
                preStatement.setString(2, customerGroup.getNameCustomerGroup());
                preStatement.setInt(3, 0);
                return preStatement.executeUpdate();
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        }
        return -1;
    }

    public int deleteCustomerGroup(String codeCustomerGroup){
        try {
            String sql="update CustomerGroup set IsDeleted=? where CodeGroup=?";
            preStatement=connection.prepareStatement(sql);
            preStatement.setInt(1,1);
            preStatement.setString(2, codeCustomerGroup);
            return preStatement.executeUpdate();
        }catch(Exception ex){
            ex.printStackTrace();
        }
        return -1;
    }
}
