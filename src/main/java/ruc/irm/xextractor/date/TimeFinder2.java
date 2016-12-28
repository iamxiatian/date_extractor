package ruc.irm.xextractor.date;

import java.util.Date;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 11:42 PM EST on Dec 15, 2016
 * 9:59 p.m. on Wednesday, December 21, 2016
 * @author Tian Xia
 * @date Dec 21, 2016 16:30
 */
class TimeFinder2 extends Finder {
    //11:42 PM EST on Dec 15, 2016
    private static Pattern pattern = Pattern.compile("(?<![0-9])"  // Don't start match in middle of a number.
                    + "([0-9]|[0-5][0-9])"  // hour
                    + "[:]"  // Separator
                    + "([0-9]|[0-5][0-9])"  // minutes
                    + "[a-z|,\\.\\s]+"  // Separator
                    + monthNameRegex
                    + "[,\\.]?[-\\s]*"  // Separator
                    + "([12][0-9]|3[01]|0?[0-9])"  // Day of month
                    + "[,\\.]?[-\\s]*"  // Separator
                    + "(20[0-9][0-9])"  // 4 digit year
                    + "(?![0-9])" // Don't match if year continues.);
            , Pattern.CASE_INSENSITIVE
    );


    public Date extract(String s){
        String text = s.toLowerCase();
        Matcher matcher = pattern.matcher(text);
        if(matcher.find()) {
            //System.out.println(matcher.group());
            int hour = Integer.parseInt(matcher.group(1));
            int minute = Integer.parseInt(matcher.group(2));
            int month = monthMapping.get(matcher.group(3).toLowerCase());
            int day = Integer.parseInt(matcher.group(4));
            int year = Integer.parseInt(matcher.group(5));

            if(text.contains("p.m. ") || text.contains("pm ")) {
                if(hour<12) hour += 12;
            }

            return makeDate(year, month, day, hour, minute);
        }

        return null;
    }

    public void validate(){
        check("abc 11:42 PM EST on Dec 15, 2016 edf", "2016/12/15 23:42");
        check("asd 6:41 p.m. EST December 20, 2016 eee", "2016/12/20 18:41");
    }
}
