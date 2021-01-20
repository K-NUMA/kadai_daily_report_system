package models;

import java.sql.Date;
import java.sql.Timestamp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;

@Table(name="approvals")
@NamedQueries({
    @NamedQuery(
      name = "getAllApprovals",
      query = "SELECT a FROM Approval AS a ORDER BY a.id DESC"
     )
})
@Entity
public class Approval {
    @Id
    @Column(name = "id", nullable = false)
    private Integer id;

    @ManyToOne
    @JoinColumn(name = "employee_id", nullable = false)
    private Employee employee;

    @Column(name = "approval_employee_id", nullable = false)
    private Integer approval_employee;

    public Integer getApproval_employee() {
        return approval_employee;
    }

    public void setApproval_employee(Integer approval_employee) {
        this.approval_employee = approval_employee;
    }

    @Column(name = "report_date", nullable = false)
    private Date report_date;

    @Column(name = "report_title", length = 255 , nullable = false)
    private String report_title;

    @Column(name = "report_content", length = 255 , nullable = false)
    private String report_content;

    @Column(name = "approval_date", nullable = false)
    private Timestamp approval_date;

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

    public Date getReport_date() {
        return report_date;
    }

    public void setReport_date(Date report_date) {
        this.report_date = report_date;
    }

    public String getReport_title() {
        return report_title;
    }

    public void setReport_title(String report_title) {
        this.report_title = report_title;
    }

    public String getReport_content() {
        return report_content;
    }

    public void setReport_content(String report_content) {
        this.report_content = report_content;
    }

    public Timestamp getApproval_date() {
        return approval_date;
    }

    public void setApproval_date(Timestamp approval_date) {
        this.approval_date = approval_date;
    }



}
