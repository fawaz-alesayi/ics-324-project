package club;

public class Club {
	private int id;
	private ClubInfo info;
	
	public Club(int id, ClubInfo info) {
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
	public ClubInfo getInfo() {
		return info;
	}
	public void setInfo(ClubInfo info) {
		this.info = info;
	}
	
}
