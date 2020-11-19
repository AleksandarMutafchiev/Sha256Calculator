package com.example.test.demo.util;

import com.example.test.demo.model.Entety.Document;
import org.hibernate.Session;
import org.hibernate.SessionFactory;

public class HibernateUtil {

    public static Session getHibernateSession() {
        final SessionFactory sf = new org.hibernate.cfg.Configuration().configure("hibernate.cfg.xml")
                .addAnnotatedClass(Document.class).buildSessionFactory();
        final org.hibernate.Session session = sf.openSession();
        return session;
    }

}
