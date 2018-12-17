package service.recommend;

import java.util.HashSet;
import java.util.Set;

import entity.Item;
import external.TicketDaoFactory;
import external.TicketMasterDao;

public class TicketMasterServiceImpl implements ItemService {
	private static final TicketMasterDao TICKET_MASTER_DAO = TicketDaoFactory.get();

	@Override
	public Set<Item> getItemsFromTicketMaster(double lat, double lon, Set<String> categories) {
		Set<Item> items = new HashSet<>();
		for (String category : categories) {
			items.addAll(TICKET_MASTER_DAO.search(lat, lon, category));
		}
		return items;
	}
}
