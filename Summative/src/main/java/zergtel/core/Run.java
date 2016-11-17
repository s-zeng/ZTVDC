package zergtel.core;

import org.apache.log4j.BasicConfigurator;
import zergtel.UI.ComputerUI;
import zergtel.core.converter.Converter;
import zergtel.core.io.Metadata;
import org.slf4j.LoggerFactory;
import zergtel.core.converter.*;

import java.io.*;

public class Run {

	public static void main(String[] args) {
		File f = new File("C:/Users/User/IdeaProjects/Summative/summative/bin/file.mp4");
		File f2 = new File("C:/Users/User/IdeaProjects/Summative/summative/bin/hi.mp3");
		Converter c = new Converter();
		c.merge(f, f2, "Test", "mp4");
				}
}
