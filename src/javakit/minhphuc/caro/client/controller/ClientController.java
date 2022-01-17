package javakit.minhphuc.caro.client.controller;

import com.fasterxml.jackson.annotation.JsonAlias;
import javakit.minhphuc.caro.client.RunClient;
import javakit.minhphuc.caro.client.view.MainMenu;
import javakit.minhphuc.caro.server.game.Caro;
import javakit.minhphuc.convert.PlayerConvert;
import javakit.minhphuc.dto.*;
import javakit.minhphuc.model.Player;
import javakit.minhphuc.util.*;

import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.SecretKey;
import javax.crypto.spec.IvParameterSpec;
import javax.swing.*;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.HashMap;
import java.util.Map;

public class ClientController {

    private Socket clientSocket = null;
    private PrintWriter out = null;
    private BufferedReader in = null;

    private Thread listener = null;

    private PlayerDTO player;
    private BaseDTO base;


    public String startConnection(String address, Integer port){
        try {
            clientSocket = new Socket(address, port);

            player = new PlayerDTO();
            base = new BaseDTO();

            in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
            out = new PrintWriter(clientSocket.getOutputStream(), true);

            if(listener != null && listener.isAlive()){
                listener.interrupt();
            }

            listener = new Thread(this::listen);
            listener.start();

            return SystemConstant.SUCCESS;
        } catch (IOException e) {
            try {
                closeEverything(clientSocket, in, out);
            } catch (IOException ioException) {
                ioException.printStackTrace();
            }
            return SystemConstant.FAILED + ";" + e.getMessage();
        }

    }

    public void listen(){
        String received = null;
        while (clientSocket.isConnected()){
            try {
                received = in.readLine();

                if(received != null){
                    System.out.println("Response: " + received);

                    base = JSONUtils.toModel(received, BaseDTO.class);

                    StreamData.Types type = StreamData.getType(base.getType());

                    switch (type){
                        case CONNECT_SERVER:
                            onConnectHandler(base);
                            break;
                        case LOGIN:
                            onLoginHandler(received);
                            break;
                        case SIGNUP:
                            onSignupHandler(base);
                            break;
                        case ARENA_HALL:
                            onArenaHallHandler(received);
                            break;
                        case LOGOUT:
                            onLogoutHandler(received);
                            break;
                        case GET_PROFILE:
                            onGetProfileHandler(received);
                            break;
                        case UPDATE_PROFILE:
                            onProfileHandler(received);
                            break;
                        case CHANGE_PASSWORD:
                            onChangePassHandler(received);
                            break;
                        case REQUEST_PAIR_MATCH:
                            onRequestPairMatchHandler(received);
                            break;
                        case RESULT_PAIR_MATCH:
                            onResultPairMatchHandler(received);
                            break;
                        case CHAT_ROOM:
                            onChatRoomHandler(received);
                            break;
                        case GAME_EVENT:
                            onGameEventHandler(received);
                            break;
                        case EXIT:
                            break;
                        default:
                    }
                }

            } catch (IOException e) {
                try {
                    closeEverything(clientSocket, in, out);
                } catch (IOException ioException) {
                    ioException.printStackTrace();
                }
                break;
            }

        }


    }

    private void onGameEventHandler(String received) {
        StreamData.Types gameEventType = StreamData.getType(base.getInType());
        CaroDTO caroDto = JSONUtils.toModel(received, CaroDTO.class);

        switch (gameEventType) {
            case START:
                int turnTimeLimit = caroDto.getTURN_TIME_LIMIT();
                int matchTimeLimit = caroDto.getMATCH_TIME_LIMIT();

                RunClient.inGameScene.startGame(turnTimeLimit, matchTimeLimit);
                break;

            case MOVE:
                PointDTO point = JSONUtils.toModel(received, PointDTO.class);
                int row = point.getRow();
                int column = point.getColumn();
                String _email = point.getOwner();

                RunClient.inGameScene.addPoint(row, column, _email);
                RunClient.inGameScene.changeTurnFrom(_email);
                break;

            case WIN:
                PlayerDTO winPlayer = JSONUtils.toModel(received, PlayerDTO.class);
                RunClient.inGameScene.setWin(winPlayer);
                break;

            case TURN_TICK:
                int turnValue = caroDto.getCurrentTick();
                RunClient.inGameScene.setTurnTimerTick(turnValue);
                break;

            case TURN_TIMER_END:
                PlayerDTO winnerPlayer = caroDto.getLastMovePlayer();
                RunClient.inGameScene.setWin(winnerPlayer);
                break;

            case MATCH_TICK:
                int matchValue = caroDto.getCurrentTick();
                RunClient.inGameScene.setMatchTimerTick(matchValue);
                break;

            case MATCH_TIMER_END:
                RunClient.inGameScene.setWin(null);
                break;
        }
    }

