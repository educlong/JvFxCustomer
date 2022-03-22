package sampleGUI.models;

import javax.persistence.*;
import java.util.Collection;
import java.util.Objects;

@Entity
public class CustomerGroup {
    private String codeGroup;
    private String nameGroup;
    private Integer isDeleted;
    private Collection<Customer> customersByCodeGroup;

    /*tự thêm constructor và tostring vào*/
    public CustomerGroup() {
    }
    public CustomerGroup(String codeGroup, String nameGroup, Integer isDeleted, Collection<Customer> customersByCodeGroup) {
        this.codeGroup = codeGroup;
        this.nameGroup = nameGroup;
        this.isDeleted = isDeleted;
        this.customersByCodeGroup = customersByCodeGroup;
    }
    @Override
    public String toString() {
        return codeGroup + ": "+nameGroup;
    }

    @Id
    @Column(name = "CodeGroup")
    public String getCodeGroup() {
        return codeGroup;
    }

    public void setCodeGroup(String codeGroup) {
        this.codeGroup = codeGroup;
    }

    @Basic
    @Column(name = "NameGroup")
    public String getNameGroup() {
        return nameGroup;
    }

    public void setNameGroup(String nameGroup) {
        this.nameGroup = nameGroup;
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
        CustomerGroup that = (CustomerGroup) o;
        return Objects.equals(codeGroup, that.codeGroup) && Objects.equals(nameGroup, that.nameGroup) && Objects.equals(isDeleted, that.isDeleted);
    }

    @Override
    public int hashCode() {
        return Objects.hash(codeGroup, nameGroup, isDeleted);
    }

    @OneToMany(mappedBy = "customerGroupByCodeGroup")
    public Collection<Customer> getCustomersByCodeGroup() {
        return customersByCodeGroup;
    }

    public void setCustomersByCodeGroup(Collection<Customer> customersByCodeGroup) {
        this.customersByCodeGroup = customersByCodeGroup;
    }
}
