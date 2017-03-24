package client;

import java.io.InputStream;

import common.Protocol;
import common.Reader;


public class CommandReader extends Reader {

	private boolean done=false;

	public CommandReader(InputStream inputStream) {
		super (inputStream);
	}

	public void receive() {
		type = readInt ();
		switch (type) {
		case Protocol.RP_OK:
			done=true;
			break;
		case Protocol.RP_KO:
			done=false;
			break;
		default:
			break;
		}
	}

	public boolean getDone() {
		return done;
	}

}
