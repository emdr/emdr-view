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

import edu.iusb.emdr.analysis.AnalysisDao;

@WebServlet("/Analysis")
public class Analysis extends HttpServlet {
	/**
	 * Servlet implementation class Stations
	 */
	
		private static final long serialVersionUID = 1L;

		private AnalysisDao dao;
		Map margins;

		/**
		 * @see HttpServlet#HttpServlet()
		 */
		public Analysis() {
			super();
			// TODO Auto-generated constructor stub
		}

		@Override
		public void init() throws ServletException {
			dao = new AnalysisDao();
			try {
				margins = dao.getOrders();
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
//			HttpSession session = request.getSession(true);

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


