package GameplayAndLogic;

import Cells.DecorativeCell;
import Cells.FinalCell;
import Cells.InitCell;
import Cells.StarCell;
import GameBoardData.GameBoardMap;
import WindowInterface.GamePanel;
import java.awt.Color;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;
import java.util.Iterator;
import javax.swing.BorderFactory;
import javax.swing.JOptionPane;
import javax.swing.border.Border;
import javax.swing.border.LineBorder;

/**
 * Gameplay class is responsible for the proper operation of the game, for the
 * respect of the rules, the graphical updating of gameBoard with gamePanel, the
 * update of game log area, about who win the game, the right set of players,
 * when a player has a move available or not, if the move is chosen if it is
 * valid, if can play again or not, when can roll the dice and when round must
 * change and more.
 *
 * @author Πλέσσιας Αλέξανδρος (ΑΜ.:2025201100068).
 */
public class Gameplay {

    // Basic class fields.
    private ArrayList<Player> players;
    private Dice gameDice;
    private GamePanel gamePanel;
    private GameBoardMap gameBoardMap;
    private int gameBoardSize;
    private int squareSize;

    // Init pawns positions
    int[] greenInitPos;
    int[] redInitPos;
    int[] yellowInitPos;
    int[] blueInitPos;

    // Start positions.
    int greenStart;
    int redStart;
    int yellowStart;
    int blueStart;

    // Game paths.
    ArrayList<Integer> greenPathList;
    ArrayList<Integer> redPathList;
    ArrayList<Integer> yellowPathList;
    ArrayList<Integer> bluePathList;

    /**
     * Constractor of Gameplay create the dice generate start, init, path
     * positions, shuffle players, set all pawn to right init positions and
     * start the game log showing the sequence of players.
     *
     * @param playersTable All active players of game.
     * @param gameBoardMap The data/cell stacture structure.
     * @param gamePanel The game Panel.
     */
    public Gameplay(Player[] playersTable, GameBoardMap gameBoardMap, GamePanel gamePanel) {

        // Create arraylist with players
        this.players = new ArrayList<>(playersTable.length);
        this.players.addAll(Arrays.asList(playersTable));
        this.gameBoardMap = gameBoardMap;
        this.gamePanel = gamePanel;

        // Game dice
        this.gameDice = new Dice();

        // Initialize gameBoardSize & squareSize
        this.gameBoardSize = this.gamePanel.getGameBoard_length();
        this.squareSize = this.gamePanel.getSquare_size();

        // Initialize init positions, start positions and game paths, created only for active colors
        for (Player player : players) {
            if (player.getColor() == Color.green) {
                greenInitPos = new int[]{gameBoardSize + 2, gameBoardSize + (squareSize - 1), gameBoardSize * (squareSize - 2) + 2, (gameBoardSize * (squareSize - 2)) + (squareSize - 1)};
                greenStart = (gameBoardSize * squareSize) + 2;
                greenPathList = greenPath(greenStart);
            } else if (player.getColor() == Color.red) {
                redInitPos = new int[]{gameBoardSize + squareSize + 3 + 2, gameBoardSize * 2 - 1, gameBoardSize * (squareSize - 2) + squareSize + 3 + 2, gameBoardSize * (squareSize - 1) - 1};
                redStart = gameBoardSize + squareSize + 3;
                redPathList = redPath(redStart);
            } else if (player.getColor() == Color.yellow) {
                yellowInitPos = new int[]{gameBoardSize * (squareSize + 3 + 1) + 2, gameBoardSize * (squareSize + 3 + 1) + (squareSize - 1), gameBoardSize * (gameBoardSize - 2) + 2, gameBoardSize * (gameBoardSize - 2) + (squareSize - 1)};
                yellowStart = (gameBoardSize * (gameBoardSize - 2)) + (squareSize + 1);
                yellowPathList = yellowPath(yellowStart);
            } else {    // (players.get(i).getColor() ==Color.blue) 
                blueInitPos = new int[]{(gameBoardSize * (squareSize + 3 + 1)) + squareSize + 3 + 2, gameBoardSize * (squareSize + 3 + 2) - 1, gameBoardSize * (gameBoardSize - 2) + (squareSize + 3 + 2), gameBoardSize * (gameBoardSize - 1) - 1};
                blueStart = (gameBoardSize * (squareSize + 3)) - 1;
                bluePathList = bluePath(blueStart);
            }
        }

        // Shuffle players for create turns.
        Collections.shuffle(this.players);

        // Initialize each player's pawns.
        initPawns(this.players.size(), greenInitPos, redInitPos, yellowInitPos, blueInitPos);

        // First info messages for game logs.
        gamePanel.getInfoJTextArea().setText("[Σύστημα]: Γίνεται τυχαία επιλογή σειράς.");
        for (int i = 0; i < this.players.size(); i++) {
            String oldText = gamePanel.getInfoJTextArea().getText();
            String newText = "\n[Σύστημα]: Ο " + (i + 1) + "ος Παίκτης θα ειναι ο " + this.players.get(i).getNickname() + " με χρώμα " + this.players.get(i).getColorText() + ".";
            gamePanel.getInfoJTextArea().setText(oldText + newText);
        }

    }

    // GETTERS //
    /**
     * @return The game dice.
     */
    public Dice getPlayersDice() {
        return gameDice;
    }

    /**
     * @return Arraylist with all players.
     */
    public ArrayList<Player> getPlayers() {
        return players;
    }

