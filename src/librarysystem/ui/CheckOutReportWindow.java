package librarysystem.ui;

import business.ControllerInterface;
import business.LibrarySystemException;
import business.SystemController;
import librarysystem.LibWindow;
import librarysystem.ui.util.Util;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

public class CheckOutReportWindow extends JFrame implements LibWindow {
    public static final CheckOutReportWindow INSTANCE = new CheckOutReportWindow();
    private final ControllerInterface systemController;
    private final DefaultTableModel tableModel = new DefaultTableModel();
    private boolean initialize = false;
    private JPanel contentPane;
    private JTable table;
    private JTextField memberIdField;

    private CheckOutReportWindow() {
        systemController = new SystemController();
    }

    @Override
    public void init() {
        setBounds(100, 100, 600, 550);
        contentPane = new JPanel();
        contentPane.setLayout(null);
        add(contentPane);
        setTitle("Dashboard");

        String[] headers = new String[]{"Member Id", "Member Name", "ISBN", "Book Name", "Copy Number", "Checkout Date", "Due Date"};
        tableModel.setColumnIdentifiers(headers);

        table = new JTable();
        table.setModel(tableModel);
        table.setBounds(5, 88, 576, 415);
        contentPane.add(table.getTableHeader());
        JScrollPane scrollPane = new JScrollPane(table, JScrollPane.VERTICAL_SCROLLBAR_ALWAYS, JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
        scrollPane.setBounds(5, 88, 576, 415);
        contentPane.add(scrollPane);

        JLabel lblNewLabel = new JLabel("Member Id : ");
        lblNewLabel.setHorizontalAlignment(SwingConstants.TRAILING);
        lblNewLabel.setBounds(10, 39, 76, 13);
        contentPane.add(lblNewLabel);

        memberIdField = new JTextField();
        memberIdField.setBounds(96, 36, 340, 19);
        contentPane.add(memberIdField);
        memberIdField.setColumns(10);

        JButton searchBtn = new JButton("Search");
        searchBtn.setBounds(446, 35, 130, 21);
        searchBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                updateData();
            }
        });
        contentPane.add(searchBtn);

        setResizable(false);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        Util.centerFrameOnDesktop(INSTANCE);
        isInitialized(true);
    }

    public void clearData() {
        tableModel.setRowCount(0);
        memberIdField.setText("");
    }

    private void updateData() {
        tableModel.setRowCount(0);
        String memberId = memberIdField.getText();
        try {
            if (memberId == null) {
                throw new LibrarySystemException("Member Id should not be null!");
            }
            if (memberId.trim().length() == 0) {
                throw new LibrarySystemException("Member Id should not be empty!");
            }
            List<List<String>> records = systemController.getMemberCheckoutEntries(memberId.trim());
            records.forEach(record -> tableModel.addRow(record.toArray()));
            table.repaint();
        } catch (LibrarySystemException e) {
            Util.errorMessage(e.getMessage());
        }
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
