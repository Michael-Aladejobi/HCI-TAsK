import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

public class ModernGSMPhone extends JFrame {
    private boolean isPredictiveMode = false;
    private String currentText = "";
    private String currentCharacter = "";
    private String lastButtonPressed = "";
    private Timer timer;
    private final int TIMEOUT = 1000;
    private long pressStartTime;
    private final int LONG_PRESS_THRESHOLD = 700;

    // UserInterface components
    private JTextField displayField;
    private JPanel phoneBody;
    private JPanel keypadPanel;
    private JLabel statusLabel;
    private JPopupMenu suggestionPopup;

    private final Color PHONE_BODY_COLOR = new Color(40, 44, 52);
    private final Color DISPLAY_BG_COLOR = new Color(30, 30, 30);
    private final Color DISPLAY_TEXT_COLOR = new Color(220, 220, 220);
    private final Color BUTTON_COLOR = new Color(60, 65, 75);
    private final Color BUTTON_HOVER_COLOR = new Color(75, 80, 90);
    private final Color BUTTON_PRESS_COLOR = new Color(100, 100, 120);
    private final Color BUTTON_TEXT_COLOR = new Color(230, 230, 230);
    private final Color BUTTON_SUBTEXT_COLOR = new Color(180, 180, 180);

    // Fonts
    private final Font DISPLAY_FONT = new Font("Segoe UI", Font.BOLD, 28);
    private final Font BUTTON_FONT = new Font("Segoe UI", Font.BOLD, 20);
    private final Font SUBTEXT_FONT = new Font("Segoe UI", Font.PLAIN, 12);
    private final Font STATUS_FONT = new Font("Segoe UI", Font.PLAIN, 12);