    // PATHS OF COLORS AND INITIALIZE PAWNS //
    /**
     * Pawns of each player is added to the appropriate init posiotion.
     *
     * @param playersNum The number of active players.
     * @param greenInitPos // The arraylist with init poitions for green pawns.
     * @param redInitPos // The arraylist with init poitions for red pawns.
     * @param yellowInitPos // The arraylist with init poitions for yellow
     * pawns.
     * @param blueInitPos // The arraylist with init poitions for blue pawns.
     */
    private void initPawns(int playersNum, int[] greenInitPos, int[] redInitPos, int[] yellowInitPos, int[] blueInitPos) {

        for (int i = 0; i < playersNum; i++) {
            if (players.get(i).getColor() == Color.green) {
                for (int j = 0; j < players.get(i).getPanws().size(); j++) {
                    players.get(i).setPanws(j, greenInitPos[j]); // give number
                    gameBoardMap.getCell(greenInitPos[j]).addPawn(players.get(i).getPawn(j));
                }
            } else if (players.get(i).getColor() == Color.red) {
                for (int j = 0; j < players.get(i).getPanws().size(); j++) {
                    players.get(i).setPanws(j, redInitPos[j]);
                    gameBoardMap.getCell(redInitPos[j]).addPawn(players.get(i).getPawn(j));
                }
            } else if (players.get(i).getColor() == Color.yellow) {
                for (int j = 0; j < players.get(i).getPanws().size(); j++) {
                    players.get(i).setPanws(j, yellowInitPos[j]);
                    gameBoardMap.getCell(yellowInitPos[j]).addPawn(players.get(i).getPawn(j));
                }
            } else { // players.get(i).getColor()==Color.blue
                for (int j = 0; j < players.get(i).getPanws().size(); j++) {
                    players.get(i).setPanws(j, blueInitPos[j]);
                    gameBoardMap.getCell(blueInitPos[j]).addPawn(players.get(i).getPawn(j));
                }
            }
        }

    }

    /**
     * Creation of the path with the help of a "virtual transition" from the
     * first cell to the last Cell.
     *
     * @param greenStart The first cell of path.
     * @return ArrayList containing the path having to cross the green Pawns.
     */
    private ArrayList<Integer> greenPath(int greenStart) {

        ArrayList<Integer> greenPathArraylist = new ArrayList<>();
        greenPathArraylist.add(greenStart);

        for (int i = 0; i < squareSize - 2; i++) {
            greenStart++;
            greenPathArraylist.add(greenStart);
        }
        greenStart++;
        for (int i = 0; i < squareSize; i++) {
            greenStart = greenStart - gameBoardSize;
            greenPathArraylist.add(greenStart);
        }
        greenStart++;
        greenPathArraylist.add(greenStart);

        greenStart++;
        greenPathArraylist.add(greenStart);
        for (int i = 0; i < squareSize - 1; i++) {
            greenStart = greenStart + gameBoardSize;
            greenPathArraylist.add(greenStart);
        }
        greenStart = greenStart + gameBoardSize;
        for (int i = 0; i < squareSize; i++) {
            greenStart++;
            greenPathArraylist.add(greenStart);
        }

        greenStart = greenStart + gameBoardSize;
        greenPathArraylist.add(greenStart);

        greenStart = greenStart + gameBoardSize;
        for (int i = 0; i < squareSize; i++) {
            greenPathArraylist.add(greenStart);
            greenStart--;
        }

        for (int i = 0; i < squareSize; i++) {
            greenStart = greenStart + gameBoardSize;
            greenPathArraylist.add(greenStart);
        }
        greenStart--;
        greenPathArraylist.add(greenStart);

        greenStart--;
        greenPathArraylist.add(greenStart);

        for (int i = 0; i < squareSize - 1; i++) {
            greenStart = greenStart - gameBoardSize;
            greenPathArraylist.add(greenStart);
        }
        greenStart = greenStart - gameBoardSize;
        for (int i = 0; i < squareSize; i++) {
            greenStart--;
            greenPathArraylist.add(greenStart);
        }
        greenStart = greenStart - gameBoardSize;
        greenPathArraylist.add(greenStart);

        for (int i = 0; i < squareSize; i++) {

            greenStart++;
            greenPathArraylist.add(greenStart);
        }
        return greenPathArraylist;
    }

    /**
     * Creation of the red path with the help of a "virtual transition" from the
     * first cell to the last Cell.
     *
     * @param redStart The first red cell of path.
     * @return ArrayList containing the path having to cross the green Pawns.
     */
    private ArrayList<Integer> redPath(int redStart) {

        ArrayList<Integer> redPathArraylist = new ArrayList<>();
        redPathArraylist.add(redStart);

        for (int i = 0; i < squareSize - 2; i++) {
            redStart = redStart + gameBoardSize;
            redPathArraylist.add(redStart);

        }
        redStart = redStart + gameBoardSize;
        for (int i = 0; i < squareSize; i++) {
            redStart++;
            redPathArraylist.add(redStart);

        }

        redStart = redStart + gameBoardSize;
        redPathArraylist.add(redStart);

        redStart = redStart + gameBoardSize;
        redPathArraylist.add(redStart);

        for (int i = 0; i < squareSize - 1; i++) {
            redStart--;
            redPathArraylist.add(redStart);

        }
        redStart--;
        for (int i = 0; i < squareSize; i++) {
            redStart = redStart + gameBoardSize;
            redPathArraylist.add(redStart);

        }
        redStart--;
        redPathArraylist.add(redStart);

        redStart--;
        redPathArraylist.add(redStart);
        for (int i = 0; i < squareSize - 1; i++) {
            redStart = redStart - gameBoardSize;
            redPathArraylist.add(redStart);
        }
        redStart = redStart - gameBoardSize;
        for (int i = 0; i < squareSize; i++) {
            redStart--;
            redPathArraylist.add(redStart);
        }
        redStart = redStart - gameBoardSize;
        redPathArraylist.add(redStart);

        redStart = redStart - gameBoardSize;
        redPathArraylist.add(redStart);
        for (int i = 0; i < squareSize - 1; i++) {
            redStart++;
            redPathArraylist.add(redStart);
        }
        redStart++;
        for (int i = 0; i < squareSize; i++) {
            redStart = redStart - gameBoardSize;
            redPathArraylist.add(redStart);
        }

        redStart++;
        redPathArraylist.add(redStart);

        for (int i = 0; i < squareSize; i++) {
            redStart = redStart + gameBoardSize;
            redPathArraylist.add(redStart);
        }

        return redPathArraylist;
    }

