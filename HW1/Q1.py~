"""

Measurement topology for CS640, Spring 2017, Assignment 1
"""

from mininet.cli import CLI
from mininet.net import Mininet
from mininet.link import TCLink
from mininet.topo import Topo
from mininet.log import setLogLevel

class AssignmentNetworks(Topo):
    def __init__(self, **opts):
        Topo.__init__(self, **opts)
        h1 = self.addHost('h1')
        h2 = self.addHost('h2')
        h3 = self.addHost('h3')
        h4 = self.addHost('h4')
        h5 = self.addHost('h5')
        h6 = self.addHost('h6')
        h7 = self.addHost('h7')
        h8 = self.addHost('h8')
        h9 = self.addHost('h9')
        h10 = self.addHost('h10')
        s1 = self.addSwitch('s1')
        s2 = self.addSwitch('s2')
        s3 = self.addSwitch('s3')
        s4 = self.addSwitch('s4')
        s5 = self.addSwitch('s5')
        s6 = self.addSwitch('s6')
        self.addLink(h1, s1)
        self.addLink(h7, s1)
        self.addLink(h8, s1)
        self.addLink(h2, s2)
        self.addLink(h3, s3)
        self.addLink(h4, s4)
        self.addLink(h9, s4)
        self.addLink(h10, s4)
        self.addLink(h5, s5)
        self.addLink(h6, s6)
        self.addLink(s1, s2, bw=20, delay='40ms')
        self.addLink(s2, s3, bw=40, delay='10ms')
        self.addLink(s3, s4, bw=30, delay='30ms')
        self.addLink(s2, s5, bw=25, delay='5ms')
        self.addLink(s3, s6, bw=25, delay='5ms')
        
        
if __name__ == '__main__':
    setLogLevel( 'info' )
    
    # Create data network
    topo = AssignmentNetworks()
    net = Mininet(topo=topo, link=TCLink,autoSetMacs=True,
           autoStaticArp=True)

    # Run network
    net.start()
    h1 = net.getNodeByName("h1")
    h2 = net.getNodeByName("h2")
    h3 = net.getNodeByName("h3")
    h4 = net.getNodeByName("h4")
    h4 = net.getNodeByName("h4")
    h5 = net.getNodeByName("h5")
    h6 = net.getNodeByName("h6")
    h7 = net.getNodeByName("h7")
    h8 = net.getNodeByName("h8")
    h9 = net.getNodeByName("h9")
    h10 = net.getNodeByName("h10")
    res1=h1.cmd("ping 10.0.0.2 -c 20")
    res2=h2.cmd("ping 10.0.0.3 -c 20")
    res3=h3.cmd("ping 10.0.0.4 -c 20")
    res4=h3.cmd("ping 10.0.0.6 -c 20")
    res5=h2.cmd("ping 10.0.0.5 -c 20")
    if os.path.isdir('Q1')==False:
	os.mkdir('Q1')
    f1=open('Q1/latency_L1.txt','w')
    f2=open('Q1/latency_L2.txt','w')
    f3=open('Q1/latency_L3.txt','w')
    f4=open('Q1/latency_L4.txt','w')
    f5=open('Q1/latency_L5.txt','w')
    f1.write(res1)
    f2.write(res2) 
    f3.write(res3)
    f4.write(res4)
    f5.write(res5)
     #CLI( net )
    net.stop()
