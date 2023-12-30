//我们此次课设的的代码主要是一个简单的用户管理系统，使用Java Swing进行界面设计，与MySQL数据库进行交互
//本次项目实践涉及到的内容有：GUI应用程序开发、数据库连接和操作、事件处理等方面

package TestDemo;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.*;
import java.util.Scanner;

public class TestDemo extends JFrame implements ActionListener {
    private JLabel nameLabel;
    private JLabel ageLabel;
    private JLabel addressLabel;
    private JLabel passwordLabel;
    private JTextField nameField;
    private JTextField ageField;
    private JTextField addressField;
    private JTextField passwordField;
    private JButton addButton;
    private JButton deleteButton;
    private JButton updateButton;
    private JButton resetButton;
    private JTable table;
    private DefaultTableModel tableModel;

    private Connection connection;
    private Scanner input;

    public TestDemo() {
        this.setTitle("用户管理");
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setPreferredSize(new Dimension(600, 400));
        this.nameLabel = new JLabel("姓名:");
        this.ageLabel = new JLabel("年龄:");
        this.addressLabel = new JLabel("地址:");
        this.passwordLabel = new JLabel("密码:");

        Font labelFont = nameLabel.getFont().deriveFont(Font.BOLD, 16);
        nameLabel.setFont(labelFont);
        ageLabel.setFont(labelFont);
        addressLabel.setFont(labelFont);
        passwordLabel.setFont(labelFont);

        this.nameField = new JTextField(10);
        this.ageField = new JTextField(10);
        this.addressField = new JTextField(10);
        this.passwordField = new JTextField(10);
        this.addButton = new JButton("添加");
        this.deleteButton = new JButton("删除");
        this.updateButton = new JButton("修改");
        this.resetButton = new JButton("重置");
        this.addButton.addActionListener(this);
        this.deleteButton.addActionListener(this);
        this.updateButton.addActionListener(this);
        this.resetButton.addActionListener(this);

        String[] columnNames = new String[]{"姓名", "年龄", "地址"};
        Object[][] data = new Object[0][];
        this.tableModel = new DefaultTableModel(data, columnNames);
        this.table = new JTable(this.tableModel);
        JScrollPane scrollPane = new JScrollPane(this.table);

        JPanel panel = new JPanel();
        panel.setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);
        gbc.gridx = 0;
        gbc.gridy = 0;
        panel.add(this.nameLabel, gbc);
        gbc.gridx = 1;
        gbc.gridy = 0;
        panel.add(this.nameField, gbc);
        gbc.gridx = 2;
        gbc.gridy = 0;
        panel.add(this.ageLabel, gbc);
        gbc.gridx = 3;
        gbc.gridy = 0;
        panel.add(this.ageField, gbc);
        gbc.gridx = 0;
        gbc.gridy = 1;
        panel.add(this.addressLabel, gbc);
        gbc.gridx = 1;
        gbc.gridy = 1;
        panel.add(this.addressField, gbc);
        gbc.gridx = 2;
        gbc.gridy = 1;
        panel.add(this.passwordLabel, gbc);
        gbc.gridx = 3;
        gbc.gridy = 1;
        panel.add(this.passwordField, gbc);
        gbc.gridx = 0;
        gbc.gridy = 2;
        panel.add(this.addButton, gbc);
        gbc.gridx = 1;
        gbc.gridy = 2;
        panel.add(this.deleteButton, gbc);
        gbc.gridx = 2;
        gbc.gridy = 2;
        panel.add(this.updateButton, gbc);
        gbc.gridx = 3;
        gbc.gridy = 2;
        panel.add(this.resetButton, gbc);

        Container container = this.getContentPane();
        container.setLayout(new BorderLayout());
        container.add(scrollPane, BorderLayout.CENTER);
        container.add(panel, BorderLayout.SOUTH);
        this.pack();
        this.setVisible(true);

        // 初始化数据库连接和输入对象
        connection = connect();
        input = new Scanner(System.in);