    /**
     * Creation of the yellow path with the help of a "virtual transition" from
     * the first cell to the last Cell.
     *
     * @param yellowStart The first yellow cell of path.
     * @return ArrayList containing the path having to cross the green Pawns.
     */
    private ArrayList<Integer> yellowPath(int yellowStart) {

        ArrayList<Integer> yellowPathArraylist = new ArrayList<>();
        yellowPathArraylist.add(yellowStart);

        for (int i = 0; i < squareSize - 2; i++) {
            yellowStart = yellowStart - gameBoardSize;
            yellowPathArraylist.add(yellowStart);
        }

        yellowStart = yellowStart - gameBoardSize;
        for (int i = 0; i < squareSize; i++) {
            yellowStart--;
            yellowPathArraylist.add(yellowStart);
        }

        yellowStart = yellowStart - gameBoardSize;
        yellowPathArraylist.add(yellowStart);
        yellowStart = yellowStart - gameBoardSize;
        yellowPathArraylist.add(yellowStart);
        for (int i = 0; i < squareSize - 1; i++) {
            yellowStart++;
            yellowPathArraylist.add(yellowStart);
        }
        yellowStart++;
        for (int i = 0; i < squareSize; i++) {
            yellowStart = yellowStart - gameBoardSize;
            yellowPathArraylist.add(yellowStart);
        }
        yellowStart++;
        yellowPathArraylist.add(yellowStart);
        yellowStart++;
        yellowPathArraylist.add(yellowStart);

        for (int i = 0; i < squareSize - 1; i++) {
            yellowStart = yellowStart + gameBoardSize;
            yellowPathArraylist.add(yellowStart);
        }
        yellowStart = yellowStart + gameBoardSize;
        for (int i = 0; i < squareSize; i++) {
            yellowStart++;
            yellowPathArraylist.add(yellowStart);
        }
        yellowStart = yellowStart + gameBoardSize;
        yellowPathArraylist.add(yellowStart);
        yellowStart = yellowStart + gameBoardSize;
        yellowPathArraylist.add(yellowStart);
        for (int i = 0; i < squareSize - 1; i++) {
            yellowStart--;
            yellowPathArraylist.add(yellowStart);
        }
        yellowStart--;
        for (int i = 0; i < squareSize; i++) {
            yellowStart = yellowStart + gameBoardSize;
            yellowPathArraylist.add(yellowStart);
        }
        yellowStart--;
        yellowPathArraylist.add(yellowStart);
        for (int i = 0; i < squareSize; i++) {
            yellowStart = yellowStart - gameBoardSize;
            yellowPathArraylist.add(yellowStart);
        }

        return yellowPathArraylist;

    }

    /**
     * Creation of the blue path with the help of a "virtual transition" from
     * the first cell to the last Cell.
     *
     * @param blueStart The first blue cell of path.
     * @return ArrayList containing the path having to cross the green Pawns.
     */
    private ArrayList<Integer> bluePath(int blueStart) {

        ArrayList<Integer> bluePathArraylist = new ArrayList<>();
        bluePathArraylist.add(blueStart);

        for (int i = 0; i < squareSize - 2; i++) {
            blueStart--;
            bluePathArraylist.add(blueStart);
        }

        blueStart--;

        for (int i = 0; i < squareSize; i++) {
            blueStart = blueStart + gameBoardSize;
            bluePathArraylist.add(blueStart);
        }
        blueStart--;
        bluePathArraylist.add(blueStart);
        blueStart--;
        bluePathArraylist.add(blueStart);

        for (int i = 0; i < squareSize - 1; i++) {
            blueStart = blueStart - gameBoardSize;
            bluePathArraylist.add(blueStart);
        }

        blueStart = blueStart - gameBoardSize;

        for (int i = 0; i < squareSize; i++) {
            blueStart--;
            bluePathArraylist.add(blueStart);
        }

        blueStart = blueStart - gameBoardSize;
        bluePathArraylist.add(blueStart);
        blueStart = blueStart - gameBoardSize;
        bluePathArraylist.add(blueStart);
        for (int i = 0; i < squareSize - 1; i++) {
            blueStart++;
            bluePathArraylist.add(blueStart);
        }
        blueStart++;
        for (int i = 0; i < squareSize; i++) {
            blueStart = blueStart - gameBoardSize;
            bluePathArraylist.add(blueStart);
        }
        blueStart++;
        bluePathArraylist.add(blueStart);
        blueStart++;
        bluePathArraylist.add(blueStart);
        for (int i = 0; i < squareSize - 1; i++) {
            blueStart = blueStart + gameBoardSize;
            bluePathArraylist.add(blueStart);
        }
        blueStart = blueStart + gameBoardSize;

        for (int i = 0; i < squareSize; i++) {
            blueStart++;
            bluePathArraylist.add(blueStart);
        }
        blueStart = blueStart + gameBoardSize;
        bluePathArraylist.add(blueStart);

        for (int i = 0; i < squareSize; i++) {
            blueStart--;
            bluePathArraylist.add(blueStart);
        }

        return bluePathArraylist;

    }

    // MESSAGES UPDATES //
    /**
     * Update InfoJTextArea about who is playnig now.
     *
     * @param playerNum The player who is playing now.
     */
    public void nowPlayMsgUpdate(int playerNum) {
        String oldText = this.gamePanel.getInfoJTextArea().getText();
        String newText = ("\n[Σύστημα]: Τώρα παίζει ο " + players.get(playerNum).getNickname() + ".");
        gamePanel.getInfoJTextArea().setText(oldText + newText);
    }

    /**
     * Update InfoJTextArea about the moves.
     *
     * @param player The player who made ​​the move.
     * @param pawnNum The player Pawn number.
     * @param startPos The start position
     * @param finishPos The finish position.
     */
    public void movePlayMsgUpdate(Player player, int pawnNum, int startPos, int finishPos) {
        String oldText = this.gamePanel.getInfoJTextArea().getText();
        String newText = null;
        if (startPos == -1) {
            newText = ("\n[" + player.getNickname() + "]: Έβγαλα το πιόνι " + (pawnNum + 1) + " από την αφετηρία.");
        } else {
            newText = ("\n[" + player.getNickname() + "]: Μετακίνησα το πιόνι " + (pawnNum + 1) + " από την θέση " + startPos + " στην θέση " + finishPos + ".");
        }

        this.gamePanel.getInfoJTextArea().setText(oldText + newText);
    }

