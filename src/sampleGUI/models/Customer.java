package sampleGUI.models;

import javax.persistence.*;
import java.util.Objects;

@Entity
public class Customer {
    private String codeCustomer;
    private String nameCustomer;
    private String phoneCustomer;
    private String emailCustomer;
    private Integer isDeleted;
    private CustomerGroup customerGroupByCodeGroup;

    /*tự thêm constructor và tostring vào*/
    public Customer() {

    }
    @Override
    public String toString() {
        return codeCustomer+": " +nameCustomer;
    }
    public Customer(String codeCustomer, String nameCustomer, String phoneCustomer, String emailCustomer, Integer isDeleted, CustomerGroup customerGroupByCodeGroup) {
        this.codeCustomer = codeCustomer;
        this.nameCustomer = nameCustomer;
        this.phoneCustomer = phoneCustomer;
        this.emailCustomer = emailCustomer;
        this.isDeleted = isDeleted;
        this.customerGroupByCodeGroup = customerGroupByCodeGroup;
    }

    @Id
    @Column(name = "CodeCustomer")
    public String getCodeCustomer() {
        return codeCustomer;
    }

    public void setCodeCustomer(String codeCustomer) {
        this.codeCustomer = codeCustomer;
    }

    @Basic
    @Column(name = "NameCustomer")
    public String getNameCustomer() {
        return nameCustomer;
    }

    public void setNameCustomer(String nameCustomer) {
        this.nameCustomer = nameCustomer;
    }

    @Basic
    @Column(name = "PhoneCustomer")
    public String getPhoneCustomer() {
        return phoneCustomer;
    }

    public void setPhoneCustomer(String phoneCustomer) {
        this.phoneCustomer = phoneCustomer;
    }

    @Basic
    @Column(name = "EmailCustomer")
    public String getEmailCustomer() {
        return emailCustomer;
    }

    public void setEmailCustomer(String emailCustomer) {
        this.emailCustomer = emailCustomer;
    }

    @Basic
    @Column(name = "IsDeleted")
    public Integer getIsDeleted() {
        return isDeleted;
    }

    public void setIsDeleted(Integer isDeleted) {
        this.isDeleted = isDeleted;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Customer customer = (Customer) o;
        return Objects.equals(codeCustomer, customer.codeCustomer) && Objects.equals(nameCustomer, customer.nameCustomer) && Objects.equals(phoneCustomer, customer.phoneCustomer) && Objects.equals(emailCustomer, customer.emailCustomer) && Objects.equals(isDeleted, customer.isDeleted);
    }

    @Override
    public int hashCode() {
        return Objects.hash(codeCustomer, nameCustomer, phoneCustomer, emailCustomer, isDeleted);
    }

    @ManyToOne
    @JoinColumn(name = "CodeGroup", referencedColumnName = "CodeGroup")
    public CustomerGroup getCustomerGroupByCodeGroup() {
        return customerGroupByCodeGroup;
    }

    public void setCustomerGroupByCodeGroup(CustomerGroup customerGroupByCodeGroup) {
        this.customerGroupByCodeGroup = customerGroupByCodeGroup;
    }
}
