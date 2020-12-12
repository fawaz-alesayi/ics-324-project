package club_applications;

import java.util.Date;

import club.Club;
import guest.Student;

public class ClubApplicationInfo {
	Student applicant;
	Club club;
	Date creation_date;
	
	public ClubApplicationInfo(Student applicant, Club club, Date creation_date) {
		super();
		this.applicant = applicant;
		this.club = club;
		this.creation_date = creation_date;
	}

	public Student getApplicant() {
		return applicant;
	}

	public void setApplicant(Student applicant) {
		this.applicant = applicant;
	}

	public Club getClub() {
		return club;
	}

	public void setClub(Club club) {
		this.club = club;
	}

	public Date getCreation_date() {
		return creation_date;
	}

	public void setCreation_date(Date creation_date) {
		this.creation_date = creation_date;
	}
	
	
	
	
}
