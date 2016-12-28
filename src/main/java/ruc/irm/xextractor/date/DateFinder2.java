package ruc.irm.xextractor.date;

import java.util.Date;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 匹配形式：
 * Dec 05, 2016
 * Dec. 05, 2016
 *
 * @author Tian Xia
 * @date Dec 21, 2016 19:24
 */
class DateFinder2 extends Finder {

    private static Pattern pattern = Pattern.compile(monthNameRegex
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
            int month = monthMapping.get(matcher.group(1).toLowerCase());
            int day = Integer.parseInt(matcher.group(2));
            int year = Integer.parseInt(matcher.group(3));

            return makeDate(year, month, day);
        }

        return null;
    }

    public void validate(){
        check("abc 11:42 PM EST on Dec 15, 2016 edf", "2016/12/15");
        check("asd 6:41 p.m. EST December 20, 2016 eee", "2016/12/20");
    }
}
