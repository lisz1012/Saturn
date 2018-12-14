package db.favorite;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import db.MySQLDBUtil;
import entity.Item;
import external.TicketDaoFactory;
import external.TicketMasterDao;

public class FavoriteDaoImpl implements FavoriteDao {
	
	static {
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
	}
	
	@Override
	public Set<String> getFavoriteItemIds(String user_id) {
		Set<String> set = new HashSet<>();
		try (Connection connection = DriverManager.getConnection(MySQLDBUtil.URL);
			 PreparedStatement ps = connection.prepareStatement("select item_id from history where user_id = ?")) {
			ps.setString(1, user_id);
			ResultSet rs = ps.executeQuery();
			
			while (rs.next()) {
				set.add(rs.getString(1));
			}
			
			rs.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		
		return set;
	}

	@Override
	public List<Item> getFavoriteItems(String user_id) {
		TicketMasterDao ticketMasterDao = TicketDaoFactory.get();
		Set<String> favoriteItemIds = getFavoriteItemIds(user_id);
		for (String itemId : favoriteItemIds) {
			System.out.print(itemId + " - ");
		}
		System.out.println();
		List<Item> favoriteItems = ticketMasterDao.search(favoriteItemIds);
		
		return favoriteItems;
	}
	
	@Override
	public void addFavorite(String userId, String itemId) {
		try (Connection connection = DriverManager.getConnection(MySQLDBUtil.URL);
			 PreparedStatement ps = connection.prepareStatement("insert into history (user_id, item_id) values (?, ?)");) {
			ps.setString(1, userId);
			ps.setString(2, itemId);
			ps.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	@Override
	public void deleteFavorite(String userId, String itemId) {
		try (Connection connection = DriverManager.getConnection(MySQLDBUtil.URL);
				 PreparedStatement ps = connection.prepareStatement("delete from history where user_id = ? and item_id = ?");) {
				ps.setString(1, userId);
				ps.setString(2, itemId);
				ps.executeUpdate();
			} catch (SQLException e) {
				e.printStackTrace();
			}
	}

	public static void main(String[] args) {
		FavoriteDaoImpl favoriteDaoImpl = new FavoriteDaoImpl();
		Set<String> set = favoriteDaoImpl.getFavoriteItemIds("1111");
		for (String s : set) {
			System.out.println(s);
		}
		
	}
}
