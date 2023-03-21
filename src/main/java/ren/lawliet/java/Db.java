package ren.lawliet.java;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

public class Db {
    static File file = null;
    static void init() throws IOException{
        file = new File("db");
        if(!file.exists()){
            file.createNewFile();
        }
    }
    public static String getPwd(String uid) throws IOException{
        var pwd = "";
        var context  =  Files.readString(Paths.get(file.getAbsolutePath()));
        for (var line : context.split("\n")) {
            var data = line.split("-");
            if(data.length == 2){
                if(data[0].equalsIgnoreCase(uid)){
                    return data[1];
                }
            }
        }
        return pwd;
    }

}
