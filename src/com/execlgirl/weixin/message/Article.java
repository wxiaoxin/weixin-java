package com.execlgirl.weixin.message;

public class Article {
	private String Title;
	private String Description;
	private String PicUrl;
	private String Url;
	
	public String getTitle(){
		return this.Title;
	}
	
	public void setTitle(String title){
		this.Title = title;
	}
	
	public String getDescription(){
		return null == Description ? "" : Description;
	}
	
	public void setDescription(String description){
		this.Description = description;
	}
	
	public String getPicUrl(){
		return null == PicUrl ? "" : PicUrl;
	}
	
	public void setPicUrl(String picUrl){
		this.PicUrl = picUrl;
	}
	
	public String getUrl(){
		return null == Url ? "" : Url;
	}
	
	public void setUrl(String url){
		this.Url = url;
	}

}
