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

	<h1 class="on-air">
		This a cameras overview page
	</h1>

	
    <c:set var="currentColumn" value="${0}"/>
    <c:set var="currentlyDisplayed" value="${0}"/>
    
    <div style="margin-left: 30px;">
    <div class="stream-list">
    <ul>
    <c:forEach var="singleCameraURL" items="${camerasURL}">
    	<li>
		    <div class="video-wrap">
		    	<input type="checkbox" id="cameraIdToRecord" value="${singleCameraURL.key}"> &nbsp;${singleCameraURL.value}
		        <div>
		            <embed id = "vlcRTSP${singleCameraURL.key}" type="application/x-vlc-plugin" pluginspage="http://www.videolan.org" volume="0" autoplay="yes" controls="yes" loop="no" width="272" height="172" target="${singleCameraURL.value}" />
		        </div>
		    </div>
		</li>
		<c:set var="currentColumn" value="${currentColumn+1}"/>
		<c:set var="currentlyDisplayed" value="${currentlyDisplayed+1}"/>
		<c:if test="${currentColumn == columns || currentlyDisplayed >= fn:length(camerasURL)}">
			</ul>
			</div>
			<c:if test="${currentlyDisplayed < fn:length(camerasURL)}">
				<div class="stream-list">
				<ul>
			</c:if>
			<c:set var="currentColumn" value="${0}"/>
		</c:if>
	</c:forEach>
	</div>
		
	<input type="hidden" id="cameraIdArray">
	
	<br/>		
	
	<input class="btn blue" id="recordingStartButton" type="button" value="Start recording" onClick="verifyCheckboxes();"/>
	<input class="btn blue" id="recordingStopButton" type="button" value="Stop recording" onClick="showProgress(); stopRecording();" style="display:none;"/>
	<img src="/images/loading.gif" style="width:20px;display:none;" id="loading_gif"/>
	<img src="/images/recording.gif" style="width:20px;display:none;" id="recording_gif" style="display:none;"/>
	</br></br>
	
	
	<div id="comment_area" style="display:none;" class="bordered-block">
		<div class="new-comment comment">
			<form id="comment_form">
				Enter your comment:</br>
				<TEXTAREA WRAP="virtual" COLS="40" ROWS="3" id="commentText"></TEXTAREA></br>
				<input id="commentButton" class="btn blue"type="button" value="Send comment" onClick="commentFormSubmit();"/>
				<input id="recordId" type="hidden" value=""/>
			</form>
		</div>
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
			    	};
			    	checkRecordingStatus();
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
			    	checkRecordingStatus();
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
</section>

</body>
