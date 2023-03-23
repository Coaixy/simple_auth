package ren.lawliet.java;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.math.BigInteger;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Random;

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

    public static void addData(String str) throws IOException{
        FileOutputStream fos = null;
        OutputStreamWriter osw = null;
        try{
            fos = new FileOutputStream(file,true);
            osw = new OutputStreamWriter(fos,"UTF-8");
            osw.write(str);
            osw.write("\n");

        }catch(Exception e){
            e.printStackTrace();
        }finally{
            osw.close();
            fos.close();
        }
    }
    public static String makeToken() throws NoSuchAlgorithmException {
        String token = (System.currentTimeMillis() + new Random().nextInt(999999999)) + "";
        token = md5(token);
        return token;
    }

}
