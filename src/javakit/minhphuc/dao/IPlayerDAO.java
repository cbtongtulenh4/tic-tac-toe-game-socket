package javakit.minhphuc.dao;

import javakit.minhphuc.config.RepositoryDataConfig;
import javakit.minhphuc.model.Player;

public interface IPlayerDAO extends RepositoryDataConfig<Player> {
    public Player findOneByID(Long id);

    public Player findOneByEmailAndPass(String email,String password);

    public void updatePlayer(Player player);

    public void updateAchievement(Player player);

    public Long insertPlayer(Player player);

    public void updateProfile(Player player);

    public Player findOneByEmail(String email);

}
