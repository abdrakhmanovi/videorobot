<%@ include file="/common/taglibs.jsp"%>

<head>
    <title><fmt:message key="home.title"/></title>
    <meta name="menu" content="Home"/>
</head>
<body class="home">

This a cameras overview page
</br></br></br>

Streaming from  ${defaultCameraAddress}<br/>
<embed type="application/x-vlc-plugin" pluginspage="http://www.videolan.org" autoplay="no" loop="no" width="640" height="480" target="${defaultCameraAddress}" />
<object classid="clsid:9BE31822-FDAD-461B-AD51-BE1D1C159921" codebase="http://download.videolan.org/pub/videolan/vlc/last/win32/axvlc.cab" style="display:none;"></object>


<br/>
<input id="recordingStartButton" type="button" value="Start recording" onClick="showProgress(); startRecording();"/>
<input id="recordingStopButton" type="button" value="Stop recording" onClick="showProgress(); stopRecording();" style="display:none;"/>
<img src="images/loading.gif" style="width:20px;display:none;" id="loading_gif"/>
<img src="images/recording.gif" style="width:20px;display:none;" id="recording_gif" style="display:none;"/>
</br></br>

<script>

	function showProgress(){
		
		$("#loading_gif").show();
		$("#recordingStopButton").attr("disabled", true);
		$("#recordingStartButton").attr("disabled", true);
	}
	
	function hideProgress(){
		$("#loading_gif").hide();
		$("#recordingStopButton").attr("disabled", false);
		$("#recordingStartButton").attr("disabled", false);
	}

	var startRecording = function() {
		$("#recordingStartButton").hide();
		$("#recordingStopButton").show();
		$("#comment_textarea").show();
		$.ajax({
		    url: "/camerasOverview/startRecording",
			type: "POST",
		    success: function(response) {
		    	hideProgress();
		    	if(!response.isSuccessful){
		    		alert(response.errorMessage);
		    	} else{
		    		$("#recording_gif").show();
		    	}
		    },
		    error: function(jqXHR, textStatus, errorThrown) {
		    	hideProgress();
		    	alert(jqXHR.status + " - " + errorThrown + " - " + jqXHR.responseText);
		    }
		});
		
	}
	
	var stopRecording = function() {
		$("#recordingStopButton").hide();
		$("#recordingStartButton").show();
		$("#comment_textarea").hide();
		$.ajax({
		    url: "/camerasOverview/stopRecording",
			type: "POST",
		    success: function(response) {
		    	hideProgress();
		    	if(!response.isSuccessful){
		    		alert(response.errorMessage);
		    	}
		    },
		    error: function(jqXHR, textStatus, errorThrown) {
		    	hideProgress();
		    	alert(jqXHR.status + " - " + errorThrown + " - " + jqXHR.responseText);
		    }
		});
		$("#recording_gif").hide();
	}

</script>

</body>
