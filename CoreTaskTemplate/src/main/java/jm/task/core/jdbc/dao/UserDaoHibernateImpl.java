package jm.task.core.jdbc.dao;


import jm.task.core.jdbc.model.User;
import jm.task.core.jdbc.util.Util;
import org.hibernate.*;

import java.util.ArrayList;
import java.util.List;

public class UserDaoHibernateImpl implements UserDao {

    private final SessionFactory sessionFactory = Util.getSessionFactory();

    public UserDaoHibernateImpl() {

    }

    /*String sql = "create table IF NOT EXISTS userTable" +
                "(id BIGINT primary key auto_increment, " +
                "name varchar(100), " +
                "lastname varchar(100), " +
                "age tinyint);";*/
    @Override
    public void createUsersTable() {
        Session session = sessionFactory.openSession();
        Transaction tx1 = session.beginTransaction();
        try {
            session.createSQLQuery("create table IF NOT EXISTS userTable" +
                    "(id BIGINT primary key auto_increment, " +
                    "name varchar(100), " +
                    "lastname varchar(100), " +
                    "age tinyint);").executeUpdate();
            tx1.commit();
        } catch (HibernateException e) {
            //  System.out.println("ошибка в createUsersTable");
            //e.printStackTrace();
            tx1.rollback();
        } finally {
            session.close();
        }
    }

    //String sql = "drop table IF EXISTS userTable;";
    @Override
    public void dropUsersTable() {
        Session session = sessionFactory.openSession();
        Transaction tx1 = session.beginTransaction();
        try {
            session.createSQLQuery("drop table IF EXISTS userTable;").executeUpdate();
            tx1.commit();
        } catch (HibernateException e) {
            tx1.rollback();
        } finally {
            session.close();
        }
    }

    @Override
    public void saveUser(String name, String lastName, byte age) {
        User user = new User(name, lastName, age);
        Session session = sessionFactory.openSession();
        Transaction tx1 = session.beginTransaction();
        try {
            session.save(user);
            tx1.commit();
        } catch (HibernateException e) {

            tx1.rollback();
        } finally {
            session.close();
        }
    }

    @Override
    public void removeUserById(long id) {
        Session session = sessionFactory.openSession();
        User user = (User) session.get(User.class, id);
        Transaction tx1 = session.beginTransaction();
        try {
            session.delete(user);
            tx1.commit();
        } catch (HibernateException e) {
            //System.out.println("ошибка в removeUserById");
            tx1.rollback();
        } finally {
            session.close();
        }
    }

    @Override
    public List<User> getAllUsers() {
        Session session = sessionFactory.openSession();
        Transaction tx1 = session.beginTransaction();
        List<User> users = new ArrayList<>();
        try {
            users = session.createQuery("FROM User ").list();
            tx1.commit();
        } catch (HibernateException e) {
            tx1.rollback();
            // System.out.println("СНОВА ЧТО-ТО НЕ ТАК В getAllUsers() ");
        } finally {
            session.close();
        }
        return users;
    }

    /*    public List<User> getAllUsers() {
        List<User> users = new ArrayList<>();
        String sql = "SELECT id,name,lastname,age from userTable";
        try (Statement statement = SQL_connection.createStatement()) {
            ResultSet resultSet = statement.executeQuery(sql);
            while (resultSet.next()) {
                User user = new User();
                user.setId(      resultSet.getLong("id"));
                user.setName(    resultSet.getString("name"));
                user.setLastName(resultSet.getString("lastname"));
                user.setAge(     resultSet.getByte("age"));
                users.add(user);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return users;

    }*/

    @Override
    public void cleanUsersTable() {
        Session session = sessionFactory.openSession();
        Transaction tx1 = session.beginTransaction();
        try {
            session.createSQLQuery("TRUNCATE TABLE userTable;").executeUpdate();
            tx1.commit();
        } catch (HibernateException e) {
            tx1.rollback();
        } finally {
            session.close();
        }
    }
}
