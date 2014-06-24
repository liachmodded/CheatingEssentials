package com.luna.lib.util.logging;

import java.util.logging.Handler;
import java.util.logging.LogRecord;

/**
 * Date: 4/16/2014
 * ===============================
 *
 * @author RogueCoder
 */
public class SimpleLogHandler extends Handler {
    @Override
    public void publish(LogRecord record) {
        String format = "[%s/%s/%s] %s";
        System.out.println(String.format(format, record.getLevel().getName(), record.getLoggerName(),
                Thread.currentThread().getName(), record.getMessage()));
    }

    @Override
    public void flush() {
        return;
    }

    @Override
    public void close() throws SecurityException {
        return;
    }
}