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

@WebServlet(name = "Customer")
public class Customer extends HttpServlet {

    private DataSource dataSource;

    @Override
    public void init() throws ServletException {
        super.init();
        if (dataSource==null) {
            dataSource = (DataSource) getServletContext().getAttribute("dbPool");
        }
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        String id = request.getParameter("id");
        String name = request.getParameter("name");
        String address = request.getParameter("address");
        double salary = Double.parseDouble(request.getParameter("salary"));

        Connection connection = null;
        PreparedStatement statement = null;

        try {

            connection = dataSource.getConnection();
            statement = connection.prepareStatement("INSERT INTO T_CUSTOMER VALUES (?,?,?,?)");
            statement.setString(1,id);
            statement.setString(2,name);
            statement.setString(3,address);
            statement.setDouble(4,salary);

            int rst = statement.executeUpdate();


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

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        Connection connection = null;
        PreparedStatement statement = null;

        try {
            ArrayList<CustomerDTO> arrayList = new ArrayList<>();
            connection = dataSource.getConnection();
            statement = connection.prepareStatement("SELECT * FROM T_CUSTOMER");

            ResultSet resultSet = statement.executeQuery();

            while (resultSet.next()) {
                CustomerDTO dto = new CustomerDTO();
                dto.setId(resultSet.getString(1));
                dto.setName(resultSet.getString(2));
                dto.setAddress(resultSet.getString(3));
                dto.setSalary(resultSet.getDouble(4));
                arrayList.add(dto);
            }

            Gson gson = new Gson();
            String s = gson.toJson(arrayList);
            response.setContentType("application/json");
            response.setCharacterEncoding("UTF-8");
            response.getWriter().write(s);

//            request.setAttribute("customers", arrayList);
//            request.getRequestDispatcher("/app/customer.html").forward(request, response);

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

    @Override
    protected void service(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        String s = req.getParameter("_METHOD");
        if ("PUT".equals(s)) {
            doPut(req, resp);

        } else if ("POST".equals(s)) {
            doPost(req, resp);

        } else if ("DELETE".equals(s)) {
            doDelete(req, resp);

        } else if ("GET".equals(s)) {
            doGet(req, resp);

        } else {
            doGet(req, resp);

        }

    }

    @Override
    protected void doPut(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        String id = req.getParameter("id");
        String name = req.getParameter("name");
        String address = req.getParameter("address");
        double salary = Double.parseDouble(req.getParameter("salary"));

        Connection connection = null;
        PreparedStatement statement = null;

        try {
            connection = dataSource.getConnection();
            statement = connection.prepareStatement("UPDATE T_CUSTOMER SET NAME=?,ADDRESS=?,SALARY=? WHERE ID=?");
            statement.setString(1,name);
            statement.setString(2,address);
            statement.setDouble(3,salary);
            statement.setString(4,id);

            int rst = statement.executeUpdate();
            System.out.println(rst+" updated");


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

    @Override
    protected void doDelete(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        String id = req.getParameter("id");

        Connection connection = null;
        PreparedStatement statement = null;

        try {
            connection = dataSource.getConnection();
            statement = connection.prepareStatement("DELETE FROM T_CUSTOMER WHERE ID=?");
            statement.setString(1,id);

            int rst = statement.executeUpdate();
            System.out.println(rst+" DELETED");


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
