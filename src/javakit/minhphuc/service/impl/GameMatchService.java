package javakit.minhphuc.service.impl;

import javakit.minhphuc.convert.GameMatchConvert;
import javakit.minhphuc.dao.IGameMatchDAO;
import javakit.minhphuc.dao.impl.GameMatchDAO;
import javakit.minhphuc.dto.GameMatchDTO;
import javakit.minhphuc.model.GameMatch;
import javakit.minhphuc.service.IGameMatchService;

public class GameMatchService implements IGameMatchService {

    private IGameMatchDAO gameMatchDAO = new GameMatchDAO();

    @Override
    public Long appendGameMatch(GameMatchDTO gameMatchDto) {
        GameMatch matchEntity = GameMatchConvert.toEntity(gameMatchDto);
        return gameMatchDAO.appendGameMatch(matchEntity);
    }
}
