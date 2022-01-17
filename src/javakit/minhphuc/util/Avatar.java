package javakit.minhphuc.util;

import java.io.File;

public class Avatar {

    public static final String PATH = "src/javakit/minhphuc/caro/client/view/asset/avatar/";
    public static final String ASSET_PATH = "src/javakit/minhphuc/caro/client/view/asset/";
    public static final String EMPTY_AVATAR = "icons8_confusion_96px.png";

    public static String[] getAll(){
        File file = new File("C:/Users/Reason/OneDrive/Máy tính/Caro_Java_Socket/src/javakit/minhphuc/caro/client/view/asset/avatar/");
        String[] fileList = file.list();
        return fileList;
    }

    public static String getOne(String path){
        String[] names = path.split("/");
        return names[names.length - 1];
    }


}
