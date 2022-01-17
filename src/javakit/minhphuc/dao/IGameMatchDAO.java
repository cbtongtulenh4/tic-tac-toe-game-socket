package javakit.minhphuc.dao;

import javakit.minhphuc.config.RepositoryDataConfig;
import javakit.minhphuc.model.GameMatch;

public interface IGameMatchDAO extends RepositoryDataConfig<GameMatch> {

    public Long appendGameMatch(GameMatch gameMatch);
    
}
