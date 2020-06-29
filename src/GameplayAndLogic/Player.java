package GameplayAndLogic;

import java.awt.Color;
import java.util.ArrayList;

/**
 * Player have  name, color and a arraylist of pawns (from 2-4).
 * Is one of the most important elements of the game.
 *
 * @author Πλέσσιας Αλέξανδρος (ΑΜ.:2025201100068).
 */
public class Player {

    // Basic class fields.
    private String nickname;
    private Color color;
    private ArrayList<Pawn> panws;

    /**
     * Constructor init fields of Player.
     * @param nickname Player's nickname. 
     * @param color Player's color
     * @param pawnNum Player's Pawn number.
     */
    public Player(String nickname, Color color, int pawnNum) {
        this.nickname = nickname;
        this.color = color;
        this.panws = new ArrayList<>();

        for (int i = 0; i < pawnNum; i++) {
            this.panws.add(new Pawn(0, this));
        }
    }

    /**
     * @return The player's nickname.
     */
    public String getNickname() {
        return this.nickname;
    }

     /**
     *
     * @return The player's color.
     */
    public Color getColor() {
        return this.color;
    }

     /**
     * 
     * @return The player's color in String.
     */
    public String getColorText() {

        if (this.color == color.green) {
            return "Πράσινο";
        } else if (this.color == color.red) {
            return "Κόκκινο";
        } else if (this.color == color.yellow) {
            return "Κίτρινο";
        } else {
            return "Μπλέ";
        }

    }

     /**
     * @return Array list with player's Pawns
     */
    public ArrayList<Pawn> getPanws() {
        return panws;
    }

     /**
     * Get a specific Pawn of player.
     * @param pawnNum The pawn number.
     * @return The Pawn corresponding to pawnNum.
     */
    public Pawn getPawn(int pawnNum) {
        return panws.get(pawnNum);
    }

     /**
     * Set a specific Pawn of player.
     * @param pawnNum The pawn number.
     * @param pos The Pawn new position.
     */
    public void setPanws(int pawnNum, int pos) {
        this.panws.get(pawnNum).setPosition(pos);
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) { // Empty
            return false;
        }
        if (this == obj) { // The same
            return true;
        }
        if (obj instanceof Player == false) { // same class
            return false;
        }
        final Player other = (Player) obj;
        return (this.nickname.equals(other.nickname));
    }

}
