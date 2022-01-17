package javakit.minhphuc.mapper;

import javakit.minhphuc.model.GameMatch;

import java.sql.ResultSet;
import java.sql.SQLException;

public class GameMatchMapper implements RowMapper<GameMatch> {
    @Override
    public GameMatch mapRow(ResultSet rs) {
        GameMatch gameMatch = new GameMatch();
        try{
            gameMatch.setId(rs.getLong("id"));
            gameMatch.setPlayerID1(rs.getLong("playerid1"));
            gameMatch.setPlayerID2(rs.getLong("playerid2"));
            gameMatch.setWinnerID(rs.getInt("winnerid"));
            if(rs.getInt("playtime") != 0){
                gameMatch.setPlayTime(rs.getInt("playtime"));
            }
            if(rs.getInt("totalmove")!=0){
                gameMatch.setTotalMove(rs.getInt("totalmove"));
            }
            if(rs.getTimestamp("startedtime") != null){
                gameMatch.setStartedTime(rs.getTimestamp("startedtime"));
            }
            if(rs.getNString("chat") != null){
                gameMatch.setChat(rs.getNString("chat"));
            }
        }catch (SQLException e){
            e.printStackTrace();
        }
        return gameMatch;
    }
}
