package edu.wisc.cs.sdn.vnet.sw;

import net.floodlightcontroller.packet.Ethernet;
import net.floodlightcontroller.packet.MACAddress;
import edu.wisc.cs.sdn.vnet.Device;
import edu.wisc.cs.sdn.vnet.DumpFile;
import edu.wisc.cs.sdn.vnet.Iface;
import java.util.*;

/**
 * @author Aaron Gember-Jacobson
 */
public class Switch extends Device
{	
	/**
	 * Creates a router for a specific host.
	 * @param host hostname for the router
	 */
	public Switch(String host, DumpFile logfile)
	{
		super(host,logfile);
		macTable=new MacTable();
	}
	/**
	 * Handle an Ethernet packet received on a specific interface.
	 * @param etherPacket the Ethernet packet that was received
	 * @param inIface the interface on which the packet was received
	 */
	public void handlePacket(Ethernet etherPacket, Iface inIface)
	{
		System.out.println("*** -> Received packet: " +
                etherPacket.toString().replace("\n", "\n\t"));
		
		/********************************************************************/
		/* TODO: Handle packets                                             */
		MACAddress cur_dst=etherPacket.getDestinationMAC();
		MACAddress cur_src=etherPacket.getSourceMAC();

		macTable.add(cur_src,inIface);				

		int i=macTable.find(cur_dst);		
		if(i!=-1){
			sendPacket(etherPacket,macTable.mTable.get(i).next_hop);
			return;	
		}
		    Iterator it = interfaces.entrySet().iterator();
		    while (it.hasNext()) {
			Map.Entry<String,Iface> pair = (Map.Entry<String,Iface>)it.next();
			sendPacket(etherPacket,pair.getValue());
		    }
		/********************************************************************/
	}
	
	MacTable macTable;
	public class MacTable{
		public class MacTableMember{
			MACAddress dst;
			Iface next_hop;
			long last_time;
			MacTableMember(MACAddress addr, Iface iface){
				dst=addr;
				next_hop=iface;
				last_time=System.currentTimeMillis();				
			}	
			void update(MACAddress addr, Iface iface){
				dst=addr;
				next_hop=iface;
				last_time=System.currentTimeMillis();				
			}	
		}
		ArrayList<MacTableMember> mTable;
		
		void add(MACAddress addr, Iface iface){
			int find=find(addr);
			if(find!=-1){
				mTable.get(find).update(addr,iface);
				return;				
			}
			mTable.add(new MacTableMember(addr,iface));		
		}
		void delete(MACAddress addr){
			int find=find(addr);
			if(find!=-1){
				mTable.remove(find);
				return;			
			}		
		}
		int find(MACAddress addr){
			for(int i=0;i<mTable.size();i++){
				if(mTable.get(i).dst.equals(addr)){
					return i;
				}				
			}	
			return -1;			
		}
		void clean(){
			for(int i=0;i<mTable.size();i++){
				long cur_time=System.currentTimeMillis();
				if(cur_time-mTable.get(i).last_time>15*1000){
					mTable.remove(i);
					i--;
				}
			}		
		}
		MacTable(){
			mTable=new ArrayList<MacTableMember>();
		}	
	}
}
