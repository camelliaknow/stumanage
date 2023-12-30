package MYFRAM;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.*;

// 登陆界面
public class LoginFrame extends JFrame implements ActionListener {
    private JLabel usernameLabel;
    private JLabel passwordLabel;
    private JTextField usernameField;
    private JPasswordField passwordField;
    private JButton loginButton;

    private Connection connection;

    public LoginFrame() {
        this.setTitle("用户登录");
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setSize(300, 150);
        this.setLayout(new GridLayout(3, 2));

        usernameLabel = new JLabel("用户名:");
        passwordLabel = new JLabel("密码:");

        usernameField = new JTextField();
        passwordField = new JPasswordField();

        loginButton = new JButton("登录");
        loginButton.addActionListener(this);

        this.add(usernameLabel);
        this.add(usernameField);
        this.add(passwordLabel);
        this.add(passwordField);
        this.add(new JLabel()); // 对齐占位符
        this.add(loginButton);

        connection = connect();
    }

    private Connection connect() {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            String url = "jdbc:mysql://localhost:3306/itcast";
            String user = "root";
            String password = "1472580sjK";
            return DriverManager.getConnection(url, user, password);
        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, "数据库连接失败");
            return null;
        }
    }

    private boolean validateLogin(String username, String password) {
        if (username.isEmpty() || password.isEmpty()) {
            JOptionPane.showMessageDialog(this, "请输入用户名和密码");
            return false;
        }

        try {
            String sql = "SELECT * FROM user WHERE name = ? AND pwd = ?";
            try (PreparedStatement statement = connection.prepareStatement(sql)) {
                statement.setString(1, username);
                statement.setString(2, password);
                try (ResultSet resultSet = statement.executeQuery()) {
                    return resultSet.next(); //若用户名和密码符合数据库中元素，返回true
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == loginButton) {
            String username = usernameField.getText();
            String password = new String(passwordField.getPassword());

            if (validateLogin(username, password)) {
                JOptionPane.showMessageDialog(this, "登录成功");
                this.dispose();//关闭窗体
            } else {
                JOptionPane.showMessageDialog(this, "用户名或密码错误");
            }
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            TestDemo.TestDemo.LoginFrame loginFrame = new TestDemo.TestDemo.LoginFrame();
            loginFrame.setVisible(true);
        });
    }
}