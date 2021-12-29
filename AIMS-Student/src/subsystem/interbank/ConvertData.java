package subsystem.interbank;

import utils.MyMap;

import java.util.Map;

public class ConvertData {
    public String convertMapToJson(Map<String, Object> data) {
        return ((MyMap) data).toJSON();
    }
}
