
package registrationandlogin;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Conn{
    public static Connection connect(){
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            return DriverManager.getConnection("jdbc:mysql://localhost:3306/dbconnection","root","Tebalelo123@");
        }
        catch(ClassNotFoundException | SQLException e){
            e.printStackTrace();
           return null;
        }
       
    }
    
}
