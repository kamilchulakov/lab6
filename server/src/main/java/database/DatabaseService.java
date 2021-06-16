package database;

public class DatabaseService {
    private final String INSERT_LABWORK_SQL = "INSERT INTO labworks" +
            "  (id, labname, coordinate_x, coordinate_y, minimal_point, difficulty, discipline, self_study_hours, creation_date, labwork_id) VALUES " +
            " (?, ?, ?, ?, ?, ?, ?, ?, ?, ?);";
    private final String CLEAR_SQL = "TRUNCATE TABLE labworks";
    private final String REMOVE_BY_ID_SQL = "DELETE FROM labworks WHERE (id = ?) AND (author = ?)";
    private final String ADD_USER_SQL = "INSERT INTO users (user, password) VALUES (?, ?);";
    private final String UPDATE_SQL = "UPDATE labworks set WHERE id = ? and author = ? " +
            "labname = ? , coordinate_x = ? , coordinate_y = ? , minimal_point = ? , difficulty = ? , discipline = ? " +
            ", self_study_hours = ? , creation_date = ?, labwork_id = ? ;";
    private final String AVG_MINIMAL_POINT_SQL = "SELECT AVG(minimal_point) FROM labworks WHERE author = ?";
    private final String REMOVE_LOWER_SQL = "DELETE FROM labworks WHERE labwork_id < ? and author = ?";
    private final String REMOVE_BY_DISCIPLINE_SQL = "DELETE FROM labworks WHERE discipline = ? and self_study_hours = ? and author = ?";
    private final String DESCENDING_DIFFICULTY_SQL = "SELECT difficulty FROM labworks WHERE author = ? ORDER BY difficulty";
    
}
