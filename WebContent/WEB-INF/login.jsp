<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<jsp:include page="template-top.jsp" />

<p style="font-size:medium">
	Please login below or click <a href="register.do">here</a> to register as a new user.
</p>

<jsp:include page="error-list.jsp" />

<p>	
	<form method="post" action="login.do">
		<table>
			<tr>
				<td> Email: </td>
				<td><input type="text" name="email" value="${form.email}"/></td>
			</tr>
			<tr>
				<td> Password: </td>
				<td><input type="password" name="password" value=""/></td>
			</tr>
			<tr>
				<td colspan="2" align="center">
					<input type="submit" name="button" value="Login"/>
				</td>
			</tr>
		</table>
	</form>
</p>

<br><br><br><br><br><br><br><br><br><br>

<p>
	<table>
		<tr>
			<td>Hints for generic initialization</td>
			<td>(when there are less than 3 users to begin with)</td>
			<td>(ignore if you already have more than 3 users set up)</td>
		</tr>
		<tr>
			<td>Name: Tony Stark</td>
			<td>Email: ironman@avengers.com</td>
			<td>Password: pass</td>
		</tr>
		<tr>
			<td>Name: Peter Parker</td>
			<td>Email: spiderman@avengers.com</td>
			<td>Password: pass</td>
		</tr>
		<tr>
			<td>Name: Steve Rogers</td>
			<td>Email: captainamerica@avengers.com</td>
			<td>Password: pass</td>
		</tr>
	</table>
</p>

<jsp:include page="template-bottom.jsp" />
