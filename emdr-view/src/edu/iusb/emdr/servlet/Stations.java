package edu.iusb.emdr.servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.simple.JSONValue;

import edu.iusb.emdr.dao.StationsDao;

/**
 * Servlet implementation class Stations
 */
@WebServlet("/Stations")
public class Stations extends HttpServlet {
	private static final long serialVersionUID = 1L;

	private StationsDao dao;
	List<String> stations;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public Stations() {
		super();
		// TODO Auto-generated constructor stub
	}

	@Override
	public void init() throws ServletException {
		dao = new StationsDao();
		try {
			stations = dao.getStations();
		} catch (Exception e) {
			// TODO: handle exception
		}
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		// Set a cookie for the user
//		HttpSession session = request.getSession(true);

		response.setContentType("application/json");
		PrintWriter out = response.getWriter();

		JSONValue.writeJSONString(stations, out);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doPost(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
	}

}
