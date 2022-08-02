package librarysystem;

import librarysystem.ui.LoginWindow;

import java.awt.*;

public class Main {

    public static void main(String[] args) {
        EventQueue.invokeLater(() ->
        {
            LoginWindow.INSTANCE.init();
            LoginWindow.INSTANCE.setVisible(true);
        });
    }
}
