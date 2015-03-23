package tests.tablet.search;


/**
 * Created by Jose Cabrera
 * 1/28/15
 * 
 */

import java.io.IOException;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import jxl.read.biff.BiffException;

import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.Test;

import framework.common.AppConfigConstants;
import framework.pages.tablet.HomePage;
import framework.pages.tablet.SearchPage;
import framework.rest.RootRestMethods;
import framework.utils.readers.ExcelReader;

/**
 * @title  Verify on search page if put a number on "Minimum Capacity" field, 
 * 		   the rooms are filtered by the capacity of the room
 * @author Jose Cabrera
 */
public class WhenPutMinimumCapFieldRoomsThatMatchAreFiltered {
	public HomePage home = new HomePage();
	public SearchPage search = new SearchPage();
	@Test(groups = {"ACCEPTANCE"})
	public void testPutMinimumCapFieldRoomsAreFiltered ()throws BiffException, IOException {
		ExcelReader excelReader = new ExcelReader(AppConfigConstants.EXCEL_INPUT_DATA);
		List<Map<String, String>> testData = excelReader.getMapValues("Search");
		String capacity = testData.get(1).get("Capacity");
		LinkedList<String> capacityCond = RootRestMethods
				.getListByNumeric("rooms","capacity",capacity, "displayName");
		search = home.clickSearchBtn()
				.clickCollapseAdvancedBtn()
				.setMinimumCap(capacity);
		Assert.assertTrue(search.roomsInList(capacityCond));
	}

	@AfterClass
	public void toHome() {
		search.clickBackBtn();
	}
}
