package club;

import java.util.List;
import java.util.Optional;
import java.util.OptionalInt;

public interface IClubRepository {

	public void insertClub(ClubInfo c) throws Exception;
	public void insertClub(Club c) throws Exception;
	public Optional<Club> findById(int id) throws Exception;
	public Optional<Club> findByName(String string) throws Exception;
	public void insertClubApplicationForStudentWithId(int studentId, int clubId) throws Exception;
	public List<Integer> findClubApplicationsIdByStudentId(int studentId) throws Exception;
}