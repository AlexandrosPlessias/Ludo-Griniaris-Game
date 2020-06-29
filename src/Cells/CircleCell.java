package Cells;

import java.awt.Color;
import java.io.File;
import java.util.Iterator;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import GameplayAndLogic.Pawn;

/**
 * Circle Cell can add as many pawns we want in that cell regardless of 
 * their color.
 * 
 * @author Πλέσσιας Αλέξανδρος (ΑΜ.:2025201100068).
 */
public class CircleCell extends Cell {

    // Circle Image Path & Icon.
    private final String circleImgPath = "Icons" + File.separator + "GameBoard" + File.separator +"circle.png";
    private Icon circleImg;

    // 2-Combo Images Paths & Icons.
    private final String redANDgreenImgPath = "Icons" + File.separator + "PawnsCombos" + File.separator + "red&greenPawns.png";
    private final String redANDyellowImgPath = "Icons" + File.separator + "PawnsCombos" + File.separator + "red&yellowPawns.png";
    private final String redANDblueImgPath = "Icons" + File.separator + "PawnsCombos" + File.separator + "red&bluePawns.png";
    private final String yellowANDgreenImgPath = "Icons" + File.separator + "PawnsCombos" + File.separator + "yellow&greenPawns.png";
    private final String greenANDblueImgPath = "Icons" + File.separator + "PawnsCombos" + File.separator + "green&bluePawns.png";
    private final String yellowANDblueImgPath = "Icons" + File.separator + "PawnsCombos" + File.separator + "yellow&bluePawns.png";

    private Icon redANDgreenImg;
    private Icon redANDyellowImg;
    private Icon redANDblueImg;
    private Icon yellowANDgreenImg;
    private Icon greenANDblueImg;
    private Icon yellowANDblueImg;

    // 3-Combo Images Paths & Icons.
    private final String redANDgreenANDyellowImgPath = "Icons" + File.separator + "PawnsCombos" + File.separator + "red&green&yellowPawns.png";
    private final String redANDgreenANDblueImgPath = "Icons" + File.separator + "PawnsCombos" + File.separator + "red&green&bluePawns.png";
    private final String greenANDblueANDyellowImgPath = "Icons" + File.separator + "PawnsCombos" + File.separator + "green&blue&yellowPawns.png";
    private final String redANDblueANDyellowImgPath = "Icons" + File.separator + "PawnsCombos" + File.separator + "red&blue&yellowPawns.png";

    private Icon redANDgreenANDyellowImg;
    private Icon redANDgreenANDblueImg;
    private Icon greenANDblueANDyellowImg;
    private Icon redANDblueANDyellowImg;

    // 4-Combo Images Paths & Icons.
    private final String redANDgreenANDblueANDyellowImgPath = "Icons" + File.separator + "PawnsCombos" + File.separator + "red&green&blue&yellowPawns.png";
    private Icon redANDgreenANDblueANDyellowImg;

    
    /**
     * Constructor of CircleCell have white background, black foreground, have horizontal & vertical 
     * centered text, is enable and have a circle icon.
     */
    public CircleCell() {
        super();
        setBackground(Color.white);
        setForeground(Color.white);

        circleImg = new ImageIcon(circleImgPath);
        setIcon(circleImg);
        setEnabled(true);
        
        setHorizontalTextPosition(JButton.CENTER);
        setVerticalTextPosition(JButton.CENTER);
       

    }


    /**
     * Informs us about whether you can add a specific cell but in circle cell case
     * always can add pawns to Circle Cell.
     * @param curPawn The pawn we want to add.
     * @return True always because can add all kinds of pawns at the same time.
     */
    @Override
    public boolean checkCell(Pawn curPawn) {
        return true;
    }

    /**
     * Add pawn to Circle Cell, change background color to icon's color and update
     * Cell icon based on the pawn are parked that moment.
     * @param curPawn The pawn we want to add.
     * @return Null because you never need to returns pawn caught.
     */
    @Override
    public Pawn addPawn(Pawn curPawn) {
        setBackground(new Color(206, 68, 152)); // The GRB code of color of star icon.

        getCellPawns().add(curPawn);
        updateIcons();

        return null;
    }

    /**
     * Remove and return a specific pawn, change background back to white if needed, set text with
     * pawns number of each color/colors (max two colors) and update Cell icon based on the pawn are 
     * parked that moment or if there is no define the icon of Circle.
     * @param curPawn The player's pawn.
     * @return The removed pawn or null (if something go wrong)
     */
    @Override
    public Pawn removePawn(Pawn curPawn) {

        Iterator iter = getCellPawns().iterator();
        Pawn tempPawn = null;

        // Remove curPawn if exist.
        while (iter.hasNext()) {
            tempPawn = (Pawn) iter.next();

            if (tempPawn == curPawn) {
                getCellPawns().remove(curPawn);
                break;
            }
        }

        // Change cell display based on size of pawns consisting.
        if (getCellPawns().isEmpty()) {
            setBackground(Color.white);
            setIcon(circleImg);
        } else if (getCellPawns().size() == 1) {
            Iterator iterFirst = getCellPawns().iterator();
            tempPawn = (Pawn) iterFirst.next();
            setIcon(tempPawn.getPawnIcon());
            setText("");
        } else {
            updateIcons();
        }

        return tempPawn;

    }

