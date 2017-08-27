package edu.kit.pse17.go_app;


import edu.kit.pse17.go_app.ClientCommunication.Downstream.EventArg;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;

public class TestUtil {

    private static final String PATH = "/home/tina/PSE/04_Qualitätssicherung/ObserverTestsResult/";

    public static boolean runInTestMode = true;

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

        String fileNameStub = PATH + outputFiles.get(arg);
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

}
