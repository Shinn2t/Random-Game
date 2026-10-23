import javax.swing.*;
import java.awt.*;
import java.util.Random;

public class CharacterGUI extends JFrame {

    private JTextField txtName;
    private JTextField txtLevel;
    private JComboBox<String> cboType;

    private JTextArea outputArea;
    private CharacterManager manager;

    private JProgressBar leftHP;
    private JProgressBar rightHP;
    private JLabel leftName;
    private JLabel rightName;
    private JLabel leftType;
    private JLabel rightType;

    private final Random random = new Random();

    public CharacterGUI() {
        manager = new CharacterManager();

        setTitle("Fantasy VS Battle");
        setSize(950, 650);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        createGUI();
        refreshBattle();

        setVisible(true);
    }

    private void createGUI() {
        setLayout(new BorderLayout(8, 8));

        // TOP: Character creation form
        JPanel topPanel = new JPanel(new GridLayout(3, 2, 8, 5));
        topPanel.setBorder(BorderFactory.createTitledBorder(
                "Create Your Fighter"));

        txtName = new JTextField();
        txtLevel = new JTextField();

        cboType = new JComboBox<>(new String[]{
                "Warrior", "Mage", "Archer", "Healer"
        });

        topPanel.add(new JLabel("Character Name:"));
        topPanel.add(txtName);

        topPanel.add(new JLabel("Level:"));
        topPanel.add(txtLevel);

        topPanel.add(new JLabel("Character Type:"));
        topPanel.add(cboType);

        add(topPanel, BorderLayout.NORTH);

        // CENTER: Two fighters facing each other
        JPanel battlePanel = new JPanel(new BorderLayout(10, 10));
        battlePanel.setBorder(BorderFactory.createEmptyBorder(
                10, 15, 5, 15));

        JPanel fightersPanel = new JPanel(new GridLayout(1, 3, 15, 5));

        JPanel leftPanel = createFighterPanel(true);
        JPanel rightPanel = createFighterPanel(false);

        JLabel vsLabel = new JLabel("VS", SwingConstants.CENTER);
        vsLabel.setFont(new Font("Arial", Font.BOLD, 38));
        vsLabel.setForeground(Color.RED);

        fightersPanel.add(leftPanel);
        fightersPanel.add(vsLabel);
        fightersPanel.add(rightPanel);

        outputArea = new JTextArea(9, 40);
        outputArea.setEditable(false);
        outputArea.setLineWrap(true);
        outputArea.setWrapStyleWord(true);
        outputArea.setFont(new Font("Monospaced", Font.PLAIN, 13));

        JScrollPane scrollPane = new JScrollPane(outputArea);
        scrollPane.setBorder(BorderFactory.createTitledBorder(
                "Battle Log"));

        battlePanel.add(fightersPanel, BorderLayout.NORTH);
        battlePanel.add(scrollPane, BorderLayout.CENTER);

        add(battlePanel, BorderLayout.CENTER);

        // BOTTOM: Action buttons
        JPanel buttonPanel = new JPanel(new FlowLayout(
                FlowLayout.CENTER, 7, 8));

        JButton btnAdd = new JButton("Add");
        JButton btnView = new JButton("View");
        JButton btnSearch = new JButton("Search");
        JButton btnUpdate = new JButton("Update");
        JButton btnDelete = new JButton("Delete");

        JButton btnAttack = new JButton("Attack");
        JButton btnSpecial = new JButton("Special Move");
        JButton btnFight = new JButton("Fight Round");

        buttonPanel.add(btnAdd);
        buttonPanel.add(btnView);
        buttonPanel.add(btnSearch);
        buttonPanel.add(btnUpdate);
        buttonPanel.add(btnDelete);
        buttonPanel.add(btnAttack);
        buttonPanel.add(btnSpecial);
        buttonPanel.add(btnFight);

        add(buttonPanel, BorderLayout.SOUTH);

        // Button actions
        btnAdd.addActionListener(e -> addCharacter());
        btnView.addActionListener(e -> viewCharacters());
        btnSearch.addActionListener(e -> searchCharacter());
        btnUpdate.addActionListener(e -> updateCharacter());
        btnDelete.addActionListener(e -> deleteCharacter());

        btnAttack.addActionListener(e -> attackCharacter());
        btnSpecial.addActionListener(e -> specialMove());
        btnFight.addActionListener(e -> fight());
    }

