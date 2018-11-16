package sk.repas.user_management;

import java.util.List;

import sk.repas.user_management.persist.User;
import sk.repas.user_management.persist.UserDao;
import sk.repas.user_management.persist.DaoFactory;

public class App2 {
	public static void main(String[] args) {
		UserDao userDao = DaoFactory.INSTANCE.getUserDao();
		List<User> users = userDao.getAll();
		System.out.println(users);
		
		//menim Anku
		User firstUser = userDao.getById(1);
		if (firstUser != null) {
			firstUser.setEmail("anka@estenovsieelct.sk");
			userDao.save(firstUser);
		}
		
		List<User> usersPoZmene = userDao.getAll();
		System.out.println(usersPoZmene);
		
		User jano = new User();
    	jano.setName("Jano");
    	jano.setEmail("jano@jano.sk");
    	jano.setPassword("sompekny");
    	userDao.add(jano);

		usersPoZmene = userDao.getAll();
		System.out.println(usersPoZmene);
		
		userDao.delete(jano.getId());

		usersPoZmene = userDao.getAll();
		System.out.println(usersPoZmene);
		
		List<User> lenky = userDao.getByName("Lenka");
		System.out.println("Lenky : " + lenky);
	}
}
