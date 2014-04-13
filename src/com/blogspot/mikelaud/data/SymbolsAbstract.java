package com.blogspot.mikelaud.data;

public abstract class SymbolsAbstract implements Symbols {

	private final String[] NO_SYMBOLS = {};
	
	private int mSymbolMaxSize = 0;
	
	protected abstract String[] getSymbols();
	
	private String[] getSymbolsNvl() {
		String[] symbols = getSymbols();
		return (null == symbols ? NO_SYMBOLS : symbols);
	}
	
	private void updateSymbolMaxSize() {
		String[] symbols = getSymbolsNvl();
		for (int id = 0; id < symbols.length; id++) {
			String symbol = symbols[id];
			if (null == symbol) {
				symbol = "";
			}
			int symbolSize = symbol.length(); 
			if (symbolSize > mSymbolMaxSize) {
				mSymbolMaxSize = symbolSize; 
			}
		}
	}

	@Override
	public int getCount() {
		String[] symbols = getSymbolsNvl();
		int count = symbols.length;
		return count;
	}

	@Override
	public String getSymbol(int aSymbolId) {
		String[] symbols = getSymbolsNvl();
		if (aSymbolId >= 0 && aSymbolId < getCount()) {
			return symbols[aSymbolId];
		}
		else {
			return "";
		}
	}

	@Override
	public int getSymbolMaxSize() {
		if (mSymbolMaxSize <= 0) {
			updateSymbolMaxSize();
		}
		return mSymbolMaxSize;
	}

	public SymbolsAbstract() {
		// void
	}

}
