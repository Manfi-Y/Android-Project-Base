package cn.tianqu.libs.app.common;

import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.support.annotation.NonNull;
import android.text.TextUtils;

import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

/**
 * 字符串工具
 * Created by Manfi on 2017/3/9.
 */

public class StringUtils {

    /**
     * 判断是否中文
     *
     * @param c ~
     * @return ~
     */
    private static boolean isChinese(char c) {
        Character.UnicodeBlock ub = Character.UnicodeBlock.of(c);
        if (ub == Character.UnicodeBlock.CJK_UNIFIED_IDEOGRAPHS
                || ub == Character.UnicodeBlock.CJK_COMPATIBILITY_IDEOGRAPHS
                || ub == Character.UnicodeBlock.CJK_UNIFIED_IDEOGRAPHS_EXTENSION_A
                || ub == Character.UnicodeBlock.GENERAL_PUNCTUATION
                || ub == Character.UnicodeBlock.CJK_SYMBOLS_AND_PUNCTUATION
                || ub == Character.UnicodeBlock.HALFWIDTH_AND_FULLWIDTH_FORMS) {
            return true;
        }
        return false;
    }

    /**
     * 判断是否有中文
     *
     * @param str ~
     * @return ~
     */
    public static boolean isChinese(String str) {
        char[] ch = str.toCharArray();
        for (int i = 0; i < ch.length; i++) {
            char c = ch[i];
            if (isChinese(c)) {
                return true;
            }
        }
        return false;
    }

    /**
     * 复制到剪切板
     *
     * @param context ~
     * @param content ~
     */
    public static void copy2Clipboard(Context context, String content) {
        ClipboardManager cmb = (ClipboardManager) context.getSystemService(Context.CLIPBOARD_SERVICE);
        cmb.setPrimaryClip(ClipData.newPlainText("", content));
    }

    /**
     * 格式化分钟为时分
     *
     * @param minute ~
     * @return ~
     */
    public static String formatTimeMinute(int minute) {
        if (minute == 0) {
            return "0分钟";
        }
        int hour = minute / 60;
        if (minute < 60) {
            return minute + "分钟";
        }
        int newMinute = minute % 60;
        return hour + "小时" + newMinute + "分钟";
    }

    /**
     * 格式化金额
     * 没有小数部分不会显示
     *
     * @param money ~
     * @return ~
     */
    public static String formatMoney(double money) {
        String pattern;
        if (money < 0) {
            money = Math.abs(money);
            pattern = isMoneyHasDecimal(money) ? "-#,##0.00" : "-#,###";
        } else {
            pattern = isMoneyHasDecimal(money) ? "#,##0.00" : "#,###";
        }
        DecimalFormat df = new DecimalFormat(pattern);
        BigDecimal bd = new BigDecimal(money).setScale(2, BigDecimal.ROUND_HALF_UP);
        return df.format(bd);
    }

    /**
     * 判断金额小数部分是否大于0
     *
     * @param value ~
     * @return ~
     */
    private static boolean isMoneyHasDecimal(double value) {
        long valueInt = (long) value;
        double decimal = value - valueInt;
        return decimal > 0;
    }

    /**
     * 判断字符串是否为数字
     *
     * @param string ~
     * @return ~
     */
    public static boolean isNumberic(String string) {
        if (TextUtils.isEmpty(string)) {
            return false;
        }
        return string.matches("[-+]?\\\\d*\\\\.?\\\\d+");
    }

    /**
     * 判断手机号码格式
     *
     * @param phone ~
     * @return ~
     */
    public static boolean isRightMobile(String phone) {
        if (TextUtils.isEmpty(phone)) {
            return false;
        }

        String head1;
        String head2;

        if (phone.length() < 11) {
            return false;
        } else {
            // 处理国内的+86开头
            if (phone.startsWith("+")) {
                phone = phone.substring(1);
            }
            if (phone.startsWith("86")) {
                phone = phone.substring(2);
            }
        }

        if (phone.length() != 11) {
            return false;
        }

        // 判断去除+86后剩余的是否全为数字
        if (!isNumberic(phone)) {
            return false;
        }

        head1 = phone.substring(0, 3);
        head2 = phone.substring(0, 4);

        // 移动前三位
        boolean cmcctemp3 = head1.equals("135") || head1.equals("136")
                || head1.equals("137") || head1.equals("138")
                || head1.equals("139") || head1.equals("147")
                || head1.equals("150") || head1.equals("151")
                || head1.equals("152") || head1.equals("157")
                || head1.equals("158") || head1.equals("159")
                || head1.equals("182") || head1.equals("187")
                || head1.equals("188");

        if (cmcctemp3) {
            return true;
        }

        // 移动前4位
        boolean cmcctemp4 = head2.equals("1340") || head2.equals("1341")
                || head2.equals("1342") || head2.equals("1343")
                || head2.equals("1344") || head2.equals("1345")
                || head2.equals("1346") || head2.equals("1347")
                || head2.equals("1348");

        if (cmcctemp4) {
            return true;
        }

        // 联通前3位
        boolean unicomtemp = head1.equals("130") || head1.equals("131")
                || head1.equals("132") || head1.equals("145")
                || head1.equals("155") || head1.equals("156")
                || head1.equals("185") || head1.equals("186");

        if (unicomtemp) {
            return true;
        }

        // 电信前3位
        boolean telecomtemp = head1.equals("133") || head1.equals("153")
                || head1.equals("180") || head1.equals("189") || head1.equals("181");

        if (telecomtemp) {
            return true;
        }

        //虚拟运营商
        boolean virtualtemp = head1.equals("170");

        if (virtualtemp) {
            return true;
        }

        return false;
    }

