package webapp;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.sql.DataSource;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

@WebServlet(name = "Login")
public class Login extends HttpServlet {

    private DataSource dataSource;

    @Override
    public void init() throws ServletException {
        super.init();
        dataSource = (DataSource) getServletContext().getAttribute("dbPool");
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String username = request.getParameter("user");
        String password = request.getParameter("pass");
        Connection connection = null;
        PreparedStatement statement = null;

        try {
            connection = dataSource.getConnection();
            statement = connection.prepareStatement("SELECT PASSWORD FROM T_USER WHERE USER_NAME=?");
            statement.setString(1,username);

            ResultSet resultSet = statement.executeQuery();

            if (resultSet.next()){
                String rstPass = resultSet.getString(1);
                if (password.equals(rstPass)){
                    request.getSession().setAttribute("isLogged", true);
                    request.getRequestDispatcher("/app/dashboard.html").forward(request,response);
                }else {
                    request.getRequestDispatcher("/index.html").forward(request,response);
                }
            }else {
                request.getRequestDispatcher("/index.html").forward(request,response);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }finally {
            if (statement!=null){
                try {
                    statement.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
            if (connection!=null){
                try {
                    connection.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }

//        if(username.equals("admin") && password.equals("root")){
//            HttpSession session = request.getSession();
//            session.setAttribute("isActive",true);
//
//            request.getRequestDispatcher("/app/home").forward(request,response);
//
//        }else {
//            PrintWriter writer = response.getWriter();
//            writer.write("<h1>Invalid Login</h1>");
//
//        }

    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
//        PrintWriter writer = response.getWriter();
//        writer.write("<form action=\"/login\" method=\"post\">\n" +
//                "          <label for=\"inputUserName\">User Name</label>\n" +
//                "          <input type=\"text\" name=\"user\" id=\"inputUserName\">\n" +
//                "          <label for=\"inputPassword\">Password</label>\n" +
//                "          <input type=\"password\" name=\"pass\" id=\"inputPassword\">\n" +
//                "          <button type=\"submit\">Sign in</button>\n" +
//                " </form>");

    }


}
