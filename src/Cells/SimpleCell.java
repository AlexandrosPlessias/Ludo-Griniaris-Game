package Cells;

import java.awt.Color;
import java.util.Iterator;
import javax.swing.JButton;
import GameplayAndLogic.Pawn;

/**
 * SimpleCell is white and is the most numerous cell of the gameboard.
 * Also only single colored pawn/pawns can be parked.
 * 
 * @author Πλέσσιας Αλέξανδρος (ΑΜ.:2025201100068).
 */
public class SimpleCell extends Cell {

    /**
     * Constructor of SimpleCell have white background, have horizontal & vertical 
     * centered text and is enable.
     */
    public SimpleCell() {
        super();
        setBackground(Color.white);
        setHorizontalTextPosition(JButton.CENTER);
        setVerticalTextPosition(JButton.CENTER);
    }

    /**
     * Check if other players create barrier, first check if have pawns or only
     * one, after check if first pawn have the same owner with the curPawn.
     * @param curPawn The pawn we want to add.
     * @return true if can add curPawn false if can't add.
     */
    @Override
    public boolean checkCell(Pawn curPawn) {

        if (getCellPawns().isEmpty() || (getCellPawns().size() == 1)) { // If no other Pawns are there or have only one.
            return true;
        } else { // else if have get first
            Iterator iter = getCellPawns().iterator();
            Pawn firstPawn = (Pawn) iter.next();

            if (firstPawn.getOwner().equals(curPawn.getOwner())) // same owners, same pawns
            {
                return true;
            }
        }
        return false;
    }

    /**
     * Check if exist pawn and after check if pawn is from same player or other
     * and perform the add function. If have same player pawn or is empty just
     * add it, if there is a other player pawn removes is and after add the new
     * pawn. Also set enable the button & update icon.
     * @param curPawn The pawn we want to add to SimpleCell.
     * @return null if can add pawns or the removed pawn (Caught pawn).
     */
    @Override
    public Pawn addPawn(Pawn curPawn) {

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
     * Remove pawn, also set disable the button & icon.
     * @param curPawn NOT USED HERE.
     * @return The removed pawn or null (if something go wrong)
     */
    @Override
    public Pawn removePawn(Pawn curPawn) {
        setIcon(null);
        Iterator iter = getCellPawns().iterator();
        Pawn firstPawn = (Pawn) iter.next();

        // All are the same a remove first.
        if (getCellPawns().remove(firstPawn)) {
             if ((getCellPawns().size()) >= 1) {
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
