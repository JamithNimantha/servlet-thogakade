package webapp;

import com.google.gson.Gson;
import webapp.dto.ItemDTO;
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

@WebServlet(name = "Item")
public class Item extends HttpServlet {
    private DataSource dataSource;

    @Override
    public void init() throws ServletException {
        super.init();
        if (dataSource==null) {
            dataSource = (DataSource) getServletContext().getAttribute("dbPool");
        }
    }
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String code = request.getParameter("code");
        String name = request.getParameter("name");
        String description = request.getParameter("description");
        double price = Double.parseDouble(request.getParameter("price"));
        int qty = Integer.parseInt(request.getParameter("qty"));

        Connection connection = null;
        PreparedStatement statement = null;

        try {
            connection = dataSource.getConnection();
            statement = connection.prepareStatement("INSERT INTO T_ITEM VALUES (?,?,?,?,?)");
            statement.setString(1,code);
            statement.setString(2,name);
            statement.setString(3,description);
            statement.setDouble(4,price);
            statement.setDouble(5,qty);

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
            ArrayList<ItemDTO> artrayList = new ArrayList<>();
            connection = dataSource.getConnection();
            statement = connection.prepareStatement("SELECT * FROM T_ITEM");

            ResultSet resultSet = statement.executeQuery();

            while (resultSet.next()) {
                ItemDTO dto = new ItemDTO();
                dto.setCode(resultSet.getString(1));
                dto.setName(resultSet.getString(2));
                dto.setDescription(resultSet.getString(3));
                dto.setPrice(resultSet.getDouble(4));
                dto.setQty(resultSet.getInt(4));
                artrayList.add(dto);
            }

            Gson gson = new Gson();
            String s = gson.toJson(artrayList);
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

        String code = req.getParameter("code");
        String name = req.getParameter("name");
        String description = req.getParameter("description");
        double price = Double.parseDouble(req.getParameter("price"));
        int qry = Integer.parseInt(req.getParameter("qty"));

        Connection connection = null;
        PreparedStatement statement = null;

        try {
            connection = dataSource.getConnection();
            statement = connection.prepareStatement("UPDATE T_ITEM SET NAME=?,DESCRIPTION=?,UNIT_PRICE=?,QTY_ON_HAND=? WHERE CODE=?");
            statement.setString(1,name);
            statement.setString(2,description);
            statement.setDouble(3,price);
            statement.setDouble(4,qry);
            statement.setString(5,code);

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

        String code = req.getParameter("code");

        Connection connection = null;
        PreparedStatement statement = null;

        try {
            connection = dataSource.getConnection();
            statement = connection.prepareStatement("DELETE FROM T_ITEM WHERE CODE=?");
            statement.setString(1,code);

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
