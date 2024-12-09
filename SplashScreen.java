import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;

public class SplashScreen extends JFrame {
    public SplashScreen() {
        // Set up frame
        setTitle("TaskEase - Splash Screen");
        setSize(400, 400);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());
        setLocationRelativeTo(null); // Posisikan di tengah layar

        // Panel utama
        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.Y_AXIS));
        mainPanel.setBackground(Color.WHITE);

        // Muat gambar logo menggunakan BufferedImage
        JLabel logoLabel = new JLabel();
        try {
            // Gunakan path absolut
            String imagePath = "E:/Coding VSC/TaskEase/taskease.png"; // Ganti dengan path absolut Anda
            BufferedImage originalImage = ImageIO.read(new File(imagePath));
            int logoWidth = 300; // Lebar gambar diinginkan
            int logoHeight = (int) (originalImage.getHeight() * (logoWidth / (double) originalImage.getWidth())); // Skala proporsional
            Image scaledImage = originalImage.getScaledInstance(logoWidth, logoHeight, Image.SCALE_SMOOTH);
            ImageIcon scaledLogoIcon = new ImageIcon(scaledImage);
            logoLabel.setIcon(scaledLogoIcon);
        } catch (IOException e) {
            e.printStackTrace();
            logoLabel.setText("Gambar tidak ditemukan!");
            logoLabel.setFont(new Font("Arial", Font.BOLD, 16));
            logoLabel.setForeground(Color.RED);
        }
        logoLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

        // Tambahkan logo ke panel utama
        mainPanel.add(Box.createVerticalGlue());
        mainPanel.add(logoLabel);
        mainPanel.add(Box.createVerticalGlue());

        // Panel untuk tombol "Masuk"
        JPanel buttonPanel = new JPanel();
        buttonPanel.setBackground(Color.LIGHT_GRAY);
        JButton enterButton = new JButton("Masuk");
        enterButton.setFont(new Font("Arial", Font.BOLD, 16));
        enterButton.setBackground(new Color(0, 123, 255));
        enterButton.setForeground(Color.WHITE);
        enterButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose(); // Tutup splash screen
                TaskGUI.main(null); // Buka aplikasi utama
            }
        });
        buttonPanel.add(enterButton);

        // Tambahkan panel utama dan tombol ke frame
        add(mainPanel, BorderLayout.CENTER); // Logo di tengah
        add(buttonPanel, BorderLayout.SOUTH); // Tombol di bawah
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            SplashScreen splashScreen = new SplashScreen();
            splashScreen.setVisible(true);
        });
    }
}