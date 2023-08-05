package UnitTest;

import static org.junit.Assert.*;

import java.io.IOException;
import java.util.List;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import eecs3311_project.Administrator;
import eecs3311_project.Manager;
import eecs3311_project.RegisteredUser;
import eecs3311_project.Store;
import eecs3311_project.Util;

public class TestHumanResource {

	private List<RegisteredUser> users;

	@Before
	public void setup() throws IOException {
		users = Util.readUsers();
	}

	@Test
	public void testUpdateManagerStore() throws IOException {
		Manager tempUser1 = new Manager("", "m", 1);
		Administrator tempUser2 = new Administrator("", "a", 0);
		tempUser1.setStore(new Store(1, null, null, null));
		Store oldStore = tempUser1.getStore();
		tempUser2.setStore(tempUser1, new Store(2, null, null, null));
		Store newStore = tempUser1.getStore();
		
		Assert.assertNotEquals(oldStore, newStore);
	}

}
