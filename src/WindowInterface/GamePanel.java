package WindowInterface;

import GameBoardData.GameBoardMap;
import GameplayAndLogic.Gameplay;
import GameplayAndLogic.Player;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.border.TitledBorder;

/**
 * GamePanel class is used for the appearance of game logs, the rounds, who is now 
 * playing what color have, the game board grid, the dice button and the finish move
 * button(used for skip moves to verify the functionality by examiner).
 *
 * @author Πλέσσιας Αλέξανδρος (ΑΜ.:2025201100068).
 */
public class GamePanel extends JPanel {

    // Basic class fields,
    private static Color background_color;
    private final int square_size;
    private static int gameBoard_length;
    private int roundCounter=-1;

    // Play stop Paths and Icons set.
    private final String playImgPath = "Icons" + File.separator + "Status" + File.separator + "play.png";
    private final String stopImgPath = "Icons" + File.separator + "Status" + File.separator + "stop.png";
    private Icon playImg = new ImageIcon(playImgPath);
    private Icon stopImg = new ImageIcon(stopImgPath);

    // Game etc.
    private GameBoardMap gameBoardMap;
    private Gameplay gameplay;

    // Log Panel.
    private JTextArea infoJTextArea = new JTextArea(5, 0);
    private JScrollPane infoJTextAreascrollPane = new JScrollPane(infoJTextArea);

    // Round and Play now.
    private JLabel roundNumJLabel;
    private JPanel nowPlayJPanel;
    private JCheckBox firstPLayerJCheckBox = null;
    private JCheckBox secondPLayerJCheckBox = null;
    private JCheckBox thirdPLayerJCheckBox = null;
    private JCheckBox fourthPLayerJCheckBox = null;

    // Dice & Finish Box.
    private JPanel dicePanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
    private JPanel diceImgPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
    private JButton finishMoveJButton = new JButton("   Εκκίνηση   ");
    private JButton diceButton = new JButton(" Ρίξε το ζάρι ");
    private JLabel diceImgLabel = new JLabel();
    
    /**
     * Constructor of GamePanel set background color and the use of
     * ancillary methods for the creation of panel.
     *
     * @param square_size The size of center square.
     * @param background_color The backgound color.
     * @param players The list of players.
     */
    public GamePanel(int square_size, Color background_color, Player[] players) {

        this.square_size = square_size;
        this.background_color = background_color;
        this.gameBoard_length = square_size * 2 + 3;
        this.gameBoardMap = new GameBoardMap(this.square_size);
        setLayout(new BorderLayout());
        setBackground(this.background_color);

        // Create a new Gameplay !!!
        gameplay = new Gameplay(players, gameBoardMap, this);

        JPanel gameBoardPanel = createGameBoardPanel();
        JPanel logsJPanel = createInfoPanel();
        JPanel roundAndPlayNowJPanel = createRoundAndPlayNowPanel();
        Box diceAndFinishBox = createDiceAndFinishMovePanel();

        fixColors();

        add(logsJPanel, BorderLayout.PAGE_START);
        add(roundAndPlayNowJPanel, BorderLayout.LINE_START);
        add(gameBoardPanel, BorderLayout.CENTER);
        add(diceAndFinishBox, BorderLayout.LINE_END);

    }

    
    // UTILITIES METHODS //
    
    private JPanel createGameBoardPanel() {

        JPanel gameBoardPanel = new JPanel(new GridLayout(gameBoard_length, gameBoard_length));
        gameBoardPanel.setBackground(background_color);

        for (int i = 1; i <= gameBoard_length*gameBoard_length; i++) {   
            gameBoardPanel.add(this.gameBoardMap.getCell(i));  
        }
        return gameBoardPanel;
    }

    private JPanel createInfoPanel() {

        JPanel logJPanel = new JPanel(new GridLayout(1, 0));

        // Info area.
        Font myFont = new Font("MyFont", Font.ROMAN_BASELINE, 16);
        infoJTextArea.setFont(myFont);
        infoJTextArea.setLineWrap(true);
        infoJTextArea.setWrapStyleWord(true);
        infoJTextArea.setEditable(false);

        infoJTextAreascrollPane = new JScrollPane(infoJTextArea);
        infoJTextAreascrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
        infoJTextAreascrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
        infoJTextAreascrollPane.setBackground(background_color);

        logJPanel.add(infoJTextAreascrollPane);

        return logJPanel;
    }

