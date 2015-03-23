package framework.common;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.PageFactory;

import framework.pages.tablet.HomePage;
import framework.pages.tablet.SchedulePage;
import framework.pages.tablet.SettingsPage;
import framework.selenium.SeleniumDriverManager;
import framework.utils.TimeManager;

public class MeetingMethods {
	WebDriver driver;
	HomePage home = new HomePage();
	

	/**
	 * [AC] This method initializes the listMaps to read from the excel
	 */
	public MeetingMethods() {
		driver = SeleniumDriverManager.getManager().getDriver();
		PageFactory.initElements(driver, this);
	}	

	/**
	 * [AC] This method creates a meeting
	 * @param organizer
	 * @param subject
	 * @param startTime
	 * @param endTime
	 * @param attendee
	 * @param body
	 * @param password
	 */
	public void createMeetingWithAllDataFromExcel(String organizer, String subject, String startTime, String endTime,
			String attendee, String body, String password) {
		home.clickScheduleBtn()
		.setOrganizerTxtBox(organizer)
		.setSubjectTxtBox(subject)
		.setStartTimeDate(startTime)
		.setEndTimeDate(endTime)
		.setAttendeeTxtBox(attendee)
		.setBodyTxtBox(body)
		.clickCreateBtn()
		.confirmCredentials(password)
		.isMessageMeetingCreatedDisplayed();
	}

	/**
	 * [YA]This method creates a meeting 
	 * @param organizer: Organizer name
	 * @param subject: Meeting's subject
	 * @param starTimeMinutes: Minutes to add or subtract to current time to get startTime
	 * @param endTimeMinutes: Minutes to add or subtract to current time to get endTime
	 * @param attendee: Attendees for the meeting
	 * @param body: Meeting's body message
	 * @param password: Organizer's password
	 */
	public void createMeeting(String organizer, String subject, String starTimeMinutes,
			String endTimeMinutes, String attendee, String body, String password) {

		String startTime = TimeManager.getTime(Integer.parseInt(starTimeMinutes), "hh:mm a");
		String endTime = TimeManager.getTime(Integer.parseInt(endTimeMinutes), "hh:mm a");
		home.clickScheduleBtn()
		.createMeeting(organizer, subject, startTime, endTime, attendee, body)		
		.confirmCredentials(password).isMessageMeetingCreatedDisplayed();
	}

	/**
	 * [AC] This class delete a meeting
	 * @param nameMeeting
	 * @return SchedulePage
	 */
	public SchedulePage deleteMeeting(String nameMeeting, String password) {
		home.getHome().clickScheduleBtn()
		.deleteMeeting(nameMeeting, password);
		return new SchedulePage();
	}

	/**
	 * [YA] This method sets the url for tablet home and choose an specific room
	 * @param roomName
	 * @return
	 */
	public HomePage getHomeForSpecificRoom(String roomName) {
		SettingsPage settings = new SettingsPage();
		settings.selectRoom(roomName);
		return new HomePage();
	}
}
