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

    public static Connection getConnection() throws SQLException {
        if (NetworkUtil.isNetworkAvailable()) {
            try {
                Class.forName("com.mysql.cj.jdbc.Driver");
                //  con = DriverManager.getConnection("jdbc:oracle:thin:@192.168.200.64:1521:hrpos", "WEP", "WEP");
                con = DriverManager.getConnection("jdbc:mysql://localhost:3306/wep_desktop_app","root","1234");
                //create hw_info table
                stmt = con.createStatement();
                String sql = "CREATE TABLE IF NOT EXISTS HW_INFO (\n"
                        + "	ID INT NOT NULL AUTO_INCREMENT,\n"
                        + "	MAC_ADDRESS VARCHAR(20) NOT NULL,\n"
                        + "	CPU_SERIAL VARCHAR(20) NOT NULL,\n"
                        + "	HARDDISK_SERIAL VARCHAR(20) NOT NULL,\n"
                        + "	RAM_INFO VARCHAR(20) NOT NULL,\n"
                        + "	IS_FORM_FILL_UP INT NOT NULL,\n"
                        + "	PRIMARY KEY (ID)\n"
                        + ");";
                stmt.executeUpdate(sql);


                if (con == null) {
                    System.err.println("Failed to connect with database.");
                }
            } catch (SQLException | ClassNotFoundException ex) {
                Logger.getLogger(DBConnection.class.getName()).log(Level.SEVERE, null, ex);
            }
        } else {
            JOptionPane.showMessageDialog(null, "No internet connection!");

        }
        System.out.println("Connection Successful"+con.getClass());
        return con;
    }
}
