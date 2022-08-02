package librarysystem.ui.util;

import javax.swing.*;
import java.awt.*;
import java.nio.file.FileSystems;
import java.nio.file.Path;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;


public class Util {
    public static final Color DARK_BLUE = Color.BLUE.darker();
    public static final Color ERROR_MESSAGE_COLOR = Color.RED.darker(); //dark red
    public static final Color INFO_MESSAGE_COLOR = new Color(24, 98, 19); //dark green
    public static final Color LABEL_COLOR = Color.BLACK;
    public static final Color BACKGROUND_COLOR = Color.WHITE;
    //rgb(18, 75, 14)

    public static Font makeSmallFont(Font f) {
        return new Font(f.getName(), f.getStyle(), (f.getSize() - 2));
    }

    public static void adjustLabelFont(JLabel label, Color color, boolean bigger) {
        if (bigger) {
            Font f = new Font(label.getFont().getName(),
                    label.getFont().getStyle(), (label.getFont().getSize() + 2));
            label.setFont(f);
        } else {
            Font f = new Font(label.getFont().getName(),
                    label.getFont().getStyle(), (label.getFont().getSize() - 2));
            label.setFont(f);
        }
        label.setForeground(color);

    }

    public static void adjustButtonFont(JButton button, Color color, boolean bigger) {
        if (bigger) {
            Font f = new Font(button.getFont().getName(),
                    button.getFont().getStyle(), (button.getFont().getSize() + 2));
            button.setFont(f);
        } else {
            Font f = new Font(button.getFont().getName(),
                    button.getFont().getStyle(), (button.getFont().getSize() - 2));
            button.setFont(f);
        }
        button.setForeground(color);

    }

    /**
     * Sorts a list of numeric strings in natural number order
     */
    public static List<String> numericSort(List<String> list) {
        Collections.sort(list, new NumericSortComparator());
        return list;
    }

    public static void successMessage(String message) {
        JOptionPane.showMessageDialog(null, message, "Success", JOptionPane.INFORMATION_MESSAGE);
    }

    public static void errorMessage(String message) {
        JOptionPane.showMessageDialog(null, message, "Error", JOptionPane.ERROR_MESSAGE);
    }

    public static boolean isNumeric(String s) {
        if (s == null) return false;
        try {
            Integer.parseInt(s);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    public static void centerFrameOnDesktop(Component f) {
        Toolkit toolkit = Toolkit.getDefaultToolkit();
        int height = toolkit.getScreenSize().height;
        int width = toolkit.getScreenSize().width;
        int frameHeight = f.getSize().height;
        int frameWidth = f.getSize().width;
        f.setLocation(((width - frameWidth) / 2), (height - frameHeight) / 3);
    }

    private static ImageIcon getIcon(String image, int width, int height) {
        Path path = FileSystems.getDefault().getPath(System.getProperty("user.dir")
                + "\\src\\librarysystem\\ui\\images", image);
        Image resizedImage = new ImageIcon(path.toString()).getImage().getScaledInstance(width, height, java.awt.Image.SCALE_SMOOTH);
        return new ImageIcon(resizedImage);
    }

    public static JButton dashboardButton(String image, String text, int width, int height) {
        JButton totalBookBtn = new JButton(getIcon(image, width, height));
        totalBookBtn.setText(text);
        totalBookBtn.setHorizontalAlignment(SwingConstants.LEADING);
        totalBookBtn.setBorder(null);
        totalBookBtn.setContentAreaFilled(false);
        totalBookBtn.setBorderPainted(false);
        totalBookBtn.setFocusPainted(false);
        totalBookBtn.setFont(new Font("Arial", Font.BOLD, 22));
        totalBookBtn.setForeground(Color.WHITE);
        return totalBookBtn;
    }

    static class NumericSortComparator implements Comparator<String> {
        @Override
        public int compare(String s, String t) {
            if (!isNumeric(s) || !isNumeric(t))
                throw new IllegalArgumentException("Input list has non-numeric characters");
            int sInt = Integer.parseInt(s);
            int tInt = Integer.parseInt(t);
            if (sInt < tInt) return -1;
            else if (sInt == tInt) return 0;
            else return 1;
        }
    }
}
