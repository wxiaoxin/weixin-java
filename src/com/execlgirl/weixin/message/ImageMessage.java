package com.execlgirl.weixin.message;

public class ImageMessage extends BaseMessage{
	private Image Image;
	
	public Image getImage(){
		return this.Image;
	}
	
	public void setImage(Image image){
		this.Image = image;
	}
}
