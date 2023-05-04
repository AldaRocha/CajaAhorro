
package org.utl.dsm.cajaahorro.core;

import java.sql.Connection;
import java.sql.DriverManager;

/**
 *
 * @author rocha
 */
public class ConexionMySQL{
    Connection conn;
    public Connection open(){
        String usuario="root";
        String contrasenia="2418Fran11@@";
        String url="jdbc:mysql://127.0.0.1:3306/caja_ahorro?useSSL=false&"
                  +"allowPublicKeyRetrieval=true&"
                  +"useUnicode=true&characterEncoding=utf-8";
        try{
            Class.forName("com.mysql.cj.jdbc.Driver");
            conn=DriverManager.getConnection(url, usuario, contrasenia);
            return conn;
        } catch(Exception e){
            throw new RuntimeException(e);
        }
    }
    
    public void close(){
        if(conn!=null){
            try{
                conn.close();
            } catch(Exception e){
                e.printStackTrace();
                System.out.println("Exception controlada.");
            }
        }
    }
}
