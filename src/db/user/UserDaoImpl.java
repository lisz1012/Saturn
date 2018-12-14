package db.user;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import db.MySQLDBUtil;

public class UserDaoImpl implements UserDao {

	static {
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
	}
	
	@Override
	public int getUserCountWithUsernameAndPassword(String username, String password) {
		String sql = "select count(*) from users where user_id = ? and password = ?";
		int count = 0;
		try (Connection connection = DriverManager.getConnection(MySQLDBUtil.URL);
			 PreparedStatement ps = connection.prepareStatement(sql)) {
			ps.setString(1, username);
			ps.setString(2, password);
			ResultSet rs = ps.executeQuery();
			if (rs.next()) {
				count = rs.getInt(1);
			}
			rs.close();
			return count;
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return 0;
	}

}
