package javakit.minhphuc.caro.client;

import javakit.minhphuc.caro.client.controller.ClientController;
import javakit.minhphuc.caro.client.view.*;

public class RunClient {

    public static ConnectServer connectServer;
    public static Login loginScene;
    public static Signup signupScene;
    public static MainMenu arenaHall;
    public static Profile profileScene;
    public static ChangePassword changePass;
    public static InGame inGameScene;


    public static ClientController clientController;


    public enum Scene{
        CONNECT_SERVER,
        LOGIN,
        SIGNUP,
        ARENA_HALL,
        PROFILE,
        CHANGE_PASSWORD,
        IN_GAME
    }



    public RunClient(){
        clientController = new ClientController();
        openScene(Scene.CONNECT_SERVER);
    }

    private void initScene(){

    }


    // Open Views
    public static void openScene(Scene scene){
        if (scene != null) {
            switch (scene) {
                case CONNECT_SERVER:
                    connectServer = new ConnectServer();
                    connectServer.setVisible(true);
                    break;
                case LOGIN:
                    loginScene = new Login();
                    loginScene.setVisible(true);
                    break;
                case SIGNUP:
                    signupScene = new Signup();
                    signupScene.setVisible(true);
                    break;
                case ARENA_HALL:
                    arenaHall = new MainMenu();
                    arenaHall.setVisible(true);
                    break;
                case PROFILE:
                    profileScene = new Profile();
                    profileScene.setVisible(true);
                    break;
                case CHANGE_PASSWORD:
                    changePass = new ChangePassword();
                    changePass.setVisible(true);
                    break;
                case IN_GAME:
                    inGameScene = new InGame();
                    inGameScene.setVisible(true);
                    break;
                default:
            }
        }
    }


    public static void closeScene(Scene scene){
        if(scene != null){
            switch (scene) {
                case CONNECT_SERVER:
                    connectServer.dispose();
                    break;
                case LOGIN:
                    loginScene.dispose();
                    break;
                case SIGNUP:
                    signupScene.dispose();
                    break;
                case ARENA_HALL:
                    arenaHall.dispose();
                    break;
                case PROFILE:
                    profileScene.dispose();
                    break;
                case CHANGE_PASSWORD:
                    changePass.dispose();
                    break;
                case IN_GAME:
                    inGameScene.dispose();
                    break;
                default:
            }
        }
    }

    public static void closeAll() {
        connectServer.dispose();
        loginScene.dispose();
        signupScene.dispose();
        arenaHall.dispose();
        profileScene.dispose();
        changePass.dispose();
        inGameScene.dispose();
    }


    public static void main(String[] args) {

        new RunClient();
    }

}
