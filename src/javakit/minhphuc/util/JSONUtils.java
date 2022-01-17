package javakit.minhphuc.util;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.introspect.VisibilityChecker;
import javakit.minhphuc.dto.PlayerDTO;
import javakit.minhphuc.dto.PlayersDTO;
import javakit.minhphuc.model.Player;
import org.json.simple.JSONObject;

import java.lang.reflect.Array;
import java.util.Arrays;
import java.util.Map;

public class JSONUtils {

    // Note: How to use set params as Object ... params without use Map
    // specifically: How to get the name of instance
    public static JSONObject toJSON(Map<String, Object> params){
        JSONObject jsonObject = new JSONObject();
        jsonObject.putAll(params);
        return jsonObject;
    }

    // Note: How to build mapping Model without use ObjectMapper.readValue()
    public static <T> T toModel(String JSONString, Class<T> tClass ){
        T model = null;
        try {
            ObjectMapper mapper = new ObjectMapper();
            // Ignore unknown fields - bỏ qua
            mapper.disable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES);
            //Unknown Purpose
            mapper.setVisibility(VisibilityChecker.Std.defaultInstance().withFieldVisibility(JsonAutoDetect.Visibility.ANY));

            model = mapper.readValue(JSONString, tClass);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        return model;
    }

    public static JSONObject toJSON(Object object){
        JSONObject jsonObject = new JSONObject();

        // USE ObjectMapper.convertValue
        ObjectMapper objectMapper = new ObjectMapper();
        jsonObject.putAll(objectMapper.convertValue(object, Map.class));

        //
        return jsonObject;
    }

//    public static JSONObject toJSON(PlayerDTO player){
//        JSONObject jsonObject = new JSONObject();
//
//        // USE ObjectMapper.convertValue
//        ObjectMapper objectMapper = new ObjectMapper();
//        jsonObject.putAll(objectMapper.convertValue(player, Map.class));
//
//        //
//        return jsonObject;
//    }





    public static void main(String[] args) {
        PlayerDTO a = new PlayerDTO();
        String str = "{\"password\":\"123456\",\"gender\":\"Nam\",\"name\":\"sdfsf\",\"avatar\":\"icons8_alien_96px.png\",\"type\":\"SIGNUP\",\"email\":\"gamo@gmail.com\",\"yearOfBirth\":2000, \"a\":3 }";
        a = toModel(str, PlayerDTO.class);
        System.out.println(String.valueOf(toModel(str, PlayerDTO.class)));
    }

        
}
