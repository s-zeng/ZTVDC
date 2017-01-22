package zergtel.core;


import zergtel.UI.ComputerUI;
import zergtel.core.downloader.EzHttp;

import java.io.File;

public class Main {
	public static ComputerUI ui;
	//main method which runs our program (as seen by the (String[] args))
	public static void main(String[] args) {
		//runs ComputerUI
		ui = new ComputerUI();
		//clears temp folder when user closes JFrame
		Runtime.getRuntime().addShutdownHook(new Thread() {
			@Override
			public void run() {
				File temp = new File(EzHttp.TEMP_LOCATION);
				for (File file : temp.listFiles()) {
					file.delete();
				}
			}
		});
	}
}
