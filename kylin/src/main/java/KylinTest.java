import java.sql.*;

/**
 * @author Shelly An
 * @create 2020/8/25 15:31
 */
public class KylinTest {
    public static void main(String[] args) throws ClassNotFoundException, SQLException {
        String sql = "";
        //注册驱动
        Class.forName("org.apache.kylin.jdbc.Driver");

        //获取连接
        Connection connection = DriverManager.getConnection("jdbc:kylin://hadoop102:7070/gmall","ADMIN","KYLIN");

        //预编译sql
        PreparedStatement ps = connection.prepareStatement(sql);

        //执行查询
        ResultSet rs = ps.executeQuery();

        while (rs.next()) {
            System.out.println(rs.getString("province_name") +
                    rs.getString("sumAmount"));
        }

    }
}
