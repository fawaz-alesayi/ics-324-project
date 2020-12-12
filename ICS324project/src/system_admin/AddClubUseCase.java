package system_admin;

import club.ClubInfo;
import club.IClubRepository;

public class AddClubUseCase {
	Admin admin;
	ClubInfo clubInfo;
	IClubRepository clubRepository;
	
	public AddClubUseCase(Admin admin, ClubInfo clubInfo, IClubRepository clubRepository) {
		super();
		this.admin = admin;
		this.clubInfo = clubInfo;
		this.clubRepository = clubRepository;
	}


	void execute() throws Exception {
		clubRepository.insertClub(clubInfo);
	}
}
