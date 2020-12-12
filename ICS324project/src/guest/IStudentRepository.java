package guest;

import java.util.List;
import java.util.Optional;
import java.util.OptionalInt;

public interface IStudentRepository {

	public void insertStudent(Student s) throws Exception;
	public Optional<Student> findStudentById(int id) throws Exception;
	public OptionalInt findStudentIdByPhone(String phoneNum) throws Exception;
	List<Student> findStudentsWhoAreApplyingToClubId(int clubId) throws Exception;
}