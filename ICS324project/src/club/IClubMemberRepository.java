package club;

import guest.Student;

public interface IClubMemberRepository {

	void insertClubMemberToClub(Student student, int clubId) throws Exception;
	boolean studentIsAClubMemberOf(int clubId, Student student) throws Exception;

}
