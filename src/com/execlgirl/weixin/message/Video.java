package com.execlgirl.weixin.message;

public class Video{
	private String MediaId;
	private String ThumbMediaId;
	
	public String getMediaId(){
		return this.MediaId;
	}
	
	public void setMediaId(String mediaId){
		this.MediaId = mediaId;
	}
	
	public String getThumbMediaId(){
		return this.ThumbMediaId;
	}
	
	public void setThumbMediaId(String thumbMediaId){
		this.ThumbMediaId = thumbMediaId; 
	}
}
