package com.cdo.cloud.thread;

import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.lang.Thread.State;

public class Charpt1Main {

	public void example1(){
		System.out.printf("Min Priority:%s\n",Thread.MIN_PRIORITY);
		System.out.printf("Normal Priority:%s\n",Thread.NORM_PRIORITY);
		System.out.printf("Max Priority:%s\n",Thread.MAX_PRIORITY);
		
		Thread threads[]=new Thread[10];
		Thread.State status[]=new Thread.State[threads.length];
		for(int i=0;i<threads.length;i++){
				threads[i]=new Thread(new Calculator());
				if(i%2==0)
					threads[i].setPriority(Thread.MAX_PRIORITY);
				else
					threads[i].setPriority(Thread.MIN_PRIORITY);
				threads[i].setName("Thread-"+i);
		}
		
		try(FileWriter fw=new FileWriter("d:/log.txt");
			PrintWriter pw=new PrintWriter(fw);){
			for(int i=0;i<threads.length;i++){
				pw.println("Main:status of Thread "+i+" : "+threads[i].getState());
				status[i]=threads[i].getState();				
			}
			for(int i=0;i<threads.length;i++){
				threads[i].start();
			}
			boolean finish=false;
			while(!finish){
				for(int i=0;i<threads.length;i++){
					if(threads[i].getState()!=status[i]){
						writeThreadInfo(pw,threads[i],status[i]);
						status[i]=threads[i].getState();						
					}				
				}
				finish=true;
				for(int i=0;i<threads.length;i++){
					finish=finish&&(threads[i].getState()==State.TERMINATED); 
				}
				
			}
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	private static void writeThreadInfo(PrintWriter pw,Thread thread,State state){
		pw.printf("Main:Id %d-%s\n", thread.getId(),thread.getName());
		pw.printf("Main:Priority %d\n", thread.getPriority());
		pw.printf("Main:Old State:%s\n",state);
		pw.printf("Main:New State:%s\n",thread.getState());
		pw.printf("Main:******************************************\n");
	}
}
