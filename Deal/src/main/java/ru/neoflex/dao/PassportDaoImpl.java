package ru.neoflex.dao;

import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import ru.neoflex.models.Passport;

import java.util.List;
@Repository
@Transactional
public class PassportDaoImpl implements PassportDao {
    private static final Logger logger = LoggerFactory.getLogger(ClientDaoImpl.class);
    @Autowired
    private SessionFactory sessionFactory;
    @Override
    public Passport findPassportByID(int id)  {
        Integer findPassportID = (Integer) id;
        Session session = this.sessionFactory.getCurrentSession();
        Passport passport = (Passport) session.load(Passport.class,findPassportID);
        return passport;
    }

    @Override
    public void addPassport(Passport passport)  {
        Session session = this.sessionFactory.getCurrentSession();
        session.persist(passport);
    }

    @Override
    public void updatePassport(Passport passport)  {
        Session session = this.sessionFactory.getCurrentSession();
        session.update(passport);
    }

    @Override
    public void removePassport(int id)  {
            Integer findPassportById= (Integer)id;
            Session session = this.sessionFactory.getCurrentSession();
            Passport passport = session.load(Passport.class,findPassportById);
            if(passport!=null)session.delete(passport);
    }

    @Override
    public List<Passport> listPassports()  {
        Session session = this.sessionFactory.getCurrentSession();
        List<Passport> passportList = session.createSQLQuery("Select * from pasports").addEntity(Passport.class).list();
        return passportList;
    }
}
