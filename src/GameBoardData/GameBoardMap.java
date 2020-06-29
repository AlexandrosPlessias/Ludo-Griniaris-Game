package GameBoardData;

import Cells.Cell;
import Cells.CircleCell;
import Cells.ColoredSimpleCell;
import Cells.DecorativeCell;
import Cells.FinalCell;
import Cells.InitCell;
import Cells.SimpleCell;
import Cells.StarCell;
import java.awt.Color;
import java.util.Comparator;
import java.util.Map;
import java.util.TreeMap;

/**
 * GameBoardMap class used for store the gameboard cells to two tree maps one
 * based on CellPos (i,j) and the Cell and the other based on position(from 1 to
 * gameBoard_length^2) and the CellPos (i,j).
 *
 * @author Πλέσσιας Αλέξανδρος (ΑΜ.:2025201100068).
 */
public class GameBoardMap {

    // Basic parameters for creating game board maps.
    private Color background_color;
    private int square_size;
    private int gameBoard_length;

    // The tree maps for store the gameboard cells.
    private TreeMap<CellPos, Cell> gameBoardMap;
    private TreeMap<Integer, CellPos> gameBoardPosMap;

    /**
     * Constructor of GameBoardMap init square_size with value of @param size,
     * init the gameBoard_length with formula [(square_size * 2) + 3] and create
     * completely the two tree maps.
     *
     * @param size
     */
    public GameBoardMap(int size) {

        this.square_size = size;
        this.gameBoard_length = (square_size * 2) + 3;
        // Use comparator for correct sort (importand for gameBoardPosMap).
        this.gameBoardMap = new TreeMap<>(new colAndRowComparator());
        this.gameBoardPosMap = new TreeMap<>();

        createBoardCells();
        createBoardPosMap();
    }

