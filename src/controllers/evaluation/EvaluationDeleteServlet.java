package controllers.evaluation;

import java.io.IOException;

import javax.persistence.EntityManager;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import models.Evaluation;
import models.Report;
import utils.DBUtil;

/**
 * Servlet implementation class EvaluationDeleteServlet
 */
@WebServlet("/evaluations/delete")
public class EvaluationDeleteServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    /**
     * @see HttpServlet#HttpServlet()
     */
    public EvaluationDeleteServlet() {
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

            Report r = em.find(Report.class ,Integer.parseInt(request.getParameter("report_id")));
            Evaluation e = em.find(Evaluation.class ,Integer.parseInt(request.getParameter("eval_id")));
            em.remove(e);

            em.getTransaction().begin();
            em.getTransaction().commit();
            em.close();

            response.sendRedirect(request.getContextPath() + "/reports/show?id=" + r.getId());
        }
    }

}
