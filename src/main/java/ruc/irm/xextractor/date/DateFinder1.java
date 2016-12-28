package ruc.irm.xextractor.date;

import java.util.Date;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 05 Dec 2016
 *
 * @author Tian Xia
 * @date Dec 21, 2016 19:24
 */
class DateFinder1 extends Finder {

    private static Pattern pattern = Pattern.compile("(?<![0-9])"  // Don't start match in middle of a number.
                    + "(0?[1-9]|[12][0-9]|3[01])"  // Day of month
                    + "[-\\s/.]"  // Separator
                    + monthNameRegex
                    + "[-\\s/.]"  // Separator
                    + "(20[0-9][0-9])"  // 4 digit year
                    + "(?![0-9])",  // Don't match if year continues.);
            Pattern.CASE_INSENSITIVE
    );

    public Date extract(String s){
        String text = s.toLowerCase();
        Matcher matcher = pattern.matcher(text);
        if(matcher.find()) {
            int day = Integer.parseInt(matcher.group(1));
            int month = monthMapping.get(matcher.group(2).toLowerCase());
            int year = Integer.parseInt(matcher.group(3));

            return makeDate(year, month, day);
        }

        return null;
    }

    public void validate() {
        String text = "Monday, 05 Dec 2016 09:58 PM";
        check(text, "2016/12/05");
    }
}
