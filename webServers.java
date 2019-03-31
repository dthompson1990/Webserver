//Darian Thompson
//March 21, 2017
//ITEC 371
import java.net.*;
import java.io.*;
import com.sun.net.httpserver.HttpServer;
import com.sun.net.httpserver.HttpContext;

public class webServers
{
      public static void main (String []args)
      {
         class Connection
         {
            BufferedReader in = null;
            DataOutputStream out = null;
            Socket socket;
            Connection (Socket socket) throws Exception
               {
                  in = new BufferedReader(new InputStreamReader(System.in));
                  out = new DataOutputStream(socket.getOutputStream());
                  socket = this.socket;
               }
            //reads information to be transfered through the server
            String getRequest() throws Exception
               {
                  String input = null;
                  while ( (input = in.readLine()) != null)
                  {
                     System.out.println(input);
                  }  
                  return input;
               }
         }
         // parameters for port to be read and folder to be read
         String destination = args[0];
         int port = Integer.parseInt(args[1]);
         webServer webserver = new webServer();
         try  
         {
            ServerSocket server = new ServerSocket(port);
            Socket connectSocket = server.accept();
            System.out.println("Client has connected to port: " + port);
            
            Connection connect = new Connection(connectSocket);
            String str = connect.getRequest();
         }
   
         catch (Exception e)
         {
            e.printStackTrace();
         }
   }
}