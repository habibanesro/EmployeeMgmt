import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@WebServlet("/viewdata")
public class ViewEmployeeServlet extends HttpServlet {
    private final static String query = "select id, name, designation, salary from employees";

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
        PrintWriter out = res.getWriter();
        res.setContentType("text/html");

        out.println("<link rel='stylesheet' href ='css/bootstrap.css\'></link>");

        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
        } catch (Exception e) {
            e.printStackTrace();
        }

        try (Connection con = DriverManager.getConnection("jdbc:mysql:///employeemanagement", "root", "root");
                PreparedStatement ps = con.prepareStatement(query)) {

            ResultSet rs = ps.executeQuery();

            out.println("<div style='margin:auto;width:900px;margin-top:100px;'>");
            out.println("<table class='table table-hover table-striped'>");
            out.println("<tr class='bg-primary'>");
            out.println("<th>ID</th>");
            out.println("<th>Name</th>");
            out.println("<th>Designation</th>");
            out.println("<th>Salary</th>");
            out.println("<th>Edit</th>");
            out.println("<th>Delete</th>");
            out.println("<tr>");

            while (rs.next()) {
                int id = rs.getInt("id");
                String name = rs.getString("name");
                String designation = rs.getString("designation");
                double salary = rs.getDouble("salary");

                out.println("<tr>");
                out.println("<td>" + id + "</td>");
                out.println("<td>" + name + "</td>");
                out.println("<td>" + designation + "</td>");
                out.println("<td>" + salary + "</td>");
                out.println("<td><a href='editurl?id=" + id + "'>Edit</a></td>");
                out.println("<td><a href='deleteurl?id=" + id + "'>Delete</a></td>");
                out.println("</tr>");
            }

            out.println("</table>");
//            out.println("</div>");

        } catch (SQLException se) {
            out.println("<h2 class ='bg-danger text-light text-center>" + se.getMessage() + "</h2>");
            se.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }

        
        out.println("<a href='index.html'><button class='btn btn-outline-dark'>Back</button></a>");
        out.println("</div>");
        out.close();
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
        doGet(req, res);
    }
}
