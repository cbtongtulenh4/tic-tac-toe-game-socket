package javakit.minhphuc.mapper;

import javakit.minhphuc.model.Player;
import javakit.minhphuc.model.Roles;

import java.sql.ResultSet;
import java.sql.SQLException;

public class PlayerMapper implements RowMapper<Player> {

    @Override
    public Player mapRow(ResultSet rs) {
        Player player = new Player();
        try{
            player.setId(rs.getLong("id"));
            if(rs.getString("email") != null) {
                player.setEmail(rs.getString("email"));
            }
            if(rs.getString("password") != null) {
                player.setPassword(rs.getString("password"));
            }
            player.setScore(rs.getDouble("score"));
            player.setRoleID(rs.getLong("roleid"));
            player.setStatus(rs.getInt("status"));
            if(rs.getString("avatar") != null){
                player.setAvatar(rs.getString("avatar"));
            }
            if(rs.getString("name") != null){
                player.setName(rs.getString("name"));
            }
            if(rs.getString("gender") != null){
                player.setGender(rs.getString("gender"));
            }if(rs.getInt("yearOfBirth") != 0){
                player.setYearOfBirth(rs.getInt("yearOfBirth"));
            }
            Roles role = new Roles();
            if (rs.getString("r.code") != null){
                role.setCode(rs.getString("r.code"));
            }
//            if (rs.getLong("r.id") != 0){
//                role.setId(rs.getLong("id"));
//            }
            if (rs.getString("r.name") != null){
                role.setCode(rs.getString("r.name"));
            }
            player.setRole(role);

            player.setMatch(rs.getInt("match"));
            player.setWin(rs.getInt("win"));
            player.setLose(rs.getInt("lose"));
            player.setDraw(rs.getInt("draw"));
            player.setWinRate(rs.getFloat("winRate"));
            player.setStreak(rs.getInt("streak"));



        }catch (SQLException e){
            e.printStackTrace();
        }
        return player;
    }

}
