package edu.kit.pse17.go_app;


import edu.kit.pse17.go_app.ClientCommunication.Downstream.EventArg;
import edu.kit.pse17.go_app.ServiceLayer.UserLocation;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class TestUtil {

    private static final String PATH_OBSERVER = "/home/tina/PSE/04_Qualitätssicherung/ObserverTestsResult/";
    private static final String PATH_CLUSTER = "/home/tina/PSE/04_Qualitätssicherung/ClusteringTestResult/";

    private static final String CLUSTER_FILE_NAME = "clusteringResults";

    public static boolean runInTestMode = false;

    private static HashMap<EventArg, String> outputFiles;

    private static void initializeFiles() {
        outputFiles = new HashMap<>();
        outputFiles.put(EventArg.GO_ADDED_EVENT, "goAdded");
        outputFiles.put(EventArg.GO_EDITED_EVENT, "goEdited");
        outputFiles.put(EventArg.GO_REMOVED_EVENT, "goRemoved");
        outputFiles.put(EventArg.GROUP_EDITED_EVENT, "groupEdited");
        outputFiles.put(EventArg.GROUP_REMOVED_EVENT, "groupRemoved");
        outputFiles.put(EventArg.GROUP_REQUEST_RECEIVED_EVENT, "requestReceived");
        outputFiles.put(EventArg.MEMBER_ADDED_EVENT, "memberAdded");
        outputFiles.put(EventArg.MEMBER_REMOVED_EVENT, "memberRemoved");
        outputFiles.put(EventArg.GROUP_REQUEST_DENIED_EVENT, "requestDenied");
        outputFiles.put(EventArg.STATUS_CHANGED_EVENT, "statusChanged");
        outputFiles.put(EventArg.USER_DELETED_EVENT, "userDeleted");
    }

    public static void recordData(EventArg arg, String json, List<String> receiver) {
        if (outputFiles == null) {
            initializeFiles();
        }

        String fileNameStub = PATH_OBSERVER + outputFiles.get(arg);
        String json_file = fileNameStub + "_json" + ".txt";
        String receiver_file = fileNameStub + "_rec" + ".txt";

        File jsonFile = new File(json_file);
        File receiverFile = new File(receiver_file);
        try {
            jsonFile.createNewFile();
            receiverFile.createNewFile();

            FileWriter jsonWriter = new FileWriter(jsonFile);
            FileWriter recWriter = new FileWriter(receiverFile);

            jsonWriter.write(json);
            jsonWriter.flush();
            jsonWriter.close();

            for (String rec : receiver) {
                recWriter.write(rec + "\n");
            }
            recWriter.flush();
            recWriter.close();

        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    public static void recordClusteringData(Map<Long, List<UserLocation>> currentGos) {
        String fileName = PATH_CLUSTER + CLUSTER_FILE_NAME + ".csv";
        File file = new File(fileName);
        try {
            file.createNewFile();
            FileWriter writer = new FileWriter(file);
            for (long id : currentGos.keySet()) {
                for (UserLocation ul : currentGos.get(id)) {
                    String line = String.valueOf(id) + ", " + ul.getUserId() + ", " + String.valueOf(ul.getLat()) + ", " + String.valueOf(ul.getLon()) + "\n";
                    writer.write(line);
                }
            }

            writer.flush();
            writer.close();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
