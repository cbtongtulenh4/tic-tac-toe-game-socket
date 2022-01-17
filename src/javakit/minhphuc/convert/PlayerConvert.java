package javakit.minhphuc.convert;

import javakit.minhphuc.dto.PlayerDTO;
import javakit.minhphuc.dto.PlayerInGameDTO;
import javakit.minhphuc.model.Player;

import java.lang.reflect.Field;

public class PlayerConvert {
    public static PlayerDTO toDTO(Player model){
        if (model == null) return null;
        PlayerDTO dto = new PlayerDTO();
        dto.setId(model.getId());
        if(model.getEmail() != null){
            dto.setEmail(model.getEmail());
        }

        if(model.getPassword() != null){
            dto.setPassword(model.getPassword());
        }

        if(model.getAvatar() != null){
            dto.setAvatar(model.getAvatar());
        }

        if(model.getGender()!= null){
            dto.setGender(model.getGender());
        }

        if(model.getName() != null){
            dto.setName(model.getName());
        }

        if(model.getRole() != null){
            dto.setRole(model.getRole());
        }

        if(model.getRoleID() != null){
            dto.setRoleID(model.getRoleID());
        }

        if(model.getScore() != null){
            dto.setScore(model.getScore());
        }

        if(model.getStatus() != null){
            dto.setStatus(model.getStatus());
        }

        if(model.getYearOfBirth() != null){
            dto.setYearOfBirth(model.getYearOfBirth());
        }

        if(model.getId() != null){
            dto.setId(model.getId());
        }

        //

        dto.setMatch(model.getMatch());
        dto.setWin(model.getWin());
        dto.setWinRate(model.getWinRate());
        dto.setLose(model.getLose());
        dto.setDraw(model.getDraw());
        dto.setStreak(model.getStreak());

        //


        return dto;
    }

    public static Player toModel(PlayerDTO dto){
        if (dto == null) return null;
        Player player = new Player();
        player.setId(dto.getId());

        if(dto.getEmail() != null){
            player.setEmail(dto.getEmail());
        }
        if(dto.getPassword() != null){
            player.setPassword(dto.getPassword());
        }

        if(dto.getAvatar() != null){
            player.setAvatar(dto.getAvatar());
        }

        if(dto.getGender() != null){
            player.setGender(dto.getGender());
        }

        if(dto.getName() != null){
            player.setName(dto.getName());
        }

        if(dto.getRole() != null){
            player.setRole(dto.getRole());
        }

        if(dto.getRoleID() != null){
            player.setRoleID(dto.getRoleID());
        }

        if(dto.getScore() != null){
            player.setScore(dto.getScore());
        }

        if(dto.getStatus() != null){
            player.setStatus(dto.getStatus());
        }

        if(dto.getYearOfBirth() != null){
            player.setYearOfBirth(dto.getYearOfBirth());
        }

        if(dto.getId() != null){
            player.setId(dto.getId());
        }

        player.setMatch(dto.getMatch());
        player.setWin(dto.getWin());
        player.setWinRate(dto.getWinRate());
        player.setLose(dto.getLose());
        player.setDraw(dto.getDraw());
        player.setStreak(dto.getStreak());



        return player;
    }

    public static PlayerDTO updatePlayerDTO(PlayerDTO in, PlayerDTO out){
//        Field[] fields = in.getClass().getFields();
//        for( Field f : fields ){
//            if(f.getName().get)
//        }
//

        return null;
    }

    public static PlayerInGameDTO toPlayerInGameDTO(PlayerDTO playerDTO){
        if (playerDTO == null) return null;
        PlayerInGameDTO playerInGameDTO = new PlayerInGameDTO();
        playerInGameDTO.setAvatar(playerDTO.getAvatar());
        playerInGameDTO.setEmail(playerDTO.getEmail());
        playerInGameDTO.setName(playerDTO.getName());
        return playerInGameDTO;
    }


}
