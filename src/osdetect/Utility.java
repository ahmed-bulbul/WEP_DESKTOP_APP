/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package osdetect;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author WALTON
 */
public class Utility {

    public static boolean isMacExists(String mac){
        try {
            PreparedStatement psmt = DBConnection.getConnection().prepareStatement("SELECT * from HW_INFO WHERE MAC_ADDRESS = ?");
            psmt.setString(1, mac);
            ResultSet rs = psmt.executeQuery();
            return rs.next();
        } catch (SQLException ex) {
            Logger.getLogger(Utility.class.getName()).log(Level.SEVERE, null, ex);
        }
        return false;
    }

    public static int isRegistered(String mac) {
        try {
            PreparedStatement psmt = DBConnection.getConnection().prepareStatement("SELECT IS_FORM_FILL_UP FROM HW_INFO WHERE MAC = ?");
            psmt.setString(1, mac);
            try (ResultSet rs = psmt.executeQuery()) {
                while (rs.next()) {
                    return rs.getInt("IS_FORM_FILL_UP");
                }
            }
        } catch (SQLException e) {
        }
        return 0;
    }

    public static int getPrimaryKey(String mac) {
        try {
            PreparedStatement psmt = DBConnection.getConnection().prepareStatement("SELECT ID FROM HW_INFO WHERE MAC = ?");
            psmt.setString(1, mac);
            try (ResultSet rs = psmt.executeQuery()) {
                while (rs.next()) {
                    return rs.getInt("ID");
                }
            }
        } catch (SQLException e) {
        }
        return 0;
    }
}
