package sampleGUI.services;

import java.sql.Connection;
import java.sql.DriverManager;

public class SQLServerService {
    protected Connection connection=null;
    public SQLServerService(){
        String userName="sa";
        String password="1001";
        String serverName="DESKTOP-M3M3TQ4\\SQLEXPRESS";
        int portNumber=1433;
        String databaseName="DbCustomerFX";
        try {
            Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
            String connectionUrl="jdbc:sqlserver://"+serverName+":"+portNumber+";databaseName="+databaseName+";username="+userName+";password="+password;
            connection= DriverManager.getConnection(connectionUrl);
        } catch (Exception ex){
            ex.printStackTrace();
        }
    }

}
