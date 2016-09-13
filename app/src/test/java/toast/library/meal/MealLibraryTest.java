package toast.library.meal;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.util.Calendar;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.not;

/**
 * Created by whdghks913 on 2016-08-03.
 */
public class MealLibraryTest {
    private String CountryCode, schulCode, schulCrseScCode, schulKndScCode;
    private String year, month, day;

    @Before
    public void setUp() throws Exception {
        CountryCode = "ice.go.kr"; // 접속 할 교육청 도메인
        schulCode = "E100001786"; // 학교 고유 코드
        schulCrseScCode = "4"; // 학교 종류 코드 1
        schulKndScCode = "04"; // 학교 종류 코드 2

        Calendar mCalendar = Calendar.getInstance();

        year = Integer.toString(mCalendar.get(Calendar.YEAR));
        month = Integer.toString(mCalendar.get(Calendar.MONTH) + 1);
        day = Integer.toString(mCalendar.get(Calendar.DAY_OF_MONTH));

        if (month.length() <= 1)
            month = "0" + month;
        if (day.length() <= 1)
            day = "0" + day;
    }

    @Test
    public void getDateNew() throws Exception {
        String[] date = MealLibrary.getDateNew(CountryCode, schulCode,
                schulCrseScCode, schulKndScCode, "1", year, month, day);
        assertThat(date != null, equalTo(true));
    }

    @Test
    public void getKcalNew() throws Exception {
        String[] kcal = MealLibrary.getKcalNew(CountryCode, schulCode,
                schulCrseScCode, schulKndScCode, "1", year, month, day);
        assertThat(kcal != null, equalTo(true));
    }

    @Test
    public void getMealNew() throws Exception {
        String[] meal = MealLibrary.getMealNew(CountryCode, schulCode,
                schulCrseScCode, schulKndScCode, "1", year, month, day);
        assertThat(meal != null, equalTo(true));
    }

    @Test
    public void getPeopleNew() throws Exception {
        String[] people = MealLibrary.getPeopleNew(CountryCode, schulCode,
                schulCrseScCode, schulKndScCode, "1", year, month, day);
        assertThat(people != null, equalTo(true));
    }

    @Test
    public void isMealCheck() throws Exception {
        assertThat(MealLibrary.isMealCheck(""), is(false));
        assertThat(MealLibrary.isMealCheck(" "), is(false));
        assertThat(MealLibrary.isMealCheck(null), is(false));
    }

}