    private JPanel createRoundAndPlayNowPanel() {

        nowPlayJPanel = new JPanel(new BorderLayout(10, 10));
        nowPlayJPanel.add(Box.createRigidArea(new Dimension(150, 100)));

        // Round Label.
        Font RoundFont = new Font("RoundFont", Font.ROMAN_BASELINE, 20);
        roundNumJLabel = new JLabel("ROUND: ");
        roundNumJLabel.setEnabled(false);
        roundNumJLabel.setFont(RoundFont);

        // Players area and add checkboxes.
        Box playersPanel = new Box(BoxLayout.Y_AXIS);
        playersPanel.add(Box.createVerticalGlue());
        playersPanel.add(Box.createRigidArea(new Dimension(14, 0)));
        playersPanel.add(roundNumJLabel);

        
        Font playersFont = new Font("playersFont", Font.ROMAN_BASELINE, 18);
        // Now play.
        // Create checkboxes.
        for (int i = 0; i < gameplay.getPlayers().size(); i++) {
            if (i == 0) {
                firstPLayerJCheckBox = new JCheckBox(" " + gameplay.getPlayers().get(i).getNickname());
                firstPLayerJCheckBox.setPreferredSize(new Dimension(150, 25));

                if ((gameplay.getPlayers().get(i).getColor() == Color.yellow) && (this.background_color == Color.white)) {
                    firstPLayerJCheckBox.setForeground(new Color(237, 181, 12));
                } else if ((gameplay.getPlayers().get(i).getColor() == Color.green) && (this.background_color == Color.white)) {
                    firstPLayerJCheckBox.setForeground(new Color(51, 153, 0));
                } else if ((gameplay.getPlayers().get(i).getColor() == Color.blue) && (this.background_color == Color.black)) {
                    firstPLayerJCheckBox.setForeground(new Color(51 , 51  , 255));
                }else {
                    firstPLayerJCheckBox.setForeground(gameplay.getPlayers().get(i).getColor());
                }

                firstPLayerJCheckBox.setFont(playersFont);
                firstPLayerJCheckBox.setBackground(this.background_color);
                firstPLayerJCheckBox.setIcon(stopImg);

            } else if (i == 1) {
                secondPLayerJCheckBox = new JCheckBox(" " + gameplay.getPlayers().get(i).getNickname());
                secondPLayerJCheckBox.setPreferredSize(new Dimension(150, 25));

                if ((gameplay.getPlayers().get(i).getColor() == Color.yellow) && (this.background_color == Color.white)) {
                    secondPLayerJCheckBox.setForeground(new Color(237, 181, 12));
                } else if ((gameplay.getPlayers().get(i).getColor() == Color.green) && (this.background_color == Color.white)) {
                    secondPLayerJCheckBox.setForeground(new Color(51, 153, 0));
                }else if ((gameplay.getPlayers().get(i).getColor() == Color.blue) && (this.background_color == Color.black)) {
                    secondPLayerJCheckBox.setForeground(new Color(51 , 51  , 255));
                } else {
                    secondPLayerJCheckBox.setForeground(gameplay.getPlayers().get(i).getColor());
                }

                secondPLayerJCheckBox.setFont(playersFont);
                secondPLayerJCheckBox.setBackground(this.background_color);
                secondPLayerJCheckBox.setIcon(stopImg);

            } else if (i == 2) {
                thirdPLayerJCheckBox = new JCheckBox(" " + gameplay.getPlayers().get(i).getNickname());
                thirdPLayerJCheckBox.setPreferredSize(new Dimension(150, 25));
                if ((gameplay.getPlayers().get(i).getColor() == Color.yellow) && (this.background_color == Color.white)) {
                    thirdPLayerJCheckBox.setForeground(new Color(237, 181, 12));
                } else if ((gameplay.getPlayers().get(i).getColor() == Color.green) && (this.background_color == Color.white)) {
                    thirdPLayerJCheckBox.setForeground(new Color(51, 153, 0));
                } else if ((gameplay.getPlayers().get(i).getColor() == Color.blue) && (this.background_color == Color.black)) {
                    thirdPLayerJCheckBox.setForeground(new Color(51 , 51  , 255));
                } else {
                    thirdPLayerJCheckBox.setForeground(gameplay.getPlayers().get(i).getColor());
                }
                thirdPLayerJCheckBox.setFont(playersFont);
                thirdPLayerJCheckBox.setBackground(this.background_color);
                thirdPLayerJCheckBox.setIcon(stopImg);
            } else if(i==3) { //  
                fourthPLayerJCheckBox = new JCheckBox(" " + gameplay.getPlayers().get(i).getNickname());
                fourthPLayerJCheckBox.setPreferredSize(new Dimension(150, 25));
                if ((gameplay.getPlayers().get(i).getColor() == Color.yellow) && (this.background_color == Color.white)) {
                    fourthPLayerJCheckBox.setForeground(new Color(237, 181, 12));
                } else if ((gameplay.getPlayers().get(i).getColor() == Color.green) && (this.background_color == Color.white)) {
                    fourthPLayerJCheckBox.setForeground(new Color(51, 153, 0));
                }else if ((gameplay.getPlayers().get(i).getColor() == Color.blue) && (this.background_color == Color.black)) {
                    fourthPLayerJCheckBox.setForeground(new Color(51 , 51  , 255));
                } else {
                    fourthPLayerJCheckBox.setForeground(gameplay.getPlayers().get(i).getColor());
                }
                fourthPLayerJCheckBox.setFont(playersFont);
                fourthPLayerJCheckBox.setBackground(this.background_color);
                fourthPLayerJCheckBox.setIcon(stopImg);
            }
        }

        playersPanel.add(Box.createVerticalGlue());

        for (int i = 0; i < gameplay.getPlayers().size(); i++) {
            if (i == 0) {
                playersPanel.add(Box.createRigidArea(new Dimension(15, 15)));
                playersPanel.add(firstPLayerJCheckBox);

            } else if (i == 1) {
                playersPanel.add(Box.createRigidArea(new Dimension(15, 15)));
                playersPanel.add(secondPLayerJCheckBox);

            } else if (i == 2) {
                playersPanel.add(Box.createRigidArea(new Dimension(15, 15)));
                playersPanel.add(thirdPLayerJCheckBox);

            } else if (i == 3) { //
                playersPanel.add(Box.createRigidArea(new Dimension(15, 15)));
                playersPanel.add(fourthPLayerJCheckBox);
            }
        }
        playersPanel.add(Box.createVerticalGlue());

        nowPlayJPanel.add(playersPanel, BorderLayout.CENTER);

        return nowPlayJPanel;

    }

