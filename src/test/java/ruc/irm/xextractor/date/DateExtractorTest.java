package ruc.irm.xextractor.date;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * 日期测试
 *
 * @author Tian Xia
 *         School of IRM, Renmin University of China.
 *         Aug 18, 2017 12:18
 */
public class DateExtractorTest {
    private DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm");

    @Before
    public void setUp() {

    }

    /**
     * 执行一批日期抽取处理，通常该方法里面的抽取结果都是经过验证正确的
     */
    @Test
    public void testBatchExtract() {
        Date d = DateExtractor.extract("December 21, 2016 | 3:32pm");
        assertEquals(df.format(d), "2016-12-21 15:32");

        d = DateExtractor.extract("16 August 2017, 23:23");
        assertEquals(df.format(d), "2017-08-16 23:23");
    }

    /**
     * 执行一个字符串的日期抽取，方便验证结果
     */
    @Test
    public void testSingleExtract() {
        Date d = DateExtractor.extract("16 August 2017, 23:23");
        assertEquals(df.format(d), "2017-08-16 23:23");
    }
}