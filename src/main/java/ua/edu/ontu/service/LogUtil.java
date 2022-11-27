package ua.edu.ontu.service;

import org.slf4j.Logger;
import org.springframework.stereotype.Service;

public class LogUtil {

	public final static String TELEGRAM_CLIENT_REQUEST_ERROR = "Telegram client request error";
	public final static String TELEGRAM_CLIENT_REQUEST_INFO = "Telegram client request";
	public final static String ADMIN_PANEL_JWT_ERROR = "Admin panel jwt error";

	public static void logError(Logger logger, String logKey, String msg, Exception exception) {
		logger.error(logKey + ": " + msg, exception);
	}

	public static void logError(Logger logger, String logKey, String msg) {
		logger.error(logKey + ": " + msg);
	}

	public static void logInfo(Logger logger, String logKey, String msg) {
		logInfo(logger, logKey + ": " + msg);
	}

	public static void logInfo(Logger logger, String msg) {
		logger.info(msg);
	}
}