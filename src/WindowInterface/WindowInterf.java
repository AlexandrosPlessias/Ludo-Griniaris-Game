package WindowInterface;

import java.awt.BorderLayout;
import java.awt.CardLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Frame;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.SwingConstants;

/**
 * WindowInterf class is our games main JFrame have the menu build, the
 * navigation buttons and cardLayout for switching JPanels/Screens.
 *
 * @author Πλέσσιας Αλέξανδρος (ΑΜ.:2025201100068).
 */
public class WindowInterf extends JFrame {

    // The backgroundColor
    private static final Color backgroundColor = Color.white;

    // Menu 
    private final JMenuBar menuBar = new JMenuBar();

    private final JMenu gameJMenu = new JMenu("Παιχνίδι");
    private final JMenuItem startGameJMenuItem = new JMenuItem("Συμμετοχή σε παιχνίδι");
    private final JMenuItem stopGameJMenuItem = new JMenuItem("Διακοπή παιχνιδιού");
    private final JMenuItem exitJMenuItem = new JMenuItem("Έξοδος");

    private final JMenu helpJMenu = new JMenu("Βοήθεια");
    private final JMenuItem helpJMenuItem = new JMenuItem("Βοήθεια");
    private final JMenuItem aboutJMenuItem = new JMenuItem("Σχετικά με τον «Γκρινιάρη»");

    // Navigation 
    private JButton nextJButton = new JButton(" Next > ");
    private JButton previousJButton = new JButton(" < Back ");
    private int pageCounter = -1;

    // Dynamic panel via CardLayout.
    private static final CardLayout crdLyt = new CardLayout(10, 10);
    private JPanel dynamicJPanel = new JPanel(crdLyt);
    private WelcomePanel welcomePanel;
    private SettingsPanel settingsPanel;
    private GamePanel gamePanel;

    // Navigation buttons
    private Box buttonsBox = new Box(BoxLayout.X_AXIS);

    // Menu ButtonsListeners
    private StartListener.StartJDialog startJDialog = null;
    private HelpListener.HelpJDialog helpJDialog = null;
    private AboutListener.AboutJDialog aboutJDialog = null;

    /**
     * Constuctor of WindowInterf set basic utilities of JFrame,menu and
     * navigation buttons.
     */
    public WindowInterf() {

        setTitle("Γκρινιάρης v0.3");
        setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
        setMinimumSize(new Dimension(1000, 850));
        setLocationRelativeTo(null);
        setVisible(true);
        setResizable(true); // Best is with false.
        setLayout(new BorderLayout());
        dynamicJPanel.setBackground(backgroundColor);

        // Dynamic area add panels.
        add(dynamicJPanel, BorderLayout.CENTER);

        // Add panels to Dynamic panel.
        welcomePanel = new WelcomePanel();
        dynamicJPanel.add(welcomePanel);

        settingsPanel = new SettingsPanel(nextJButton, previousJButton);
        dynamicJPanel.add(settingsPanel);

        // Create menu bar. 
        setJMenuBar(menuBar);

        menuBar.add(gameJMenu);
        menuBar.add(helpJMenu);

        gameJMenu.add(startGameJMenuItem);
        startGameJMenuItem.addActionListener(new StartListener());
        stopGameJMenuItem.setEnabled(false);
        stopGameJMenuItem.setToolTipText("Δεν υπάρχει λόγος ύπαρξης χωρίς Connection με τον Server.");
        gameJMenu.add(stopGameJMenuItem);
        gameJMenu.addSeparator();
        exitJMenuItem.addActionListener(new ExitListener());
        gameJMenu.add(exitJMenuItem);

        helpJMenuItem.addActionListener(new HelpListener());
        helpJMenu.add(helpJMenuItem);
        helpJMenu.addSeparator();
        aboutJMenuItem.addActionListener(new AboutListener());
        helpJMenu.add(aboutJMenuItem);

        // Create buttons panel. 
        nextJButton.addActionListener(new NextListener());
        previousJButton.setEnabled(false);
        previousJButton.addActionListener(new PreviousListener());

        buttonsBox.add(Box.createHorizontalGlue());
        buttonsBox.add(previousJButton);
        buttonsBox.add(Box.createRigidArea(new Dimension(8, 0)));
        buttonsBox.add(nextJButton);
        buttonsBox.add(Box.createRigidArea(new Dimension(8, 0)));
        // Create border in buttonsPanel WITH inner border a 5,0,5,0 spaces and a line as outer.
        buttonsBox.setBorder(BorderFactory.createCompoundBorder(BorderFactory.createLineBorder(null), BorderFactory.createEmptyBorder(5, 0, 5, 0)));

        add(buttonsBox, BorderLayout.PAGE_END);

        addWindowListener(new WindowCloseListener());

        pack();

    }

