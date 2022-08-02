package librarysystem.ui;

import business.ControllerInterface;
import business.SystemController;
import librarysystem.LibWindow;
import librarysystem.ui.util.Util;

import javax.swing.*;
import java.util.Collections;

public class AllMemberIdsWindow extends JFrame implements LibWindow {

    public static AllMemberIdsWindow INSTANCE = new AllMemberIdsWindow();
    private final ControllerInterface controllerInterface = new SystemController();
    private JLabel allMemberIdsLbl;
    private JList<String> allMemberIdsList;
    private DefaultListModel listModel;
    private boolean initialize = false;

    @Override
    public void init() {
        setBounds(100, 100, 600, 550);
        getContentPane().setLayout(null);
        setTitle("Member Id's");

        allMemberIdsLbl = new JLabel("All Member IDs");
        Util.adjustLabelFont(allMemberIdsLbl, Util.LABEL_COLOR, true);
        allMemberIdsLbl.setBounds(234, 37, 128, 14);
        getContentPane().add(allMemberIdsLbl);

        listModel = new DefaultListModel<String>();
        allMemberIdsList = new JList<>(listModel);
        allMemberIdsList.setBounds(138, 89, 309, 364);

        JScrollPane scroll = new JScrollPane(allMemberIdsList,
                JScrollPane.VERTICAL_SCROLLBAR_ALWAYS, JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
        scroll.setBounds(138, 89, 309, 364);
        getContentPane().add(scroll);

        updateData();

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

    public void updateData() {
        java.util.List<String> memberIds = controllerInterface.allMemberIds();
        Collections.sort(memberIds);
        listModel.clear();
        for (String id : memberIds) {
            listModel.addElement(id);
        }
        allMemberIdsList.repaint();
    }
}
