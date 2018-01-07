package common;

public abstract class Protocol {
	
	public static final int COMMAND_PORT = 9896;
	public static final int MESSAGE_PORT = 9895;
	public static final int CAPTURE_PORT = 9894;

	public static final int RQ_CONNECT_ADMIN = 101;
	public static final int RQ_DISCONNECT_ADMIN = 104;
	public static final int RQ_CONNECT=102;
	public static final int RQ_DISCONNECT=103;
	public static final int RQ_CLIENTS=135;
	public static final int RQ_APPS = 140;
	public static final int RQ_INSTALL = 145;
	public static final int RQ_CONTROL = 150;
	public static final int RQ_STOP_CONTROL = 155;
	public static final int RQ_DIR_NAMES = 160;
	
	
	public static final int RP_OK=10;
	public static final int RP_KO=20;
	public static final int RP_FILE=30;
	public static final int RP_CLIENTS=35;
	public static final int RP_DIR=40;
	public static final int RP_APPS = 45;
	public static final int RP_INFO = 50;
	public static final int RP_INSTALL = 55;
	public static final int RP_CONTROL = 60;
	public static final int RP_DIR_NAMES = 65;

	public static final int PACKET_SIZE = (int)Math.pow(2, 20);
	
	
	public static String IPSERV;
	public static String DEST_DIR;

}
