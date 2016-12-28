package ruc.irm.xextractor.date;

import java.util.Date;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Dec 19, 2016 5:00 AM ET
 * December 21, 2016 at 2:59 PM
 * Dec 21, 2016, 4:18 PM ET
 * December 26, 2016 - 12:14pm
 *
 * @author Tian Xia
 * @date Dec 21, 2016 16:30
 */
class TimeFinder3 extends Finder {
    //Dec 19, 2016 5:00 AM ET
    private static Pattern pattern = Pattern.compile(monthNameRegex
                    + "[,\\.]?[-\\s]*"  // Separator
                    + "([12][0-9]|3[01]|0?[0-9])"  // Day of month
                    + "[,\\.]?[-\\s]*"  // Separator
                    + "(20[0-9][0-9])"  // 4 digit year
                    + "[a-z|,\\.\\s\\-]+"  // Separator
                    + "([0-9]|[0-5][0-9])"  // hour
                    + "[:]"  // Separator
                    + "([0-9]|[0-5][0-9])"  // minutes
                    + "(?![0-9])" // Don't match if year continues.
            , Pattern.CASE_INSENSITIVE
    );


    public Date extract(String s) {
        String text = s.toLowerCase();
        Matcher matcher = pattern.matcher(text);
        if (matcher.find()) {
            //System.out.println(matcher.group());
            int month = monthMapping.get((matcher.group(1)));
            int day = Integer.parseInt(matcher.group(2));
            int year = Integer.parseInt(matcher.group(3));
            int hour = Integer.parseInt(matcher.group(4));
            int minute = Integer.parseInt(matcher.group(5));

            if (text.contains("pm") || text.contains(" p.m. ")) {
                if (hour < 12) hour += 12;
            }

            return makeDate(year, month, day, hour, minute);
        }

        return null;
    }

    public void validate() {
        check("abc Dec 19, 2016 5:00 AM ET edf", "2016/12/19 05:00");
        check("December 21, 2016 | 3:32pm", "2016/12/21 15:32");
        check("December 21, 2016 at 2:59 PM", "2016/12/21 14:59");
        check("December 21, 2016 at 5:49 PM", "2016/12/21 17:49");
    }
}