    private void onChatRoomHandler(String received) {
        PlayerChatDTO chat = JSONUtils.toModel(received, PlayerChatDTO.class);
        RunClient.inGameScene.addChat(chat);
    }

    private void onResultPairMatchHandler(String received) {

        if(base.getResult().equals(SystemConstant.SUCCESS)){
//            PlayersDTO players = JSONUtils.toModel(received, PlayersDTO.class);
//            PlayerDTO player1 = players.getLists().get(0);
//            PlayerDTO player2 = players.getLists().get(1);
            PlayerDTO rival = JSONUtils.toModel(received, PlayerDTO.class);

            RunClient.closeScene(RunClient.Scene.ARENA_HALL);
            RunClient.openScene(RunClient.Scene.IN_GAME);

            RunClient.inGameScene.setPlayerInGame(PlayerConvert.toPlayerInGameDTO(this.player),
                    PlayerConvert.toPlayerInGameDTO(rival));
        }
    }

    private void onRequestPairMatchHandler(String received) {
        PlayerDTO rival = JSONUtils.toModel(received, PlayerDTO.class);

        RunClient.arenaHall.setDisplayState(MainMenu.State.DEFAULT);
        //
        RunClient.arenaHall.foundMatch(rival.getName());
    }

    private void onGetProfileHandler(String received) {

        // turn off loading
        RunClient.profileScene.setLoading(false);

        if(base.getResult().equals(SystemConstant.SUCCESS)){
            PlayerDTO temp = JSONUtils.toModel(received, PlayerDTO.class);
            RunClient.profileScene.setProfileData(temp);
        }else {
            JOptionPane.showMessageDialog(RunClient.profileScene, base.getMessage(), base.getTitle(), JOptionPane.ERROR_MESSAGE);
        }
    }

    private void onLogoutHandler(String received) {
        // clear data
        this.player = new PlayerDTO();

        //
        RunClient.closeAll();
        RunClient.openScene(RunClient.Scene.LOGIN);
    }

    private void onChangePassHandler(String received) {
        PlayerDTO temp = JSONUtils.toModel(received, PlayerDTO.class);

        // turn off loading form
        RunClient.changePass.setLoading(false);

        if(temp.getResult().equals(SystemConstant.FAILED)){
            JOptionPane.showMessageDialog(RunClient.changePass, base.getMessage(), base.getTitle(), JOptionPane.ERROR_MESSAGE);
        }else {
            // close Change pass form
            RunClient.closeScene(RunClient.Scene.CHANGE_PASSWORD);
            JOptionPane.showMessageDialog(RunClient.signupScene, base.getMessage(), base.getTitle(), JOptionPane.INFORMATION_MESSAGE);
        }

    }

    private void onProfileHandler(String received) {
        // turn off loading form
        RunClient.profileScene.setProfileSaveLoading(false);

        if(base.getResult().equals(SystemConstant.FAILED)){
            JOptionPane.showMessageDialog(RunClient.profileScene, base.getMessage(), base.getTitle(), JOptionPane.ERROR_MESSAGE);
        }else {
            JOptionPane.showMessageDialog(RunClient.profileScene, base.getMessage(), base.getTitle(), JOptionPane.INFORMATION_MESSAGE);
            player = JSONUtils.toModel(received, PlayerDTO.class);
        }

    }

    private void onArenaHallHandler(String received) {

    }

    private void onSignupHandler(BaseDTO base) {
        // turn off loading in SIGNUP SCENE
        RunClient.signupScene.setLoading(false);

        if(base.getResult().equals(SystemConstant.FAILED)){
            JOptionPane.showMessageDialog(RunClient.signupScene, base.getMessage(), base.getTitle(), JOptionPane.ERROR_MESSAGE);
        }else {
            JOptionPane.showMessageDialog(RunClient.signupScene, base.getMessage(), base.getTitle(), JOptionPane.INFORMATION_MESSAGE);
            RunClient.closeScene(RunClient.Scene.SIGNUP);
            RunClient.openScene(RunClient.Scene.LOGIN);
        }
    }

    private void onConnectHandler(BaseDTO base) {


    }

    private void onLoginHandler(String received) {
        
        // turn off loading in LOGIN SCENE
        RunClient.loginScene.setLoading(false);

        if(base.getResult().equals(SystemConstant.SUCCESS)){
            
            //save player
            player = JSONUtils.toModel(received, PlayerDTO.class);

            // turn off LoginScene + turn on ArenaHallScene
            RunClient.closeScene(RunClient.Scene.LOGIN);
            RunClient.openScene(RunClient.Scene.ARENA_HALL);
            
        }else {
            JOptionPane.showMessageDialog(RunClient.loginScene, base.getMessage(), base.getTitle(), JOptionPane.ERROR_MESSAGE);
        }

    }


    public void login(String email, String password) {
        Map<String, Object> map = new HashMap<>();
        map.put("email", email);
        map.put("password", password);
        map.put("type", StreamData.Types.LOGIN.name());
        sendRequest(JSONUtils.toJSON(map).toJSONString());
    }

