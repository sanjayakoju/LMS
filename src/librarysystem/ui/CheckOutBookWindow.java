package librarysystem.ui;

import business.*;
import librarysystem.LibWindow;
import librarysystem.ui.util.Util;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.time.LocalDate;

public class CheckOutBookWindow extends JFrame implements LibWindow {
    public static final CheckOutBookWindow INSTANCE = new CheckOutBookWindow();
    private final ControllerInterface systemController;
    private boolean initialize = false;
    private JPanel contentPane;
    private JTextField memberIdField;
    private JTextField isbnField;

    private CheckOutBookWindow() {
        systemController = new SystemController();
    }

    @Override
    public void init() {
        setBounds(100, 100, 600, 550);
        setTitle("Check Out Book");
        contentPane = (JPanel) getContentPane();
        contentPane.setLayout(null);

        JLabel titleLbl = new JLabel("Checkout Book");
        Util.adjustLabelFont(titleLbl, Util.DARK_BLUE, true);
        titleLbl.setBounds(237, 72, 115, 14);
        contentPane.add(titleLbl);

        memberIdField = new JTextField();
        memberIdField.setBounds(237, 117, 208, 20);
        contentPane.add(memberIdField);
        memberIdField.setColumns(10);

        isbnField = new JTextField();
        isbnField.setBounds(237, 147, 208, 20);
        contentPane.add(isbnField);
        isbnField.setColumns(10);

        JLabel memberIdLbl = new JLabel("Member ID : ");
        memberIdLbl.setHorizontalAlignment(SwingConstants.TRAILING);
        Util.adjustLabelFont(memberIdLbl, Util.LABEL_COLOR, false);
        memberIdLbl.setBounds(138, 119, 89, 14);
        contentPane.add(memberIdLbl);

        JLabel isbnLbl = new JLabel("ISBN : ");
        isbnLbl.setHorizontalAlignment(SwingConstants.TRAILING);
        Util.adjustLabelFont(isbnLbl, Util.LABEL_COLOR, false);
        isbnLbl.setBounds(138, 149, 89, 14);
        contentPane.add(isbnLbl);

        JButton checkOutBtn = new JButton("Checkout");
        Util.adjustButtonFont(checkOutBtn, Util.LABEL_COLOR, true);
        checkOutBtn.setBounds(235, 201, 140, 23);
        checkOutBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String isbn = isbnField.getText();
                String memberId = memberIdField.getText();
                if (isbn == null || memberId == null) {
                    Util.errorMessage("ISBN and Member ID may not be empty");
                } else if (isbn.length() == 0 || memberId.length() == 0) {
                    Util.errorMessage("ISBN and Member ID may not be empty");
                } else {
                    try {
                        Book book = systemController.getBookById(isbn);
                        LibraryMember member = systemController.searchMember(memberId);
                        if (book == null) {
                            Util.errorMessage("Book with ISBN '" + isbn + "' not found.");
                        } else if (member == null) {
                            throw new LibrarySystemException("Library member with ID '" + memberId + "' not found.");
                        } else {
                            StringBuilder sb = new StringBuilder();
                            sb.append("ISBN: " + book.getIsbn()).append("\n");
                            sb.append("Book Name: " + book.getTitle()).append("\n");
                            sb.append("Return Date: " + LocalDate.now().plusDays(book.getMaxCheckoutLength())).append("\n\n");
                            sb.append("Authors: ").append("\n");

                            for (Author author : book.getAuthors()) {
                                sb.append("\t").append("Name: " + author.getFirstName() + " " + author.getLastName()).append("\n");
                            }
                            int result = JOptionPane.showConfirmDialog(INSTANCE, sb.toString(), "Check Out Details",
                                    JOptionPane.YES_NO_OPTION,
                                    JOptionPane.PLAIN_MESSAGE);
                            if (result == JOptionPane.YES_OPTION) {
                                systemController.checkoutBook(member, book);
                                Util.successMessage("Checkout Successful");
                            }
                        }
                    } catch (LibrarySystemException ex) {
                        Util.errorMessage(ex.getMessage());
                    } finally {
                        clear();
                    }
                }
            }
        });
        contentPane.add(checkOutBtn);


        setResizable(false);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        Util.centerFrameOnDesktop(INSTANCE);
        isInitialized(true);
    }

    private void clear() {
        isbnField.setText("");
        memberIdField.setText("");
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
