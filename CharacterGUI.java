import javax.swing.*;
import java.awt.*;

public class CharacterGUI extends JFrame {

    // --- Game Models ---
    private GameCharacter player;
    private GameCharacter enemy;

    // --- HP Stats ---
    private int playerMaxHp = 100, playerHp = 100;
    private int enemyMaxHp = 100, enemyHp = 100;

    // --- UI Components ---
    private JProgressBar playerHpBar, enemyHpBar;
    private JLabel playerHpLabel, enemyHpLabel;
    private JTextArea dialogueArea;

    // --- Action Buttons ---
    private JButton btnAttack, btnSpecial, btnHeal, btnRun;

    public CharacterGUI() {
        // 1. Initialize Characters
        player = new Warrior("Warrior Brader");
        enemy = new Mage("Wild Mage GabMids");

        // 2. Frame Configuration
        setTitle("Pokémon Style Battle System");
        setSize(550, 500);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());

        // 3. Assemble UI Sections
        add(createBattleStage(), BorderLayout.NORTH);
        add(createDialogueBox(), BorderLayout.CENTER);
        add(createMenuPanel(), BorderLayout.SOUTH);

        // 4. Initial Battle Announcement
        appendDialogue("A wild " + enemy.name + " appeared!");
        appendDialogue("What will " + player.name + " do?");
    }

    // ==========================================
    // UI CREATION METHODS
    // ==========================================

    public JPanel createBattleStage() {
        JPanel stage = new JPanel(new GridLayout(2, 2, 10, 10));
        stage.setBackground(new Color(235, 245, 230));
        stage.setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15));

        // Enemy Info Panel
        enemyHpBar = createHpBar();
        enemyHpLabel = new JLabel(enemyHp + " / " + enemyMaxHp + " HP", SwingConstants.RIGHT);
        JPanel enemyPanel = createCharacterPanel(enemy.name, enemyHpBar, enemyHpLabel);

        // Player Info Panel
        playerHpBar = createHpBar();
        playerHpLabel = new JLabel(playerHp + " / " + playerMaxHp + " HP", SwingConstants.RIGHT);
        JPanel playerPanel = createCharacterPanel(player.name, playerHpBar, playerHpLabel);

        // Sprites (Emoji Placeholders)
        JLabel enemySprite = createSpriteLabel("🧙‍♂️");
        JLabel playerSprite = createSpriteLabel("⚔️");

        // Layout assembly
        stage.add(enemyPanel);
        stage.add(enemySprite);
        stage.add(playerSprite);
        stage.add(playerPanel);

        return stage;
    }

    public JScrollPane createDialogueBox() {
        dialogueArea = new JTextArea(4, 30);
        dialogueArea.setEditable(false);
        dialogueArea.setFont(new Font("Monospaced", Font.BOLD, 13));
        dialogueArea.setBackground(new Color(250, 250, 250));
        dialogueArea.setMargin(new Insets(8, 8, 8, 8));

        JScrollPane scrollPane = new JScrollPane(dialogueArea);
        scrollPane.setBorder(BorderFactory.createTitledBorder(
                BorderFactory.createLineBorder(Color.DARK_GRAY, 2), "Battle Text"));
        return scrollPane;
    }

    public JPanel createMenuPanel() {
        JPanel menu = new JPanel(new GridLayout(2, 2, 8, 8));
        menu.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        menu.setBackground(Color.DARK_GRAY);

        btnAttack = createActionButton("ATTACK", new Color(220, 50, 50));
        btnSpecial = createActionButton("SPECIAL MOVE", new Color(150, 50, 200));
        btnHeal = createActionButton("POTION (HEAL)", new Color(50, 180, 50));
        btnRun = createActionButton("RUN", new Color(100, 100, 100));

        // Action Listeners
        btnAttack.addActionListener(e -> playerTurn("Attack", 18));
        btnSpecial.addActionListener(e -> playerTurn("Special", 32));
        btnHeal.addActionListener(e -> healPlayer(25));
        btnRun.addActionListener(e -> appendDialogue("You can't run from a trainer battle!"));

        menu.add(btnAttack);
        menu.add(btnSpecial);
        menu.add(btnHeal);
        menu.add(btnRun);

        return menu;
    }

    // ==========================================
    // BATTLE LOGIC METHODS
    // ==========================================

    public void playerTurn(String attackType, int damage) {
        if (attackType.equals("Attack")) {
            player.attack();
        } else {
            player.specialMove();
        }

        enemyHp = Math.max(0, enemyHp - damage);
        updateHpDisplay();
        appendDialogue(player.name + " used " + attackType + "! Dealt " + damage + " dmg.");

        if (enemyHp == 0) {
            appendDialogue("The opponent " + enemy.name + " fainted!");
            appendDialogue("YOU WON THE BATTLE!");
            setButtonsEnabled(false);
            return;
        }

        // Enemy responds after 1.2 seconds
        setButtonsEnabled(false);
        Timer timer = new Timer(1200, e -> enemyTurn());
        timer.setRepeats(false);
        timer.start();
    }

    public void enemyTurn() {
        int enemyDmg = 15 + (int) (Math.random() * 10);
        enemy.attack();

        playerHp = Math.max(0, playerHp - enemyDmg);
        updateHpDisplay();
        appendDialogue(enemy.name + " attacks back and deals " + enemyDmg + " dmg!");

        if (playerHp == 0) {
            appendDialogue(player.name + " fainted! YOU LOST!");
        } else {
            setButtonsEnabled(true);
        }
    }

    public void healPlayer(int amount) {
        playerHp = Math.min(playerMaxHp, playerHp + amount);
        updateHpDisplay();
        appendDialogue(player.name + " used a Potion and recovered " + amount + " HP!");

        setButtonsEnabled(false);
        Timer timer = new Timer(1200, e -> enemyTurn());
        timer.setRepeats(false);
        timer.start();
    }

    // ==========================================
    // HELPER & UTILITY METHODS
    // ==========================================

    public JPanel createCharacterPanel(String name, JProgressBar hpBar, JLabel hpLabel) {
        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        panel.setOpaque(false);

        JLabel nameLabel = new JLabel(name);
        nameLabel.setFont(new Font("SansSerif", Font.BOLD, 14));

        panel.add(nameLabel);
        panel.add(Box.createVerticalStrut(5));
        panel.add(hpBar);
        panel.add(hpLabel);
        return panel;
    }

    public JLabel createSpriteLabel(String emoji) {
        JLabel sprite = new JLabel(emoji, SwingConstants.CENTER);
        sprite.setFont(new Font("Segoe UI Emoji", Font.PLAIN, 72));
        return sprite;
    }

    public JProgressBar createHpBar() {
        JProgressBar bar = new JProgressBar(0, 100);
        bar.setValue(100);
        bar.setForeground(new Color(46, 204, 113));
        bar.setStringPainted(false);
        return bar;
    }

    public JButton createActionButton(String text, Color bg) {
        JButton btn = new JButton(text);
        btn.setFont(new Font("SansSerif", Font.BOLD, 12));
        btn.setBackground(bg);
        btn.setForeground(Color.WHITE);
        btn.setFocusPainted(false);
        return btn;
    }

    public void updateHpDisplay() {
        // Update Player HP
        playerHpBar.setValue(playerHp);
        playerHpLabel.setText(playerHp + " / " + playerMaxHp + " HP");
        setHpBarColor(playerHpBar, playerHp);

        // Update Enemy HP
        enemyHpBar.setValue(enemyHp);
        enemyHpLabel.setText(enemyHp + " / " + enemyMaxHp + " HP");
        setHpBarColor(enemyHpBar, enemyHp);
    }

    public void setHpBarColor(JProgressBar bar, int hp) {
        if (hp > 50) {
            bar.setForeground(new Color(46, 204, 113)); // Green
        } else if (hp > 20) {
            bar.setForeground(new Color(241, 196, 15));  // Yellow
        } else {
            bar.setForeground(new Color(231, 76, 60));   // Red
        }
    }

    public void appendDialogue(String text) {
        dialogueArea.append("> " + text + "\n");
        dialogueArea.setCaretPosition(dialogueArea.getDocument().getLength());
    }

    public void setButtonsEnabled(boolean enabled) {
        btnAttack.setEnabled(enabled);
        btnSpecial.setEnabled(enabled);
        btnHeal.setEnabled(enabled);
        btnRun.setEnabled(enabled);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new CharacterGUI().setVisible(true));
    }
}