    /**
     * @return 生成8位英文字符串（大写）
     */
    public static String getA2ZRandom8() {
        String letter = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
        StringBuilder builder = new StringBuilder();
        for (int i = 0; i < 8; i++) {
            int rand = (int) (Math.random() * letter.length());
            builder.append(letter.charAt(rand));
        }
        return builder.toString();
    }

    /**
     * @param size ~
     * @return 生成size位数字字符串
     */
    public static String createZero2NineRandom(int size) {
        String letter = "0123456789";
        StringBuilder builder = new StringBuilder();
        for (int i = 0; i < size; i++) {
            int rand = (int) (Math.random() * letter.length());
            builder.append(letter.charAt(rand));
        }
        return builder.toString();
    }

    /**
     * 格式化日期
     *
     * @param date        日期字符串
     * @param currPattern 当前日期格式
     * @param newPattern  格式化后日期格式
     * @return
     */
    public static String formatDate(String date, String currPattern, String newPattern) {
        if (TextUtils.isEmpty(date) || TextUtils.isEmpty(currPattern) || TextUtils.isEmpty(newPattern)) {
            return null;
        }
        SimpleDateFormat sdfCurr = new SimpleDateFormat(currPattern, Locale.getDefault());
        SimpleDateFormat sdfNew = new SimpleDateFormat(newPattern, Locale.getDefault());
        Date d;
        try {
            d = sdfCurr.parse(date);
        } catch (ParseException e) {
            e.printStackTrace();
            return date;
        }
        return sdfNew.format(d);
    }

    /**
     * 格式化日期
     *
     * @param timeMillis 时间（毫秒）
     * @param pattern    格式
     * @return ~
     */
    public static String formatDate(long timeMillis, String pattern) {
        if (TextUtils.isEmpty(pattern)) {
            return "";
        }
        Date date = new Date(timeMillis);
        SimpleDateFormat sdfCurr = new SimpleDateFormat(pattern, Locale.getDefault());
        return sdfCurr.format(date);
    }

    /**
     * 获取字符串首字母
     *
     * @param str   ~
     * @param index 第几个
     * @return 小写字母
     */
    public static String getStringLatter(@NonNull String str, int index) {
        if (TextUtils.isEmpty(str)) {
            return "";
        }
        if (index < str.length()) {
            return str.substring(index, index + 1).toLowerCase();
        }
        return "";
    }

    /**
     * 格式化大小
     *
     * @param size ~
     * @return ~
     */
    public static String formatSize(long size) {
        double sizeKB = (double) size / 1024;
        if (sizeKB > 0.1 && sizeKB < 10) {
            return String.valueOf(formatCoefficient((float) sizeKB)) + "KB";
        }
        double sizeMB = (double) size / 1024 / 1024;
        return String.valueOf(formatCoefficient((float) sizeMB)) + "MB";
    }

    /**
     * 格式化小数，精确到1位,小数位是0不显示
     *
     * @param coefficient ~
     * @return ~
     */
    public static String formatCoefficient(float coefficient) {
        String pattern = isHasDecimal(coefficient) ? "#.#" : "#";
        DecimalFormat df = new DecimalFormat(pattern);
        return df.format(coefficient);
    }

    private static boolean isHasDecimal(float value) {
        long valueInt = (long) value;
        double decimal = value - valueInt;
        return decimal > 0;
    }

    /**
     * 格式化距离
     *
     * @param distance 米
     * @return ~
     */
    public static String formatDistance(int distance) {
        float distanceF = (float) distance;
        String distanceStr;

        if (distance >= 1000) {
            distanceF = distanceF / 1000;
            distanceStr = formatCoefficient(distanceF) + "km";
        } else {
            distanceStr = distance + "m";
        }
        return distanceStr;
    }
}
