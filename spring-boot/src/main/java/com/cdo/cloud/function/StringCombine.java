package com.cdo.cloud.function;

public class StringCombine {
	private String prefix;
	private String suffix;
	private String delimiter;
	
	private StringBuilder value;
	
	public StringCombine(String delimiter,String prefix,String suffix){
		this.delimiter=delimiter;
		this.prefix=prefix;
		this.suffix=suffix;
	}
	
	boolean areAtStart(){		
		return value==null?true:false;
	}
	
	StringBuilder prepareBuilder(){
		if(areAtStart()){
			value=new StringBuilder();
			value.append(prefix);
		}else{
			value.append(delimiter);
		}
		return value;
	}
	public StringCombine add(String el){		
		prepareBuilder().append(el);		
		return this;
	}

	
	public StringCombine merge(StringCombine other){
		System.out.println("method merge");
		if(other.value!=null){
			final int length = other.value.length();
			StringBuilder builder=prepareBuilder();
			builder.append(other.value, other.prefix.length(), length);
		}
		return this;
	}
	
    public String toString() {
    	if(value==null)
    		return "null";
    	return value.append(suffix).toString();
    }
}
