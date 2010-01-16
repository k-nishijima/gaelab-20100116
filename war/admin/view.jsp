<%@page contentType="text/html; charset=UTF-8"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<!-- The HTML 4.01 Transitional DOCTYPE declaration-->
<!-- above set at the top of the file will set     -->
<!-- the browser's rendering engine into           -->
<!-- "Quirks Mode". Replacing this declaration     -->
<!-- with a "Standards Mode" doctype is supported, -->
<!-- but may lead to some differences in layout.   -->
<%
request.setCharacterEncoding("UTF-8");
String id = request.getParameter("id");
String title = request.getParameter("title");
String content = request.getParameter("content");
String tag = request.getParameter("tag");
if(id==null) {
    id = "";
}
if(title==null) {
    title = "";
}
if(content==null) {
    content = "";
}
if (tag==null) {
	tag = "";
}
%>

<html>
  <head>
    <meta http-equiv="content-type" content="text/html; charset=UTF-8">
    <title>Hello App Engine</title>
  </head>

  <body>
    <h1><a href="/">Hello App Engine!</a></h1>
    <hr />
    <form method="POST" action="/admin/post">
      <input type="hidden" name="id" value="<%=id %>" />
      Title:<br />
      <input id="title" type="text" name="title" value="<%=title %>"/><br />
      Content:<br />
      <textarea id="content" cols="50" name="content" rows="10"><%=content %></textarea><br />
      Tag:<br />
      <input id="tag" type="text" name="tag" value="<%=tag %>"/><br />
      <input id="submit" type="submit" value="post" />
    </form>
  </body>
</html>