    /**
     * Adding different kinds of cells in the proper position.
     * Initially separates the dashboard in 3 horizontal range 
     * ([square_size]-[3_cells]-[square_size]) and each region 
     * in three vertical sub-regions ([square_size]-[3_cells]-[square_size]). 
     * Basis of this striping creates a grid, with the help of this boundary and some simple
     * formulas ensures that all cells will added to proper position.
     */
    private void createBoardCells() {

        boolean firstPart = false;
        boolean secondPart = false;
        boolean thirdtPart = false;

        for (int i = 0; i < gameBoard_length; i++) {

            // Monitoring at waht horizontal range is now.
            if ((i < square_size)) {
                firstPart = true;
            } else if ((i >= square_size) && (i < (square_size + (gameBoard_length - (2 * square_size))))) {
                firstPart = false;
                secondPart = true;
            } else if ((i >= ((square_size + (gameBoard_length - (2 * square_size))))) && (i < gameBoard_length)) {
                firstPart = false;
                secondPart = false;
                thirdtPart = true;
            }

            for (int j = 0; j < gameBoard_length; j++) {

                // First Horizontal range.
                if (firstPart == true) {
                    // First Verical range of First Horizontal range.
                    if (j < square_size) {

                        if (i == 1 && j == 1) {
                            this.gameBoardMap.put(new CellPos(i, j), new Cells.InitCell());
                        } else if (i == 1 && j == (square_size - 2)) {
                            this.gameBoardMap.put(new CellPos(i, j), new Cells.InitCell());
                        } else if (j == 1 && i == (square_size - 2)) {
                            this.gameBoardMap.put(new CellPos(i, j), new Cells.InitCell());
                        } else if (j == (square_size - 2) && i == (square_size - 2)) {
                            this.gameBoardMap.put(new CellPos(i, j), new Cells.InitCell());
                        } else {
                            this.gameBoardMap.put(new CellPos(i, j), new Cells.DecorativeCell(Color.green, null, square_size));
                        }
                        // Second  Verical range of First Horizontal range.
                    } else if ((j >= square_size) && (j < (square_size + (gameBoard_length - (2 * square_size))))) {
                        if (i == 0) {
                            if ((j >= square_size) && (j < (square_size + (gameBoard_length - (2 * square_size))))) {
                                this.gameBoardMap.put(new CellPos(i, j), new SimpleCell());
                            }
                        } else if (i == 1) {
                            if (j == square_size) {
                                this.gameBoardMap.put(new CellPos(i, j), new SimpleCell());
                            }
                            if (j == square_size + 1) {
                                this.gameBoardMap.put(new CellPos(i, j), new ColoredSimpleCell(Color.red));
                            }
                            if (j == square_size + 2) {
                                this.gameBoardMap.put(new CellPos(i, j), new ColoredSimpleCell(Color.red));
                            }
                        } else if (i == (square_size - 2) && j == (square_size + 2)) {
                            this.gameBoardMap.put(new CellPos(i, j), new StarCell());
                        } else {
                            if (j == square_size + 1) {
                                this.gameBoardMap.put(new CellPos(i, j), new ColoredSimpleCell(Color.red));
                            } else {
                                this.gameBoardMap.put(new CellPos(i, j), new SimpleCell());
                            }
                        }

                        if (i == square_size - 3 && j == square_size) {
                            this.gameBoardMap.put(new CellPos(i, j), new CircleCell()); // OK CHECKKK
                        }

                        // Third  Verical range of First Horizontal range.
                    } else if ((j >= (square_size + (gameBoard_length - (2 * square_size)))) && (j < gameBoard_length)) {
                        if (i == 1 && j == (square_size + 4)) {
                            this.gameBoardMap.put(new CellPos(i, j), new Cells.InitCell());
                        } else if (i == 1 && j == (gameBoard_length - 2)) {
                            this.gameBoardMap.put(new CellPos(i, j), new Cells.InitCell());
                        } else if (i == (square_size - 2) && j == (square_size + 4)) {
                            this.gameBoardMap.put(new CellPos(i, j), new Cells.InitCell());
                        } else if (i == (square_size - 2) && j == (gameBoard_length - 2)) {
                            this.gameBoardMap.put(new CellPos(i, j), new Cells.InitCell());
                        } else {
                            this.gameBoardMap.put(new CellPos(i, j), new Cells.DecorativeCell(Color.red, null, square_size));
                        }
                    }

                    // Second Horizontal range.
                } else if (secondPart == true) {

                    // First Verical range of Second Horizontal range.
                    if (i == square_size) {
                        if (j == 1) {
                            this.gameBoardMap.put(new CellPos(i, j), new ColoredSimpleCell(Color.green));
                        } else if (j == square_size) {
                            this.gameBoardMap.put(new CellPos(i, j), new Cells.DecorativeCell(background_color, "MidSquareTopLeft", square_size));
                        } else if (j == square_size + 1) {
                            this.gameBoardMap.put(new CellPos(i, j), new FinalCell(Color.red));
                        } else if (j == square_size + 2) {
                            this.gameBoardMap.put(new CellPos(i, j), new Cells.DecorativeCell(background_color, "MidSquareTopRight", square_size));
                        } else if (j == (square_size + 3) + 2) {
                            this.gameBoardMap.put(new CellPos(i, j), new CircleCell());
                        } else if (j == square_size - 2) {
                            this.gameBoardMap.put(new CellPos(i, j), new StarCell());
                        } else {
                            this.gameBoardMap.put(new CellPos(i, j), new SimpleCell());
                        }
                        
                        // Second Verical range of Second Horizontal range.
                    } else if (i == square_size + 1) {
                        if (j >= 1 && j < square_size) {
                            this.gameBoardMap.put(new CellPos(i, j), new ColoredSimpleCell(Color.green));
                        } else if (j == square_size) {
                            this.gameBoardMap.put(new CellPos(i, j), new FinalCell(Color.green));
                        } else if (j == square_size + 1) {
                            this.gameBoardMap.put(new CellPos(i, j), new Cells.DecorativeCell(background_color, "MidSquareCenter", square_size));
                        } else if (j == square_size + 2) {
                            this.gameBoardMap.put(new CellPos(i, j), new FinalCell(Color.blue));
                        } else if (j >= (square_size + 3) && j < (gameBoard_length - 1)) {
                            this.gameBoardMap.put(new CellPos(i, j), new ColoredSimpleCell(Color.blue));
                        } else {
                            this.gameBoardMap.put(new CellPos(i, j), new SimpleCell());
                        }
                        
                        // Third Verical range of Second Horizontal range.
                    } else if (i == square_size + 2) {
                        if (j == gameBoard_length - 2) {
                            this.gameBoardMap.put(new CellPos(i, j), new ColoredSimpleCell(Color.blue));
                        } else if (j == square_size) {
                            this.gameBoardMap.put(new CellPos(i, j), new Cells.DecorativeCell(background_color, "MidSquareBottomLeft", square_size));
                        } else if (j == square_size + 1) {
                            this.gameBoardMap.put(new CellPos(i, j), new FinalCell(Color.yellow));
                        } else if (j == square_size + 2) {
                            this.gameBoardMap.put(new CellPos(i, j), new Cells.DecorativeCell(background_color, "MidSquareBottomRight", square_size));
                        } else if (j == square_size - 3) {
                            this.gameBoardMap.put(new CellPos(i, j), new CircleCell());
                        } else if (j == (square_size + 2) + 2) {
                            this.gameBoardMap.put(new CellPos(i, j), new StarCell());
                        } else {
                            this.gameBoardMap.put(new CellPos(i, j), new SimpleCell());
                        }
                    }

                    // Third Horizontal range.
                } else if (thirdtPart == true) {
                    // First Verical range of Third Horizontal range.
                    if (j < square_size) {
                        if (j == 1 && i == (square_size + 4)) {
                            this.gameBoardMap.put(new CellPos(i, j), new InitCell());
                        } else if (j == (square_size - 2) && i == (square_size + 4)) {
                            this.gameBoardMap.put(new CellPos(i, j), new InitCell());
                        } else if (i == (gameBoard_length - 2) && j == 1) {
                            this.gameBoardMap.put(new CellPos(i, j), new InitCell());
                        } else if (i == (gameBoard_length - 2) && j == (square_size - 2)) {
                            this.gameBoardMap.put(new CellPos(i, j), new InitCell());
                        } else {
                            this.gameBoardMap.put(new CellPos(i, j), new DecorativeCell(Color.yellow, null, square_size));
                        }
                        
                    // Second Verical range of Third Horizontal range.
                    } else if ((j >= square_size) && (j < (square_size + (gameBoard_length - (2 * square_size))))) {

                        if (i == gameBoard_length - 1) {
                            if ((j >= square_size) && (j < (square_size + (gameBoard_length - (2 * square_size))))) {
                                this.gameBoardMap.put(new CellPos(i, j), new SimpleCell());
                            }
                        } else if (i == (gameBoard_length - 2)) {
                            if (j == square_size) {
                                this.gameBoardMap.put(new CellPos(i, j), new ColoredSimpleCell(Color.yellow));
                            }
                            if (j == square_size + 1) {
                                this.gameBoardMap.put(new CellPos(i, j), new ColoredSimpleCell(Color.yellow));
                            }
                            if (j == square_size + 2) {
                                this.gameBoardMap.put(new CellPos(i, j), new SimpleCell());
                            }
                        } else {
                            if (j == square_size + 1) {
                                this.gameBoardMap.put(new CellPos(i, j), new ColoredSimpleCell(Color.yellow));
                            } else {
                                this.gameBoardMap.put(new CellPos(i, j), new SimpleCell());
                            }
                        }

                        if (i == (square_size + 2) + 3) {
                            if (j == square_size + 2) {
                                this.gameBoardMap.put(new CellPos(i, j), new CircleCell());
                            }
                        }

                        if (i == (square_size + 2) + 2) {
                            if (j == square_size) {
                                this.gameBoardMap.put(new CellPos(i, j), new StarCell());
                            }
                        }
                        
                    // Third Verical range of Third Horizontal range.
                    } else if ((j >= (square_size + (gameBoard_length - (2 * square_size)))) && (j < gameBoard_length)) {
                        if (i == (square_size + 4) && j == (gameBoard_length - 2)) {
                            this.gameBoardMap.put(new CellPos(i, j), new InitCell());
                        } else if (i == (square_size + 4) && j == (square_size + 4)) {
                            this.gameBoardMap.put(new CellPos(i, j), new InitCell());
                        } else if (i == (gameBoard_length - 2) && j == (gameBoard_length - 2)) {
                            this.gameBoardMap.put(new CellPos(i, j), new InitCell());
                        } else if (i == (gameBoard_length - 2) && j == (square_size + 4)) {
                            this.gameBoardMap.put(new CellPos(i, j), new InitCell());
                        } else {
                            this.gameBoardMap.put(new CellPos(i, j), new DecorativeCell(Color.blue, null, square_size));
                        }
                    }
                }

            }

        }

    }

