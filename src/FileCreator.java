import java.io.File;
import java.io.FileWriter;
import java.security.CodeSource;

/**
 * @Author: Brandon
 * Date: 4/5/13
 * Time: 12:35 PM
 */
public class FileCreator {
    private static File folderDirectory;
    private static File streamList;
    private static File readme;
    private static File bat;

    public static File getDesktopDirectory() {
        return desktopDirectory;
    }

    private static File desktopDirectory;

    public FileCreator(String directory) {
        desktopDirectory = new File(System.getProperty("user.home") + "\\"+"Desktop" + "BackupStreamAlive.txt");
        folderDirectory = new File(directory);
        streamList = new File(directory + File.separator + "StreamAliveData.txt");
        readme = new File(directory + File.separator + "Readme.txt");
        bat = new File(directory + File.separator + "RunStreamAlive.bat");
        System.out.println("Write directory: " +directory + File.separator + "StreamAliveData.txt");
    }

    /**
     * Creates a bat file the user can put in their Startup folder. Windows only!
     */
    public static boolean createBat() {
        try {
            CodeSource codeSource = Main.class.getProtectionDomain().getCodeSource();
            File jarFile = new File(codeSource.getLocation().toURI().getPath());
            String jarDir = jarFile.getParentFile().getPath() +File.separator;
            FileWriter fileWriter = new FileWriter(bat);
            fileWriter.write("@echo off\r\n");
            //Start beforehand ensures the closure of the cmd.
            fileWriter.write("start javaw -jar " +jarDir  +new java.io.File(Main.class.getProtectionDomain().getCodeSource().getLocation().getPath()).getName() +"\r\n");
            fileWriter.close();
        }  catch(Exception e) {
            System.out.println(e);
        }
        return bat.exists();
    }

    public static File getFolderDirectory() {
        return folderDirectory;
    }

    public static boolean createDirectory() {
        return !folderDirectory.exists() ? folderDirectory.mkdir() : folderDirectory.exists();
    }

    public static File getStreamListFile() {
        return streamList;
    }

    public static File getReadme() {
        return readme;
    }

    public static boolean createReadMe() {
        //automatically writes over the file. Why waste time seeing if they're the same size
        try {
            FileWriter fileWriter = new FileWriter(readme);
            fileWriter.write("This is pretty much the instructions file for everything related" +
                    "to Stream Alive, a program that monitors streams for Twitch.tv and Azubu.tv\r\n");
            fileWriter.write("\r\n");
            fileWriter.write("File Explanations:\r\n");
            fileWriter.write("\r\n");
            fileWriter.write("1. StreamAliveData.txt: The list of all added streams and settings for the system tray\r\n");
            fileWriter.write("\r\n");
            fileWriter.write("2. RunStreamAlive.bat: Put this file in your windows startup folder and " +
                    "it will run the jar from the current location. This file is created everytime the jar " +
                    "is ran. So if you move the jar, just rerun it and replace the bat file in your startup" +
                    "folder. The startup folder is usually located at (replace YOUR_NAME): C:\\Users\\YOUR_NAME\\AppData\\Roaming\\Microsoft\\Windows\\Start Menu\\Programs\\Startup \r\n");
            fileWriter.write("\r\n");
            fileWriter.write("Bugs:\r\n");
            fileWriter.write("\r\n");
            fileWriter.write("1. Newer streamers on twitch who don't save their videos give the script errors." +
                    "These people will always be at the bottom of the script list and will not be alphabetized " +
                    "with the rest. \r\n");

            fileWriter.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return readme.exists();
    }

    public static File getBat() {
        return bat;
    }
}
