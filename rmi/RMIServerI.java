/*
 * Created on 01-Mar-2016
 */
package rmi;

import java.rmi.Remote;
import java.rmi.RemoteException;

import common.*;

// Remote interface
// Contains methods remotely onvoked on remote object
// Every interface extends java.rmi.Remote
public interface RMIServerI extends Remote {
	public void receiveMessage(MessageInfo msg) throws RemoteException;

	public void setCounter(int count) throws RemoteException;

	public void getMissingMessages() throws RemoteException;
}
