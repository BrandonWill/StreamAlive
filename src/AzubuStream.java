import java.awt.*;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.ArrayList;

/**
 * @author Brandon
 * Date: 3/27/13
 * Time: 2:54 PM
 */
public class AzubuStream {
    public static final String STREAM_URL = "http://beta.azubu.tv/";
    public static final String STREAM_ALIVE_CHECK = "<div id=\"pulsar\"><div id=\"channel_live\">&nbsp;LIVE&nbsp;</div></div>";
    private static volatile ArrayList<String> streamList = new ArrayList<>();
    private static volatile ArrayList<String> streamName = new ArrayList<>();
    private static volatile ArrayList<Boolean> streamAlive = new ArrayList<>();
    private static volatile int listLocation = 0;



    /**
     * In order to check if someone is live, there is an image given to their stream that says "Live"
     * We check to see if this image is there to determine whether or not they are live
     * Script should only need to be updated if that changes
     */

    //TODO remove
    public AzubuStream() {
        addStream("riotgames", "Riot Games");

        areStreamsUp();
    }

    public AzubuStream(String streamID, String streamerName) {
        addStream(streamID, streamerName);
    }

    public void addStream(String streamID, String streamerName) {
        streamList.add(listLocation, streamID);
        streamName.add(listLocation, streamerName);
        listLocation++;
    }

    public static String getStreamID(String streamerName) {
        for (int i = 0 ; i < streamName.size(); i++) {
            if (streamName.get(i).equalsIgnoreCase(streamerName)) {
                return streamList.get(i);
            }
        }
        return null;
    }

    public static void removeStream(String streamID, String streamerName) {
        try {
            streamList.remove(streamID);
            streamName.remove(streamerName);
            listLocation--;
        } catch (Exception ignored) {}
    }

    public static void removeStream(String streamerName) {
        try {
            for(int i =0; i < streamName.size(); i++) {
                if (streamName.get(i).equalsIgnoreCase(streamerName)) {
                    if (currentlyOnline.contains(streamList.get(i))) {
                        currentlyOnline.remove(streamList.get(i));
                    }
                    streamList.remove(i);
                    streamName.remove(i);
                    streamAlive.remove(i);
                    listLocation--;

                    break;
                }
            }
        } catch (Exception ignored) {}
    }

    public static void list() {
        for (String element: streamList) {
            System.out.println(element);
        }
    }

    //don't want to have a bunch of threads checking on 1 stream
    public static int threadCount = 0;
    public void areStreamsUp() {
        if (!streamList.isEmpty()) {
            for (;threadCount < streamList.size(); threadCount++) {
                new StreamIsUp(streamList.get(threadCount), threadCount, streamName.get(threadCount)).start();
            }
        }
    }

    public static void restartStreams() {
        threadCount = 0;
        SystemTrayIcon.setStreamNotifications(false);
        GUI.isRunning = false;
//        streamAlive.clear();
        currentlyOnline.clear();
        try {
            Thread.sleep(1000);
        } catch (InterruptedException ignored) {}
        new TwitchStream().areStreamsUp();
        GUI.isRunning = true;
        SystemTrayIcon.setStreamNotifications(true);
    }

    static ArrayList<String> currentlyOnline = new ArrayList<>();
    private class StreamIsUp extends Thread {
        private String streamID;
        private String streamerName;
        private int arrayLocation;

        public StreamIsUp(String id, int location, String name) {
            streamID = id;
            arrayLocation = location;
            streamerName = name;
        }

        /**
         * Updates the array size
         */
        private void fixArray() {
            if (streamAlive.size() < streamName.size()) {
                streamAlive.add(false);
                fixArray();
            }

            if (streamAlive.size() > streamName.size()) {
                streamAlive.clear();
                fixArray();
            }
        }

        @Override
        public void run() {
            while (GUI.getIsRunning()) {
                fixArray();
                if (!streamName.contains(streamerName)) {
                    interrupt();
                }
                try {
                    final URL url = new URL(STREAM_URL + streamID);
                    final BufferedReader br = new BufferedReader(new InputStreamReader(url.openStream()));
                    String input;
                    boolean ensureDown = false;
                    while ((input = br.readLine()) != null) {
                        if (input.contains(STREAM_ALIVE_CHECK)) {
                            fixArray();
                            streamAlive.set(arrayLocation, true);
                            if (!currentlyOnline.contains(streamID)) {
//                                SimpleDateFormat timingFormat = new SimpleDateFormat("hh:mm");
//                                timingFormat.format(new Date());
//                                System.out.println(getTime(timingFormat.getCalendar().getTime().toString()) + " Adding " +streamerName + " to online list!");
                                currentlyOnline.add(streamID);
                                if (SystemTrayIcon.getStreamNotifications())
                                    SystemTrayIcon.trayIcon.displayMessage("LoL Stream List", streamerName + " just went live!", TrayIcon.MessageType.INFO);
                            }
                            ensureDown = true;
                        }
                    }
                    if (!ensureDown) {
                        fixArray();
                        streamAlive.set(arrayLocation, false);
                        if (currentlyOnline.contains(streamID)) {
//                            SimpleDateFormat timingFormat = new SimpleDateFormat("hh:mm");
//                            timingFormat.format(new Date());
//                            System.out.println(getTime(timingFormat.getCalendar().getTime().toString()) + " Removing " +streamerName + " from online list!");
                            currentlyOnline.remove(streamID);
                            if (SystemTrayIcon.getStreamNotifications())
                                SystemTrayIcon.trayIcon.displayMessage("LoL Stream List", streamerName + " went offline", TrayIcon.MessageType.INFO);
                        }
                    }
                    br.close();
                } catch (final IOException e) {
                    if (SystemTrayIcon.getErrorNotifications() && !e.getMessage().contains("503") && streamName.contains(streamerName)) {
                        SystemTrayIcon.trayIcon.displayMessage("Azubu: ERROR!", streamerName + " had an error while obtaining info!\n" +
                            "Make sure stream information is correct! May be a false if it doesn't repeat!\n" +
                            "Remove stream to fix this problem if it does repeat!", TrayIcon.MessageType.ERROR);
                    }
                    if (!streamName.contains(streamerName)) {
                        interrupt();
                    }
                }

                try {
                    Thread.sleep(timeInMinutes(1));
                } catch(Exception ignored) {}
            }
        }
    }



    public static ArrayList<String> getStreamList() {
        return streamList;
    }

    public static ArrayList<String> getStreamName() {
        return streamName;
    }

    /**
     *
     * @return the list of currently online users
     */
    public static ArrayList getCurrentlyOnline() {
        return currentlyOnline;
    }

    /***
     *Gets the list of all the streams and determines if a stream is alive.
     * @return true if yes, false if no
     */
    public static ArrayList<Boolean> getStreamAlive() {
        return streamAlive;
    }

    private static int timeInMinutes(int minutes) {
        return 60000*minutes;
    }

    private static String getTime(String time) {
        time = time.split(" ")[3];
        time = (Integer.parseInt(time.split(":")[0]) > 12 ? Integer.parseInt(time.split(":")[0])-12 :  time.split(":")[0]) + ":" +time.split(":")[1]
                +(Integer.parseInt(time.split(":")[0]) > 12 ? " PM" : " AM");
        return time;
    }

}
