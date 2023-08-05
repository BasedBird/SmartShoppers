package UnitTest;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;

@RunWith(Suite.class)
@SuiteClasses({TestRecommendation.class, TestBestShoppingOrder.class, TestShoppingList.class, TestItemSearch.class, 
	TestStorePreference.class, TestHumanResource.class, TestStoreManagement.class, 
	TestItemManagement.class, TestUserAuthentication.class, TestUserProfile.class })
public class AllTests {

}
