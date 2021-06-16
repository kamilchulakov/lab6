package database;

import objects.Coordinates;
import objects.Difficulty;
import objects.Discipline;
import objects.LabWork;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.HashMap;

public class DatabaseService {
    private static DatabaseService ds;
    private final static String INSERT_LABWORK_SQL = "INSERT INTO labworks" +
            "  (id, labname, coordinate_x, coordinate_y, minimal_point, difficulty, discipline, self_study_hours, creation_date, labwork_id) VALUES " +
            " (?, ?, ?, ?, ?, ?, ?, ?, ?, ?);";
    private final static String CLEAR_SQL = "TRUNCATE TABLE labworks";
    private final static String REMOVE_BY_ID_SQL = "DELETE FROM labworks WHERE (id = ?) AND (author = ?)";
    private final static String ADD_USER_SQL = "INSERT INTO users (user, password) VALUES (?, ?);";
    private final static String UPDATE_SQL = "UPDATE labworks set WHERE id = ? and author = ? " +
            "labname = ? , coordinate_x = ? , coordinate_y = ? , minimal_point = ? , difficulty = ? , discipline = ? " +
            ", self_study_hours = ? , creation_date = ?, labwork_id = ? ;";
    private final static String AVG_MINIMAL_POINT_SQL = "SELECT AVG(minimal_point) FROM labworks WHERE author = ?";
    private final static String REMOVE_LOWER_SQL = "DELETE FROM labworks WHERE labwork_id < ? and author = ?";
    private final static String REMOVE_BY_DISCIPLINE_SQL = "DELETE FROM labworks WHERE discipline = ? and self_study_hours = ? and author = ?";
    private final static String DESCENDING_DIFFICULTY_SQL = "SELECT difficulty FROM labworks WHERE author = ? ORDER BY difficulty";
    private final static String GET_SQL = "SELECT * FROM labworks";

    public DatabaseService() {
        ds = this;
    }

    public HashMap<String, LabWork> getCollection() throws SQLException {
        HashMap<String, LabWork> collection = new HashMap<>();
        Connection connection = DatabaseConnection.getConnection();
        Statement statement = connection.createStatement();
        ResultSet resultSet = statement.executeQuery(GET_SQL);
        while (resultSet.next()) {
            LabWork labWork = new LabWork(resultSet.getString(2),
                    new Coordinates(resultSet.getFloat(3), resultSet.getFloat(4)),
                    resultSet.getLong(5),
                    Difficulty.valueOf(resultSet.getString(6)),
                    new Discipline(resultSet.getString(7), resultSet.getLong(8)),
                    resultSet.getString(9),
                    resultSet.getString(10));
            collection.put(resultSet.getString(1), labWork);
        }
        return collection;
    }

    public static DatabaseService getInstance() {
        return ds;
    }
}
