package com.joker.core.util;

import java.util.UUID;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import com.joker.core.constant.LogType;
import com.joker.core.dto.SystemLog;

/**
 * 日志级别分类，异常日志，调试日志，性能日志，行为日志
 *
 * @Author crell
 * @Date 2016/3/18 16:14
 */
@Component("logUtil")
public class LogUtil {


	/**
	 * 异常日志
	 */
	private static Logger exlog = LoggerFactory.getLogger(LogType.EX_LOG);

	/**
	 * 调试日志
	 */
	private static Logger debuglog = LoggerFactory.getLogger(LogType.DEBUG_LOG);

	/**
	 * 性能日志
	 */
	private static Logger perflog = LoggerFactory.getLogger(LogType.PERF_LOG);

	/**
	 * 
	 * 作用描述：调试日志：记录类名、日志信息、数据
	 * 
	 * 
	 * 修改说明：
	 * 
	 * 
	 * @param className
	 *            类名
	 * @param message
	 *            日志信息
	 * @param data
	 *            数据
	 */
	public static void debug(String className, String message, String data, String ip, String userId, String sessionId,
			String serviceId) {
		if (isDebugEnabled(LogType.DEBUG_LOG)) {
			int size = 0;
			String id=UUID.randomUUID().toString();
			size = countSize(size, id);
			size = countSize(size, className);
			size = countSize(size, message);
			size = countSize(size, data);
			size = countSize(size, ip);
			size = countSize(size, userId);
			size = countSize(size, sessionId);
			size = countSize(size, serviceId);
			StringBuilder builder = new StringBuilder(size + 18);// 18为7个参数之间的6个分隔符 6*3(一个分隔符为3)
			appendNull(builder, id, false);
			appendNull(builder, className, false);
			appendNull(builder, message, false);
			appendNull(builder, data, false);
			appendNull(builder, ip, false);
			appendNull(builder, userId, false);
			appendNull(builder, sessionId, false);
			appendNull(builder, serviceId, true);
			debuglog.debug(builder.toString());
		}
	}

	/**
	 * 
	 * 作用描述：性能日志：记录类名、方法名、日志信息、ip地址
	 * 
	 * 
	 * 修改说明：
	 * 
	 * 
	 * @param className
	 *            类名
	 * @param methodName
	 * 			  方法名           
	 * @param message
	 *            日志信息
	 * @param useTime
	 *            执行时长（所使用时间）
	 * @param ip
	 *            ip地址
	 * @param userId
	 *            用户ID
	 */
	public static void debug(String className, String methodName, String message, String useTime, String ip, String userId) {
		if (isDebugEnabled(LogType.PERF_LOG)) {
			int size = 0;
			String id = UUID.randomUUID().toString();
			size = countSize(size, id);
			size = countSize(size, className);
			size = countSize(size, methodName);
			size = countSize(size, message);
			size = countSize(size, useTime);
			size = countSize(size, ip);
			size = countSize(size, userId);
			StringBuilder builder = new StringBuilder(size + 18);// 18为7个参数之间的6个分隔符 6*3(一个分隔符为3)
			appendNull(builder, id, false);
			appendNull(builder, className, false);
			appendNull(builder, methodName, false);
			appendNull(builder, message, false);
			appendNull(builder, useTime, false);
			appendNull(builder, ip, false);
			appendNull(builder, userId, true);
			perflog.debug(builder.toString());
		}
	}
	
	/**
	 * 
	 * 作用描述：异常日志
	 * 
	 * 
	 * 修改说明：
	 * 
	 *@param message 日志消息
	 *@param ex 异常
	 */
	public static void error(String message,Throwable ex){
		exlog.error(message, ex);
	}
	
	/**
	 * 
	 * 作用描述：简单自定义非抛出异常日志
	 * 
	 * 
	 * 修改说明：
	 * 
	 *@param message 日志消息
	 */
	public static void error(String message){
		exlog.error(message);
	}

	/**
	 * 
	 * 作用描述：判断debug级别日志是否开启
	 * 
	 * 
	 * 修改说明：
	 * 
	 * 
	 * @param logType
	 *            日志类型
	 * @return debug级别日志是否开启（true:开启,false：未开启）
	 */
	public static boolean isDebugEnabled(String logType) {
		boolean flag = false;
		if (logType.equals(LogType.DEBUG_LOG)) {
			flag = debuglog.isDebugEnabled();
		}else if (logType.equals(LogType.PERF_LOG)) {
			flag = perflog.isDebugEnabled();
		} else if (logType.equals(LogType.EX_LOG)) {
			flag = exlog.isDebugEnabled();
		}
		return flag;
	}

	/**
	 * 
	 * 作用描述：计算拼装日志参数的StringBuilder所需要的大小
	 * 
	 * 
	 * 修改说明：
	 * 
	 * 
	 * @param size
	 *            原大小
	 * @param mes
	 *            参数字符串
	 * @return 所需大小
	 */
	private static int countSize(int size, String mes) {
		if (null != mes) {
			size += mes.length();
		} else {
			size += 1;
		}
		return size;
	}

	/**
	 * 
	 * 作用描述：对日志参数为null或空的处理
	 * 
	 * 
	 * 修改说明：
	 * 
	 * 
	 * @param builder
	 *            原日志拼装的StringBuilder对象
	 * @param mes
	 *            参数字符串
	 * @param isLastOne
	 *            该参数是否为最后的参数(true:是,false：否)
	 */
	private static void appendNull(StringBuilder builder, String mes, boolean isLastOne) {
		if (null != mes && !"".equals(mes)) {
			builder.append(mes);
		} else {
			builder.append(" ");
		}
		if (!isLastOne) {
			builder.append("{,}");
		}
	}
	
	/**
	 * 
	 * 作用描述：系统程序操作日志
	 * 
	 * @param msg
	 *            参数字符串
	 */
	public void action(String actionName,String msg) {
		//生成用户日志信息
//		SystemLog log = new SystemLog();
//		SaleUser user = SystemUtil.getUser();
//		log.setId(UUID.randomUUID().toString());
//		log.setUserName(user.getUserName());
//		log.setIpAddress(user.getIp());
//		log.setCreateDate(DatetimeUtil.nowToString());
//		log.setActionName(actionName);
//		log.setMessage(msg);
		//记录数据
		//mongoTemplate.save(log);
	}

}
