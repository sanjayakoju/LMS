package librarysystem.ui;

import business.Book;
import business.ControllerInterface;
import business.LibrarySystemException;
import business.SystemController;
import librarysystem.LibWindow;
import librarysystem.ui.util.Util;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class AddBookCopyWindow extends JFrame implements LibWindow {

    public static AddBookCopyWindow INSTANCE = new AddBookCopyWindow();
    private final ControllerInterface systemController;
    private boolean initialize = false;
    private JTextField isbnField;
    private JTextField copyField;

    private AddBookCopyWindow() {
        systemController = new SystemController();
    }

    @Override
    public void init() {
        setBounds(100, 100, 600, 550);
        getContentPane().setLayout(null);
        setTitle("Add Book Copy");
        Container contentPane = getContentPane();

        JLabel titleLbl = new JLabel("Add Book Copy");
        titleLbl.setHorizontalAlignment(SwingConstants.CENTER);
        titleLbl.setForeground(Util.DARK_BLUE);
        Util.adjustLabelFont(titleLbl, Util.DARK_BLUE, true);
        titleLbl.setBounds(216, 53, 134, 29);
        contentPane.add(titleLbl);

        JLabel isbnLbl = new JLabel("Enter ISBN");
        isbnLbl.setBounds(132, 120, 91, 14);
        isbnLbl.setHorizontalAlignment(SwingConstants.TRAILING);
        Util.adjustLabelFont(isbnLbl, Util.LABEL_COLOR, false);
        contentPane.add(isbnLbl);

        isbnField = new JTextField();
        isbnField.setBounds(233, 118, 194, 20);
        contentPane.add(isbnField);
        isbnField.setColumns(10);

        JLabel copyLbl = new JLabel("No. Of Copy");
        copyLbl.setHorizontalAlignment(SwingConstants.TRAILING);
        Util.adjustLabelFont(copyLbl, Util.LABEL_COLOR, false);
        copyLbl.setBounds(132, 156, 91, 14);
        contentPane.add(copyLbl);

        copyField = new JTextField();
        copyField.setColumns(10);
        copyField.setBounds(233, 154, 194, 20);
        copyField.setText("1");
        contentPane.add(copyField);


        JButton addCopyBtn = new JButton("Add Book Copy");
        addCopyBtn.setFont(new Font("Tahoma", Font.BOLD, 11));
        addCopyBtn.setBounds(216, 214, 134, 23);
        addCopyBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String bookId = isbnField.getText();
                String copy = copyField.getText();
                if (bookId == null || copy == null) {
                    Util.errorMessage("Error!: ISBN and No of Copy should not be empty");
                } else if (bookId.trim().length() == 0 || copy.trim().length() == 0) {
                    Util.errorMessage("Error!: ISBN and No of Copy should not be empty");
                } else if (!Util.isNumeric(copy.trim())) {
                    Util.errorMessage("Error!: No of Copy should be Integer");
                } else {
                    Book book = systemController.getBookById(bookId.trim());

                    if (book != null) {
                        book.addCopy(Integer.parseInt(copy.trim()));
                        try {
                            systemController.updateBook(book);
                            Util.successMessage(copy + " copy of book " + book.getTitle() + " has been created");
                            clear();
                        } catch (LibrarySystemException ex) {
                            Util.errorMessage(ex.getMessage());
                        }

                    } else {
                        Util.errorMessage("Book does not exist");
                    }
                }
            }
        });
        contentPane.add(addCopyBtn);

        setResizable(false);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        Util.centerFrameOnDesktop(INSTANCE);
        isInitialized(true);
    }

    private void clear() {
        isbnField.setText("");
        copyField.setText("1");
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
