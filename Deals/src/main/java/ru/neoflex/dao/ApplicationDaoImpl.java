package ru.neoflex.dao;


import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import ru.neoflex.models.Application;

import java.util.List;
@Repository
@Transactional
public class ApplicationDaoImpl implements ApplicationDao {
    //private static final Logger logger = LoggerFactory.getLogger(ClientDaoImpl.class);
    @Autowired
    private SessionFactory sessionFactory;

    @Override
    public Application findApplicationByID(int id)  {
        Integer findId = (Integer) id;
        Session session = sessionFactory.getCurrentSession();
        Application application = (Application) session.load(Application.class, findId);
        return application;
    }

    @Override
    public void addApplication(Application application)  {
        Session session = sessionFactory.getCurrentSession();
        session.persist(application);
    }

    @Override
    public void updateApplication(Application application)  {
        Session session = sessionFactory.getCurrentSession();
        session.update(application);
    }

    @Override
    public void removeApplication(int id)  {
        Integer findId = (Integer) id;
        Session session = sessionFactory.getCurrentSession();
        Application application = (Application) session.load(Application.class, findId);
        if (application != null) session.remove(application);
    }

    @Override
    public List<Application> listApplications()  {
        Session session = sessionFactory.getCurrentSession();
        List<Application> applicationList= session. createNativeQuery("Select * from applications",Application.class).list();
        return applicationList;
    }
}
