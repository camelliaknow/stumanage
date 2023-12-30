package MYFRAM;

import java.awt.BorderLayout;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;
import javax.swing.table.DefaultTableModel;

public class UserManagementGUI extends JFrame implements ActionListener {
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

    public UserManagementGUI() {

        this.setTitle("用户管理");
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setPreferredSize(new Dimension(600, 400));
        this.nameLabel = new JLabel("姓名:");
        this.ageLabel = new JLabel("年龄:");
        this.addressLabel = new JLabel("地址:");
        this.passwordLabel = new JLabel("密码:");

        // 修改字体大小
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
        String[] columnNames = new String[]{"姓名", "年龄", "地址", "密码"};
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
    }

    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == this.addButton) {
            String name = this.nameField.getText();
            String age = this.ageField.getText();
            String address = this.addressField.getText();
            String password = this.passwordField.getText();
            Object[] row = new Object[]{name, age, address, password};
            this.tableModel.addRow(row);
        } else if (e.getSource() == this.deleteButton) {
            int selectedRow = this.table.getSelectedRow();
            if (selectedRow != -1) {
                this.tableModel.removeRow(selectedRow);
            } else {
                JOptionPane.showMessageDialog(this, "请选择要删除的行");
            }
        } else if (e.getSource() == this.updateButton) {
            int selectedRow = this.table.getSelectedRow();
            if (selectedRow != -1) {
                String name = this.nameField.getText();
                String age = this.ageField.getText();
                String address = this.addressField.getText();
                String password = this.passwordField.getText();
                this.tableModel.setValueAt(name, selectedRow, 0);
                this.tableModel.setValueAt(age, selectedRow, 1);
                this.tableModel.setValueAt(address, selectedRow, 2);
                this.tableModel.setValueAt(password, selectedRow, 3);
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

}
