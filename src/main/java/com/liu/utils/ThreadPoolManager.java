package com.liu.utils;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.Callable;
import java.util.concurrent.CancellationException;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.RejectedExecutionHandler;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 
* @ClassName: ThreadPoolManager 
* @Description: 线程池工具类
* @author 汪海霖 wanghl15@midea.com.cn 
* @date 2014-5-24 上午10:17:42 
*
 */
public class ThreadPoolManager {
	
	private static Logger logger = LoggerFactory.getLogger(ThreadPoolManager.class);
	/** **/
	private ThreadPoolExecutor threadPool;
	/** **/
	private ArrayBlockingQueue<Runnable> taskQueue = new ArrayBlockingQueue<Runnable>(10000);
	/**核心线程个数**/
	private static int corePoolSize = Runtime.getRuntime().availableProcessors()*2;
	/**最大线程个数 **/
	private static int maximumPoolSize = Runtime.getRuntime().availableProcessors()*5;
	/**保持心跳时间 **/
	private static int keepAliveTime = 1;
	/**定时执行线程个数**/
	private final static int minSchedule = 2;
	/**线程池实例化**/
	private static ThreadPoolManager threadPoolManage = new ThreadPoolManager();
	/**延时执行线程**/
	private ScheduledExecutorService appSchedule;
	/**
	 * 线程池构造方法
	 */
	private ThreadPoolManager(){
		RejectedExecutionHandler myHandler = new RejectedExecutionHandler(){
			public void rejectedExecution(Runnable r,
					ThreadPoolExecutor executor) {
				taskQueue.offer(r);
			}
		};
		Runnable command = new Runnable(){
			public void run() {
				Runnable task =null;
				try {
					task = taskQueue.take();//使用具备阻塞特性的方法
				} catch (InterruptedException e) {
					return;
				}
				threadPool.execute(task);
			}
		};
		ScheduledExecutorService scheduledPool = Executors.newScheduledThreadPool(1);
		//每一次执行终止和下一次执行开始之间都存在给定的延迟 16毫秒
		scheduledPool.scheduleWithFixedDelay(command, 0L, 16L, TimeUnit.MILLISECONDS);
		threadPool = new ThreadPoolExecutor(corePoolSize, maximumPoolSize, 
				keepAliveTime, TimeUnit.SECONDS, new ArrayBlockingQueue<Runnable>(20), 
				new NamedThreadFactory("CoreImplServiceHandler"),myHandler){
			public void afterExecute(Runnable r, Throwable t) { 
		        super.afterExecute(r, t);   
		        printException(r, t);   
		    }  
		};
		appSchedule = Executors.newScheduledThreadPool(minSchedule);
	}
	
	private static void printException(Runnable r, Throwable t) {
		if (t == null && r instanceof Future<?>) {
			try {
				Future<?> future = (Future<?>) r;
				if (future.isDone())
					future.get();
			} catch (CancellationException ce) {
				t = ce;
			} catch (ExecutionException ee) {
				t = ee.getCause();
			} catch (InterruptedException ie) {
				Thread.currentThread().interrupt(); 
			}
		}
		if (t != null){
			Throwable cause = t.getCause();
			if(RespPackUtil.isTimeoutThrowable(cause)){
				logger.error("系统自有线程池任务调用超时异常,error_msg=="+cause.getMessage());
			}else{
				logger.error("系统自有线程池任务异常,error_msg=="+t.getMessage(), t);
			}
		}
	}
	/**
	 * 得到线程池的实例
	 * @return
	 */
	public static ThreadPoolManager getInstance(){
		return threadPoolManage;
	}
	
	public int getActiveCount(){
		return threadPool.getActiveCount();
	}
	
	public int getCorePoolCount(){
		return corePoolSize;
	}
	/**
	 * 任务添加到线程池中
	 * @param paramRunnable
	 */
	public Future<?> addExecuteTask(Runnable paramRunnable){
		if(paramRunnable == null)
			return null;
		return this.threadPool.submit(paramRunnable);
	}
	
	public Future<?> addExecuteTask(Callable<?> paramRunnable){
		if(paramRunnable == null)
			return null;
		return this.threadPool.submit(paramRunnable);
	}
	/**
	 * 延时任务添加到线程池中
	 * @param paramRunnable
	 */
	public void addDelayExecuteTask(Runnable task,int delayTime){
		appSchedule.schedule(new DelayTask(task), delayTime, 
					TimeUnit.MILLISECONDS);
	}
	
	/**
	 * 延时固定周期执行
	 * @param task
	 * @param delay
	 * @param period
	 */
	public void addPeriodDelayExecuteTask(Runnable task, Long delay, Long period) {
		this.appSchedule.scheduleWithFixedDelay(new DelayTask(task), delay, period, TimeUnit.MILLISECONDS);
	}
	
	public boolean isAsyncBySimple(){
		return getActiveCount() <= getCorePoolCount();
	}
	
	/**延时任务**/
	class DelayTask implements Runnable{
		
		private Runnable task;
		
		public DelayTask(Runnable paramTask){
			this.task = paramTask;
		}
		public void run() {
			ThreadPoolManager.getInstance().addExecuteTask(task);
		}
	}
}
