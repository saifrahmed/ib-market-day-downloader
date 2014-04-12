package com.blogspot.mikelaud.data;

public abstract class SymbolsAbstract implements Symbols {

	private final String[] NO_SYMBOLS = {};
	
	protected abstract String[] getSymbols();
	
	private String[] getSymbolsNvl() {
		String[] symbols = getSymbols();
		return (null == symbols ? NO_SYMBOLS : symbols);
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

}
