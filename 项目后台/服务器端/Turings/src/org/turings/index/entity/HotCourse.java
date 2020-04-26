package org.turings.index.entity;

public class HotCourse {
	  private  String  courseId;
	    private String courseImage;
	    private String courseTitle;
	    private  String coursePerson;
	    private String courserData;

	    public HotCourse(String hotCourseId,String courseImage, String courseTitle, String coursePerson, String courserData) {
	        this.courseId = hotCourseId;
	        this.courseImage = courseImage;
	        this.courseTitle = courseTitle;
	        this.coursePerson = coursePerson;
	        this.courserData = courserData;
	    }
	    public String getCourseId() {
	        return courseId;
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
