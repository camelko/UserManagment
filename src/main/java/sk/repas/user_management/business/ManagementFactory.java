package sk.repas.user_management.business;

public enum ManagementFactory {
	INSTANCE;
	
	public UsersManager getUsersManager() {
		return new DefaultUsersManager();
	}	
}
