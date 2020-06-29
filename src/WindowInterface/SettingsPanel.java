package WindowInterface;

import GameplayAndLogic.Player;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.ItemSelectable;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.concurrent.TimeUnit;
import javax.imageio.ImageIO;
import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JProgressBar;
import javax.swing.JTextField;
import javax.swing.border.TitledBorder;

/**
 * SettingsPanel class is used for the entry of the players and the
 * customization of the game (playersNumber, gameboardSize, pawnNum & background
 * color)
 *
 * @author Πλέσσιας Αλέξανδρος (ΑΜ.:2025201100068).
 */
public class SettingsPanel extends JPanel {

    // Basic class fields.
    private static final Color backgroundColour = WindowInterf.getBackgroundColor();
    private JButton nextButton;
    private JButton previousButton;

    // PlayersFormPanel
    private JLabel[] nicknameLabel = new JLabel[4];
    private JTextField[] nicknamTextField = new JTextField[4];

    private JLabel[] colorLabel = new JLabel[4];
    private JComboBox[] colorComboBox = new JComboBox[4];

    // BoardSize, Players number and pawn numbers.
    private JLabel boardSizeLabel = new JLabel("Μέγεθος : ", JLabel.RIGHT);
    private JComboBox boardSizeNumComboBox = new JComboBox<>();

    private JLabel pawnNumLabel = new JLabel("Πιόνια : ", JLabel.RIGHT);
    private JComboBox pawnNumComboBox = new JComboBox<>();

    private JLabel backgoundColorLabel = new JLabel("Χρώμα υπόβαθρου : ", JLabel.RIGHT);
    private JComboBox backgoundColorComboBox = new JComboBox<>();

    private JButton submitButton = new JButton(" Αποστολή ");

    // Progress bar 
    private JProgressBar progressBar = new JProgressBar(0, 100);

    // Path and icon of StartLogo. 
    private final String pressStartLogoPath = "Icons" + File.separator + "pressStartLogo.png";
    private BufferedImage pressStartLogo = null;

    private JPanel pressStartPanel;
    private ComboBoxItemListener comboBoxItemListener = new ComboBoxItemListener();

    /**
     * Constructor of SettingsPanel set background color and the use of
     * ancillary methods for the creation of panel.
     *
     * @param nextButton For transition to Game screen and more.
     * @param previousButton For transition to Welcome screen and more.
     */
    public SettingsPanel(JButton nextButton, JButton previousButton) { // Must Get Server Available colors & ServerNAME...

        setLayout(new BorderLayout());
        setBackground(WindowInterf.getBackgroundColor());
        this.nextButton = nextButton;
        this.previousButton = previousButton;

        Box inputAndSettingSPanel = new Box(BoxLayout.Y_AXIS);
        inputAndSettingSPanel.add(createPlayersFormPanel());
        inputAndSettingSPanel.add(Box.createRigidArea(new Dimension(20, 20)));
        inputAndSettingSPanel.add(createGameBoardSettingPanel());
        inputAndSettingSPanel.add(Box.createRigidArea(new Dimension(20, 20)));
        inputAndSettingSPanel.add(createProgressPanel());
        inputAndSettingSPanel.add(Box.createRigidArea(new Dimension(20, 20)));

        add(inputAndSettingSPanel, BorderLayout.PAGE_START);
        add(createPressStartPanel(), BorderLayout.CENTER);

    }

