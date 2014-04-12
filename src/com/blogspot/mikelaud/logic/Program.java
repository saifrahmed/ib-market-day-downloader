package com.blogspot.mikelaud.logic;

import com.blogspot.mikelaud.data.Symbols;
import com.blogspot.mikelaud.data.SymbolsNyseStocks;

public class Program implements Runnable {

	@Override
	public void run() {
		Symbols symbols = new SymbolsNyseStocks();
		for (int id = 0; id < symbols.getCount(); id++) {
			Logger.println(symbols.getSymbol(id));
		}
	}
	
	public Program() {
		// void
	}
	
}