    /**
     * Set Icon and text based on num of different colors of pawns,
     * the set text function activated only for one or two colors.
     */
    public void updateIcons() {

        // Create Iterator passage of Set.
        Iterator iter = getCellPawns().iterator();
        Pawn tempPawn = null;
        setText("");

        // Init color counter
        int colorCounter = 0;
        int greenCounter = 0;
        int redCounter = 0;
        int yellowCounter = 0;
        int blueCounter = 0;

        // Count each color amount.
        while (iter.hasNext()) {
            tempPawn = (Pawn) iter.next();

            if (tempPawn.getOwner().getColor() == Color.green) {
                greenCounter++;
            } else if (tempPawn.getOwner().getColor() == Color.red) {
                redCounter++;
            } else if (tempPawn.getOwner().getColor() == Color.yellow) {
                yellowCounter++;
            } else if (tempPawn.getOwner().getColor() == Color.blue) {
                blueCounter++;
            }

        }

        // Calculate different colors.
        if (greenCounter != 0) {
            colorCounter++;
        }
        if (redCounter != 0) {
            colorCounter++;
        }
        if (yellowCounter != 0) {
            colorCounter++;
        }
        if (blueCounter != 0) {
            colorCounter++;
        }

        // Single color and colorful cases.
        if (colorCounter == 1) {
            Iterator iterFirst = getCellPawns().iterator();
            tempPawn = (Pawn) iterFirst.next();
            setIcon(tempPawn.getPawnIcon());
            if (getCellPawns().size() > 1) {
                setText(getCellPawns().size() + "");
            }

        } else if (colorCounter == 2) {
            if (redCounter > 0 && greenCounter > 0) {
                redANDgreenImg = new ImageIcon(redANDgreenImgPath);
                setIcon(redANDgreenImg);
                setText(redCounter + "  " + greenCounter);
            } else if (redCounter > 0 && yellowCounter > 0) {
                redANDyellowImg = new ImageIcon(redANDyellowImgPath);
                setIcon(redANDyellowImg);
                setText(redCounter + "  " + yellowCounter);
            } else if (redCounter > 0 && blueCounter > 0) {
                redANDblueImg = new ImageIcon(redANDblueImgPath);
                setIcon(redANDblueImg);
                setText(redCounter + "  " + blueCounter);
            } else if (yellowCounter > 0 && greenCounter > 0) {
                yellowANDgreenImg = new ImageIcon(yellowANDgreenImgPath);
                setIcon(yellowANDgreenImg);
                setText(yellowCounter + "  " + greenCounter);
            } else if (greenCounter > 0 && blueCounter > 0) {
                greenANDblueImg = new ImageIcon(greenANDblueImgPath);
                setIcon(greenANDblueImg);
                setText(greenCounter + "  " + blueCounter);
            } else if (yellowCounter > 0 && blueCounter > 0) {
                yellowANDblueImg = new ImageIcon(yellowANDblueImgPath);
                setIcon(yellowANDblueImg);
                setText(yellowCounter + "  " + blueCounter);
            }

        } else if (colorCounter == 3) {
            if (redCounter > 0 && greenCounter > 0 && yellowCounter > 0) {
                redANDgreenANDyellowImg = new ImageIcon(redANDgreenANDyellowImgPath);
                setIcon(redANDgreenANDyellowImg);
                setText(getCellPawns().size() + "");
            } else if (redCounter > 0 && greenCounter > 0 && blueCounter > 0) {
                redANDgreenANDblueImg = new ImageIcon(redANDgreenANDblueImgPath);
                setIcon(redANDgreenANDblueImg);
                setText(getCellPawns().size() + "");
            } else if (greenCounter > 0 && blueCounter > 0 && yellowCounter > 0) {
                greenANDblueANDyellowImg = new ImageIcon(greenANDblueANDyellowImgPath);
                setIcon(greenANDblueANDyellowImg);
                setText(getCellPawns().size() + "");
            } else if (redCounter > 0 && blueCounter > 0 && yellowCounter > 0) {
                redANDblueANDyellowImg = new ImageIcon(redANDblueANDyellowImgPath);
                setIcon(redANDblueANDyellowImg);
                setText(getCellPawns().size() + "");
            }
        } else if (colorCounter == 4) { // colorCounter==4
            redANDgreenANDblueANDyellowImg = new ImageIcon(redANDgreenANDblueANDyellowImgPath);
            setIcon(redANDgreenANDblueANDyellowImg);
            setText(getCellPawns().size() + "");
        }

    }

}
