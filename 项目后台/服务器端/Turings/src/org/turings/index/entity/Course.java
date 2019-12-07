package org.turings.index.entity;

public class Course {
	private String courseId;
	private String courseImage;
    private String courseTitle;
    private  String coursePerson;
    private String courserData;

    public Course(String courseId,String courseImage, String courseTitle, String coursePerson, String courserData) {
    	this.courseId = courseId;
        this.courseImage = courseImage;
        this.courseTitle = courseTitle;
        this.coursePerson = coursePerson;
        this.courserData = courserData;
    }
    
    public Course(String courseTitle) {
        this.courseTitle = courseTitle;
		
	}
    public String getCourseId() {
		return courseId;
	}

	public void setCourseId(String courseId) {
		this.courseId = courseId;
	}

    public void setCourseImage(String courseImage) {
		this.courseImage = courseImage;
	}

	public void setCourseTitle(String courseTitle) {
		this.courseTitle = courseTitle;
	}

	public void setCoursePerson(String coursePerson) {
		this.coursePerson = coursePerson;
	}

	public void setCourserData(String courserData) {
		this.courserData = courserData;
	}

	public String getCourseImage() {
        return courseImage;
    }

    public String getCourseTitle() {
        return courseTitle;
    }

    public String getCoursePerson() {
        return coursePerson;
    }

    public String getCourserData() {
        return courserData;
    }
}
