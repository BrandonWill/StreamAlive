import javax.swing.*;
import java.awt.*;
import java.io.FileWriter;
import java.net.URI;
import java.util.ArrayList;
import java.util.Collections;

/**
 * @author Brandon
 * Date: 3/27/13
 * Time: 2:42 PM
 */
public class GUI extends JFrame {

    public static JList jList1;
    public static JList jList2;
    public static JTextField jTextField1;
    public static JTextField jTextField2;

    private static TwitchStream twitchStream = new TwitchStream();
    private static AzubuStream azubuStream = new AzubuStream();

    public static volatile boolean isRunning = true;

    public GUI() {
        try {
            for (UIManager.LookAndFeelInfo info : UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (Exception e) {
            // If Nimbus is not available, fall back to cross-platform
            try {
                UIManager.setLookAndFeel(UIManager.getCrossPlatformLookAndFeelClassName());
            } catch (Exception ignored) {}
        }


        initComponents();
    }

    public static synchronized boolean getIsRunning() {
        return isRunning;
    }

    // <editor-fold defaultstate="collapsed" desc="Generated Code">
    private void initComponents() {

        MyShutdownHook shutdownHook = new MyShutdownHook();
        Runtime.getRuntime().addShutdownHook(shutdownHook);

        JTabbedPane jTabbedPane1 = new JTabbedPane();
        JPanel jPanel1 = new JPanel();
        JScrollPane jScrollPane1 = new JScrollPane();
        jList1 = new javax.swing.JList();
        jTextField1 = new javax.swing.JTextField();
        JButton jButton1 = new JButton();
        JButton jButton2 = new JButton();
        JButton jButton3 = new JButton();
        JPanel jPanel2 = new JPanel();
        JScrollPane jScrollPane2 = new JScrollPane();
        jList2 = new javax.swing.JList();
        jTextField2 = new javax.swing.JTextField();
        JButton jButton4 = new JButton();
        JButton jButton5 = new JButton();
        JButton jButton6 = new JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("LoL Streamer List");
        //removes maximize button and stops it from being resized
        setResizable(false);

        jList1.setModel(new javax.swing.AbstractListModel() {
            String[] strings = getTwitchStreamList();
            public int getSize() { return strings.length; }
            public Object getElementAt(int i) { return strings[i]; }
        });
        jList1.setSelectionMode(javax.swing.ListSelectionModel.SINGLE_SELECTION);

        jScrollPane1.setViewportView(jList1);

        jTextField1.setText("Search");

        jButton1.setText("View Stream");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed();
            }
        });

