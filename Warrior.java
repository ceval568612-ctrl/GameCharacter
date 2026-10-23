class Warrior extends GameCharacter {
    public Warrior(String name) {
        super(name);
    }

    @Override
    public void attack() {
        System.out.println(name + " swings a mighty sword!");
    }

    @Override
    public void specialMove() {
        System.out.println(name + " uses Shield Bash!");
    }
}