package zergtel.core;


import zergtel.UI.ComputerUI;
import zergtel.core.downloader.EzHttp;

import java.io.File;

/**
 * The main class that calls upon and starts the UI
 * Honestly, this class should be in zergtel.ui and not zergtel.core, but we've never come around to actually fixing that.
 *
 * There are two things to note in this main class (with our implementation of the ui, at the very least)
 *   1. We put the temp directory clearing on exit code here rather than as part of the main ui - you may decide to do this
 *      differently if you wish.
 *   2. The ui class object is assigned to a public static variable - this is very important for our implementation of
 *      task cancelling (see zergtel.core.ComputerUI for more)
 */
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
