package ua.edu.ontu.service;

import org.slf4j.Logger;
import org.springframework.stereotype.Service;

@Service
public class LogUtil {

    public final static String TELEGRAM_CLIENT_REQUEST_ERROR = "Telegram client request error";

    public void logError(Logger logger, String logKey, String msg) {
        logger.error(logKey + ": " + msg);
    }
}