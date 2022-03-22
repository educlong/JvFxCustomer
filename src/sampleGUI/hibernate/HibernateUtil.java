package sampleGUI.hibernate;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

public class HibernateUtil {
    public static Session getSession(){
        SessionFactory sf=new Configuration().configure("hibernate.cfg.xml").buildSessionFactory();
        return sf.openSession();
    }
}
