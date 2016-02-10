package be.nitroxis.tls;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.security.Security;
import javax.net.ssl.SSLServerSocket;
import javax.net.ssl.SSLServerSocketFactory;
import javax.net.ssl.SSLSocket;
import org.bouncycastle.jce.provider.BouncyCastleProvider;

public class Server {

  public static void main(final String[] args) throws Exception {    
    // Registering the Bouncy Castle provider
    Security.addProvider(new BouncyCastleProvider());

    // TODO load the keystore programatically
    
    //Specifying the Keystore details
    System.setProperty("javax.net.ssl.keyStore", "./src/main/resources/ic-keystore");
    
    // Hardcoding password in the code is a security mess - fix me
    System.setProperty("javax.net.ssl.keyStorePassword", "password");

	// Enable debugging to view the handshake and communication which happens between the SSLClient
    // and the SSLServer
    //System.setProperty("javax.net.debug","all");

    // Initialize the Server Socket
    SSLServerSocketFactory sslServerSocketfactory =
      (SSLServerSocketFactory) SSLServerSocketFactory.getDefault();
    SSLServerSocket sslServerSocket =
      (SSLServerSocket) sslServerSocketfactory.createServerSocket(4443);

    SSLSocket sslSocket = (SSLSocket) sslServerSocket.accept();

    // Create Input / Output Streams for communication with the client
    // Use try with resources for out and in - fix me
    PrintWriter out = new PrintWriter(sslSocket.getOutputStream(), true);
    BufferedReader in = new BufferedReader(new InputStreamReader(sslSocket.getInputStream()));
    String inputLine;

    while ((inputLine = in.readLine()) != null) {
      out.println(inputLine);
      System.out.println(inputLine);
    }

    // Close the streams and the socket
    out.close();
    in.close();
    sslSocket.close();
    sslServerSocket.close();
  }
}