    /**
     * Update InfoJTextArea about Star moves.
     *
     * @param player The player who made the Star move.
     * @param finishPos The finish(Next Star) position.
     */
    public void starMoveMsgUpdate(Player player, int finishPos) {
        String oldText = this.gamePanel.getInfoJTextArea().getText();
        String newText = ("\n[Σύστημα]: Ο " + player.getNickname() + " μεταφέρθηκε στο επομενο StarCell στην θέση " + finishPos + ".");

        this.gamePanel.getInfoJTextArea().setText(oldText + newText);
    }

    /**
     * Update InfoJTextArea about who caught the pawn of other players and the
     * number of pawn.
     *
     * @param player The player who caught the pawn of the the rival.
     * @param catchPlayer The player who has caught.
     * @param catchPawnNum The number of the pawn which is caught.
     */
    public void catchMsgUpdate(Player player, Player catchPlayer, int catchPawnNum) {
        String oldText = this.gamePanel.getInfoJTextArea().getText();
        String newText = ("\n[" + player.getNickname() + "]: Έπιασα το πιόνι " + (catchPawnNum + 1) + " του " + catchPlayer.getNickname() + ".");

        this.gamePanel.getInfoJTextArea().setText(oldText + newText);
    }

    /**
     * Update InfoJTextArea about the player who play again.
     *
     * @param player The player who play again.
     * @param codeCom if 0 for outside of init area(with 5) else for dice = 6.
     */
    public void playAgainMsgUpdate(Player player, int codeCom) {
        String oldText = this.gamePanel.getInfoJTextArea().getText();
        String newText;

        if (codeCom == 0) {
            newText = ("\n[Σύστημα]: Ο " + player.getNickname() + " ξαναπαίζει γιατί έβγαλε πιόνι απο την εκκίνηση .");
        } else {
            newText = ("\n[Σύστημα]: Ο " + player.getNickname() + " ξαναπαίζει γιατί έφερε 6.");
        }

        this.gamePanel.getInfoJTextArea().setText(oldText + newText);
    }

    /**
     * Update InfoJTextArea about finish of a pawn.
     *
     * @param player Pawn's owner
     * @param curPlayerPawnNum Pawn's number.
     */
    public void pawnFinishMsgUpdate(Player player, int curPlayerPawnNum) {
        String oldText = this.gamePanel.getInfoJTextArea().getText();
        String newText = ("\n[Σύστημα]: To πιόνι " + (curPlayerPawnNum + 1) + " του " + player.getNickname() + " τερμάτισε. ");

        this.gamePanel.getInfoJTextArea().setText(oldText + newText);
    }

    // UTILITIES FUNCTIONS //
    /**
     * First disable all Cells(except DecorativeCell) and after enable only
     * current player's path and init positions.
     *
     * @param curPlayer The play now/current player.
     */
    public void onCurrentOffOther(int curPlayer) {

        // Disable all Cells(except DecorativeCell) 
        for (int i = 1; i < (gameBoardSize * gameBoardSize); i++) {

            if (!(gameBoardMap.getCell(i) instanceof DecorativeCell)) {
                gameBoardMap.getCell(i).setEnabled(false);
            }

        }

        // Enable current player's path and init positions.
        if (players.get(curPlayer).getColor() == Color.green) {

            // Enable init pawns
            for (int i = 0; i < greenInitPos.length; i++) {
                gameBoardMap.getCell(greenInitPos[i]).setEnabled(true);
            }
            // Enable path cells.
            for (Integer greenPathList1 : greenPathList) {
                gameBoardMap.getCell(greenPathList1).setEnabled(true);
            }

        } else if (players.get(curPlayer).getColor() == Color.red) {

            // Enable init pawns
            for (int i = 0; i < redInitPos.length; i++) {
                gameBoardMap.getCell(redInitPos[i]).setEnabled(true);
            }
            // Enable path cells.
            for (Integer redPathList1 : redPathList) {
                gameBoardMap.getCell(redPathList1).setEnabled(true);
            }

        } else if (players.get(curPlayer).getColor() == Color.yellow) {
            // Enable init pawns
            for (int i = 0; i < yellowInitPos.length; i++) {
                gameBoardMap.getCell(yellowInitPos[i]).setEnabled(true);
            }
            // Enable path cells.
            for (Integer yellowPathList1 : yellowPathList) {
                gameBoardMap.getCell(yellowPathList1).setEnabled(true);
            }
        } else if (players.get(curPlayer).getColor() == Color.blue) {
            // Enable init pawns
            for (int i = 0; i < blueInitPos.length; i++) {
                gameBoardMap.getCell(blueInitPos[i]).setEnabled(true);
            }
            // Enable path cells.
            for (Integer bluePathList1 : bluePathList) {
                gameBoardMap.getCell(bluePathList1).setEnabled(true);
            }
        }
    }

