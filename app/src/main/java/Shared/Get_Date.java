package Shared;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class Get_Date {
    public String getCurrentDate() {
        // Get current date
        Calendar calendar = Calendar.getInstance();
        Date currentDate = calendar.getTime();

        // Format the date
        SimpleDateFormat dateFormat = new SimpleDateFormat("d' 'MMMM', 'EEEE' 'yyyy", Locale.getDefault());

        return dateFormat.format(currentDate);
    }
}
