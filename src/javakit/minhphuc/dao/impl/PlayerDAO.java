package javakit.minhphuc.dao.impl;

import javakit.minhphuc.config.DataConfig;
import javakit.minhphuc.dao.IPlayerDAO;
import javakit.minhphuc.mapper.PlayerMapper;
import javakit.minhphuc.model.Player;
import java.util.*;

public class PlayerDAO extends DataConfig<Player> implements IPlayerDAO {

    @Override
    public Player findOneByID(Long id) {
        return null;
    }

    @Override
    public Player findOneByEmailAndPass(String email,String password) {
        String sql = "SELECT * FROM player AS p" +
                " INNER JOIN roles AS r ON p.roleid = r.id" +
                " WHERE p.email = ? AND p.password = ? ";
        List<Player> result = query(sql, new PlayerMapper(), email, password);
        return result.isEmpty() ? null : result.get(0);
    }

    @Override
    public void updatePlayer(Player player) {
        String sql = "UPDATE player" +
                " SET email = ?, password = ?, avatar = ?, name = ?," +
                " gender = ?, roleid = ?, yearOfBirth = ?, score = ?, status = ?" +
                " WHERE id = ?";
        update(sql, player.getEmail(), player.getPassword(), player.getAvatar(),
                player.getName(), player.getGender(), player.getRoleID(), player.getYearOfBirth(),
                player.getScore(), player.getScore(), player.getId());
    }

    @Override
    public void updateAchievement(Player player) {
        String sql = "UPDATE player" +
                " SET score = ?, match = ?, win = ?, lose = ?," +
                " draw = ?, winRate = ?, streak = ?" +
                " WHERE id = ?";
        update(sql, player.getScore(), player.getMatch(), player.getWin(),
                player.getLose(), player.getDraw(), player.getWinRate(), player.getStreak());
    }

    @Override
    public Long insertPlayer(Player player) {
        StringBuilder sql = new StringBuilder("INSERT INTO player");
        sql.append("(email, password, avatar, name, gender, roleid, yearOfBirth)");
        sql.append(" VALUES(?, ?, ?, ?, ?, ?, ?)");
        return insert(sql.toString(), player.getEmail(), player.getPassword(), player.getAvatar(),
                player.getName(), player.getGender(), player.getRoleID(), player.getYearOfBirth());
    }

    @Override
    public void updateProfile(Player player) {
        String sql = "UPDATE player" +
                " SET email = ?, avatar = ?, name = ?," +
                " gender = ?, yearOfBirth = ?" +
                " WHERE id = ?";
        update(sql, player.getEmail(), player.getAvatar(),player.getName(),
                player.getGender(), player.getYearOfBirth(), player.getId());
    }


    @Override
    public Player findOneByEmail(String email) {
        StringBuilder sql = new StringBuilder("SELECT * FROM player as p");
        sql.append(" INNER JOIN roles AS r ON p.roleid = r.id");
        sql.append(" WHERE p.email = ?");
        List<Player> result = query(sql.toString(), new PlayerMapper(), email);
        return result.isEmpty() ? null : result.get(0);
    }

    public static void main(String[] args) {
        String email = "gapro@gamil.com";
        PlayerDAO dao = new PlayerDAO();
        System.out.println(dao.findOneByEmail(email));
    }

}