    public void declinePairMatch() {
    }

    public void signup(String email, String password, String name, String gender, int yearOfBirth, String avatar) {
        Map<String, Object> map = new HashMap<>();
        map.put("email", email);
        map.put("password", MyHash.hashPBKDF2(password));
        map.put("name", name);
        map.put("gender", gender);
        map.put("yearOfBirth", yearOfBirth);
        map.put("avatar", avatar);
        map.put("type", StreamData.Types.SIGNUP.name());
        sendRequest(JSONUtils.toJSON(map).toJSONString());
    }

    public void sendRequest(String request){
        try{
            System.out.println("Request: " + request);
            out.println(request);
        }catch (Exception e){
            System.out.println("Catch send");
        }
    }

    public String sendMessage(String msg){
        String resp = null;
        try {
            out.println(msg);
            resp = in.readLine();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return resp;
    }

    public void stopConnection(){
        try {
            clientSocket.close();
            in.close();
            out.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public PlayerDTO getPlayer() {
        return player;
    }

    public void setPlayer(PlayerDTO player) {
        this.player = player;
    }

    public BaseDTO getBase() {
        return base;
    }

    public void setBase(BaseDTO base) {
        this.base = base;
    }

    public void logout() {
        Map<String, Object> map = new HashMap<>();
        map.put("type", StreamData.Types.LOGOUT.name());
        sendRequest(JSONUtils.toJSON(map).toJSONString());
    }

    public void listChamber() {
    }

    private void closeEverything(Object ... params) throws IOException {
        for (Object ob : params){
            if(ob instanceof Socket){
                ((Socket) ob).close();
            }
            if(ob instanceof PrintWriter){
                ((PrintWriter) ob).close();
            }
            if(ob instanceof BufferedReader){
                ((BufferedReader) ob).close();
            }
        }
    }

    public void getProfile(String email) {
        Map<String, Object> map = new HashMap<>();
        map.put("email", email);
        map.put("type", StreamData.Types.LOGOUT.name());
        sendRequest(JSONUtils.toJSON(map).toJSONString());
    }

    public void updateProfile(Long id, String email, String name, String avatar, String yearOfBirth, String gender) {
        Map<String, Object> map = new HashMap<>();
        map.put("id", id);
        map.put("email", email);
        map.put("name", name);
        map.put("gender", gender);
        map.put("yearOfBirth", yearOfBirth);
        map.put("avatar", avatar);
        map.put("type", StreamData.Types.UPDATE_PROFILE.name());
        sendRequest(JSONUtils.toJSON(map).toJSONString());
    }

    public void changePassword(String oldPass, String newPass) {
        Map<String, Object> map = new HashMap<>();
        map.put("originalPass", newPass);
        map.put("storedPass", oldPass);
        map.put("type", StreamData.Types.CHANGE_PASSWORD.name());
        sendRequest(JSONUtils.toJSON(map).toJSONString());
    }

    public void chatRoom(String chatMsg) {
        Map<String, Object> map = new HashMap<>();
        map.put("content", chatMsg);
        map.put("type", StreamData.Types.CHAT_ROOM.name());
        sendRequest(JSONUtils.toJSON(map).toJSONString());
    }

    public void move(int row, int column) {
        Map<String, Object> map = new HashMap<>();
        map.put("row", row);
        map.put("column", column);
        map.put("inType", StreamData.Types.MOVE.name());
        map.put("type", StreamData.Types.GAME_EVENT.name());
        sendRequest(JSONUtils.toJSON(map).toJSONString());
    }

    // game event
    public void sendGameEvent(String gameEventData) {
    }

    public void leaveRoom() {
    }

    public void findMatch() {
        Map<String, Object> map = new HashMap<>();
        map.put("type", StreamData.Types.FIND_MATCH.name());
        sendRequest(JSONUtils.toJSON(map).toJSONString());
    }

    public void acceptPairMatch() {
        Map<String, Object> map = new HashMap<>();
        map.put("result", SystemConstant.SUCCESS);
        map.put("type", StreamData.Types.REQUEST_PAIR_MATCH.name());
        sendRequest(JSONUtils.toJSON(map).toJSONString());
    }

    public String initAESCrypt(String rawString, String type) throws NoSuchAlgorithmException,
            IllegalBlockSizeException, InvalidKeyException,
            BadPaddingException, InvalidAlgorithmParameterException,
            NoSuchPaddingException
    {
        SecretKey key = AESUtils.generateKey(128);
        IvParameterSpec iv = AESUtils.generateIv(16);

        String cipherText = AESUtils.encrypt(rawString, key, iv);

        Map<String, Object> map = new HashMap<>();
        map.put("key", key);
        map.put("iv", iv);
        map.put("cipherText", cipherText);
        map.put("type", type);
        return JSONUtils.toJSON(map).toJSONString();
    }

}
