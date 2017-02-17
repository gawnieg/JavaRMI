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
	private int count =0;

	public RMIServer() throws RemoteException {
	}

	public void receiveMessage(MessageInfo msg) throws RemoteException {
			//printout received message
			System.out.println("Server recieves message - " +msg.toString());
			//try {
				// On receipt of first message, initialise the receive buffer
				if(count == 0){
					totalMessages = msg.totalMessages;
					receivedMessages = new int[totalMessages];
				}
				//  Log receipt of the message
				receivedMessages[msg.messageNum-1] = msg.messageNum;
				msg.toString();
				count++;

				// If this is the last expected message, then identify any missing messages
				
			//} catch (Exception e){
			//	System.err.println("Fail receiving message from the client");
			//}
	}
	
	public void setCounter(int value) throws RemoteException{
		count =value;	
	}
	

	public void getMissingMessages() throws RemoteException{
		int countMissing = 0;		
		for(int j =1; j <= totalMessages; j++){
			if(receivedMessages[j-1] != j){
	 			System.out.println("Missing "+totalMessages+";"+j+ " from received");		countMissing ++;
			}			
		}
		System.out.println("Total number of missing messages: "+ countMissing);			
	}

	public static void main(String[] args) {
		//Initialise Security Manager
		if (System.getSecurityManager() == null) {
				System.setSecurityManager(new SecurityManager());
		}
		// Instantiate the server class
		try{
		System.out.println("Server initialising...");
		RMIServerI ser = new RMIServer(); 
		String serverURL ="coline";
		// Bind to RMI registry
		rebindServer(serverURL, (RMIServer) ser); 
		} catch (Exception e) {
			System.err.println("RMIServerI exception:");
			e.printStackTrace();
		}
	}

	protected static void rebindServer(String serverURL, RMIServer server) {
		// Start / find the registry (hint use LocateRegistry.createRegistry(...)
		// If we *know* the registry is running we could skip this (eg run rmiregistry in the start script)
		try{
		Registry registry = LocateRegistry.createRegistry(9000); 
		// Now rebind the server to the registry (rebind replaces any existing servers bound to the serverURL)
		registry.rebind(serverURL, server);
		System.out.println("RMIServerI bound");
		} catch (Exception e) {
			System.err.println("RMIServerI exception:");
			e.printStackTrace();
		}
	}
}
