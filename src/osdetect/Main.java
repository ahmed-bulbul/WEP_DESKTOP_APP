package osdetect;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 *
 * @author WALTON
 */
public class Main {

    private static final Logger logger = LoggerFactory.getLogger(Main.class);

    public static void main(String[] args) throws SQLException {

        //check connection
        if (DBConnection.getConnection() == null) {
            logger.error("Connection failed");
            System.exit(1);
        }else{
            logger.info("Connection success");
        }


        String cpuSerial = HardwareUtil.getCPUSerial();
        String macAddress = HardwareUtil.getMacAddress();
        String harddiskserial = HardwareUtil.getHarddisSerialNo("C");
        String ramInfo = HardwareUtil.getRamInfo();

        Toolbar.myToolBar();

        if (Utility.isMacExists(macAddress)) {
            logger.info("HW Info Already Exists");
        } else {
            try {
                PreparedStatement psmt = DBConnection.getConnection().prepareStatement("insert into HW_INFO values(null,?,?,?,?,?)");
                psmt.setString(1, macAddress);
                psmt.setString(2, cpuSerial);
                psmt.setString(3, harddiskserial);
                psmt.setString(4, ramInfo);
                psmt.setInt(5, 1);
                psmt.executeUpdate();

                logger.info("HW Info Inserted Succesfully");
            } catch (SQLException ex) {
                logger.error("SQLException", ex);
            }
        }
    }
}
