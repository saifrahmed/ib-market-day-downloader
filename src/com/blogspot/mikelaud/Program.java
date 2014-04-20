package com.blogspot.mikelaud;

import com.blogspot.mikelaud.data.Symbols;
import com.blogspot.mikelaud.data.SymbolsNyseStocks;
import com.blogspot.mikelaud.ib.Connection;

public class Program implements Runnable {

	private Connection mConnection = new Connection();
	
	private String getBeginFormat(int aSymbolMaxSize) {
		StringBuilder formatBuilder = new StringBuilder();
		formatBuilder.append("%2dh %2dm [%2d%%] %");
		formatBuilder.append(aSymbolMaxSize);
		formatBuilder.append("s ");
		return formatBuilder.toString();
	}
	
	private String getEndFormat(int aSymbolsCount) {
		StringBuilder formatBuilder = new StringBuilder();
		formatBuilder.append("(%d of ");
		formatBuilder.append(aSymbolsCount);
		formatBuilder.append(") OK");
		return formatBuilder.toString();
	}
	
	private void processSymbol(int aSymbolId, String aSymbol) {
		// void
	}
	
	private void processSymbols() {
		Symbols symbols = new SymbolsNyseStocks();
		int symbolsCount = symbols.getCount();
		int symbolMaxSize = symbols.getSymbolMaxSize();
		//
		String beginFormat = getBeginFormat(symbolMaxSize);
		String endFormat = getEndFormat(symbols.getCount());
		//
		int requestPeriodSec = Settings.getRequestPeriodSec();
		for (int id = 0; id < symbolsCount; id++) {
			//
			String symbol = symbols.getSymbol(id);
			int percent = id * 100 / symbols.getCount();
			//
			int remainingSec = requestPeriodSec * (symbolsCount - id - 1);
			int totalMin = remainingSec / 60;
			int remainingH = totalMin / 60;
			int remainingMin = totalMin - (remainingH * 60);
			//
			String beginLine = String.format(beginFormat, remainingH, remainingMin, percent, symbol);
			Logger.print(beginLine);
			//
			for (int i = 0; i < requestPeriodSec; i++) {
				Logger.print(".");
				try {
					Thread.sleep(1000);
				}
				catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
			processSymbol(id, symbol);
			String endLine = String.format(endFormat, id + 1);
			Logger.println(endLine);
		}
	}
	
	@Override
	public void run() {
		try {
			mConnection.connect();
			Logger.println("Connected.");
			processSymbols();
		}
		catch(Throwable t) {
			Logger.logError("Fatal error: " + t.getMessage());
		}
		finally {
			mConnection.disconnect();
			Logger.println("Done.");
		}
	}
	
	public Program() {
		// void
	}
	
}
