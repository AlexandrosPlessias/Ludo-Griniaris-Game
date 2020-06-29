package Cells;

import java.awt.Color;
import java.util.Iterator;
import GameplayAndLogic.Pawn;

/**
 * InitCell used for store one of players's pawn.
 * 
 * @author Πλέσσιας Αλέξανδρος (ΑΜ.:2025201100068).
 */
public class InitCell extends Cell {

    /**
     * Constructor of InitCell is white and enable.
     */
    public InitCell() {
        super();
        setBackground(Color.white);
        setEnabled(true);
    }

    /**
     * Check if InitCell is empty only (Always is when want to add).
     * @param curPawn The pawn a want to check if can add.
     * @return Always true because always is empty.
     */
    @Override
    public boolean checkCell(Pawn curPawn) {
        return getCellPawns().isEmpty();

    }

    /**
     * Add pawn in his own init cell, also set enable the button & update icon.
     * @param curPawn The pawn a want to check if can add.
     * @return Always null because always add is success.
     */
    @Override
    public Pawn addPawn(Pawn curPawn) {
        setIcon(curPawn.getPawnIcon());
        getCellPawns().add(curPawn);
        return null;
    }

    /**
     * Remove pawn, also set disable the button & set icon to null.
     * @param curPawn NOT USED HERE.
     * @return The removed pawn or null (if something go wrong)
     */
    @Override
    public Pawn removePawn(Pawn curPawn) {
        
        setIcon(null);
        Iterator iter = getCellPawns().iterator();
        Pawn firstPawn = (Pawn) iter.next();
        getCellPawns().remove(firstPawn);
        
        return firstPawn;
    }

}
