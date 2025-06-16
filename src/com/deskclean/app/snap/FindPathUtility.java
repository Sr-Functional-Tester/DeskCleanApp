package com.deskclean.app.snap;

public class FindPathUtility {
	
	public static String backupPath(String query) {
		int index = query.toLowerCase().indexOf("path");

		// Variable to store the backup path
		String backupPath = "";

		if (index != -1) {
			// Find the substring that comes after the keyword "path"
			String pathPart = query.substring(index + 4).trim(); // Skip the keyword "path" and trim any spaces

			// If the path part is not empty, assign it to backupPath
			if (!pathPart.isEmpty()) {
				backupPath = pathPart;
				System.out.println(backupPath);
			}
		}
		return backupPath;
	}

}
