package jm.task.core.jdbc.util;

import jm.task.core.jdbc.model.User;
import org.hibernate.Hibernate;
import org.hibernate.HibernateException;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;


import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;


public class Util {
    //jdbc:mysql://localhost:3306/Peoples?useSSL=false
    private static final String URL = "jdbc:mysql://localhost:3306/jpp_1_3?useSSL=false?serverTimezone=UTC&useSSL=false";
    private static final String USERNAME = "root";
    private static final String PASSWORD = "root";

    private static Connection connection = null;
    private static SessionFactory sessionfactory = null;

    public static Connection getConnection() {
      //  Connection connection = null;

        try {
            connection = DriverManager.getConnection(URL, USERNAME, PASSWORD);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return connection;

    }

    public static SessionFactory getSessionFactory() {
        //SessionFactory sessionFactory = null;
        try {
            Configuration configuration = new Configuration()
                    .setProperty("connection.driver_class","com.mysql.jdbc.Driver")
                    .setProperty("hibernate.connection.url", "jdbc:mysql://localhost:3306/jpp_1_3")
                    .setProperty("hibernate.connection.username", "root")
                    .setProperty("hibernate.connection.password", "root")
                    .setProperty("hibernate.dialect", "org.hibernate.dialect.MySQLDialect")
                    .setProperty("hibernate.show_sql", "true")
                    .addAnnotatedClass(jm.task.core.jdbc.model.User.class);
            StandardServiceRegistryBuilder builder = new StandardServiceRegistryBuilder().applySettings(configuration.getProperties());
            sessionfactory = configuration.buildSessionFactory(builder.build());

        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("Исключение!" + e);
        }
        return sessionfactory;
    }

    public static void shutdown() {
        if(connection != null){
            try {
                getConnection().close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        if(sessionfactory!= null){
            getSessionFactory().close();
        }
    }
}
