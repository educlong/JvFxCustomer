package sampleGUI.jpa_persistence;

import sampleGUI.models.Customer;

import javax.persistence.*;
import java.util.List;

public class Test {
    public static void main(String[] args) {
        try {
            EntityManagerFactory emf=null;
            EntityManager em=null;
            EntityTransaction et=null;
            emf= Persistence.createEntityManagerFactory("JavaBasicFx");
            em=emf.createEntityManager();
            et=em.getTransaction();
            et.begin();
            Query query=em.createQuery("from Customer ");
            List<Customer>customers=query.getResultList();
            for (Customer customer: customers){
                System.out.println(customer);
            }
        }
        catch (Exception ex){
            ex.printStackTrace();
        }
    }
}
