package sampleGUI.models;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class CustomerGroupDb {
    private String codeCustomerGroup;   /*trong sql có bao nhiêu cột thì customerGroup có bấy nhiêu thuộc tính*/
    private String nameCustomerGroup;
    private int isDelete;
    private ObservableList<CustomerDb> customers = FXCollections.observableArrayList();   /*thêm 1 thuộc tính để chứa list các customer*/

    @Override
    public String toString() {
        return this.codeCustomerGroup+": "+this.nameCustomerGroup;
    }

    public void addCustomer(CustomerDb customer) {    /*add thêm 1 customer vào customerGroup*/
        this.customers.add(customer);
        customer.setGroupCustomer(this);
        customer.setCodeCustomerGroup(this.codeCustomerGroup);
    }

    public CustomerGroupDb() {    /*khởi tạo ban đầu cho customerGroup khi được new ra là chưa bị xóa, tức là isdelete=0*/
        this.isDelete=0;
    }

    public CustomerGroupDb(String codeCustomerGroup, String nameCustomerGroup) {
        this.codeCustomerGroup = codeCustomerGroup;
        this.nameCustomerGroup = nameCustomerGroup;
    }

    public CustomerGroupDb(String codeCustomerGroup, String nameCustomerGroup, ObservableList<CustomerDb> customers) {
        this.codeCustomerGroup = codeCustomerGroup;
        this.nameCustomerGroup = nameCustomerGroup;
        this.customers = customers;
    }

    public int getIsDelete() {
        return isDelete;
    }

    public void setIsDelete(int isDelete) {
        this.isDelete = isDelete;
    }

    public String getCodeCustomerGroup() {
        return codeCustomerGroup;
    }

    public void setCodeCustomerGroup(String codeCustomerGroup) {
        this.codeCustomerGroup = codeCustomerGroup;
    }

    public String getNameCustomerGroup() {
        return nameCustomerGroup;
    }

    public void setNameCustomerGroup(String nameCustomerGroup) {
        this.nameCustomerGroup = nameCustomerGroup;
    }

    public ObservableList<CustomerDb> getCustomers() {
        return customers;
    }

    public void setCustomers(ObservableList<CustomerDb> customers) {
        this.customers = customers;
    }
}
