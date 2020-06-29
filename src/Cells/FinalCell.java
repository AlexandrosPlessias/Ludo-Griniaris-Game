
package Cells;

import java.awt.Color;
import GameplayAndLogic.Pawn;
import javax.swing.JButton;

/**
 * FinalCell used parked only single colored pawn/pawns when is full with players pawns 
 * the game is over and that player is the winner.
 * 
 * @author Πλέσσιας Αλέξανδρος (ΑΜ.:2025201100068).
 */
public class FinalCell extends Cell{
    
     private Color colorOfCell;

    /**
     * Constructor of FinalCell have @param color as background, foreground color based on 
     * the color contrast of @param color, have horizontal & vertical centered text and is 
     * enable.
     * @param colorOfCell The color of background.
     */
    public FinalCell(Color colorOfCell) {
        super();
        this.colorOfCell = colorOfCell;
        setBackground(this.colorOfCell);

        if (this.colorOfCell == Color.green) {
            setForeground(Color.black);
        } else if (this.colorOfCell == Color.red) {
            setForeground(Color.black);
        } else if (this.colorOfCell == Color.yellow) {
            setForeground(Color.black);
        } else {
            setForeground(Color.white);
        }
        setHorizontalTextPosition(JButton.CENTER);
        setVerticalTextPosition(JButton.CENTER);
        setEnabled(true);
    }
    
     /**
     * Always take pawns beacause each player have different paths.
     * @param curPawn The pawn we want to add to FinalCell.
     * @return Always true beacause each player have different paths.
     */
    @Override
    public boolean checkCell(Pawn curPawn) {
        
        return true;
    }

    /**
     * Always can add because each player have different paths also change icon 
     *  based on curPawn and text based the number of pawns that there.
     * @param curPawn The pawn we want to add to FinalCell.
     * @return null true beacause each player have different paths.
     */
    @Override
    public Pawn addPawn(Pawn curPawn) {

        
        getCellPawns().add(curPawn);
        setIcon(curPawn.getPawnIcon());
       
        if (this.colorOfCell == Color.green) {
            setForeground(Color.black);
        } else if (this.colorOfCell == Color.red) {
            setForeground(Color.black);
        } else if (this.colorOfCell == Color.yellow) {
            setForeground(Color.black);
        } else {
            setForeground(Color.white);
        }
        

        // If more than one show the num of same pawns.
        if (getCellPawns().size() > 1) {
            setText(getCellPawns().size() + "");
        }
        
        return null;
    }

     /**
     * Never used for FinalCell.
     * @param curPawn
     * @return Always null because always is empty.
     */
    @Override
    public Pawn removePawn(Pawn curPawn) {
        return null;
    }
    
}
