package zergtel.core;

import org.apache.log4j.BasicConfigurator;
import zergtel.core.io.Metadata;
import org.slf4j.LoggerFactory;

import java.io.*;




public class Run {

	public static void main(String[] args) {
		File file = new File("C:/Users/User/IdeaProjects/summative/Summative/bin/file.mp4");
		File file2 = new File("C:/Users/User/IdeaProjects/summative/Summative/bin/test.mp4");
		BasicConfigurator.configure();
		try{
			Metadata.transfer(file, file2);
		} catch (IOException e) {
			System.err.println("doh I missed");
		}
	}

}
