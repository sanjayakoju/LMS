package librarysystem.ui.menu;

import business.LoginException;
import business.SystemController;
import dataaccess.Auth;
import librarysystem.ui.*;
import librarysystem.ui.util.Util;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class MenuBarPanel extends JMenuBar {

    public static MenuBarPanel INSTANCE = new MenuBarPanel();

    private JMenu homeMenu;
    private JMenu bookMenu;
    private JMenu checkOutRecordMenu;
    private JMenu memberMenu;
    private JMenuItem dashboardMenuItem;

    private JMenuItem allBookIdMenuItem;

    private JMenuItem allMemberIdMenuItem;
    private JMenuItem logoutMenuItem;
    private JMenuItem exitMenuItem;
    private JMenuItem addMemberMenuItem;
    private JMenuItem addBookMenuItem;
    private JMenuItem addBookCopyMenuItem;
    private JMenuItem checkOutBookMenuItem;
    private JMenuItem viewCheckoutRecordMenuItem;

    private MenuBarPanel() {
    }

    public void init(JFrame frame, Auth currentAuth) {
        removeAll();
        setBounds(100, 100, 836, 582);
        homeMenu = new JMenu("Home");
        add(homeMenu);

        dashboardMenuItem = new JMenuItem("Dashboard");
        dashboardMenuItem.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                try {
                    if (!DashboardWindow.INSTANCE.isInitialized()) {
                        DashboardWindow.INSTANCE.init();
                    }
                    if (SystemController.currentAuth == Auth.BOTH || SystemController.currentAuth == Auth.LIBRARIAN || SystemController.currentAuth == Auth.ADMIN) {
                        MenuBarPanel.INSTANCE.init(DashboardWindow.INSTANCE, SystemController.currentAuth);
                    } else {
                        throw new LoginException("Invalid Authorization");
                    }
                    DashboardWindow.INSTANCE.updateDashboard();
                    frame.setVisible(false);
                    frame.dispose();
                    DashboardWindow.INSTANCE.setVisible(true);
                } catch (LoginException err) {
                    Util.errorMessage("Error! " + err.getMessage());
                    logOut(frame);
                }
            }
        });
        dashboardMenuItem.setBackground(Util.BACKGROUND_COLOR);
        homeMenu.add(dashboardMenuItem);

        logoutMenuItem = new JMenuItem("Logout");
        logoutMenuItem.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                logOut(frame);
            }
        });
        homeMenu.add(logoutMenuItem);

        exitMenuItem = new JMenuItem("Exit");
        exitMenuItem.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.exit(0);
            }
        });
        homeMenu.add(exitMenuItem);

        if (currentAuth == Auth.ADMIN || currentAuth == Auth.BOTH) {
            memberMenu = new JMenu("Member");
            add(memberMenu);

            allMemberIdMenuItem = new JMenuItem("Member Id's");
            allMemberIdMenuItem.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent e) {
                    if (SystemController.currentAuth == Auth.BOTH || SystemController.currentAuth == Auth.ADMIN) {
                        if (!AllMemberIdsWindow.INSTANCE.isInitialized()) {
                            AllMemberIdsWindow.INSTANCE.init();
                        } else {
                            AllMemberIdsWindow.INSTANCE.updateData();
                        }
                        frame.setVisible(false);
                        frame.dispose();
                        MenuBarPanel.INSTANCE.init(AllMemberIdsWindow.INSTANCE, SystemController.currentAuth);
                        AllMemberIdsWindow.INSTANCE.setVisible(true);
                    } else {
                        Util.errorMessage("Invalid Authorization");
                    }
                }
            });
            memberMenu.add(allMemberIdMenuItem);

            addMemberMenuItem = new JMenuItem("Add Member");
            addMemberMenuItem.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent e) {
                    if (SystemController.currentAuth == Auth.BOTH || SystemController.currentAuth == Auth.ADMIN) {
                        if (!AddMemberWindow.INSTANCE.isInitialized()) {
                            AddMemberWindow.INSTANCE.init();
                        }
                        frame.setVisible(false);
                        frame.dispose();
                        MenuBarPanel.INSTANCE.init(AddMemberWindow.INSTANCE, SystemController.currentAuth);
                        AddMemberWindow.INSTANCE.setVisible(true);
                    } else {
                        Util.errorMessage("Invalid Authorization");
                    }
                }
            });
            memberMenu.add(addMemberMenuItem);
        }

        bookMenu = new JMenu("Book");
        add(bookMenu);
        if (currentAuth == Auth.LIBRARIAN || currentAuth == Auth.BOTH || SystemController.currentAuth == Auth.ADMIN) {
            allBookIdMenuItem = new JMenuItem("Book Id's");
            allBookIdMenuItem.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent e) {

                    if (SystemController.currentAuth == Auth.BOTH || SystemController.currentAuth == Auth.LIBRARIAN || SystemController.currentAuth == Auth.ADMIN) {
                        if (!AllBookIdsWindow.INSTANCE.isInitialized()) {
                            AllBookIdsWindow.INSTANCE.init();
                        } else {
                            AllBookIdsWindow.INSTANCE.updateData();
                        }
                        frame.setVisible(false);
                        frame.dispose();
                        MenuBarPanel.INSTANCE.init(AllBookIdsWindow.INSTANCE, SystemController.currentAuth);
                        AllBookIdsWindow.INSTANCE.setVisible(true);
                    } else {
                        Util.errorMessage("Invalid Authorization");
                    }
                }
            });
            bookMenu.add(allBookIdMenuItem);
        }
        if (currentAuth == Auth.ADMIN || currentAuth == Auth.BOTH) {
            addBookMenuItem = new JMenuItem("Add Book");
            addBookMenuItem.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent e) {
                    if (SystemController.currentAuth == Auth.BOTH || SystemController.currentAuth == Auth.ADMIN) {
                        if (!AddBookWindow.INSTANCE.isInitialized()) {
                            AddBookWindow.INSTANCE.init();
                        }
                        frame.setVisible(false);
                        frame.dispose();
                        MenuBarPanel.INSTANCE.init(AddBookWindow.INSTANCE, SystemController.currentAuth);
                        AddBookWindow.INSTANCE.clear();
                        AddBookWindow.INSTANCE.setVisible(true);
                    } else {
                        Util.errorMessage("Invalid Authorization");
                    }
                }
            });
            bookMenu.add(addBookMenuItem);

            addBookCopyMenuItem = new JMenuItem("Add Book Copy");
            addBookCopyMenuItem.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    if (SystemController.currentAuth == Auth.BOTH || SystemController.currentAuth == Auth.ADMIN) {
                        if (!AddBookCopyWindow.INSTANCE.isInitialized()) {
                            AddBookCopyWindow.INSTANCE.init();
                        }
                        frame.setVisible(false);
                        frame.dispose();
                        MenuBarPanel.INSTANCE.init(AddBookCopyWindow.INSTANCE, SystemController.currentAuth);
                        AddBookCopyWindow.INSTANCE.setVisible(true);
                    } else {
                        Util.errorMessage("Invalid Authorization");
                    }
                }
            });
            bookMenu.add(addBookCopyMenuItem);
        }

        if (currentAuth == Auth.BOTH || currentAuth == Auth.LIBRARIAN) {
            checkOutRecordMenu = new JMenu("Checkout Record");
            add(checkOutRecordMenu);

            checkOutBookMenuItem = new JMenuItem("Checkout Book");
            checkOutBookMenuItem.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent e) {
                    if (SystemController.currentAuth == Auth.BOTH || SystemController.currentAuth == Auth.LIBRARIAN) {
                        if (!CheckOutBookWindow.INSTANCE.isInitialized()) {
                            CheckOutBookWindow.INSTANCE.init();
                        }
                        frame.setVisible(false);
                        frame.dispose();
                        MenuBarPanel.INSTANCE.init(CheckOutBookWindow.INSTANCE, SystemController.currentAuth);
                        CheckOutBookWindow.INSTANCE.setVisible(true);
                    } else {
                        Util.errorMessage("Invalid Authorization");
                    }
                }
            });
            checkOutRecordMenu.add(checkOutBookMenuItem);
            viewCheckoutRecordMenuItem = new JMenuItem("View Checkout Record");
            viewCheckoutRecordMenuItem.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    if (SystemController.currentAuth == Auth.BOTH || SystemController.currentAuth == Auth.LIBRARIAN) {
                        if (!CheckOutReportWindow.INSTANCE.isInitialized()) {
                            CheckOutReportWindow.INSTANCE.init();
                        }
                        CheckOutReportWindow.INSTANCE.clearData();
                        frame.setVisible(false);
                        frame.dispose();
                        MenuBarPanel.INSTANCE.init(CheckOutReportWindow.INSTANCE, SystemController.currentAuth);
                        CheckOutReportWindow.INSTANCE.setVisible(true);
                    } else {
                        Util.errorMessage("Invalid Authorization");
                    }
                }
            });
            checkOutRecordMenu.add(viewCheckoutRecordMenuItem);
        }
        frame.setJMenuBar(INSTANCE);
    }

    private void logOut(JFrame frame) {
        SystemController.currentAuth = null;
        frame.setVisible(false);
        frame.dispose();
        LoginWindow.INSTANCE.init();
        LoginWindow.INSTANCE.setVisible(true);
    }
}
