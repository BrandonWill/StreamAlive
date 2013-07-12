import javax.swing.*;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.ArrayList;

/**
 * @author Brandon/Dwarfeh
 * @version 1.0
 */

public class Main {
    public static GUI gui;


    public static void main(String[] args) throws Exception {
        if (startup()) {
            new StreamHandler();
            new SystemTrayIcon();
            gui = new GUI();
            gui.setVisible(true);
            TwitchStreamHandler twitchStream = new TwitchStreamHandler();
            AzubuStreamHandler azubuStream = new AzubuStreamHandler();
            while (true) {
                if (gui.getExtendedState() == 1) {
                    gui.setVisible(false);
                }

                try {
                    Thread.sleep(200);
                } catch (InterruptedException ignored) { }

                if (!twitchStream.isAlive() || twitchStream.getForceStop()) {
                    System.out.println("Restarting twitch streams!");
                    twitchStream.interrupt();
                    twitchStream = new TwitchStreamHandler();
                    try {
                        Thread.sleep(400);
                    } catch (Exception ignored) {}
                }
                if (!azubuStream.isAlive() || azubuStream.getForceStop()) {
                    System.out.println("Restarting azubu streams!");
                    azubuStream.interrupt();
                    azubuStream = new AzubuStreamHandler();
                    try {
                        Thread.sleep(400);
                    } catch (Exception ignored) {}
                }


            }
        }
    }

    private static class TwitchStreamHandler extends Thread {
        private boolean forceStop = false;

        public TwitchStreamHandler() {
            start();
        }

        @Override
        public void run() {
            while (GUI.getIsRunning() && !forceStop) {
                try {
                    if (GUI.jTextField1.getText().isEmpty() || GUI.jTextField1.getText().equalsIgnoreCase("Search")) {
                        java.awt.EventQueue.invokeLater(new Runnable() {
                            public void run() {
                                int index = GUI.jList1.getSelectedIndex();
                                GUI.jList1.setListData(new String[0]);
                                GUI.jList1.setListData(GUI.getTwitchStreamList());
                                GUI.jList1.setSelectedIndex(index);
                            }
                        });


                    } else {

                        java.awt.EventQueue.invokeLater(new Runnable() {
                            public void run() {
                                GUI.jList1.setListData(new String[0]);
                                String[] streamList = GUI.getTwitchStreamList();
                                ArrayList<String> finalList = new ArrayList<>();
                                for (String streamHas : streamList) {
                                    if (streamHas.toLowerCase().contains(GUI.jTextField1.getText().toLowerCase())) {
                                        finalList.add(streamHas);
                                    }
                                }
                                GUI.jList1.setListData(finalList.toArray(new String[finalList.size()]));
                                System.out.println("I'm adding the list!");
                            }
                        });


                    }

                    try {
                        Thread.sleep(500);
                    } catch (Exception e) {
                    e.printStackTrace();
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                    forceStop = true;
                }

            }
            forceStop = true;
        }

        public boolean getForceStop() {
            return forceStop;
        }
    }

    private static class AzubuStreamHandler extends Thread {
        private boolean forceStop = false;

        public AzubuStreamHandler() {
            start();
        }

        @Override
        public void run() {
            while (GUI.getIsRunning() && !forceStop) {
                try {
                    if (GUI.jTextField2.getText().isEmpty() || GUI.jTextField2.getText().equalsIgnoreCase("Search")) {
                        java.awt.EventQueue.invokeLater(new Runnable() {
                            public void run() {
                                int index = GUI.jList2.getSelectedIndex();
                                GUI.jList2.setListData(new String[0]);
                                GUI.jList2.setListData(GUI.getAzubuStreamList());
                                GUI.jList2.setSelectedIndex(index);
                            }
                        });


                    } else {
                        java.awt.EventQueue.invokeLater(new Runnable() {
                            public void run() {
                                GUI.jList2.setListData(new String[0]);
                                String[] streamList = GUI.getAzubuStreamList();
                                ArrayList<String> finalList = new ArrayList<>();
                                for (String streamHas : streamList) {
                                    if (streamHas.toLowerCase().contains(GUI.jTextField2.getText().toLowerCase())) {
                                        finalList.add(streamHas);
                                    }
                                }
                                GUI.jList2.setListData(finalList.toArray(new String[finalList.size()]));
                            }
                        });
                    }


                    try {
                        Thread.sleep(500);
                    } catch (Exception ignored) {}
                } catch (Exception e) {
                    e.printStackTrace();
                    forceStop = true;
                    this.interrupt();
                }
            }
            forceStop = true;
        }

        public boolean getForceStop() {
            return forceStop;
        }
    }

    private static class StreamHandler extends Thread {

        public StreamHandler() {
            start();
        }

        @Override
        public void run() {
            try {
                File fileDirectory = FileCreator.getStreamListFile();
                FileReader fileReader = new FileReader(fileDirectory);
                BufferedReader br = new BufferedReader(fileReader);
                String text;
                boolean twitchStream = false;
                boolean azubuStream = false;
                while ((text = br.readLine()) != null) {
                    if (twitchStream && !text.equals("Twitch Streams") && !text.equals("Azubu Streams") && text.length() > 0) {
                        String[] streamData = text.split(" ");
                        System.out.println("Starting twitch stream: " +streamData[0] + "  " +streamData[1].replace("_", " "));
                        new TwitchStream(streamData[0],  streamData[1].replace("_", " "));
                    }
                    if (azubuStream && !text.equals("Twitch Streams") && !text.equals("Azubu Streams") && text.length() > 0) {
                        String[] streamData = text.split(" ");
                        System.out.println("Starting azubu stream: " +streamData[0] + "  " +streamData[1].replace("_", " "));
                        new AzubuStream(streamData[0],  streamData[1].replace("_", " "));
                    }
                    if (text.equals("Twitch Streams")) {
                        twitchStream = true;
                    }
                    if (text.equals("Azubu Streams")) {
                        azubuStream = true;
                        twitchStream = false;
                    }
                }

            }    catch (Exception e) {
                e.printStackTrace();
                System.out.println("Cant find file!");
                java.awt.EventQueue.invokeLater(new Runnable() {
                    public void run() {
                        JOptionPane.showMessageDialog(null, "Can't find stream list! If this is your first time running please ignore. \n" +
                                "All files related to this program are located at: " +FileCreator.getFolderDirectory());

                    }
                });
            }
        }
    }

    private static boolean startup() throws Exception {
        new FileCreator(System.getProperty("user.home") + File.separator + "Documents" + File.separator + "StreamAlive");
        System.out.println(System.getProperty("user.home")+ File.separator+"Documents");
        if (FileCreator.createDirectory() && FileCreator.createReadMe() && FileCreator.createBat()) {
            return true;
        }
        throw new Exception("Can't start!");
    }
}
