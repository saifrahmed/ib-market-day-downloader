package com.blogspot.mikelaud.logic;

public class Logger {

	private static final java.util.logging.Logger LOGGER =
		java.util.logging.Logger.getLogger(
			Thread.currentThread().getStackTrace()[0].getClassName()
		);
	//
	public static void logError(final String aMessage) { LOGGER.severe(aMessage); }
	public static void logWarning(final String aMessage) { LOGGER.warning(aMessage); }
	public static void logInfo(final String aMessage) { LOGGER.info(aMessage); }
	public static void logDebug(final String aMessage) { LOGGER.config(aMessage); }
	//
	public static void print(final String aMessage) { System.out.print(aMessage); }
	public static void println(final String aMessage) { System.out.println(aMessage); }
	
}
