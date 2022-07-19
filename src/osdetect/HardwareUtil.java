package osdetect;

import com.sun.javafx.PlatformUtil;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Inet4Address;
import java.net.InetAddress;
import java.net.InterfaceAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.List;
import java.util.StringJoiner;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import oshi.SystemInfo;

/**
 *
 * @author WALTON
 */
public class HardwareUtil {

    //private static final Logger mLogger = Logger.getLogger(HardwareUtil.class.getName());
    private static final Logger logger = LoggerFactory.getLogger(HardwareUtil.class);
    private static final String OS = System.getProperty("os.name").toLowerCase();

    //==================== CPU Serial =======================
    public static String getCPUSerial() {
        String result = "";

        if (PlatformUtil.isWindows()) {
            try {
                File file = File.createTempFile("tmp", ".vbs");
                file.deleteOnExit();
                try (FileWriter fw = new java.io.FileWriter(file)) {
                    String vbs = "On Error Resume Next \r\n\r\n" + "strComputer = \".\"  \r\n"
                            + "Set objWMIService = GetObject(\"winmgmts:\" _ \r\n"
                            + "    & \"{impersonationLevel=impersonate}!\\\\\" & strComputer & \"\\root\\cimv2\") \r\n"
                            + "Set colItems = objWMIService.ExecQuery(\"Select * from Win32_Processor\")  \r\n "
                            + "For Each objItem in colItems\r\n " + "    Wscript.Echo objItem.ProcessorId  \r\n "
                            + "    exit for  ' do the first cpu only! \r\n" + "Next                    ";

                    fw.write(vbs);
                }
                Process p = Runtime.getRuntime().exec("cscript //NoLogo " + file.getPath());
                try (BufferedReader input = new BufferedReader(new InputStreamReader(p.getInputStream()))) {
                    String line;
                    while ((line = input.readLine()) != null) {
                        result += line;
                    }
                }
                file.delete();
            } catch (IOException e) {
                logger.error(null, e);
            }
        } else if (PlatformUtil.isUnix()) {
            String CPU_ID_CMD = "dmidecode -t 4 | grep ID |sort -u |awk -F': ' '{print $2}'";
            Process p;
            try {
                p = Runtime.getRuntime().exec(new String[]{"sh", "-c", CPU_ID_CMD});//The Conduit
                try (BufferedReader br = new BufferedReader(new InputStreamReader(p.getInputStream()))) {
                    String line;
                    while ((line = br.readLine()) != null) {
                        result += line;
                        break;
                    }
                }
            } catch (IOException e) {
                logger.error(null, e);
            }
        } else if (PlatformUtil.isLinux()) {

            String CPU_ID_CMD = "dmidecode";
            BufferedReader bufferedReader;
            Process p;
            try {
                p = Runtime.getRuntime().exec(new String[]{"sh", "-c", CPU_ID_CMD});// pipe
                bufferedReader = new BufferedReader(new InputStreamReader(p.getInputStream()));
                String line;
                int index = -1;
                while ((line = bufferedReader.readLine()) != null) {
                    // Look for the tag string [hwaddr]
                    index = line.toLowerCase().indexOf("uuid");
                }
            } catch (IOException e) {
                logger.error(null, e);
            }
        }
        if (result.trim().length() < 1) {
            result = "No CPU_ID is read";
        }
        return result.trim();
    }

    //======================== Mac Address ==================
    public static String getMacAddress() {

        try {
            InetAddress ip;
            StringBuilder macAddress = new StringBuilder();

            ip = InetAddress.getLocalHost();
            NetworkInterface network = NetworkInterface.getByInetAddress(ip);

            if (network.isUp() && !network.isLoopback()) {
                List<InterfaceAddress> adrs = network.getInterfaceAddresses();
                for (InterfaceAddress adr : adrs) {
                    InetAddress inadr = adr.getAddress();
                    if (inadr instanceof Inet4Address) {
                        byte[] mac = network.getHardwareAddress();
                        for (int i = 0; i < mac.length; i++) {
                            macAddress.append(String.format("%02X%s", mac[i], (i < mac.length - 1) ? "-" : ""));
                        }
                        return macAddress.toString();
                    }
                }
            }
        } catch (SocketException | UnknownHostException ex) {
            logger.error(null, ex);
        }
        return null;
    }

