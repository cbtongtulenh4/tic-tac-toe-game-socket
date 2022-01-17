package javakit.minhphuc.service;

import javakit.minhphuc.dto.PlayerDTO;

public interface IPlayerService {

    public PlayerDTO findOneByEmailAndPass(String email, String password);

    public void save(PlayerDTO playerDTO);

    public void saveProfile(PlayerDTO playerDTO);

    public PlayerDTO findOneByEmail(String email);

}
