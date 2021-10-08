package battleship;

public class Player {
    String name;
    Battlefield battlefield;

    public Player(String name) {
        this.name = name;
        this.battlefield = new Battlefield();
    }
}
