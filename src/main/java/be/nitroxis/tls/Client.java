package be.nitroxis.tls;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.security.Security;
import javax.net.ssl.SSLSocket;
import javax.net.ssl.SSLSocketFactory;
import org.bouncycastle.jce.provider.BouncyCastleProvider;

public class Client {

  public static void main(String[] args) throws Exception {
    PrintWriter out = null;
    BufferedReader in = null;

    System.setProperty("javax.net.ssl.trustStore", "./src/main/resources/ic-truststore");
    
    // Hardcoding password in the code is a security mess - fix me
    System.setProperty("javax.net.ssl.trustStorePassword", "password");
    
    // Registering the JSSE provider
    Security.addProvider(new BouncyCastleProvider());

    // Creating Client Sockets
    SSLSocketFactory sslsocketfactory = (SSLSocketFactory) SSLSocketFactory.getDefault();
    SSLSocket sslSocket = (SSLSocket) sslsocketfactory.createSocket("localhost", 4443);
    sslSocket.setEnabledProtocols(new String[] { "TLSv1.1", "TLSv1.2", });
    sslSocket.setEnabledCipherSuites(
      new String[] { "TLS_ECDHE_ECDSA_WITH_AES_128_CBC_SHA", "TLS_ECDHE_RSA_WITH_AES_128_CBC_SHA", }
    );

    // Initializing the streams for Communication with the Server
    out = new PrintWriter(sslSocket.getOutputStream(), true);
    in = new BufferedReader(new InputStreamReader(sslSocket.getInputStream()));

    BufferedReader stdIn = new BufferedReader(new InputStreamReader(System.in));
    String userInput = "Hello World with TLS";
    out.println(userInput);
    System.out.println("echo: " + in.readLine());

    // Closing the Streams and the Socket
    out.close();
    in.close();
    stdIn.close();
    sslSocket.close();
  }
}
