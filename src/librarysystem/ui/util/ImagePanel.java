package librarysystem.ui.util;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.io.IOException;
import java.nio.file.FileSystems;
import java.nio.file.Path;

public class ImagePanel extends JPanel {
    private final Image image;

    public ImagePanel(String image) {
        Path path = FileSystems.getDefault().getPath(System.getProperty("user.dir")
                + "\\src\\librarysystem\\ui\\images", image);
        try {
            this.image = ImageIO.read(path.toFile());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.drawImage(image, 0, 0, this);
    }
}
