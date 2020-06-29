package Cells;

import GameplayAndLogic.Pawn;
import java.awt.Color;
import java.awt.Image;
import java.io.File;
import javax.swing.ImageIcon;

/**
 * DecorativeCell used for the decoration of disabled Cells.
 * 
 * @author Πλέσσιας Αλέξανδρος (ΑΜ.:2025201100068).
 */
public class DecorativeCell extends Cell {

    // Center_Images paths .
    private final String centerImgPath = "Icons" + File.separator + "GameBoard" + File.separator +"center.png";
    private final String topLeftImgPath = "Icons" + File.separator + "GameBoard" + File.separator +"green_red.png";
    private final String topRightImgPath = "Icons" + File.separator + "GameBoard" + File.separator +"red_blue.png";
    private final String bottomLeftImgPath = "Icons" + File.separator + "GameBoard" + File.separator +"green_yellow.png";
    private final String bottomRightImgPath = "Icons" + File.separator + "GameBoard" + File.separator +"yellow_blue.png";

    // Center_Images icons for use in board.
    private ImageIcon centerImg;
    private ImageIcon topLeftImg;
    private ImageIcon topRightImg;
    private ImageIcon bottomLeftImg;
    private ImageIcon bottomRightImg;

    /**
     * Constructor of DecorativeCell have diferent color based @param colorOfCell, to set icons 
     * where needed and set icon's size based on game board size and are disabled.
     * @param colorOfCell The color of cell background if have.
     * @param positionOfImg The position of icon we want to add.
     * @param SquareSize Help to define a scalable size for images where they exist.
     */
    public DecorativeCell(Color colorOfCell, String positionOfImg, int SquareSize) {
        
        setBackground(colorOfCell); // If exist.
        
        if (positionOfImg != null) { // If exist.
            setEnabled(true);
            int size=0;
            // Size = width & height of icons.
            if(SquareSize==4){
                size =60;
            }else if (SquareSize==5){
                size =52;
            }else if (SquareSize==6){
                size =45;
            }
            
            // Set icons.
            if (positionOfImg.compareTo("MidSquareTopLeft") == 0) {
                topLeftImg= new ImageIcon(((new ImageIcon(topLeftImgPath)).getImage()).getScaledInstance(size,size, Image.SCALE_SMOOTH));  
                setIcon(topLeftImg);
            } else if (positionOfImg.compareTo("MidSquareTopRight") == 0) {
                topRightImg = new ImageIcon(((new ImageIcon( topRightImgPath)).getImage()).getScaledInstance(size,size, Image.SCALE_SMOOTH));  
                setIcon(topRightImg);
            } else if (positionOfImg.compareTo("MidSquareBottomLeft") == 0) {
                bottomLeftImg = new ImageIcon(((new ImageIcon( bottomLeftImgPath)).getImage()).getScaledInstance(size,size, Image.SCALE_SMOOTH));  
                setIcon(bottomLeftImg);
            } else if (positionOfImg.compareTo("MidSquareBottomRight") == 0) {
                bottomRightImg = new ImageIcon(((new ImageIcon( bottomRightImgPath)).getImage()).getScaledInstance(size,size, Image.SCALE_SMOOTH));  
                setIcon(bottomRightImg);
            } else if (positionOfImg.compareTo("MidSquareCenter") == 0) {
                centerImg = new ImageIcon(((new ImageIcon( centerImgPath)).getImage()).getScaledInstance(size,size, Image.SCALE_SMOOTH));  
                setIcon(centerImg);
            }
        }else{
            setEnabled(false);
        }
    }

    /**
     * Never used for DecorativeCell.
     * @param curPawn
     * @return Always false because can't add.
     */
    @Override
    public boolean checkCell(Pawn curPawn) {
        return false;
    }

    /**
     * Never used for DecorativeCell.
     * @param curPawn
     * @return Always null because can't add.
     */
    @Override
    public Pawn addPawn(Pawn curPawn) {
        return null;
    }
    
    /**
     * Never used for DecorativeCell.
     * @param curPawn
     * @return Always null because always is empty.
     */
    @Override
    public Pawn removePawn(Pawn curPawn) {
        return null;
    }

}
