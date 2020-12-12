package guest;

import java.sql.Connection;
import java.util.Optional;
import java.util.OptionalInt;

import club.Club;
import club.IClubRepository;

public class ApplyForAClubUseCase {
	Student student;
	String clubName;
	IClubRepository clubRepository;
	IStudentRepository studentRepository;
	Connection connection;
	
	
	public ApplyForAClubUseCase(Student student, String clubName, IClubRepository clubRepository,
			IStudentRepository studentRepository, Connection connection) {
		super();
		this.student = student;
		this.clubName = clubName;
		this.clubRepository = clubRepository;
		this.studentRepository = studentRepository;
		this.connection = connection;
	}
	
	private void registerForClub(Student s, String clubName) throws Exception {
		Optional<Club> club = clubRepository.findByName(clubName);
		if (club.isPresent()) {
			connection.setAutoCommit(false);

			studentRepository.insertStudent(student);
			clubRepository.insertClubApplicationForStudentWithId(s.id, club.get().getId());
			connection.commit();

			connection.setAutoCommit(true);
		} else
			throw new Exception("No club with name: " + clubName + " found.");
	}
	
	public void execute() throws Exception {
		registerForClub(student, clubName);
	}
}
