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
    
    <c:set var="scripts" scope="request">
    	<%@ include file="/scripts/login.js"%>
    </c:set>
</head>
<body class="login-page">
	<div class="roll">
	    <img class="logo" src="images/vr/logo_login.png"/>
	    <form class="bordered-block" method="post" id="loginForm" action="<c:url value='/j_security_check'/>" onsubmit="saveUsername(this);return validateForm(this)" class="form-signin" autocomplete="off">
	        <h2><fmt:message key="vr.enterSystem"/></h2>
	        <div class="row">
	            <input type="text" placeholder="<fmt:message key='vr.login'/>" name="j_username" id="j_username"/>
	        </div>
	        <div class="row">
	            <input type="password" name="j_password" id="j_password" placeholder="<fmt:message key='vr.password'/>"/>
	            <a href="" class="forgot"><fmt:message key='vr.forgotPassword'/></a>
	        </div>
	        <div class="row">
	            <input type="submit" class="btn light-blue" value="<fmt:message key='vr.enter'/>"/>
	            <input type="checkbox"/>
	            <a href="" class="remember"><fmt:message key='vr.rememberMe'/></a>
	        </div>
	    </form>
	</div>
	<footer>
	    <p class="copy">
	        &copy; 2016, videorobot.
	    </p>
	</footer>
</body>
</html>