    public GamePanel gamePanelCreator() {

        String sizeStr = (String) boardSizeNumComboBox.getSelectedItem();
        int sizeint = 0;

        if (sizeStr.compareTo("11 x 11") == 0) {
            sizeint = 4;
        } else if (sizeStr.compareTo("13 x 13") == 0) {
            sizeint = 5;
        } else { // sizeStr.compareTo("15 x 15")==0
            sizeint = 6;
        }

        String pawnNumStr = (String) pawnNumComboBox.getSelectedItem();
        int pawnNum = 0;

        if (pawnNumStr.compareTo("2") == 0) {
            pawnNum = 2;
        } else if (pawnNumStr.compareTo("3") == 0) {
            pawnNum = 3;
        } else if (pawnNumStr.compareTo("4") == 0) {
            pawnNum = 4;
        }

        String backgoundColorStr = (String) backgoundColorComboBox.getSelectedItem();
        Color bckgndColor;

        if (backgoundColorStr.compareTo("Μαύρο") == 0) {
            bckgndColor = Color.black;
        } else { //backgoundColorStr.compareTo("Λευκό")==1)
            bckgndColor = Color.white;
        }

        int playersCounter = 0;
        for (JTextField nicknamTextFieldFE : nicknamTextField) {
            if (!nicknamTextFieldFE.getText().isEmpty()) {
                playersCounter++;
            }
        }

        Player[] players = new Player[playersCounter];
        playersCounter = -1;
        for (int i = 0; i < nicknamTextField.length; i++) {
            if (!nicknamTextField[i].getText().isEmpty()) {
                playersCounter++;
                String pawnColorStr = (String) colorComboBox[i].getSelectedItem();
                Color pawnColor;
                if (pawnColorStr.compareTo("Πράσινο") == 0) {
                    pawnColor = Color.green;
                } else if (pawnColorStr.compareTo("Κόκκινο") == 0) {
                    pawnColor = Color.red;
                } else if (pawnColorStr.compareTo("Κίτρινο") == 0) {
                    pawnColor = Color.yellow;
                } else { //ppawnColorStr.compareTo("Μπλέ") == 0
                    pawnColor = Color.blue;
                }
                //Num of pawns
                players[playersCounter] = new Player(nicknamTextField[i].getText(), pawnColor, pawnNum);

            }
        }

        return (new GamePanel(sizeint, bckgndColor, players));

    }

    
    // UTILITIES METHODS //
    
    /**
     * Initialize comboboxes.
     */
    private void createComboBoxes() {
        // Colors ComboBox.    

        // Here WANT FOR LOOP WITH add available colours from Server To Combobox.
        for (int i = 0; i < 4; i++) {
            ((JLabel) colorComboBox[i].getRenderer()).setHorizontalAlignment(JLabel.CENTER);
            colorComboBox[i].addItem("Πράσινο");
            colorComboBox[i].addItem("Κόκκινο");
            colorComboBox[i].addItem("Κίτρινο");
            colorComboBox[i].addItem("Μπλέ");
            colorComboBox[i].setMaximumRowCount(4);
            colorComboBox[i].setSelectedIndex(-1);
            colorComboBox[i].addItemListener(comboBoxItemListener);
        }

        // Board size ComboBox.
        ((JLabel) boardSizeNumComboBox.getRenderer()).setHorizontalAlignment(JLabel.CENTER);
        boardSizeNumComboBox.addItem("11 x 11");
        boardSizeNumComboBox.addItem("13 x 13");
        boardSizeNumComboBox.addItem("15 x 15");
        boardSizeNumComboBox.setMaximumRowCount(3);
        boardSizeNumComboBox.setSelectedIndex(-1);

        // Pawn num ComboBox.
        ((JLabel) pawnNumComboBox.getRenderer()).setHorizontalAlignment(JLabel.CENTER);
        pawnNumComboBox.addItem("2");
        pawnNumComboBox.addItem("3");
        pawnNumComboBox.addItem("4");
        pawnNumComboBox.setMaximumRowCount(3);
        pawnNumComboBox.setSelectedIndex(-1);

        // Backgound color ComboBox.
        ((JLabel) backgoundColorComboBox.getRenderer()).setHorizontalAlignment(JLabel.CENTER);
        backgoundColorComboBox.addItem("Μαύρο");
        backgoundColorComboBox.addItem("Λευκό");
        backgoundColorComboBox.setMaximumRowCount(2);
        backgoundColorComboBox.setSelectedIndex(-1);

    }

