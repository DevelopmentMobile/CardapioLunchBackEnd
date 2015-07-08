package br.edu.ifpe.pdm.cardapiolanches.backend;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;

/**
 * Created by Richardson on 04/07/2015.
 */
public class ConnectionCardapioLunch {

    public static Connection getConnection()throws Exception{
        try{
            Class.forName("com.mysql.jdbc.Driver");
            return DriverManager.getConnection("jdbc:mysql://localhost:3306/cardapiolunch", "cardapiolunch", "12345");
        }catch(Exception e){
            throw new Exception(e.getMessage());
        }
    }
    public static void closeConnection(Connection conn, Statement stmt, ResultSet set) throws Exception{
        close(conn,stmt,set);
    }


    public static void closeConnection(Connection conn, Statement stmt) throws Exception{
        close(conn,stmt,null);
    }

    public static void closeConnection(Connection conn) throws Exception{
        close(conn,null,null);
    }

    private static void close(Connection conn, Statement stmt, ResultSet rs)throws Exception{
        try{
            if(rs!= null) rs.close();
            if(stmt!= null) stmt.close();
            if(conn!= null) conn.close();
        }catch(Exception e){
            throw new Exception(e.getMessage());
        }
    }
}
