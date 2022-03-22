package sampleGUI.hibernate;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.ButtonBar;
import javafx.scene.control.ButtonType;
import org.hibernate.Session;
import org.hibernate.Transaction;
import sampleGUI.AnnounceFactory;
import sampleGUI.models.Customer;
import sampleGUI.models.CustomerGroup;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import javax.swing.*;
import java.util.List;
import java.util.Optional;

public class DAOCustomer{
    Session s=HibernateUtil.getSession();

    public boolean checkCodeCustomer(String codeCustomer){
        try {
            return s.createNativeQuery("select * from Customer where CodeCustomer='" + codeCustomer + "'").list().size()>0;
        }
        catch (Exception ex){
            ex.printStackTrace();
        }
        return false;
    }

    public int saveData(Customer customer) {
        try {
            Session s = HibernateUtil.getSession();
            Transaction t = s.beginTransaction();
            if (checkCodeCustomer(customer.getCodeCustomer())) {
                Optional<ButtonType> ret = AnnounceFactory.alertYesNo("Confirm to update", "Update Customer",
                        "Code Customer is existed, Do you want to update ", "YES", "NO", ButtonBar.ButtonData.YES, ButtonBar.ButtonData.NO);
                if (ret.get().getButtonData() == ButtonBar.ButtonData.NO) return -1;
                s.update(customer);
            }
            else {
                Optional<ButtonType> ret = AnnounceFactory.alertYesNo("Confirm to saving", "Saving a Customer",
                        "Code Customer is a new code, Do you want to save ", "YES", "NO", ButtonBar.ButtonData.YES, ButtonBar.ButtonData.NO);
                if (ret.get().getButtonData() == ButtonBar.ButtonData.NO) return -1;
                s.save(customer);
            }
            t.commit();
            s.close();
            return 0;
        }
        catch (Exception ex){
            ex.printStackTrace();
        }
        return -1;
    }

    /*do not do this action*/
    public void deleteData(Customer customer) {
        Session s=HibernateUtil.getSession();
        Transaction t = s.beginTransaction();
        customer.setIsDeleted(1);   //delete customer
        s.update(customer);
        t.commit();
        s.close();
    }
    public ObservableList<Customer> getAll(CustomerGroup customerGroup) {
//        CriteriaBuilder builder=s.getCriteriaBuilder();
//        CriteriaQuery<Customer> query=builder.createQuery(Customer.class);
//
//        Root<Customer> root= query.from(Customer.class);

//        List<Customer> cList=s.createQuery(query).getResultList();
//        List<Customer> cList=s.createNativeQuery
//                ("select * from Customer cus where cus.customerGroupByCodeGroup.codeGroup=:cg",Customer.class)
//                .setParameter("cg",customerGroup.getCodeGroup()).list();

//        List<Customer> cList=s.createNativeQuery("select * from Customer where IsDeleted=0",Customer.class).list();
        List<Customer> cList=s.createNativeQuery("select * from Customer where IsDeleted=0 and CodeGroup='"+customerGroup.getCodeGroup()+"'",Customer.class).list();

        s.close();
        return FXCollections.observableArrayList(cList);
    }

}
