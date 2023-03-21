package ren.lawliet.java;

import java.io.File;
import java.io.IOException;
import java.math.BigInteger;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

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
                if(data[0].equals(uid)){
                    return data[1];
                }
            }
        }
        return pwd;
    }
    public static String md5(String str) throws NoSuchAlgorithmException{
        MessageDigest md = MessageDigest.getInstance("MD5");
        md.update(str.getBytes());
        String hashedPwd = new BigInteger(1, md.digest()).toString(16);
        return hashedPwd;
    }

}