    // GET //
    /**
     * @return Color of background.
     */
    public static Color getBackgroundColor() {
        return backgroundColor;
    }

    // LISTENERS //
    /**
     * Start Listener Listener create a JDialog once and show it also have a
     * inner class the StartJDialog class for game Settings.
     */
    private class StartListener implements ActionListener {

        // Show or hide JDialog.
        @Override
        public void actionPerformed(ActionEvent e) {

            if (startJDialog == null) {
                startJDialog = new StartJDialog(WindowInterf.this);
            }
            startJDialog.setVisible(true);

        }

        /**
         * StartJDialog is a JDialog which creates a settingsPanel and a start
         * button.
         */
        private class StartJDialog extends JDialog {

            SettingsPanel settingsJDialogPanel = new SettingsPanel(nextJButton, previousJButton);

            public StartJDialog(Frame owner) {
                super(owner, true);
                setTitle("Ρυθμίσεις παιχνιδιού");
                setMinimumSize(new Dimension(800, 750));
                setLocationRelativeTo(owner);
                setResizable(false);
                setLayout(new BorderLayout());
                setBackground(backgroundColor);

                // Go to next screen which is disabled.
                nextJButton.doClick();

                // Start Button area.
                JButton startJButton = new JButton(" Start ");
                startJButton.addActionListener(new startGameButtonListener());

                Box startButtonBox = new Box(BoxLayout.X_AXIS);

                startButtonBox.add(Box.createHorizontalGlue());
                startButtonBox.add(startJButton);
                startButtonBox.add(Box.createHorizontalGlue());

                startButtonBox.setBorder(BorderFactory.createCompoundBorder(BorderFactory.createLineBorder(null), BorderFactory.createEmptyBorder(5, 0, 5, 0)));

                // Add component to Jdialog.
                add(settingsJDialogPanel, BorderLayout.CENTER);
                add(startButtonBox, BorderLayout.PAGE_END);

            }

            /**
             * StartGameButtonListener give values to gamepanel from
             * jdialogGamePanel and press next in back screen which is disabled.
             */
            private class startGameButtonListener implements ActionListener {

                @Override
                public void actionPerformed(ActionEvent e) {

                    settingsPanel.setNicknamTextField(settingsJDialogPanel.getNicknamTextField());
                    settingsPanel.setColorComboBox(settingsJDialogPanel.getColorComboBox());
                    settingsPanel.setBoardSizeNumComboBox(settingsJDialogPanel.getBoardSizeNumComboBox());
                    settingsPanel.setPawnNumComboBox(settingsJDialogPanel.getPawnNumComboBox());
                    settingsPanel.setBackgoundColorComboBox(settingsJDialogPanel.getBackgoundColorComboBox());

                    nextJButton.doClick();

                    StartJDialog.this.dispose();

                }

            }

        }

    }

