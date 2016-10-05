<%@ include file="/common/taglibs.jsp"%>

<head>
    <title><fmt:message key="home.title"/></title>
    <meta http-equiv="Cache-Control" content="no-cache"/>
    <meta http-equiv="Pragma" content="no-cache"/>
    <meta http-equiv="Expires" content="0"/>
    <meta name="menu" content="Recording"/>
</head>
<body class="home">

<section>
<div class="roll">
<div class="sub-section">


	This a cameras overview page
	</br></br></br>
	
	<c:forEach var="singleCameraURL" items="${camerasURL}">
		<input type="checkbox" id="cameraIdToRecord" value="${singleCameraURL.key}">
		Streaming from  ${singleCameraURL.value}<br/>
		<embed id = "vlcRTSP${singleCameraURL.key}" type="application/x-vlc-plugin" pluginspage="http://www.videolan.org" volume="30" autoplay="no" controls="yes" loop="no" width="640" height="480" target="${singleCameraURL.value}" />
		</br>
	</c:forEach>
	
	<input type="hidden" id="cameraIdArray">
	
	<input type="button" onClick="verifyCheckboxes();">
	<input type="button" onClick="getVLCTime();">
		
		
	<br/>
	<input id="recordingStartButton" type="button" value="Start recording" onClick="verifyCheckboxes();"/>
	<input id="recordingStopButton" type="button" value="Stop recording" onClick="showProgress(); stopRecording();" style="display:none;"/>
	<img src="/images/loading.gif" style="width:20px;display:none;" id="loading_gif"/>
	<img src="/images/recording.gif" style="width:20px;display:none;" id="recording_gif" style="display:none;"/>
	</br></br>
	
	<div id="comment_area" style="display:none;"> 
		<form id="comment_form">
			Enter your comment:</br>
			<TEXTAREA WRAP="virtual" COLS="40" ROWS="3" id="commentText"></TEXTAREA></br>
			<input id="commentButton" type="button" value="Send comment" onClick="commentFormSubmit();"/>
			<input id="recordId" type="hidden" value=""/>
		</form>
	</div>
	
	<script>
	
		var cameraListVariable = "";
	
		function getVLCTime(){
			var vlc = document.getElementById("vlcRTSP");
			alert(vlcRTSP.input.time/1000/60);
		};
		
		function verifyCheckboxes(){
			cameraListVariable = "";
			$("#cameraIdToRecord:checked").each(function(){
				cameraListVariable = cameraListVariable + $(this).val() + "_";
				
			});
			if(cameraListVariable != ""){
				$("#cameraIdArray").val(cameraListVariable);
				showProgress();
				startRecording();
			} else {
				alert("select camera to record")
			}
		};
	
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
			$("#recordingStopButton").show();
			$("#recordingStartButton").hide();
			$("#comment_area").show();
			hideProgress();
		};
		
		function hideRecordingControls(){
			$("#recordingStopButton").hide();
			$("#recordingStartButton").show();
			$("#comment_area").hide();
			hideProgress();
		};
		
		var startRecording = function() {
			showProgress();
			$.ajax({
			    url: "/videoRecording/startRecording",
				type: "POST",
				data: "cameraIdToRecord=" + $("#cameraIdArray").val(),
			    success: function(response) {
			    	displayRecordingControls();
			    	if(!response.isSuccessful){
			    		hideRecordingControls();
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
			showProgress();
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
</div>
</div>
</section>

</body>
