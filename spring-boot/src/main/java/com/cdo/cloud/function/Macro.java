package com.cdo.cloud.function;

import java.util.ArrayList;
import java.util.List;

public class Macro {
	private final List<Action> actions;
	public Macro(){
		this.actions=new ArrayList<Action>();
	}
	
	public void record(Action action){
		actions.add(action);
	}
	
	public void run(){
		actions.forEach(action->action.perform());
	}
}
