package webmining.helpers;

import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.util.Collection;
import java.util.Vector;

public class File {
	public static String read(String path) {
		String result = "";
		for (String line : readLines(path))
			result += line + System.getProperty("line.separator");

		return result;
	}

	public static Collection<String> readLines(String path) {
		try {
			DataInputStream instream = new DataInputStream(new FileInputStream(
					path));
			BufferedReader rdr = new BufferedReader(new InputStreamReader(
					instream));

			Vector<String> lines = new Vector<String>();

			String line = null;
			while ((line = rdr.readLine()) != null) {
				lines.add(line);
			}
			instream.close();
			return lines;
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}
}
