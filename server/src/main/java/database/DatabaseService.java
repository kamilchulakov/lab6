package database;

import objects.Coordinates;
import objects.Difficulty;
import objects.Discipline;
import objects.LabWork;

import java.sql.*;
import java.util.HashMap;

public class DatabaseService {
    private static DatabaseService ds;
    private final static String INSERT_LABWORK_SQL = "INSERT INTO labworks" +
            "  (id, labname, coordinate_x, coordinate_y, minimal_point, difficulty, discipline, self_study_hours, creation_date, labwork_id) VALUES " +
            " (?, ?, ?, ?, ?, ?, ?, ?, ?, ?);";
    private final static String CLEAR_SQL = "TRUNCATE TABLE labworks";
    private final static String REMOVE_BY_ID_SQL = "DELETE FROM labworks WHERE (id = ?))";
    private final static String ADD_USER_SQL = "INSERT INTO users (user, password) VALUES (?, ?);";
    private final static String UPDATE_SQL = "UPDATE labworks set WHERE labwork_id = ? " +
            "labname = ? , coordinate_x = ? , coordinate_y = ? , minimal_point = ? , difficulty = ? , discipline = ? " +
            ", self_study_hours = ? , creation_date = ?, labwork_id = ? ;";
    private final static String REMOVE_LOWER_SQL = "DELETE FROM labworks WHERE labwork_id < ?";
    private final static String REMOVE_BY_DISCIPLINE_SQL = "DELETE FROM labworks WHERE discipline = ? and self_study_hours = ?";
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

    public void update(int id, LabWork labWork) throws SQLException {
        Connection connection = DatabaseConnection.getConnection();
        PreparedStatement preparedStatement = connection.prepareStatement(UPDATE_SQL);
        preparedStatement.setInt(1, id);
        preparedStatement.setString(2, labWork.getName());
        preparedStatement.setDouble(3, labWork.getCoordinates().getX());
        preparedStatement.setDouble(4, labWork.getCoordinates().getY());
        preparedStatement.setLong(5, labWork.getMinimalPoint());
        preparedStatement.setString(6, labWork.getDifficulty().toString());
        preparedStatement.setString(7, labWork.getDiscipline().getName());
        preparedStatement.setLong(8, labWork.getDiscipline().getSelfStudyHours());
        preparedStatement.setString(9, labWork.getCreationDate().toString());
        preparedStatement.executeUpdate();
    }
    public void remove(String key) throws SQLException {
        Connection connection = DatabaseConnection.getConnection();
        PreparedStatement preparedStatement = connection.prepareStatement(REMOVE_BY_ID_SQL);
        preparedStatement.setInt(1, Integer.parseInt(key));
        preparedStatement.execute();
    }
    public void clear() throws SQLException {
        Connection connection = DatabaseConnection.getConnection();
        PreparedStatement preparedStatement = connection.prepareStatement(CLEAR_SQL);
        preparedStatement.execute();
    }
    public void removeAllLower(LabWork labWork) throws SQLException {
        Connection connection = DatabaseConnection.getConnection();
        PreparedStatement preparedStatement = connection.prepareStatement(REMOVE_LOWER_SQL);
        preparedStatement.setLong(1, labWork.getId());
        preparedStatement.execute();
    }
    public void removeByDiscipline(Discipline discipline) throws SQLException {
        Connection connection = DatabaseConnection.getConnection();
        PreparedStatement preparedStatement = connection.prepareStatement(REMOVE_BY_DISCIPLINE_SQL);
        preparedStatement.setString(1, discipline.getName());
        preparedStatement.setLong(2, discipline.getSelfStudyHours());
        preparedStatement.execute();

    }
    public void insert(String key, LabWork labWork) throws SQLException {
        Connection connection = DatabaseConnection.getConnection();
        PreparedStatement preparedStatement = connection.prepareStatement(INSERT_LABWORK_SQL);
        preparedStatement.setInt(1, Integer.parseInt(key));
        preparedStatement.setString(2, labWork.getName());
        preparedStatement.setDouble(3, labWork.getCoordinates().getX());
        preparedStatement.setDouble(4, labWork.getCoordinates().getY());
        preparedStatement.setLong(5, labWork.getMinimalPoint());
        preparedStatement.setString(6, labWork.getDifficulty().toString());
        preparedStatement.setString(7, labWork.getDiscipline().getName());
        preparedStatement.setLong(8, labWork.getDiscipline().getSelfStudyHours());
        preparedStatement.setString(9, labWork.getCreationDate().toString());
        preparedStatement.execute();
    }


    public static DatabaseService getInstance() {
        return ds;
    }
}
