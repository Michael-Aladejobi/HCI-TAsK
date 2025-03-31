import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.HashMap;

public class FeaturePhoneSimulator {
    private JFrame frame;
    private JTextField textField;
    private StringBuilder currentInput = new StringBuilder();
    private HashMap<Integer, String> keyMappings;
    private int lastKey = -1;
    private long lastPressTime = 0;
    private int charIndex = 0;
    private javax.swing.Timer holdTimer;
    private boolean isNumberMode = false;

    public FeaturePhoneSimulator() {
        frame = new JFrame("Aladejobi's Phone");
        frame.setSize(380, 520);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLayout(new BorderLayout());
        frame.getContentPane().setBackground(Color.BLACK);

        textField = new JTextField();
        textField.setFont(new Font("Arial", Font.BOLD, 22));
        textField.setHorizontalAlignment(JTextField.CENTER);
        textField.setEditable(false);
        textField.setBackground(Color.DARK_GRAY);
        textField.setForeground(Color.WHITE);
        textField.setBorder(BorderFactory.createLineBorder(Color.CYAN, 2));
        frame.add(textField, BorderLayout.NORTH);

        keyMappings = new HashMap<>();
        keyMappings.put(2, "ABC");
        keyMappings.put(3, "DEF");
        keyMappings.put(4, "GHI");
        keyMappings.put(5, "JKL");
        keyMappings.put(6, "MNO");
        keyMappings.put(7, "PQRS");
        keyMappings.put(8, "TUV");
        keyMappings.put(9, "WXYZ");

        JPanel buttonPanel = new JPanel(new GridLayout(4, 3, 8, 8));
        buttonPanel.setBackground(Color.BLACK);

        for (int i = 1; i <= 9; i++) {
            buttonPanel.add(createStyledButton(i));
        }
        buttonPanel.add(createStyledButton(0));

        JButton eraseButton = createStyledButton("DEL", -1);
        eraseButton.addActionListener(e -> handleErase());
        buttonPanel.add(eraseButton);

        frame.add(buttonPanel, BorderLayout.CENTER);
        frame.setFocusable(true);
        frame.setVisible(true);
    }

    private JButton createStyledButton(int number) {
        String label = String.valueOf(number);
        if (keyMappings.containsKey(number)) {
            label += " " + keyMappings.get(number) + " ";
        }
        return createStyledButton(label, number);
    }

    private JButton createStyledButton(String label, int number) {
        JButton button = new JButton(label);
        button.setFont(new Font("Arial", Font.BOLD, 18));
        button.setForeground(Color.WHITE);
        button.setBackground(new Color(0, 51, 102));
        button.setBorder(BorderFactory.createLineBorder(Color.CYAN, 2));
        button.setFocusPainted(false);
        button.setOpaque(true);

        button.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                button.setBackground(new Color(0, 102, 204));
            }

            public void mouseExited(java.awt.event.MouseEvent evt) {
                button.setBackground(new Color(0, 51, 102));
            }
        });

        if (number != -1) {
            // Button Press & Hold Logic
            button.addMouseListener(new MouseAdapter() {
                @Override
                public void mousePressed(MouseEvent e) {
                    isNumberMode = false;
                    handleKeyPress(number);

                    holdTimer = new javax.swing.Timer(1000, evt -> {
                        isNumberMode = true;
                        insertNumber(number);
                    });
                    holdTimer.setRepeats(false);
                    holdTimer.start();
                }

                @Override
                public void mouseReleased(MouseEvent e) {
                    if (holdTimer != null) {
                        holdTimer.stop();
                    }
                }
            });
        }

        return button;
    }

    private void handleKeyPress(int key) {
        if (isNumberMode)
            return;

        long currentTime = System.currentTimeMillis();
        if (key == lastKey && currentTime - lastPressTime < 1000) {
            charIndex = (charIndex + 1) % keyMappings.getOrDefault(key, "").length();
        } else {
            charIndex = 0;
            currentInput.append(" ");
        }
        lastKey = key;
        lastPressTime = currentTime;

        if (keyMappings.containsKey(key)) {
            char letter = keyMappings.get(key).charAt(charIndex);
            currentInput.setCharAt(currentInput.length() - 1, letter);
            textField.setText(currentInput.toString());
        }
    }

    private void insertNumber(int number) {
        currentInput.append(number);
        textField.setText(currentInput.toString());
    }

    private void handleErase() {
        if (currentInput.length() > 0) {
            currentInput.deleteCharAt(currentInput.length() - 1);
            textField.setText(currentInput.toString());
        }
    }

    private void showSuggestions(String text) {
        suggestionPopup.removeAll();
        java.util.List<String> suggestions = prediction.predictNextWords(text);

        for (String suggestion : suggestions) {
            JMenuItem item = new JMenuItem(suggestion);
            item.addActionListener(e -> {
                currentInput = new StringBuilder(suggestion);
                textField.setText(suggestion);
                suggestionPopup.setVisible(false);
            });
            suggestionPopup.add(item);
        }
        if (!suggestions.isEmpty()) {
            suggestionPopup.show(textField, 0, textField.getHeight());
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(FeaturePhoneSimulator::new);
    }
}