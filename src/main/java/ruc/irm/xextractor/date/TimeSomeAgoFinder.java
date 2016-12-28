package ruc.irm.xextractor.date;

import java.util.Date;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 2 hours ago
 * 1 minute ago
 *
 * @author Tian Xia
 * @date Dec 21, 2016 19:24
 */
class TimeSomeAgoFinder extends Finder {

    private static Pattern minutePattern = Pattern.compile("(?<![0-9])"  // Don't start match in middle of a number.
                    + "(0?[0-9]|[0-9][0-9])"
                    + "[\\s]*"  // Separator
                    + "(minute ago|minutes ago|分钟前)"
            , Pattern.CASE_INSENSITIVE
    );

    private static Pattern hourPattern = Pattern.compile("(?<![0-9])"  // Don't start match in middle of a number.
                    + "(0?[0-9]|[0-9][0-9])"
                    + "[\\s]*"  // Separator
                    + "(hour ago|hours ago|小时前)"
            , Pattern.CASE_INSENSITIVE
    );


    public Date extract(String s){
        String text = s.toLowerCase();
        Matcher matcher = minutePattern.matcher(text);
        if(matcher.find()) {
            int minutes = Integer.parseInt((matcher.group(1)));
            return new Date(System.currentTimeMillis()-60000L*minutes);
        } else {
            matcher = hourPattern.matcher(text);
            if(matcher.find()) {
                int hours = Integer.parseInt((matcher.group(1)));
                return new Date(System.currentTimeMillis()-3600000L*hours);
            }
        }

        return null;
    }

    public void validate(){
        Date d = new Date(System.currentTimeMillis()-86400000*2L);

        check("2 minutes ago", format(new Date(System.currentTimeMillis()-60000*2L)));
        check("1 minutes ago", format(new Date(System.currentTimeMillis()-60000L)));
        check("1分钟前", format(new Date(System.currentTimeMillis()-60000L)));
        check("1小时前", format(new Date(System.currentTimeMillis()-3600000L)));
    }
}
