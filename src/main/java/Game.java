import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


@WebServlet("/game")
public class Game extends HttpServlet {

    public void doPost(HttpServletRequest request, HttpServletResponse response) {

        try {
            /// TODO: 12/3/2019 Generate carti, afisare cartile pe care le are
        }


        catch (Throwable theException) {
            System.out.println(theException.getMessage());
        }
    }
}