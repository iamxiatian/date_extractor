package ruc.irm.xextractor.date;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 2 days ago
 * 1 day ago
 * 1天前
 *
 * @author Tian Xia
 * @date Dec 21, 2016 19:24
 */
class DateSomeAgoFinder extends Finder {
    //2016/12/21 09:22:00
    private static Pattern pattern = Pattern.compile("(?<![0-9])"  // Don't start match in middle of a number.
                    + "(0?[0-9]|[0-9][0-9])"
                    + "[\\s]*"  // Separator
                    + "(day ago|days ago|天前)"
            , Pattern.CASE_INSENSITIVE
    );


    public Date extract(String s){
        String text = s.toLowerCase();
        Matcher matcher = pattern.matcher(text);
        if(matcher.find()) {
            int days = Integer.parseInt((matcher.group(1)));
            return new Date(System.currentTimeMillis()-86400000L*days);
        }

        return null;
    }

    public void validate(){
        Date d = new Date(System.currentTimeMillis()-86400000*2L);

        check("2 days ago", format(d));

        d = new Date(System.currentTimeMillis()-86400000L);
        check("1 day ago", format(d));
        check("1天前", format(d));
    }
}
