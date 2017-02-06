/*

 * Created on 01-Mar-2016

 */

package udp;



import java.io.IOException;

import java.net.DatagramPacket;

import java.net.DatagramSocket;

import java.net.InetAddress;

import java.net.SocketException;

import java.net.UnknownHostException;



import common.MessageInfo;



public class UDPClient {



	private DatagramSocket sendSoc;



	public static void main(String[] args) {

		InetAddress	serverAddr = null; // this is from java datagram class

		int			recvPort;

		int 		countTo;

		String 		message;



		// Get the parameters

		if (args.length < 3) { // args in from command line

			System.err.println("Arguments required: server name/IP, recv port, message count");

			System.exit(-1);

		}



		try {

			serverAddr = InetAddress.getByName(args[0]); //Determines the IP address of a host, given the host's name.

		} catch (UnknownHostException e) {

			System.out.println("Bad server address in UDPClient, " + args[0] + " caused an unknown host exception " + e);

			System.exit(-1);

		}

		recvPort = Integer.parseInt(args[1]);

		countTo = Integer.parseInt(args[2]);





		// TO-DO: Construct UDP client class and try to send messages







		int tries =0;

		while(tries < countTo){



			String numberandmess = "first"; //replace with number increasing

			sendSoc.send(numberandmess,serverAddr,recvPort);

			tries++;



		}









	}



	public UDPClient(int recvPort) {

		// TO-DO: Initialise the UDP socket for sending data



		// get a datagram socket

        sendSoc = new DatagramSocket(recvPort);











	}



	private void testLoop(InetAddress serverAddr, int recvPort, int countTo) {

		int				tries = 0;

	//why do we need this???

		/*

		while(tries < countTo){

					// TO-DO: Send the messages to the server



			String numberandmess = "first";

			send sending = new send(numberandmess,serverAddr,recvPort);

			tries++;



		}

		*/











	}



	private void send(String payload, InetAddress destAddr, int destPort) {

		int				payloadSize =0;

		byte[]				pktData;

		DatagramPacket		pkt;



		// TO-DO: build the datagram packet and send it to the server

		for(int i=0; i < payload.length();i++){

			pktData[i]=(byte)payload;

			payloadSize++;

		}





		pkt = new DatagramPacket(pktData, payloadSize, destAddr, destPort);

		sendSoc.send(pkt);



	}

}
