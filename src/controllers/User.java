package controllers;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.hibernate.Session;
import org.hibernate.Transaction;

import models.Users;
import until.Until;

/**
 * Servlet implementation class User
 */
@WebServlet("/register")
public class User extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public User() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		
		String username = (String)request.getParameter("username");
		String password = (String)request.getParameter("password");
		Users user = new Users(username, password);
		Transaction transaction = null;
		try {
			// start a transaction
			Session session = Until.getSessionFactory().openSession();
			transaction = session.beginTransaction();
			session.save(user);
			// commit transaction
			transaction.commit();
			if (transaction != null) {
				List<Users>  allUsers = getAllUsers();
				request.setAttribute("allUsers", allUsers);
				request.getRequestDispatcher("/allusers.jsp").forward(request, response);
			}
		} catch (Exception e) {
			if (transaction != null) {
				transaction.rollback();
			}
			e.printStackTrace();
		}
	}

	protected List<Users> getAllUsers() {
		Transaction transaction = null;
		List<Users> listOfUser = null;
		try{
			Session session = Until.getSessionFactory().openSession() ;
			// start a transaction
			transaction = session.beginTransaction();
			// get an user object

			listOfUser = session.createQuery("from Users").getResultList();

			// commit transaction
			transaction.commit();
		} catch (Exception e) {
			if (transaction != null) {
				transaction.rollback();
			}
			e.printStackTrace();
		}
		return listOfUser;
	}
}
