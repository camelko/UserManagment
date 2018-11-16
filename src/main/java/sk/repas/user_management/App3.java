package sk.repas.user_management;

import java.util.List;

import sk.repas.user_management.persist.DaoFactory;
import sk.repas.user_management.persist.Group;
import sk.repas.user_management.persist.GroupDao;

public class App3 {

	public static void main(String[] args) {
		GroupDao groupDao = DaoFactory.INSTANCE.getGroupDao();
		List<Group> groups = groupDao.getAll();
		System.out.println(groups);
	}

}
