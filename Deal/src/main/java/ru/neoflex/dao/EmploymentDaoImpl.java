package ru.neoflex.dao;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import ru.neoflex.models.Employment;

import java.util.List;
@Repository
@Transactional
public class EmploymentDaoImpl implements EmploymentDao {
    private static final Logger logger = LoggerFactory.getLogger(ClientDaoImpl.class);
    @Autowired
    private SessionFactory sessionFactory;
    @Override
    public Employment findEmploymentByID(int id)  {
        Integer findId=Integer.parseInt(String.valueOf(id));
        Session session =this.sessionFactory.getCurrentSession();
        Employment employment = (Employment) session.load(Employment.class, Integer.valueOf(findId));
        return employment;
    }

    @Override
    public void addEmployment(Employment employment)  {
        Session session =this.sessionFactory.getCurrentSession();
         session.persist(employment);
    }

    @Override
    public void updateEmployment(Employment employment)  {
        Session session =this.sessionFactory.getCurrentSession();
        session.update(employment);
    }

    @Override
    public void removeEmployment(int id)  {
        Integer removeId=Integer.parseInt(String.valueOf(id));
        Session session =this.sessionFactory.getCurrentSession();
        Employment employment = (Employment) session.load(Employment.class, Integer.valueOf(removeId));
        if (employment!=null) session.delete(employment);
    }

    @Override
    public List<Employment> listEmployments()  {
        Session session =this.sessionFactory.getCurrentSession();
        List<Employment> employmentsList= session.createSQLQuery("select * from employments").addEntity(Employment.class).list();
        return employmentsList;
    }
}
