package battleship;

import java.util.Scanner;

public class Main {

    public static void main(String[] args) {
        boolean isGameFinished = false;
        Player player1 = new Player("Player 1");
        Player player2 = new Player("Player 2");

        System.out.println("Player 1, place your ships on the game field");
        player1.battlefield.initiate();
        nextPlayer();

        System.out.println("Player 2, place your ships to the game field");
        player2.battlefield.initiate();
        nextPlayer();

        while (!isGameFinished) {
            //enemy field with fog
            player2.battlefield.printField("fog");
            System.out.println("---------------------");
            //current player without fog
            player1.battlefield.printField("all");
            System.out.println("Player 1, it's your turn:\n");
            //changePlayer
            isGameFinished = player2.battlefield.takeShot();
            if (isGameFinished) {
               break;
            }
            nextPlayer();
            player1.battlefield.printField("fog");
            System.out.println("---------------------");
            player2.battlefield.printField("all");
            System.out.println("Player 2, it's your turn:\n");
            isGameFinished = player1.battlefield.takeShot();
            if (isGameFinished) {
                break;
            }
            nextPlayer();

        }


    }

    private static void nextPlayer() {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Press Enter and pass the move to another player");
        scanner.nextLine();
    }
}