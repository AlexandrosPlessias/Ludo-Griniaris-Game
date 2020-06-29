package GameplayAndLogic;

import java.awt.Color;
import java.io.File;
import javax.swing.Icon;
import javax.swing.ImageIcon;

/**
 * Pawn have position(Cell's number from gameBoardPosMap), owner(Player) 
 * and each color has its own icon. Is one of the most important elements 
 * of the game
 *
 * @author Πλέσσιας Αλέξανδρος (ΑΜ.:2025201100068).
 */
public class Pawn {

    // Pawn images paths.
    private final String greenPawnImgPath = "Icons" + File.separator + "Pawns" + File.separator + "greenPawn.png";
    private final String redPawnImgPath = "Icons" + File.separator + "Pawns" + File.separator + "redPawn.png";
    private final String yellowPawnImgPath = "Icons" + File.separator + "Pawns" + File.separator + "yellowPawn.png";
    private final String bluePawnImgPath = "Icons" + File.separator + "Pawns" + File.separator + "bluePawn.png";

    // Pawn Icons.
    private Icon greenPawnImg;
    private Icon redPawnImg;
    private Icon yellowPawnImg;
    private Icon bluePawnImg;

    // Basic class fields.
    private int position;
    private Player owner;

    /**
     * Pawn Constructor init position, define owner and create the right pawn
     * icon based in owner's color.
     *
     * @param position A number in game board.
     * @param owner The Player have this pawn.
     */
    public Pawn(int position, Player owner) {
        this.position = position;
        this.owner = owner;

        if (this.owner.getColor() == Color.green) {
            greenPawnImg = new ImageIcon(greenPawnImgPath);
        } else if (this.owner.getColor() == Color.red) {
            redPawnImg = new ImageIcon(redPawnImgPath);
        } else if (this.owner.getColor() == Color.yellow) {
            yellowPawnImg = new ImageIcon(yellowPawnImgPath);
        } else // this.owner.getColor()== Color.blue
        {
            bluePawnImg = new ImageIcon(bluePawnImgPath);
        }
    }

    /**
     * @return The owner of Pawn.
     */
    public Player getOwner() {
        return owner;
    }

    /**
     * @return The color of Pawn.
     */
    public Color getColor() {
        return (this.owner.getColor());
    }

    /**
     * @return The Icon of Pawn.
     */
    public Icon getPawnIcon() {
        Icon pawnIcon = null;

        if (this.owner.getColor() == Color.green) {
            pawnIcon = greenPawnImg;
        } else if (this.owner.getColor() == Color.red) {
            pawnIcon = redPawnImg;
        } else if (this.owner.getColor() == Color.yellow) {
            pawnIcon = yellowPawnImg;
        } else // this.owner.getColor()== Color.blue
        {
            pawnIcon = bluePawnImg;
        }

        return pawnIcon;
    }

    /**
     * @return The Position of Pawn (Cell's number from gameBoardPosMap)..
     */
    public int getPosition() {
        return position;
    }

    /**
     * Set position of Pawn.
     *
     * @param position The position number (Cell's number from gameBoardPosMap).
     */
    public void setPosition(int position) {
        this.position = position;
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
        final Pawn other = (Pawn) obj;
        if (this.owner.equals(other.owner)) {
            if (this.position == other.position) {
                return true;
            }
        }

        return false;
    }

}
