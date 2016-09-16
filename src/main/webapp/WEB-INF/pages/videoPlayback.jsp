<%@ include file="/common/taglibs.jsp"%>

<head>
    <title><fmt:message key="home.title"/></title>
    <!--<link href="http://vjs.zencdn.net/5.10.7/video-js.css" rel="stylesheet">-->
    
    <title><fmt:message key="home.title"/></title>
    <meta http-equiv="Cache-Control" content="no-cache"/>
    <meta http-equiv="Pragma" content="no-cache"/>
    <meta http-equiv="Expires" content="0"/>
    <meta name="menu" content="Playback"/>
</head>
<body class="home">

	This a playback
	</br>
	
	<c:if test="${not empty recordsList}">
		<ul>
			<c:forEach var="record" items="${recordsList}">
				<li>
					<c:choose>
					    <c:when test="${record.videoFound}">
					    	<a href="/videoPlayback?videoId=${record.id}">${record.name}</a> ${record.creationDate}
					    </c:when>    
					    <c:otherwise>
					    	${record.name} ${record.creationDate} (video not found in file storage)
					    </c:otherwise>
				    </c:choose>
				</li>
			</c:forEach>
		</ul>
	</c:if>
	
	<c:if test="${not empty record}">
		<video id="videoContainer" width="320" height="240" controls> 
			<source src="/videoPlayback/playVideo?recordId=${record.id}" type="video/mp4">
		</video>

		<c:forEach var="subtitle" items="${subtitles}">
			<li>
				<a href="javascript:moveToPoint(${subtitle.startTime.time}/1000);">${subtitle.fullText} : ${subtitle.startTime.time/1000}</a> 
			</li>
		</c:forEach>
		
		<script>
			
			function moveToPoint(point){
				var mediaElement = document.getElementById("videoContainer");
				mediaElement.pause(); 
				mediaElement.currentTime = point;
				mediaElement.play();
			}
		
		</script>

		
	</c:if>
	
</body>
