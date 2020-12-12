package utility;

import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;

public class ProjectInfo {
	public SimpleIntegerProperty projectId = new SimpleIntegerProperty();
	public SimpleStringProperty name = new SimpleStringProperty();
	public SimpleIntegerProperty projectTypeId = new SimpleIntegerProperty();
	public SimpleIntegerProperty ClubId = new SimpleIntegerProperty();
	public SimpleStringProperty desc = new SimpleStringProperty();
	public SimpleStringProperty startDate = new SimpleStringProperty();
	public SimpleStringProperty endDate = new SimpleStringProperty();
	public SimpleIntegerProperty statusId = new SimpleIntegerProperty();


	public ProjectInfo(Integer projectId, String name,Integer projectTypeId, Integer ClubId,
			String desc,String startDate,String endDate, Integer statusId){
		this.projectId = new SimpleIntegerProperty(projectId);
		this.name = new SimpleStringProperty(name);
		this.projectTypeId = new SimpleIntegerProperty(projectTypeId);
		this.desc = new SimpleStringProperty(desc);
		this.startDate = new SimpleStringProperty(startDate);
		this.endDate = new SimpleStringProperty(endDate);
		this.statusId = new SimpleIntegerProperty(statusId);
	}

	public String toString() {
		return this.projectId.toString()+", "+ this.name.toString()+", "+this.startDate.toString();
	}

}

