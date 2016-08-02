<%@ include file="/common/taglibs.jsp"%>

<head>
    <title><fmt:message key="home.title"/></title>
    <meta http-equiv="Cache-Control" content="no-cache"/>
    <meta http-equiv="Pragma" content="no-cache"/>
    <meta http-equiv="Expires" content="0"/>
    <meta name="menu" content="Home"/>
</head>
<body class="home">

This a cameras overview page
</br></br></br>

Streaming from  ${defaultCameraAddress}<br/>
<embed type="application/x-vlc-plugin" pluginspage="http://www.videolan.org" volume="30" autoplay="no" controls="yes" loop="no" width="640" height="480" target="${defaultCameraAddress}" />

<br/>
<input id="recordingStartButton" type="button" value="Start recording" onClick="showProgress();startRecording();"/>
<input id="recordingStopButton" type="button" value="Stop recording" onClick="showProgress(); stopRecording();" style="display:none;"/>
<img src="/images/loading.gif" style="width:20px;display:none;" id="loading_gif"/>
<img src="/images/recording.gif" style="width:20px;display:none;" id="recording_gif" style="display:none;"/>
</br></br>

<div id="comment_area" style="display:none;"> 
	<form id="comment_form">
		Enter your comment:</br>
		<TEXTAREA WRAP="virtual" COLS="40" ROWS="3" id="commentText"></TEXTAREA></br>
		<input id="commentButton" type="button" value="Send comment" onClick="commentFormSubmit();"/>
	</form>
</div>

<script>

	function commentFormSubmit(){
		var msg   = $('#comment_form').serialize();
        $.ajax({
        	type: 'POST',
        	url: '/videoRecording/saveComment',
        	data: "commentText=" + $("#commentText").val(),
        	success: function(data) {
        		$("#commentText").val("");
        	},
        	error: function(jqXHR, textStatus, errorThrown) {
        		alert(jqXHR.status + " - " + errorThrown + " - " + jqXHR.responseText);
        	}
        });
	};

	function showProgress(){
		$("#loading_gif").show();
		$("#recordingStopButton").attr("disabled", true);
		$("#recordingStartButton").attr("disabled", true);
	};
	
	function hideProgress(){
		$("#loading_gif").hide();
		$("#recordingStopButton").attr("disabled", false);
		$("#recordingStartButton").attr("disabled", false);
	};

	function displayRecordingControls(){
		hideProgress();
		$("#comment_area").show();
	};
	
	function hideRecordingControls(){
		hideProgress();
		$("#comment_area").hide();
	};
	
	var startRecording = function() {
		$("#recordingStartButton").hide();
		$("#recordingStopButton").show();
		$("#comment_textarea").show();
		$.ajax({
		    url: "/videoRecording/startRecording",
			type: "POST",
		    success: function(response) {
		    	displayRecordingControls();
		    	if(!response.isSuccessful){
		    		alert(response.errorMessage);
		    	} else{
		    		$("#recording_gif").show();
		    	}
		    },
		    error: function(jqXHR, textStatus, errorThrown) {
		    	hideRecordingControls();
		    	alert(jqXHR.status + " - " + errorThrown + " - " + jqXHR.responseText);
		    }
		});
		
	};
	
	var stopRecording = function() {
		$("#recordingStopButton").hide();
		$("#recordingStartButton").show();
		$("#comment_textarea").hide();
		$.ajax({
		    url: "/videoRecording/stopRecording",
			type: "POST",
		    success: function(response) {
		    	hideRecordingControls();
		    	if(!response.isSuccessful){
		    		alert(response.errorMessage);
		    	}
		    },
		    error: function(jqXHR, textStatus, errorThrown) {
		    	hideRecordingControls();
		    	alert(jqXHR.status + " - " + errorThrown + " - " + jqXHR.responseText);
		    }
		});
		$("#recording_gif").hide();
	};

</script>

</body>
