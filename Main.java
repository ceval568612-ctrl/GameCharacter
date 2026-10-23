public class Main {
    public static void main(String[] args) {
        GameCharacter warrior = new Warrior("Brader");
        GameCharacter mage = new Mage("GabMids");

        warrior.showName();
        warrior.attack();
        warrior.specialMove();

        System.out.println();

        mage.showName();
        mage.attack();
        mage.specialMove();
    }
}