    /**
     * Exit Listener handle the possibility of the user wants to close the
     * window and asking if is sure.
     */
    private class ExitListener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            int answer = JOptionPane.showConfirmDialog(WindowInterf.this, "Είσαι σίγουρος \nπως θέλεις να κλείσεις το παιχνίδι?", "Κλείσιμο παραθύρου", JOptionPane.YES_NO_OPTION, JOptionPane.WARNING_MESSAGE);
            if (answer == JOptionPane.YES_OPTION) {
                WindowInterf.this.dispose();
            } else {
                // Do nothing.
            }
        }

    }

    /**
     * Help Listener create once and show a HelpJDialog which is an inner class
     */
    private class HelpListener implements ActionListener {

        // Show or hide JDialog.
        @Override
        public void actionPerformed(ActionEvent e) {

            if (helpJDialog == null) {
                helpJDialog = new HelpJDialog(WindowInterf.this);
            }
            helpJDialog.setVisible(true);
        }

        /**
         * HelpJDialog create a JDialog with a disabled text field with cersor
         * set on start with helpful info and rules of game.
         */
        private class HelpJDialog extends JDialog {

            public HelpJDialog(Frame owner) {
                super(owner, true);

                setTitle("Βοήθεια");
                setMinimumSize(new Dimension(600, 400));
                setLocationRelativeTo(owner);
                setResizable(false);
                setLayout(new BorderLayout());
                setBackground(backgroundColor);

                Font myHelpFrameFont = new Font("myWelcomeFont", Font.BOLD + Font.HANGING_BASELINE, 13);

                JTextArea helpJTextField = new JTextArea();

                String helpString = ""
                        + "Επιλέξτε ένα ψευδώνυμο και χρώμα (δυνατότητα από 2 έως 4 παίκτες) καθώς και μέγεθος ταμπλό, χρώμα υπόβαθρου και με πόσα πιόνια θέλετε να έχει ο κάθε παίκτης. Δεν υπάρχει πλεονέκτημα ή μειονέκτημα για την επιλογή ενός χρώματος έναντι ενός άλλου. \n"
                        + "\n"
                        + "Η σειρά των παικτών επιλέγετε τυχαία και φαίνεται στην αριστερή πλευρά του ταμπλό και το ζάρι που φέρνει ο κάθε παίκτης φαίνεται στα δεξιά.\n"
                        + "\n"
                        + "Παίζουμε με δεξιόστροφη φορά και βγάζεις τα πιόνια σου από την περιοχή αφετηρίας εαν φέρεις  5 και ξαναπαίζεται για μια ακόμα  φορά. Εαν, δεν φέρεται 5 τότε χάνεται την σειρά σας για αυτό οπλιστείτε με υπομονή.\n"
                        + "\n"
                        + "Σημειώστε ότι, θα πρέπει να ρίξετε τον ακριβή αριθμό για να μετακινήσετε ένα πιόνι στο τελικό κελί (home/final), εαν δεν έχετε αυτή την δυνατότητα  θα πρέπει να επιλέξετε να μετακινήσετε ένα άλλο πιόνι εαν έχετε έγκυρη κίνηση αλλιώς χάνετε τη σειρά σας. Νικητής είναι αυτός του οποίοι όλα του τα πιόνια του φτάσουν σπίτι (home/final)  που είναι στο κέντρο του ταμπλό\n"
                        + "\n"
                        + "Όποιος ρίξει  6 έχει την δυνατότητα να ξαναρίξει το ζάρι για μια ακόμα φορά. Οι παίκτες που είναι αρκετά τυχεροί μπορεί να ρίξουν αρκετά διαδοχικά 6αρια και να φτάσουν αρκετά μακριά σε σχεση με τους αντιπάλους τους.\n"
                        + "\n"
                        + "Προσέξτε!!! Εαν έχετε ένα πιόνι μόνο του σε ένα κελί και αυτό (το κελί) δεν είναι σε κυκλικό κελί (Circle Cell) ή σε μονοπάτι τερματισμού(τα τελευταία χρωματιστά κελιά πριν το σπίτι) ή δεν προστατεύεται από φράγμα (στο ίδιο κελί πάνω από 2 πιόνια σου)  τότε υπάρχει ο κίνδυνος κάποιος αντίπαλος να σου πιάσει το πιόνι και να σου το γυρίσει  πάλι στην περιοχή εκκίνησης και μετά θα πρέπει να το βγάλεις πάλι όταν φέρεις 5.\n"
                        + "\n"
                        + "Παραλλαγές !!! Στο ταμπλό του παιχνιδιού υπάρχουν ακόμα 2 είδη κελιών και αυτά είναι  το αστέρι(Star Cell) και ο κύκλος(Circle Cell), είναι επιρροές από το Pachisi (ο πρόγονος /πρόδρομος του σημερινού \"Γκρινιάρη\").\n\n"
                        + "Αστέρι: Όταν ένα πιόνι κάτσει σε ένα τέτοιο κελί προωθείτε αυτόματα στο επίμονο διαθέσιμο της διαδρομής του εαν υπάρχει, εαν δεν υπάρχει άπλα κάθετε στο τελευταίο. Αξίζει να σημειωθεί πως εαν υπάρχει φράγμα είναι αδύνατο να μεταφερθεί στο επόμενο και πως πρώτα περνά, κάθετε και έπειτα στιγμιαία μεταφέρεται  στο επόμενο άρα είναι πιθανό να πιάσει αντίπαλα πιόνια και στα 2 κελιά.\n\n"
                        + "Κύκλος: Σε αυτό το κελί είναι δυνατόν να υπάρχουν πιόνια από διαφορετικούς παίκτες ή μόνο έναν και να είναι προστατευμένα από το ενδεχόμενο πιασίματος από κάποιον αντίπαλο.";

                helpJTextField.setText(helpString);
                helpJTextField.setFont(myHelpFrameFont);
                helpJTextField.setLineWrap(true);
                helpJTextField.setWrapStyleWord(true);
                helpJTextField.setEditable(false);
                helpJTextField.setCaretPosition(0);

                JScrollPane helpJScrollPane = new JScrollPane(helpJTextField);
                helpJScrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
                helpJScrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);

                add(helpJScrollPane, BorderLayout.CENTER);

            }

        }

    }

    /**
     * About Listener create once and show a AboutJDialog which is an inner class.
     */
    private class AboutListener implements ActionListener {

        // Show or hide JDialog.
        @Override
        public void actionPerformed(ActionEvent e) {

            if (aboutJDialog == null) {
                aboutJDialog = new AboutJDialog(WindowInterf.this);
            }
            aboutJDialog.setVisible(true);

        }

        /**
         * About JDialog just show the Name, Registation Code of UOP and 
         * a blue colored mail.
         */
        private class AboutJDialog extends JDialog {

            public AboutJDialog(Frame owner) {
                super(owner, true);

                setTitle("Σχετικά με τον «Γκρινιάρη»");
                setMinimumSize(new Dimension(310, 110));
                setLocationRelativeTo(owner);
                setResizable(false);
                setLayout(new BorderLayout());
                setBackground(backgroundColor);

                JPanel tableElementsJPanel = new JPanel(new GridLayout(3, 2));
                tableElementsJPanel.setBackground(backgroundColor);

                JLabel studentFieldJLabel = new JLabel("Ονοματεπώνυμο: ", SwingConstants.RIGHT);
                JLabel studentJLabel = new JLabel("Πλέσσιας Αλέξανδρος", SwingConstants.LEFT);

                JLabel regNumFieldLabel = new JLabel("Αριθμός Μητρώου: ", SwingConstants.RIGHT);
                JLabel regNumJLabel = new JLabel("2025201100068", SwingConstants.LEFT);

                JLabel emailFieldJLabel = new JLabel("E-mail: ", SwingConstants.RIGHT);
                JLabel emailJLabel = new JLabel("<HTML><FONT color=\"#000099\"><U>cst11068@uop.gr</U></FONT> </HTML>", SwingConstants.LEFT);

                tableElementsJPanel.add(studentFieldJLabel);
                tableElementsJPanel.add(studentJLabel);
                tableElementsJPanel.add(regNumFieldLabel);
                tableElementsJPanel.add(regNumJLabel);
                tableElementsJPanel.add(emailFieldJLabel);
                tableElementsJPanel.add(emailJLabel);

                add(tableElementsJPanel, BorderLayout.CENTER);

            }

        }

    }

    /**
     * Window Listener handle the possibility of the user wants to close the
     * window and asking if is sure.
     */
    private class WindowCloseListener extends WindowAdapter {

        @Override
        public void windowClosing(WindowEvent e) {
            int answer = JOptionPane.showConfirmDialog(WindowInterf.this, "Είσαι σίγουρος \nπως θέλεις να κλείσεις το παιχνίδι?", "Κλείσιμο παραθύρου", JOptionPane.YES_NO_OPTION, JOptionPane.WARNING_MESSAGE);
            if (answer == JOptionPane.YES_OPTION) {
                WindowInterf.this.dispose();
            } else {
                // Do nothing.
            }
        }

    }

    /**
     * Next Listener handle the changes of cardlayout, change text of buttons and
     * disable or enable menu items.
     */
    private class NextListener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent ae) {

            if (pageCounter == -1) {
                settingsPanel.clearAndEnableAll();
                dynamicJPanel.add(settingsPanel);
            }

            if (pageCounter <= 1) {
                pageCounter++;
            }

            if (pageCounter == 0) {
                startGameJMenuItem.setEnabled(false);
                nextJButton.setText(" Start ");
                nextJButton.setEnabled(false);
            }

            if (pageCounter == 1) {
                // create game panel with start button clic.
                gamePanel = settingsPanel.gamePanelCreator();
                dynamicJPanel.add(gamePanel);
                startGameJMenuItem.setEnabled(false);
                nextJButton.setText(" Exit ");
            }

            if (pageCounter < 1) {
                previousJButton.setEnabled(true);
            }

            // Chech max next.
            if (pageCounter <= 1) {
                crdLyt.next(dynamicJPanel);
            }

        }
    }

    /**
     * Previous Listener handle the changes of cardlayout, change text of buttons  and
     * cleaning the settings panel's fields.
     */
    private class PreviousListener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent ae) {
            pageCounter--;

            if ((pageCounter == -1) && (nextJButton.getText().compareTo(" Start ") == 0)) {
                nextJButton.setText(" Next > ");
                startGameJMenuItem.setEnabled(true);
                nextJButton.setEnabled(true);
                settingsPanel.clearAndEnableAll();
            }

            if (pageCounter == -1) {
                previousJButton.setEnabled(false);
            }

            if (pageCounter == 0) {

                nextJButton.setText(" Start ");
            }

            crdLyt.previous(dynamicJPanel);
        }
    }

}
