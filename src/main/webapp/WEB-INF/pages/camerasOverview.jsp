<%@ include file="/common/taglibs.jsp"%>

<head>
    <title><fmt:message key="home.title"/></title>
    <meta name="menu" content="Home"/>
</head>
<body class="home">

This a cameras overview page
</br></br></br>
<!-- 
<embed type="application/x-vlc-plugin" pluginspage="http://www.videolan.org" autoplay="no" loop="no" width="640" height="480" target="${defaultCameraAddress}" />
<object classid="clsid:9BE31822-FDAD-461B-AD51-BE1D1C159921" codebase="http://download.videolan.org/pub/videolan/vlc/last/win32/axvlc.cab" style="display:none;"></object>
 -->


<br/>

<input type="button" value="Start recording" onClick="startRecordingPost();"/>

<script>

	var startRecordingPost = function() {
		alert("lalal");
		
		
		
		$.ajax({
			   url: "/camerasOverview/startRecording",
			   data: {
			      format: "json"
			   },
			   error: function(jqXHR, textStatus, errorThrown) {
			    	alert(jqXHR.status + " - " + errorThrown + " - " + jqXHR.responseText);
			   },
			   dataType: "jsonp",
			   success: function(data) {
			      alert(data);
			   },
			   type: "GET"
			});
	}

</script>

</body>
