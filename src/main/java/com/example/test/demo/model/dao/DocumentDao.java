package com.example.test.demo.model.dao;

import com.example.test.demo.model.Entety.Document;
import com.example.test.demo.util.HibernateUtil;
import org.hibernate.Session;
import org.hibernate.query.Query;

import javax.persistence.NoResultException;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Path;
import javax.persistence.criteria.Root;

public class DocumentDao {
    public Document getDocumentByName(String name) {
        final Session session = HibernateUtil.getHibernateSession();
        final CriteriaBuilder cb = session.getCriteriaBuilder();
        final CriteriaQuery<Document> cr = cb.createQuery(Document.class);
        final Root<Document> root = cr.from(Document.class);
        Path path = root.get("name");
        cr.select(root).where(cb.equal(path, name));
        Query<Document> query = session.createQuery(cr);
        Document document = null;
        try {
            document = query.getSingleResult();

        } catch (NoResultException e) {

        }
        return document;
    }
}
