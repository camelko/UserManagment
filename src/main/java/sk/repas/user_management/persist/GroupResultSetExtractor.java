package sk.repas.user_management.persist;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.ResultSetExtractor;

public class GroupResultSetExtractor implements ResultSetExtractor<List<Group>> {

	@Override
	public List<Group> extractData(ResultSet rs) throws SQLException, DataAccessException {
		List<Group> groups = new ArrayList<>();
		Group lastGroup = null;
		while (rs.next()) {
			long id = rs.getLong("id");
			if (lastGroup == null || lastGroup.getId() != id) {
				lastGroup = new Group();
				lastGroup.setId(id);
				lastGroup.setName(rs.getString("name"));
				groups.add(lastGroup);
			}
			String permission = rs.getString("value");
			if (permission != null)
			lastGroup.getPermissions().add(permission);
		}
		return groups;
	}

}
