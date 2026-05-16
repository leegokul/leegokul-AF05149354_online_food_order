import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.PreparedStatement;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

@WebServlet("/order")
public class OrderServlet extends HttpServlet {

    protected void doGet(HttpServletRequest request,
                         HttpServletResponse response)
            throws ServletException, IOException {

        int menuId =
                Integer.parseInt(
                        request.getParameter(
                                "menu_id"
                        )
                );

        response.setContentType("text/html");

        PrintWriter out =
                response.getWriter();

        out.println("<html>");
        out.println("<head>");
        out.println("<link rel='stylesheet' href='style.css'>");
        out.println("<title>Place Order</title>");
        out.println("</head>");
        out.println("<body>");

        out.println("<form action='order' method='post'>");

        out.println("<h1>🍔 Place Order</h1>");

        out.println(
                "<input type='hidden' "
                        + "name='menu_id' value='"
                        + menuId + "'>"
        );

        out.println(
                "Quantity:<br><br>"
                        + "<input type='number' "
                        + "name='quantity' "
                        + "required>"
                        + "<br><br>"
        );

        out.println(
                "<input type='submit' "
                        + "value='Place Order'>"
        );

        out.println("<br><br>");

        out.println(
                "<a class='logout-btn' href='restaurant'>Back</a>"
        );

        out.println("</form>");

        out.println("<body class='image-page'>");
        out.println("</html>");
    }

    protected void doPost(HttpServletRequest request,
                          HttpServletResponse response)
            throws ServletException, IOException {

        HttpSession session =
                request.getSession();

        int userId =
                (int) session.getAttribute(
                        "user_id"
                );

        int menuId =
                Integer.parseInt(
                        request.getParameter("menu_id")
                );

        int quantity =
                Integer.parseInt(
                        request.getParameter("quantity")
                );

        try {

            Connection con =
                    DBConnection.getConnection();

            String getPrice =
                    "SELECT price FROM menu WHERE menu_id=?";

            PreparedStatement ps1 =
                    con.prepareStatement(getPrice);

            ps1.setInt(1, menuId);

            var rs = ps1.executeQuery();

            double price = 0;

            if(rs.next()) {
                price = rs.getDouble("price");
            }

            double total =
                    quantity * price;

            String query =
                    "INSERT INTO orders(user_id,menu_id,quantity,total_price) VALUES(?,?,?,?)";

            PreparedStatement ps2 =
                    con.prepareStatement(query);

            ps2.setInt(1, userId);
            ps2.setInt(2, menuId);
            ps2.setInt(3, quantity);
            ps2.setDouble(4, total);

            int rows =
                    ps2.executeUpdate();

            if(rows > 0) {

                response.setContentType("text/html");

                response.getWriter().println(

                        "<html>"

                                + "<head>"
                                + "<link rel='stylesheet' href='style.css'>"
                                + "</head>"

                                + "out.println(\"<body class='image-page'>\");"

                                + "<div class='page-container'>"

                                + "<h1>🎉 Order Placed Successfully!</h1>"

                                + "<br>"

                                + "<a class='btn' href='dashboard.html'>"
                                + "Go To Dashboard"
                                + "</a>"

                                + "</div>"

                                + "</body>"
                                + "</html>"
                );

            }

        } catch(Exception e) {
            e.printStackTrace();
        }
    }
}