package javakit.minhphuc.caro.server;

import javakit.minhphuc.caro.server.controller.Admin;
import javakit.minhphuc.caro.server.controller.Client;
import javakit.minhphuc.caro.server.controller.ClientManage;
import javakit.minhphuc.util.SystemConstant;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

public class RunServer {
    private Socket socket = null;
    private ServerSocket server = null;


    public RunServer(){
        try {
            server = new ServerSocket(SystemConstant.PORT);
            System.out.println("Server started!");
            ThreadPoolExecutor threadPool = new ThreadPoolExecutor(
                    10,
                    100,
                    10,
                    TimeUnit.SECONDS,
                    new ArrayBlockingQueue<>(8)
            );
            threadPool.execute(new Admin());

            while(!SystemConstant.IS_SHUTDOWN){
                socket = server.accept();
                System.out.println("New Client connect!!!");

                Client client = new Client(socket);
                ClientManage.addClient(client);

                threadPool.execute(client);
            }

            System.out.println("ThreadPool shutdown!!!");
            threadPool.shutdownNow();

        } catch (IOException e) {
            e.printStackTrace();
            SystemConstant.IS_SHUTDOWN = true;
        }

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


    public static void main(String[] args) {
        RunServer server = new RunServer();
    }


}
