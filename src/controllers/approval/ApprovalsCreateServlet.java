package controllers.approval;

import java.io.IOException;
import java.sql.Timestamp;

import javax.persistence.EntityManager;
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
 * Servlet implementation class ApprovalsCreateServlet
 */
@WebServlet("/approvals/create")
public class ApprovalsCreateServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    /**
     * @see HttpServlet#HttpServlet()
     */
    public ApprovalsCreateServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

    /**
     * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
     */
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String _token = (String)request.getParameter("_token");
        if(_token != null && _token.equals(request.getSession().getId())){
            EntityManager em = DBUtil.createEntityManager();

            int report_id = Integer.parseInt(request.getParameter("report_id"));
            Report r = em.find(Report.class,report_id);

            Approval a = new Approval();

            a.setId(report_id);
            a.setEmployee((Employee)request.getSession().getAttribute("login_employee"));
            a.setReport_date(r.getReport_date());
            a.setReport_title(r.getTitle());
            a.setReport_content(r.getContent());

            Timestamp currentTime =  new Timestamp(System.currentTimeMillis());
            a.setApproval_date(currentTime);

            //MySQLへコミットする
            em.getTransaction().begin();
            em.persist(a);
            em.getTransaction().commit();
            em.close();

            request.getSession().setAttribute("flush", "承認が完了しました。");

            response.sendRedirect(request.getContextPath() + "/reports/index");
        }
    }

}
