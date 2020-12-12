package club_applications;

public class ClubApplication {
	int id;
	ClubApplicationInfo info;
	
	public ClubApplication(int id, ClubApplicationInfo info) {
		super();
		this.id = id;
		this.info = info;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public ClubApplicationInfo getInfo() {
		return info;
	}
	public void setInfo(ClubApplicationInfo info) {
		this.info = info;
	}
	
	
}