    private JPanel createPlayersFormPanel() {

        for (int i = 0; i < 4; i++) {
            nicknameLabel[i] = new JLabel("Εισάγεται Όνομα " + (i + 1) + "ου Παίκτη: ", JLabel.RIGHT);
            nicknamTextField[i] = new JTextField(20);
            nicknamTextField[i].setHorizontalAlignment(JTextField.CENTER);

            colorLabel[i] = new JLabel("Επιλέξτε Χρώμα " + (i + 1) + "ου Παίκτη: ", JLabel.RIGHT);
            colorComboBox[i] = new JComboBox<>();
        }

        createComboBoxes();

        JPanel playersInputPanel = new JPanel(new GridLayout(7, 4));
        playersInputPanel.setBackground(backgroundColour);
        // Create formPanel in buttonsPanel.
        playersInputPanel.setBorder(BorderFactory.createTitledBorder(BorderFactory.createLineBorder(Color.black), " Στοιχεία Παίκτών \n(από 2 εώς 4) ", TitledBorder.CENTER, TitledBorder.TOP, new Font("MyFont", Font.TRUETYPE_FONT + Font.BOLD + Font.ITALIC, 15), Color.BLACK));

        // First Line.
        for (int i = 0; i < 4; i++) {
            playersInputPanel.add(Box.createGlue());
        }

        // Second Line.
        playersInputPanel.add(nicknameLabel[0]);
        playersInputPanel.add(nicknamTextField[0]);
        playersInputPanel.add(nicknameLabel[1]);
        playersInputPanel.add(nicknamTextField[1]);

        // Third Line.
        playersInputPanel.add(colorLabel[0]);
        playersInputPanel.add(colorComboBox[0]);
        playersInputPanel.add(colorLabel[1]);
        playersInputPanel.add(colorComboBox[1]);

        // Fourth line.
        for (int i = 0; i < 4; i++) {
            playersInputPanel.add(Box.createGlue());
        }

        // Fifth Line.
        playersInputPanel.add(nicknameLabel[2]);
        playersInputPanel.add(nicknamTextField[2]);
        playersInputPanel.add(nicknameLabel[3]);
        playersInputPanel.add(nicknamTextField[3]);

        // Sixeth Line.
        playersInputPanel.add(colorLabel[2]);
        playersInputPanel.add(colorComboBox[2]);
        playersInputPanel.add(colorLabel[3]);
        playersInputPanel.add(colorComboBox[3]);

        // Seventh & Last line.
        for (int i = 0; i < 4; i++) {
            playersInputPanel.add(Box.createGlue());
        }

        return playersInputPanel;
    }

    private JPanel createGameBoardSettingPanel() {

        JPanel settingsPanel = new JPanel(new GridLayout(5, 4));
        settingsPanel.setBackground(backgroundColour);
        // Create formPanel in buttonsPanel.
        settingsPanel.setBorder(BorderFactory.createTitledBorder(BorderFactory.createLineBorder(Color.black), "Στοιχεία Ταμπλό", TitledBorder.CENTER, TitledBorder.TOP, new Font("MyFont", Font.TRUETYPE_FONT + Font.BOLD + Font.ITALIC, 15), Color.BLACK));

        for (int i = 0; i < 4; i++) {
            settingsPanel.add(Box.createGlue());
        }
        settingsPanel.add(boardSizeLabel);
        settingsPanel.add(boardSizeNumComboBox);
        settingsPanel.add(backgoundColorLabel);
        settingsPanel.add(backgoundColorComboBox);
        settingsPanel.add(pawnNumLabel);
        settingsPanel.add(pawnNumComboBox);

        for (int i = 0; i < 4; i++) {
            settingsPanel.add(Box.createGlue());
        }

        settingsPanel.add(pawnNumLabel);
        settingsPanel.add(pawnNumComboBox);
        settingsPanel.add(Box.createGlue());
        submitButton.addActionListener(new SubmitListener());
        settingsPanel.add(submitButton);
        for (int i = 0; i < 4; i++) {
            settingsPanel.add(Box.createGlue());
        }

        return settingsPanel;
    }

    private JPanel createProgressPanel() {

        //Create progress Panel.
        JPanel progressPanel = new JPanel(new GridLayout(1, 1));
        progressPanel.setBackground(backgroundColour);
        progressPanel.setBorder(BorderFactory.createTitledBorder(BorderFactory.createLineBorder(Color.black), " Δημιουργία Ταμπλό ", TitledBorder.CENTER, TitledBorder.TOP, new Font("MyFont", Font.TRUETYPE_FONT + Font.BOLD + Font.ITALIC, 15), Color.BLACK));

        // Create Progress Bar.
        progressBar.setMaximumSize(new Dimension(500, 150));
        progressBar.setStringPainted(true);

        // Create progress Bar Panel.
        Box progressBarPanel = new Box(BoxLayout.Y_AXIS);
        progressBarPanel.add(Box.createRigidArea(new Dimension(20, 20)));
        progressBarPanel.add(progressBar);
        progressBarPanel.add(Box.createRigidArea(new Dimension(20, 20)));

        progressPanel.add(progressBarPanel);
        return progressPanel;

    }

