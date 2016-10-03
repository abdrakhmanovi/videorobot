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

	<c:forEach var="record" items="${records}">
		${record.id} - ${record.creationDate}
		</br>
		<c:forEach var="recordCamera" items="${record.recordCameras}">
		&nbsp;&nbsp;<a href="/videoPlayback?recordCamera=${recordCamera.recordCameraId}">${recordCamera.recordCameraId} - ${recordCamera.creationDate}</a></br>
		</c:forEach>
		
	</c:forEach>

	
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