    //======================== IP Address ==================
    public static String getIpAddress() {
        try {
            InetAddress ip;
            ip = InetAddress.getLocalHost();
            return ip.getHostAddress();
        } catch (UnknownHostException ex) {
            logger.error(null, ex);
        }
        return null;
    }

    //============ Harddisk Serial =============
    public static String getHarddisSerialNo(String drive) {
        StringBuilder hardDiskSerial = new StringBuilder();

        if (PlatformUtil.isWindows()) {
            try {
                File file = File.createTempFile("realhowto", ".vbs");
                file.deleteOnExit();
                try (FileWriter fw = new java.io.FileWriter(file)) {
                    String vbs = "Set objFSO = CreateObject(\"Scripting.FileSystemObject\")\n"
                            + "Set colDrives = objFSO.Drives\n"
                            + "Set objDrive = colDrives.item(\"" + drive + "\")\n"
                            + "Wscript.Echo objDrive.SerialNumber";  // see note
                    fw.write(vbs);
                } // see note
                Process p = Runtime.getRuntime().exec("cscript //NoLogo " + file.getPath());
                try (BufferedReader input = new BufferedReader(new InputStreamReader(p.getInputStream()))) {
                    String line;
                    while ((line = input.readLine()) != null) {
                        hardDiskSerial.append(line);
                    }
                }
            } catch (IOException e) {
                logger.error(null, e);
            }
        } else if (PlatformUtil.isLinux()) {
            //hdparm -i /dev/hda  
        }
        return hardDiskSerial.toString().trim();
    }

    //========================= Ram Info ===============================   
    public static String getRamInfo() {
        BufferedReader reader;
        List<String> RAMList = new ArrayList<>();
        String command = "wmic memorychip get serialnumber";
        String ubuntu = "sudo dmidcode -t 17";

        if (PlatformUtil.isWindows()) {
            try {
                Process process = Runtime.getRuntime().exec(command);
                reader = new BufferedReader(new InputStreamReader(process.getInputStream()));
                String line;
                while ((line = reader.readLine()) != null) {
                    if (!line.trim().equalsIgnoreCase("SerialNumber") && !line.trim().isEmpty()) {
                        RAMList.add(line.trim());
                    }
                }
                reader.close();
            } catch (IOException e) {
                logger.error(null, e);
            }
        } else if (PlatformUtil.isLinux()) {
            try {
                Process process = Runtime.getRuntime().exec(ubuntu);
                reader = new BufferedReader(new InputStreamReader(process.getInputStream()));
                String line;
                while ((line = reader.readLine()) != null) {
                    if (!line.trim().equalsIgnoreCase("SerialNumber") && !line.trim().isEmpty()) {
                        RAMList.add(line.trim());
                    }
                }
                reader.close();
            } catch (IOException e) {
                logger.error(null, e);
            }
        }

        StringJoiner joiner = new StringJoiner(",");
        RAMList.forEach((value) -> {
            joiner.add(value);
        });
        return joiner.toString();
    }

    //========================= System Info ===============================
    public static String getSystemManufacture() {
        return new SystemInfo().getHardware().getComputerSystem().getManufacturer();
    }

    public static String getSystemModel() {
        return new SystemInfo().getHardware().getComputerSystem().getModel();
    }

    //========================= BaseBoard Info ===============================
    public static String getBaseBoardManufacture() {
        return new SystemInfo().getHardware().getComputerSystem().getBaseboard().getManufacturer();
    }
}
