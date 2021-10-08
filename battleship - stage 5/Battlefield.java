package battleship;

import java.util.Scanner;

public class Battlefield {
    final int SIZE = 10;
    char[][] field = new char[SIZE][SIZE];
    Ship[] ships;
    final char MISSED = 'M';
    final char HIT = 'X';
    final char SHIP = 'O';
    final char EMPTY = '~';
    int rowShot;
    int columnShot;


    public Battlefield() {
        for (int i = 0; i < SIZE; i++) {
            for (int j = 0; j < SIZE; j++) {
                field[i][j] = EMPTY;
            }
        }
    }


    public void initiate() {
        printField("all");
        Scanner scanner = new Scanner(System.in);
        ships = new Ship[5];
        ships[0] = new Ship(5, "Aircraft Carrier");
        ships[1] = new Ship(4, "Battleship");
        ships[2] = new Ship(3, "Submarine");
        ships[3] = new Ship(3, "Cruiser");
        ships[4] = new Ship(2, "Destroyer");

        for (int i = 0; i < 5; i++) {
            System.out.printf("Enter the coordinates of the %s (%d cells):\n", ships[i].name, ships[i].size);
            while (true) {
                String[] coordinates = new String[2];
                coordinates[0] = scanner.next();
                coordinates[1] = scanner.next();
                //convert coordinates to fit in field[][]array by using ASCII conversion or by using substring
                int rowBegin = coordinates[0].charAt(0) - 65;
                int columnBegin = Integer.parseInt(coordinates[0].substring(1)) - 1;
                int rowEnd = coordinates[1].charAt(0) - 65;
                int columnEnd = Integer.parseInt(coordinates[1].substring(1)) - 1;
                if (rowBegin > rowEnd) {             //just change the end to begin in case the user entered coordinates backwards
                    int tmp = rowBegin;
                    rowBegin = rowEnd;
                    rowEnd = tmp;
                }
                if (columnBegin > columnEnd) {
                    int tmp = columnEnd;
                    columnEnd = columnBegin;
                    columnBegin = tmp;
                }
                if (ships[i].checkCoordinates(rowBegin, columnBegin, rowEnd, columnEnd)) {
                    if (placeShip(rowBegin, columnBegin, rowEnd, columnEnd, ships[i])) {
                        printField("all");
                        break;
                    }
                }
            }
        }

    }

    public boolean takeShot() {

        Scanner scanner = new Scanner(System.in);
        String coordinate = scanner.nextLine();
        //convert coordinates to fit in field[][]array by using ASCII conversion or by using substring
        int rowShot = coordinate.charAt(0) - 65;
        int columnShot = Integer.parseInt(coordinate.substring(1)) - 1;
        if (rowShot >= 10 || columnShot >= 10) {
            System.out.println("Error! You entered the wrong coordinates! Try again:");
        } else {
            boolean isHitOnShip = false;
            boolean isSankAShip = false;
            boolean isEndGame = true;

            for (Ship ship : ships) {
                if (rowShot == ship.rowBegin && rowShot == ship.rowEnd) {
                    if (columnShot >= ship.columnBegin && columnShot <= ship.columnEnd) {
                        this.field[rowShot][columnShot] = HIT;
                        isHitOnShip = true;
                        isSankAShip = ship.isFinalHit(columnShot - ship.columnBegin, HIT);
                        break;
                    }
                } else if (columnShot == ship.columnBegin && columnShot == ship.columnEnd) {
                    if ((rowShot >= ship.rowBegin && rowShot <= ship.rowEnd)) {
                        this.field[rowShot][columnShot] = HIT;
                        isHitOnShip = true;
                        isSankAShip = ship.isFinalHit(rowShot - ship.rowBegin, HIT);
                        break;
                    }
                }
            }
            if (isHitOnShip && !isSankAShip) {
                System.out.println("You hit a ship!");
            } else if (isSankAShip) {
                for (Ship ship : ships) {
                    if (!ship.isDead) {
                        System.out.println("You sank a ship!");
                        isEndGame = false;
                    }
                }
                if (isEndGame) {
                    System.out.println("You sank the last ship. You won. Congratulations!");
                }

            } else {
                this.field[rowShot][columnShot] = MISSED;
                System.out.println("You missed!");
            }
        }
        return false;
    }

    public boolean placeShip(int _rowBegin, int _columnBegin, int _rowEnd, int _columnEnd, Ship _ship) {
        for (Ship ship : ships) {
            //if the ship being compared is not an installable ship and the ship isn't on the field yet
            if (ship != _ship && ship.isPlaced()) {// "ship != _ship" means don't check the current one to avoid wrong answer
                //find out if there are any coordinates of other ships near the one being placed
                for (int i = _rowBegin - 1; i <= _rowEnd + 1; i++) {
                    for (int j = _columnBegin - 1; j <= _columnEnd + 1; j++) {
                        if ((i == ship.getRowBegin() && j == ship.getColumnBegin()) ||
                                (i == ship.getRowEnd() && j == ship.getColumnEnd())) {
                            System.out.println("Error! You placed it too close to another one. Try again:");
                            return false;
                        }
                    }
                }
            }
        }

        if (_rowBegin == _rowEnd) {
            for (int i = _columnBegin; i <= _columnEnd; i++) {
                this.field[_rowBegin][i] = _ship.getCells()[i - _columnBegin];
            }
        } else {
            for (int i = _rowBegin; i <= _rowEnd; i++) {
                this.field[i][_columnBegin] = _ship.getCells()[i - _rowBegin];
            }
        }
        return true;
    }

    public void printField(String str) {
        System.out.println();
        System.out.print("  1 2 3 4 5 6 7 8 9 10\n");
        for (int i = 0; i < SIZE; i++) {
            System.out.print(Character.toChars(65 + i));
            System.out.print(" ");
            for (int j = 0; j < SIZE; j++) {
                switch (str) {
                    case "all":
                        System.out.print(field[i][j] + " ");
                        break;
                    case "fog":
                        System.out.print("~" + " ");
                        break;
                    case "miss":
                    case "hit":
                        if (field[i][j] == SHIP) {
                            System.out.print("~" + " ");
                            continue;
                        }
                        System.out.print(field[i][j] + " ");
                        break;
                }

            }
            System.out.println();

        }
        System.out.println();
    }
}
