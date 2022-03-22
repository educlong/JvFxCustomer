package sampleGUI.hibernate;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import org.hibernate.Session;
import org.hibernate.Transaction;
import sampleGUI.models.Customer;
import sampleGUI.models.CustomerGroup;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.util.List;

public class DAOCustomerGroup{
    public void saveData(CustomerGroup customerGroup) {
        Session s=HibernateUtil.getSession();
        Transaction t = s.beginTransaction();
        s.save(customerGroup);
        t.commit();
        s.close();
    }

    public void deleteData(CustomerGroup customerGroup) {
        Session s=HibernateUtil.getSession();
        Transaction t = s.beginTransaction();
        customerGroup.setIsDeleted(1);
        s.update(customerGroup);
        t.commit();
        s.close();
    }

    public ObservableList<CustomerGroup> getAll() {
        Session s=HibernateUtil.getSession();
//        CriteriaBuilder builder=s.getCriteriaBuilder();
//        CriteriaQuery<CustomerGroup> query=builder.createQuery(CustomerGroup.class);

//        Root<CustomerGroup> root= query.from(CustomerGroup.class);

//        List<CustomerGroup> cgList=s.createQuery(query).getResultList();

        List<CustomerGroup> cgList=s.createNativeQuery("select * from CustomerGroup where IsDeleted=0",CustomerGroup.class).list();
        s.close();
        return FXCollections.observableArrayList(cgList);
    }
}