    /**
     * Check if player have valid moves. Scan all players pawns for the next
     * curPos + dice num one by one with use o check method.
     *
     * @param curPlayer The play now/current player.
     * @return true if have valid moves else false.
     */
    public boolean checkValidMoves(int curPlayer) {

        int validMoveCounter = 0;

        ArrayList<Pawn> curPlayerPwns = players.get(curPlayer).getPanws();
        int pawnPos = -1;
        ArrayList<Integer> colorPath = null;

        // Check for curPlayer color.
        if (players.get(curPlayer).getColor() == Color.green) {
            colorPath = greenPathList;
        } else if (players.get(curPlayer).getColor() == Color.red) {
            colorPath = redPathList;
        } else if (players.get(curPlayer).getColor() == Color.yellow) {
            colorPath = yellowPathList;
        } else if (players.get(curPlayer).getColor() == Color.blue) {
            colorPath = bluePathList;
        }

        int curCellPathPos;

        // Check every pawn.
        for (int i = 0; i < curPlayerPwns.size(); i++) {
            // Get pawn's position in colorPath.
            pawnPos = players.get(curPlayer).getPawn(i).getPosition();
            curCellPathPos = colorPath.indexOf(pawnPos);

            if ((gameBoardMap.getCell(pawnPos) instanceof InitCell) && gameDice.getDiceNum() == 5) {
                validMoveCounter++;
            } else if ((gameBoardMap.getCell(pawnPos) instanceof InitCell) && gameDice.getDiceNum() != 5) {

            } else {
                // Check for barrier one by one with help of checkCell.
                if (gameDice.getDiceNum() <= (colorPath.size() - (curCellPathPos + 1))) {
                    int eachCellPathCounter = 0; // see it more
                    for (int j = 1; j <= gameDice.getDiceNum(); j++) {
                        if (gameBoardMap.getCell(colorPath.get((curCellPathPos) + j)).checkCell(players.get(curPlayer).getPawn(i))) {
                            eachCellPathCounter++;
                        }
                    }
                    if (gameDice.getDiceNum() == eachCellPathCounter) {
                        validMoveCounter++;
                    }
                }
            }
        }

        // Msg for infoJTextField about if don't have valid moves. 
        if (validMoveCounter == 0) {
            String oldText = this.gamePanel.getInfoJTextArea().getText();
            String newText = ("\n[Σύστημα]: Ο " + players.get(curPlayer).getNickname() + " δεν έχει καμία έγκυρη κίνηση διαθέσιμη.");
            this.gamePanel.getInfoJTextArea().setText(oldText + newText);
            JOptionPane.showMessageDialog(null, " Ο " + players.get(curPlayer).getNickname() + " δεν έχει καμία \nέγκυρη κίνηση διαθέσιμη ", " Καμία κίνηση διαθέσιμη", JOptionPane.INFORMATION_MESSAGE);
            return false;
        }
        return true;
    }

    /**
     * Activate Listener for Cells with curent player pawns.
     *
     * @param curPlayer The play now/current player.
     */
    public void activeMouseGuide(int curPlayer) {

        // Only uniqueeeeeeeeeeeeee positions, not two OR MORE listeners in same Cell FAIL.
        for (int i = 1; i < gameBoardSize * gameBoardSize; i++) {

            // If cell isn't empty.
            if (!gameBoardMap.getCell(i).getCellPawns().isEmpty()) {
                // Store fast cells's pawns
                HashSet<Pawn> cellPawns = gameBoardMap.getCell(i).getCellPawns();

                // Check each pawn/pawns
                for (Pawn tempPawnOfCell : cellPawns) {

                    // If pawn belong to current player.
                    if (tempPawnOfCell.getOwner() == players.get(curPlayer)) {
                        int count = -1;

                        // Take player's all pawns.
                        ArrayList<Pawn> tempPawns = players.get(curPlayer).getPanws();

                        // Compare this cell with the others of player is hava the same position
                        // if have two or more on same cell MUST add only one listener.
                        for (Pawn tempPawn : tempPawns) {
                            count++;
                            if (tempPawn.getPosition() == tempPawnOfCell.getPosition()) {
                                break;
                            }
                        }

                        // Add Listener to cell based on players's color.
                        if (players.get(curPlayer).getColor() == Color.green) {
                            gameBoardMap.getCell(i).addMouseListener(new MouseOverAndClickListener(count, i, gameDice.getDiceNum(), players.get(curPlayer), gameBoardMap, greenPathList));
                        } else if (players.get(curPlayer).getColor() == Color.red) {
                            gameBoardMap.getCell(i).addMouseListener(new MouseOverAndClickListener(count, i, gameDice.getDiceNum(), players.get(curPlayer), gameBoardMap, redPathList));

                        } else if (players.get(curPlayer).getColor() == Color.yellow) {
                            gameBoardMap.getCell(i).addMouseListener(new MouseOverAndClickListener(count, i, gameDice.getDiceNum(), players.get(curPlayer), gameBoardMap, yellowPathList));

                        } else if (players.get(curPlayer).getColor() == Color.blue) {
                            gameBoardMap.getCell(i).addMouseListener(new MouseOverAndClickListener(count, i, gameDice.getDiceNum(), players.get(curPlayer), gameBoardMap, bluePathList));
                        }
                        break;
                    }
                }
            }

        }

    }

    /**
     * Every button have MouseListener in game board now would not have. Do it
     * for extra safety reasons.
     */
    public void disableMouseGuide() {

        // Scan all Cells.
        for (int i = 1; i < gameBoardSize * gameBoardSize; i++) {

            // If have mouse listeners, remove them all.
            for (MouseListener ml : gameBoardMap.getCell(i).getMouseListeners()) {
                gameBoardMap.getCell(i).removeMouseListener(ml);
            }

        }
    }

    /**
     * Check all player's pawns whether located in FinalCell. If have winner
     * show a message and close the game.
     */
    private void isGameFinished() {

        int curPos;
        int countFin;
        // Scan every player.
        for (Player player : this.players) {
            countFin = 0;
            // Scan player pawns.
            for (int j = 0; j < player.getPanws().size(); j++) {
                curPos = player.getPawn(j).getPosition();
                if (gameBoardMap.getCell(curPos) instanceof FinalCell) {
                    countFin++;
                }
            }
            if (countFin == player.getPanws().size()) {
                // Close application
                String oldText = this.gamePanel.getInfoJTextArea().getText();
                String newText = ("\n[Σύστημα]: Συγχαρητήρια  " + player.getNickname() + " είσαι ο νικητής.");
                this.gamePanel.getInfoJTextArea().setText(oldText + newText);
                JOptionPane.showMessageDialog(gamePanel, "Συγχαρητήρια " + player.getNickname() + " είσαι ο νικητής.", "Συγχαρητήρια " + player.getNickname(), JOptionPane.INFORMATION_MESSAGE);
                System.exit(0);
            }
        }

    }

    // LISTENER //
    /**
     * MouseOverAndClickListener Class used for detect some mouse events like
     * mouseEntered, mouseExited & mousemouseClicked. Used for the mouseOver
     * function and for the interface between the player and the gameboard.
     */
    private class MouseOverAndClickListener extends MouseAdapter {

