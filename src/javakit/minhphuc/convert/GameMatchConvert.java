package javakit.minhphuc.convert;

import javakit.minhphuc.dto.GameMatchDTO;
import javakit.minhphuc.model.GameMatch;

import java.sql.Timestamp;

public class GameMatchConvert {

    public static GameMatch toEntity(GameMatchDTO dto){
        if (dto == null) return null;
        GameMatch entity = new GameMatch();
        entity.setPlayerID1(dto.getPlayerID1());
        entity.setPlayerID2(dto.getPlayerID2());
        entity.setTotalMove(dto.getTotalMove());
        entity.setChat(dto.getChat());
        entity.setWinnerID(dto.getWinnerID());
        entity.setPlayTime(dto.getPlayTime());
        entity.setStartedTime(Timestamp.valueOf(dto.getStartedTime()));
        return entity;
    }

}
