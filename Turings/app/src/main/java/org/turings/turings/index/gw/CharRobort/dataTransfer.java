package org.turings.turings.index.gw.CharRobort;

public class dataTransfer {
	private String name;
	private String word;
	
	private int isMessage;
	
	public dataTransfer()
	{
		
	}
	
	public dataTransfer(String name, String word, int isMessage)
	{
		super();
		this.name = name;
		this.word = word;
		this.isMessage = isMessage;
	}
	
	public void setName(String name)
	{
		this.name = name;
	}
	
	public String getName()
	{
		return this.name;
	}
	
	public void setWord(String word)
	{
		this.word = word;
	}
	
	public String getWord()
	{
		return this.word;
	}
	
	public void setRobotUser(int isMessage)
	{
		this.isMessage = isMessage;
	}
	
	public int getRobotUser()
	{
		return this.isMessage;
	}
}
