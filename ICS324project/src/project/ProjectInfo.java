package project;

import java.util.Date;
import java.util.Optional;

import club.Club;
import club.ClubInfo;

public class ProjectInfo {
	private String name;
	private int type;
	private Club club;
	private String description;
	private Optional<Date> startDate;
	private Optional<Date> endDate;
	private int status;
	private ProjectMember[] members;

	public ProjectInfo() throws Exception {
		this.startDate(new Date());
		this.endDate = Optional.empty();
	}

	public ProjectInfo(String name, int type, Club club, String description, Date startDate, Date endDate, int status,
			ProjectMember[] members) throws Exception {
		super();
		setName(name);
		setType(type);
		setClub(club);
		setDescription(description);
		startDate(startDate);
		endDate(endDate);
		setStatus(status);
		setMembers(members);
	}

	public ProjectInfo(String name, int type, Club club, String description, Optional<Date> endDate, int status,
			ProjectMember[] members) {
		super();
		this.name = name;
		this.type = type;
		this.club = club;
		this.description = description;
		this.endDate = endDate;
		this.status = status;
		this.members = members;
	}

	public ProjectInfo(String name, int type, Club club, String description, int status, ProjectMember[] members) {
		super();
		this.name = name;
		this.type = type;
		this.club = club;
		this.description = description;
		this.status = status;
		this.members = members;
	}

	public ProjectInfo name(String name) {
		setName(name);
		return this;
	}

	public ProjectInfo club(Club c) {
		setClub(c);
		return this;
	}

	public ProjectInfo type(int type) {
		setType(type);
		return this;
	}

	public ProjectInfo description(String description) {
		setDescription(description);
		return this;
	}

	public ProjectInfo startDate(Date date) throws Exception {
		if (date == null)
			setStartDate(Optional.empty());
		else
			setStartDate(Optional.of(date));

		return this;
	}

	public ProjectInfo endDate(Date date) {
		if (date == null)
			setEndDate(Optional.empty());
		else
			setEndDate(Optional.of(date));
		return this;
	}

	public ProjectInfo status(int status) throws Exception {
		setStatus(status);
		return this;
	}

	public ProjectInfo members(ProjectMember[] members) {
		setMembers(members);
		return this;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getType() {
		return type;
	}

	public void setType(int type) {
		this.type = type;
	}

	public Club getClub() {
		return club;
	}

	public void setClub(Club club) {
		this.club = club;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public Optional<Date> getStartDate() {
		return startDate;
	}

	public void setStartDate(Optional<Date> startDate) {
		this.startDate = startDate;
	}

	public Optional<Date> getEndDate() {
		return endDate;
	}

	public void setEndDate(Optional<Date> endDate) {
		this.endDate = endDate;
	}

	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}

	public ProjectMember[] getMembers() {
		return members;
	}

	public void setMembers(ProjectMember[] members) {
		this.members = members;
	}

}
