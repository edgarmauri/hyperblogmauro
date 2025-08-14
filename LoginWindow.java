import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.util.Arrays;

public class LoginWindow extends JFrame {
    private final JTextField usernameField;
    private final JPasswordField passwordField;
    private final JButton loginButton;
    private final JButton cancelButton;
    private final JCheckBox showPasswordCheckBox;

    public LoginWindow() {
        super("Login");
        usernameField = new JTextField(20);
        passwordField = new JPasswordField(20);
        loginButton = new JButton("Login");
        cancelButton = new JButton("Cancel");
        showPasswordCheckBox = new JCheckBox("Show password");

        configureComponents();
        layoutComponents();
        attachEventHandlers();

        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setResizable(false);
        pack();
        setLocationRelativeTo(null);
    }

    private void configureComponents() {
        passwordField.setEchoChar('•');
        loginButton.setMnemonic('L');
        cancelButton.setMnemonic('C');
    }

    private void attachEventHandlers() {
        loginButton.addActionListener(this::handleLogin);
        cancelButton.addActionListener(e -> dispose());
        showPasswordCheckBox.addActionListener(e -> {
            if (showPasswordCheckBox.isSelected()) {
                passwordField.setEchoChar((char) 0);
            } else {
                passwordField.setEchoChar('•');
            }
        });
        passwordField.addActionListener(this::handleLogin);
        usernameField.addActionListener(this::handleLogin);
        getRootPane().setDefaultButton(loginButton);
    }

    private void handleLogin(ActionEvent event) {
        String username = usernameField.getText().trim();
        char[] password = passwordField.getPassword();

        if (username.isEmpty() || password.length == 0) {
            JOptionPane.showMessageDialog(this, "Please enter username and password.", "Missing information", JOptionPane.WARNING_MESSAGE);
            Arrays.fill(password, '\0');
            return;
        }

        boolean authenticated = authenticate(username, password);
        Arrays.fill(password, '\0');

        if (authenticated) {
            JOptionPane.showMessageDialog(this, "Welcome, " + username + "!", "Success", JOptionPane.INFORMATION_MESSAGE);
        } else {
            JOptionPane.showMessageDialog(this, "Invalid username or password.", "Authentication failed", JOptionPane.ERROR_MESSAGE);
            passwordField.setText("");
            passwordField.requestFocusInWindow();
        }
    }

    private boolean authenticate(String username, char[] password) {
        return "admin".equals(username) && Arrays.equals(password, "password".toCharArray());
    }

    private void layoutComponents() {
        JPanel contentPanel = new JPanel(new GridBagLayout());
        contentPanel.setBorder(BorderFactory.createEmptyBorder(16, 16, 16, 16));
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(6, 6, 6, 6);
        gbc.anchor = GridBagConstraints.WEST;

        JLabel titleLabel = new JLabel("Please sign in");
        titleLabel.setFont(titleLabel.getFont().deriveFont(Font.BOLD, titleLabel.getFont().getSize() + 2f));
        gbc.gridx = 0; gbc.gridy = 0; gbc.gridwidth = 2; gbc.fill = GridBagConstraints.NONE; gbc.weightx = 1.0;
        contentPanel.add(titleLabel, gbc);

        gbc.gridwidth = 1; gbc.gridy = 1; gbc.gridx = 0; gbc.fill = GridBagConstraints.NONE; gbc.weightx = 0;
        contentPanel.add(new JLabel("Username:"), gbc);
        gbc.gridx = 1; gbc.fill = GridBagConstraints.HORIZONTAL; gbc.weightx = 1.0;
        contentPanel.add(usernameField, gbc);

        gbc.gridy = 2; gbc.gridx = 0; gbc.fill = GridBagConstraints.NONE; gbc.weightx = 0;
        contentPanel.add(new JLabel("Password:"), gbc);
        gbc.gridx = 1; gbc.fill = GridBagConstraints.HORIZONTAL; gbc.weightx = 1.0;
        contentPanel.add(passwordField, gbc);

        gbc.gridy = 3; gbc.gridx = 1; gbc.fill = GridBagConstraints.NONE; gbc.weightx = 0;
        contentPanel.add(showPasswordCheckBox, gbc);

        JPanel buttonsPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT, 8, 0));
        buttonsPanel.add(cancelButton);
        buttonsPanel.add(loginButton);
        gbc.gridy = 4; gbc.gridx = 0; gbc.gridwidth = 2; gbc.fill = GridBagConstraints.HORIZONTAL; gbc.weightx = 1.0;
        contentPanel.add(buttonsPanel, gbc);

        setContentPane(contentPanel);
    }

    public static void main(String[] args) {
        setSystemLookAndFeel();
        SwingUtilities.invokeLater(() -> new LoginWindow().setVisible(true));
    }

    private static void setSystemLookAndFeel() {
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (Exception ignored) {
        }
    }
}