    /**
     * Simply scans the gameBoardMap threeMap from beginning to end and with the help of an 
     * index corresponding each Key of gameBoardMap to a Integer(Pos) and create a new entry
     * with index(Integer(Pos)) and the CellPos.
     */
    private void createBoardPosMap() {

        int counter = 0;
        for (Map.Entry<CellPos, Cell> entry : gameBoardMap.entrySet()) {
            counter++;
            gameBoardPosMap.put(counter, entry.getKey());
        }

    }

    /**
     * Getter to return the cell that corresponds to a specific number.
     * @param cellNum The cell number.
     * @return The cell that corresponds to a specific number.
     */
    public Cell getCell(int cellNum) {
        CellPos tempCell = gameBoardPosMap.get(cellNum);
        return gameBoardMap.get(tempCell);
    }

    /**
     * CellPos is an auxiliary Class that represents a position in a intelligibility array.
     */
    private class CellPos {

        // Row & Col 
        private int row;
        private int col;

       
        //Constractur
        public CellPos(int row, int col) {
            this.row = row;
            this.col = col;
        }

        // Getters for use in colAndRowComparator.
        public int getRow() {
            return row;
        }
        public int getCol() {
            return col;
        }


    }

    /**
     * Comparator to ensure the correct order of gameBoardMap(TreeMap) entries
     */
    private class colAndRowComparator implements Comparator<CellPos> {

       
        @Override
        public int compare(CellPos ep, CellPos ep1) {

            if (ep.getRow() < ep1.getRow()) {
                return -1;
            } else if (ep.getRow() > ep1.getRow()) {
                return 1;
            } else {
                return (ep.getCol() - ep1.getCol());
            }
        }
    }

}
