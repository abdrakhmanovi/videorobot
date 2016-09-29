<!DOCTYPE html>
<%@ include file="/common/taglibs.jsp"%>
<html lang="en">
<head>
    <meta http-equiv="Cache-Control" content="no-store"/>
    <meta http-equiv="Pragma" content="no-cache"/>
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8"/>
    <meta http-equiv="X-UA-Compatible" content="IE=edge"/>
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <link rel="icon" href="<c:url value="/images/favicon.ico"/>"/>
    <title><decorator:title/> | <fmt:message key="webapp.name"/></title>
    <t:assets type="css"/>
    <decorator:head/>
</head>
<body<decorator:getProperty property="body.id" writeEntireProperty="true"/><decorator:getProperty property="body.class" writeEntireProperty="true"/>>
   <c:set var="currentMenu" scope="request"><decorator:getProperty property="meta.menu"/></c:set>

   
   	<nav>
    	<div class="roll">
	       <img class="logo" src="/images/vr/logo_white.png"/>
	       <%@ include file="/common/videorobot_menu.jsp" %>
	       <div class="cur-user">
	       	   <span><c:out value="${pageContext.request.remoteUser}" escapeXml="true"/></span>
	           <img src="/images/vr/kursant_ico.png" class="avatar"/>
	       </div>
       </div>
    </nav>
    

    <div class="container" id="content">
        <%@ include file="/common/messages.jsp" %>
        <div class="row">
            <decorator:body/>

            <c:if test="${currentMenu == 'AdminMenu'}">
                <div class="col-sm-2">
                <menu:useMenuDisplayer name="Velocity" config="navlistMenu.vm" permissions="rolesAdapter">
                    <menu:displayMenu name="AdminMenu"/>
                </menu:useMenuDisplayer>
                </div>
            </c:if>
        </div>
    </div>

    <div id="footer" class="container navbar-fixed-bottom">
        <span class="col-sm-6 text-left"><fmt:message key="webapp.version"/>
            <c:if test="${pageContext.request.remoteUser != null}">
            | <fmt:message key="user.status"/> ${pageContext.request.remoteUser}
            </c:if>
        </span>
        <span class="col-sm-6 text-right">
            &copy; <fmt:message key="copyright.year"/>&nbsp;&nbsp;<fmt:message key="company.name"/>
        </span>
    </div>
<t:assets type="js"/>    
<%= (request.getAttribute("scripts") != null) ?  request.getAttribute("scripts") : "" %>
</body>
</html>
