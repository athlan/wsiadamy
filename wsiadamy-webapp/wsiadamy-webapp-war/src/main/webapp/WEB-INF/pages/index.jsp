<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="t" tagdir="/WEB-INF/tags" %>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<t:wrapper>
<h2>Hello World2!</h2>

<h4>Message : ${message}</h4>	

  <form:form method="post" action="myForm" commandName="myUser">
    <table>
      <tr>
        <td>Name: <font color="red"><form:errors path="name" /></font></td>
      </tr>
      <tr>
        <td><form:input path="name" /></td>
      </tr>
      <tr>
        <td>Age: <font color="red"><form:errors path="age" /></font></td>
      </tr>
      <tr>
        <td><form:input path="age" /></td>
      </tr>
      <tr>
        <td><input type="submit" value="Submit" /></td>
      </tr>
    </table>
  </form:form>

</t:wrapper>