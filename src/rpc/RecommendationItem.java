package rpc;

import java.io.IOException;
import java.util.HashSet;
import java.util.Set;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.JSONArray;

import db.recommend.RecommendationDao;
import db.recommend.RecommendationDaoFactory;
import entity.Item;
import external.TicketDaoFactory;
import external.TicketMasterDao;
import utils.WebPrinter;

/**
 * Servlet implementation class RecommendItem
 */
@WebServlet("/recommendation")
public class RecommendationItem extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private static final TicketMasterDao TICKET_MASTER_DAO_DAO = TicketDaoFactory.get();
	private static final RecommendationDao RECOMMENDATION_DAO = RecommendationDaoFactory.get();
	
    /**
     * @see HttpServlet#HttpServlet()
     */
    public RecommendationItem() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String userId = request.getParameter("user_id");
		double lat = Double.parseDouble(request.getParameter("lat"));
		double lon = Double.parseDouble(request .getParameter("lon"));
		Set<Item> recommendedItems = new HashSet<>();
		Set<String> categories = RECOMMENDATION_DAO.getRecommendedCategories(userId);
		for (String category : categories) {
			recommendedItems.addAll(TICKET_MASTER_DAO_DAO.search(lat, lon, category));
		}
		JSONArray array = new JSONArray();
		for (Item item : recommendedItems) {
			array.put(item.toJSONObject());
		}
		
		WebPrinter.printJSONArray(response, array);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

	/**
	 * @see HttpServlet#doDelete(HttpServletRequest, HttpServletResponse)
	 */
	protected void doDelete(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
	}

}
