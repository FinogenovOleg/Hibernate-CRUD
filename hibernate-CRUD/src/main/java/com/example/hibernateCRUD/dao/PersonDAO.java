package com.example.hibernateCRUD.dao;

import com.example.hibernateCRUD.entity.Person;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class PersonDAO {

    public Session setConfigurationAndGetSession() {
        Configuration configuration = new Configuration().addAnnotatedClass(Person.class);
        SessionFactory sessionFactory = configuration.buildSessionFactory();
        return sessionFactory.openSession();
    }
    public List<Person> read() {
        Session session = setConfigurationAndGetSession();
        List<Person> list;
        try {
            session.beginTransaction();
            list = session.createQuery("FROM Person").getResultList();
            session.getTransaction().commit();
        } finally {
            session.close();
        }
        return list;
    }

    public void create(Person person) {
        Session session = setConfigurationAndGetSession();
        try {
            session.beginTransaction();
            session.persist(person);
            session.getTransaction().commit();
        } finally {
            session.close();
        }
    }

    public Person getPersonByID(int id) {
        Session session = setConfigurationAndGetSession();
        Person person;
        try {
            session.beginTransaction();
            person = session.get(Person.class, id);
            session.getTransaction().commit();
        } finally {
            session.close();
        }
        return person;
    }

    public void delete(int id) {
        Session session = setConfigurationAndGetSession();
        try {
            session.beginTransaction();
            session.remove(session.get(Person.class, id));
            session.getTransaction().commit();
        } finally {
            session.close();
        }
    }

    public void update(Person person) {
        Session session = setConfigurationAndGetSession();
        try {
            session.beginTransaction();
            session.update(person);
            session.getTransaction().commit();
        } finally {
            session.close();
        }
    }
}
