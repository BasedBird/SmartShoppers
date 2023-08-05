package UnitTest;

import static org.junit.Assert.*;

import java.io.IOException;
import java.util.List;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import eecs3311_project.RegisteredUser;
import eecs3311_project.RegularUser;
import eecs3311_project.Util;

public class TestUserAuthentication {

	private List<RegisteredUser> users;

	@Before
	public void setup() throws IOException {
		users = Util.readUsers();
	}

	@Test
	public void testSignInSuccess() throws IOException {
		String username = "jill";
		String password = "123";

		boolean flag = false;
		for (RegisteredUser u : users) if (u.signIn(username, password)) flag = true;
		Assert.assertEquals(true, flag);
	}

	@Test
	public void testSignInFail() throws IOException {
		String username = "not a user";
		String password = "123";

		boolean flag = false;
		for (RegisteredUser u : users) if (u.signIn(username, password)) flag = true;
		Assert.assertEquals(false, flag);
	}
	
	@Test
	public void testCreateAccount() throws IOException {
		RegisteredUser.createAccount("Trevor", "123");
		users = Util.readUsers();
		Assert.assertTrue(users.contains(new RegularUser("Trevor", "123")));
	}
}
