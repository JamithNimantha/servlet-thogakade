package webapp;

import com.google.gson.Gson;
import webapp.dto.CustomerDTO;

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
import java.util.ArrayList;

@WebServlet(name = "Dashboard")
public class Dashboard extends HttpServlet {
    private DataSource dataSource;

    @Override
    public void init() throws ServletException {
        super.init();
        if (dataSource==null) {
            dataSource = (DataSource) getServletContext().getAttribute("dbPool");
        }
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
//        request.getRequestDispatcher("/app/dashboard.html").forward(request,response);

    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
//        request.getRequestDispatcher("/app/dashboard.html").forward(request,response);
        Connection connection = null;
        PreparedStatement statement = null;

        try {
            ArrayList<Integer> arrayList = new ArrayList<>();
            connection = dataSource.getConnection();

            statement = connection.prepareStatement("SELECT COUNT(*) FROM T_CUSTOMER");
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()){
                arrayList.add(resultSet.getInt(1));
            }

            statement = connection.prepareStatement("SELECT COUNT(*) FROM T_ITEM");
            resultSet = statement.executeQuery();
            if (resultSet.next()){
                arrayList.add(resultSet.getInt(1));
            }

            statement = connection.prepareStatement("SELECT COUNT(*) FROM T_ORDER");
            resultSet = statement.executeQuery();
            if (resultSet.next()){
                arrayList.add(resultSet.getInt(1));
            }


            Gson gson = new Gson();
            String s = gson.toJson(arrayList);
            response.setContentType("application/json");
            response.setCharacterEncoding("UTF-8");
            response.getWriter().write(s);


        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            if (statement != null) {
                try {
                    statement.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
            if (connection != null) {
                try {
                    connection.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
