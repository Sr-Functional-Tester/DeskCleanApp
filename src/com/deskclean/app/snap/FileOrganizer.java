package com.deskclean.app.snap;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.nio.file.attribute.BasicFileAttributes;
import java.text.SimpleDateFormat;
import java.util.Date;

public class FileOrganizer {

	public static void organizedBackup(String query) {
		String userHome = System.getProperty("user.home");
		Path desktopPath = Paths.get(userHome, "Desktop");
		String backupPath = FindPathUtility.backupPath(query);

		Path backupPathFolder;
		System.out.println(desktopPath);
		if (!backupPath.isEmpty()) {
			backupPathFolder = Paths.get(backupPath);
		} else
			backupPathFolder = desktopPath;

		String parentFolderPath = null;
		if (!backupPath.isEmpty())
			parentFolderPath = backupPathFolder + File.separator + "organized";
		else
			parentFolderPath = desktopPath.toString() + File.separator + "organized";
		// Organize the files and folders
		try {
			organizeFilesAndFoldersByDate(backupPathFolder, parentFolderPath);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public static void organizeFilesAndFoldersByDate(Path backupPathFolder, String parentFolderPath)
			throws IOException {
		// Create the parent folder if it doesn't exist
		File parentFolder = new File(parentFolderPath);
		if (!parentFolder.exists()) {
			parentFolder.mkdir();
		}
		System.out.println(backupPathFolder);
		File[] filesAndFolders = Files.list(backupPathFolder).filter(path -> !path.equals(backupPathFolder)) // Exclude
																												// the
																												// desktop
																												// folder
																												// itself
				.filter(path -> !path.equals(parentFolderPath)) // Exclude the backup folder
				.map(Path::toFile) // Convert Path to File
				.toArray(File[]::new); // Convert to File[] array

		if (filesAndFolders == null) {
			System.out.println("No files or folders found in the specified folder.");
			return;
		}

		// Loop through each file/folder and organize it
		for (File fileOrFolder : filesAndFolders) {
			if (fileOrFolder.isFile() || fileOrFolder.isDirectory()) {
				// Get the creation date of the file/folder
				Path path = fileOrFolder.toPath();
				BasicFileAttributes attrs = Files.readAttributes(path, BasicFileAttributes.class);
				long creationTimeMillis = attrs.creationTime().toMillis();
				Date creationDate = new Date(creationTimeMillis);

				// Get the year, month, and day from the creation date
				SimpleDateFormat yearFormat = new SimpleDateFormat("yyyy");
				SimpleDateFormat monthFormat = new SimpleDateFormat("MM");
				SimpleDateFormat dayFormat = new SimpleDateFormat("dd-MM-yyyy");

				String year = yearFormat.format(creationDate);
				String month = monthFormat.format(creationDate);
				String day = dayFormat.format(creationDate);

				// Create the year folder
				File yearFolder = new File(parentFolderPath + File.separator + year);
				if (!yearFolder.exists()) {
					yearFolder.mkdir();
				}

				// Create the month folder inside the year folder
				File monthFolder = new File(yearFolder + File.separator + month);
				if (!monthFolder.exists()) {
					monthFolder.mkdir();
				}

				// Create the day folder inside the month folder (formatted as dd-MM-yyyy)
				File dayFolder = new File(monthFolder + File.separator + day);
				if (!dayFolder.exists()) {
					dayFolder.mkdir();
				}

				// Prepare the destination path (the day folder inside the correct
				// year/month/day folder)
				Path destinationPath = Paths.get(dayFolder.toString(), fileOrFolder.getName());
				// Check if the parent folder is "organized" and skip moving the folder into
				// itself
				if (fileOrFolder.getName().equals("organized")) {
					System.out.println(
							"Skipping move for " + fileOrFolder.getName() + " as it's the parent 'organized' folder.");
					continue;
				}
				// Skip moving the folder if it's already in the destination path
				if (fileOrFolder.toPath().equals(destinationPath)) {
					System.out.println("Skipping move for " + fileOrFolder.getName()
							+ " as it's already in the target directory.");
					continue;
				}

				// Move the file or folder directly to the target folder
				Files.move(fileOrFolder.toPath(), destinationPath, StandardCopyOption.REPLACE_EXISTING);
				// System.out.println("Moved " + (fileOrFolder.isDirectory() ? "folder" :
				// "file") + ": " + fileOrFolder.getName() + " to " + destinationPath);
			}
		}
	}
}