package com.imgretrievaldemo;

public class IndexRunner extends Thread {
	private String type;
	private Integer bin;
	public IndexRunner(String name, String type, Integer bin){
		    super(name);
		    this.type=type;
		    this.bin=bin;
	}
	public void run(){
		Tools.createIndex(type, bin);
	} 
}
