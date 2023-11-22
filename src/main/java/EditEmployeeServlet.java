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

@WebServlet("/editurl")
public class EditEmployeeServlet extends HttpServlet {
	
	private final static String query = "select id, name, designation, salary from employees where id=?";
	private final static String updateQuery = "update employees set name=?, designation=?, salary=? where id=?";
	
	
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
        PrintWriter out = res.getWriter();
        res.setContentType("text/html");
        
        int id = Integer.parseInt(req.getParameter("id"));
        out.println("<link rel='stylesheet' href ='css/bootstrap.css\'></link>");

        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
        } catch (Exception e) {
            e.printStackTrace();
        }

        try (Connection con = DriverManager.getConnection("jdbc:mysql:///employeemanagement", "root", "root");
                PreparedStatement ps = con.prepareStatement(query)) {
        	ps.setInt(1,id);
            ResultSet rs = ps.executeQuery();
            rs.next();

            out.println("<div style='margin:auto;width:500px;margin-top:100px;'>");
           out.println("<form action='edit?id="+id+"' method='post'>");
            out.println("<table class='table table-hover table-striped'>");
            out.println("<tr>");
            out.println("<td>Name</td>");
            out.println("<td><input type='text' name='name' value='"+rs.getString(1)+"'></td>");
            out.println("</tr>");
            out.println("<tr>");
            out.println("<td>Designation</td>");
            out.println("<td><input type='text' name='designation' value='"+rs.getString(2)+"'></td>");
            out.println("</tr>");
            out.println("<tr>");
            out.println("<td>Salary</td>");
            out.println("<td><input type='double' name='salary' value='"+rs.getDouble(3)+"'></td>");
            out.println("</tr>");
            out.println("<tr>");
            out.println("<td><button type='submit' class='btn btn-outline-dark'>Edit</button></td>");
//            out.println("<td><button type='reset'>Cancel</button></td>");
            out.println("</tr>");
            
            out.println("</table>");
            out.println("</form>");
            
            
            
            

        } catch (SQLException se) {
            out.println("<h2 class ='bg-danger text-light text-center>" + se.getMessage() + "</h2>");
            se.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }

        out.println("<a href='viewdata'><button class='btn btn-outline-dark'>Back</button></a>");
        out.println("</div>");
        out.close();
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
//        doGet(req, res);PrintWriter out = res.getWriter();
    	PrintWriter out= res.getWriter();
        res.setContentType("text/html");

        int id = Integer.parseInt(req.getParameter("id"));
        String name = req.getParameter("name");
        String designation = req.getParameter("designation");
        Double salary = Double.parseDouble(req.getParameter("salary"));

        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
        } catch (Exception e) {
            e.printStackTrace();
        }

        try (Connection con = DriverManager.getConnection("jdbc:mysql:///employeemanagement", "root", "root");
                PreparedStatement ps = con.prepareStatement(updateQuery)) {

            ps.setString(1, name);
            ps.setString(2, designation);
            ps.setDouble(3, salary);
            ps.setInt(4, id);

            int count = ps.executeUpdate();

            out.println("<div style='margin:auto;width:500px;margin-top:100px;'>");

            if (count == 1) {
                out.println("<h2 class ='bg-secondary text-light text-center '>Employee updated successfully</h2>");
            } else {
                out.println("<h2 class ='bg-danger text-light text-center'>Employee not updated</h2>");
            }

        } catch (SQLException se) {
            out.println("<h2 class ='bg-danger text-light text-center>" + se.getMessage() + "</h2>");
            se.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }

        out.println("<a href='viewdata'><button class='btn btn-outline-dark'>Back</button></a>");
        out.println("</div>");
        out.close();
    }
}