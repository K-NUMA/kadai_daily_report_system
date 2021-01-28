package controllers.evaluation;

import java.io.IOException;

import javax.persistence.EntityManager;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import models.Employee;
import models.Evaluation;
import models.Report;
import utils.DBUtil;

/**
 * Servlet implementation class EvaluationCreateServlet
 */
@WebServlet("/evaluations/create")
public class EvaluationCreateServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    /**
     * @see HttpServlet#HttpServlet()
     */
    public EvaluationCreateServlet() {
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

            Evaluation e = new Evaluation();
            Integer gb = Integer.parseInt(request.getParameter("gb"));

            switch(gb){
                case 1:
                    e.setGood_report(1);
                    e.setBad_report(0);
                    break;

                case 0:
                    e.setBad_report(1);
                    e.setGood_report(0);
                    break;
            }

            //Employeeクラスは評価した従業員。ReportクラスのEmployeeフィールドと違うため注意
            Report r = em.find(Report.class ,Integer.parseInt(request.getParameter("id")));
            e.setEmployee((Employee)request.getSession().getAttribute("login_employee"));
            e.setReport(r);

            em.getTransaction().begin();
            em.persist(e);
            em.getTransaction().commit();
            em.close();

            response.sendRedirect(request.getContextPath() + "/reports/show?id=" + r.getId());
        }

    }

}
