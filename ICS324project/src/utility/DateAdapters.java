package utility;

import java.sql.Timestamp;
import java.util.Date;

public class DateAdapters {

	
	public static Timestamp getTimestampFromDate(Date d) {
		return new Timestamp(d.getTime());
		
	}
	
	public static Date getDateFromTimestamp(Timestamp ts) {
        return new Date(ts.getTime());
		
	}
	
	public static java.sql.Date getCurrentDateAsSQL() {
		java.util.Date date = new java.util.Date();
		java.sql.Date sqlDate = new java.sql.Date(date.getTime());
		return sqlDate;
	}
}
