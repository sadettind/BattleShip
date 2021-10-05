package battleship;

public class Main {

    public static void main(String[] args) {
        Battlefield battleField = new Battlefield();
        battleField.initiate();
        battleField.takeShot();
    }
}