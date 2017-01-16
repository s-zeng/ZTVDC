package zergtel.core;


import zergtel.UI.ComputerUI;
import zergtel.core.downloader.EzHttp;

import java.io.File;

public class Main {

	public static void main(String[] args) {
		ComputerUI ui = new ComputerUI();
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
