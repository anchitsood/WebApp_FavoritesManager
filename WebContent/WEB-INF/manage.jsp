<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<jsp:include page="template-top.jsp" />

<p style="font-size:medium">
	Add a new favorite:
</p>

<jsp:include page="error-list.jsp" />

<p>
	<form method="post" action="add.do" enctype="multipart/form-data">
		<table>
			<tr>
				<td>URI (use full URI): </td>
				<td colspan="2"><input type="text" name="uri" value="${uri}"/></td>
			</tr>
			<tr>
				<td>Comment: </td>
				<td><input type="text" name="comment" value="${comment}"/></td>
			</tr>
			<tr>
				<td colspan="3" align="center">
					<input type="submit" name="button" value="Add URI"/>
				</td>
			</tr>
		</table>
	</form>
</p>
<hr/>
<p>
	<table>
		<c:forEach var="favorites" items="${favorites}" varStatus="count">

        <tr>
            <td valign="top">
                <form method="POST" action="remove.do">
                    <input type="hidden" name="id" value="${favorites.favoriteId}"/>
                    <input type="submit" value="X"/>
                </form>
            </td>
            <td valign="top">
                <form method="POST" action="move-up.do">
                    <input type="hidden" name="id" value="${favorites.favoriteId}" />
                    <input type="submit" value="&uarr;">
                </form>
            </td>
            <td valign="top">
                <form method="POST" action="move-down.do">
                    <input type="hidden" name="id" value="${favorites.favoriteId}"/>
                    <input type="submit" value="&darr;"/>
                </form>
            </td>
            <td valign="top">
            	<form method="POST" action="click.do">
                    <a href="click.do?id=${favorites.favoriteId}" onclick="window.open('${favorites.uri}')">${favorites.uri}</a>
                </form>
            </td>
        </tr>
        
        <tr>
        	<td></td>
        	<td></td>
        	<td></td>
        	<td valign="top">${favorites.comment}</td>
        </tr>
        
        <tr>
        	<td></td>
        	<td></td>
        	<td></td>
        	<td valign="top">${favorites.clicks} hits so far</td>
        </tr>        	

		</c:forEach>
	</table>
</p>

<jsp:include page="template-bottom.jsp" />
