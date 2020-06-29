package WindowInterface;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridLayout;
import java.io.File;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;

/**
 * WelcomePanel class is used for show a wellocame message and a retro 
 * icon of ludo game. 
 *
 * @author Πλέσσιας Αλέξανδρος (ΑΜ.:2025201100068).
 */
public class WelcomePanel extends JPanel {

    // WelcomePanel panel's labels and panels.
    private JPanel welcomeMsgFirstLineJPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
    private JLabel welcomeMsgFirstLineJLabel = new JLabel("Καλώς ήρθατε στην εφαρμογή του παιχνιδιού Γκρινιάρης.");

    private JPanel welcomeMsgSecondLineJPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
    private JLabel welcomeMsgSecondLineJLabel = new JLabel("Καλή σας διασκέδαση!!!");

    private JPanel welcomeMsgJPanel = new JPanel(new GridLayout(2, 0));

    // Icon's Path,Panel and Label.
    private final String imageLogoPath = "Icons" + File.separator + "ludoLogo.png";
    private JPanel imageLogoJPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
    private JLabel logoJLabel = new JLabel(new ImageIcon(imageLogoPath));

    /**
     * Constuctor of WelcomePanel set basic utilities of JPanel.
     */
    public WelcomePanel() {

        setLayout(new BorderLayout());
        setBackground(WindowInterf.getBackgroundColor());
        Font myWelcomeFont = new Font("myWelcomeFont", Font.ITALIC + Font.BOLD, 14);

        // Welcome message area.
        welcomeMsgFirstLineJLabel.setFont(myWelcomeFont);
        welcomeMsgSecondLineJLabel.setFont(myWelcomeFont);

        welcomeMsgFirstLineJPanel.setBackground(WindowInterf.getBackgroundColor());
        welcomeMsgSecondLineJPanel.setBackground(WindowInterf.getBackgroundColor());

        welcomeMsgFirstLineJPanel.add(welcomeMsgFirstLineJLabel);
        welcomeMsgSecondLineJPanel.add(welcomeMsgSecondLineJLabel);

        // Image Logo area.
        welcomeMsgJPanel.add(welcomeMsgFirstLineJPanel);
        welcomeMsgJPanel.add(welcomeMsgSecondLineJPanel);
        add(welcomeMsgJPanel, BorderLayout.PAGE_START);

        imageLogoJPanel.setBackground(WindowInterf.getBackgroundColor());
        imageLogoJPanel.add(logoJLabel);
        add(imageLogoJPanel, BorderLayout.CENTER);
    }

}
