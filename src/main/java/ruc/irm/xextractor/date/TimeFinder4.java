package ruc.irm.xextractor.date;

import java.util.Date;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 2016-12-21 09:22:00
 * 2016年12月21日 09:22:00
 * 2016年2月1日 09:22:00
 * 2016/12/21 09:22:00
 * 2016.12.21 09:22:00
 *
 * @author Tian Xia
 * @date Dec 21, 2016 16:30
 */
class TimeFinder4 extends Finder {
    //2016/12/21 09:22:00
    private static Pattern pattern = Pattern.compile("(?<![0-9])"  // Don't start match in middle of a number.
                    + "(20[0-9][0-9])"  // 4 digit year
                    + "[-/\\.年]"  // Separator
                    + "(0?[1-9]|1[0-2])"  //month
                    + "[-/\\.月]"  // Separator
                    + "(0?[1-9]|[12][0-9]|3[01])"  // Day of month
                    + "[日]?[\\s]+"  // Separator
                    + "([0-9]|[0-5][0-9])"  // hour
                    + "[:]"  // Separator
                    + "([0-9]|[0-5][0-9])"  // minutes
                    + "(?![0-9])" // Don't match if minute continues.
            , Pattern.CASE_INSENSITIVE
    );


    public Date extract(String s){
        String text = s.toLowerCase();
        Matcher matcher = pattern.matcher(text);
        if(matcher.find()) {
            //System.out.println(matcher.group());
            int year = Integer.parseInt((matcher.group(1)));
            int month = Integer.parseInt(matcher.group(2));
            int day = Integer.parseInt(matcher.group(3));
            int hour = Integer.parseInt(matcher.group(4));
            int minute = Integer.parseInt(matcher.group(5));

            if(text.contains("pm")|| text.contains(" p.m. ") ) {
                if(hour<12) hour += 12;
            }

            return makeDate(year, month, day, hour, minute);
        }

        return null;
    }

    public void validate(){
        check("2016-12-21 09:22:00", "2016/12/21 09:22");
        check("2016年12月21日 09:22:00", "2016/12/21 09:22");
        check("2016年2月1日 09:22:00", "2016/02/01 09:22");
        check("2016/12/21 09:22:00", "2016/12/21 09:22");
        check("2016.12.21 09:22:00", "2016/12/21 09:22");
        check("2016.2.21 9:1:00", "2016/02/21 09:01");
    }
}
