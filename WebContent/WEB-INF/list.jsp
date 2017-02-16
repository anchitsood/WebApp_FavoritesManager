<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>

<jsp:include page="template-top.jsp" />

<p>
	<table>
	<c:forEach var="favorites" items="${favorites}">
		<tr>
            <td valign="top">
            	<form method="POST" action="click.do">
                    <a href="click.do?id=${favorites.favoriteId}" onclick="window.open('${favorites.uri}')">${favorites.uri}</a>                   
                </form>
            </td>
        </tr>
        
        <tr>
        	<td valign="top">${favorites.comment}</td>
        </tr>
        
        <tr>
        	<td valign="top">${favorites.clicks} hits so far</td>
        </tr>
	</c:forEach>
	</table>
</p>

<jsp:include page="template-bottom.jsp" />