        // Basic class fields.
        private int playerPawnNum;
        private int curCellNum;
        private int diceNum;
        private Player curPlayer;
        private GameBoardMap gameBoardMap;
        private ArrayList<Integer> colorPath;

        /**
         * Constractor of MouseOverAndClickListener only initialize the fields of class.
         */
        public MouseOverAndClickListener(int playerPawnNum, int curCellNum, int diceNum, Player curPlayer, GameBoardMap gameBoardMap, ArrayList<Integer> colorPath) {
            this.playerPawnNum = playerPawnNum;
            this.curCellNum = curCellNum;
            this.diceNum = diceNum;
            this.curPlayer = curPlayer;
            this.gameBoardMap = gameBoardMap;
            this.colorPath = colorPath;
        }

        /**
         * When mouseEntered check the current Cell and change the current Cell+ dice number Border to
         * LineBorder(Color.black, 5) to show where pawn go, in init case show the start position and in
         * init case without 5 just disable the init Cell/JButton.
         * @param evt Not used.
         */
        @Override
        public void mouseEntered(MouseEvent evt) {
            Border borderMouseOver = new LineBorder(Color.black, 5);

            if ((gameBoardMap.getCell(curCellNum) instanceof InitCell) && (gameBoardMap.getCell(curCellNum).getCellPawns().isEmpty())) {
                gameBoardMap.getCell(curCellNum).setEnabled(false);
            } else if ((gameBoardMap.getCell(curCellNum) instanceof InitCell) && (diceNum == 5)) {
                gameBoardMap.getCell(colorPath.get(0)).setBorder(borderMouseOver);
            } else if ((gameBoardMap.getCell(curCellNum) instanceof InitCell) && (diceNum != 5)) {
                gameBoardMap.getCell(curCellNum).setEnabled(false);
            } else if (!(gameBoardMap.getCell(curCellNum).getCellPawns().isEmpty())) { // gia gemata mono.
                int curCellPathPos = colorPath.indexOf(curCellNum);

                if (diceNum <= (colorPath.size() - (curCellPathPos + 1))) {
                    gameBoardMap.getCell(colorPath.get(curCellPathPos + diceNum)).setBorder(borderMouseOver);
                } else {
                    //do nothing 
                }

            }
        }

        /**
         * When mouseExited check the current Cell and set the current Cell+ dice number Border to createLineBorder, 
         * in init case also Border to createLineBorder for start position and in init case without 5 just enable
         *  again the init Cell/JButton.
         * @param evt Not used.
         */
        @Override
        public void mouseExited(MouseEvent evt) {

            if ((gameBoardMap.getCell(curCellNum) instanceof InitCell) && (gameBoardMap.getCell(curCellNum).getCellPawns().isEmpty())) {
                gameBoardMap.getCell(curCellNum).setEnabled(false);
                gameBoardMap.getCell(colorPath.get(0)).setBorder(BorderFactory.createLineBorder(Color.black));
            } else if ((gameBoardMap.getCell(curCellNum) instanceof InitCell) && (diceNum == 5)) {
                gameBoardMap.getCell(colorPath.get(0)).setBorder(BorderFactory.createLineBorder(Color.black));
            } else if ((gameBoardMap.getCell(curCellNum) instanceof InitCell) && (diceNum != 5)) {
                gameBoardMap.getCell(curCellNum).setEnabled(true);
            } else { //if (!(gameBoardMap.getCell(curCellNum).getCellPawns().isEmpty())) {
                int curCellPathPos = colorPath.indexOf(curCellNum);
                if (diceNum <= (colorPath.size() - (curCellPathPos + 1))) {
                    gameBoardMap.getCell(colorPath.get(curCellPathPos + diceNum)).setBorder(BorderFactory.createLineBorder(Color.black));
                } else {
                    // Do nothnig
                }

            }

        }

