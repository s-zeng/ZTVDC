package zergtel.core;



import java.io.*;




public class Run {

	public static void main(String[] args) {
		File file = new File("file.mp4");
		try{
			Metadata.get(file);
		} catch (IOException e) {
			System.err.println("doh I missed");
		}
	}

}
