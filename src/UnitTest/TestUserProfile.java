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

public class TestUserProfile {

		private List<RegisteredUser> users;

		@Before
		public void setup() throws IOException {
			users = Util.readUsers();
		}
		
		@Test
		public void testChangeUsername() throws IOException {
			RegisteredUser temp = new RegisteredUser("Test", "123");
			temp.setUsername("Tom");
			Assert.assertEquals(temp.getName(), "Tom");
		}	
		
		@Test
		public void testChangePassword() throws IOException {
			RegisteredUser temp = new RegisteredUser("Test", "123");
			temp.setPassword("1234");
			Assert.assertEquals(temp.getEmail(), "");
			Assert.assertEquals(temp.getPassword(), "1234");
		}	
		
		@Test
		public void testDeleteAccount() throws IOException {
			RegisteredUser.createAccount("Trevor", "123");
			for (RegisteredUser u : users) {
				if (u.equals(new RegularUser("Trevor", "123"))) u.deleteAccount();
			}
			users = Util.readUsers();
			Assert.assertFalse(users.contains(new RegularUser("Trevor", "123")));
		}	
}
