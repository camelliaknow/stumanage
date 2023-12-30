package JDBCUtil;

import com.sun.security.jgss.GSSUtil;

import javax.swing.*;
import java.sql.*;
import java.util.Scanner;

public class JDBCUtil {
    private Connection connection;
    Scanner input = new Scanner(System.in);

    public void connect() throws ClassNotFoundException, SQLException {
        Class.forName("com.mysql.cj.jdbc.Driver");

        String url = "jdbc:mysql://localhost:3306/itcast";
        String user = "root";
        String password = "1472580sjK";
        connection = DriverManager.getConnection(url, user, password);
    }

    public void disconnect() throws SQLException {
        if (connection != null) {
            connection.close();
        }
    }

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
        }
    }


    // 删除用户信息
    public void deleteUserDataFromDatabase(String name) {
        try {
            String sql = "DELETE FROM user WHERE name = ?";
            try (PreparedStatement statement = connection.prepareStatement(sql)) {
                statement.setString(1, name);
                int rowsAffected = statement.executeUpdate();
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }


    //查找用户信息
    public void select() throws SQLException {
        String sql = "SELECT * FROM user WHERE name= ?";
        System.out.println("请输入要查询人的姓名：");
        String name1=input.next();
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
           // statement.setInt(1, inputId);
            try (ResultSet resultSet = statement.executeQuery()) {
                while (resultSet.next()) {
                    String name = resultSet.getString("name");
                    if(name==name1){
                        int id=resultSet.getInt("id");
                        String name2 = resultSet.getString("name");
                        int age = resultSet.getInt("age");
                        String address = resultSet.getString("address");

                        // 处理查询结果
                        System.out.println("ID: " + id + ", Name: " + name + ", Age: " + age + ", Address: " + address);
                        break;
                    }

                }
            }
        }
    }


//    public void change() throws SQLException {
//        String sql="SELECT * FROM user WHRER id=?";
//        while (true) {
//        System.out.println("请输入你要修改信息人的的id值：");
//        int num= input.nextInt();
//        System.out.println("请输入要修改的信息(退出修改请按1)：");
//        String change = input.next();
//        if(num==1){
//            break;
//        }
//            switch (change) {
//                case "id":
//                    System.out.println("请输入修改后的id值：");
//                    int newId = input.nextInt();
//                    updateId(num, newId);
//                    System.out.println("修改后的信息为：");
//                    break;
//                case "name":
//                    System.out.println("请输入新的姓名：");
//                    String newName = input.next();
//                    updateName(num, newName);
//                    System.out.println("修改后的信息为：");
//                    break;
//                case "age":
//                    System.out.println("请输入新的年龄：");
//                    int newAge = input.nextInt();
//                    updateAge(num, newAge);
//                    System.out.println("修改后的信息为：");
//                    break;
//                case "address":
//                    System.out.println("请输入新的地址：");
//                    String newAddress = input.next();
//                    updateAddress(num, newAddress);
//                    System.out.println("修改后的信息为：");
//                    break;
//                default:
//                    System.out.println("无效的选项");
//                    break;
//            }
//        }
//    }
//
//    // 更新 ID
//    public void updateId(int inputId, int newId) throws SQLException {
//        String sql = "UPDATE user SET id = ? WHERE id = ?";
//
//        try (PreparedStatement statement = connection.prepareStatement(sql)) {
//            statement.setInt(1, newId);
//            statement.setInt(2, inputId);
//            statement.executeUpdate();//执行修改操作
//        }
//    }
//
//    // 更新姓名
//    public void updateName(int inputId, String newName) throws SQLException {
//        String sql = "UPDATE user SET name = ? WHERE id = ?";
//
//        try (PreparedStatement statement=connection.prepareStatement(sql)) {
//            statement.setString(1, newName);
//            statement.setInt(2, inputId);
//            statement.executeUpdate();
//        }
//    }
//
//    // 更新年龄
//    public void updateAge(int inputId, int newAge) throws SQLException {
//        String sql = "UPDATE user SET age = ? WHERE id = ?";
//
//        try (PreparedStatement statement=connection.prepareStatement(sql)) {
//            statement.setInt(1, newAge);
//            statement.setInt(2, inputId);
//            statement.executeUpdate();
//        }
//    }
//
//    // 更新地址
//    public void updateAddress(int inputId, String newAddress) throws SQLException {
//        String sql = "UPDATE user SET address = ? WHERE id = ?";
//
//        try (PreparedStatement statement=connection.prepareStatement(sql)) {
//            statement.setString(1, newAddress);
//            statement.setInt(2, inputId);
//            statement.executeUpdate();
//        }
//    }

    // 更新数据库中的用户数据
    private void updateUserDataInDatabase(String name, String age, String address, String password) {
        try {
            String sql = "UPDATE user SET age=?, address=?, pwd=? WHERE name=?";
            try (PreparedStatement statement = connection.prepareStatement(sql)) {
                statement.setString(1, age);
                statement.setString(2, address);
                statement.setString(3, password);
                statement.setString(4, name);
                statement.executeUpdate();
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
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
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    //输出所有用户信息
    public void ALLOUTinfo() throws SQLException {
        String sql = "SELECT * FROM user";

        try (PreparedStatement statement = connection.prepareStatement(sql);
             ResultSet resultSet = statement.executeQuery()) {

            while (resultSet.next()) {
                int id = resultSet.getInt("id");
                String name = resultSet.getString("name");
                int age = resultSet.getInt("age");
                String address = resultSet.getString("address");

                // 处理查询结果
                System.out.println("ID: " + id + ", Name: " + name + ", Age: " + age + ", Address: " + address);
            }
        }
    }


}
