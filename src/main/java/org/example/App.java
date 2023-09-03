package org.example;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.example.config.DbManager;

/**
 * Hello world!
 *
 */
public class App 
{
    private static  final  Logger logger = LogManager.getLogger(App.class);
    public static void main( String[] args )
    {
//        System.out.println( "Hello World!" );
        DbManager manager =  DbManager.getInstance();
        logger.info("connected successfully: " + manager.isConnectedSuccessfully());
    }
}
