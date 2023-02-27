package com.rkdeveloper.registration;

import java.io.IOException;
import java.sql.*;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Servlet implementation class RegistrationServlet
 */
@WebServlet("/register")
public class RegistrationServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
  
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		String uname=request.getParameter("name");
		String upwd=request.getParameter("pass");
		String umail=request.getParameter("email");
		String umno=request.getParameter("contact");
		RequestDispatcher dispatcher=null;
		
		Connection con=null;
		PreparedStatement pstmt=null;
		String qry="insert into login.users(uname,upwd,umail,umno) values(?,?,?,?) ";
		try {
			Class.forName("com.mysql.jdbc.Driver");
			con=DriverManager.getConnection("jdbc:mysql://localhost:3306?user=root&password=tiger@1450");
			pstmt=con.prepareStatement(qry);
			pstmt.setString(1, uname);
			pstmt.setString(2, upwd);
			pstmt.setString(3, umail);
			pstmt.setString(4, umno);
			
			int rowcount=pstmt.executeUpdate();
			dispatcher= request.getRequestDispatcher("registration.jsp");
			if(rowcount>0) {
				request.setAttribute("status", "success");
			}
			else {
				request.setAttribute("status", "failed");
			}
			
			dispatcher.forward(request, response);
			
		} catch (ClassNotFoundException | SQLException e) {
			e.printStackTrace();
		}
		finally {
			if(pstmt!=null) {
				try {
					pstmt.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
			
			if(con!=null) {
				try {
					con.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
		}
	}

}
