package com.techpalle.controller;

import java.io.IOException;
import java.util.ArrayList;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.techpalle.dao.CustomerDao;
import com.techpalle.model.Customer;

@WebServlet("/")
public class CustomerController extends HttpServlet {
	private static final long serialVersionUID = 1L;

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String path = request.getServletPath();

		switch (path) {
		case "/insertForm":
			getInsertForm(request, response);
			break;

		case "/add":
			insertCustomer(request, response);
			break;
		case "/edit":
			getEditForm(request, response);
			break;
		case "/update":
			updateCustomer(request, response);
			break;
		case "/delete":
			deleteCustomer(request, response);
			break;
		default:
			getStartUpPage(request, response);
			break;
		}
	}

	private void getStartUpPage(HttpServletRequest request, HttpServletResponse response) {
		// method reading table data and redirect the data to customer-list

		try {
			// to data from database to servlet
			ArrayList<Customer> alCustomer = CustomerDao.getAllCustomer();

			RequestDispatcher rd = request.getRequestDispatcher("Customer-list.jsp");
			// send data from servlet to customer-lidt.jsp (browser)
			request.setAttribute("al", alCustomer);
			rd.forward(request, response);
		} catch (ServletException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}

	}

	// it will redirect user to customer-form page //1st URL(add customer button)
	private void getInsertForm(HttpServletRequest request, HttpServletResponse response) {
		// redirecting method
		
		try {
			RequestDispatcher rd = request.getRequestDispatcher("Customer-form.jsp");
			rd.forward(request, response);
		} catch (ServletException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	// 2nd URL (SAVE button)
	private void insertCustomer(HttpServletRequest request, HttpServletResponse response) { // reading data from customer
																							// form page
		String n = request.getParameter("tbName");
		String e = request.getParameter("tbEmail");
		long m = Long.parseLong(request.getParameter("tbMobile"));

		// store the admin given data into model/object
		Customer c = new Customer(n, e, m);

		// Insert customer data to db //call the method from dao
		CustomerDao.insertCustomers(c); 														

		// redirect user to home page (customer-list page)
		getStartUpPage(request, response); // one of the method to call

	}

	private void getEditForm(HttpServletRequest request, HttpServletResponse response) {
		   //fetch the id from url:
		int i = Integer.parseInt(request.getParameter("id"));

		Customer c = CustomerDao.getOneCustomer(i);

		try {
			RequestDispatcher rd = request.getRequestDispatcher("Customer-form.jsp");
			request.setAttribute("customer", c);
			rd.forward(request, response);
			
		} catch (ServletException | IOException e) {
			e.printStackTrace();
		}
	}

	private void updateCustomer(HttpServletRequest request, HttpServletResponse response) {
		int i = Integer.parseInt(request.getParameter("tbId"));
		String n = request.getParameter("tbName");
		String e = request.getParameter("tbEmail");
		long m = Long.parseLong(request.getParameter("tbMobile"));

		Customer c = new Customer(i, n, e, m);

		CustomerDao.updateCustomer(c);

		try {
			response.sendRedirect("list");
		} catch (IOException e1) {
			e1.printStackTrace();
		}
	}

	private void deleteCustomer(HttpServletRequest request, HttpServletResponse response) {
		int i = Integer.parseInt(request.getParameter("id"));

		CustomerDao.deleteCustomer(i);

		try {
			response.sendRedirect("list");
		} catch (IOException e1) {
			e1.printStackTrace();
		}
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
