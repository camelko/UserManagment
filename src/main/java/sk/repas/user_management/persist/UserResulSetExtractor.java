package sk.repas.user_management.persist;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.ResultSetExtractor;

public class UserResulSetExtractor implements ResultSetExtractor<List<User>> {

	@Override
	public List<User> extractData(ResultSet rs) throws SQLException, DataAccessException {
		List<User> users = new ArrayList<>();
		
		GroupDao groupDao = DaoFactory.INSTANCE.getGroupDao();
		List<Group> allGroups = groupDao.getAll();
		Map<Long, Group> groupsMap = new HashMap<>();
		for (Group group: allGroups) {
			groupsMap.put(group. getId(), group);
		}
		
		User novy = null;
		while (rs.next()) {
			long id = rs.getLong("id");
			if (novy == null || novy.getId() != id) {
				novy = new User();
				novy.setId(id);
				novy.setName(rs.getString("name"));
				novy.setEmail(rs.getString("email"));
				novy.setPassword(rs.getString("password"));
				Timestamp timestamp = rs.getTimestamp("last_login");
				if (timestamp != null) {
					novy.setLastLogin(timestamp.toLocalDateTime());
				}
				users.add(novy);
			}
			long groupId = rs.getLong("group_id");
			if (! rs.wasNull()) {
				novy.getGroups().add(groupsMap.get(groupId));
			}
		}
		return users;
	}
}