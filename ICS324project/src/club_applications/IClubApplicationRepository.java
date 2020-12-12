package club_applications;

import java.util.List;
import java.util.Optional;
import java.util.OptionalInt;

public interface IClubApplicationRepository {

	public Optional<ClubApplication> findById(int id) throws Exception;
	public void update(ClubApplication c) throws Exception;
	public void create(ClubApplication c) throws Exception;
}