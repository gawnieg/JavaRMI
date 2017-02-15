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

		try {
			recvSoc.setSoTimeout(10000); // this looks after the timeout
			while(true){ // this is not correct just for testing!


				recvSoc.receive(pac);
				String data = new String(pac.getData(), 0, pac.getLength());
				//System.out.println(data);
				processMessage(data);
			}
		}
		catch(IOException e){
			System.err.println("Fail receiving message from the client");
		}
		//        Use a timeout (e.g. 30 secs) to ensure the program doesn't block forever
		//looked after above!!!!


		recvSoc.close();
	}

	public void processMessage(String data) {
		try{
			MessageInfo msg = new MessageInfo(data);
			System.out.print("Processed Message and Found: ");
			System.out.println(msg.toString());
			//if first then create storage Arrays
			if(msg.messageNum==1){
				//create array of messages
				//MessageInfo [] msg_storage = new MessageInfo(msg.totalMessages);
				// String [] msg_storage = new String [msg.totalMessages];
				// msg_storage[0]=Integer.toString(msg.messageNum);


				//how do you intialise the size from here???
				//int receivedMessages [] = new receivedMessages(msg.totalMessages);

			}
			totalMessages=totalMessages+1;
			System.out.println(totalMessages);
			//got to use this due to scope reasons
			receivedMessages[totalMessages]=msg.messageNum;
			//this is giving a null pointer as I do not know how to work java

			//if is last message
			// if(msg.messageNum==(msg.totalMessages-1)){
			// 	for(int j =0; j < msg.totalMessages; j++){
			// 		if(msg_storage[j] != j){
			// 			System.out.print("Missing ");
			// 			System.out.print(j);
			// 			System.out.print("from recieved");
			// 		}
			// 	}
			// }




		}
		catch(Exception e){
			System.out.print("Processing messaged caused");
			System.out.println(e);

		}

		// TO-DO: Use the data to construct a new MessageInfo object
		//done
		// TO-DO: On receipt of first message, initialise the receive buffer
		//think its done?
		// TO-DO: Log receipt of the message

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
