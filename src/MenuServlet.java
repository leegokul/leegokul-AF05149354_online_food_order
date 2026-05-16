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

@WebServlet("/menu")
public class MenuServlet extends HttpServlet {

    protected void doGet(HttpServletRequest request,
                         HttpServletResponse response)
            throws ServletException, IOException {

        int restaurantId =
                Integer.parseInt(
                        request.getParameter(
                                "restaurant_id"
                        )
                );

        response.setContentType("text/html");

        PrintWriter out =
                response.getWriter();

        try {

            Connection con =
                    DBConnection.getConnection();

            String query =
                    "SELECT * FROM menu WHERE restaurant_id=?";

            PreparedStatement ps =
                    con.prepareStatement(query);

            ps.setInt(1, restaurantId);

            ResultSet rs =
                    ps.executeQuery();

            out.println("<html>");
            out.println("<head>");
            out.println("<link rel='stylesheet' href='style.css'>");
            out.println("<title>Menu</title>");
            out.println("</head>");
            out.println("<body class='image-page'>");
            out.println("<div class='page-container'>");
            out.println("<h1>🍕 Food Menu</h1>");

            while(rs.next()) {

                out.println(
                        "<div style='background:white;"
                                + "width:320px;"
                                + "margin:20px auto;"
                                + "padding:20px;"
                                + "border-radius:12px;"
                                + "box-shadow:0 0 10px gray;'>"

                                + "<h2>"
                                + rs.getString("food_name")
                                + "</h2>"

                                + "<h3>₹"
                                + rs.getDouble("price")
                                + "</h3>"

                                + "<a class='btn' href='order?menu_id="
                                + rs.getInt("menu_id")
                                + "'>Order Now</a>"

                                + "</div>"
                );
            }

            out.println("<br>");
            out.println(
                    "<a class='logout-btn' href='restaurant'>Back</a>"
            );

            out.println("</div>");
            out.println("</body>");
            out.println("</html>");

        } catch(Exception e) {
            e.printStackTrace();
        }
    }
}