package zergtel.core.downloader;

import java.io.*;
import java.net.*;


public class Http {
	public static File get(String uri) throws Exception {
		return get(new URL(uri));
	}
	
	public static File get(URL uri) throws Exception {
		File x = null;
		return x;
	}
}
