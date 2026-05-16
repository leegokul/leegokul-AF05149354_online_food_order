import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;


import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

@WebServlet("/login")
public class LoginServlet extends HttpServlet {

    protected void doPost(HttpServletRequest request,
                          HttpServletResponse response)
            throws ServletException, IOException {

        String email = request.getParameter("email");
        String password = request.getParameter("password");

        try {

            Connection con =
                    DBConnection.getConnection();

            String query =
                    "SELECT * FROM users WHERE email=? AND password=?";

            PreparedStatement ps =
                    con.prepareStatement(query);

            ps.setString(1, email);
            ps.setString(2, password);

            ResultSet rs = ps.executeQuery();

            if(rs.next()) {

                int userId =
                        rs.getInt("user_id");

                HttpSession session =
                        request.getSession();

                session.setAttribute(
                        "user_id",
                        userId
                );

                response.sendRedirect(
                        "dashboard.html"
                );

            } else {

                response.sendRedirect("loginerror.html");
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}