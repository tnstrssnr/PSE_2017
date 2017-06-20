package edu.kit.pse17.go_app.ServerCom.JSON;

import org.json.JSONObject;

import edu.kit.pse17.go_app.Model.GroupLocation;

/**
 * Created by tina on 20.06.17.
 */

public class LocationParser implements JsonParser<GroupLocation> {

    @Override
    public JSONObject parseToJson(GroupLocation groupLocation) {
        return null;
    }

    @Override
    public GroupLocation parseFromJson(JSONObject o) {
        return null;
    }

}
