
package javakit.minhphuc.caro.server.controller;


import javakit.minhphuc.caro.server.game.Caro;
import javakit.minhphuc.caro.server.game.History;
import javakit.minhphuc.dto.BaseDTO;
import javakit.minhphuc.dto.CaroDTO;
import javakit.minhphuc.dto.GameMatchDTO;
import javakit.minhphuc.dto.PlayerDTO;
import javakit.minhphuc.service.impl.GameMatchService;
import javakit.minhphuc.service.impl.PlayerService;
import javakit.minhphuc.util.JSONUtils;
import javakit.minhphuc.util.StreamData;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.concurrent.Callable;


public class Room {

    String id;
    Caro gameLogic;
    Client Client1 = null, Client2 = null;
    ArrayList<Client> Clients = new ArrayList<>();
    boolean gameStarted = false;

    public LocalDateTime startedTime;

    public Room(String id) {
        // room id
        this.id = id;

        // create game logic
        gameLogic = new Caro();
    }

    public boolean isGameStarted() {
        return gameStarted;
    }

    public void startGame() {
        startedTime = LocalDateTime.now();
        gameStarted = true;
        BaseDTO response = new BaseDTO();
        response.setType(StreamData.Types.GAME_EVENT.name());
        CaroDTO caroDto = new CaroDTO();
        caroDto.setType(StreamData.Types.GAME_EVENT.name());
        gameLogic.getTurnTimer()
                .setTimerCallBack(
                        // end turn callback
                        (Callable) () -> {
                            // TURN_TIMER_END;<winner-email>
                            caroDto.setInType(StreamData.Types.TURN_TIMER_END.name());
                            PlayerDTO playerDTO = new PlayerService().findOneByEmail(gameLogic.getLastMoveEmail());
                            caroDto.setLastMovePlayer(playerDTO);
                            broadcast(
                                    JSONUtils.toJSON(caroDto).toJSONString()
                            );
                            return null;
                        },
                        // tick turn callback
                        (Callable) () -> {
                            caroDto.setInType(StreamData.Types.TURN_TICK.name());
                            caroDto.setCurrentTick(gameLogic.getMatchTimer().getCurrentTick());
                            broadcast(
                                    JSONUtils.toJSON(caroDto).toJSONString()
                            );
                            return null;
                        },
                        // tick interval
                        Caro.TURN_TIME_LIMIT / 10
                );

        gameLogic.getMatchTimer()
                .setTimerCallBack(
                        // end match callback
                        (Callable) () -> {

                            // tinh diem hoa
                            new GameMatchService().appendGameMatch(new GameMatchDTO(
                                    Client1.getPlayer().getId(),
                                    Client1.getPlayer().getId(),
                                    -1,
                                    gameLogic.getMatchTimer().getCurrentTick(),
                                    gameLogic.getHistory().size(),
                                    startedTime
                            ));

                            response.setInType(StreamData.Types.MATCH_TIMER_END.name());
                            broadcast(
                                    JSONUtils.toJSON(response).toJSONString()
                            );
                            return null;
                        },
                        // tick match callback
                        (Callable) () -> {
                            caroDto.setInType(StreamData.Types.MATCH_TICK.name());
                            caroDto.setCurrentTick(gameLogic.getMatchTimer().getCurrentTick());
                            broadcast(
                                    JSONUtils.toJSON(caroDto).toJSONString()
                            );
                            return null;
                        },
                        // tick interval
                        Caro.MATCH_TIME_LIMIT / 10
                );
    }

    // add/remove Client
    public boolean addClient(Client c, boolean isWatcher) {
        if (!Clients.contains(c)) {
            Clients.add(c);

            if (!isWatcher) {
                if (Client1 == null) {
                    Client1 = c;
                } else if (Client2 == null) {
                    Client2 = c;
                }
            }

            return true;
        }
        return false;
    }

    public boolean removeClient(Client c) {
        if (Clients.contains(c)) {
            Clients.remove(c);
            return true;
        }
        return false;
    }

    // broadcast messages
    public void broadcast(String msg) {
        Clients.forEach((c) -> {
            c.sendData(msg);
        });
    }

//    public void close(String reason) {
//        // notify all Client in room
//        broadcast(StreamData.Types.CLOSE_ROOM.name() + ";" + reason);
//
//        // remove reference
//        Clients.forEach((Client) -> {
//            Client.setJoinedRoom(null);
//        });
//
//        // remove all Clients
//        Clients.clear();
//
//        // remove room
//        RunServer.roomManager.remove(this);
//    }

    // get room data
    public String getFullData() {
        String data = "";

        // player data
        data += getClient12InGameData() + ";";
        data += getListClientData() + ";";
//        // timer
//        data += getTimerData() + ";";
        // board
        data += getBoardData();

        return data;
    }

    public String getTimerData() {
        String data = "";

        data += Caro.MATCH_TIME_LIMIT + ";" + gameLogic.getMatchTimer().getCurrentTick() + ";";
        data += Caro.TURN_TIME_LIMIT + ";" + gameLogic.getTurnTimer().getCurrentTick();

        return data;
    }

    public String getBoardData() {
        ArrayList<History> history = gameLogic.getHistory();

        String data = history.size() + ";";
        for (History his : history) {
            data += his.getRow() + ";" + his.getColumn() + ";" + his.getPlayerEmail() + ";";
        }

        return data.substring(0, data.length() - 1); // bỏ dấu ; ở cuối
    }

    public String getClient12InGameData() {
        String data = "";

        data += (Client1 == null ? Client.getEmptyInGameData() : Client1.getInGameData() + ";");
        data += (Client2 == null ? Client.getEmptyInGameData() : Client2.getInGameData());

        return data;
    }

    public String getListClientData() {
        // kết quả trả về có dạng playerCount;player1_data;player2_data;...;playerN_data

        String data = Clients.size() + ";";

        for (Client c : Clients) {
            data += c.getInGameData() + ";";
        }

        return data.substring(0, data.length() - 1); // bỏ dấu ; ở cuối
    }

    // gets sets
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Caro getGameLogic() {
        return gameLogic;
    }

    public void setGameLogic(Caro gameLogic) {
        this.gameLogic = gameLogic;
    }

    public void setGameStarted(boolean gameStarted) {
        this.gameStarted = gameStarted;
    }

    public LocalDateTime getStartedTime() {
        return startedTime;
    }

    public void setStartedTime(LocalDateTime startedTime) {
        this.startedTime = startedTime;
    }

    public Client getClient1() {
        return Client1;
    }

    public void setClient1(Client Client1) {
        this.Client1 = Client1;
    }

    public Client getClient2() {
        return Client2;
    }

    public void setClient2(Client Client2) {
        this.Client2 = Client2;
    }

    public ArrayList<Client> getClients() {
        return Clients;
    }

    public void setClients(ArrayList<Client> Clients) {
        this.Clients = Clients;
    }

}
