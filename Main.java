import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.Arrays;

public class Main {

    static void solve(String stringToPrint, double delay, JTextArea outputArea, JButton startButton) {
        String target = stringToPrint;
        double delayInMS = delay;
        char[] current = new char[target.length()];
        Arrays.fill(current, ' ');

        for (int i = 0; i < target.length(); i++) {
            while (current[i] != target.charAt(i)) {
                outputArea.append(new String(current) + "\n");
                outputArea.setCaretPosition(outputArea.getDocument().getLength());
                current[i]++;

                try {
                    Thread.sleep((long) delayInMS);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
        outputArea.append(new String(current) + "\n");
        outputArea.setCaretPosition(outputArea.getDocument().getLength());

        startButton.setEnabled(true);
    }

    public static void main(String[] args) {
        JFrame frame = new JFrame("String Printer");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLayout(new BorderLayout());

        frame.setIconImage(new ImageIcon("icon.png").getImage());

        JPanel contentPanel = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                Graphics2D g2d = (Graphics2D) g;
                GradientPaint gradient = new GradientPaint(0, 0, new Color(40, 40, 40), 0, getHeight(), new Color(0x2C3E50));
                g2d.setPaint(gradient);
                g2d.fillRect(0, 0, getWidth(), getHeight());
            }
        };
        contentPanel.setLayout(new BorderLayout());

        JPanel inputPanel = new JPanel();
        inputPanel.setLayout(new FlowLayout(FlowLayout.CENTER, 20, 30));
        inputPanel.setOpaque(false);

        JTextField inputField = new JTextField(20);
        inputField.setFont(new Font("Segoe UI", Font.PLAIN, 16));
        inputField.setPreferredSize(new Dimension(250, 40));
        inputField.setBackground(new Color(0x34495E));
        inputField.setForeground(Color.WHITE);
        inputField.setBorder(BorderFactory.createLineBorder(new Color(0xBDC3C7), 2));
        inputField.setCaretColor(Color.WHITE);
        inputField.setText("Enter the string...");
        inputField.setToolTipText("Enter the string to animate");
        inputField.setSelectionColor(new Color(0x2980B9));
        inputField.setSelectedTextColor(Color.WHITE);
        
        inputField.addFocusListener(new FocusAdapter() {
            @Override
            public void focusGained(FocusEvent e) {
                if (inputField.getText().equals("Enter the string...")) {
                    inputField.setText("");
                }
            }

            @Override
            public void focusLost(FocusEvent e) {
                if (inputField.getText().isEmpty()) {
                    inputField.setText("Enter the string...");
                }
            }
        });

        JTextField delayField = new JTextField(5);
        delayField.setFont(new Font("Segoe UI", Font.PLAIN, 16));
        delayField.setPreferredSize(new Dimension(100, 40));
        delayField.setBackground(new Color(0x34495E));
        delayField.setForeground(Color.WHITE);
        delayField.setBorder(BorderFactory.createLineBorder(new Color(0xBDC3C7), 2));
        delayField.setCaretColor(Color.WHITE);
        delayField.setToolTipText("Enter the delay in milliseconds");
        delayField.setText("100");
        delayField.setSelectionColor(new Color(0x2980B9));
        delayField.setSelectedTextColor(Color.WHITE);
        
        delayField.addFocusListener(new FocusAdapter() {
            @Override
            public void focusGained(FocusEvent e) {
                if (delayField.getText().equals("100")) {
                    delayField.setText("");
                }
            }

            @Override
            public void focusLost(FocusEvent e) {
                if (delayField.getText().isEmpty()) {
                    delayField.setText("100");
                }
            }
        });
        
        delayField.addKeyListener(new KeyAdapter() {
            @Override
            public void keyTyped(KeyEvent e) {
                char c = e.getKeyChar();
                if (!(Character.isDigit(c) || c == '.' || c == KeyEvent.VK_BACK_SPACE)) {
                    e.consume();
                }
            }
        });

        JButton startButton = new JButton("Start Animation");
        startButton.setFont(new Font("Segoe UI", Font.BOLD, 16));
        startButton.setBackground(new Color(0x1ABC9C));
        startButton.setForeground(Color.WHITE);
        startButton.setFocusPainted(false);
        startButton.setPreferredSize(new Dimension(180, 40));
        startButton.setBorder(BorderFactory.createEmptyBorder(10, 20, 10, 20));
        startButton.setBorderPainted(false);
        startButton.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        startButton.setToolTipText("Click to start the animation");

        inputPanel.add(new JLabel("Enter String:"));
        inputPanel.add(inputField);
        inputPanel.add(new JLabel("Enter Delay (ms):"));
        inputPanel.add(delayField);
        inputPanel.add(startButton);

        JTextArea outputArea = new JTextArea(10, 30);
        outputArea.setEditable(false);
        outputArea.setFont(new Font("Monospaced", Font.PLAIN, 16));
        outputArea.setBackground(new Color(0x2C3E50));
        outputArea.setForeground(Color.WHITE);
        outputArea.setLineWrap(true);
        JScrollPane scrollPane = new JScrollPane(outputArea);
        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
        scrollPane.getVerticalScrollBar().setUnitIncrement(16);

        contentPanel.add(inputPanel, BorderLayout.NORTH);
        contentPanel.add(scrollPane, BorderLayout.CENTER);

        frame.add(contentPanel);

        startButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                startButton.setEnabled(false);

                String input = inputField.getText();
                double delayInMilliseconds = Double.parseDouble(delayField.getText());

                outputArea.setText("");

                new Thread(() -> solve(input, delayInMilliseconds, outputArea, startButton)).start();
            }
        });

        frame.setExtendedState(JFrame.MAXIMIZED_BOTH);
        frame.setVisible(true);
    }
}
