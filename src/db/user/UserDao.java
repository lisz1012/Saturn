package db.user;

public interface UserDao {
	/**
	 * Use this method to verify if a username password pair is valid
	 * @param password 
	 * @param username 
	 * @return
	 */
	int getUserCountWithUsernameAndPassword(String username, String password);
}
