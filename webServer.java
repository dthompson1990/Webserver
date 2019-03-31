//Darian Thompson
//Project 4j
//ITEC 371
//April 20, 2017
import java.net.*;
import java.io.*;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.*;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantLock;
import com.sun.net.httpserver.HttpServer;
import com.sun.net.httpserver.HttpContext;
import java.util.Scanner; 

public class webServer implements Runnable
{    
      public void run()
      {
         webConnection();
      }
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
      //establishes connect to specified port and request location
      public void webConnection ()
         {
            Lock lock = new ReentrantLock();  
            webServer webserver = new webServer();
            try  
            {
               FileWriter write = new FileWriter("log.txt");
               BufferedWriter writ = new BufferedWriter(write);
               ServerSocket server = new ServerSocket(port);
               Socket connectSocket = server.accept();
               System.out.println("Client has connected to port: " + port);
               
               Connection connect = new Connection(connectSocket);
               String str = connect.getRequest();
               //code to ensure multiple files are not trying to write to file at same time
               try
               {
                  if (lock.tryLock(1000, TimeUnit.MILLISECONDS))
                     writ.write("Connected established on port: " + port + " at " + destination + ".");              
               }
               catch (InterruptedException er)
               {
                  er.printStackTrace();
               }
               finally
               {
                  lock.unlock();
               }
            }
            catch (BindException e)
            {
               System.out.println("Port is already in use.");
               e.printStackTrace();
            }
            catch (IOException e)
            {
               e.printStackTrace();
            }
            catch (Exception e)
            {
               e.printStackTrace();
            }
         }
      public static void main (String []args)
      {
         boolean end = true;

         //loop that allows input of port destination and port number.
         while (end)
         {
            Runnable r = new webServer();
            Thread t = new Thread(r);
            Scanner input = new Scanner(System.in);
            System.out.println("Enter the destination for the request.");
            destination = input.nextLine();
            System.out.println("Enter a port number.");
            port = input.nextInt();
            t.start();
         }
      }
         private static String destination;
         private static int port;
}