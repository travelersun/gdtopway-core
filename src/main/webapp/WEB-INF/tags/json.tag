<%@tag pageEncoding="UTF-8"%><%@ attribute name="value" type="java.lang.Object" required="true"%><%out.print(com.gdtopway.core.util.JsonUtils.writeValueAsString(value));%>