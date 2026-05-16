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

@WebServlet("/restaurant")
public class RestaurantServlet extends HttpServlet {

    protected void doGet(HttpServletRequest request,
                         HttpServletResponse response)
            throws ServletException, IOException {

        response.setContentType("text/html");

        PrintWriter out = response.getWriter();

        try {

            Connection con =
                    DBConnection.getConnection();

            String query =
                    "SELECT * FROM restaurants";

            PreparedStatement ps =
                    con.prepareStatement(query);

            ResultSet rs =
                    ps.executeQuery();

            out.println("<html>");
            out.println("<head>");
            out.println("<link rel='stylesheet' href='style.css'>");
            out.println("<title>Restaurants</title>");
            out.println("</head>");
            out.println("<body class='image-page'>");

            out.println("<div class='page-container'>");
            out.println("<h1>🍔 Restaurants</h1>");

            while(rs.next()) {

                out.println(
                        "<div style='background:white;"
                                + "width:300px;"
                                + "margin:20px auto;"
                                + "padding:20px;"
                                + "border-radius:12px;"
                                + "box-shadow:0 0 10px gray;'>"

                                + "<h2>"
                                + rs.getString("restaurant_name")
                                + "</h2>"

                                + "<p>"
                                + rs.getString("location")
                                + "</p>"

                                + "<a class='btn' href='menu?restaurant_id="
                                + rs.getInt("restaurant_id")
                                + "'>View Menu</a>"

                                + "</div>"
                );
            }

            out.println("<br>");
            out.println("<a class='logout-btn' href='dashboard.html'>Dashboard</a>");
            out.println("</div>");

            out.println("</body>");
            out.println("</html>");

        } catch(Exception e) {
            e.printStackTrace();
        }
    }
}