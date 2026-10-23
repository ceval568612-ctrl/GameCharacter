abstract class GameCharacter {
    String name;

    public GameCharacter(String name) {
        this.name = name;
    }

    // Abstract methods (must be implemented by subclasses)
    public abstract void attack();
    public abstract void specialMove();

    // Concrete method
    public void showName() {
        System.out.println("Character: " + name);
    }
}