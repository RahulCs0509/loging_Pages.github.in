package com.rkdeveloper.registration;

import java.io.IOException;
import java.sql.*;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;


@WebServlet("/login")
public class LoginServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
			String umail=request.getParameter("username");
			String upwd=request.getParameter("password");
			HttpSession session=request.getSession();
			Connection con=null;
			PreparedStatement pstmt=null;
			ResultSet rs=null;
			RequestDispatcher dispatcher=null;
			
			String qry="select * from login.users where umail=? and upwd=?";
			try {
				Class.forName("com.mysql.jdbc.Driver");
				con=DriverManager.getConnection("jdbc:mysql://localhost:3306?user=root&password=tiger@1450");
				pstmt=con.prepareStatement(qry);
				pstmt.setString(1, umail);
				pstmt.setString(2, upwd);
				
				rs=pstmt.executeQuery();
				if(rs.next()) {
					session.setAttribute("name", rs.getString("uname"));
					dispatcher =request.getRequestDispatcher("index.jsp");
				}
				else {
					request.setAttribute("status", "failed");
					dispatcher=request.getRequestDispatcher("login.jsp");
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
