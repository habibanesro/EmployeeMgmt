import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@WebServlet("/deleteurl")
public class DeleteEmployeeServlet extends HttpServlet {
	
	private final static String query = "delete from employees where id=?";
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
		PrintWriter out = res.getWriter();
		res.setContentType("text/html");
		
		out.println("<link rel='stylesheet' href ='css/bootstrap.css\'></link>");
		
		int id = Integer.parseInt(req.getParameter("id"));
		
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
		}catch(Exception e) {
			e.printStackTrace();
		}
		try(Connection con = DriverManager.getConnection("jdbc:mysql:///employeemanagement","root","root");
				PreparedStatement ps = con.prepareStatement(query);){
			ps.setInt(1, id);
			int count = ps.executeUpdate();
			
			out.println("<div class='card' style='margin:autho;width:500px;margin-top:100px'>");
			
			if(count==1) {
				out.println("<h2 class ='bg-secondary text-light text-center '>Employee Removed successfully</h2>");
			}else {
				out.println("<h2 class ='bg-danger text-light text-center>Employee is not Removed</h2>");
			}
			
		} catch (SQLException se) {
			out.println("<h2 class ='bg-success text-light text-center>"+se.getMessage()+"</h2>");
			se.printStackTrace();
		}catch(Exception e) {
			e.printStackTrace();
		}
		out.println("<a href='index.html'><button class='btn btn-outline-dark'>Home</button></a>");
		out.println("&nbsp; &nbsp;");
		out.println("<a href='viewdata'><button class='btn btn-outline-dark'>View Employees</button></a>");
		
		out.println("</div>");
		//close the stream
		out.close();
	}
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
		doGet(req, res);
	}
}

