package com.blogspot.mikelaud.ib;

import com.blogspot.mikelaud.Logger;
import com.blogspot.mikelaud.Settings;
import com.ib.client.EClientSocket;

public class Connection extends ConnectionBase {

	private EClientSocket mClientSocket = new EClientSocket(this);
	
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
