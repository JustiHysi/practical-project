package org.example.config;


import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;
import org.hibernate.cfg.Environment;
import org.hibernate.service.ServiceRegistry;

import java.util.List;
import java.util.Properties;

public class DbManager {
    private SessionFactory factory ;
    private static final Logger logger = LogManager.getLogger(DbManager.class);
//    private static  final Logg

    private DbManager(){
        if(factory==null){
            try{
                Configuration conf =  new Configuration();
                Properties properties = new Properties();
                properties.put(Environment.DRIVER, "com.mysql.jdbc.Driver");
                properties.put(Environment.URL,
                        "jdbc:mysql://localhost:3306/school");
                properties.put(Environment.USER, "root");
                properties.put(Environment.PASS, "1234");
                properties.put(Environment.DIALECT,
                        "org.hibernate.dialect.MySQL5Dialect");
                properties.put(Environment.SHOW_SQL, "true");
//                conf.setProperties(properties);
                conf.addProperties(properties);

                ServiceRegistry serviceRegistry = new StandardServiceRegistryBuilder()
                        .applySettings(conf.getProperties()).build();
                factory = conf.buildSessionFactory(serviceRegistry);
            }catch (Exception e){
                logger.error("Error: " + e.getMessage());

            }
        }
    }

    public  static  DbManager getInstance(){
        return  new DbManager();
    }


    public SessionFactory getFactory() {
        return factory;
    }

    public boolean isConnectedSuccessfully(){
        boolean connected =  false;
        if(factory!=null){

            Session session = factory.openSession();
//            Transaction transaction = session.beginTransaction();
            try{
                List<Object> objects = session.createNativeQuery("show databases;").getResultList();
                if(objects!=null && objects.size()>0){
                    connected= true;
                }
            }catch (Exception e){
                logger.error("Error: " + e.getMessage());
//                 transaction.rollback();
            }finally {
                if(session!=null){
                    session.clear();
                    session.close();
                }
            }

        }
        return  connected;
    }
}
