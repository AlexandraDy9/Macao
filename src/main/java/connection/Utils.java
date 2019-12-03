package connection;

import model.Room;
import model.User;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;


public class Utils {
    private static List<User> users = new ArrayList<>();
    private static List<Room> rooms = new ArrayList<>();

    public Utils() {
        getUsers();
    }

    private void getUsers() {
        try {
            Statement myStatement = Objects.requireNonNull(ConnectionDB.getInstance().createConnection()).createStatement();
            String sqlInsert = "SELECT * from user";

            ResultSet rs = myStatement.executeQuery(sqlInsert);
            while (rs.next()) {
                int id = rs.getInt("id");
                String name = rs.getString("username");
                String password = rs.getString("password");
                User user = new User(id, name, password);
                users.add(user);
            }
            rs.close();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void getRooms() {
        try {
            Statement myStatement = Objects.requireNonNull(ConnectionDB.getInstance().createConnection()).createStatement();
            String sqlInsert = "SELECT * from room";

            ResultSet rs = myStatement.executeQuery(sqlInsert);
            while (rs.next()) {
                int id = rs.getInt("id");
                int joinedUsers = rs.getInt("joinedUsers");
                int hostedBy = rs.getInt("hostedBy");
                Room room = new Room(id, joinedUsers, hostedBy);
                rooms.add(room);
            }
            rs.close();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }


    /**
     * validation for login
     *
     * @param username
     * @param password
     * @return if exists
     */
    public boolean checkLogin(String username, String password) {
        for (User user : users) {
            if (user.getUsername().equals(username) && user.getPassword().equals(password)) {
                return true;
            }
        }
        return false;
    }
}
