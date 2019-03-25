package com.ligoo.rpc.zookeeper.util;


/**
 * @Author: Administrator
 * @Date: 2018/12/12 15:33:21
 * @Description:转型操作工具类
 */
public final class CastUtil {
    /**
     * description: 转为String类型
     * author: Administrator
     * date: 2018/12/12 15:38
     *
     * @param: 
     * @return: 
     */
    public static String castString(Object obj){
        return CastUtil.castString(obj, "");
    }
    /**
     * description: 转为String类型,提供默认值
     * author: Administrator
     * date: 2018/12/12 15:38
     *
     * @param defaultValue 提供的默认值
     * @return:
     */

    public static String castString(Object obj, String defaultValue){
        return obj != null ? String.valueOf(obj) : defaultValue;
    }

    public static double castDouble(Object obj){
        return CastUtil.castDouble(obj, 0) ;
    }

    public static double castDouble(Object obj, double defaultValue){
        double doubleValue = defaultValue;
        if(obj != null){
            String strValue = CastUtil.castString(obj);
            if(StringUtil.isNotEmpty(strValue)){
                try {
                    doubleValue = Double.parseDouble(strValue);
                }catch (Exception e){
                    doubleValue = defaultValue;
                }
            }
        }
        return doubleValue;
    }

    public static long castLong(Object obj){
        return CastUtil.castLong(obj, 0);
    }

    public static long castLong(Object obj, long defaultValue){
        long longValue = defaultValue;
        if(obj != null){
            String strValue = CastUtil.castString(obj);
            if(StringUtil.isNotEmpty(strValue)){
                try {
                    longValue = Long.parseLong(strValue);
                }catch (Exception e){
                    longValue = defaultValue;
                }
            }
        }
        return longValue;
    }

    public static int castInt(Object obj){
        return CastUtil.castInt(obj, 0);
    }

    public static int castInt(Object obj, int defaultValue){
        int intValue = defaultValue;
        if(obj != null){
            String strValue = CastUtil.castString(obj);
            if(StringUtil.isNotEmpty(strValue)){
                try {
                    intValue = Integer.parseInt(strValue);
                }catch (Exception e){
                    intValue = defaultValue;
                }
            }
        }
        return intValue;
    }

    public static boolean castBoolean(Object obj){
        return CastUtil.castBoolean(obj, false);
    }

    public static boolean castBoolean(Object obj, boolean defaultValue){
        boolean booleanValue = defaultValue;
        if(obj != null){
            booleanValue = Boolean.parseBoolean(castString(obj));
        }
        return false;
    }

}
