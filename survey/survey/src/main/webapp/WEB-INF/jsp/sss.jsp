<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%-- <jsp:useBean id="user" class="com.ylsh.survey.pojo.TbUser" scope="request" /> --%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>

<title>test</title>
</head>
<body>
<%
	Object s = request.getQueryString();
	out.println(s);
%>

<form action="sss" method="get">
	<input name="p1" type="text" />
	<input name="p2" type="text" />
	<input type="submit" />
</form>
<%-- 
<form:form modelAttribute="user" action="/sss">
    <table>
        <tr>
            <td>First Name:</td>
            <td><form:input path="id" /></td>
        </tr>
        <tr>
            <td>Last Name:</td>
            <td><form:input path="nick" /></td>
        </tr>
        <tr>
            <td>password:</td>
            <td><form:password path="password" showPassword="false"/></td>
        </tr>
        <tr>
        	<td>
	        	<form:select path="phone">
					<form:option value="" label="默认"/>
					<form:options items="${users}" itemLabel="nick" itemValue="id" />
				</form:select>
			</td>
        </tr>
        <tr>
        <form:checkboxes path="phone" items="${users}" itemLabel="nick" itemValue="id" />
        </tr>
        <tr>
        	<td>
        	<form:radiobuttons path="phone" items="${users}" itemLabel="nick" itemValue="id" />
        	</td>
        </tr>
        
        <tr>
            <td>
          	  <form:textarea path="nick" />
            </td>
        </tr>
         <tr>
            <td>
          	  <form:errors path="*" />
            </td>
        </tr>
        <tr>
            <td colspan="3">
            	<input type="reset" value="reset" />
                <input type="submit" value="Save Changes" />
            </td>
        </tr>
    </table>
</form:form>
 --%>


<!-- 
<script>
function IPCallBack(data) {

	alert(JSON.stringify(data));

}


</script>

<script type="text/javascript" src="http://whois.pconline.com.cn/ipJson.jsp"></script>

 -->

</body>

</html>