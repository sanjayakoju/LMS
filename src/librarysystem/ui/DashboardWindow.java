package librarysystem.ui;

import business.ControllerInterface;
import business.SystemController;
import librarysystem.LibWindow;
import librarysystem.ui.util.ImagePanel;
import librarysystem.ui.util.Util;

import javax.swing.*;
import java.awt.*;

public class DashboardWindow extends JFrame implements LibWindow {

    public static DashboardWindow INSTANCE = new DashboardWindow();
    private final ControllerInterface systemController;
    private boolean innitialize = false;
    private JButton totalBookBtn;
    private JButton totalMemberBtn;

    private DashboardWindow() {
        systemController = new SystemController();
    }

    @Override
    public void init() {
        setBounds(100, 100, 600, 550);
        JPanel contentPane = new ImagePanel("dashboard.jpg");
        contentPane.setLayout(null);
        add(contentPane);
        setTitle("Dashboard");

        JButton titleLbl = Util.dashboardButton("lms.png", "", 500, 150);
        titleLbl.setBounds(30, 0, 540, 200);
        titleLbl.setHorizontalAlignment(SwingConstants.CENTER);
        titleLbl.setFont(new Font("Arial", Font.BOLD, 35));
        titleLbl.setForeground(Color.BLUE);
        titleLbl.setBackground(new Color(0, 0, 0, 100));
        contentPane.add(titleLbl);

        totalBookBtn = Util.dashboardButton("books.png", "Total Books: 0", 100, 100);
        totalBookBtn.setBounds(50, 300, 350, 100);
        contentPane.add(totalBookBtn);

        totalMemberBtn = Util.dashboardButton("users.png", "Total Members: 0", 100, 100);
        totalMemberBtn.setBounds(50, 400, 350, 100);
        contentPane.add(totalMemberBtn);

        setResizable(false);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        Util.centerFrameOnDesktop(INSTANCE);
        isInitialized(true);
    }


    @Override
    public boolean isInitialized() {
        return innitialize;
    }

    @Override
    public void isInitialized(boolean val) {
        innitialize = val;
    }


    public void updateDashboard() {
        totalBookBtn.setText("Total Books: " + systemController.allBookIds().size());
        totalMemberBtn.setText("Total Members: " + systemController.allMemberIds().size());

    }
}
