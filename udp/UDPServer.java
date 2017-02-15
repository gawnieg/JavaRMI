/*
 * Created on 01-Mar-2016
 */
package udp;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.SocketException;
import java.net.SocketTimeoutException;
import java.util.Arrays;

import common.MessageInfo;

public class UDPServer {

	private DatagramSocket recvSoc;
	private int totalMessages = -1;
	private int[] receivedMessages;
	private boolean close;

	private void run() {
		int				pacSize;
		byte[]			pacData;
		DatagramPacket 	pac;

		// TO-DO: Receive the messages and process them by calling processMessage(...).
		pacData = new byte[218];
		pacSize = pacData.length;
		pac = new DatagramPacket(pacData, pacSize);
		close=true;
		while(close){
			try {
				recvSoc.receive(pac);
				String data = new String(pac.getData(), 0, pac.getLength());
				processMessage(data);
			}
			catch(IOException e){
				System.err.println("Fail receiving message from the client");
			}
		}
		//        Use a timeout (e.g. 30 secs) to ensure the program doesn't block forever

		recvSoc.close();
	}

	public void processMessage(String data) {

		MessageInfo msg = null;
		System.out.println(data);

		// TO-DO: Use the data to construct a new MessageInfo object
		try {
			MessageInfo ourMessage = new MessageInfo(data) ;
				ourMessage.toString();

		// TO-DO: On receipt of first message, initialise the receive buffer
			if(ourMessage.messageNum == 1){
				totalMessages = ourMessage.totalMessages;
				receivedMessages = new int[totalMessages];
			}

		// TO-DO: Log receipt of the message
			receivedMessages[ourMessage.messageNum-1] = ourMessage.messageNum;
			ourMessage.toString();
		} catch (Exception e){
			System.err.println("Fail receiving message from the client");
		}
		// TO-DO: If this is the last expected message, then identify
		//        any missing messages

	}


	public UDPServer(int rp) {
		// TO-DO: Initialise UDP socket for receiving data
		try {
			this.recvSoc = new DatagramSocket(rp);
		}
		catch(IOException e){
				  System.err.println("Could not create a datagram socket.");
		}
		// Done Initialisation
		System.out.println("UDPServer ready");
	}

	public static void main(String args[]) {
		int	recvPort;

		// Get the parameters from command line
		if (args.length < 1) {
			System.err.println("Arguments required: recv port");
			System.exit(-1);
		}
		recvPort = Integer.parseInt(args[0]);

		// TO-DO: Construct Server object and start it by calling run().
		UDPServer oneUDPserver = new UDPServer(recvPort);
		oneUDPserver.run();

	}

}
