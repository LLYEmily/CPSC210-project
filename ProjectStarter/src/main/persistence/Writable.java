package persistence;

import org.json.JSONObject;

// Represent an object that can be convert to Json
public interface Writable {
    // EFFECTS: returns this as JSON object
    JSONObject toJson();
}
