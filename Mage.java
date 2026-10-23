class Mage extends GameCharacter {
    public Mage(String name) {
        super(name);
    }

    @Override
    public void attack() {
        System.out.println(name + " casts a fireball!");
    }

    @Override
    public void specialMove() {
        System.out.println(name + " uses Lightning Storm!");
    }
}