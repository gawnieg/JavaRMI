/*
 * Created on 01-Mar-2016
 */
package rmi;

import java.rmi.Naming;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.*;

import common.MessageInfo;

//invokes method on a RMI server
public class RMIClient {

	public static void main(String[] args) {

		// Check arguments for Server host and number of messages
		if (args.length < 2){
			System.out.println("Needs 2 arguments: ServerHostName/IPAddress, TotalMessageCount");
			System.exit(-1);
		}
		String urlServer = new String("coline");
		int numMessages = Integer.parseInt(args[1]);

		// Initialise Security Manager
		if (System.getSecurityManager() == null) {
				System.setSecurityManager(new SecurityManager());
		}
		//Bind to RMIServer
		try {
				System.out.println("Initialising client side");
				Registry registry = LocateRegistry.getRegistry(args[0],9000);
				iRMIServer = (RMIServerI) registry.lookup("coline");//why casting here? // was "RMIServer"
			} catch (Exception e) {
				System.err.println("Client  exception when binding to the server :");
				e.printStackTrace();
		}

		// Attempt to send messages the specified number of times
		for (int i=1; i<=numMessages;i++){
			try {
				MessageInfo msg = new MessageInfo(numMessages,i); //constructuing new messagesInfo with constructor
				iRMIServer.receiveMessage(msg);
				System.out.println("Client Sending message - "+ msg.toString());
    		} catch (Exception e) {
				System.err.println("Client Sending message " +i+" exception:");
				e.printStackTrace();
    		}
		}
	}
}
