package system_admin;

import java.util.Optional;
import java.util.OptionalInt;

import club.Club;
import club.IClubMemberRepository;
import club.IClubRepository;
import guest.IStudentRepository;
import guest.Student;

class AddNewMemberToClubUseCase {
	Student student;
	int clubId;
	IClubRepository clubRepository;
	IStudentRepository studentRepository;
	IClubMemberRepository clubMemberRepository;

	public AddNewMemberToClubUseCase(Student student, int clubId, IClubRepository clubRepository,
			IStudentRepository studentRepository, IClubMemberRepository clubMemberRepository) {
		this.student = student;
		this.clubId = clubId;
		this.clubRepository = clubRepository;
		this.studentRepository = studentRepository;
		this.clubMemberRepository = clubMemberRepository;
	}

	public void execute() throws Exception {
			clubMemberRepository.insertClubMemberToClub(student, clubId);
	}

}
