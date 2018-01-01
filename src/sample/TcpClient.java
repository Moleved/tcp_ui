package sample;

import com.company.Schedule;

import java.io.*;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.ArrayList;

public class TcpClient
{
    public final static String SERVER_HOSTNAME = "localhost";
    public final static int COMM_PORT = 5050;

    InputStream iStream = null;
    ObjectInputStream oiStream = null;

    private Socket socket;

    public TcpClient(ArrayList<Schedule> list)
    {
        try
        {
            this.socket = new Socket(SERVER_HOSTNAME, COMM_PORT);

            System.out.println("Connected");

            iStream = this.socket.getInputStream();

            receiveList(5, list);
        }
        catch (UnknownHostException uhe)
        {
            System.out.println("Don't know about host: " + SERVER_HOSTNAME);
            System.exit(1);
        }
        catch (IOException ioe)
        {
            System.out.println("Couldn't get I/O for the connection to: " +
                    SERVER_HOSTNAME + ":" + COMM_PORT);
            System.exit(1);
        }
    }

    public void receiveList(int size, ArrayList<Schedule> list) {
        try {
            if (oiStream == null) oiStream = new ObjectInputStream(iStream);

            Schedule schedule = null;
            while((schedule = (Schedule) oiStream.readObject()) != null) {
                list.add(schedule);

                System.out.println("Received: Schedule: " + schedule.getId());

                if (list.size() == size) break;
            }
        } catch (ClassNotFoundException ex) {
            ex.printStackTrace();
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    public void sendObject(Schedule schedule) {
        try {
            OutputStream oStream = this.socket.getOutputStream();
            ObjectOutputStream ooStream = new ObjectOutputStream(oStream);
            ooStream.writeObject(schedule);
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

}
