import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Main {
    public static void main(String[] args) throws ClassNotFoundException, SQLException {
        //加载驱动和
        Class.forName("com.mysql.cj.jdbc.Driver");

        String url="jdbc:mysql://localhost:3306/itcast";
        String user="root";
        String pwd="1472580sjK";
        Connection connection=DriverManager.getConnection(url,user,pwd);

        if(connection==null){
            System.out.println("连接失败");
        }else {
            System.out.println("连接成功");

            connection.close();
        }
    }
    
}
