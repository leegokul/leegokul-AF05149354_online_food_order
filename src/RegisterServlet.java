import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.PreparedStatement;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@WebServlet("/register")
public class RegisterServlet extends HttpServlet {

    protected void doPost(HttpServletRequest request,
                          HttpServletResponse response)
            throws ServletException, IOException {

        String fullname = request.getParameter("fullname");
        String email = request.getParameter("email");
        String password = request.getParameter("password");
        String phone = request.getParameter("phone");

        response.setContentType("text/html");
        PrintWriter out = response.getWriter();

        try {

            Connection con = DBConnection.getConnection();

            String query =
                    "INSERT INTO users(fullname,email,password,phone) VALUES(?,?,?,?)";

            PreparedStatement ps =
                    con.prepareStatement(query);

            ps.setString(1, fullname);
            ps.setString(2, email);
            ps.setString(3, password);
            ps.setString(4, phone);

            int rows = ps.executeUpdate();

            if(rows > 0){

                response.sendRedirect(
                        "login.html"
                );
            }
            else{
                out.println("<h2>Registration Failed!</h2>");
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}