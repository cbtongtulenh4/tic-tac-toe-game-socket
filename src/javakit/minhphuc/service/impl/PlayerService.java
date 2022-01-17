package javakit.minhphuc.service.impl;

import javakit.minhphuc.convert.PlayerConvert;
import javakit.minhphuc.dao.IPlayerDAO;
import javakit.minhphuc.dao.impl.PlayerDAO;
import javakit.minhphuc.dto.PlayerDTO;
import javakit.minhphuc.model.Player;
import javakit.minhphuc.service.IPlayerService;
import javakit.minhphuc.util.MyHash;

import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;

public class PlayerService implements IPlayerService {

    private IPlayerDAO playerDAO = new PlayerDAO();

    @Override
    public PlayerDTO findOneByEmailAndPass(String email, String password) {

        Player player = playerDAO.findOneByEmail(email);

        PlayerDTO result = null;
        if(player != null){
            String storedPassword = player.getPassword();
            try {
                result = (MyHash.validatePassPBKDF2(password, storedPassword) == true)
                                ? PlayerConvert.toDTO(player) : null;
            } catch (NoSuchAlgorithmException e) {
                e.printStackTrace();
            } catch (InvalidKeySpecException e) {
                e.printStackTrace();
            }
        }
        return result;
    }

    @Override
    public void save(PlayerDTO playerDTO) {
        Player player = PlayerConvert.toModel(playerDTO);
        if(playerDTO.getId() != null){
            playerDAO.updatePlayer(player);
        }else {
            if(player.getRoleID() == null){
                player.setRoleID(2L);
            }
            Long id = playerDAO.insertPlayer(player);
//            if(id != null)
        }
    }

    @Override
    public void saveProfile(PlayerDTO playerDTO) {
        playerDAO.updateProfile(PlayerConvert.toModel(playerDTO));
    }

    @Override
    public PlayerDTO findOneByEmail(String email) {
        return PlayerConvert.toDTO(playerDAO.findOneByEmail(email));
    }

    public static void main(String[] args) {
        PlayerService ps = new PlayerService();
        ps.findOneByEmail("hientran@gmail.com");
    }


}
