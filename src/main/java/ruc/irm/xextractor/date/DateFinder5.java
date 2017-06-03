package ruc.irm.xextractor.date;

import java.util.Date;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 英式日期：
 * 16-05-2016
 * 15.05.2017
 * @author Tian Xia
 * @date Dec 21, 2016 19:24
 */
class DateFinder5 extends Finder {
    private static Pattern pattern = Pattern.compile("(?<![0-9])"  // Don't start match in middle of a number.
                    + "(0?[1-9]|[12][0-9]|3[01])"  // Day of month
                    + "[-/\\.日]+"  // Separator
                    + "(0?[1-9]|1[0-2])"  //month
                    + "[-/\\.月]+"  // Separator
                    + "(20[0-9][0-9])"  // 4 digit year
                    + "[年]?"  // Separator
            , Pattern.CASE_INSENSITIVE
    );


    public Date extract(String s){
        String text = s.toLowerCase();
        Matcher matcher = pattern.matcher(text);
        if(matcher.find()) {
            int day = Integer.parseInt((matcher.group(1)));
            int month = Integer.parseInt(matcher.group(2));
            int year = Integer.parseInt(matcher.group(3));
            if(month>12 & day<=12) {
                return makeDate(year, day, month);
            } else {
                return makeDate(year, month, day);
            }
        }

        return null;
    }

    public void validate(){
        check("16-05-2016", "2016/05/16");
        check("19日5月2016年", "2016/05/19");
    }
}
