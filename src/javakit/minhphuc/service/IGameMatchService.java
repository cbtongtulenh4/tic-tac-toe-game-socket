package javakit.minhphuc.service;

import javakit.minhphuc.dto.GameMatchDTO;
import javakit.minhphuc.model.GameMatch;

public interface IGameMatchService {

    Long appendGameMatch(GameMatchDTO gameMatchDto);

}
