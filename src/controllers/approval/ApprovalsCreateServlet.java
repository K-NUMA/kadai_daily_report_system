package controllers.approval;

import java.io.IOException;
import java.sql.Timestamp;

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
import models.validators.ApprovalValidator;
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
            Report r;
            Approval a = new Approval();

            if((ApprovalValidator.validate(a,report_id))){
                request.setAttribute("app_flush", "既に承認済みの日報です。");

                RequestDispatcher rd = request.getRequestDispatcher("/WEB-INF/views/approvals/new.jsp");
                rd.forward(request, response);
            }else{
                r = em.find(Report.class,report_id);
                Employee e = (Employee)request.getSession().getAttribute("login_employee");

                a.setId(report_id);
                a.setEmployee(r.getEmployee());
                a.setApproval_employee(e.getId());
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

}
