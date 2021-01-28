package models;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;

@Table(name = "evaluation")
@NamedQueries({
    @NamedQuery(
            name = "getEvaledReport",
            query = "SELECT e FROM Evaluation AS e WHERE e.employee = :employee AND e.report = :report"
            ),
    @NamedQuery(
            name = "getMyReportsGoodCount",
            query = "SELECT COUNT(e) FROM Evaluation AS e WHERE e.report = :report AND e.good_report = 1"
            ),
    @NamedQuery(
            name = "getMyReportsBadCount",
            query = "SELECT COUNT(e) FROM Evaluation AS e WHERE e.report = :report AND e.bad_report = 1"
            )
})
@Entity
public class Evaluation {
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne
    @JoinColumn(name = "employee_id", nullable = false)
    private Employee employee;

    @ManyToOne
    @JoinColumn(name = "report_id", nullable = false)
    private Report report;

    @Column(name = "good_report",nullable = false)
    private Integer good_report;

    @Column(name = "bad_report",nullable = false)
    private Integer bad_report;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Employee getEmployee() {
        return employee;
    }

    public void setEmployee(Employee employee) {
        this.employee = employee;
    }

    public Report getReport() {
        return report;
    }

    public void setReport(Report report) {
        this.report = report;
    }

    public Integer getGood_report() {
        return good_report;
    }

    public void setGood_report(Integer good_report) {
        this.good_report = good_report;
    }

    public Integer getBad_report() {
        return bad_report;
    }

    public void setBad_report(Integer bad_report) {
        this.bad_report = bad_report;
    }


}
