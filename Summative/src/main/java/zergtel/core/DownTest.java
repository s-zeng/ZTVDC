package zergtel.core;

import zergtel.core.downloader.Bandcamp;
import zergtel.core.downloader.Downloader;
import zergtel.core.downloader.EzHttp;

/**
 * Created by Simon on 11/4/2016.
 */
public class DownTest {
	public static void main(String[] args) {
		try {
//			Downloader.get("https://insaneintherainmusic.bandcamp.com/album/live-at-grillbys");
//			Downloader.get("http://simonzeng.tk/example.mp3");
//			Downloader.get("https://www.youtube.com/watch?v=f4yvZF1cMz0");
			Downloader.get("https://vimeo.com/29950141"); //vimeo not working right now - due to relatively recent https change unsupported by vget, will have to manually change

			/**
			 * things left to implement (caps means emphasis):
			 * design of extension system
			 *  -this extension system must:
			 *      -be versatile enough for most sites
			 *      -be secure (NOT ALLOW ARBITRARY CODE EXECUTION)
			 *      -allow drag and drop functionality of adding extensions
			 * THREADING
			 * proper file location/name handling
			 * vimeo fix, integration of vget library for greater changes
			 * error handling paradigm design
			 * imports cleanup
			 * filetype checking for raw http downloads (overall just more comprehensive url parsing)
			 * temp file handling and cleanup
			 * bandcamp additional quality/format detection
			 *
			 * ...and maybe then we can start on the ui :)
			 */

		} catch (Exception e) {
			e.printStackTrace();
		}

	}
}
