package osdetect;

import java.sql.*;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;

/**
 *
 * @author WALTON
 */
public class DBConnection {

    private static Connection con;
    private static Statement stmt;

    private static ResultSet rs;
    private static PreparedStatement psmt;

    public static Connection getConnection() {
        if (NetworkUtil.isNetworkAvailable()) {
            try {
                Class.forName("oracle.jdbc.driver.OracleDriver");
              //  con = DriverManager.getConnection("jdbc:oracle:thin:@192.168.200.64:1521:hrpos", "WEP", "WEP");
                con = DriverManager.getConnection("jdbc:oracle:thin:@127.0.0.1:1521:xe", "WEP2", "WEP2");
                if (con == null) {
                    System.err.println("Failed to connect with database.");
                }
            } catch (SQLException | ClassNotFoundException ex) {
                Logger.getLogger(DBConnection.class.getName()).log(Level.SEVERE, null, ex);
            }
        } else {
            JOptionPane.showMessageDialog(null, "No internet connection!");

        }
        return con;
    }
}
