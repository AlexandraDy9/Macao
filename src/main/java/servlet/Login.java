package servlet;

import connection.Utils;
import logger.Logging;
import model.*;
import repository.GameCardsRepository;
import repository.UserCardsRepository;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.logging.Level;


@WebServlet(name = "login",
        urlPatterns = {"/login"},
        loadOnStartup = 1)
public class Login extends HttpServlet {

    private Utils utils = new Utils();

    /***
     * A POST request results from an HTML form that specifically lists POST as the METHOD and it should be handled by doPost() method.
     * @param request
     * @param response
     */
    public void doPost(HttpServletRequest request, HttpServletResponse response) {

        try {
            User currentUser = new User();

            currentUser.setUsername(request.getParameter("username"));
            currentUser.setPassword(request.getParameter("password"));

            List<User> users = utils.getUsers();
            if(!UserCardsRepository.getInstance().getAll().isEmpty())
                UserCardsRepository.getInstance().removeAll();
            users.forEach(user1 -> UserCardsRepository
                    .getInstance()
                    .add(new UserCards(user1.getIdRoom(), user1, new ArrayList<>())));

            List<Room> rooms = utils.getRooms();

            List<Card> cards = GenerateDeck.generate();
            Random rand = new Random();
            Card currentCard = cards.get(rand.nextInt(cards.size()));
            cards.remove(currentCard);

            rooms.forEach(room -> GameCardsRepository.getInstance().add(new GameCards(room.getId(), currentCard, cards, 0, 0, 0)));

            currentUser = utils.checkLogin(currentUser.getUsername(), currentUser.getPassword());

            if (currentUser != null) {
                Logging.log(Level.INFO, "User " + currentUser.getUsername() + " has been successfully logged in.");
                setAttributeForWindow(request, currentUser, rooms);
                response.sendRedirect("rooms_view.jsp");
            }

            else {
                //ConfigureLog.configureLogFile().info("User " + currentUser.getUsername() + " invalid.");
                response.sendRedirect("invalidLogin.jsp");
            }
        }

        catch (Throwable theException) {
            System.out.println(theException.getMessage());
        }
    }

    /**
     * sets the attributes for a session
     * @param request
     * @param currentUser
     * @param rooms
     */
    private void setAttributeForWindow(HttpServletRequest request, User currentUser, List<Room> rooms) {
        HttpSession session = request.getSession(true);
        session.setAttribute("currentSessionUser", currentUser);
        session.setAttribute("rooms", rooms);
    }
}