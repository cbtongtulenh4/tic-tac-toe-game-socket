package javakit.minhphuc.dao.impl;

import javakit.minhphuc.config.DataConfig;
import javakit.minhphuc.dao.IGameMatchDAO;
import javakit.minhphuc.model.GameMatch;

public class GameMatchDAO extends DataConfig<GameMatch> implements IGameMatchDAO {

    @Override
    public Long appendGameMatch(GameMatch gameMatch) {
        StringBuilder sql = new StringBuilder("INSERT INTO gamematch");
        sql.append("(playerid1, playerid2, winnerid, playtime, totalmove, startedtime) ");
        sql.append("VALUES(?, ?, ?, ?, ?, ?)");
        return insert(sql.toString(), gameMatch.getPlayerID1(), gameMatch.getPlayerID2(),
                gameMatch.getWinnerID(), gameMatch.getPlayTime(), gameMatch.getTotalMove(), gameMatch.getStartedTime());
    }
}
