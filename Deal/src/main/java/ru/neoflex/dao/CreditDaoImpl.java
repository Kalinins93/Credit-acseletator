package ru.neoflex.dao;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import ru.neoflex.models.Credit;

import java.util.List;
@Repository
@Transactional
public class CreditDaoImpl implements CreditDao{
    private static final Logger logger = LoggerFactory.getLogger(ClientDaoImpl.class);
    @Autowired
    private SessionFactory sessionFactory;
    @Override
    public Credit findCreditByID(int id) {
        Integer findId = (Integer)id;
        Session session = sessionFactory.getCurrentSession();
        Credit credit = (Credit)session.load(Credit.class,findId);
        return credit;
    }

    @Override
    public void addCredit(Credit credit) {
        Session session = sessionFactory.getCurrentSession();
        session.persist(credit);
    }

    @Override
    public void updateCredit(Credit credit) {
        Session session = sessionFactory.getCurrentSession();
        session.update(credit);
    }

    @Override
    public void removeCredit(int id) {
        Integer findId = (Integer)id;
        Session session = sessionFactory.getCurrentSession();
        Credit credit = (Credit) session.load(Credit.class, findId);
        if(credit!=null)session.remove(credit);
    }

    @Override
    public List<Credit> listCredits() {
        Session session = sessionFactory.getCurrentSession();
        List<Credit> creditList = session.createSQLQuery("Select * from credits").addEntity(Credit.class).list();
        return creditList;
    }
}
