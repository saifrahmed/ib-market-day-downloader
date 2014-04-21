package com.blogspot.mikelaud.ib;

import java.util.concurrent.ConcurrentLinkedQueue;

import com.blogspot.mikelaud.Logger;
import com.blogspot.mikelaud.Settings;
import com.ib.client.Contract;
import com.ib.client.EClientSocket;

public class Connection extends ConnectionBase {

	private EClientSocket mClientSocket = new EClientSocket(this);
	private int mTickerId = 0;
	private int mHistoricalDataCount = 0;
	private volatile boolean mHistoricalDataDone = false;
	private ConcurrentLinkedQueue<String> mErrors = new ConcurrentLinkedQueue<String>(); 
	
	private void checkConnection() throws Exception {
		//
		String host = Settings.getHost();
		int port = Settings.getPort();
		int clientId = Settings.getClientId();
		//
		if (isDisconnected()) {
			String exceptionMessage = String.format(
				"Lost connection to: host=\"%s\" port=\"%s\" clientId=\"%s\"",
				host, port, clientId
			);
			throw new Exception(exceptionMessage);
		}
	}
	
    private Contract createContract(String symbol, String securityType, String exchange, String primaryExchange, String currency) {
        Contract contract = new Contract();
        //
        contract.m_symbol = symbol;
        contract.m_secType = securityType;
        contract.m_exchange = exchange;
        contract.m_primaryExch = primaryExchange;
        contract.m_currency = currency;
        //
        return contract;
    }
	
    private void resetHistoricalData() {
    	mHistoricalDataCount = 0;
    	mHistoricalDataDone = false;
    }
    
    public boolean isHistoricalDataDone() {
    	return mHistoricalDataDone;
    }
    
    public void cancelHistoricalData() throws Exception {
    	checkConnection();
    	mClientSocket.cancelHistoricalData(mTickerId);
    }
    
	public void reqHistoricalData(String aSymbol) throws Exception {
		checkConnection();
		resetHistoricalData();
		++mTickerId;
		//
		Contract contract = createContract(aSymbol, "STK", "NYSE", "NYSE", "USD");
		String endDateTime = "20140404  23:59:59 EST"; // (yyyymmdd{space}{space}hh:mm:dd)
        String durationStr = "2 D";
        String barSizeSetting = "1 min";
        String whatToShow = "TRADES";
        int useRTH = 1; // 1: only data within the regular trading hours is returned
        int formatDate = 1; // 1: yyyymmdd{space}{space}hh:mm:dd
        //
        mClientSocket.reqHistoricalData(
        	mTickerId, contract, endDateTime, durationStr, barSizeSetting, whatToShow, useRTH, formatDate
        );
	}
	
	public int getHistoricalDataCount() {
		return mHistoricalDataCount;
	}

	@Override
	public void historicalData(int reqId, String date, double open,
			double high, double low, double close, int volume, int count,
			double WAP, boolean hasGaps) // callback
	{
		if (reqId != mTickerId) {
			return;
		}
		//
		if (date.startsWith("finished-")) {
			mHistoricalDataDone = true;
			return;
		}
		//
		++mHistoricalDataCount;
		/*
		System.out.println(
    		"id=" + reqId +
    		" no=" + mHistoricalDataCount +
    		" date=" + date +
    		" open=" + open +
    		" high=" + high +
    		" low=" + low +
    		" close=" + close +
    		" volume=" + volume +
    		" count=" + count +
    		" WAP=" + WAP +
    		" hasGaps=" + hasGaps
    	);
    	*/
    	
	}
	
	@Override
	public void error(Exception e) {
		mErrors.add(e.toString());
	}

	@Override
	public void error(String str) {
		mErrors.add(str);
	}

	@Override
	public void error(int id, int errorCode, String errorMsg) {
		mErrors.add("(id=" + id + " errorCode=" + errorCode + ") " + errorMsg);
	}
	
	public boolean hasNoErrors() {
		return mErrors.isEmpty();
	}
	
	public boolean hasErrors() {
		return ! hasNoErrors();
	}
		
	public void printErrors() {
		for (;;) {
			String error = mErrors.poll();
			if (null == error) break;
			Logger.logError(error);
		}
	}
	
	public boolean isConnected() { return mClientSocket.isConnected(); }
	public boolean isDisconnected() { return ! isConnected(); }
	
	public void connect() throws Exception {
		//
		String host = Settings.getHost();
		int port = Settings.getPort();
		int clientId = Settings.getClientId();
		//
		String message = String.format(
			"Connect to: host=\"%s\" port=\"%s\" clientId=\"%s\" ...",
			host, port, clientId
		);
		Logger.println(message);
		//
		mClientSocket.eConnect(host, port, clientId);
		//
		if (isDisconnected()) {
			String exceptionMessage = String.format(
				"Unable connect to: host=\"%s\" port=\"%s\" clientId=\"%s\"",
				host, port, clientId
			);
			throw new Exception(exceptionMessage);
		}
	}
	
	public void disconnect() {
		if (isConnected()) {
			mClientSocket.eDisconnect();
			if (isDisconnected()) {
				//
				String host = Settings.getHost();
				int port = Settings.getPort();
				int clientId = Settings.getClientId();
				//
				String message = String.format(
					"Disconnected from: host=\"%s\" port=\"%s\" clientId=\"%s\"",
					host, port, clientId
				);
				Logger.println(message);
			}
		}
	}
	
	public Connection() {
		// void
	}

}
