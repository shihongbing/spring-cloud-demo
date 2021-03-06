package com.saltfishsoft.springclouddemo.common.util;

import java.util.Random;

/**
 * Created by Shihongbing on 2019/10/15.
 */
public class RandomUtil {

    public static final String ALLCHAR = "0123456789abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ";
    public static final String LETTERCHAR = "abcdefghijkllmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ";
    public static final String NUMBERCHAR = "0123456789";

    public RandomUtil() {
    }

    public static String generateString(int length) {
        StringBuffer sb = new StringBuffer();
        Random random = new Random();

        for(int i = 0; i < length; ++i) {
            sb.append("0123456789abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ".charAt(random.nextInt("0123456789abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ".length())));
        }

        return sb.toString();
    }

    public static String generateMixString(int length) {
        StringBuffer sb = new StringBuffer();
        Random random = new Random();

        for(int i = 0; i < length; ++i) {
            sb.append("0123456789abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ".charAt(random.nextInt("abcdefghijkllmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ".length())));
        }

        return sb.toString();
    }

    public static String generateLowerString(int length) {
        return generateMixString(length).toLowerCase();
    }

    public static String generateUpperString(int length) {
        return generateMixString(length).toUpperCase();
    }

    public static String generateNumString(int length) {
        StringBuffer sb = new StringBuffer();
        Random random = new Random();

        for(int i = 0; i < length; ++i) {
            sb.append("0123456789".charAt(random.nextInt("0123456789".length())));
        }

        return sb.toString();
    }

    public static String generateZeroString(int length) {
        StringBuffer sb = new StringBuffer();

        for(int i = 0; i < length; ++i) {
            sb.append('0');
        }

        return sb.toString();
    }

    public static String toFixdLengthString(long num, int fixdlenth) {
        StringBuffer sb = new StringBuffer();
        String strNum = String.valueOf(num);
        if(fixdlenth - strNum.length() >= 0) {
            sb.append(generateZeroString(fixdlenth - strNum.length()));
            sb.append(strNum);
            return sb.toString();
        } else {
            throw new RuntimeException("将数字" + num + "转化为长度为" + fixdlenth + "的字符串发生异常！");
        }
    }

    public static int getNotSimple(int[] param, int len) {
        Random rand = new Random();

        int result;
        int i;
        for(result = param.length; result > 1; --result) {
            i = rand.nextInt(result);
            int tmp = param[i];
            param[i] = param[result - 1];
            param[result - 1] = tmp;
        }

        result = 0;

        for(i = 0; i < len; ++i) {
            result = result * 10 + param[i];
        }

        return result;
    }

    public static void main(String[] args) {
        System.out.println("返回一个定长的随机字符串(只包含大小写字母、数字):" + generateString(10));
        System.out.println("返回一个定长的随机纯字母字符串(只包含大小写字母):" + generateMixString(10));
        System.out.println("返回一个定长的随机纯大写字母字符串(只包含大小写字母):" + generateLowerString(10));
        System.out.println("返回一个定长的随机纯小写字母字符串(只包含大小写字母):" + generateUpperString(10));
        System.out.println("生成一个定长的纯数据字符串:" + generateNumString(10));
        System.out.println("生成一个定长的纯0字符串:" + generateZeroString(10));
        System.out.println("根据数字生成一个定长的字符串，长度不够前面补0:" + toFixdLengthString(123L, 10));
        int[] in = new int[]{1, 2, 3, 4, 5, 6, 7};
        System.out.println("每次生成的len位数都不相同:" + getNotSimple(in, 3));
    }
}
