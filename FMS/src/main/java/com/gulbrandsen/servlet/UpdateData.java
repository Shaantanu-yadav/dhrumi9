package com.gulbrandsen.servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.util.Calendar;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/UpdateData")
public class UpdateData extends HttpServlet {
	private static final long serialVersionUID = 1L;

	public UpdateData() {
		super();

	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		response.getWriter().append("Served at: ").append(request.getContextPath());

		String productgroup = request.getParameter("productGroup");
		String productcode = request.getParameter("productCode");
		String productname = request.getParameter("productName");
		String sapproductcode = request.getParameter("sap");
		String status = request.getParameter("status");
		String pId= request.getParameter("id");

		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
			Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/test", "root", "1234");

			PreparedStatement ps = (PreparedStatement) conn.prepareStatement(
					"update test.product_master set product_group=?, product_abbr=?, product_name=?, Sap=? , status=?, update_date_time=? WHERE product_id=?");

			ps.setString(1, productgroup);
			ps.setString(2, productcode);
			ps.setString(3, productname);
			ps.setString(4, sapproductcode);
			ps.setString(5, status);
			ps.setDate(6, new java.sql.Date(Calendar.getInstance().getTimeInMillis()));
			ps.setString(7, pId);
			
			ps.executeUpdate();
			int x = ps.executeUpdate();
			if (x > 0) {

				RequestDispatcher rd = request.getRequestDispatcher("/productmaster.jsp");
				rd.forward(request, response);
			} else {
				PrintWriter out = response.getWriter();
				out.print("Not Done");
			}

			ps.close();
		}

		catch (Exception e) {
			PrintWriter out = response.getWriter();
			out.print(e);
		}
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		doGet(request, response);

	}

}