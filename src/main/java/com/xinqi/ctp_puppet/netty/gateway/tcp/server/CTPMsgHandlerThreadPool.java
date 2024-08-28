package com.xinqi.ctp_puppet.netty.gateway.tcp.server;


import lombok.extern.slf4j.Slf4j;

import java.util.Comparator;
import java.util.concurrent.Executors;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * Description : TODO
 * @author : JasonHan hanzhe.jason@gmail.com
 * @since : 2024/1/23 10:37
 **/
@Slf4j
public class CTPMsgHandlerThreadPool {

	//new PriorityBlockingQueue(5000, new CTPMsgHandlerTaskComparator())
	private static ThreadPoolExecutor THREAD_POOL_EXECUTOR
			= new ThreadPoolExecutor(
					1, 5000, 2, TimeUnit.MINUTES,
			new LinkedBlockingQueue<>(), Executors.defaultThreadFactory(), new ThreadPoolExecutor.CallerRunsPolicy());

	public static void execute(MsgHandlerTask task) {
		THREAD_POOL_EXECUTOR.execute(task);
	}

	public static void clean() {
		THREAD_POOL_EXECUTOR.shutdownNow();
		THREAD_POOL_EXECUTOR
				= new ThreadPoolExecutor(
				1, 5000, 2, TimeUnit.MINUTES,
				new LinkedBlockingQueue<>(), Executors.defaultThreadFactory(), new ThreadPoolExecutor.CallerRunsPolicy());
	}

	public static class CTPMsgHandlerTaskComparator implements Comparator<MsgHandlerTask>{
		@Override
		public int compare(MsgHandlerTask task1, MsgHandlerTask task2) {
			if (task1.getHandleTime() == task2.getHandleTime()) {
				return 0;
			}
			return task1.getHandleTime() < task2.getHandleTime() ? 1 : -1;
		}
	}
}
