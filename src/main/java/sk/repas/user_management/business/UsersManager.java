package sk.repas.user_management.business;

import java.util.List;

import sk.repas.user_management.persist.User;

public interface UsersManager {
	public List<User> getTodayUsers();
}
