package com.bootdo.system.utils;

import java.security.MessageDigest;
import java.util.Calendar;
import java.util.Random;

/**
 * Copyright (C) 2014-2016 天津紫藤科技有限公司. Co. Ltd. All Rights Reserved.
 *
 * @author Andy
 * @version v1
 * @description 随机数生产工具
 * @serve
 * @module
 * @date 2016年12月27日
 * @code
 */
public class RandomUtils {
    private static final String ALL_CHAR = "0123456789abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ";
    private static final String LETTER_CHAR = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ";
    private static final String NUMBER_CHAR = "0123456789";



    /**
     * 获取定长的随机数，包含大小写、数字
     *
     * @param length 随机数长度
     * @return
     * @autor:chenssy
     * @date:2014年8月11日
     */
    public static String generateString(int length) {
        StringBuffer sb = new StringBuffer();
        Random random = new Random();
        for (int i = 0; i < length; i++) {
            sb.append(ALL_CHAR.charAt(random.nextInt(ALL_CHAR.length())));
        }
        return sb.toString();
    }

    /**
     * 获取定长的随机数，包含大小写字母
     *
     * @param length 随机数长度
     * @return
     * @autor:chenssy
     * @date:2014年8月11日
     */
    public static String generateMixString(int length) {
        StringBuffer sb = new StringBuffer();
        Random random = new Random();
        for (int i = 0; i < length; i++) {
            sb.append(LETTER_CHAR.charAt(random.nextInt(LETTER_CHAR.length())));
        }
        return sb.toString();
    }

    /**
     * 获取定长的随机数，只包含小写字母
     *
     * @param length 随机数长度
     * @return
     * @autor:chenssy
     * @date:2014年8月11日
     */
    public static String generateLowerString(int length) {
        return generateMixString(length).toLowerCase();
    }

    /**
     * 获取定长的随机数，只包含大写字母
     *
     * @param length 随机数长度
     * @return
     * @autor:chenssy
     * @date:2014年8月11日
     */
    public static String generateUpperString(int length) {
        return generateMixString(length).toUpperCase();
    }

    /**
     * 获取定长的随机数，只包含数字
     *
     * @param length 随机数长度
     * @return
     * @autor:chenssy
     * @date:2014年8月11日
     */
    public static String generateNumberString(int length) {
        StringBuffer sb = new StringBuffer();
        Random random = new Random();
        for (int i = 0; i < length; i++) {
            sb.append(NUMBER_CHAR.charAt(random.nextInt(NUMBER_CHAR.length())));
        }
        return sb.toString();
    }

    public static String getNewAccount(){
        String t1 = "1";
        String t2=getNumberForLength(String.valueOf((int)(Math.random()*(10000))),5);
        //String t3 = "2";
        return t1 + t2;
    }

    /**
     * 生成订单号
     * @return
     */
    public static String orderNo(){
        int r1=(int)(Math.random()*(10));//产生2个0-9的随机数
        int r2=(int)(Math.random()*(10));
        long now = System.currentTimeMillis();//一个13位的时间戳
        return String.valueOf(r1)+String.valueOf(r2)+String.valueOf(now);
    }


    /**           8 + 4 +3 + 7 + 6 = 28
     * 订单编号： 年月日 + 1000 + 3位随机数 + 时分毫秒 + 6位随机数 = 27位数字
     * @return
     *
     * 20180425 1000 963 115642  659552
     * 20180425 1000 932 1157578 816113
     *
     */
    public static String getOrderNo(){

        Calendar now = Calendar.getInstance();
        String t1 = String.valueOf("" + now.get(Calendar.YEAR) + appendZero(now.get(Calendar.MONTH) + 1) + appendZero(now.get(Calendar.DATE)));
        String t2 = "1000";
        String t3=getNumberForLength(String.valueOf((int)(Math.random()*(1000))),3);
        String t4 = getNumberForLength(String.valueOf("" + appendZero(now.get(Calendar.HOUR)) + appendZero(now.get(Calendar.MINUTE)) + now.get(Calendar.MILLISECOND)),7);
        String t5=getNumberForLength(String.valueOf((int)(Math.random()*(1000000))),6);
        return t1+t2+t3+t4+t5;
    }
    private static String getNumberForLength(String str,Integer len){
        return   String.format("%0" + len + "d", Integer.parseInt(str) + 1);
    }

    /**
     * 不足2位补0
     * @return
     */
    private static String appendZero(Integer tar){
        return  tar < 10 ? "0" + tar : String.valueOf(tar);
    }

    /**
     * MD5序列化字符串 加密
     * @param inStr
     * @return
     */
    public static String getMD5Value(String inStr){
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
        return hexValue.toString().toUpperCase();
    }
}