        // 在构造函数中调用加载数据的方法
        loadUserDataFromDatabase();
    }

    // 将窗体界面的操作同步到数据库中

    // 向数据库中插入用户数据
    private void insertUserDataIntoDatabase(String name, String age, String address, String pwd) {
        try {
            String sql = "INSERT INTO user (name, age, address, pwd) VALUES (?, ?, ?, ?)";
            try (PreparedStatement statement = connection.prepareStatement(sql)) {
                statement.setString(1, name);
                statement.setString(2, age);
                statement.setString(3, address);
                statement.setString(4, pwd);
                statement.executeUpdate();
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(this, "插入数据失败");
        }
    }


    // 删除用户信息
    public void deleteUserDataFromDatabase(String name) {
        try {
            String sql = "DELETE FROM user WHERE name = ?";
            try (PreparedStatement statement = connection.prepareStatement(sql)) {
                statement.setString(1, name);
                int rowsAffected = statement.executeUpdate();

                if (rowsAffected > 0) {
                    JOptionPane.showMessageDialog(this, "删除数据成功");
                } else {
                    JOptionPane.showMessageDialog(this, "未找到匹配的数据");
                }
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(this, "删除数据失败");
        }
    }


    // 更新数据库中的用户数据
    private void updateUserDataInDatabase(String name, String age, String address, String password) {
        try {
            String sql = "UPDATE user SET age=?, address=?, pwd=? WHERE name=?";//按照数据库中的对应顺序填写
            try (PreparedStatement statement = connection.prepareStatement(sql)) {
                statement.setString(1, age);
                statement.setString(2, address);
                statement.setString(3, password);
                statement.setString(4, name);
                statement.executeUpdate();
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(this, "更新数据失败");
        }
    }



    private Connection connect() {//连接数据库
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


    // 加载数据库中的用户数据到表格中
    private void loadUserDataFromDatabase() {
        try {
            String sql = "SELECT * FROM user";
            try (Statement statement = connection.createStatement();
                 ResultSet resultSet = statement.executeQuery(sql)) {
                while (resultSet.next()) {
                    String name = resultSet.getString("name");
                    String age = resultSet.getString("age");
                    String address = resultSet.getString("address");
                    Object[] row = new Object[]{name, age, address};
                    this.tableModel.addRow(row);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, "加载数据失败");
        }
    }

    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == this.addButton) {
            String name = this.nameField.getText();
            String age = this.ageField.getText();
            String address = this.addressField.getText();
            String pwd=this.passwordField.getText();
            // 向表格中添加新行
            Object[] row = new Object[]{name, age, address,pwd};
            this.tableModel.addRow(row);

            // 向数据库中添加新行
            insertUserDataIntoDatabase(name, age, address,pwd);
        } else if (e.getSource() == this.deleteButton) {
            int selectedRow = this.table.getSelectedRow();
            if (selectedRow != -1) {
                String name = (String) this.tableModel.getValueAt(selectedRow, 0);
                this.tableModel.removeRow(selectedRow);

                // 从数据库中删除相应行
                deleteUserDataFromDatabase(name);
            } else {
                JOptionPane.showMessageDialog(this, "请选择要删除的行");
            }
    } else if (e.getSource() == this.updateButton) {
            int selectedRow = this.table.getSelectedRow();
            if (selectedRow != -1) {
                String name = this.nameField.getText();
                String age = this.ageField.getText();
                String address = this.addressField.getText();
                String passwrd=this.passwordField.getText();
                this.tableModel.setValueAt(name, selectedRow, 0);
                this.tableModel.setValueAt(age, selectedRow, 1);
                this.tableModel.setValueAt(address, selectedRow, 2);

                // 更新数据库中的相应行
                updateUserDataInDatabase(name,age,address,passwrd);
            } else {
                JOptionPane.showMessageDialog(this, "请选择要修改的行");
            }
        } else if (e.getSource() == this.resetButton) {
            this.nameField.setText("");
            this.ageField.setText("");
            this.addressField.setText("");
            this.passwordField.setText("");
        }
    }

    // 登陆界面
    public static class LoginFrame extends JFrame implements ActionListener {
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
                    // 打开数据库操作界面进行对数据库数据的操作
                    SwingUtilities.invokeLater(() -> new TestDemo());
                } else {
                    JOptionPane.showMessageDialog(this, "用户名或密码错误");
                }
            }
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            LoginFrame loginFrame = new LoginFrame();
            loginFrame.setVisible(true);
        });
    }
}
