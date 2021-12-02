package com.example.russ.m08_net_01.common;

import java.io.ObjectInputStream;
import java.net.Socket;
import java.util.Observable;

public class NetReader extends Observable implements Runnable {

	private Socket sock;

	ObjectInputStream in;

	private boolean connected = false;

	public NetReader(Socket sock) {
		this.sock = sock;
		this.connected = true;
	}

	public void run() {

		System.out.println("GetPacket " + sock.getInetAddress() + ":"
				+ sock.getPort());

		while (connected) {

			try { // Get request

				if (in == null) {
					in = new ObjectInputStream(sock.getInputStream());
				}

				Object obj;

				try {
					obj = (Object) (in.readObject());
					// System.out.println(" Read => " + obj.toString());
					this.setChanged();
					this.notifyObservers(obj);

				} catch (Exception e) {
					System.out.println(" Disconnected at other end ");
					e.printStackTrace();
					connected = false; // other side disconnected
				}

			} catch (Exception e) {
				e.printStackTrace();

			}
		}
	}
}
