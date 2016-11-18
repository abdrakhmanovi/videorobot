package com.globaltec.prototype;

import java.awt.BorderLayout;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.Panel;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.util.Date;

import javax.swing.JButton;
import javax.swing.JTextField;

import com.globaltec.util.srt.SRT;
import com.globaltec.util.srt.SRTInfo;
import com.globaltec.util.srt.SRTWriter;
import com.xuggle.mediatool.IMediaListener;
import com.xuggle.mediatool.IMediaReader;
import com.xuggle.mediatool.IMediaWriter;
import com.xuggle.mediatool.MediaListenerAdapter;
import com.xuggle.mediatool.ToolFactory;
import com.xuggle.mediatool.event.IVideoPictureEvent;
import com.xuggle.xuggler.IError;
import com.xuggle.xuggler.demos.VideoImage;

public class VideoAndComments {

    private static VideoImage mScreen = null;
    private static IMediaReader reader = null;
    private static IMediaWriter writer = null;
    private static JButton recordButton = new JButton("Record");
    private static JButton archiveButton = new JButton("Play archived video =>");
    private static JButton addComment = new JButton("Add comment");
    private static boolean continueProcessing = true;
    private static Panel controlPanel = new Panel();;

    private static JTextField userText = new JTextField(6);
    
    private static Long recordingStart = null;
    
    static SRTInfo info = new SRTInfo();
    
    static int commentId = 0;

    // private static IMediaWriter mediaWriter;
    private static IMediaListener mediaListener = new MediaListenerAdapter() {
	@Override
	public void onVideoPicture(IVideoPictureEvent event) {
	    try {
		BufferedImage bi = event.getImage();
		if (bi != null)
		    updateJavaWindow(bi);
	    } catch (Exception ex) {
		ex.printStackTrace();
	    }
	}
    };

    public static void main(String[] args) {
	launchRecordMode();
	//showDefaultWindow();
    }

    public static void launchRecordMode() {
	reader = ToolFactory.makeReader("rtsp://admin:admin@192.168.0.39:554/cam/realmonitor?channel=1&subtype=1");
	//reader = ToolFactory.makeReader("http://multimedia.stargazette.com/webcasts/weather/webcast.mov");
	//reader = ToolFactory.makeReader("rtsp://admin:admin@87.117.163.209:554/cam/realmonitor?channel=1&subtype=1");
	reader.setBufferedImageTypeToGenerate(BufferedImage.TYPE_3BYTE_BGR);
	//reader.setAddDynamicStreams(false);
	reader.setQueryMetaData(false);
	reader.addListener(mediaListener);
	writer = ToolFactory.makeWriter("output.mp4", reader);

	int i = 0;
	createJavaWindow();
	addButtons();
	
	while (continueProcessing) {
	    IError err = null;
	    if (reader != null){
		
		try {
		    err = reader.readPacket();
		} catch (Exception e) {
		    // TODO Auto-generated catch block
		    e.printStackTrace();
		}
	    }
	    if (err != null) {
		System.out.println("Error: " + err);
		break;
	    }
	    System.out.println(i);
	    i++;
	}
	closeStreams();
    }

    private static void startRecording() {
	Thread t1 = new Thread(new Runnable() {
	    public void run() {
		reader.addListener(writer);
		recordingStart = System.currentTimeMillis();
		recordButton.setText("Stop");
		recordButton.addActionListener(new ActionListener() {
		    public void actionPerformed(ActionEvent e) {
			continueProcessing = false;
		    }
		});
		userText.setVisible(true);
		addComment.setVisible(true);
	    }
	});
	t1.start();
    }

    private static void updateJavaWindow(BufferedImage javaImage) {
	mScreen.setImage(javaImage);
    }

    private static void createJavaWindow() {
	mScreen = new VideoImage();
    }

    /**
     * Opens a Swing window on screen.
     */
    private static void addButtons() {
	recordButton.addActionListener(new ActionListener() {
	    public void actionPerformed(ActionEvent e) {
		archiveButton.setVisible(false);
		startRecording();
	    }
	});
	
	archiveButton.addActionListener(new ActionListener() {
	    public void actionPerformed(ActionEvent e) {
		
	    }
	});

	controlPanel.setBounds(new Rectangle(10, 10, 10, 10));
	controlPanel.setMaximumSize(new Dimension(10, 10));
	controlPanel.add(recordButton);
	//controlPanel.add(archiveButton);

	userText.setVisible(false);
	controlPanel.add(userText);

	addComment.addActionListener(new ActionListener() {
	    public void actionPerformed(ActionEvent e) {
		saveComment();
	    }
	});
	addComment.setVisible(false);
	controlPanel.add(addComment);

	Container cont = mScreen.getContentPane();
	cont.add(controlPanel, BorderLayout.NORTH);
    }

    private static void saveComment() {
	if(userText.getText().length()>0){
	    commentId++;
	    Long timePastFromStart = System.currentTimeMillis() - recordingStart;
	    Date subtitleDate = new Date (timePastFromStart);
	    Date endSubtitle = new Date (timePastFromStart + 3000);
	    SRT comment = new SRT(commentId, subtitleDate, endSubtitle, userText.getText());
            info.add(comment);
            userText.setText("");
	} 
    }

    private static void closeStreams() {
	if (writer!=null && writer.isOpen()) {
	    writer.close();
	}
	if (reader!=null && reader.isOpen()) {
	    reader.close();
	}
	
	File f = new File("output.srt");
        SRTWriter.write(f, info);
	
	System.exit(0);
    }
}
