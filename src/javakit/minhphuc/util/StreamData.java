package javakit.minhphuc.util;

public class StreamData {


    // List type request
    public enum Types{
        CRYPTION_CODE,
        CONNECT_SERVER,
        LOGIN,
        SIGNUP,
        LOGOUT,
        ARENA_HALL,
        GET_PROFILE,
        UPDATE_PROFILE,
        CHANGE_PASSWORD,
        FIND_MATCH,
        REQUEST_PAIR_MATCH,
        RESULT_PAIR_MATCH,
        CHAT_ROOM,
        GAME_EVENT,
        START,
        MOVE,
        TURN_TIMER_END,
        TURN_TICK,
        MATCH_TIMER_END,
        MATCH_TICK,
        WIN,
        EXIT,
        UNKNOWN_TYPE
    }



    public static Types getType(String received){
        Types result = Types.UNKNOWN_TYPE;
        try{
            result = Enum.valueOf(StreamData.Types.class, received);
        }catch (Exception e){
            System.err.println("UNKNOWN_TYPE: " + e.getMessage());
        }
        return result;
    }

}
