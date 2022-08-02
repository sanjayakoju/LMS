package librarysystem.ui;

import business.ControllerInterface;
import business.LibrarySystemException;
import business.SystemController;
import librarysystem.LibWindow;
import librarysystem.ui.util.Util;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class AddMemberWindow extends JFrame implements LibWindow {

    public static AddMemberWindow INSTANCE = new AddMemberWindow();
    private final ControllerInterface systemController = new SystemController();
    private boolean initialize = false;
    private JTextField idField;
    private JTextField firstNameField;
    private JTextField lastNameField;
    private JTextField streetField;
    private JTextField cityField;
    private JTextField stateField;
    private JTextField zipCodeField;
    private JTextField phoneField;

    @Override
    public void init() {
        setBounds(100, 100, 600, 550);
        getContentPane().setLayout(null);
        setTitle("Add Member");

        JLabel titleLbl = new JLabel("Add New Member");
        Util.adjustLabelFont(titleLbl, Util.DARK_BLUE, true);
        titleLbl.setBounds(218, 45, 127, 14);
        add(titleLbl);

        idField = new JTextField();
        idField.setBounds(255, 105, 158, 20);
        add(idField);
        idField.setColumns(10);

        JLabel idLbl = new JLabel("ID");
        idLbl.setHorizontalAlignment(SwingConstants.TRAILING);
        idLbl.setLabelFor(idField);
        Util.adjustLabelFont(idLbl, Util.LABEL_COLOR, true);
        idLbl.setBounds(159, 108, 86, 14);
        add(idLbl);

        firstNameField = new JTextField();
        firstNameField.setBounds(255, 136, 158, 20);
        add(firstNameField);
        firstNameField.setColumns(10);

        JLabel firstNameLbl = new JLabel("First Name");
        firstNameLbl.setHorizontalAlignment(SwingConstants.TRAILING);
        Util.adjustLabelFont(firstNameLbl, Util.LABEL_COLOR, true);
        firstNameLbl.setBounds(161, 139, 84, 14);
        add(firstNameLbl);

        lastNameField = new JTextField();
        lastNameField.setBounds(255, 167, 158, 20);
        add(lastNameField);
        lastNameField.setColumns(10);

        JLabel lastNameLbl = new JLabel("Last Name");
        lastNameLbl.setHorizontalAlignment(SwingConstants.TRAILING);
        Util.adjustLabelFont(lastNameLbl, Util.LABEL_COLOR, true);
        lastNameLbl.setBounds(159, 169, 86, 14);
        add(lastNameLbl);

        streetField = new JTextField();
        streetField.setBounds(255, 199, 158, 20);
        add(streetField);
        streetField.setColumns(10);

        JLabel streetLbl = new JLabel("Street");
        streetLbl.setHorizontalAlignment(SwingConstants.TRAILING);
        Util.adjustLabelFont(streetLbl, Util.LABEL_COLOR, true);
        streetLbl.setBounds(159, 201, 86, 14);
        add(streetLbl);

        cityField = new JTextField();
        cityField.setBounds(255, 230, 158, 20);
        add(cityField);
        cityField.setColumns(10);

        JLabel cityLbl = new JLabel("City");
        cityLbl.setHorizontalAlignment(SwingConstants.TRAILING);
        Util.adjustLabelFont(cityLbl, Util.LABEL_COLOR, true);
        cityLbl.setBounds(159, 232, 86, 14);
        add(cityLbl);

        JLabel stateLbl = new JLabel("State");
        stateLbl.setHorizontalAlignment(SwingConstants.TRAILING);
        Util.adjustLabelFont(stateLbl, Util.LABEL_COLOR, true);
        stateLbl.setBounds(159, 263, 86, 14);
        add(stateLbl);

        stateField = new JTextField();
        stateField.setBounds(255, 261, 158, 20);
        add(stateField);
        stateField.setColumns(10);

        JLabel zipCodeLbl = new JLabel("ZipCode");
        zipCodeLbl.setHorizontalAlignment(SwingConstants.TRAILING);
        Util.adjustLabelFont(zipCodeLbl, Util.LABEL_COLOR, true);
        zipCodeLbl.setBounds(161, 295, 84, 14);
        add(zipCodeLbl);

        zipCodeField = new JTextField();
        zipCodeField.setBounds(255, 292, 158, 20);
        add(zipCodeField);
        zipCodeField.setColumns(10);

        JLabel phoneLbl = new JLabel("Phone");
        phoneLbl.setHorizontalAlignment(SwingConstants.TRAILING);
        Util.adjustLabelFont(phoneLbl, Util.LABEL_COLOR, true);
        phoneLbl.setBounds(161, 326, 84, 14);
        add(phoneLbl);

        phoneField = new JTextField();
        phoneField.setBounds(255, 323, 158, 20);
        add(phoneField);
        phoneField.setColumns(10);

        JButton saveBtn = new JButton("Save");
        Util.adjustButtonFont(saveBtn, Util.LABEL_COLOR, true);
        saveBtn.setBounds(169, 373, 113, 32);
        saveBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent event) {
                String id = idField.getText();
                String firstName = firstNameField.getText();
                String lastName = lastNameField.getText();
                String street = streetField.getText();
                String city = cityField.getText();
                String state = stateField.getText();
                String zip = zipCodeField.getText();
                String cell = phoneField.getText();

                try {
                    systemController.addMember(id.trim(), firstName.trim(), lastName.trim(), cell.trim(), street.trim(), city.trim(), state.trim(), zip.trim());
                    Util.successMessage("Successfully added new Library member!");
                    clear();
                } catch (LibrarySystemException e) {
                    Util.errorMessage("Error! " + e.getMessage());
                }
            }
        });
        add(saveBtn);

        JButton clearBtn = new JButton("Clear");
        Util.adjustButtonFont(clearBtn, Util.LABEL_COLOR, true);
        clearBtn.setBounds(300, 373, 113, 32);
        clearBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                clear();
            }
        });
        add(clearBtn);

        setResizable(false);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        Util.centerFrameOnDesktop(INSTANCE);
        isInitialized(true);
    }

    private void clear() {
        idField.setText("");
        firstNameField.setText("");
        lastNameField.setText("");
        streetField.setText("");
        cityField.setText("");
        stateField.setText("");
        zipCodeField.setText("");
        phoneField.setText("");
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