    private JPanel createFighterPanel(boolean left) {
        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        panel.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(
                        left ? new Color(40, 160, 70)
                             : new Color(190, 60, 60), 2),
                BorderFactory.createEmptyBorder(12, 10, 12, 10)));

        JLabel nameLabel = new JLabel(
                left ? "Fighter 1" : "Fighter 2");
        nameLabel.setFont(new Font("Arial", Font.BOLD, 19));
        nameLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

        JLabel typeLabel = new JLabel("Waiting for fighter...");
        typeLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

        JProgressBar hp = new JProgressBar(0, 100);
        hp.setValue(100);
        hp.setString("100 / 100 HP");
        hp.setStringPainted(true);
        hp.setForeground(new Color(35, 190, 75));
        hp.setBackground(Color.LIGHT_GRAY);
        hp.setPreferredSize(new Dimension(220, 28));
        hp.setMaximumSize(new Dimension(Integer.MAX_VALUE, 28));

        JLabel iconLabel = new JLabel(
                left ? "⚔" : "🛡", SwingConstants.CENTER);
        iconLabel.setFont(new Font("Arial", Font.PLAIN, 55));
        iconLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

        panel.add(nameLabel);
        panel.add(Box.createVerticalStrut(8));
        panel.add(typeLabel);
        panel.add(Box.createVerticalStrut(15));
        panel.add(iconLabel);
        panel.add(Box.createVerticalStrut(15));
        panel.add(hp);

        if (left) {
            leftName = nameLabel;
            leftType = typeLabel;
            leftHP = hp;
        } else {
            rightName = nameLabel;
            rightType = typeLabel;
            rightHP = hp;
        }

        return panel;
    }

    private GameCharacter getFighter(int index) {
        if (manager.getCharacters().size() < 2) {
            return null;
        }

        if (index == 0) {
            return manager.getCharacters().get(0);
        }

        return manager.getCharacters().get(1);
    }

    private void refreshBattle() {
        GameCharacter fighter1 = getFighter(0);
        GameCharacter fighter2 = getFighter(1);

        if (fighter1 == null || fighter2 == null) {
            leftName.setText("Fighter 1");
            leftType.setText("Waiting for fighter...");
            rightName.setText("Fighter 2");
            rightType.setText("Waiting for fighter...");

            updateHealthBar(leftHP, 100);
            updateHealthBar(rightHP, 100);
            return;
        }

        leftName.setText(fighter1.getName());
        leftType.setText(fighter1.getClass().getSimpleName()
                + " | Level " + fighter1.getLevel());

        rightName.setText(fighter2.getName());
        rightType.setText(fighter2.getClass().getSimpleName()
                + " | Level " + fighter2.getLevel());

        updateHealthBar(leftHP, fighter1.getHealth());
        updateHealthBar(rightHP, fighter2.getHealth());
    }

    private void updateHealthBar(JProgressBar bar, int hp) {
        bar.setValue(hp);
        bar.setString(hp + " / 100 HP");

        if (hp > 50) {
            bar.setForeground(new Color(35, 190, 75));
        } else if (hp > 20) {
            bar.setForeground(Color.ORANGE);
        } else {
            bar.setForeground(Color.RED);
        }
    }

    private boolean readyToBattle() {
        GameCharacter fighter1 = getFighter(0);
        GameCharacter fighter2 = getFighter(1);

        if (fighter1 == null || fighter2 == null) {
            JOptionPane.showMessageDialog(this,
                    "Add at least 2 characters first!");
            return false;
        }

        if (fighter1.getHealth() <= 0 ||
                fighter2.getHealth() <= 0) {
            JOptionPane.showMessageDialog(this,
                    "The battle is over! One fighter has lost.");
            return false;
        }

        return true;
    }

    private void addCharacter() {
        String name = txtName.getText().trim();

        if (name.isEmpty()) {
            JOptionPane.showMessageDialog(this,
                    "Name cannot be empty!");
            return;
        }

        int level;

        try {
            level = Integer.parseInt(txtLevel.getText().trim());

            if (level <= 0) {
                throw new NumberFormatException();
            }
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this,
                    "Level must be a positive number!");
            return;
        }

        String type = (String) cboType.getSelectedItem();
        GameCharacter character;

        switch (type) {
            case "Warrior":
                character = new Warrior(name, level);
                break;
            case "Mage":
                character = new Mage(name, level);
                break;
            case "Archer":
                character = new Archer(name, level);
                break;
            case "Healer":
                character = new Healer(name, level);
                break;
            default:
                return;
        }

        manager.addCharacter(character);

        outputArea.append("Added " + name + " the "
                + type + " (Level " + level + ").\n");

        txtName.setText("");
        txtLevel.setText("");

        refreshBattle();
    }

    private void attackCharacter() {
        if (!readyToBattle()) {
            return;
        }

        GameCharacter attacker = getFighter(0);
        GameCharacter target = getFighter(1);

        int damage = random.nextInt(11) + 15;
        target.takeDamage(damage);

        outputArea.append(attacker.getName() + " "
                + attacker.attack() + "\n");

        outputArea.append(target.getName() + " takes "
                + damage + " damage!\n");

        checkWinner();
        refreshBattle();
    }

    private void specialMove() {
        if (!readyToBattle()) {
            return;
        }

        GameCharacter attacker = getFighter(0);
        GameCharacter target = getFighter(1);

        if (attacker instanceof Healer) {
            int oldHP = attacker.getHealth();
            attacker.heal(25);
            int healed = attacker.getHealth() - oldHP;

            outputArea.append(attacker.getName() + " "
                    + attacker.specialMove() + "\n");
            outputArea.append(attacker.getName()
                    + " recovers " + healed + " HP!\n");
        } else {
            int damage = random.nextInt(11) + 25;
            target.takeDamage(damage);

            outputArea.append(attacker.getName() + " "
                    + attacker.specialMove() + "\n");
            outputArea.append(target.getName() + " takes "
                    + damage + " special damage!\n");
        }

        checkWinner();
        refreshBattle();
    }

    private void fight() {
        if (!readyToBattle()) {
            return;
        }

        GameCharacter fighter1 = getFighter(0);
        GameCharacter fighter2 = getFighter(1);

        // Both fighters attack during one round.
        GameCharacter first = random.nextBoolean()
                ? fighter1 : fighter2;
        GameCharacter second = first == fighter1
                ? fighter2 : fighter1;

        performAttack(first, second);

        // Stop the round if the first attack defeated the opponent.
        if (second.getHealth() > 0) {
            performAttack(second, first);
        }

        checkWinner();
        refreshBattle();
    }

    private void performAttack(GameCharacter attacker,
                               GameCharacter target) {
        int damage = random.nextInt(11) + 15;
        target.takeDamage(damage);

        outputArea.append(attacker.getName() + " "
                + attacker.attack() + "\n");
        outputArea.append(target.getName() + " takes "
                + damage + " damage!\n\n");
    }

    private void checkWinner() {
        GameCharacter fighter1 = getFighter(0);
        GameCharacter fighter2 = getFighter(1);

        if (fighter1.getHealth() <= 0) {
            outputArea.append("GAME OVER! "
                    + fighter2.getName() + " WINS!\n\n");
        } else if (fighter2.getHealth() <= 0) {
            outputArea.append("GAME OVER! "
                    + fighter1.getName() + " WINS!\n\n");
        }
    }

    private void viewCharacters() {
        outputArea.setText("");

        for (GameCharacter c : manager.getCharacters()) {
            outputArea.append(c.toString() + "\n");
            outputArea.append("Attack: " + c.attack() + "\n");
            outputArea.append("Special: "
                    + c.specialMove() + "\n\n");
        }

        refreshBattle();
    }

    private void searchCharacter() {
        String name = txtName.getText().trim();
        GameCharacter c = manager.searchCharacter(name);

        if (c == null) {
            JOptionPane.showMessageDialog(this,
                    "Character not found.");
            return;
        }

        outputArea.setText(c.toString() + "\n"
                + "Attack: " + c.attack() + "\n"
                + "Special: " + c.specialMove() + "\n");
    }

    private void updateCharacter() {
        String oldName = JOptionPane.showInputDialog(this,
                "Enter character name to update:");

        if (oldName == null || oldName.trim().isEmpty()) {
            return;
        }

        String newName = JOptionPane.showInputDialog(this,
                "Enter new name:");

        if (newName == null || newName.trim().isEmpty()) {
            return;
        }

        if (manager.updateCharacter(
                oldName.trim(), newName.trim())) {
            outputArea.append("Character renamed to "
                    + newName.trim() + ".\n");
            refreshBattle();
        } else {
            JOptionPane.showMessageDialog(this,
                    "Character not found.");
        }
    }

    private void deleteCharacter() {
        String name = txtName.getText().trim();

        if (name.isEmpty()) {
            JOptionPane.showMessageDialog(this,
                    "Enter a character name first.");
            return;
        }

        if (manager.removeCharacter(name)) {
            outputArea.append("Removed " + name + ".\n");
            refreshBattle();
        } else {
            JOptionPane.showMessageDialog(this,
                    "Character not found.");
        }
    }
}