    private JPanel createPressStartPanel() {

        try {
            pressStartLogo = ImageIO.read(new File(pressStartLogoPath));
        } catch (IOException ex) {
            ex.getMessage();
        }

        // Create press start panel.
        pressStartPanel = new JPanel(new BorderLayout());
        pressStartPanel.setBackground(backgroundColour);
        pressStartPanel.setVisible(false);
        pressStartPanel.setBorder(BorderFactory.createTitledBorder(BorderFactory.createLineBorder(Color.black), " Είσαι Έτοιμος/η ??? ", TitledBorder.CENTER, TitledBorder.TOP, new Font("MyFont", Font.TRUETYPE_FONT + Font.BOLD + Font.ITALIC, 15), Color.BLACK));

        JLabel pressStartLabel = new JLabel(new ImageIcon(pressStartLogo));

        pressStartPanel.add(pressStartLabel, BorderLayout.CENTER);

        return pressStartPanel;

    }

    /**
     * Clear all panel's field and enable them again.
     */
    public void clearAndEnableAll() {
        for (int i = 0; i < 4; i++) {
            nicknamTextField[i].setText("");
            nicknamTextField[i].setEnabled(true);
            colorComboBox[i].setEnabled(true);
        }

        // remove item listner and empty  combo boxes
        for (JComboBox colorComboBoxFE : colorComboBox) {
            colorComboBoxFE.removeItemListener(comboBoxItemListener);
            colorComboBoxFE.removeAllItems();
        }

        // full combo boxes with items.
        for (JComboBox colorComboBoxFE : colorComboBox) {

            colorComboBoxFE.addItem("Πράσινο");
            colorComboBoxFE.addItem("Κόκκινο");
            colorComboBoxFE.addItem("Κίτρινο");
            colorComboBoxFE.addItem("Μπλέ");
            colorComboBoxFE.setMaximumRowCount(4);
            colorComboBoxFE.setSelectedIndex(-1);
        }

        // add  item listner
        for (JComboBox colorComboBoxFE : colorComboBox) {
            colorComboBoxFE.addItemListener(comboBoxItemListener);
        }

        submitButton.setEnabled(true);
        boardSizeNumComboBox.setSelectedIndex(-1);
        boardSizeNumComboBox.setEnabled(true);
        pawnNumComboBox.setSelectedIndex(-1);
        pawnNumComboBox.setEnabled(true);
        backgoundColorComboBox.setSelectedIndex(-1);
        backgoundColorComboBox.setEnabled(true);
        progressBar.setStringPainted(false);
        progressBar.setValue(0);
        pressStartPanel.setVisible(false);

    }

    
    // GETTERS //
    
    public JTextField[] getNicknamTextField() {
        return nicknamTextField;
    }

    public JComboBox[] getColorComboBox() {
        return colorComboBox;
    }
    
    public JComboBox getBoardSizeNumComboBox() {
        return boardSizeNumComboBox;
    }

    public JComboBox getPawnNumComboBox() {
        return pawnNumComboBox;
    }

    public JComboBox getBackgoundColorComboBox() {
        return backgoundColorComboBox;
    }

    
    // SETTERS //
    
    public void setNicknamTextField(JTextField[] nicknamTextField) {
        this.nicknamTextField = nicknamTextField;
    }

    public void setColorComboBox(JComboBox[] colorComboBox) {
        this.colorComboBox = colorComboBox;
    }

    public void setBoardSizeNumComboBox(JComboBox boardSizeNumComboBox) {
        this.boardSizeNumComboBox = boardSizeNumComboBox;
    }

    public void setPawnNumComboBox(JComboBox pawnNumComboBox) {
        this.pawnNumComboBox = pawnNumComboBox;
    }

    public void setBackgoundColorComboBox(JComboBox backgoundColorComboBox) {
        this.backgoundColorComboBox = backgoundColorComboBox;
    }

    
    // LISTEENRS //
    
