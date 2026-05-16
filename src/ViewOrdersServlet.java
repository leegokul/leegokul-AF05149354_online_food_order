import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@WebServlet("/vieworders")
public class ViewOrdersServlet extends HttpServlet {

    protected void doGet(HttpServletRequest request,
                         HttpServletResponse response)
            throws ServletException, IOException {

        response.setContentType("text/html");

        PrintWriter out =
                response.getWriter();

        try {

            Connection con =
                    DBConnection.getConnection();

            String query =
                    "SELECT orders.order_id, " +
                            "menu.food_name, " +
                            "orders.quantity, " +
                            "orders.total_price " +
                            "FROM orders " +
                            "JOIN menu " +
                            "ON orders.menu_id = menu.menu_id";

            PreparedStatement ps =
                    con.prepareStatement(query);

            ResultSet rs =
                    ps.executeQuery();

            out.println("<html>");

            out.println("<head>");
            out.println("<title>My Orders</title>");
            out.println("<link rel='stylesheet' href='style.css'>");
            out.println("</head>");

            out.println("<body class='image-page'>");

            out.println("<div class='page-container'>");
            out.println("<h1>📦 My Orders</h1>");

            while(rs.next()) {

                out.println(

                        "<div style='background:white;"
                                + "width:350px;"
                                + "margin:20px auto;"
                                + "padding:20px;"
                                + "border-radius:15px;"
                                + "box-shadow:0 0 10px gray;'>"

                                + "<h2>"
                                + rs.getString("food_name")
                                + "</h2>"

                                + "<p><b>Order ID:</b> "
                                + rs.getInt("order_id")
                                + "</p>"

                                + "<p><b>Quantity:</b> "
                                + rs.getInt("quantity")
                                + "</p>"

                                + "<p><b>Total Price:</b> ₹"
                                + rs.getDouble("total_price")
                                + "</p>"

                                + "</div>"
                );
            }

            out.println("<br>");

            out.println(
                    "<a href='dashboard.html' " +
                            "class='btn'>Dashboard</a>"
            );

            out.println("</div>");
            out.println("</body>");
            out.println("</html>");

        } catch(Exception e) {
            e.printStackTrace();
        }
    }
}