package groupeighteen.itufit.domain.facility;

public class FacilityConstants {
    public static final int GYM_CALORIE = 450;
    public static final int POOL_CALORIE = 350;
    public static final int TENNIS_COURT_CALORIE = 500;
    public static final int TABLE_TENNIS_CALORIE = 300;
    public static final int BASKETBALL_CALORIE = 600;
    public static final int FOOTBALL_CALORIE = 550;
    public static final int VOLLEYBALL_CALORIE = 350;
    public static final int[] facilityCalories = {
            GYM_CALORIE, POOL_CALORIE, TENNIS_COURT_CALORIE, TABLE_TENNIS_CALORIE, BASKETBALL_CALORIE, FOOTBALL_CALORIE, VOLLEYBALL_CALORIE};
    public static final String[] facilityNames = {
            "GYM_CALORIE", "POOL_CALORIE", "TENNIS_COURT_CALORIE", "TABLE_TENNIS_CALORIE", "BASKETBALL_CALORIE", "FOOTBALL_CALORIE", "VOLLEYBALL_CALORIE"};

    public static int getCalorie(String facilityType) {
        int counter = 0;
        for (String facilityName : facilityNames) {
            if (facilityName.contains(facilityType))
                return facilityCalories[counter];
            counter++;
        }
        return 0;
    }
}
