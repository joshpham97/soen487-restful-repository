<%--
  Created by IntelliJ IDEA.
  User: timmy
  Date: 23/01/2021
  Time: 2:43 p.m.
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
  <title>Test</title>
</head>
<body>
Hello World.
<div class="h4">Create New Artist</div>
<form action="ArtistServlet" method="post">
  <input type="text" name="action" value="post" hidden/>
  <div class="form-group">
    <input id="postNickname" name="nickname" class="form-control" rows="1" placeholder="nickname" required />
    <input id="postFirstName" name="firstname" class="form-control" rows="1" placeholder="firstname" required />
    <input id="postLastName" name="lastname" class="form-control" rows="1" placeholder="lastname" required />
    <input id="postBio" name="bio" class="form-control" rows="2" placeholder="bio" required />
  </div>
  <div class="col text-center">
    <button type="submit" class="btn btn-primary mt-2 mi">Create Artist</button>
  </div>
</form>
<div>
  Click here to see all the artists <a href="ArtistServlet"> View Artists</a>
</div>
</body>
</html>
