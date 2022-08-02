package librarysystem.ui;

import business.ControllerInterface;
import business.SystemController;
import librarysystem.LibWindow;
import librarysystem.ui.util.Util;

import javax.swing.*;
import java.util.Collections;

public class AllBookIdsWindow extends JFrame implements LibWindow {

    public static AllBookIdsWindow INSTANCE = new AllBookIdsWindow();
    private final ControllerInterface controllerInterface = new SystemController();
    private JList<String> allBookIdsList;
    private DefaultListModel listModel;
    private JLabel allBookIdsLbl;
    private boolean initialize = false;

    private AllBookIdsWindow() {
    }

    @Override
    public void init() {
        setBounds(100, 100, 600, 550);
        getContentPane().setLayout(null);
        setTitle("Book Id's");

        allBookIdsLbl = new JLabel("All Book IDs");
        Util.adjustLabelFont(allBookIdsLbl, Util.LABEL_COLOR, true);
        allBookIdsLbl.setBounds(234, 37, 128, 14);
        getContentPane().add(allBookIdsLbl);

        listModel = new DefaultListModel<String>();
        allBookIdsList = new JList<>(listModel);
        allBookIdsList.setBounds(138, 89, 309, 364);
        JScrollPane scroll = new JScrollPane(allBookIdsList,
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
        initialize = true;
    }

    public void updateData() {
        java.util.List<String> bookIds = controllerInterface.allBookIds();
        Collections.sort(bookIds);
        listModel.clear();
        for (String id : bookIds) {
            listModel.addElement(id);
        }
        allBookIdsList.repaint();
    }
}
