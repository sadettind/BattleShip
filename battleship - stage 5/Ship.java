package battleship;

import java.util.Arrays;

public class Ship {

    final char SHIP_SYMBOL = 'O';
    int size;
    String name;
    int rowBegin;
    int rowEnd;
    int columnBegin;
    int columnEnd;
    boolean isPlaced = false;
    char[] cells;
    boolean isDead;


    public Ship(int size, String name) {
        this.size = size;
        this.name = name;
        this.cells = new char[size];
        Arrays.fill(this.cells, SHIP_SYMBOL);
    }


    public String getName() {
        return name;
    }

    public int getSize() {
        return size;
    }

    public boolean checkCoordinates(int rowBegin, int columnBegin, int rowEnd, int columnEnd) {
        if (rowBegin == rowEnd) {
            if ((columnEnd - columnBegin) != this.size - 1) {
                System.out.printf("Error! Wrong length of the %s! Try again:\n", this.name);
                return false;
            }
        } else if (columnBegin == columnEnd) {
            if (rowEnd - rowBegin != this.size - 1) {
                System.out.printf("Error! Wrong length of the %s! Try again:\n", this.name);
                return false;
            }
        } else {
            System.out.println("Error! Wrong ship location! Try again:\n");
            return false;
        }

        this.rowBegin = rowBegin;
        this.rowEnd = rowEnd;
        this.columnBegin = columnBegin;
        this.columnEnd = columnEnd;
        this.isPlaced = true;

        return true;
    }

    public char[] getCells() {
        return cells;
    }

    public int getRowBegin() {
        return rowBegin;
    }

    public int getRowEnd() {
        return rowEnd;
    }

    public int getColumnBegin() {
        return columnBegin;
    }

    public int getColumnEnd() {
        return columnEnd;
    }

    public boolean isPlaced() {
        return isPlaced;
    }

    public boolean isFinalHit(int index, char hit) {
        this.cells[index] = hit;
        for (char each : cells) {
            if (each != hit) {
                return false;
            }
        }
        this.isDead = true;
        return true;
    }
}