        jButton2.setText("Add Stream");
        jButton2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton2ActionPerformed();
            }
        });

        jButton3.setText("Remove Stream");
        jButton3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton3ActionPerformed();
            }
        });

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
                jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 215, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(47, 47, 47)
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                                .addComponent(jButton1, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 109, Short.MAX_VALUE)
                                                .addComponent(jTextField1, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 109, Short.MAX_VALUE)
                                                .addComponent(jButton2, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 109, Short.MAX_VALUE))
                                        .addComponent(jButton3)))
        );
        jPanel1Layout.setVerticalGroup(
                jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(jPanel1Layout.createSequentialGroup()
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                        .addGroup(jPanel1Layout.createSequentialGroup()
                                                .addComponent(jTextField1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                                .addComponent(jButton1)
                                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                                .addComponent(jButton2)
                                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                                .addComponent(jButton3))
                                        .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 210, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addContainerGap(25, Short.MAX_VALUE))
        );

        jTabbedPane1.addTab("Twitch Streams", jPanel1);

        jList2.setModel(new javax.swing.AbstractListModel() {
            String[] strings = getAzubuStreamList();

            public int getSize() {
                return strings.length;
            }

            public Object getElementAt(int i) {
                return strings[i];
            }
        });
        jList2.setSelectionMode(javax.swing.ListSelectionModel.SINGLE_SELECTION);
        jScrollPane2.setViewportView(jList2);

        jTextField2.setText("Search");

        jButton4.setText("View Stream");
        jButton4.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton4ActionPerformed();
            }
        });

        jButton5.setText("Add Stream");
        jButton5.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton5ActionPerformed();
            }
        });

        jButton6.setText("Remove Stream");
        jButton6.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton6ActionPerformed();
            }
        });

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
                jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(jPanel2Layout.createSequentialGroup()
                                .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 215, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(47, 47, 47)
                                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                        .addComponent(jButton4, javax.swing.GroupLayout.DEFAULT_SIZE, 109, Short.MAX_VALUE)
                                        .addComponent(jTextField2, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, 109, Short.MAX_VALUE)
                                        .addComponent(jButton5, javax.swing.GroupLayout.DEFAULT_SIZE, 109, Short.MAX_VALUE)
                                        .addComponent(jButton6, javax.swing.GroupLayout.DEFAULT_SIZE, 109, Short.MAX_VALUE)))
        );
        jPanel2Layout.setVerticalGroup(
                jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(jPanel2Layout.createSequentialGroup()
                                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                        .addGroup(jPanel2Layout.createSequentialGroup()
                                                .addComponent(jTextField2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                                .addComponent(jButton4)
                                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                                .addComponent(jButton5)
                                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                                .addComponent(jButton6))
                                        .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 210, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addContainerGap(25, Short.MAX_VALUE))
        );

        jTabbedPane1.addTab("Azubu Streams", jPanel2);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
                layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addComponent(jTabbedPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 376, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
                layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(layout.createSequentialGroup()
                                .addComponent(jTabbedPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 263, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        pack();

    }// </editor-fold>

    /**
     * Button to view the current twitch stream that's selected.
     */
    private void jButton1ActionPerformed() {
        if(Desktop.isDesktopSupported()){
            try {
                Desktop.getDesktop().browse(new URI("http://www.twitch.tv/" + TwitchStream.getStreamID(jList1.getModel().getElementAt(jList1.getSelectedIndex()).toString().replace(" (ONLINE)", ""))));
            } catch (Exception ignored) {}
        }
    }


    /**
     * button to add a twitch stream to the list
     */
    private void jButton2ActionPerformed() {
        String identityOfStreamer = JOptionPane.showInputDialog(null, "Enter the streamer's ID.\n" +
                "http://twitch.tv/riotgames id is riotgames");
        if (identityOfStreamer == null || identityOfStreamer.isEmpty() ) { return; }
        String nameOfStreamer = JOptionPane.showInputDialog(null, "Enter the name of the streamer: ");
        if (nameOfStreamer == null || nameOfStreamer.isEmpty()) { return; }
        new TwitchStream(identityOfStreamer, nameOfStreamer);
        TwitchStream.restartStreams();
    }

    /**
     * button to remove a twitch stream from the list
     */
    private void jButton3ActionPerformed() {
        String nameOfStreamer = JOptionPane.showInputDialog(null, "Enter the streamer's name: ");
        if (nameOfStreamer == null || nameOfStreamer.isEmpty()) { return; }
        TwitchStream.removeStream(nameOfStreamer);
        TwitchStream.restartStreams();
    }


    /**
     * Button to view the current azubu stream that's selected.
     */
    private void jButton4ActionPerformed() {
        if(Desktop.isDesktopSupported()){
            try {
                Desktop.getDesktop().browse(new URI(AzubuStream.STREAM_URL + AzubuStream.getStreamID(jList2.getModel().getElementAt(jList2.getSelectedIndex()).toString().replace(" (ONLINE)", ""))));
            } catch (Exception ignored) {}
        }
    }

    /**
     * Button to add an Azubu stream to the list
     */
    private void jButton5ActionPerformed() {
        String identityOfStreamer = JOptionPane.showInputDialog(null, "Enter the streamer's ID.\n" +
                "Riot games url is: http://www.azubu.tv/channel/live_small.do?cn_id=2125982449001&vod_id=\n"  +
                "Riot games ID is: 2125982449001");
        String nameOfStreamer = JOptionPane.showInputDialog(null, "Enter the name of the streamer: ");
        if (identityOfStreamer.isEmpty()) { return; }
        if (nameOfStreamer.isEmpty()) { return; }
        new AzubuStream(identityOfStreamer, nameOfStreamer);
        AzubuStream.restartStreams();
    }

    /**
     * Button to remove azubu's stream
     */
    private void jButton6ActionPerformed() {
        String nameOfStreamer = JOptionPane.showInputDialog(null, "Enter the streamer's name: ");
        AzubuStream.removeStream(nameOfStreamer);
        AzubuStream.restartStreams();
    }

    /**
     * Gets the current online and offline stream lists and alphabetizes them
     * @return twitch stream list and whether they're online or not
     */
    public static synchronized String[] getTwitchStreamList() {
        //TODO ensure these streams are loaded up via text file
        twitchStream.areStreamsUp();

        ArrayList<String> streamList = TwitchStream.getStreamList(); //ids for stream
        ArrayList<String> streamName = TwitchStream.getStreamName(); //name for streamer
        ArrayList<Boolean> streamAlive = TwitchStream.getStreamAlive(); //whether the stream is up or not
        ArrayList<String> finalList = new ArrayList<>();
        ArrayList<String> onlineList = new ArrayList<>();
        ArrayList<String> offlineList = new ArrayList<>();
        ArrayList<String> makeLowercase = new ArrayList<>();
        try {
            for(int i = 0; i < streamList.size(); i++) {
                //checks if streamer is on
                String tempName = streamName.get(i);
                if (Character.isAlphabetic(tempName.charAt(0)) && !Character.isUpperCase(tempName.charAt(0))) {
                    makeLowercase.add(tempName);
                    tempName = Character.toUpperCase(tempName.charAt(0)) + tempName.substring(1);
                }
                if (streamAlive.get(i)) {
                    onlineList.add(tempName +" (ONLINE)");
                } else {
                    offlineList.add(tempName);
                }
            }
        }    catch (Exception e) {
//            System.out.println(e);
            //Can error here from multi threading from adding and removing streams
        }
        Collections.sort(onlineList);
        Collections.sort(offlineList);

        finalList.addAll(onlineList);
        finalList.addAll(offlineList);

        for (int i =0; i < finalList.size(); i++) {
            for(String lowercase : makeLowercase) {
                if (finalList.get(i).equalsIgnoreCase(lowercase)) {
                    finalList.set(i, lowercase);
                }
            }
        }

        return finalList.toArray(new String[finalList.size()]);
    }

    /**
     * Gets the current online and offline stream lists and alphabetizes them
     * @return Azubu stream list and whether they're online or not
     */
    public static synchronized String[] getAzubuStreamList() {
        //TODO ensure these streams are loaded up via text file
        azubuStream.areStreamsUp();

        ArrayList<String> streamList = AzubuStream.getStreamList(); //ids for stream
        ArrayList<String> streamName = AzubuStream.getStreamName(); //name for streamer
        ArrayList<Boolean> streamAlive = AzubuStream.getStreamAlive(); //whether the stream is up or not
        ArrayList<String> finalList = new ArrayList<>();
        ArrayList<String> onlineList = new ArrayList<>();
        ArrayList<String> offlineList = new ArrayList<>();
        try {
            for(int i = 0; i < streamList.size(); i++) {
                //checks if streamer is on
                if (streamAlive.get(i)) {
                    onlineList.add(streamName.get(i) +" (ONLINE)");
                } else {
                    offlineList.add(streamName.get(i));
                }
            }
        } catch (Exception ignored) {
            //multi threading can crash it here when adding/removing streams!
        }
        Collections.sort(onlineList);
        Collections.sort(offlineList);

        finalList.addAll(onlineList);
        finalList.addAll(offlineList);

        return finalList.toArray(new String[finalList.size()]);
    }

    private class MyShutdownHook extends Thread {
        public void run() {
            shutdown();
        }
    }

    /**
     * Writes to a file all the streams.
     */
    private int tryAmount = 0;
    private void shutdown() {
        try {
            isRunning = false;
            FileWriter fileWriter = new FileWriter(FileCreator.getStreamListFile());

            ArrayList<String> streamList = TwitchStream.getStreamList(); //ids for stream
            ArrayList<String> streamName = TwitchStream.getStreamName(); //name for streamer
            fileWriter.write("Twitch Streams\r\n");
            fileWriter.write("\r\n");

            for (int i = 0; i < streamList.size(); i++) {
                System.out.println(streamList.get(i) + " " +streamName.get(i).replaceAll(" ", "_"));
                fileWriter.write(streamList.get(i) + " " +streamName.get(i).replaceAll(" ", "_") +"\r\n");
            }
            fileWriter.write("\r\n");
            fileWriter.write("Azubu Streams\r\n");
            fileWriter.write("\r\n");

            streamList = AzubuStream.getStreamList(); //ids for stream
            streamName = AzubuStream.getStreamName(); //name for streamer

            for (int i = 0; i < streamList.size(); i++) {
                System.out.println(streamList.get(i) + " " +streamName.get(i).replaceAll(" ", "_"));
                fileWriter.write(streamList.get(i) + " " +streamName.get(i).replaceAll(" ", "_") +"\r\n");
            }

            fileWriter.close();

        }  catch (Exception e) {
            if (tryAmount < 500) {
                tryAmount++;
                shutdown();
            }

            if (tryAmount == 500) {
                try{
                    isRunning = false;
                    FileWriter fileWriter = new FileWriter(FileCreator.getDesktopDirectory());

                    ArrayList<String> streamList = TwitchStream.getStreamList(); //ids for stream
                    ArrayList<String> streamName = TwitchStream.getStreamName(); //name for streamer
                    fileWriter.write("Twitch Streams\r\n");
                    fileWriter.write("\r\n");

                    for (int i = 0; i < streamList.size(); i++) {
                        System.out.println(streamList.get(i) + " " +streamName.get(i).replaceAll(" ", "_"));
                        fileWriter.write(streamList.get(i) + " " +streamName.get(i).replaceAll(" ", "_") +"\r\n");
                    }
                    fileWriter.write("\r\n");
                    fileWriter.write("Azubu Streams\r\n");
                    fileWriter.write("\r\n");

                    streamList = AzubuStream.getStreamList(); //ids for stream
                    streamName = AzubuStream.getStreamName(); //name for streamer

                    for (int i = 0; i < streamList.size(); i++) {
                        System.out.println(streamList.get(i) + " " +streamName.get(i).replaceAll(" ", "_"));
                        fileWriter.write(streamList.get(i) + " " +streamName.get(i).replaceAll(" ", "_") +"\r\n");
                    }

                    fileWriter.close();
                }  catch (Exception ignored) { }

            }

            System.out.println("Errored out while writing!");


        }
    }

}
