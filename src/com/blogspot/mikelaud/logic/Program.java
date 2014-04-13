package com.blogspot.mikelaud.logic;

import com.blogspot.mikelaud.data.Symbols;
import com.blogspot.mikelaud.data.SymbolsNyseStocks;

public class Program implements Runnable {

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
	
	@Override
	public void run() {
		//
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
	
	public Program() {
		// void
	}
	
}