    // Dictionary for predictive text
    private Dictionary dictionary;

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            try {
                // Set system look and feel for better integration
                UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
            } catch (Exception e) {
                e.printStackTrace();
            }
            new ModernGSMPhone();
        });
    }

    public ModernGSMPhone() {
        dictionary = new Dictionary(); // Initialize the dictionary
        suggestionPopup = new JPopupMenu(); // Initialize the suggestion popup

        // Set up the main frame
        setTitle("Aladejobi's GSM Phone");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(380, 700);
        setLocationRelativeTo(null); // Center on screen
        setResizable(false);

        phoneBody = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                Graphics2D g2d = (Graphics2D) g;
                g2d.setRenderingHint(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_QUALITY);

                // Create gradient background
                GradientPaint gradient = new GradientPaint(
                        0, 0, PHONE_BODY_COLOR,
                        0, getHeight(), new Color(20, 22, 26));
                g2d.setPaint(gradient);
                g2d.fillRect(0, 0, getWidth(), getHeight());
            }
        };
        phoneBody.setLayout(new BorderLayout(0, 10));
        phoneBody.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        setContentPane(phoneBody);

        // Create components
        createDisplayPanel();
        createKeypadPanel();
        createStatusPanel();

        setVisible(true);
    }

    private void createDisplayPanel() {
        JPanel displayPanel = new JPanel(new BorderLayout());
        displayPanel.setBackground(PHONE_BODY_COLOR);
        displayPanel.setBorder(BorderFactory.createEmptyBorder(0, 0, 10, 0));

        // Create a rounded panel for the display
        JPanel displayContainer = new JPanel(new BorderLayout());
        displayContainer.setBackground(DISPLAY_BG_COLOR);
        displayContainer.setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15));

        // Text display field
        displayField = new JTextField();
        displayField.setFont(DISPLAY_FONT);
        displayField.setForeground(DISPLAY_TEXT_COLOR);
        displayField.setBackground(DISPLAY_BG_COLOR);
        displayField.setHorizontalAlignment(JTextField.RIGHT);
        displayField.setEditable(false);
        displayField.setBorder(null);

        displayContainer.add(displayField, BorderLayout.CENTER);
        displayPanel.add(displayContainer, BorderLayout.CENTER);

        phoneBody.add(displayPanel, BorderLayout.NORTH);
    }

    private void createKeypadPanel() {
        keypadPanel = new JPanel(new GridLayout(4, 3, 10, 10));
        keypadPanel.setBackground(PHONE_BODY_COLOR);

        String[][] buttonLabels = {
                { "1", "." },
                { "2", "ABC" },
                { "3", "DEF" },
                { "4", "GHI" },
                { "5", "JKL" },
                { "6", "MNO" },
                { "7", "PQRS" },
                { "8", "TUV" },
                { "9", "WXYZ" },
                { "*", "+ ✏" },
                { "0", "Space" },
                { "#", "@? 📖" }
        };

        // Create each button with modern styling
        for (String[] labelPair : buttonLabels) {
            String buttonKey = labelPair[0];
            String subText = labelPair[1];

            // Create a custom button with rounded corners
            JButton button = createStylishButton(buttonKey, subText);

            // Add mouse listeners for button press effects
            button.addMouseListener(new MouseAdapter() {
                @Override
                public void mousePressed(MouseEvent e) {
                    pressStartTime = System.currentTimeMillis();
                    button.setBackground(BUTTON_PRESS_COLOR);

                    // Check if the button is "0" and handle long press for Prediction Mode
                    if (buttonKey.equals("0")) {
                        timer = new Timer();
                        timer.schedule(new TimerTask() {
                            @Override
                            public void run() {
                                SwingUtilities.invokeLater(() -> {
                                    togglePredictionMode();
                                });
                            }
                        }, 1000); // 1-second hold activates Prediction Mode
                    }
                }

                @Override
                public void mouseReleased(MouseEvent e) {
                    button.setBackground(BUTTON_COLOR);
                    if (timer != null) {
                        timer.cancel();
                    }

                    long pressDuration = System.currentTimeMillis() - pressStartTime;

                    if (pressDuration < LONG_PRESS_THRESHOLD && !buttonKey.equals("0")) {
                        handleButtonPress(buttonKey);
                    }
                }

                @Override
                public void mouseEntered(MouseEvent e) {
                    if (!button.getModel().isPressed()) {
                        button.setBackground(BUTTON_HOVER_COLOR);
                    }
                }

                @Override
                public void mouseExited(MouseEvent e) {
                    if (!button.getModel().isPressed()) {
                        button.setBackground(BUTTON_COLOR);
                    }
                }
            });

            keypadPanel.add(button);
        }

        // Add delete button
        JButton deleteButton = createStylishButton("⌫", "Delete");
        deleteButton.addActionListener(e -> handleDelete());
        keypadPanel.add(deleteButton);

        phoneBody.add(keypadPanel, BorderLayout.CENTER);
    }

    private void togglePredictionMode() {
        isPredictiveMode = !isPredictiveMode;
        suggestionPopup.setVisible(false); // Hide suggestions when toggling mode
        statusLabel.setText(isPredictiveMode ? "Predictive text mode enabled" : "Standard text mode");
    }

    private void handleButtonPress(String buttonKey) {
        if (isPredictiveMode) {
            currentText += buttonKey;
            displayField.setText(currentText); // Update the display with the current text
            List<String> predictions = dictionary.predictNextWords(currentText);
            showSuggestions(predictions);
        } else {
            // Standard mode: Cycle through letters
            if (!buttonKey.equals(lastButtonPressed)) {
                lockInCharacter();
                lastButtonPressed = buttonKey;
                currentCharacter = "";
            }

            String[] letters = getButtonMap().get(buttonKey);
            if (letters != null) {
                int currentIndex = getCurrentIndex(letters);
                currentCharacter = letters[(currentIndex + 1) % letters.length];
                updateDisplay();
            }

            if (timer != null) {
                timer.cancel();
            }

            timer = new Timer();
            timer.schedule(new TimerTask() {
                @Override
                public void run() {
                    SwingUtilities.invokeLater(() -> {
                        lockInCharacter();
                        updateDisplay();
                    });
                }
            }, TIMEOUT);
        }
    }

    private void handleDelete() {
        if (!currentText.isEmpty()) {
            currentText = currentText.substring(0, currentText.length() - 1); // Remove the last character
            displayField.setText(currentText); // Update the display
            if (isPredictiveMode) {
                List<String> predictions = dictionary.predictNextWords(currentText);
                showSuggestions(predictions); // Update suggestions
            }
        }
    }

    private void showSuggestions(List<String> predictions) {
        suggestionPopup.removeAll(); // Clear previous suggestions

        for (String suggestion : predictions) {
            JMenuItem item = new JMenuItem(suggestion);
            item.addActionListener(e -> {
                currentText = suggestion; // Update the current text with the selected suggestion
                displayField.setText(currentText);
                suggestionPopup.setVisible(false); // Hide the popup after selection
            });
            suggestionPopup.add(item);
        }

        if (!predictions.isEmpty()) {
            suggestionPopup.show(displayField, 0, displayField.getHeight()); // Show the popup below the display field
        }
    }

    private void lockInCharacter() {
        if (!currentCharacter.isEmpty()) {
            currentText += currentCharacter;
        }
        currentCharacter = "";
        lastButtonPressed = "";
    }

    private void updateDisplay() {
        displayField.setText(currentText + currentCharacter);
    }

    private int getCurrentIndex(String[] letters) {
        for (int i = 0; i < letters.length; i++) {
            if (letters[i].equals(currentCharacter)) {
                return i;
            }
        }
        return -1;
    }

    private java.util.Map<String, String[]> getButtonMap() {
        java.util.Map<String, String[]> buttonMap = new java.util.HashMap<>();
        buttonMap.put("1", new String[] { "1", ".", ",", "?" });
        buttonMap.put("2", new String[] { "A", "B", "C", "2" });
        buttonMap.put("3", new String[] { "D", "E", "F", "3" });
        buttonMap.put("4", new String[] { "G", "H", "I", "4" });
        buttonMap.put("5", new String[] { "J", "K", "L", "5" });
        buttonMap.put("6", new String[] { "M", "N", "O", "6" });
        buttonMap.put("7", new String[] { "P", "Q", "R", "S", "7" });
        buttonMap.put("8", new String[] { "T", "U", "V", "8" });
        buttonMap.put("9", new String[] { "W", "X", "Y", "Z", "9" });
        buttonMap.put("0", new String[] { "0", " " });
        buttonMap.put("*", new String[] { "*", "+", "-", "=" });
        buttonMap.put("#", new String[] { "#", "@", "/", "_" });
        return buttonMap;
    }

    private void createStatusPanel() {
        JPanel statusPanel = new JPanel(new BorderLayout());
        statusPanel.setBackground(PHONE_BODY_COLOR);
        statusPanel.setBorder(BorderFactory.createEmptyBorder(5, 0, 0, 0));

        statusLabel = new JLabel("Ready", JLabel.CENTER);
        statusLabel.setFont(STATUS_FONT);
        statusLabel.setForeground(new Color(150, 150, 150));

        statusPanel.add(statusLabel, BorderLayout.CENTER);

        phoneBody.add(statusPanel, BorderLayout.PAGE_END);
    }

    private JButton createStylishButton(String mainText, String subText) {
        JButton button = new JButton();
        button.setLayout(new BorderLayout());
        button.setBackground(BUTTON_COLOR);
        button.setBorderPainted(false);
        button.setFocusPainted(false);
        button.setContentAreaFilled(false);
        button.setOpaque(true);

        // Main text label
        JLabel mainLabel = new JLabel(mainText, JLabel.CENTER);
        mainLabel.setFont(BUTTON_FONT);
        mainLabel.setForeground(BUTTON_TEXT_COLOR);

        // Sub text label
        JLabel subLabel = new JLabel(subText, JLabel.CENTER);
        subLabel.setFont(SUBTEXT_FONT);
        subLabel.setForeground(BUTTON_SUBTEXT_COLOR);

        // Add labels to button
        button.add(mainLabel, BorderLayout.CENTER);
        button.add(subLabel, BorderLayout.SOUTH);

        return button;
    }
}