    private Box createDiceAndFinishMovePanel() {

        Box diceBox = new Box(BoxLayout.Y_AXIS);
        diceBox.add(Box.createRigidArea(new Dimension(150, 100)));

        // Dice Area.
        dicePanel.setBackground(this.background_color);
        diceButton.doClick();
        
        diceButton.setEnabled(false);
        diceButton.addActionListener(new DiceListener());
        dicePanel.add(diceButton);

        diceImgPanel.setBackground(this.background_color);
        diceButton.setPreferredSize(finishMoveJButton.getPreferredSize());
        finishMoveJButton.setToolTipText("Υπάρχει για λόγους ελέγχου της λειτουργικότητας από τον εξεταστή. ");
        finishMoveJButton.addActionListener(new FinishMoveListener());
        diceImgPanel.add(diceImgLabel);

        // Finish Panel
        JPanel finishMoveJPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        finishMoveJPanel.setBackground(background_color);
        finishMoveJPanel.add(finishMoveJButton);

        diceBox.add(Box.createVerticalGlue());
        diceBox.add(dicePanel);
        diceBox.add(Box.createVerticalGlue());
        diceBox.add(diceImgPanel);
        diceBox.add(Box.createVerticalGlue());
        diceBox.add(finishMoveJPanel);
        diceBox.add(Box.createVerticalGlue());

        return diceBox;
    }

