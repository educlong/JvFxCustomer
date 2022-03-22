package sampleGUI.services;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.ButtonBar;
import javafx.scene.control.ButtonType;
import sampleGUI.AnnounceFactory;
import sampleGUI.models.CustomerDb;
import sampleGUI.models.CustomerGroupDb;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.Optional;

public class SQLCustomerServices extends SQLServerService {
    PreparedStatement preStatement=null;
    public static ResultSet results=null;
    public ObservableList<CustomerDb> readAllCustomerInGroup (String codeCustomerGroup){  // đọc tất cả các customer trong 1 customerGroup
        ObservableList<CustomerDb> customers = FXCollections.observableArrayList();
        try {
            String sql="select * from Customer where CodeGroup=? and IsDeleted=0";
            preStatement=connection.prepareStatement(sql,                           //TYPE_SCROLL_INSENSITIVE: cho phép đọc bất cứ dòng nào
                    ResultSet.TYPE_SCROLL_INSENSITIVE,ResultSet.CONCUR_READ_ONLY);  //CONCUR_READ_ONLY: chỉ cho phép đọc, k đc ghi
            preStatement.setString(1,codeCustomerGroup);
            results = preStatement.executeQuery();
            while(results.next()){
                CustomerDb customer=new CustomerDb();
                customer.setCodeCustomer(results.getString(1));   //lấy từ số 1, k phải số 0
                customer.setNameCustomer(results.getString(2));
                customer.setPhoneCustomer(results.getString(3));
                customer.setEmailCustomer(results.getString(4));
                customer.setCodeCustomerGroup(results.getString(5));
                customer.setIsDelete(0);
                customers.add(customer);
            }
        }catch(Exception ex){
            ex.printStackTrace();
        }
        return customers;
    }

    public boolean checkCodeCustomer(String codeCustomer){
        try {
            String sql="Select * from Customer where CodeCustomer=?";
            preStatement=connection.prepareStatement(sql);
            preStatement.setString(1,codeCustomer);
            return preStatement.executeQuery().next();
        }catch(Exception ex){
            ex.printStackTrace();
        }
        return false;
    }

    public int saveCustomer(CustomerDb customer, CustomerGroupDb customerGroup){
        if(checkCodeCustomer(customer.getCodeCustomer())){  //check nếu mã tồn tại rồi thì update, còn ko thì insert
            Optional<ButtonType> ret= AnnounceFactory.alertYesNo("Confirm to update","Update Customer",
                    "Code Customer is existed, Do you want to update ","YES","NO", ButtonBar.ButtonData.YES, ButtonBar.ButtonData.NO);
            if(ret.get().getButtonData()==ButtonBar.ButtonData.NO) return -1;
            try {
                String sql="update Customer set NameCustomer=?, PhoneCustomer=?, EmailCustomer=?, CodeGroup=?, IsDeleted=? where CodeCustomer=?";
                preStatement=connection.prepareStatement(sql);
                preStatement.setString(1, customer.getNameCustomer());
                preStatement.setString(2, customer.getPhoneCustomer());
                preStatement.setString(3, customer.getEmailCustomer());
                preStatement.setString(4, customerGroup.getCodeCustomerGroup());
                preStatement.setInt(5,0);
                preStatement.setString(6, customer.getCodeCustomer());
                return preStatement.executeUpdate();
            }catch(Exception ex){
                ex.printStackTrace();
            }
        }
        else {
            try {
                String sql = "insert into Customer values(?,?,?,?,?,?)";
                preStatement = connection.prepareStatement(sql);
                preStatement.setString(1, customer.getCodeCustomer());
                preStatement.setString(2, customer.getNameCustomer());
                preStatement.setString(3, customer.getPhoneCustomer());
                preStatement.setString(4, customer.getEmailCustomer());
                preStatement.setString(5, customerGroup.getCodeCustomerGroup());
                preStatement.setInt(6, 0);
                return preStatement.executeUpdate();
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        }
        return -1;
    }

    public int deleteCustomer(String codeCustomer){
        try {
            String sql="update Customer set IsDeleted=? where CodeCustomer=?";
            preStatement=connection.prepareStatement(sql);
            preStatement.setInt(1,1);
            preStatement.setString(2, codeCustomer);
            return preStatement.executeUpdate();
        }catch(Exception ex){
            ex.printStackTrace();
        }
        return -1;
    }
}
