package com.itsc;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

public class RegistrationServlet extends HttpServlet {
    private static final String DB_URL = "jdbc:mysql://localhost:3306/usersdb";
    private static final String DB_USER = "root";
    private static final String DB_PASSWORD = "root";

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse res) throws IOException {

        String name = req.getParameter("name");
        String email = req.getParameter("email");
        String password = req.getParameter("password");

        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            try (Connection conn = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD)){
            
            String query = "insert into users(name, email, password) values(?,?,?)";
            
	            try (PreparedStatement pstmt = conn.prepareStatement(query)){
	
		            pstmt.setString(1, name);
		            pstmt.setString(2, email);
		            pstmt.setString(3, password);
		            
		            pstmt.executeUpdate();
		            
		            conn.close();
	            }
            }
            
            res.sendRedirect("confirmation.jsp");
            
        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace(); // Fixed typo here
            
            
            res.getWriter().println("Registration failed. Please try again correctly.");
        }
    }
}
