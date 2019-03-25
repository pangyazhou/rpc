package com.ligoo.rpc.spring;

import java.io.UnsupportedEncodingException;
import java.util.Calendar;
import java.util.Date;

/**
 * @Author: Administrator
 * @Date: 2019/3/11 10:38:44
 * @Description:
 */
public class TEst {
    public static void main(String[] args){
        /*Calendar calendar = Calendar.getInstance();
        calendar.setTime(new Date());
        System.out.println(calendar.get(Calendar.YEAR));
        System.out.println(calendar.get(Calendar.MONTH) + 1);
        System.out.println(calendar.get(Calendar.DATE));*/
        System.out.println(numToString(1));
        System.out.println(numToString(12));
        System.out.println(numToString(123));
        System.out.println(numToString(1234));
        System.out.println(getKaNaiDateString());
    }

    public static String numToString(int num){
        num += 1;
        String result = num + "";
        while (result.length() < 4){
            result = "0" + result;
        }
        return result;
    }
    public static String getKaNaiDateString(){
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(new Date());
        String year = calendar.get(Calendar.YEAR) + "";
        String month = 11 + "";
        String date = calendar.get(Calendar.DATE) + "";
        year = year.substring(2,4);
        month = Integer.toHexString(Integer.parseInt(month)).toUpperCase();
        return year + month + date;
    }
}
