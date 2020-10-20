package ruc.irm.xextractor.date;

import java.util.Calendar;
import java.util.Date;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Dec 19, 5:00 AM ET
 * December 21, at 2:59 PM
 * Dec 21, , 4:18 PM ET
 * December 26 - 12:14pm
 * JUNE 1 — 6:40AM  //注意该示例中的横线和上一个横线不同，分别为： —-
 *
 * @author Tian Xia
 * @date Oct 19, 2020 16:30
 */
class TimeFinder8 extends Finder {
    // FIXME：Not work for "Published Oct. 19 at 6:54 am"
    private static Pattern pattern = Pattern.compile(monthNameRegex
                    + "[,\\.]?[-\\s]*"  // Separator
                    + "([12][0-9]|3[01]|0?[0-9])"  // Day of month
                    + "[a-z|,\\.\\s\\-—]+"  // Separator
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
            int year = Calendar.getInstance().get(Calendar.YEAR);
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
