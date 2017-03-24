package admin;

import java.io.OutputStream;

import common.Protocol;
import common.Writer;


public class MessagesWriter extends Writer {

	public MessagesWriter(OutputStream outputStream) {
		super (outputStream);
	}

	public void connect(String name) {
		writeInt(Protocol.RQ_CONNECT_ADMIN);
		writeString(name);
	}

}
