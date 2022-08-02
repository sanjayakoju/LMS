package librarysystem.ui;

import business.LoginException;
import business.SystemController;
import dataaccess.Auth;
import librarysystem.LibWindow;
import librarysystem.ui.menu.MenuBarPanel;
import librarysystem.ui.util.ImagePanel;
import librarysystem.ui.util.Util;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class LoginWindow extends JFrame implements LibWindow {
    public static LoginWindow INSTANCE = new LoginWindow();
    private final SystemController systemController = new SystemController();
    private JPanel contentPane;
    private JTextField usernameField;
    private JPasswordField passwordField;
    private boolean initialize = false;

    private LoginWindow() {
    }

    @Override
    public void init() {
        setBounds(100, 100, 450, 300);

        contentPane = new ImagePanel("libraryimg.jpg");
        setContentPane(contentPane);
        contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
        contentPane.setLayout(null);
        usernameField = new JTextField();
        usernameField.setBounds(90, 53, 254, 20);
        contentPane.add(usernameField);
        usernameField.setColumns(10);

        JLabel usernameLbl = new JLabel("Username");
        Util.adjustLabelFont(usernameLbl, Color.WHITE, true);
        usernameLbl.setLabelFor(usernameField);
        usernameLbl.setBounds(91, 30, 253, 20);
        contentPane.add(usernameLbl);

        JLabel passwordLbl = new JLabel("Password");
        Util.adjustLabelFont(passwordLbl, Color.WHITE, true);
        passwordLbl.setBounds(91, 93, 254, 20);
        contentPane.add(passwordLbl);

        passwordField = new JPasswordField();
        passwordLbl.setLabelFor(passwordField);
        passwordField.setBounds(90, 116, 254, 20);
        contentPane.add(passwordField);

        JButton loginButton = new JButton("Login");
        loginButton.setBounds(162, 164, 124, 30);
        loginButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String userId = usernameField.getText();
                String password = String.valueOf(passwordField.getPassword());
                if (userId.length() == 0 || password.length() == 0) {
                    Util.errorMessage("Id and Password fields must be nonempty");
                } else {
                    try {
                        systemController.login(userId, password);
                        Util.successMessage("Successfully Login as " + SystemController.currentAuth);
                        if (!DashboardWindow.INSTANCE.isInitialized()) {
                            DashboardWindow.INSTANCE.init();
                        }
                        if (SystemController.currentAuth == Auth.BOTH || SystemController.currentAuth == Auth.LIBRARIAN || SystemController.currentAuth == Auth.ADMIN) {
                            MenuBarPanel.INSTANCE.init(DashboardWindow.INSTANCE, SystemController.currentAuth);
                        } else {
                            throw new LoginException("Invalid Authorization");
                        }
                        DashboardWindow.INSTANCE.updateDashboard();
                        setVisible(false);
                        dispose();
                        DashboardWindow.INSTANCE.setVisible(true);
                    } catch (LoginException err) {
                        Util.errorMessage("Error! " + err.getMessage());
                    }
                }
            }
        });
        contentPane.add(loginButton);
        Util.adjustButtonFont(loginButton, Util.LABEL_COLOR, true);

        setTitle("Library Management System");
        setResizable(false);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        Util.centerFrameOnDesktop(INSTANCE);
        isInitialized(true);
    }

    @Override
    public boolean isInitialized() {
        return initialize;
    }

    @Override
    public void isInitialized(boolean val) {
        initialize = val;
    }
}
