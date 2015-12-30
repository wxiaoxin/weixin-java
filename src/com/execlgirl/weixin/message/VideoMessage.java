package com.execlgirl.weixin.message;

public class VideoMessage extends BaseMessage{
    private Video Video;
	
	public Video getVideo(){
		return this.Video;
	}
	
	public void setVideo(Video video){
		this.Video = video;
	}
	
}

