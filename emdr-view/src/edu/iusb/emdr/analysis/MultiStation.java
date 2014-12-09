package edu.iusb.emdr.analysis;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.Map;
import org.json.simple.JSONValue;

import edu.iusb.emdr.analysis.MultiStationDao;

@WebServlet("/MultiStation")
public class MultiStation extends HttpServlet {
	/**
	 * Servlet implementation class Stations
	 */
	
		private static final long serialVersionUID = 1L;

		private MultiStationDao dao;
		Map margins;

		/**
		 * @see HttpServlet#HttpServlet()
		 */
		public MultiStation() {
			super();
			// TODO Auto-generated constructor stub
		}

		@Override
		public void init() throws ServletException {
			
		}

		/**
		 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
		 *      response)
		 */
		protected void doGet(HttpServletRequest request,
				HttpServletResponse response) throws ServletException, IOException {
			// Set a cookie for the user
//			HttpSession session = request.getSession(true);
			String id = request.getParameter("Station");
			
			dao = new MultiStationDao();
			try {
				margins = dao.getOrders(); 
			} catch (Exception e) {
				// TODO: handle exception
			}
			response.setContentType("application/json");
			PrintWriter out = response.getWriter();

			JSONValue.writeJSONString(margins, out);
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


