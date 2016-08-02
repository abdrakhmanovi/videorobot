<%@ include file="/common/taglibs.jsp"%>

<head>
    <title><fmt:message key="home.title"/></title>
    <link href="http://vjs.zencdn.net/5.10.7/video-js.css" rel="stylesheet">
</head>
<body class="home">

	This a playback
	</br>
	<c:if test="${not empty recordsList}">
		<ul>
			<c:forEach var="record" items="${recordsList}">
				<li><a href="/videoPlayback?videoId=${record.name}">${record.name}</a></li>
			</c:forEach>
		</ul>
	</c:if>
	
	<c:if test="${not empty record}">

	
	
		<video id="videoContainer" class="video-js" controls preload="auto" width="640" height="264"
		  poster="MY_VIDEO_POSTER.jpg" data-setup="{}">
		    <source src="/videoPlayback/playVideo" type='video/mp4'>
		    <source src="/videoPlayback/playVideo" type='video/webm'>
		    <p class="vjs-no-js">
		      To view this video please enable JavaScript, and consider upgrading to a web browser that
		      <a href="http://videojs.com/html5-video-support/" target="_blank">supports HTML5 video</a>
		    </p>
		  </video>
	
	
		<video id="videoContainer" width="320" height="240" controls> 
			<source src="/videoPlayback/playVideo" type="video/mp4">
		</video>
		
		
		
		
		${videoId}
		
		<input type="button" onClick="moveToPoint();"/>
		
		<script>
			
			function moveToPoint(){
				var mediaElement = document.getElementById("videoContainer");
				alert(mediaElement.currentTime);
				mediaElement.pause(); 
				mediaElement.currentTime = 50;
				mediaElement.play();
			}
		
		</script>

		
	</c:if>
	
</body>
