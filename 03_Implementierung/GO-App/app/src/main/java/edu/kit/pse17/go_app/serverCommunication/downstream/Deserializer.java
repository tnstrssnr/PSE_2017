package edu.kit.pse17.go_app.serverCommunication.downstream;

import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;

import java.lang.reflect.Type;

import edu.kit.pse17.go_app.model.entities.Group;

/**
 * Created by Vovas on 19.08.2017.
 */

public class Deserializer {

        private static JsonDeserializer<Group> deserializer;

        public static JsonDeserializer<Group> getDeserializer() {
            if(deserializer == null) {
                deserializer = new JsonDeserializer<Group>() {
                    @Override
                    public Group deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
                        Group group = new Group();

                        JsonObject jsonObject = json.getAsJsonObject();
                        group.setId(jsonObject.get("groupId").getAsLong());
                        group.setName(jsonObject.get("name").getAsString());
                        group.setDescription(jsonObject.get("description").getAsString());

                        return group;
                    }
                };
            }
            return deserializer;
        }

    }


