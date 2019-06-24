package webapp;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

@WebServlet(name = "Login")
public class Login extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        PrintWriter writer = response.getWriter();
        writer.write("<form action=\"/login\" method=\"post\">\n" +
                "          <label for=\"inputUserName\">User Name</label>\n" +
                "          <input type=\"text\" name=\"user\" id=\"inputUserName\">\n" +
                "          <label for=\"inputPassword\">Password</label>\n" +
                "          <input type=\"password\" name=\"pass\" id=\"inputPassword\">\n" +
                "          <button type=\"submit\">Sign in</button>\n" +
                " </form>");

    }
}
