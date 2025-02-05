import javax.swing.*;
import java.awt.*;

    public class StyledScientificCalculator {

        public static void main(String[] args) {
            SwingUtilities.invokeLater(() -> {
                JFrame frame = new JFrame("Scientific Calculator");
                frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                frame.setSize(900, 700);
                frame.setResizable(false);
                frame.setLayout(new BorderLayout());

                // Display area
                JTextField display = new JTextField("3.141593");
                display.setFont(new Font("Arial", Font.PLAIN, 50));
                display.setHorizontalAlignment(SwingConstants.RIGHT);
                display.setEditable(false);
                display.setBackground(Color.DARK_GRAY);
                display.setForeground(Color.WHITE);
                display.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
                frame.add(display, BorderLayout.NORTH);

                // Button area
                JPanel buttonPanel = new JPanel(new GridLayout(6, 6, 5, 5));
                buttonPanel.setBackground(Color.DARK_GRAY);

                // Button text array
                String[] buttons = {
                        "(", ")", "mc", "m+", "m-", "mr",
                        "2nd", "x²", "x³", "xʸ", "eˣ", "10ˣ",
                        "1/x", "²√x", "³√x", "ʸ√x", "ln", "log₁₀",
                        "x!", "sin", "cos", "tan", "e", "EE",
                        "Rad", "sinh", "cosh", "tanh", "π", "Rand",
                        "C", "±", "%", "÷", "7", "8", "9", "×",
                        "4", "5", "6", "−", "1", "2", "3", "+",
                        "0", ".", "="
                };

                // Button colors and styles
                Color buttonBackground = new Color(50, 50, 50);
                Color specialBackground = new Color(255, 140, 0);
                Color textColor = Color.WHITE;

                // Add buttons to panel
                for (String text : buttons) {
                    JButton button = new JButton(text);
                    button.setFont(new Font("Arial", Font.PLAIN, 20));
                    button.setForeground(textColor);

                    if (text.equals("=") || text.equals("+") || text.equals("−") || text.equals("×") || text.equals("÷")) {
                        button.setBackground(specialBackground);
                    } else {
                        button.setBackground(buttonBackground);
                    }

                    button.setFocusPainted(false);
                    button.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
                    buttonPanel.add(button);
                }

                frame.add(buttonPanel, BorderLayout.CENTER);
                frame.setVisible(true);
            });
        }
    }

