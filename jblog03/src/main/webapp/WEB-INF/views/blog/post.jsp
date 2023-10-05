<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<!doctype html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>JBlog</title>
<Link rel="stylesheet" href="${pageContext.request.contextPath}/assets/css/jblog.css">
</head>
<body>
	<div id="container">
		<div id="header">
			<c:import url="/WEB-INF/views/includes/header.jsp"/>
		</div>
		<div id="wrapper">
			<div id="content">
				<div class="blog-content">
					<c:choose>
						<c:when test="${empty post }">
							<p> 해당 카테고리에 게시글이 존재하지 않습니다. </p>
						</c:when>
						<c:otherwise>
							<h4>${post.title}</h4>
							<p>${post.contents}</p>
						</c:otherwise>
					</c:choose>
				</div>
				<ul class="blog-list">
					<c:forEach items="${categoryPostList }" var="postVo" varStatus="status">
						<li><a href="${pageContext.request.contextPath}/${blogVo.blogId}/${postVo.categoryNo}/${postVo.no}">${postVo.title } </a> <span>${postVo.date }</span>	</li>
					</c:forEach>
				</ul>
			</div>
		</div>

		<div id="extra">
			<c:import url="/WEB-INF/views/includes/extra.jsp"/>
		</div>

		<div id="navigation">
			<c:import url="/WEB-INF/views/includes/navigation.jsp"/>
		</div>
		
		<div id="footer">
			<c:import url="/WEB-INF/views/includes/footer.jsp"/>
		</div>
	</div>
</body>
</html>