package Cells;

import GameplayAndLogic.Pawn;
import java.awt.Color;
import java.util.Iterator;
import javax.swing.JButton;

/**
 * ColoredSimpleCell is a Simple Cell which is used as Start Cell & as a Final but not Last.
 * Also only single colored pawn/pawns can be parked.
 * 
 * @author Πλέσσιας Αλέξανδρος (ΑΜ.:2025201100068).
 */ 
public class ColoredSimpleCell extends Cell {

    private Color colorOfCell;

    /**
     * Constructor of ColoredSimpleCell have @param color as background, foreground color based 
     * on the color contrast of @param color, have horizontal & vertical centered text and is 
     * enable.
     * @param colorOfCell The color of background.
     */
    public ColoredSimpleCell(Color colorOfCell) {
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
     * Informs us about if you can add a specific cell here this check is based on whether there 
     * are already pawn/pawns and what size or color have.
     * @param curPawn The pawn we want to add.
     * @return True if it is empty or has another pawn/pawns in the same color with curPawn else False.
     */
    @Override
    public boolean checkCell(Pawn curPawn) {

        if (getCellPawns().isEmpty()) {
            return true;
        }

        Pawn testPawn = null;
        Iterator iter = getCellPawns().iterator();
        int otherColorCounter=0;
        while (iter.hasNext()) {
            testPawn = (Pawn) iter.next();

            if (testPawn.getColor() != curPawn.getColor()) {
                otherColorCounter++;
            }
        }
        
        return (otherColorCounter<=1);

    }

    /**
     * Add pawn to ColoredCell, change icon based curPawn's icon,
     * set text based on number of pawns and remove the old if have
     * different color(Caught pawn).
     * @param curPawn The pawn we want to add.
     * @return Caught pawn if exist or null if add first or same colored pawn with other preexisting.
     */
    @Override
    public Pawn addPawn(Pawn curPawn) {
        
        Pawn returnPawn = null;

        // Chech if is empty
        if (getCellPawns().isEmpty()) {
            getCellPawns().add(curPawn);
            setIcon(curPawn.getPawnIcon());
        } else {
            Iterator iter = getCellPawns().iterator();
            Pawn firstPawn = (Pawn) iter.next();
            // Perform remove and after the add action & and icon change.
            if (!firstPawn.getOwner().equals(curPawn.getOwner())) { // Pawn of other player is there
                returnPawn = removePawn(firstPawn); // Remove it
                getCellPawns().add(curPawn);
                setIcon(curPawn.getPawnIcon());
            } else { // Perform the add action & and icon change.
                getCellPawns().add(curPawn);
                setIcon(curPawn.getPawnIcon());
            }
        }

        // If more than one show the number of same pawns.
        if (getCellPawns().size() > 1) {
            setText(getCellPawns().size() + "");
        }

        return returnPawn;
    }

    
    /**
     * Remove and return the first pawn (does not matter what is all the same color with curPawn),
     * set text with pawns number and set icon based second pawn if exist.
     * @param curPawn The player's pawn. .
     * @return The removed pawn or null (if something go wrong, here never)
     */
    @Override
   public Pawn removePawn(Pawn curPawn) {
        setIcon(null);
        Iterator iter = getCellPawns().iterator();
        Pawn firstPawn = (Pawn) iter.next();

        // All are the same so we remove the first.
        if (getCellPawns().remove(firstPawn)) {
             if ((getCellPawns().size()) >= 1) {
                 // Use second iterator for cahnge icon.
                Iterator iter2 = getCellPawns().iterator();
                Pawn newFirstPawn = (Pawn) iter2.next();
                setIcon(newFirstPawn.getPawnIcon());
                if(getCellPawns().size()==1){
                 setText("");
                }else {
                 setText(getCellPawns().size() + "");
                }
                
            } else { // == 1
                setText("");
            }
            return firstPawn;
        } else {// Never happen just addes for safty reasons.
            return null;
        }
    }
}
