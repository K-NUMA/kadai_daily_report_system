<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<c:import url="/WEB-INF/views/layout/app.jsp">
    <c:param name="content">
        <c:choose>
            <c:when test="${report != null }">
                <h2>日報 詳細ページ</h2>

                <table>
                    <tbody>
                        <tr>
                            <th>氏名</th>
                            <td><c:out value="${report.employee.name}" /></td>
                        </tr>
                        <tr>
                            <th>日付</th>
                            <td><fmt:formatDate value="${report.report_date}" pattern="yyyy-MM-dd" /></td>
                        </tr>
                        <tr>
                            <th>内容</th>
                            <td>
                                <pre><c:out value="${report.content}" /></pre>
                            </td>
                        </tr>
                        <tr>
                            <th>登録日時</th>
                            <td><fmt:formatDate value="${report.created_at}" pattern="yyyy-MM-dd HH:mm:ss" /></td>
                        </tr>
                        <tr>
                            <th>更新日時</th>
                            <td><fmt:formatDate value="${report.updated_at}" pattern="yyyy-MM-dd HH:mm:ss" /></td>
                        </tr>
                    </tbody>
                </table>

                <c:if test="${sessionScope.login_employee.id == report.employee.id}">
                    <p id="edit"><a href="<c:url value="/reports/edit?id=${report.id}" />">この日報を編集する</a></p>
                </c:if>

                <c:choose>
                    <c:when test="${evaluation == null}">
                        <form method="POST" action="<c:url value='/evaluations/create' />" id="eval">
                            <input type="hidden" name="gb" value="1" />
                            <input type="hidden" name="id" value="${report.id}" />
                            <input type="hidden" name="_token" value="${_token}" />
                            <button type="submit">高評価する</button>
                        </form>
                        <form method="POST" action="<c:url value='/evaluations/create' />" id="eval">
                            <input type="hidden" name="gb" value="0" />
                            <input type="hidden" name="id" value="${report.id}" />
                            <input type="hidden" name="_token" value="${_token}" />
                            <button type="submit">低評価する</button>
                        </form>
                    </c:when>
                    <c:when test="${evaluation.good_report == 1}">
                        <form method="POST" action="<c:url value='/evaluations/delete' />" id="eval">
                            <input type="hidden" name="report_id" value="${report.id}" />
                            <input type="hidden" name="eval_id" value="${evaluation.id}" />
                            <input type="hidden" name="_token" value="${_token}" />
                            <button type="submit" id="evaluated">高評価済み</button>
                        </form>
                        <form method="POST" action="<c:url value='/evaluations/update' />" id="eval">
                            <input type="hidden" name="gb" value="0" />
                            <input type="hidden" name="id" value="${evaluation.id}" />
                            <input type="hidden" name="_token" value="${_token}" />
                            <button type="submit">低評価する</button>
                        </form>
                    </c:when>
                    <c:otherwise>
                        <form method="POST" action="<c:url value='/evaluations/update' />" id="eval">
                            <input type="hidden" name="gb" value="1" />
                            <input type="hidden" name="id" value="${evaluation.id}" />
                            <input type="hidden" name="_token" value="${_token}" />
                            <button type="submit">高評価する</button>
                        </form>
                        <form method="POST" action="<c:url value='/evaluations/delete' />" id="eval">
                            <input type="hidden" name="report_id" value="${report.id}" />
                            <input type="hidden" name="eval_id" value="${evaluation.id}" />
                            <input type="hidden" name="_token" value="${_token}" />
                            <button type="submit" id="evaluated">低評価済み</button>
                        </form>
                    </c:otherwise>
                </c:choose>

                <div id="eval" class="count">高評価 <c:out value="${good_count}" />件</div>
                <div id="eval" class="count">低評価 <c:out value="${bad_count}" />件</div>
            </c:when>
            <c:otherwise>
                <h2>お探しのデータは見つかりませんでした。</h2>
            </c:otherwise>
        </c:choose>

        <p><a href="<c:url value='/reports/index' />">一覧に戻る</a></p>
    </c:param>
</c:import>