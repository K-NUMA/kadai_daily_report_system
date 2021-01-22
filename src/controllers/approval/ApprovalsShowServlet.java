package controllers.approval;

import java.io.IOException;

import javax.persistence.EntityManager;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import models.Approval;
import models.Employee;
import models.Report;
import utils.DBUtil;

/**
 * Servlet implementation class ApprovalsShowServlet
 */
@WebServlet("/approvals/show")
public class ApprovalsShowServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    /**
     * @see HttpServlet#HttpServlet()
     */
    public ApprovalsShowServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

    /**
     * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
     */
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        EntityManager em = DBUtil.createEntityManager();

        int report_id = Integer.parseInt(request.getParameter("id"));

        Approval ap = em.find(Approval.class,report_id);
        Report r = em.find(Report.class,report_id);
        Employee e = (Employee)request.getSession().getAttribute("login_employee");

        request.setAttribute("report",r);
        request.setAttribute("approval",ap);
        request.setAttribute("app_employee",e);

        em.close();

        if(ap == null){
            request.setAttribute("apstatus", "未承認です。");
        }else{
            request.setAttribute("apstatus", "承認済みです。");
        }

        RequestDispatcher rd = request.getRequestDispatcher("/WEB-INF/views/approvals/show.jsp");
        rd.forward(request, response);
    }

}
