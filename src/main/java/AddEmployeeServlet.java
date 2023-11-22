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

@WebServlet("/add")

public class AddEmployeeServlet extends HttpServlet {
	private final static String query = "insert into employees(name,designation,salary) values(?,?,?)";
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
		PrintWriter out = res.getWriter();
		res.setContentType("text/html");
		
		out.println("<link rel='stylesheet' href ='css/bootstrap.css\'></link>");
		String name =req.getParameter("EmployeeName");
		String designation = req.getParameter("EmployeeDesignation");
//		Double salary = (double) Double.parseDouble(req.getParameter("EmployeeSalary"));
		String salaryParameter = req.getParameter("EmployeeSalary");
		Double salary = (salaryParameter != null && !salaryParameter.isEmpty()) ? Double.parseDouble(salaryParameter) : 0.0;

		
		
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
		}catch(Exception e) {
			e.printStackTrace();
		}
		try(Connection con = DriverManager.getConnection("jdbc:mysql:///employeemanagement","root","root");
				PreparedStatement ps = con.prepareStatement(query);){
			ps.setString(1, name);
			ps.setString(2, designation);
			ps.setDouble(3, salary);
			int count = ps.executeUpdate();
			
			out.println("<div class='card' style='margin:autho;width:500px;margin-top:100px'>");
			
			if(count==1) {
				out.println("<h2 class ='bg-secondary text-light text-center '>Employee added successfully</h2>");
			}else {
				out.println("<h2 class ='bg-danger text-light text-center>Employee not added</h2>");
			}
			
		} catch (SQLException se) {
			out.println("<h2 class ='bg-danger text-light text-center>"+se.getMessage()+"</h2>");
			se.printStackTrace();
		}catch(Exception e) {
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
