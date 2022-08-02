package librarysystem.ui;

import business.*;
import librarysystem.LibWindow;
import librarysystem.ui.util.Util;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

public class AddBookWindow extends JFrame implements LibWindow {

    public static AddBookWindow INSTANCE = new AddBookWindow();
    private final ControllerInterface systemController;
    private java.util.List<Author> authorRawList;
    private JPanel contentPane;
    private JTextField bookNameField;
    private JTextField isbnField;
    private JTextField checkOutLengthField;
    private JList<String> authorList;
    private DefaultListModel listModel;
    private boolean initialize = false;

    private AddBookWindow() {
        systemController = new SystemController();
    }

    @Override
    public void init() {
        setBounds(100, 100, 600, 550);
        contentPane = new JPanel();
        setTitle("Add Book");
        contentPane.setLayout(null);
        setContentPane(contentPane);

        JLabel titleLbl = new JLabel("Add New Book");
        titleLbl.setHorizontalAlignment(SwingConstants.CENTER);
        titleLbl.setBounds(217, 33, 130, 13);
        Util.adjustLabelFont(titleLbl, Util.DARK_BLUE, true);
        contentPane.add(titleLbl);

        JPanel panel = new JPanel();
        panel.setBounds(49, 183, 497, 198);
        contentPane.add(panel);
        panel.setLayout(null);

        JLabel authorsLbl = new JLabel("Authors");
        authorsLbl.setBounds(10, 18, 112, 13);
        Util.adjustLabelFont(titleLbl, Util.LABEL_COLOR, true);
        panel.add(authorsLbl);

        JButton addAuthorBtn = new JButton("Add Author");
        addAuthorBtn.setBounds(375, 10, 112, 28);
        addAuthorBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (authorRawList == null) {
                    authorRawList = new ArrayList<>();
                }
                AddAuthorDialog dialog = new AddAuthorDialog();
                dialog.setVisible(true);
            }
        });
        panel.add(addAuthorBtn);

        listModel = new DefaultListModel<String>();
        authorList = new JList<>(listModel);
        authorList.setBounds(10, 42, 477, 146);
        JScrollPane scroll = new JScrollPane(authorList,
                JScrollPane.VERTICAL_SCROLLBAR_ALWAYS, JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
        scroll.setBounds(10, 42, 477, 146);
        panel.add(scroll);


        JLabel bookNameLbl = new JLabel("Book Name:");
        bookNameLbl.setHorizontalAlignment(SwingConstants.TRAILING);
        bookNameLbl.setBounds(60, 88, 95, 13);
        Util.adjustLabelFont(bookNameLbl, Util.LABEL_COLOR, false);
        contentPane.add(bookNameLbl);

        bookNameField = new JTextField();
        bookNameField.setBounds(165, 85, 381, 19);
        contentPane.add(bookNameField);
        bookNameField.setColumns(10);

        isbnField = new JTextField();
        isbnField.setColumns(10);
        isbnField.setBounds(165, 114, 381, 19);
        contentPane.add(isbnField);

        JLabel isbnLbl = new JLabel("ISBN:");
        isbnLbl.setHorizontalAlignment(SwingConstants.TRAILING);
        isbnLbl.setBounds(60, 117, 95, 13);
        Util.adjustLabelFont(isbnLbl, Util.LABEL_COLOR, false);

        contentPane.add(isbnLbl);

        checkOutLengthField = new JTextField();
        checkOutLengthField.setColumns(10);
        checkOutLengthField.setBounds(165, 143, 381, 19);
        contentPane.add(checkOutLengthField);

        JLabel checkoutLengthLbl = new JLabel("Check Out Days:");
        checkoutLengthLbl.setHorizontalAlignment(SwingConstants.TRAILING);
        checkoutLengthLbl.setBounds(60, 146, 95, 13);
        Util.adjustLabelFont(checkoutLengthLbl, Util.LABEL_COLOR, false);

        contentPane.add(checkoutLengthLbl);

        JButton saveBtn = new JButton("Save");
        saveBtn.setBounds(153, 391, 85, 21);
        saveBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent ev) {
                String isbn = isbnField.getText();
                String bookName = bookNameField.getText();
                String maxCheckOutDay = checkOutLengthField.getText();
                try {
                    if (isbn == null || bookName == null || maxCheckOutDay == null || authorRawList == null) {
                        throw new LibrarySystemException("All fields must be non-empty");
                    }
                    if (isbn.trim().length() == 0 || bookName.trim().length() == 0 || !Util.isNumeric(maxCheckOutDay) || authorRawList.size() == 0) {
                        throw new LibrarySystemException("All fields must be non-empty");
                    }
                    systemController.addBook(isbn.trim(), bookName.trim(), Integer.parseInt(maxCheckOutDay.trim()), authorRawList);
                    Util.successMessage("Successfully added new Book at Library!");
                    clear();
                } catch (LibrarySystemException ex) {
                    Util.errorMessage("Error! " + ex.getMessage());
                }
            }
        });
        contentPane.add(saveBtn);

        JButton clearBtn = new JButton("Clear");
        clearBtn.setBounds(375, 391, 85, 21);
        clearBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                clear();
            }
        });
        contentPane.add(clearBtn);

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

    public void clear() {
        isbnField.setText("");
        bookNameField.setText("");
        checkOutLengthField.setText("");
        authorRawList = new ArrayList<>();
        listModel.clear();
        authorList.repaint();
    }

    public void addAuthorData() {
        listModel.clear();
        for (Author author : authorRawList) {
            listModel.addElement(author.getFirstName() + ", " + author.getLastName() + ": " + author.getAddress().getStreet() + ", " + author.getAddress().getCity() + ", " + author.getAddress().getState());
        }
        authorList.repaint();
    }

    private class AddAuthorDialog extends JFrame {
        private final JDialog dialog;
        private final JPanel contentPane;
        private final JTextField firstNameField;
        private final JTextField lastNameField;
        private final JTextField streetField;
        private final JTextField cityField;
        private final JTextField stateField;
        private final JTextField zipCodeField;
        private final JTextField phoneField;

        private final JTextArea bioTextArea;

        private AddAuthorDialog() {
            setTitle("Add Author");
            setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            dialog = new JDialog(this, "Add Author", true);
            dialog.setModalityType(Dialog.ModalityType.TOOLKIT_MODAL);
            setBounds(100, 100, 350, 500);
            contentPane = new JPanel();
            contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
            setContentPane(contentPane);
            contentPane.setLayout(null);

            JLabel titleLbl = new JLabel("Add Author");
            titleLbl.setHorizontalAlignment(SwingConstants.CENTER);
            Util.adjustLabelFont(titleLbl, Util.DARK_BLUE, true);
            titleLbl.setBounds(106, 36, 127, 14);
            contentPane.add(titleLbl);

            firstNameField = new JTextField();
            firstNameField.setBounds(106, 69, 158, 20);
            contentPane.add(firstNameField);
            firstNameField.setColumns(10);

            lastNameField = new JTextField();
            lastNameField.setBounds(106, 100, 158, 20);
            contentPane.add(lastNameField);
            lastNameField.setColumns(10);

            streetField = new JTextField();
            streetField.setBounds(106, 132, 158, 20);
            contentPane.add(streetField);
            streetField.setColumns(10);

            cityField = new JTextField();
            cityField.setBounds(106, 163, 158, 20);
            contentPane.add(cityField);
            cityField.setColumns(10);

            stateField = new JTextField();
            stateField.setBounds(106, 194, 158, 20);
            contentPane.add(stateField);
            stateField.setColumns(10);

            zipCodeField = new JTextField();
            zipCodeField.setBounds(106, 225, 158, 20);
            contentPane.add(zipCodeField);
            zipCodeField.setColumns(10);

            phoneField = new JTextField();
            phoneField.setBounds(106, 256, 158, 20);
            contentPane.add(phoneField);
            phoneField.setColumns(10);

            JLabel firstNameLbl = new JLabel("First Name : ");
            firstNameLbl.setHorizontalAlignment(SwingConstants.TRAILING);
            Util.adjustLabelFont(firstNameLbl, Util.LABEL_COLOR, false);
            firstNameLbl.setBounds(12, 72, 84, 14);
            contentPane.add(firstNameLbl);

            JLabel lastNameLbl = new JLabel("Last Name : ");
            lastNameLbl.setHorizontalAlignment(SwingConstants.TRAILING);
            Util.adjustLabelFont(lastNameLbl, Util.LABEL_COLOR, false);
            lastNameLbl.setBounds(10, 102, 86, 14);
            contentPane.add(lastNameLbl);

            JLabel streetLbl = new JLabel("Street : ");
            streetLbl.setHorizontalAlignment(SwingConstants.TRAILING);
            Util.adjustLabelFont(streetLbl, Util.LABEL_COLOR, false);
            streetLbl.setBounds(10, 134, 86, 14);
            contentPane.add(streetLbl);

            JLabel stateLbl = new JLabel("State : ");
            stateLbl.setHorizontalAlignment(SwingConstants.TRAILING);
            Util.adjustLabelFont(stateLbl, Util.LABEL_COLOR, false);
            stateLbl.setBounds(10, 196, 86, 14);
            contentPane.add(stateLbl);

            JLabel zipCodeLbl = new JLabel("ZipCode : ");
            zipCodeLbl.setHorizontalAlignment(SwingConstants.TRAILING);
            Util.adjustLabelFont(zipCodeLbl, Util.LABEL_COLOR, false);
            zipCodeLbl.setBounds(12, 228, 84, 14);
            contentPane.add(zipCodeLbl);

            JLabel phoneLbl = new JLabel("Phone : ");
            phoneLbl.setHorizontalAlignment(SwingConstants.TRAILING);
            Util.adjustLabelFont(phoneLbl, Util.LABEL_COLOR, false);
            phoneLbl.setBounds(12, 259, 84, 14);
            contentPane.add(phoneLbl);

            JLabel cityLbl = new JLabel("City : ");
            cityLbl.setHorizontalAlignment(SwingConstants.TRAILING);
            Util.adjustLabelFont(cityLbl, Util.LABEL_COLOR, false);
            cityLbl.setBounds(10, 165, 86, 14);
            contentPane.add(cityLbl);

            JLabel bioLbl = new JLabel("Bio : ");
            bioLbl.setHorizontalAlignment(SwingConstants.TRAILING);
            Util.adjustLabelFont(bioLbl, Util.LABEL_COLOR, false);
            bioLbl.setBounds(12, 295, 84, 13);
            contentPane.add(bioLbl);

            bioTextArea = new JTextArea();
            bioTextArea.setBounds(106, 289, 158, 122);
            JScrollPane scroll = new JScrollPane(bioTextArea,
                    JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED, JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
            scroll.setBounds(106, 289, 158, 122);
            contentPane.add(scroll);

            JButton cancelBtn = new JButton("Cancel");
            Util.adjustButtonFont(cancelBtn, Util.LABEL_COLOR, true);
            cancelBtn.setBounds(186, 421, 113, 32);
            cancelBtn.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    setVisible(false);
                    dispose();
                }
            });
            contentPane.add(cancelBtn);

            JButton addBtn = new JButton("Add");
            Util.adjustButtonFont(addBtn, Util.LABEL_COLOR, true);
            addBtn.setBounds(12, 421, 113, 32);
            addBtn.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    addAuthorToBook();
                    addAuthorData();
                    setVisible(false);
                    dispose();
                }
            });
            contentPane.add(addBtn);

            setResizable(false);
            setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            Util.centerFrameOnDesktop(this);
        }

        private void addAuthorToBook() {
            String firstName = firstNameField.getText();
            String lastName = lastNameField.getText();
            String street = streetField.getText();
            String city = cityField.getText();
            String state = stateField.getText();
            String zip = zipCodeField.getText();
            String phone = phoneField.getText();
            String bio = bioTextArea.getText();
            try {
                if (firstName == null || lastName == null || street == null || city == null || state == null || zip == null || phone == null || bio == null) {
                    throw new LibrarySystemException("All fields must be non-empty");
                } else if (firstName.trim().length() == 0 || lastName.trim().length() == 0 || street.trim().length() == 0 || city.trim().length() == 0 || state.trim().length() == 0 || zip.trim().length() == 0 || phone.trim().length() == 0 || bio.trim().length() == 0) {
                    throw new LibrarySystemException("All fields must be non-empty");
                }
                Author author = new Author(firstName.trim(), lastName.trim(), phone.trim(), new Address(street.trim(), city.trim(), state.trim(), zip.trim()), bio.trim());
                authorRawList.add(author);
            } catch (LibrarySystemException e) {
                Util.errorMessage(e.getMessage());
            }
        }


    }
}