        /**
         * When mouseClicked control all the possible scenarios and essentially applied the logic of the
         * game more specific check if to move from InitCell or StarCell, if moves are valid, if you have
         * the exact number for the FinalCell, if can play again, if there is barrier, if caught pawn go 
         * to InitCell of rival, inform user via game logs text field, which button should be enabled or 
         * not and maybe more.
         * @param evt Not used.
         */
        @Override
        public void mouseClicked(MouseEvent evt) {

            // Take clicked cell available pawns.
            HashSet<Pawn> cellPawns = gameBoardMap.getCell(curCellNum).getCellPawns();

            Pawn playerPawn = null;
            // I want to take my pawn (my color pawn) this used for case of Cicle or barier_simple_cell.
            Iterator iter = cellPawns.iterator();
            while (iter.hasNext()) {
                playerPawn = (Pawn) iter.next();
                if (playerPawn.getOwner().getColor() == curPlayer.getColor()) {
                    break;
                }
            }

            // Check if is empty
            if (playerPawn != null) {

                if ((gameBoardMap.getCell(curCellNum) instanceof InitCell) && diceNum == 5) {

                    if (gameBoardMap.getCell(colorPath.get(0)).checkCell(playerPawn)) {

                        gameBoardMap.getCell(curCellNum).removePawn(playerPawn); // Remove from old.
                        // Update pawn munber via player's pawns.
                        curPlayer.setPanws(playerPawnNum, colorPath.get(0));
                        playerPawn.setPosition(colorPath.get(0));

                        Pawn catchPawn = gameBoardMap.getCell(colorPath.get(0)).addPawn(playerPawn); // Add to new.
                        gameBoardMap.getCell(colorPath.get(0)).setBorder(BorderFactory.createLineBorder(Color.black));
                        movePlayMsgUpdate(curPlayer, playerPawnNum, -1, colorPath.get(0));

                        if (catchPawn != null) { // If Thhere are others pawns.

                            ArrayList<Pawn> catchPlayerPwans = catchPawn.getOwner().getPanws();

                            int catchPawnPos = -1;
                            for (Pawn catchPlayerPwan : catchPlayerPwans) {
                                catchPawnPos++;
                                if (catchPlayerPwan == catchPawn) {
                                    break;
                                }
                            }

                            catchMsgUpdate(curPlayer, catchPawn.getOwner(), catchPawnPos);

                            if (catchPawn.getOwner().getColor() == Color.green) {
                                catchPawn.getOwner().getPawn(catchPawnPos).setPosition(greenInitPos[catchPawnPos]);
                                gameBoardMap.getCell(greenInitPos[catchPawnPos]).addPawn(catchPawn);
                            } else if (catchPawn.getOwner().getColor() == Color.red) {
                                catchPawn.getOwner().getPawn(catchPawnPos).setPosition(redInitPos[catchPawnPos]);
                                gameBoardMap.getCell(redInitPos[catchPawnPos]).addPawn(catchPawn);
                            } else if (catchPawn.getOwner().getColor() == Color.yellow) {
                                catchPawn.getOwner().getPawn(catchPawnPos).setPosition(yellowInitPos[catchPawnPos]);
                                gameBoardMap.getCell(yellowInitPos[catchPawnPos]).addPawn(catchPawn);
                            } else if (catchPawn.getOwner().getColor() == Color.blue) {
                                catchPawn.getOwner().getPawn(catchPawnPos).setPosition(blueInitPos[catchPawnPos]);
                                gameBoardMap.getCell(blueInitPos[catchPawnPos]).addPawn(catchPawn);
                            }
                        }

                        // Play again. 
                        disableMouseGuide();
                        playAgainMsgUpdate(curPlayer, 0);
                        gamePanel.getDiceImgLabel().setIcon(null);
                        gamePanel.getDiceButton().setEnabled(true);
                        gamePanel.getFinishMoveJButton().setEnabled(false);

                    } else {
                        JOptionPane.showMessageDialog(null, " Δεν μπορείς να μετακινήσεις τι πιόνι διότι,\n εχει δημιουργηθεί  φραγμα. ", " Αδύνατη Κίνηση ", JOptionPane.INFORMATION_MESSAGE);
                        gameBoardMap.getCell(colorPath.get(0)).setBorder(BorderFactory.createLineBorder(Color.black));
                        gamePanel.getFinishMoveJButton().doClick();

                    }
                } else if ((gameBoardMap.getCell(curCellNum) instanceof InitCell) && diceNum != 5) {
                    JOptionPane.showMessageDialog(null, " Δεν μπορείς να μετακινήσεις πιόνι που είναι σε αρχική θέση, \n χωρίς να έχεις φέρει 5. ", " Αδύνατη Κίνηση ", JOptionPane.INFORMATION_MESSAGE);
                } else {

                    int curCellPathPos = colorPath.indexOf(curCellNum);

                    if (diceNum <= (colorPath.size() - (curCellPathPos + 1))) {

                        int eachCellPathCounter = 0;
                        for (int j = 1; j <= gameDice.getDiceNum(); j++) {
                            if (gameBoardMap.getCell(colorPath.get((curCellPathPos) + j)).checkCell(curPlayer.getPawn(playerPawnNum))) {
                                eachCellPathCounter++;
                            }
                        }

                        if (gameDice.getDiceNum() == eachCellPathCounter) {
                            if (gameBoardMap.getCell(colorPath.get(curCellPathPos + diceNum)).checkCell(playerPawn)) {

                                gameBoardMap.getCell(curCellNum).removePawn(playerPawn); // Remove from old.

                                // Update pawn munber via player's pawns.
                                curPlayer.setPanws(playerPawnNum, colorPath.get(curCellPathPos + diceNum));
                                playerPawn.setPosition(colorPath.get(curCellPathPos + diceNum));
                                Pawn catchPawn = gameBoardMap.getCell(colorPath.get(curCellPathPos + diceNum)).addPawn(playerPawn); // Add to new.
                                gameBoardMap.getCell(colorPath.get(curCellPathPos + diceNum)).setBorder(BorderFactory.createLineBorder(Color.black));
                                movePlayMsgUpdate(curPlayer, playerPawnNum, colorPath.get(curCellPathPos), colorPath.get(curCellPathPos + diceNum));

                                // If have pawn move to init area of otherPlayer.
                                if (catchPawn != null) { // There are others pawns. 

                                    ArrayList<Pawn> catchPlayerPwans = catchPawn.getOwner().getPanws();

                                    int catchPawnPos = -1;
                                    for (Pawn catchPlayerPwan : catchPlayerPwans) {
                                        catchPawnPos++;
                                        if (catchPlayerPwan == catchPawn) {
                                            break;
                                        }
                                    }

                                    catchMsgUpdate(curPlayer, catchPawn.getOwner(), catchPawnPos);

                                    if (catchPawn.getOwner().getColor() == Color.green) {
                                        catchPawn.getOwner().getPawn(catchPawnPos).setPosition(greenInitPos[catchPawnPos]);
                                        gameBoardMap.getCell(greenInitPos[catchPawnPos]).addPawn(catchPawn);
                                    } else if (catchPawn.getOwner().getColor() == Color.red) {
                                        catchPawn.getOwner().getPawn(catchPawnPos).setPosition(redInitPos[catchPawnPos]);
                                        gameBoardMap.getCell(redInitPos[catchPawnPos]).addPawn(catchPawn);
                                    } else if (catchPawn.getOwner().getColor() == Color.yellow) {
                                        catchPawn.getOwner().getPawn(catchPawnPos).setPosition(yellowInitPos[catchPawnPos]);
                                        gameBoardMap.getCell(yellowInitPos[catchPawnPos]).addPawn(catchPawn);
                                    } else if (catchPawn.getOwner().getColor() == Color.blue) {
                                        catchPawn.getOwner().getPawn(catchPawnPos).setPosition(blueInitPos[catchPawnPos]);
                                        gameBoardMap.getCell(blueInitPos[catchPawnPos]).addPawn(catchPawn);
                                    }
                                }

                                // Star case
                                boolean existNextStar = false;
                                if ((gameBoardMap.getCell(colorPath.get(curCellPathPos + diceNum))) instanceof StarCell) {

                                    int count = 0;
                                    for (int i = (curCellPathPos + diceNum); i < (colorPath.size() - 1); i++) {
                                        count++;
                                        if (gameBoardMap.getCell(colorPath.get(curCellPathPos + diceNum + count)) instanceof StarCell) {
                                            existNextStar = true;
                                            break;
                                        }
                                    }

                                    if (existNextStar == true) {
                                        int starCellNum = colorPath.get(curCellPathPos + diceNum + count);

                                        gameBoardMap.getCell(colorPath.get(curCellPathPos + diceNum)).removePawn(playerPawn); // REMOVE FROM OLD 

                                        // Update pawn munber via player's pawns.
                                        curPlayer.setPanws(playerPawnNum, starCellNum);
                                        playerPawn.setPosition(starCellNum);
                                        Pawn catchStarPawn = gameBoardMap.getCell(starCellNum).addPawn(playerPawn); //add to new & return content.

                                        starMoveMsgUpdate(curPlayer, (colorPath.get(curCellPathPos + diceNum + count)));
                                        JOptionPane.showMessageDialog(null, curPlayer.getNickname() + ", μεταφέρθηκες στο επόμενο StarCell.", " StarCell Κίνηση ", JOptionPane.INFORMATION_MESSAGE);

                                        // in have pawn in moved star cell, go old to init area of otherPlayer.
                                        if (catchStarPawn != null) { // there are others pawns 

                                            ArrayList<Pawn> catchPlayerPwans = catchStarPawn.getOwner().getPanws();

                                            int catchStarPawnPos = -1;
                                            for (Pawn catchPlayerPwan : catchPlayerPwans) {
                                                catchStarPawnPos++;
                                                if (catchPlayerPwan == catchStarPawn) {
                                                    break;
                                                }
                                            }

                                            catchMsgUpdate(curPlayer, catchStarPawn.getOwner(), catchStarPawnPos);

                                            if (catchStarPawn.getOwner().getColor() == Color.green) {
                                                catchStarPawn.getOwner().getPawn(catchStarPawnPos).setPosition(greenInitPos[catchStarPawnPos]);
                                                gameBoardMap.getCell(greenInitPos[catchStarPawnPos]).addPawn(catchStarPawn);
                                            } else if (catchStarPawn.getOwner().getColor() == Color.red) {
                                                catchStarPawn.getOwner().getPawn(catchStarPawnPos).setPosition(redInitPos[catchStarPawnPos]);
                                                gameBoardMap.getCell(redInitPos[catchStarPawnPos]).addPawn(catchStarPawn);
                                            } else if (catchStarPawn.getOwner().getColor() == Color.yellow) {
                                                catchStarPawn.getOwner().getPawn(catchStarPawnPos).setPosition(yellowInitPos[catchStarPawnPos]);
                                                gameBoardMap.getCell(yellowInitPos[catchStarPawnPos]).addPawn(catchStarPawn);
                                            } else if (catchStarPawn.getOwner().getColor() == Color.blue) {
                                                catchStarPawn.getOwner().getPawn(catchStarPawnPos).setPosition(blueInitPos[catchStarPawnPos]);
                                                gameBoardMap.getCell(blueInitPos[catchStarPawnPos]).addPawn(catchStarPawn);
                                            }
                                        }

                                    } else {
                                        JOptionPane.showMessageDialog(null, curPlayer.getNickname() + ", δεν μπορείς να μετακινηθείς στο επόμενο StarCell μιας \n και εισαι ήδη στο τελευταίο της διαδρομής σου. ", " Αδύνατη Κίνηση ", JOptionPane.INFORMATION_MESSAGE);
                                    }
                                }

                                if (gameBoardMap.getCell(colorPath.get(curCellPathPos + diceNum)) instanceof FinalCell) {

                                    int playersPawnNum = -1;
                                    ArrayList<Pawn> myPawns = curPlayer.getPanws();
                                    for (Pawn myPawns1 : myPawns) {
                                        playersPawnNum++;
                                        if (myPawns1 == playerPawn) {
                                            break;
                                        }
                                    }
                                    pawnFinishMsgUpdate(curPlayer, playersPawnNum);
                                }
                            } else {
                                JOptionPane.showMessageDialog(null, " Δεν μπορείς να μετακινήσεις τι πιόνι διότι,\n εχει δημιουργηθεί φράγμα. ", " Αδύνατη Κίνηση ", JOptionPane.INFORMATION_MESSAGE);
                                gameBoardMap.getCell(colorPath.get(curCellPathPos + diceNum)).setBorder(BorderFactory.createLineBorder(Color.black));

                            }

                        } else {
                            JOptionPane.showMessageDialog(null, " Δεν μπορείς να μετακινήσεις τι πιόνι διότι,\n εχει δημιουργηθεί φράγμα. ", " Αδύνατη Κίνηση ", JOptionPane.INFORMATION_MESSAGE);
                            gameBoardMap.getCell(colorPath.get(curCellPathPos + diceNum)).setBorder(BorderFactory.createLineBorder(Color.black));

                        }

                    } else {
                        JOptionPane.showMessageDialog(null, " Πρέπει να φέρεις τον ακριβή αριθμό του κεντρικού κελιού δηλαδή " + ((colorPath.size()) - (curCellPathPos + 1)) + ".", " Αδύνατη Κίνηση ", JOptionPane.INFORMATION_MESSAGE);
                    }

                    // Play again ig dicenum == 6.
                    if (diceNum == 6) {

                        disableMouseGuide();
                        playAgainMsgUpdate(curPlayer, 1);
                        gamePanel.getDiceImgLabel().setIcon(null);

                        gamePanel.getDiceButton().setEnabled(true);
                        gamePanel.getFinishMoveJButton().setEnabled(false);
                        gamePanel.getDiceButton().setEnabled(true);
                        gamePanel.getFinishMoveJButton().setEnabled(false);
                    }
                    gamePanel.getFinishMoveJButton().doClick();
                }
            } else {
                // Donothing.
            }

            isGameFinished();
        }

    }

}
