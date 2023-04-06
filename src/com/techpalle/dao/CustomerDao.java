package com.techpalle.dao;

import java.sql.*;
import java.util.ArrayList;

import com.techpalle.model.Customer;


public class CustomerDao {
	private static final String dburl = "jdbc:mysql://localhost:3306/customer_managment";
	private static final String dbusername = "root";
	private static final String dbpassword = "admin";

	private static Connection con = null;
	private static PreparedStatement ps = null;
	private static Statement stm = null;
	private static ResultSet rs = null;

	private static final String customerListQuery = "select * from customer";
	private static final String customerInsert = "insert into customer(name,email,mobile) values(?,?,?)";
	private static final String customerEditQry = "select * from customer where id=?";
	private static final String customerUpdateQry = "update customer set name=?, email=?, mobile=? where id=?";
	private static final String customerDeleteQry = "delete from customer where id=?";
	
	public static Connection getConnectionDef() {
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
			con = DriverManager.getConnection(dburl, dbusername, dbpassword);
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return con;

	}
	
	
	public static void insertCustomers(Customer customer) // model as the input parameter
	{

		try {
			con = getConnectionDef();
			ps = con.prepareStatement(customerInsert);
			ps.setString(1, customer.getName());
			ps.setString(2, customer.getEmail());
			ps.setLong(3, customer.getMobile());

			ps.executeUpdate();

		} catch (SQLException e) {

			e.printStackTrace();
		} finally {
			if (ps != null) {
				try {
					ps.close();
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			if (con != null) {
				try {
					con.close();
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
	}
	
	public static Customer getOneCustomer(int id)
	{
		Customer c = null;
		try 
		{
			con = getConnectionDef();

			ps = con.prepareStatement(customerEditQry);
			ps.setInt(1, id);
			
			rs = ps.executeQuery();
			
			rs.next();
			
			int i = rs.getInt("id");
			String n = rs.getString("name");
			String e = rs.getString("email");
			long m = rs.getLong("mobile");
			
			c = new Customer(i, n, e, m);
			
		} 
		catch (SQLException e) 
		{
			e.printStackTrace();
		}
		finally
		{
			try
			{
				if (rs != null)
				{
					rs.close();
				}
				if (ps != null)
				{
					ps.close();
				}
				if (con != null)
				{
					con.close();
				}
			}
			catch (SQLException e) 
			{
				e.printStackTrace();
			}
		}
		return c;
	}

	public static void updateCustomer(Customer c)
	{
		
		try 
		{
			con = getConnectionDef();

			ps = con.prepareStatement(customerUpdateQry);
			ps.setString(1, c.getName());
			ps.setString(2, c.getEmail());
			ps.setLong(3, c.getMobile());
			ps.setInt(4, c.getId());
			
			ps.executeUpdate();
		} 
		catch (SQLException e) 
		{
			e.printStackTrace();
		}
		finally
		{
			try
			{
				if (ps != null)
				{
					ps.close();
				}
				if (con != null)
				{
					con.close();
				}
			}
			catch (SQLException e) 
			{
				e.printStackTrace();
			}
		}
	}
	
	public static void deleteCustomer(int id)
	{
		
		try 
		{
			con = getConnectionDef();

			ps = con.prepareStatement(customerDeleteQry);
			ps.setInt(1, id);
			
			ps.executeUpdate();
		} 
		catch (SQLException e)
		{
			e.printStackTrace();
		}
		finally
		{
			try
			{
				if (ps != null)
				{
					ps.close();
				}
				if (con != null)
				{
					con.close();
				}
			}
			catch (SQLException e) 
			{
				e.printStackTrace();
			}
		}
	}
	public static ArrayList<Customer> getAllCustomer() {
		ArrayList<Customer> al = new ArrayList<Customer>();

		try {
			con = getConnectionDef();

			stm = con.createStatement();

			// to execute query
			rs = stm.executeQuery(customerListQuery);

			// read data from resultset
			while (rs.next()) {
				int i = rs.getInt("id");
				String n = rs.getString("name");
				String e = rs.getString("email");
				long m = rs.getLong("mobile");

				// store it in the object so we can share it to other file
				Customer c = new Customer(i, n, e, m);

				// store that value from object to the arraylist
				al.add(c);
			}

		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			if (rs != null) {
				try {
					rs.close();
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				if (stm != null) {
					try {
						stm.close();
					} catch (SQLException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
				if (ps != null) {
					try {
						ps.close();
					} catch (SQLException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
				if (con != null) {
					try {
						con.close();
					} catch (SQLException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
			}
		}
		return al;

	}

}
