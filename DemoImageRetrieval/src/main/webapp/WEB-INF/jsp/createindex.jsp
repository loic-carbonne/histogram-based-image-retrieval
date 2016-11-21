<%@ taglib uri='http://java.sun.com/jsp/jstl/core' prefix='c'%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ include file="header.jsp" %>
    <div class="container theme-showcase" role="main">
	    <div class="jumbotron">
	        <h3>Create an index :</h3>
	        <p>
	<c:choose>
    <c:when test="${stateRunner=='RUNNABLE'}">
    	<img src="loading.gif" />
        <br />The program is currently indexing the images, please come back later (average indexing time : 6 min)
    </c:when>    
    <c:otherwise>
	        <form action="/createIndex" method="post" enctype="multipart/form-data">
	        Histogram type : <select name="type">
  				<option value="C" selected>Color level</option>
  				<option value="G">Gray Level</option>
			</select></br>
			Bin number (precision) :<input type="number" id="binNumber" name="bin" step="1" min="2" max="256"></br>
	        <input type="submit" value="Create index" />
	        </form>
	</c:otherwise>
	</c:choose>
	        </p>
	        <h3>Indexes available :</h3>
	        <h4>Color :</h4>
	        <c:forEach items="${tabColorAvailable}" var="nb">
    			${nb}*${nb}*${nb} - <a href="/delete?type=C&bin=${nb}">Delete</a><br>
			</c:forEach>
	        <h4>Gray :</h4>
	        <c:forEach items="${tabGrayAvailable}" var="nb">
    			${nb} bins - <a href="/delete?type=G&bin=${nb}">Delete</a><br>
			</c:forEach>
	    </div>
    </div>

<%@ include file="footer.jsp" %>