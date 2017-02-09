/*
 * Created on 01-Mar-2016
 */
package rmi;

import java.rmi.Naming;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;

// I added this
import java.rmi.registry.*;

import common.MessageInfo;

//invokes method on a RMI server
public class RMIClient {

	public static void main(String[] args) {

		RMIServerI iRMIServer = null;

		// Check arguments for Server host and number of messages
		if (args.length < 2){
			System.out.println("Needs 2 arguments: ServerHostName/IPAddress, TotalMessageCount");
			System.exit(-1);
		}

		String urlServer = new String("rmi://" + args[0] + "/RMIServer");
		int numMessages = Integer.parseInt(args[1]);

		// Initialise Security Manager
		if (System.getSecurityManager() == null) {
				System.setSecurityManager(new SecurityManager());
		}
		System.out.println("HOLA. client side");

		// TO-DO: Bind to RMIServer
		try {
				Registry registry = LocateRegistry.getRegistry(args[0]);
				iRMIServer = (RMIServerI) registry.lookup("RMIServerI");
			}
		 catch (Exception e) {
				System.err.println("Client  exception when binding to the server :");
				e.printStackTrace();
		}

		// Attempt to send messages the specified number of times

		// number of messages sent
		int total = 10;
		for (int i=1; i<=total;i++){
			try {
				MessageInfo msg = new MessageInfo(total,i);
				// Am i sure of that?
				iRMIServer.receiveMessage(msg);
				System.out.println("Client Sending message - "+ msg.toString());
    	} catch (Exception e) {
				System.err.println("Client Sending message " +i+" exception:");
				e.printStackTrace();
    	}
		}
	}
}
