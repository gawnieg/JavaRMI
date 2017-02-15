/*
 * Created on 01-Mar-2016
 */
package rmi;

import java.net.MalformedURLException;
import java.rmi.Naming;
import java.rmi.registry.LocateRegistry;

// add this
import java.rmi.registry.*;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.Arrays;

import common.*;

public class RMIServer extends UnicastRemoteObject implements RMIServerI {

	private int totalMessages = -1;
	private int[] receivedMessages;

	public RMIServer() throws RemoteException {
	}

	public void receiveMessage(MessageInfo msg) throws RemoteException {

			System.out.println("Server recieves message - " +msg.toString());
			// TO-DO: On receipt of first message, initialise the receive buffer

			// TO-DO: Log receipt of the message

			// TO-DO: If this is the last expected message, then identify
			//        any missing messages

			// TO-DO: Use the data to construct a new MessageInfo object
			try {
				// MessageInfo ourMessage = new MessageInfo(data) ;
				// 	ourMessage.toString();
				System.out.println("In try statement");
				System.out.println("Total messages is");
				System.out.println(totalMessages);
			// TO-DO: On receipt of first message, initialise the receive buffer
				if(msg.messageNum == 1){
					totalMessages = msg.totalMessages;
					receivedMessages = new int[totalMessages];
				}

			// TO-DO: Log receipt of the message
				receivedMessages[msg.messageNum-1] = msg.messageNum;
				msg.toString();

			// TO-DO: If this is the last expected message, then identify
			//        any missing messages
			if(msg.messageNum==totalMessages){
				boolean missing_messages = false;
				System.out.println("Checking for missing messages....");
			 	for(int j =1; j <= totalMessages; j++){
			 		if(receivedMessages[j-1] != j){
			 			System.out.println("Missing "+j+ " from recieved");
						missing_messages = true;
			 		}

				}
				if(missing_messages==false){
					System.out.println("There were no missing messages!!!!");
				}
			}
		} catch (Exception e){
			System.err.println("Fail receiving message from the client");
		}
	}


	public static void main(String[] args) {

		//Initialise Security Manager
		if (System.getSecurityManager() == null) {
				System.setSecurityManager(new SecurityManager());
		}

		System.out.println("YOUHOUU. server side");

		// Instantiate the server class
		try{
		RMIServerI ser = new RMIServer(); //whoa, whats happening here?

		String serverURL ="coline";
		rebindServer(serverURL, (RMIServer) ser); //this is the below method, why casting?
	} catch (Exception e) {
		System.err.println("RMIServerI exception:");
		e.printStackTrace();
	}
		// Bind to RMI registry

		// TO-DO What is the serverURL ????
	}

	protected static void rebindServer(String serverURL, RMIServer server) {

		// TO-DO:
		// Start / find the registry (hint use LocateRegistry.createRegistry(...)
		// If we *know* the registry is running we could skip this (eg run rmiregistry in the start script)

		// Registry registry = LocateRegistry.createRegistry(8080);

		try{
		Registry registry = LocateRegistry.createRegistry(9000); //must match that of serverURL passed


		// TO-DO:
		// Now rebind the server to the registry (rebind replaces any existing servers bound to the serverURL)
		// Note - Registry.rebind (as returned by createRegistry / getRegistry) does something similar but
		// expects different things from the URL field.

			registry.rebind(serverURL, server);
			System.out.println("RMIServerI bound");
			} catch (Exception e) {
				System.err.println("RMIServerI exception:");
				e.printStackTrace();
			}
	}
}
