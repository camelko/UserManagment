package sk.repas.user_management;

import java.util.List;

import sk.repas.user_management.persist.MemoryUserDao;
import sk.repas.user_management.persist.User;
import sk.repas.user_management.persist.UserDao;

public class App {
    public static void main( String[] args ) {
    	UserDao userDao = new MemoryUserDao();
    	User jano = new User();
    	jano.setName("Jano");
    	jano.setEmail("jano@jano.sk");
    	jano.setPassword("sompekny");
    	userDao.add(jano);
    	List<User> ludia = userDao.getAll();
    	System.out.println(ludia);
    	System.out.println("prvy user: " + userDao.getById(1));
    	System.out.println("druhy user: " + userDao.getById(2));

    	userDao.delete(1);
    	ludia = userDao.getAll();
    	System.out.println(ludia);

    	userDao.add(jano);
    	ludia = userDao.getAll();
    	System.out.println(ludia);

    	User jano2 = new User();
    	jano2.setId(2L);
    	jano2.setName("Janko");
    	jano2.setEmail("jano@jozo.sk");
    	userDao.save(jano2);
    	ludia = userDao.getAll();
    	System.out.println(ludia);
    }
}