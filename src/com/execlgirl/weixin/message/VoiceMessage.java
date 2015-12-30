package com.execlgirl.weixin.message;

public class VoiceMessage extends BaseMessage{
    private Voice Voice;
	
	public Voice getVoice(){
		return this.Voice;
	}
	
	public void setVoice(Voice voice){
		this.Voice = voice;
	}
}

