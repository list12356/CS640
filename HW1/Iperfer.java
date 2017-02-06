import java.net.*;
import java.io.*;
import java.util.Calendar;
import java.util.Date;
import java.util.Timer;
import java.util.TimerTask;

public class Iperfer{
	public static void arg_err(){
		System.out.println("Error: missing or additional arguments");
	}
	public static void port_err(){
		System.out.println("Error: port number must be in the range 1024 to 65535");
	}
	public static void main(String[] args) throws IOException {
		if(args.length==0){
			arg_err();
			return;
		}
		if(args[0].equals("-c")){
			if(args.length!=7||!args[1].equals("-h")||!args[3].equals("-p")||!args[5].equals("-t")){
				arg_err();
				return;
			}
			String host=args[2];
			int portNumber = Integer.parseInt(args[4]);
			
			if(portNumber<1024||portNumber>65535){
				port_err();
				return;
			}
			
			int run_time=Integer.parseInt(args[6]);
			Socket clientSoc;
			OutputStram out;
			try{
				clientSoc=new Socket(host,portNumber);
				out = clientSoc.getOutputStream();
			}
			catch(Exception e){
				System.out.println(e.toString());
				return;
			}
			
			int count = 0;
			
			char[] data = new char[500];
			for(int i=0;i<500;i++){
				data[i] = 0;
			}
			
			long t1 = System.nanoTime();
			
			while(true){
				long t2=System.nanoTime();
				if((t2-t1)/1000000000.0>run_time){
					break;
				}
				out.write(data);
				out.flush();
				count=count+1;
			}
			try{
				clientSoc.close();
			}
			catch (Exception e){
				System.out.println(e.toString());
				return;
			}
			System.out.println("sent="+count+"KB rate="+count/run_time/125.0+"Mbps");

		}
		else if(args[0].equals("-s")){
			if(args.length!=3||!args[1].equals("-p")){
				arg_err();
				return;
			}
			int serverPort = Integer.parseInt(args[2]);
			/* 1. Create a ServerSocket that listens on the specified port */
			if(serverPort<1024||serverPort>65535){
				port_err();
				return;
			}
			ServerSocket serverSoc;
			Socket clientSoc;
			try{
				serverSoc=new ServerSocket(serverPort);			
				clientSoc=serverSoc.accept();
			}
			catch(Exception e){
				System.out.println(e.toString());
				return;
			}
			
			PrintWriter out=new PrintWriter(clientSoc.getOutputStream(),true);
			
			BufferedReader in=new BufferedReader(new InputStreamReader(clientSoc.getInputStream()));
			String text;
			int count=0;
			long t1 = System.nanoTime();
			while((text=in.readLine())!=null){
				count++;
			}
			long t2 = System.nanoTime();
			System.out.println("received="+count+"KB rate="+count/((t2-t1)/1000000000.0)/125+"Mbps");
		}
		else{
			arg_err();
			return;
		}
		return;
	}

}
