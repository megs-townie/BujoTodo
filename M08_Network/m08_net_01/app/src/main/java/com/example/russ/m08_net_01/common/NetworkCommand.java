package com.example.russ.m08_net_01.common;

import java.io.Serializable;

public class NetworkCommand implements Serializable {

	/**
	 * Code for this class
	 */
	private static final long serialVersionUID = 2640699340897179385L;

	/**
	 *
	 *
	 *Commands:
	 *
	 * [Reset]  // Client->Server re-send all animals
	 *
	 *  [Animal Data] with embedded action:
	 *  	- change movement
	 *     	- add
	 *     	- remove 
	 *
	 */


	public enum netCommands {
		No_Command, Reset , Change_Movement , Add_Animal , Remove_Animal
	}


	// The command, one of enumerations above
	private netCommands comm;


	public NetworkCommand(netCommands comm) {
		this.comm = comm;
	}

	public NetworkCommand(NetworkCommand n) {
		this.comm = n.comm;
	}

	public netCommands getComm() {
		return comm;
	}

	public String toString() {
		return comm.name();
	}

}
