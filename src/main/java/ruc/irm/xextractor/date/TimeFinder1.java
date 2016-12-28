package ruc.irm.xextractor.date;

import java.util.Date;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Monday, 05 Dec 2016 09:08 AM
 * Wednesday, 21 Dec 2016 01:23 PM
 *
 * @author Tian Xia
 * @date Dec 21, 2016 16:30
 */
class TimeFinder1 extends Finder {
    //Monday, 05 Dec 2016 09:08 AM
    private static Pattern pattern = Pattern.compile("(?<![0-9])"  // Don't start match in middle of a number.
                    + "(0?[1-9]|[12][0-9]|3[01])"  // Day of month
                    + "[-\\s/.]"  // Separator
                    + monthNameRegex
                    + "[-\\s/.]"  // Separator
                    + "(20[0-9][0-9])"  // 4 digit year
                    + "[\\s]+"  // Separator
                    + "(0?[0-9]|[0-5][0-9])"  // hour
                    + ":"  // Separator
                    + "(0?[0-9]|[0-5][0-9])"  // minutes
                    + "(?![0-9])"  // Don't match if year continues.);
            ,Pattern.CASE_INSENSITIVE
    );

    public Date extract(String s){
        String text = s.toLowerCase();
        Matcher matcher = pattern.matcher(text);
        if(matcher.find()) {
            int day = Integer.parseInt(matcher.group(1));
            int month = monthMapping.get(matcher.group(2).toLowerCase());
            int year = Integer.parseInt(matcher.group(3));
            int hour = Integer.parseInt(matcher.group(4));
            int minute = Integer.parseInt(matcher.group(5));

            if(text.contains("pm")|| text.contains(" p.m. ") ) {
                //下午
                if(hour<12) hour += 12;
            }

            return makeDate(year, month, day, hour, minute);
        }

        return null;
    }


    public void validate() {
        String text = "Monday, 05 Dec 2016 09:58 PM";
        check(text, "2016/12/05 21:58");
        check("Wednesday, 21 Dec 2016 01:23 PM", "2016/12/21 13:23");
    }
}
