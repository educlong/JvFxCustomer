package sampleGUI.models;

import java.io.Serializable;

public class CustomerDb implements Serializable {
    private String codeCustomer;        /*trong sql có bao nhiêu cột thì customer có bấy nhiêu thuộc tính*/
    private String nameCustomer;
    private String phoneCustomer;
    private String emailCustomer;
    private int isDelete;
    private String codeCustomerGroup;
    private CustomerGroupDb groupCustomer;/*thêm 1 thuộc tính để biết customer này thuộc customerGroup nào*/

    @Override
    public String toString() {
        return this.codeCustomer+": "+this.nameCustomer+", "+this.phoneCustomer+", "+this.emailCustomer+", "+this.groupCustomer.getNameCustomerGroup();
    }

    public CustomerDb() {
        this.isDelete=0;    /*khởi tạo ban đầu cho customer khi được new ra là chưa bị xóa, tức là isdelete=0*/
    }

    public CustomerDb(String codeCustomer, String nameCustomer, String phoneCustomer, String emailCustomer) {
        this.codeCustomer = codeCustomer;
        this.nameCustomer = nameCustomer;
        this.phoneCustomer = phoneCustomer;
        this.emailCustomer = emailCustomer;
    }

    public CustomerDb(String codeCustomer, String nameCustomer, String phoneCustomer, String emailCustomer, CustomerGroupDb groupCustomer) {
        this.codeCustomer = codeCustomer;
        this.nameCustomer = nameCustomer;
        this.phoneCustomer = phoneCustomer;
        this.emailCustomer = emailCustomer;
        this.groupCustomer = groupCustomer;
    }

    public String getCodeCustomer() {
        return codeCustomer;
    }

    public void setCodeCustomer(String codeCustomer) {
        this.codeCustomer = codeCustomer;
    }

    public String getNameCustomer() {
        return nameCustomer;
    }

    public void setNameCustomer(String nameCustomer) {
        this.nameCustomer = nameCustomer;
    }

    public String getPhoneCustomer() {
        return phoneCustomer;
    }

    public void setPhoneCustomer(String phoneCustomer) {
        this.phoneCustomer = phoneCustomer;
    }

    public String getEmailCustomer() {
        return emailCustomer;
    }

    public void setEmailCustomer(String emailCustomer) {
        this.emailCustomer = emailCustomer;
    }

    public CustomerGroupDb getGroupCustomer() {
        return groupCustomer;
    }

    public void setGroupCustomer(CustomerGroupDb groupCustomer) {
        this.groupCustomer = groupCustomer;
    }

    public String getCodeCustomerGroup() {
        return codeCustomerGroup;
    }

    public void setCodeCustomerGroup(String codeCustomerGroup) {
        this.codeCustomerGroup = codeCustomerGroup;
    }

    public int getIsDelete() {
        return isDelete;
    }

    public void setIsDelete(int isDelete) {
        this.isDelete = isDelete;
    }

}
