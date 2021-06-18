<%-- 
    Document   : campaign_xls
    Created on : Oct 2, 2019, 4:05:23 PM
    Author     : mdshahadat.sarker
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix = "fmt" uri = "http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>

<%
    response.reset();
    response.setHeader("Content-type", "application/vnd.ms-excel");
    String fileName = "UserAuditLog.xls";
    response.setHeader("Content-disposition", "inline; filename=" + fileName);
%>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
        <meta charset="utf-8" />
        <meta name="description" content="User login page" />
        <meta name="viewport" content="width=device-width, initial-scale=1.0, maximum-scale=1.0" />
        <% response.addHeader("x-frame-options","DENY"); %>
    </head>
    <body>
        <table class="table table-bordered" border="1">
            <thead>
                <tr>
                    <th class="text-center">SL</th>
                    <th class="text-center">Audit type</th>
                    <th class="text-center">Session ID</th>
                    <th class="text-left">Exc method</th>
                    <th class="text-left">Audit table</th>
                    <th class="text-center">Audit date</th>
                    <th class="text-left">Audit by</th>
                    <th class="text-center">Details</th>
                </tr>
            </thead>
            <tbody>
                <c:forEach items="${data}" var="obj" varStatus="counter">
                    <tr>
                        <td class="center">${fn:escapeXml(counter.count)}</td>
                        <td><c:out value="${obj.audit_type}"/></td>
                        <td><c:out value="${obj.session_id}"/></td>
                        <td><c:out value="${obj.exc_method}"/></td>
                        <td><c:out value="${obj.audit_table}"/></td>
                        <td><c:out value="${obj.audit_date}"/>&nbsp;</td>
                        <td><c:out value="${obj.user_code}"/>-<c:out value="${obj.first_name}"/>&nbsp;<c:out value="${obj.last_name}"/></td>
                        <td><c:out value="${obj.change_detail}"/></td>
                    </tr>
                </c:forEach>
            </tbody>
        </table>
    </body>
</html>
