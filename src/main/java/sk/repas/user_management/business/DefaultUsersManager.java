package sk.repas.user_management.business;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import sk.repas.user_management.persist.User;
import sk.repas.user_management.persist.UserDao;
import sk.repas.user_management.persist.DaoFactory;

class DefaultUsersManager implements UsersManager {

	private UserDao userDao = DaoFactory.INSTANCE.getUserDao(); 
	
	@Override
	public List<User> getTodayUsers() {
		List<User> users = userDao.getAll();
		List<User> todayUsers = new ArrayList<>();
		for (User user: users) {
			if (user.getLastLogin() != null) {
				LocalDate lastLoginDate = user.getLastLogin().toLocalDate();
				LocalDate today = LocalDate.now();
				if (today.equals(lastLoginDate)) {
					todayUsers.add(user);
				}
			}
		}
		return todayUsers;
	}

}
