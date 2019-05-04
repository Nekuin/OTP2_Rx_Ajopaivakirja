package util;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Locale;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

/**
 * Tests for String class
 * @author jorin
 *
 */
public class StringsTest {
	
	static Strings strings;
	static Locale locale;
	
	/**
	 * Set up for string tests
	 */
	@BeforeAll
	static void setup() {
		strings = Strings.getInstance();
		locale = new Locale("fi", "FI");
		strings.changeBundle(locale);
	}
	
	/**
	 * Changes the bundle back to default
	 */
	@BeforeEach
	void reset() {
		strings.changeBundle(locale);
	}
	
	/**
	 * Tests setting the bundle
	 */
	@Test
	@DisplayName("Set bundle")
	void setLocale() {
		Locale testLocale = new Locale("en", "US");
		boolean test = false;
		strings.changeBundle(testLocale);
		if(strings.getBundle() != null) {
			test = true;
		}
		assertTrue(test, "Bundle was not set");
	}
	
	@Test
	@DisplayName("Get string from bundle")
	void getString() {
		String temp = strings.getString("confirm_text");
		assertEquals("Vahvista", temp, "String was not found");
	}
	
	@Test
	@DisplayName("Try to find with incorrect key")
	void getNotString() {
		assertEquals(null, strings.getString("asdasd"), "String should not be found");
	}
	
	

}
