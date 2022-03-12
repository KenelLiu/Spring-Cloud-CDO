package com.cdo.cloud.thread;

import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Phaser;
import java.util.concurrent.Semaphore;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.ReentrantLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

public class ThreadMain {

	public static void main(String[] args) throws IOException {
	 		ThreadGroup threadGroup=new ThreadGroup("search");
	 	
	 		ReentrantLock lock=new ReentrantLock();
	 		lock.newCondition();
	 		ReentrantReadWriteLock readWriteLock=new ReentrantReadWriteLock();
	 		ExecutorService ex=Executors.newFixedThreadPool(5);
	 		for(int i=0;i<5;i++){
	 			Result result=new Result();
	 			SearchTask searchTask=new SearchTask(result);
	 			Thread thread=new Thread(threadGroup, searchTask);
	 			thread.start();
	 			try {
					TimeUnit.SECONDS.sleep(1);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
	 		}
	 		System.out.printf("Number of Thread:%d\n",threadGroup.activeCount());
	 		System.out.printf("list of threadGroup:");
	 		threadGroup.list();
	 		Thread[] threads=new Thread[threadGroup.activeCount()];
	 		threadGroup.enumerate(threads);
	 		for(int i=0;i<threadGroup.activeCount();i++){
	 			System.out.printf("Thread:%s %s\n",threads[i].getName(),threads[i].getState());
	 		}
//	 		waitFinish(threadGroup);
	 		threadGroup.interrupt();
	 		
	}
	
	private static void waitFinish(ThreadGroup threadGroup){
		while(threadGroup.activeCount()>0){
			try {
				TimeUnit.SECONDS.sleep(1);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}

}