    /**
     * Fix color contrast in game logs area.
     */
    private void fixColors() {

        infoJTextArea.setBackground(background_color);
        roundNumJLabel.setBackground(background_color);
        nowPlayJPanel.setBackground(background_color);
        diceButton.setBackground(background_color);
        finishMoveJButton.setBackground(background_color);

        if (background_color == Color.black) {
            infoJTextArea.setForeground(Color.white);
            roundNumJLabel.setForeground(Color.white);
            nowPlayJPanel.setForeground(Color.white);
            diceButton.setForeground(Color.white);
            finishMoveJButton.setForeground(Color.white);
            infoJTextAreascrollPane.setBorder(BorderFactory.createTitledBorder(BorderFactory.createLineBorder(Color.white), " Game logs ", TitledBorder.CENTER, TitledBorder.TOP, new Font("MyFont", Font.TRUETYPE_FONT + Font.BOLD + Font.ITALIC, 15), Color.white));
        } else {
            infoJTextArea.setForeground(Color.black);
            roundNumJLabel.setForeground(Color.black);
            nowPlayJPanel.setForeground(Color.black);
            diceButton.setForeground(Color.black);
            finishMoveJButton.setForeground(Color.black);
            infoJTextAreascrollPane.setBorder(BorderFactory.createTitledBorder(BorderFactory.createLineBorder(Color.black), " Game logs ", TitledBorder.CENTER, TitledBorder.TOP, new Font("MyFont", Font.TRUETYPE_FONT + Font.BOLD + Font.ITALIC, 15), Color.black));
        }

    }

    
    // GETTERS //
    
    public int getSquare_size() {
        return square_size;
    }

    public int getGameBoard_length() {
        return gameBoard_length;
    }

    public int getRoundCounter() {
        return roundCounter;
    }

    public Gameplay getGameplay() {
        return gameplay;
    }

    public JTextArea getInfoJTextArea() {
        return infoJTextArea;
    }

    public JButton getDiceButton() {
        return diceButton;
    }

    public JLabel getDiceImgLabel() {
        return diceImgLabel;
    }

    public JButton getFinishMoveJButton() {
        return finishMoveJButton;
    }

    
    // LISTENERS //
    
    /**
     * Dice Listener roll dice check if have available moves and if you have
     * activate player's pawn listeners, disable finishmove button and if you
     * haven't moves just click finishmove button automatically.
     */
    private class DiceListener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent ae) {
            
            
            gameplay.getPlayersDice().diceRoll(GamePanel.this);
            diceButton.setEnabled(false);
            diceImgLabel.setIcon(gameplay.getPlayersDice().getDiceIcon());
            
            // if have valiv moves
            if(gameplay.checkValidMoves(roundCounter%(gameplay.getPlayers().size()))){
                gameplay.activeMouseGuide(roundCounter%(gameplay.getPlayers().size()));
                finishMoveJButton.setEnabled(true);
            }else {
                finishMoveJButton.setEnabled(true);
                finishMoveJButton.doClick();    
                finishMoveJButton.setEnabled(false);    
            }
        }

    }
    
    /**
     * FinishMove Listener used for increase round counter, clear all Cells
     * listeners, show who plays now and activate only Cells with pawns of
     * current player.
     */
    private class FinishMoveListener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent ae) {
            if(roundCounter==-1){
                finishMoveJButton.setText("Τέλος Κίνησης");
                diceButton.setPreferredSize(finishMoveJButton.getPreferredSize());
            }
            diceImgLabel.setIcon(null);
            finishMoveJButton.setEnabled(false);
            
            gameplay.disableMouseGuide();
            
            // INCREASE ROUND
            roundCounter++;
            
            // Change roundlabel & Enable.
            roundNumJLabel.setText("ROUND: "+(roundCounter+1)+"");
            roundNumJLabel.setEnabled(true);
            
            // System msg about who plays.
            gameplay.nowPlayMsgUpdate(roundCounter%(gameplay.getPlayers().size()));
            
            // Change playnow images.
            for (int i=0;i<gameplay.getPlayers().size();i++){
                
                if(roundCounter%(gameplay.getPlayers().size())==i){
                    if(i==0){
                        firstPLayerJCheckBox.setIcon(playImg);
                    }else if (i==1){
                        secondPLayerJCheckBox.setIcon(playImg);
                    }else if (i==2){
                        thirdPLayerJCheckBox.setIcon(playImg);
                    }else if  (i==3) { // i==3
                        fourthPLayerJCheckBox.setIcon(playImg);
                    }
                }else{
                    if(i==0){
                        firstPLayerJCheckBox.setIcon(stopImg);
                    }else if (i==1){
                        secondPLayerJCheckBox.setIcon(stopImg);
                    }else if (i==2){
                        thirdPLayerJCheckBox.setIcon(stopImg);
                    }else if (i==3){ // i==3
                        fourthPLayerJCheckBox.setIcon(stopImg);
                    }
                }
            }
            gameplay.onCurrentOffOther(roundCounter%(gameplay.getPlayers().size()));
            diceButton.setEnabled(true);
            diceImgLabel.setVisible(true);
        }

    }

}
