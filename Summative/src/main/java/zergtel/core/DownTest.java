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
			String[] links = {"https://insaneintherainmusic.bandcamp.com/album/live-at-grillbys", "http://simonzeng.tk/example.mp3", "https://www.youtube.com/watch?v=f4yvZF1cMz0", "https://vimeo.com/29950141"};
			boolean success;

			for (int i = 0; i < links.length; i++) {
				success = Downloader.get(links[i]);
				if (success) {
					System.out.println("Download successful");
				} else {
					System.out.println("Download failed");
				}
			}

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
			 * 	-notably, replacing axet's wget library with my own http client
			 * error handling paradigm design
			 * imports cleanup
			 * filetype checking for raw http downloads (overall just more comprehensive url parsing)
			 * temp file handling and cleanup
			 * bandcamp additional quality/format detection
			 * automated testing
			 *
			 * ...and maybe then we can start on the ui :)
			 */

		} catch (Exception e) {
			e.printStackTrace();
		}

	}
}
