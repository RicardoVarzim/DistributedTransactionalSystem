
package bankserver;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class DerbyTest {
    
    public DerbyTest() {
        try {
            Class.forName("org.apache.derby.jdbc.ClientDriver");
        } catch (ClassNotFoundException cnfe) {
            System.err.println("Derby driver not found.");
        }
        try {
            Connection conn = DriverManager.getConnection("jdbc:derby://localhost/test;create=true;user=APP;pass=APP");
            Statement s = conn.createStatement();
            s.execute("CREATE TABLE test (id integer primary key not null, text varchar(32))");            
            s.execute("INSERT INTO test VALUES (1, 'hello world!')");
            s.execute("SELECT * FROM test");
            ResultSet rs = s.getResultSet();
            while (rs.next()) {
                System.out.println("Derby says: "+rs.getString("text"));
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        
    }
    
    public static void main(String[] args) {
        new DerbyTest();
    }    
}