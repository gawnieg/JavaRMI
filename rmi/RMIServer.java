/*
 * Created on 01-Mar-2016
 */
package rmi;

import java.net.MalformedURLException;
import java.rmi.Naming;
import java.rmi.registry.LocateRegistry;
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
			//printout received message
			System.out.println("Server recieves message - " +msg.toString());
			try {
				// On receipt of first message, initialise the receive buffer
				if(msg.messageNum == 1){
					totalMessages = msg.totalMessages;
					receivedMessages = new int[totalMessages];
				}
				//  Log receipt of the message
				receivedMessages[msg.messageNum-1] = msg.messageNum;
				msg.toString();

				// If this is the last expected message, then identify any missing messages
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
		// Instantiate the server class
		try{
		System.out.println("Server initialising...");
		RMIServerI ser = new RMIServer(); //whoa, whats happening here?
		String serverURL ="coline";
		// Bind to RMI registry
		rebindServer(serverURL, (RMIServer) ser); //this is the below method, why casting?
		} catch (Exception e) {
			System.err.println("RMIServerI exception:");
			e.printStackTrace();
		}
	}

	protected static void rebindServer(String serverURL, RMIServer server) {
		// Start / find the registry (hint use LocateRegistry.createRegistry(...)
		// If we *know* the registry is running we could skip this (eg run rmiregistry in the start script)
		try{
		Registry registry = LocateRegistry.createRegistry(9000); //must match that of serverURL passed
		// Now rebind the server to the registry (rebind replaces any existing servers bound to the serverURL)
		registry.rebind(serverURL, server);
		System.out.println("RMIServerI bound");
		} catch (Exception e) {
			System.err.println("RMIServerI exception:");
			e.printStackTrace();
		}
	}
}
