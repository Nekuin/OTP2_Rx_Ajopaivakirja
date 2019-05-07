package util;

/**
 * 
 * TestUtil class is used to define which controller to be used,
 * this is needed since Jenkins uses its own database
 * 
 * For local testing use value "false"
 * For Jenkins testing use value "true"
 *
 */
public class TestUtil {
	/**
	 * Use "false" for testing
	 * REMEMBER TO CHANGE TO "true" BEFORE PUSHING!!!!!!!!!!!
	 */
	public static boolean testVersion = true;
}
