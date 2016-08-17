package com.joker.core.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * Created by crell on 2016/1/19.
 * MD5 加密工具类
 */
public class EncryptUtil {

    //private static Logger logger = LoggerFactory.getLogger(EncryptUtil.class);

    private static final String ALGORITHM_MD5 = "MD5";

    /**
     * Encrypt the password with MD5
     * @param pass the password to encryption
     * @return encryption string
     */
    public static String doEncrypt(String pass) {
       /* String keys = null;
        try {
            MessageDigest md = MessageDigest.getInstance(ALGORITHM_MD5);
            if (pass == null) {
                pass = "";
            }
            byte[] bPass = pass.getBytes("UTF-8");
            md.update(bPass);
            keys = bytesToHexString(md.digest());
        }
        catch (NoSuchAlgorithmException aex) {
            LogUtil.error("there is no " + ALGORITHM_MD5 + " Algorithm!");
        }
        catch (java.io.UnsupportedEncodingException uex) {
            LogUtil.error("can not encode the password - " + uex.getMessage());
        }
        return keys;*/
    	return pass;
    }
    
    
    /*** 
     * MD5加密 生成32位md5码
     * @param 待加密字符串
     * @return 返回32位md5码
     */
    public static String md5Encode(String inStr) throws Exception {
        MessageDigest md5 = null;
        try {
            md5 = MessageDigest.getInstance("MD5");
        } catch (Exception e) {
            System.out.println(e.toString());
            e.printStackTrace();
            return "";
        }

        byte[] byteArray = inStr.getBytes("UTF-8");
        byte[] md5Bytes = md5.digest(byteArray);
        StringBuffer hexValue = new StringBuffer();
        for (int i = 0; i < md5Bytes.length; i++) {
            int val = ((int) md5Bytes[i]) & 0xff;
            if (val < 16) {
                hexValue.append("0");
            }
            hexValue.append(Integer.toHexString(val));
        }
        return hexValue.toString();
    }
    
    /*** 
     * SHA加密 生成40位SHA码
     * @param 待加密字符串
     * @return 返回40位SHA码
     */
    public static String shaEncode(String inStr) throws Exception {
        MessageDigest sha = null;
        try {
            sha = MessageDigest.getInstance("SHA");
        } catch (Exception e) {
            System.out.println(e.toString());
            e.printStackTrace();
            return "";
        }

        byte[] byteArray = inStr.getBytes("UTF-8");
        byte[] md5Bytes = sha.digest(byteArray);
        StringBuffer hexValue = new StringBuffer();
        for (int i = 0; i < md5Bytes.length; i++) {
            int val = ((int) md5Bytes[i]) & 0xff;
            if (val < 16) { 
                hexValue.append("0");
            }
            hexValue.append(Integer.toHexString(val));
        }
        return hexValue.toString();
    }

    /**
     * 将beye[]转换为十六进制字符串
     * @param bArray
     * @return
     */
    public static final String bytesToHexString(byte[] bArray) {
        StringBuffer sb = new StringBuffer(bArray.length);
        String sTemp;
        for (int i = 0; i < bArray.length; i++) {
            sTemp = Integer.toHexString(0xFF & bArray[i]);
            if (sTemp.length() < 2){
                sb.append(0);
            }
            sb.append(sTemp.toUpperCase());
        }
        return sb.toString();
    }

}