    /**
     * Submit Listener check if exist at least two players active,if have name players
     * with same name, if there are half completed player fields, creare a 3 sec delay
     * for full loading bar and if chose all game board settings.
     * Also after press disable all fields and enable Next/Star Button.
     */
    private class SubmitListener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent ae) {

            boolean checkFlag = false;
            int counter = 0;

            // Check if  secondd's panel all elements are compled.
            if (boardSizeNumComboBox.getSelectedIndex() != -1 && backgoundColorComboBox.getSelectedIndex() != -1 && pawnNumComboBox.getSelectedIndex() != -1) {
                for (int i = 0; i < 4; i++) {
                    if ((!nicknamTextField[i].getText().isEmpty()) && (colorComboBox[i].getSelectedIndex() != -1)) {
                        counter++;
                    }
                }

                if (counter < 2) {
                    JOptionPane.showMessageDialog(SettingsPanel.this, " Δεν υπάρχουν αρκετοί παίκτες", " Σφάλμα Συμπλήρωσης ", JOptionPane.ERROR_MESSAGE);

                }

                // At least two players.
                if (counter >= 2) {
                    checkFlag = true;
                    for (int i = 0; i < 4; i++) {
                        if ((!nicknamTextField[i].getText().isEmpty()) && (colorComboBox[i].getSelectedIndex() == -1)) {
                            checkFlag = false;
                        }
                        if ((nicknamTextField[i].getText().isEmpty()) && (colorComboBox[i].getSelectedIndex() != -1)) {
                            checkFlag = false;
                        }
                    }

                    if (checkFlag == false) {
                        JOptionPane.showMessageDialog(SettingsPanel.this, "Είτε έχεις όνομα χωρίς χρώμα είτε το αντίστροφο.", " Σφάλμα Συμπλήρωσης ", JOptionPane.ERROR_MESSAGE);
                    }

                    if (isUniqueUsersName() == false) {
                        checkFlag = false;
                        JOptionPane.showMessageDialog(SettingsPanel.this, " Δεν υπάρχει μοναδικότητα στα ονόματα.", " Σφάλμα Συμπλήρωσης ", JOptionPane.ERROR_MESSAGE);
                    }

                }

            } else {
                JOptionPane.showMessageDialog(SettingsPanel.this, " Δεν είναι συμπληρωμένα τα στοιχεία ταμπλό.", " Σφάλμα Συμπλήρωσης ", JOptionPane.ERROR_MESSAGE);

            }

            if (checkFlag == false) {
                wrongInput();

            } else {
                // Form is complet.
                progressBar.setStringPainted(true);

                // Disable fields.
                for (int i = 0; i < 4; i++) {
                    nicknamTextField[i].setEnabled(false);
                    colorComboBox[i].setEnabled(false);
                }

                boardSizeNumComboBox.setEnabled(false);
                pawnNumComboBox.setEnabled(false);
                backgoundColorComboBox.setEnabled(false);
                submitButton.setEnabled(false);

                // Run for ~3 seconds
                for (int i = 0; i < 3; i++) {
                    for (long stop = System.nanoTime() + TimeUnit.SECONDS.toNanos(1); stop > System.nanoTime();) {

                    }
                }
                progressBar.setValue(100);
                pressStartPanel.setVisible(true);
                nextButton.setEnabled(true);
                previousButton.setEnabled(false);

            }

        }

        private void wrongInput() {

            for (int i = 0; i < 4; i++) {
                nicknamTextField[i].setText("");
                colorComboBox[i].setSelectedIndex(-1);
            }
            nicknamTextField[0].requestFocus();

            // remove item listner and empty  combo boxes
            for (JComboBox colorComboBoxFE : colorComboBox) {
                colorComboBoxFE.removeItemListener(comboBoxItemListener);
                colorComboBoxFE.removeAllItems();
            }

            // full combo boxes with items.
            for (JComboBox colorComboBoxFE : colorComboBox) {

                colorComboBoxFE.addItem("Πράσινο");
                colorComboBoxFE.addItem("Κόκκινο");
                colorComboBoxFE.addItem("Κίτρινο");
                colorComboBoxFE.addItem("Μπλέ");
                colorComboBoxFE.setMaximumRowCount(4);
                colorComboBoxFE.setSelectedIndex(-1);
            }

            // add  item listner
            for (JComboBox colorComboBoxFE : colorComboBox) {
                colorComboBoxFE.addItemListener(comboBoxItemListener);
            }

        }

        private boolean isUniqueUsersName() {
            int counter;
            for (int i = 0; i < 4; i++) {
                counter = 0;
                if (!nicknamTextField[i].getText().isEmpty()) {
                    for (int j = 0; j < 4; j++) {
                        if (nicknamTextField[i].getText().compareTo(nicknamTextField[j].getText()) == 0) {
                            counter++;
                        }
                    }
                    if (counter >= 2) {
                        return false;
                    }
                }

            }

            return true;
        }
    }

    /**
     * ComboBoxItem Listener used for color selection when a player chooses one then 
     * will be deducted from the other players.
     */
    private class ComboBoxItemListener implements ItemListener {

        @Override
        public void itemStateChanged(ItemEvent e) {

            ItemSelectable is = e.getItemSelectable();

            JComboBox source = (JComboBox) is;
            String selectedItem = (String) source.getSelectedItem();

            for (JComboBox colorComboBox1 : colorComboBox) {
                if (colorComboBox1 != source) {
                    colorComboBox1.removeItem(selectedItem);
                }
            }

        }

    }
}
