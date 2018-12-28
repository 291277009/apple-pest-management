package com.manage.applepestmanagement.utils;




import org.apache.commons.codec.binary.Base64;

import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.DESKeySpec;
import java.security.MessageDigest;
import java.security.SecureRandom;


/** 
 * 采用MD5加密解密
 * 此类用于生成能力平台TOKEN值
 * @author 
 * @datetime 
 */  
public class MD5Util {  
  
    /*** 
     * MD5加码 生成32位md5码 
     */  
    public static String string2MD5(String inStr){  
        MessageDigest md5 = null;  
        try{  
            md5 = MessageDigest.getInstance("MD5");  
        }catch (Exception e){  
            System.out.println(e.toString());  
            e.printStackTrace();  
            return "";  
        }  
        char[] charArray = inStr.toCharArray();  
        byte[] byteArray = new byte[charArray.length];  
  
        for (int i = 0; i < charArray.length; i++)  
            byteArray[i] = (byte) charArray[i];  
        byte[] md5Bytes = md5.digest(byteArray);
        StringBuffer hexValue = new StringBuffer();  
        for (int i = 0; i < md5Bytes.length; i++){  
            int val = ((int) md5Bytes[i]) & 0xff;  
            if (val < 16)  
                hexValue.append("0");  
            hexValue.append(Integer.toHexString(val));  
        }  
        return hexValue.toString();  
  
    }  
  
    /** 
     * 加密解密算法 执行一次加密，两次解密 
     */   
    public static String convertMD5(String inStr){  
  
        char[] a = inStr.toCharArray();  
        for (int i = 0; i < a.length; i++){  
            a[i] = (char) (a[i] ^ 't');  
        }  
        String s = new String(a);  
        return s;  
  
    }  
    /**
     * tokenValue值的生成
     */
    public static String tokenValue(String app_id,String timestamp,String trans_id,String app_secret){
    	String tokenValue = "";
    	StringBuffer bf = new StringBuffer();
    	bf.append("APP_ID"+app_id);
    	bf.append("TIMESTAMP"+timestamp);
    	bf.append("TRANS_ID"+trans_id);
    	bf.append(app_secret);
    	String s = bf.toString();
    	tokenValue = string2MD5(s);
    	return tokenValue;
    }

    /**
     * 加密方法
     * @param data 需要加密的字符串
     * @param key 加密秘钥
     * @return 加密后的密文
     */
    public static String encrypt(String data, String key) throws Exception {
        SecureRandom sr = new SecureRandom();
        DESKeySpec dks = new DESKeySpec(key.getBytes());
        SecretKeyFactory keyFactory = SecretKeyFactory.getInstance("DES");
        SecretKey secureKey = keyFactory.generateSecret(dks);
        Cipher cipher = Cipher.getInstance("DES");
        cipher.init(Cipher.ENCRYPT_MODE, secureKey, sr);
        byte[] bt = cipher.doFinal(data.getBytes("UTF-8"));
        return Base64.encodeBase64String(bt);
    }

    /**
     * 解密方法
     * @param data 需要解密的密文
     * @param key 解密秘钥
     * @return 解密后的明文
     */
    public static String decrypt(String data, String key) throws Exception {
        if (data == null){
            return null;
        }
        SecureRandom sr = new SecureRandom();
        DESKeySpec dks = new DESKeySpec(key.getBytes());
        SecretKeyFactory keyFactory = SecretKeyFactory.getInstance("DES");
        SecretKey secureKey = keyFactory.generateSecret(dks);
        Cipher cipher = Cipher.getInstance("DES");
        cipher.init(Cipher.DECRYPT_MODE, secureKey, sr);
        byte[] bt = cipher.doFinal(Base64.decodeBase64(data));
        return new String(bt, "UTF-8");
    }

    // 测试主函数  
    public static void main(String args[]) {  
    	String a = MD5Util.tokenValue("KFpTpXKcUd", "2018-10-16 11:03:27", "20180917150606100336456", "fJiAxcQFfb4fRtNKFSBC4hrAAh8e1ZcT");//密钥生产测试不一样
    	System.out.println(a);
  
    }  
}  
