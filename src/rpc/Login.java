package rpc;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.json.JSONException;
import org.json.JSONObject;

import db.user.UserDao;
import db.user.UserDaoFactory;
import entity.User;
import utils.WebPrinter;

/**
 * Servlet implementation class Login
 */
@WebServlet("/login")
public class Login extends HttpServlet {
	private static final long serialVersionUID = 1L;
	
	
	private static final String USER_ID_KEY = "user_id";
	private static final String PASSWORD_KEY = "password";
	private static final String FULL_NAME_KEY = "name";
	private static final String STATUS_CODE_OK = "OK";
	private static final String STATUS_CODE_FAIL = "FAIL";
	private static final String STATUS_NAME = "status";
	
	private UserDao userDao = UserDaoFactory.get();
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public Login() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		response.getWriter().append("Served at: ").append(request.getContextPath());
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		JSONObject input = WebPrinter.readJSONObject(request);
		try {
			String userId = input.getString(USER_ID_KEY);
			String password = input.getString(PASSWORD_KEY);
			JSONObject result = new JSONObject();
			User user = null;
			if ((user = userDao.getUser(userId, password)) != null) {
				result.put(STATUS_NAME, STATUS_CODE_OK);
				result.put(USER_ID_KEY, user.getId());
				result.put(FULL_NAME_KEY, user.getFirstName() + " " + user.getLastName());
				HttpSession session = request.getSession();
				session.setMaxInactiveInterval(3600);
				session.setAttribute(USER_ID_KEY, userId);
			} else {
				result.put(STATUS_NAME, STATUS_CODE_FAIL);
			}

			WebPrinter.printJSONObject(response, result);
		} catch (JSONException e) {
			e.printStackTrace();
		}
	}

}
