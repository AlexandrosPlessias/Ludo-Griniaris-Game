package Cells;

import java.awt.Color;
import java.util.Iterator;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import GameplayAndLogic.Pawn;
import java.io.File;
import javax.swing.JButton;

/**
 * StarCell is a Simple Cell which used as advance to the next cell of 
 * the same if exist.
 * 
 * @author Πλέσσιας Αλέξανδρος (ΑΜ.:2025201100068).
 */
public class StarCell extends Cell {

    // Star Path and  Icon.
    private final String starImgPath = "Icons" + File.separator + "GameBoard" + File.separator + "star.png";
    private Icon starImg;

    /**
     * Constructor of StarCell have white background, is enable and have a star icon.
     */
    public StarCell() {
        super();
        setBackground(Color.white);

        starImg = new ImageIcon(starImgPath);
        setIcon(starImg);

        setHorizontalTextPosition(JButton.CENTER);
        setVerticalTextPosition(JButton.CENTER);

        setEnabled(true);
    }

    /**
     * Informs us about if you can add a specific cell here this check is based on whether there 
     * are already pawn/pawns and what size or color have.
     * @param curPawn The pawn we want to add.
     * @return true if it is empty or has another pawn/pawns in the same color with curPawn else False.
     */
    @Override
    public boolean checkCell(Pawn curPawn) {
         if (getCellPawns().isEmpty()) {
            return true;
        }

        Pawn testPawn = null;
        // I want to take my pawn (my color pawn) this used for case of Cicle or barier_simple_cell.
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
     * Add pawn to StarCell, change background color to Star icon's color, 
     * change icon based curPawn's icon, set text based on number of pawns, 
     * remove the old if have different colored pawn(Caught pawn).
     * @param curPawn The pawn we want to add.
     * @return Caught pawn if exist or null if add first or same colored pawn with other preexisting.
     */
    @Override
    public Pawn addPawn(Pawn curPawn) {

        setBackground(new Color(255, 177, 34)); // The GRB code of color of star icon.

        Pawn returnPawn = null;

        if (getCellPawns().isEmpty()) {
            getCellPawns().add(curPawn);
            setIcon(curPawn.getPawnIcon());
        } else {
            Iterator iter = getCellPawns().iterator();
            Pawn firstPawn = (Pawn) iter.next();
            // perform remove and after the add action
            if (!firstPawn.getOwner().equals(curPawn.getOwner())) { // Pawn of other player is there
                returnPawn = removePawn(firstPawn); // remove it
                setBackground(new Color(255, 177, 34)); // The GRB code of color of star icon.
                getCellPawns().add(curPawn);
                setIcon(curPawn.getPawnIcon());
            } else { // perform the add action
                getCellPawns().add(curPawn);
                setIcon(curPawn.getPawnIcon());
            }
        }

        // If more than one show the num of same pawns.
        if (getCellPawns().size() > 1) {
            setText(getCellPawns().size() + "");
        }

        return returnPawn;
    }


    /**
     * Remove and return the first pawn (does not matter what is all the same color with curPawn),
     * set text with pawns number, set icon based second pawn if exist else set again the star icon
     * and if is empty after remove set background color to white
     * @param curPawn The player's pawn.
     * @return The removed pawn or null (if something go wrong, here never)
     */
    @Override
    public Pawn removePawn(Pawn curPawn) {

        Iterator iter = getCellPawns().iterator();
        Pawn firstPawn = (Pawn) iter.next();

        // All are the same a remove first.
        if (getCellPawns().remove(firstPawn)) {
            if ((getCellPawns().size()) >= 1) {
                Iterator iter2 = getCellPawns().iterator();
                Pawn newFirstPawn = (Pawn) iter2.next();
                setIcon(newFirstPawn.getPawnIcon());
                setBackground(new Color(255, 177, 34));
                if (getCellPawns().size() == 1) {
                    setText("");
                } else {
                    setText(getCellPawns().size() + "");
                }
            } else {
                setIcon(starImg);
                setBackground(Color.white);
                setText("");
            }
            return firstPawn;
        } else {
            return null;
        }
    }

}
