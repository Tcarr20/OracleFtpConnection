package dto;

public class movieDto {
	
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public movieDto(String title, String description) {
		super();
		this.title = title;
		this.description = description;
	}
	public String title;
	public String id;
	public String description;

}
