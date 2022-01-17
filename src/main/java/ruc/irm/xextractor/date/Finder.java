package ruc.irm.xextractor.date;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * @author Tian Xia
 * @date Dec 21, 2016 16:25
 */
abstract class Finder {
    static String monthNameRegex = ("(Jan|Feb|Mar|Apr|May|Jun|Jul|Aug" +
            "|Sep|Sept|Oct|Nov|Dec" +
            "|January|February|March|April|June|July|August|September" +
            "|October|November|December" +
            "|января|февраля|марта|апрелямая|июня|июля|августа" +
            "|сентября|октября|ноября|ноября|декабря" +
            ")").toLowerCase();

    static Map<String, Integer> monthMapping = new HashMap<>();

    static {
        monthMapping.put("jan", 1);
        monthMapping.put("january", 1);
        monthMapping.put("feb", 2);
        monthMapping.put("february", 2);
        monthMapping.put("mar", 3);
        monthMapping.put("march", 3);
        monthMapping.put("apr", 4);
        monthMapping.put("april", 4);
        monthMapping.put("may", 5);
        monthMapping.put("jun", 6);
        monthMapping.put("june", 6);
        monthMapping.put("jul", 7);
        monthMapping.put("july", 7);
        monthMapping.put("aug", 8);
        monthMapping.put("august", 8);
        monthMapping.put("sep", 9);
        monthMapping.put("sept", 9);
        monthMapping.put("september", 9);
        monthMapping.put("oct", 10);
        monthMapping.put("october", 10);
        monthMapping.put("nov", 11);
        monthMapping.put("november", 11);
        monthMapping.put("dec", 12);
        monthMapping.put("december", 12);
        //俄罗斯月份: https://wenku.baidu.com/view/94650f39bed5b9f3f90f1cbc.html
        monthMapping.put("января", 1);
        monthMapping.put("февраля", 2);
        monthMapping.put("марта", 3);
        monthMapping.put("апреля", 4);
        monthMapping.put("мая", 5);
        monthMapping.put("июня", 6);
        monthMapping.put("июля", 7);
        monthMapping.put("августа", 8);
        monthMapping.put("сентября", 9);
        monthMapping.put("октября", 10);
        monthMapping.put("ноября", 11);
        monthMapping.put("декабря", 12);
    }


    Date makeDate(int month, int day) {
        return makeDate(0, month, day);
    }

    Date makeDate(int year, int month, int day) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(System.currentTimeMillis());

        return makeDate(year, month, day,
                calendar.get(Calendar.HOUR_OF_DAY),
                calendar.get(Calendar.MINUTE),
                calendar.get(Calendar.SECOND));
    }

    Date makeDate(int year, int month, int day, int hour, int minute) {
        return makeDate(year, month, day, hour, minute, 0);
    }

    Date makeDate(int year, int month, int day, int hour, int minute, int second) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(System.currentTimeMillis());

        calendar.set(Calendar.HOUR_OF_DAY, hour);
        calendar.set(Calendar.MINUTE, minute);
        calendar.set(Calendar.SECOND, second);

        if (year == 0) {
            //默认采用当前年份
        } else if (year < 100) {
            calendar.set(Calendar.YEAR, year + 2000);
        } else {
            calendar.set(Calendar.YEAR, year);
        }

        if (month > 12 & day <= 12) {
            calendar.set(Calendar.MONTH, day);
            calendar.set(Calendar.DAY_OF_MONTH, month - 1);
        } else {
            calendar.set(Calendar.MONTH, month - 1);
            calendar.set(Calendar.DAY_OF_MONTH, day);
        }

        return calendar.getTime();
    }

    public abstract Date extract(String text);

    public void validate(){};

    /**
     * 从inputText提取日期时间，如果提取的结果和expectedDateStr一样，则返回true，否则返回false
     * expectedDateStr的格式为"yyyy/MM/dd HH:mm"
     *
     * @param inputText
     * @param expectedDateStr
     * @return
     */
    public boolean check(String inputText, String expectedDateStr) {
        Date d = extract(inputText);
        if(d==null) {
            System.out.println(inputText + ": Extracting ERROR!");
            return false;
        } else if (!format(d).startsWith(expectedDateStr)) {
            System.out.println(this.getClass().getSimpleName() + "\t" + inputText + ":\t extracted: " + format(d) + ", expected: " + expectedDateStr);
            return false;
        } else {
            return true;
        }
    }

    String format(Date d) {
        SimpleDateFormat df = new SimpleDateFormat("yyyy/MM/dd HH:mm");
        return df.format(d);
    }

}
