package servlet;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class ArtistServlet extends HttpServlet {
    protected void doGet(HttpServletRequest request, HttpServletResponse response) {
        System.out.println("doGet");
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) {
        System.out.println("doPost");
    }
}
