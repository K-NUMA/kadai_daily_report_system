package controllers.reports;

import java.io.IOException;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.servlet.RequestDispatcher;
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
 * Servlet implementation class ReportsShowServlet
 */
@WebServlet("/reports/show")
public class ReportsShowServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    /**
     * @see HttpServlet#HttpServlet()
     */
    public ReportsShowServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

    /**
     * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
     */
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        EntityManager em = DBUtil.createEntityManager();

        Report r = em.find(Report.class, Integer.parseInt(request.getParameter("id")));
        Evaluation e = null;

        //Evalutionテーブルにログイン中の社員番号と日報のIDが格納されているレコードが
        //無ければ、NoResultExceptionという例外が投げられるためtry～catch文で例外を回避
        try{
            e = em.createNamedQuery("getEvaledReport",Evaluation.class)
                .setParameter("employee", (Employee)request.getSession().getAttribute("login_employee"))
                .setParameter("report", r)
                .getSingleResult();
        }catch(NoResultException ex){

        }

            //高評価および低評価の日報をテーブルのクエリを指定して探索し、
            //見つかった件数を各変数に格納
            long good_reports_count = (long)em.createNamedQuery("getMyReportsGoodCount",Long.class)
                    .setParameter("report", r)
                    .getSingleResult();

            long bad_reports_count = (long)em.createNamedQuery("getMyReportsBadCount",Long.class)
                    .setParameter("report", r)
                    .getSingleResult();

        em.close();

        request.setAttribute("report", r);
        request.setAttribute("evaluation", e);
        request.setAttribute("_token", request.getSession().getId());
        request.setAttribute("good_count",good_reports_count);
        request.setAttribute("bad_count",bad_reports_count);

        RequestDispatcher rd = request.getRequestDispatcher("/WEB-INF/views/reports/show.jsp");
        rd.forward(request, response);
    }



}
