package client;

import java.io.OutputStream;

import common.Protocol;
import common.Writer;

public class CommandWriter extends Writer {

	public CommandWriter(OutputStream outputStream) {
		super (outputStream);
	}

	public void connect(String name) {
		writeInt(Protocol.RQ_CONNECT);
		writeString(name);
	}
	
	public void disconnect() {
		writeInt(Protocol.RQ_DISCONNECT);
	}
	
}
