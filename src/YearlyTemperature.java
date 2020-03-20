import java.util.HashMap;
import java.util.Map;

public class YearlyTemperature {
    private static final Integer[] year2019 = {-25, -17, -7, -4, 5, 8, 13, 20, 22, 34, 30, 28};
    private static final Integer[] year2018 = {-15, -20, -4, -2, 3, 7, 15, 18, 30, 27, 25, 20};
    private static final Integer[] year2017 = {-10, -13, -25, -4, 8, 10, 15, 25, 27, 30, 33, 39};
    private static final Integer[] year2016 = {-4, -7, -9, 3, 5, 10, 15, 30, 33, 35, 41, 39};
    private static final Integer[] year2015 = {-21, -17, -9, 3, 9, 13, 15, 23, 28, 31, 29, 34};
    private static final Map<Integer, Integer[]> yearlyTemperatures;
    static {
        yearlyTemperatures = new HashMap<>();
        yearlyTemperatures.put(2019, year2019);
        yearlyTemperatures.put(2018, year2018);
        yearlyTemperatures.put(2017, year2017);
        yearlyTemperatures.put(2016, year2016);
        yearlyTemperatures.put(2015, year2015);
    }

    public static Integer[] getTemperature(int year) {
        if (yearlyTemperatures.containsKey(year)) {
            return yearlyTemperatures.get(year);
        }
        return null;
    }
}
