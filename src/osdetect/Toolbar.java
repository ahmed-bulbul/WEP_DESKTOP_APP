package osdetect;

import java.awt.AWTException;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.FontFormatException;
import java.awt.Graphics;
import java.awt.GraphicsDevice;
import java.awt.GraphicsEnvironment;
import java.awt.Insets;
import java.awt.LayoutManager;
import java.awt.MenuItem;
import java.awt.Point;
import java.awt.PopupMenu;
import java.awt.Rectangle;
import java.awt.SystemTray;
import java.awt.TrayIcon;
import java.awt.Window.Type;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.logging.Level;
import javax.imageio.ImageIO;
import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JFrame;
import javax.swing.border.LineBorder;
import javax.swing.border.MatteBorder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 *
 * @author WALTON
 */
public class Toolbar {

    private static final Logger logger = LoggerFactory.getLogger(Toolbar.class);

    private static JFrame window;
    private static JPanel topPanel;
    private static TrayIcon ti;
    private static Point compCoords;

    public static void myToolBar() {

        SystemTray tray = SystemTray.getSystemTray();
        window = new JFrame("Dragabble");
        //window.setType(javax.swing.JFrame.Type.UTILITY);
        //window.setType(Type.UTILITY);
        window.setAlwaysOnTop(true);
        window.setLocationByPlatform(true);
        window.setType(Type.UTILITY);
        window.getRootPane().setBorder(BorderFactory.createMatteBorder(2, 2, 2, 2, Color.decode("#024FA1")));

        topPanel = new JPanel((LayoutManager) new FlowLayout(FlowLayout.RIGHT));

        Font mFont = null;
        try {
            Font ttfBase = Font.createFont(Font.TRUETYPE_FONT, new File("fonts/montserrat-bold.ttf"));
            mFont = ttfBase.deriveFont(Font.PLAIN, 12);
        } catch (IOException | FontFormatException ex) {
            logger.error("Font Exception", ex);
        }

        final JButton appDetails = new JButton("WEP Project of Walton BD");
        appDetails.setFont(mFont);
        appDetails.setContentAreaFilled(false);
        //appDetails.setBorderPainted(false);
        //appDetails.setOpaque(false);
        appDetails.setFocusPainted(false);
        appDetails.setForeground(Color.WHITE);
        //appDetails.setBackground(Color.decode("#EB1C22"));
        appDetails.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(255, 255, 255, 220), 1),
                BorderFactory.createLineBorder(Color.decode("#024FA1"), 4)));

        final JButton regBtn = new JButton("Registration");
        regBtn.setFont(mFont);
        regBtn.setFocusPainted(false);
        regBtn.setContentAreaFilled(false);
        regBtn.setForeground(Color.WHITE);
        //regBtn.setBackground(Color.decode("#EB1C22"));
        regBtn.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(255, 255, 255, 220), 1),
                BorderFactory.createLineBorder(Color.decode("#024FA1"), 4)));

        final JButton minimize = new JButton("  -  ");
        minimize.setFont(mFont);
        minimize.setContentAreaFilled(false);
        minimize.setForeground(Color.WHITE);
        //minimize.setBackground(Color.decode("#EB1C22"));
        minimize.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(255, 255, 255, 220), 1),
                BorderFactory.createLineBorder(Color.decode("#024FA1"), 4)));

        final JButton exit = new JButton("  X  ");
        exit.setFont(mFont);
        exit.setContentAreaFilled(false);
        exit.setForeground(Color.WHITE);
        //exit.setBackground(Color.decode("#EB1C22"));
        exit.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(255, 255, 255, 220), 1),
                BorderFactory.createLineBorder(Color.decode("#024FA1"), 4)));

        regBtn.addActionListener((ActionEvent ae) -> {
            new Registration().setVisible(true);
        });

        exit.addActionListener((ActionEvent e) -> {
            System.exit(0);
        });

        minimize.addActionListener((ActionEvent e) -> {
            window.setState(JFrame.ICONIFIED);
            try {

                boolean trayExists = SystemTray.getSystemTray().getTrayIcons().length > 0;
                if (trayExists == false) {
                    TrayCreate();
                    // System.exit(0);
                }
            } catch (AWTException ex) {
                logger.error(null, ex);
            }
        });

        compCoords = null;
        topPanel.addMouseListener(new MouseListener() {

            @Override
            public void mouseReleased(MouseEvent e) {
                compCoords = null;
            }

            @Override
            public void mousePressed(MouseEvent e) {
                compCoords = e.getPoint();
            }

            @Override
            public void mouseExited(MouseEvent e) {
            }

            @Override
            public void mouseEntered(MouseEvent e) {
            }

            @Override
            public void mouseClicked(MouseEvent e) {
            }
        });

        topPanel.addMouseMotionListener(new MouseMotionListener() {
            @Override
            public void mouseMoved(MouseEvent e) {
            }

            @Override
            public void mouseDragged(MouseEvent e) {

                Point currCoords = e.getLocationOnScreen();
                window.setLocation(currCoords.x - compCoords.x, currCoords.y - compCoords.y);

            }
        });

        /*try {
            BufferedImage img = ImageIO.read(new File("images/ic.png"));
            ImageIcon icon = new ImageIcon(img);
            JLabel jl = new JLabel(icon);
            topPanel.add(jl);
        } catch (IOException ex) {
        }*/
        // Set toolbar Icon
        ImageIcon img = new ImageIcon("images/ic_toolbar.png");
        JLabel icon = new JLabel(img);

        topPanel.add(icon);
        topPanel.add(appDetails);
        topPanel.add(regBtn);
        topPanel.add(minimize);
        topPanel.add(exit);
        topPanel.setBackground(Color.decode("#024FA1"));

        window.getContentPane().setBackground(Color.decode("#024FA1"));
        window.add(BorderLayout.PAGE_START, topPanel);
        window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        //window.setSize(500, 40);
        window.setLocationRelativeTo(null);
        window.setUndecorated(true);
        //window.setVisible(true);
        window.add(topPanel);
        window.pack();

        //window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
        GraphicsDevice defaultScreen = ge.getDefaultScreenDevice();
        Rectangle rect = defaultScreen.getDefaultConfiguration().getBounds();
        int x = (int) rect.getMaxX() - window.getWidth() - 250;
        int y = 0;
        window.setLocation(x, y);
        window.setVisible(true);

    }

    public static void TrayCreate() throws AWTException {

        if (!SystemTray.isSupported()) {
            return;
        }

        SystemTray tray = SystemTray.getSystemTray();
        Dimension size = tray.getTrayIconSize();

        // Set try icon
        BufferedImage bi = null;
        try {
            BufferedImage bufImg = ImageIO.read(new File("images/ic_system_try.png"));
            bi = new BufferedImage(bufImg.getWidth(), bufImg.getHeight(), BufferedImage.TRANSLUCENT);
            bi.getGraphics().drawImage(bufImg, 0, 0, null);
        } catch (IOException ex) {
            logger.error("Try Icon", ex);
        }

        //BufferedImage bi = new BufferedImage(size.width, size.height, BufferedImage.TYPE_INT_RGB);
        //Graphics g = bi.getGraphics();
        //g.setColor(Color.blue);
        //g.fillRect(0, 0, size.width, size.height);
        PopupMenu popup = new PopupMenu();
        ActionListener al;
        MenuItem miExit = new MenuItem("Exit");

        MenuItem mTool = new MenuItem("Set as a Topbar");
        ActionListener tl;
        tl = (ActionEvent e) -> {
            /*if (!tray.equals(null)) {
                window.setVisible(true);

            } else {
                System.exit(0);
            }*/
            window.dispose();
            Toolbar.myToolBar();

            //popup.add(miExit);
        };
        mTool.addActionListener(tl);
        popup.add(mTool);

        ti = new TrayIcon(bi, "Walton WEP", popup);

        al = (ActionEvent e) -> {
            System.out.println(e.getActionCommand());
        };

        ti.setActionCommand("My Icon");
        ti.addActionListener(al);

        al = (ActionEvent e) -> {
            System.exit(0);
        };
        miExit.addActionListener(al);
        popup.add(miExit);

        MouseListener ml = new MouseListener() {
            @Override
            public void mouseClicked(MouseEvent e) {
                window.dispose();
                Toolbar.myToolBar();
            }

            @Override
            public void mouseEntered(MouseEvent e) {
                //System.out.println("Tray icon: Mouse entered");
            }

            @Override
            public void mouseExited(MouseEvent e) {
                //System.out.println("Tray icon: Mouse exited");
            }

            @Override
            public void mousePressed(MouseEvent e) {
                //System.out.println("Tray icon: Mouse pressed");
            }

            @Override
            public void mouseReleased(MouseEvent e) {
                //System.out.println("Tray icon: Mouse released");
            }
        };
        ti.addMouseListener(ml);
        MouseMotionListener mml = new MouseMotionListener() {
            @Override
            public void mouseDragged(MouseEvent e) {
//                System.out.println("Tray icon: Mouse dragged");
            }

            @Override
            public void mouseMoved(MouseEvent e) {
//                System.out.println("Tray icon: Mouse moved");
            }
        };

        ti.addMouseMotionListener(mml);
        tray.add(ti);
    }

    public static void main(String[] args) {
        Toolbar.myToolBar();
    }
}
