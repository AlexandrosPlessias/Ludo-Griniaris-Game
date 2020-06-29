package Cells;

import java.awt.Color;
import java.util.HashSet;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import GameplayAndLogic.Pawn;


/**
 * Cell Class is actually a JButton and store a HashSet with Pawn/Pawns.
 * 
 * @author Πλέσσιας Αλέξανδρος (ΑΜ.:2025201100068).
 */
public abstract class Cell extends JButton{
    
    // Use HashSet because some kind of cells have more than one Pawns 
    // and must be unique.
    private HashSet<Pawn> cellPawns;

    /**
     * Constructor of Baseic Cell Constructor just create the Set Pawns and create a
     * black line Border for display reasons. 
     */
    public Cell() {
        this.cellPawns = new HashSet<>();
        setBorder(BorderFactory.createLineBorder(Color.black));
    }

    /**
     * Return the HashSet of cellPawn/Pawns.
     * @return The HashSet of cellPawn/Pawns.   
     */
    public HashSet<Pawn> getCellPawns() {
        return cellPawns;
    }

     /**
     * Add a pawn to the HashSet of cellPawn/Pawns.
     * @param curPawn The pawn we want to add.
     */
    public void setCellPawns(Pawn curPawn) {
        this.cellPawns.add(curPawn);
    }
    
    
    // Methods Abstract.
    public abstract boolean checkCell(Pawn curPawn);
    public abstract Pawn addPawn(Pawn curPawn);
    public abstract Pawn removePawn(Pawn curPawn);

}
    
   
