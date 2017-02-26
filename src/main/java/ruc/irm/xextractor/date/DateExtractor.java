package ruc.irm.xextractor.date;

import java.util.*;

/**
 * 25th Aug 2016 11:00 AM
 * August 25, 2016
 * Aug 24, 2016
 * 08/25/2016 | 04:27am CEST
 * Aug. 22, 2016
 * August 24, 2016
 * 10:55 am, August 25, 2016
 * August 25, 2016 at 12:11 pm
 * AUG 24 2016 07:12PM EDT
 * Aug 24, 2016, 4:52 PM
 * June 19, 2016
 * 6/19/16
 * 6/19/2016
 * 6.19.2016
 * 19-Jun-16
 * 6.19.2016
 * 6/19/2016 10:07 PM
 * 6/19/2016 10:07:29 PM
 *
 * @author Tian Xia
 * @date Sep 11, 2016 09:42
 */
public class DateExtractor {
    /**
     * extract date by the following Finder sequence,
     * make sure all TimeFinders are located before DateFinders
     */
    private static Finder[] finders = new Finder[]{
            new TimeFinder1(),
            new TimeFinder2(),
            new TimeFinder3(),
            new TimeFinder4(),
            new TimeSomeAgoFinder(),

            new DateFinder1(),
            new DateFinder2(),
            new DateFinder3(),
            new DateFinder4(),
            new DateSomeAgoFinder()
    };

    /**
     * 从字符串中提取日期
     *
     * @param text
     * @return
     */
    public static Date extract(String text) {
        for (Finder f : finders) {
            try {
                Date d = f.extract(text);
                if (d != null) {
                    return d;
                }
            } catch (Exception e) {
                System.out.println("Warning: parse date error:" + text);
            }
        }
        return null;
    }

    public static void main(String[] args) {
//        for (Finder f : finders) {
//            f.validate();
//        }

        Finder f = new TimeSomeAgoFinder();
        f.validate();

//        String[] docs = new String[]{
//                "December 21, 2016 | 3:32pm",
//                "Wednesday, 21 Dec 2016 01:23 PM",
//                "Wednesday, 1 Dec 2016 12:14 PM",
//                "December 21, 2016 at 2:59 PM",
//                "December 21, 2016 at 5:49 PM",
//                "Wed Dec 21, 2016 | 10:53pm EST",
//                "Dec 21, 2016, 4:18 PM ET",
//                "9:59 p.m. on Wednesday, December 21, 2016",
//                " December 21, 2016 7:56 PM ET",
//                " December 26, 2016 - 12:14pm"
//        };
//        for (String doc : docs) {
//            System.out.println(doc + "\t==>\t" + extract(doc));
//        }
//        System.out.println(new TimeFinder1().extract("Wednesday, 21 Dec 2016 01:23 PM"));
    }

}

