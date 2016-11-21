<%@ taglib uri='http://java.sun.com/jsp/jstl/core' prefix='c'%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ include file="header.jsp" %>
    <div class="container theme-showcase" role="main">
	    <div class="jumbotron">
	        <h3>Search with an image :</h3>
	        <p>
	        <form action="/" method="post" enctype="multipart/form-data">
	        Image : <input type="file" name="file" accept="image/*" required/>
	        Histogram type : <select onchange="handleChange(this.value);" name="type" required>
  				<option value="C" selected>Color level</option>
  				<option value="G">Gray Level</option>
			</select></br>
			Bin number (precision) :<select id="binNumber" name="bin" required>
			</select></br>
	        <input type="submit" value="Search" />
	        </form>
	        </p>
	    </div>
	    <c:if test="${resultSearch != 'null'}">
	    <div class="jumbotron">
	        <h3>Results :</h3>
	        <p>
			<c:forEach items="${resultSearch}" var="result">
				<div>
    			Score : ${result.score}<br>
    			<img src="/images/${result.histogram.imgName}" />
    			</div>
			</c:forEach>
	        </p>
	    </div>
	    </c:if>
    </div>
<script type="text/javascript">
	var colorNbAvailable = ${tabColorAvailable};
	var grayNbAvailable = ${tabGrayAvailable};
	handleChange('C');
	function handleChange(value){
		var tab;
		if(value=='C'){
			tab = colorNbAvailable;
		}else{
			tab = grayNbAvailable;
		}
		var s = '';
		tab.forEach(function(element) {
			s+= '<option value="'+element+'">'+element+'</option>';
		});
		$("#binNumber").empty().append(s);
	}
</script>
<%@ include file="footer.jsp" %>