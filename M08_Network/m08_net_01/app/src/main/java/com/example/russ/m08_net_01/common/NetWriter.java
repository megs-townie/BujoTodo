package com.example.russ.m08_net_01.common;

import java.io.IOException;
import java.io.ObjectOutputStream;
import java.net.InetAddress;
import java.net.Socket;

public class NetWriter extends Thread {

	private Socket sock;

	ObjectOutputStream out;

	private boolean connected = true;

	// Send Message Queue ...
	// Adding=sending means empty
	// Adding!=sending means something to send at sending
	// use    q_adding = (q_adding+1) % q_size    to mod (and wrap around) 
	Integer q_size = 90;
	Object[] q = new Object[q_size];
	Integer q_adding = 0;
	Integer q_sending = 0;

	public NetWriter(Socket socket) {
		this.sock = socket;
		this.connected = true;

		try {
			// Need INTERNET permission
			//java.lang.SecurityException: Permission denied (missing INTERNET permission?)
			out = new ObjectOutputStream(sock.getOutputStream());
		} catch (IOException e1) {
			e1.printStackTrace();
		}
	}


	public InetAddress getIP(){
		return sock.getInetAddress();		
	}
	
	
	public synchronized void sendThisMsgOnQueue(Object objectToSend) {
		try { // send request
			q[q_adding] = (objectToSend);
			q_adding = (q_adding+1) % q_size;
			System.out.println("Send Queue => " + q_adding + "::" + q_sending);
			this.notifyAll();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private synchronized void waitToSend() {
		while (q_adding==q_sending) {
			try {
				this.wait();
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}

	public void run() {

		System.out.println("ClientWrite setup " + sock.getInetAddress() + ":" + sock.getPort()+":"+sock.getLocalPort());

		while (connected) {

			waitToSend();

			while (q_adding != q_sending) {

				Object objectToSend = q[q_sending];
				
				try { // send request

//					System.out.println("Sending=> "+ q_adding + "::" + q_sending +" "
//							+ objectToSend.toString());

					out.writeObject(objectToSend);
					out.flush();
					q_sending = (q_sending+1) % q_size;

					System.out.println("Sent=> "
							+ objectToSend.toString());
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		}

	}

}
