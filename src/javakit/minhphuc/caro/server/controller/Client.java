package javakit.minhphuc.caro.server.controller;


import javakit.minhphuc.caro.server.game.Caro;
import javakit.minhphuc.dto.*;
import javakit.minhphuc.service.IPlayerService;
import javakit.minhphuc.service.impl.PlayerService;
import javakit.minhphuc.util.*;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.util.ArrayList;
import java.util.Date;
import java.util.stream.Stream;

public class Client implements Runnable {

    private Socket socket;
    private PrintWriter out;
    private BufferedReader in;

    private PlayerDTO player;
    private BaseDTO base;

    private Client rival;
    private Room joinedRoom;
    private boolean findingMatch = false;

    private IPlayerService playerService;

    public Client(){

    }

    public Client(Socket socket){
        this.socket = socket;

        this.playerService = new PlayerService();

        this.player = new PlayerDTO();
        this.base = new BaseDTO();
        this.joinedRoom = new Room("52349");

        try {
            in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            out = new PrintWriter(socket.getOutputStream(), true);
        } catch (IOException e) {
            try {
                closeEverything(socket, in, out);
            } catch (IOException ioException) {
                ioException.printStackTrace();
            }
        }
    }

    @Override
    public void run() {
        String received = null;
        while (!socket.isClosed()){
            try {
                received = in.readLine();
                if(received != null){
                    System.out.println("Request: " + received);

                    base = JSONUtils.toModel(received, BaseDTO.class);

                    StreamData.Types type = StreamData.getType(base.getType());

                    switch (type){
                        case CONNECT_SERVER:
                            onConnectHandler(received);
                            break;
                        case LOGIN:
                            onLoginHandler(received);
                            break;
                        case SIGNUP:
                            onSignupHandler(received);
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
                            onUpdateProfileHandler(received);
                            break;
                        case CHANGE_PASSWORD:
                            onChangePassHandler(received);
                            break;
                        case FIND_MATCH:
                            onFindMatchHandler(received);
                            break;
                        case REQUEST_PAIR_MATCH:
                            onRequestPairMatchHandler(received);
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
                    closeEverything(socket, in, out);
                } catch (IOException ioException) {
                    ioException.printStackTrace();
                }
                break;
            }
        }

    }

    // game event
    private void onGameEventHandler(String received) {
        PointDTO point = JSONUtils.toModel(received, PointDTO.class);

        StreamData.Types gameEventType = StreamData.getType(point.getInType());

        Caro caroGame = (Caro) joinedRoom.getGameLogic();

        switch (gameEventType) {
            case MOVE:
                // lượt đi đầu tiên sẽ bắt đầu game
                if (!joinedRoom.isGameStarted()) {
                    joinedRoom.startGame();
                    CaroDTO caroDto = new CaroDTO();
                    caroDto.setType(StreamData.Types.GAME_EVENT.name());
                    caroDto.setInType(StreamData.Types.START.name());
                    caroDto.setMATCH_TIME_LIMIT(Caro.MATCH_TIME_LIMIT);
                    caroDto.setTURN_TIME_LIMIT(Caro.TURN_TIME_LIMIT);
                    joinedRoom.broadcast(
                            JSONUtils.toJSON(caroDto).toJSONString()
                    );
                }

                // get row/col data
                int row = point.getRow();
                int column = point.getColumn();

                // check move
                if (caroGame.move(row, column, this.player.getEmail())) {
                    // restart turn timer
                    joinedRoom.gameLogic.restartTurnTimer();

                    // broadcast to all Client in room movedata
                    PointDTO pointData = new PointDTO();
                    pointData.setType(StreamData.Types.GAME_EVENT.name());
                    pointData.setInType(StreamData.Types.MOVE.name());
                    pointData.setRow(row);
                    pointData.setColumn(column);
                    pointData.setOwner(this.player.getEmail());

                    joinedRoom.broadcast(
                             JSONUtils.toJSON(pointData).toJSONString()
                    );

                    // check win
                    Line winPath = caroGame.CheckWin(row, column);
                    if (winPath != null) {

                        PlayerDTO winner = this.player;
                        PlayerDTO loser = rival.player;

//                        // tinh diem
//                        winner.addScore(3);
//                        winner.setWinCount(winner.getWinCount() + 1);
//                        loser.addScore(-2);
//                        loser.setLoseCount(loser.getLoseCount() - 1);
//                        playerService.save(winner);
//                        playerService.save(loser);

//                        // TODO luu game match
//                        new GameMatchBUS().add(new GameMatch(
//                                winner.getId(),
//                                loser.getId(),
//                                winner.getId(),
//                                Caro.MATCH_TIME_LIMIT - ((Caro) joinedRoom.getgameLogin()).getMatchTimer().getCurrentTick(),
//                                ((Caro) joinedRoom.getgameLogin()).getHistory().size(),
//                                joinedRoom.startedTime
//                        ));

                        // stop game timer
                        caroGame.cancelTimer();

                        // broadcast to all Client in room windata
                        PlayerDTO winPlayer = this.player;
                        winPlayer.setType(StreamData.Types.GAME_EVENT.name());
                        winPlayer.setInType(StreamData.Types.WIN.name());

                        joinedRoom.broadcast(
                                JSONUtils.toJSON(winPlayer).toJSONString()
                        );

                    }
                } else {
                    // do nothing
                }
                break;
            default:
        }
    }


    private void onChatRoomHandler(String received) {
        PlayerChatDTO playerChat = JSONUtils.toModel(received, PlayerChatDTO.class);
        playerChat.setName(this.player.getName());
        playerChat.setTime(new Date());
        playerChat.setType(StreamData.Types.CHAT_ROOM.name());

        this.sendData(JSONUtils.toJSON(playerChat).toJSONString());
        this.rival.sendData(JSONUtils.toJSON(playerChat).toJSONString());

    }

    private void onRequestPairMatchHandler(String received) {
//        boolean waitRival = true;
//        while (waitRival){
//            if (rival.base.getType().equals(StreamData.Types.REQUEST_PAIR_MATCH.name())){
//                if(this.base.getResult().equals(SystemConstant.SUCCESS) &&
//                        rival.base.getResult().equals(SystemConstant.SUCCESS))
//                {
////                    java.util.List<PlayerDTO> list = new ArrayList<>();
////                    list.add(this.player);
////                    list.add(rival.player);
////                    PlayersDTO players = new PlayersDTO();
////                    players.setLists(list);
////                    players.setResult(SystemConstant.SUCCESS);
////                    players.setType(StreamData.Types.RESULT_PAIR_MATCH.name());
////                    this.sendData(JSONUtils.toJSON(players).toJSONString());
//
//                    PlayerDTO request = rival.getPlayer();
//                    request.setResult(SystemConstant.SUCCESS);
//                    request.setType(StreamData.Types.RESULT_PAIR_MATCH.name());
//
//                    this.sendData(JSONUtils.toJSON(request).toJSONString());
//                    waitRival = false;
//                    return;
//                }
//            }
//        }
        String requestRival = rival.base.getResult();

        boolean acceptRival = requestRival != null && requestRival.equals(SystemConstant.SUCCESS);
        boolean acceptMe = this.base.getResult().equals(SystemConstant.SUCCESS);

        if( acceptMe && acceptRival )
        {
            PlayerDTO request = rival.getPlayer();
            request.setResult(SystemConstant.SUCCESS);
            request.setType(StreamData.Types.RESULT_PAIR_MATCH.name());
            this.joinedRoom = RoomManage.addRoom("4465");
            this.rival.joinedRoom = this.joinedRoom;
            this.joinedRoom.addClient(this.rival, false);
            this.joinedRoom.addClient(this, false);
            this.sendData(JSONUtils.toJSON(request).toJSONString());

            request = this.player;
            request.setResult(SystemConstant.SUCCESS);
            request.setType(StreamData.Types.RESULT_PAIR_MATCH.name());
//            this.joinedRoom.addClient(this, false);
//            this.rival.joinedRoom.addClient(this, false);
            rival.sendData(JSONUtils.toJSON(request).toJSONString());


        }


    }

    private void onFindMatchHandler(String received) {
        PlayerDTO request = new PlayerDTO();

        findingMatch = true;
        while (findingMatch){
            Client candidate = ClientManage.findClientMatch(this);

            if(candidate != null){
                //
                this.findingMatch = false;
                candidate.findingMatch = false;
                //
                this.rival = candidate;
                candidate.rival = this;

                request = rival.getPlayer();
                request.setType(StreamData.Types.REQUEST_PAIR_MATCH.name());
                request.setResult(SystemConstant.SUCCESS);
                this.sendData(JSONUtils.toJSON(request).toJSONString());

                request = this.player;
                request.setType(StreamData.Types.REQUEST_PAIR_MATCH.name());
                request.setResult(SystemConstant.SUCCESS);
                candidate.sendData(JSONUtils.toJSON(request).toJSONString());
                return;
            }
        }




    }

    private void onGetProfileHandler(String received) {
        ProfileDTO temp = JSONUtils.toModel(received, ProfileDTO.class);

        PlayerDTO response= playerService.findOneByEmail(temp.getEmail());
        if(response == null){
            response.setResult(SystemConstant.FAILED);
            response.setTitle(SystemConstant.GET_PROFILE_FAILED);
            response.setMessage(SystemConstant.ACCOUNT_NOT_FOUND);
        }else {
            response.setResult(SystemConstant.SUCCESS);
            response.setTitle(SystemConstant.GET_PROFILE_SUCCESS);
            response.setMessage(SystemConstant.SUCCESS);
        }
        response.setType(StreamData.Types.GET_PROFILE.name());
        sendData(JSONUtils.toJSON(response).toJSONString());
    }

    private void onLogoutHandler(String received) {
        // clear data
        this.player = new PlayerDTO();

        //
        base.setResult(SystemConstant.SUCCESS);
        sendData(JSONUtils.toJSON(base).toJSONString());
    }

    private void onChangePassHandler(String received) {
        PassDTO pass = JSONUtils.toModel(received, PassDTO.class);

        try {
            if(MyHash.validatePassPBKDF2(pass.getStoredPass(), player.getPassword())){
                player.setPassword(MyHash.hashPBKDF2(pass.getOriginalPass()));
                player.setResult(SystemConstant.SUCCESS);
                player.setTitle(SystemConstant.CHANGE_PASS_SUCCESS);
                playerService.save(player);
            }else {
//                player.setMessage(SystemConstant.);
                pass.setResult(SystemConstant.FAILED);
                player.setTitle(SystemConstant.CHANGE_PASS_FAILED);
            }
        } catch (NoSuchAlgorithmException | InvalidKeySpecException e) {
            e.printStackTrace();
        }
        player.setType(StreamData.Types.CHANGE_PASSWORD.name());

        sendData(JSONUtils.toJSON(player).toJSONString());

    }

    private void onUpdateProfileHandler(String received) {
        PlayerDTO quest = JSONUtils.toModel(received, PlayerDTO.class);

        if(!quest.getEmail().equals(player.getEmail())){
            if (playerService.findOneByEmail(quest.getEmail()) != null){
                quest.setMessage(SystemConstant.ACCOUNT_EXISTS);
                quest.setResult(SystemConstant.FAILED);
                quest.setTitle(SystemConstant.UPDATE_PROFILE_FAILED);
            }
        }else {
            playerService.saveProfile(quest);
            quest = playerService.findOneByEmail(quest.getEmail());
            quest.setResult(SystemConstant.SUCCESS);
            quest.setTitle(SystemConstant.UPDATE_PROFILE_SUCCESS);
        }
        this.player = quest;
        quest.setType(StreamData.Types.UPDATE_PROFILE.name());
        sendData(JSONUtils.toJSON(quest).toJSONString());
    }

    private void onArenaHallHandler(String received) {
    }

    private void onSignupHandler(String received) {
        PlayerDTO quest = JSONUtils.toModel(received, PlayerDTO.class);

        if (playerService.findOneByEmail(quest.getEmail()) != null){
            quest.setMessage(SystemConstant.ACCOUNT_EXISTS);
            quest.setResult(SystemConstant.FAILED);
            quest.setTitle(SystemConstant.SIGNUP_FAILED);
        }else {
            playerService.save(quest);
            quest.setResult(SystemConstant.SUCCESS);
            quest.setTitle(SystemConstant.SIGNUP_SUCCESS);
        }
        sendData(JSONUtils.toJSON(quest).toJSONString());
    }

    private void onConnectHandler(String received) {


    }

    private void onLoginHandler(String received) {
        PlayerDTO guest = JSONUtils.toModel(received, PlayerDTO.class);

        // Check ACCOUNT_LOGGED_ON
        if(ClientManage.checkOnline(guest.getEmail())){
            guest.setResult(SystemConstant.FAILED);
            guest.setMessage(SystemConstant.ACCOUNT_LOGGED_ON);
            guest.setTitle(SystemConstant.LOGIN_FAILED);
        }else {
            // Find ACCOUNT in database
            PlayerDTO temp = playerService.findOneByEmailAndPass(guest.getEmail(), guest.getPassword());
            if(temp != null){ // check EXIST ACCOUNT
                guest = temp;
                if(guest.getStatus() == 0){ // Check ACCOUNT_BLOCKED
                    guest.setMessage(SystemConstant.ACCOUNT_BLOCKED);
                    guest.setResult(SystemConstant.FAILED);
                    guest.setTitle(SystemConstant.LOGIN_FAILED);
                }else {
                    guest.setOnl(true);
                    guest.setResult(SystemConstant.SUCCESS);
                    guest.setTitle(SystemConstant.LOGIN_SUCCESS);
                }
            }else {
                guest.setMessage(SystemConstant.ACCOUNT_NOT_FOUND);
                guest.setResult(SystemConstant.FAILED);
                guest.setTitle(SystemConstant.LOGIN_FAILED);
            }
        }
        guest.setType(StreamData.Types.LOGIN.name());
        this.player = guest;
        sendData(JSONUtils.toJSON(this.player).toJSONString());
    }

    public void sendData(String data){
        System.out.println("Response: " + data);
        out.println(data);
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

    private void closeEverything(Object ... params) throws IOException {
        ClientManage.removeClient(this);
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

    public boolean isFindingMatch() {
        return findingMatch;
    }

    public void setFindingMatch(boolean findingMatch) {
        this.findingMatch = findingMatch;
    }

    // get set
    public static String getEmptyInGameData() {
        return ";;";
    }

    public String getInGameData() {
        if (this.player == null) {
            return getEmptyInGameData(); // trả về rỗng
        }
        return this.player.getEmail() + ";" + this.player.getName() + ";" + this.player.getAvatar();
    }


}
