<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<c:import url="/WEB-INF/views/layout/app.jsp">
    <c:param name="content">
        <c:choose>
            <c:when test="${approval != null }">
                <h2>日報 承認詳細画面</h2>

                <table>
                    <tbody>
                        <tr>
                            <th>氏名</th>
                            <td><c:out value="${approval.employee.name}" /></td>
                        </tr>
                        <tr>
                            <th>日付</th>
                            <td><fmt:formatDate value="${approval.report_date}" pattern="yyyy-MM-dd" /></td>
                        </tr>
                        <tr>
                            <th>タイトル</th>
                            <td>
                                <pre><c:out value="${approval.report_title}" /></pre>
                            </td>
                        </tr>
                        <tr>
                            <th>内容</th>
                            <td>
                                <pre><c:out value="${approval.report_content}" /></pre>
                            </td>
                        </tr>
                        <tr>
                            <th>承認日時</th>
                            <td><fmt:formatDate value="${approval.approval_date}" pattern="yyyy-MM-dd HH:mm:ss" /></td>
                        </tr>
                        <tr>
                            <th>承認ステータス</th>
                            <td><c:out value="${apstatus}" /></td>
                        </tr>
                    </tbody>
                </table>

            </c:when>
            <c:otherwise>
                <h2>この日報はまだ承認していません。</h2>
                <p><a href="<c:url value='/approvals/new?report_id=${report.id}' />">日報の承認へ進む</a></p>
            </c:otherwise>
        </c:choose>

        <p><a href="<c:url value='/reports/index' />">一覧に戻る</a></p>
    </c:param>
</c:import>