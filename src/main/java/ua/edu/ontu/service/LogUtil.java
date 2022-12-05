package ua.edu.ontu.service;

import org.slf4j.Logger;

public class LogUtil {

	public static final String TELEGRAM_CLIENT_REQUEST_ERROR = "Telegram client request error";
	public static final String TELEGRAM_CLIENT_REQUEST_INFO = "Telegram client request";
	public static final String ADMIN_PANEL_JWT_ERROR = "Admin panel jwt error";

	private LogUtil() {
		throw new IllegalStateException("Utility class");
	}

	public static void logError(Logger logger, String logKey, String msg, Exception exception) {
		String logMessage = logKey + ": " + msg;
		logger.error(logMessage, exception);
	}

	public static void logError(Logger logger, String logKey, String msg) {
		String logMessage = logKey + ": " + msg;
		logger.error(logMessage);
	}

	public static void logInfo(Logger logger, String logKey, String msg) {
		logInfo(logger, logKey + ": " + msg);
	}

	public static void logInfo(Logger logger, String msg) {
		logger.info(msg);
	}
}