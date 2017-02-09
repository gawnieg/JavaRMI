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

	}


	public static void main(String[] args) {

		RMIServer rmis = null;

		// Initialise Security Manager
		if (System.getSecurityManager() == null) {
				System.setSecurityManager(new SecurityManager());
		}

		System.out.println("YOUHOUU. server side");

		// Instantiate the server class
		try{
		RMIServerI ser = new RMIServer();

		String serverURL ="XXX";
		rebindServer(serverURL, (RMIServer) ser);
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
		Registry registry = LocateRegistry.getRegistry(8080);


		// TO-DO:
		// Now rebind the server to the registry (rebind replaces any existing servers bound to the serverURL)
		// Note - Registry.rebind (as returned by createRegistry / getRegistry) does something similar but
		// expects different things from the URL field.

			registry.rebind("RMIServerI", server);
			System.out.println("RMIServerI bound");
			} catch (Exception e) {
				System.err.println("RMIServerI exception:");
				e.printStackTrace();
			}
	}
}
