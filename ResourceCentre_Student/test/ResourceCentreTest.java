import static org.junit.Assert.*;

import java.util.ArrayList;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
// C206 AY2023 SEM1 - Eclipse 21-09 JDK16
// 22026533 Priya
// 22000313 Samuel
// 22028492 Kendrick
//22013820 Shwetha
// 22003342 Khin

public class ResourceCentreTest {
	// prepare test data
	private Camcorder cc1;
	private Camcorder cc2;
	private Camcorder cc3;
	private Chromebook cb1;
	private Chromebook cb2;
	private Chromebook cb3;

	private ArrayList<Camcorder> camcorderList;
	private ArrayList<Chromebook> chromebookList;

	public ResourceCentreTest() {
		super();
	}

	@Before
	public void setUp() throws Exception {
		// prepare test data
		cc1 = new Camcorder("CC0011", "Nikon HDSLR", 40);
		cc2 = new Camcorder("CC0012", "Sony DSC-RX100M7", 20);
		cc3 = new Camcorder("CC0013", "panasoni DSC-RX100M7", 30);
		cb1 = new Chromebook("CB0011", "My Google Chromebook 1st", "Mac OS");
		cb2 = new Chromebook("CB0012", "SAMSUNG Chromebook 4+", "Win 10");
		cb3 = new Chromebook("CB0013", "HUAWEI Magicbook 100+", "Mac 10");

		camcorderList= new ArrayList<Camcorder>();
		chromebookList= new ArrayList<Chromebook>();
	}


	@Test
	public void testAddCamcorder() {
		// Item list is not null and it is empty
		assertNotNull("Test if there is valid Camcorder arraylist to add to", camcorderList);
		assertEquals("Test that the Camcorder arraylist is empty.", 0, camcorderList.size());
		//Given an empty list, after adding 1 item, the size of the list is 1
		ResourceCentre.addCamcorder(camcorderList, cc1);		
		assertEquals("Test that the Camcorder arraylist size is 1.", 1, camcorderList.size());

		
		// Add an item
		ResourceCentre.addCamcorder(camcorderList, cc2);
		assertEquals("Test that the Camcorder arraylist size is now 2.", 2, camcorderList.size());
		//The item just added is as same as the last item in the list
		assertSame("Test that Camcorder is added to the end of the list.", cc2, camcorderList.get(1));

		// Add an item that already exists in the list
		ResourceCentre.addCamcorder(camcorderList, cc2);
		assertEquals("Test that the Camcorder arraylist size is unchange.", 2, camcorderList.size());

		// Add an item that has missing detail
		Camcorder cc_missing = new Camcorder("CC0014", "", 60);
		ResourceCentre.addCamcorder(camcorderList, cc_missing);
		assertEquals("Test that the Camcorder arraylist size is unchange.", 2, camcorderList.size());
	}

	@Test
	public void testAddChromebook() {
		//fail("Not yet implemented");
		// write your code here 
	}

	@Test
	public void testRetrieveAllCamcorder() {
		//Test Case 1
		// Test if Item list is not null and empty
		assertNotNull("Test if there is valid Camcorder arraylist to add to", camcorderList);
		assertEquals("Test that the Camcorder arraylist is empty.", 0, camcorderList.size());
		// Attempt to retrieve the Camcoders 
		String allCamcorder= ResourceCentre.retrieveAllCamcorder(camcorderList);
		String testOutput = "";
		// Test if the output is empty
		assertEquals("Test that nothing is displayed", testOutput, allCamcorder);

		//Test Case 2
		ResourceCentre.addCamcorder(camcorderList, cc1);
		ResourceCentre.addCamcorder(camcorderList, cc2);
		// Test that the list is not empty
		assertEquals("Test that Camcorder arraylist size is 2.", 2, camcorderList.size());
		// Attempt to retrieve the Camcoders 
		allCamcorder= ResourceCentre.retrieveAllCamcorder(camcorderList);
		testOutput = String.format("%-10s %-30s %-10s %-10s %-20d\n","CC0011", "Nikon HDSLR", "Yes", "", 40);
		testOutput += String.format("%-10s %-30s %-10s %-10s %-20d\n","CC0012", "Sony DSC-RX100M7", "Yes", "", 20);
		// Test that the details are displayed correctly
		assertEquals("Test that the display is correct.", testOutput, allCamcorder);

		//Test Case 3
		cc3.setIsAvailable(false);
		ResourceCentre.addCamcorder(camcorderList, cc3);
		assertEquals("Test that Camcorder arraylist size is 2.", 3, camcorderList.size());
		assertFalse("Test that the last item in the arraylist is not available", camcorderList.get(2).getIsAvailable());
		// Attempt to retrieve the Camcoders 
		allCamcorder= ResourceCentre.retrieveAllCamcorder(camcorderList);
		testOutput = String.format("%-10s %-30s %-10s %-10s %-20d\n","CC0011", "Nikon HDSLR", "Yes", "", 40);
		testOutput += String.format("%-10s %-30s %-10s %-10s %-20d\n","CC0012", "Sony DSC-RX100M7", "Yes", "", 20);
		// Test that the details are displayed correctly
		assertEquals("Test that the display is correct.", testOutput, allCamcorder);
	}

	@Test
	public void testRetrieveAllChromebook() {
		//fail("Not yet implemented");
		// write your code here
	}

	@Test
	public void testDoLoanCamcorder() {

		// Test Case 1 - Loan an available Item
		assertNotNull("test if there is valid Camcorder arraylist to loan from", camcorderList);
		ResourceCentre.addCamcorder(camcorderList, cc1);
		Boolean ok = ResourceCentre.doLoanCamcorder(camcorderList, "CC0011", "8-8-2020" );
		assertTrue("Test if an available item is ok to loan?", ok);
		assertFalse(camcorderList.get(0).getIsAvailable());
		assertEquals(camcorderList.get(0).getDueDate(),"8-8-2020");

		// Test Case 2 - Loan an unavailable item
		cc2.setIsAvailable(false);
		ResourceCentre.addCamcorder(camcorderList, cc2);
		assertFalse("Test that there is an item not available", camcorderList.get(1).getIsAvailable());
		ok = ResourceCentre.doLoanCamcorder(camcorderList, "CC0012", "8-8-2020" );
		assertFalse("Test that the loan fails.", ok);	

		// Test Case 3 - Item not found
		ok = ResourceCentre.doLoanCamcorder(camcorderList, "CC0016", "8-8-2020" );
		assertFalse("Test that the loan fails.", ok);

		// Test case 4 - Missing details
		ResourceCentre.addCamcorder(camcorderList, cc3);
		assertTrue("Test that there is an item available", camcorderList.get(2).getIsAvailable());
		ok = ResourceCentre.doLoanCamcorder(camcorderList, "CC0013", "" );
		assertFalse("Test that the loan fails.", ok);
	}

	@Test
	public void testDoLoanChromebook() {
		//fail("Not yet implemented");
		// write your code here
	}

	@Test
	public void testDoReturnCamcorder() {
		
		// Test case 1: Return a loaned out item
		assertNotNull("Test if there is valid Camcorder arraylist to add to", camcorderList);
		ResourceCentre.addCamcorder(camcorderList, cc1);
		Boolean ok = ResourceCentre.doLoanCamcorder(camcorderList, "CC0011", "8-8-2020" );
		assertTrue("Test if CC0011 is successfully loaned out.", ok);
		Boolean isReturned = ResourceCentre.doReturnCamcorder(camcorderList, "CC0011");
		assertTrue("Test if the return of CC0011 is successful.", isReturned);
		assertTrue("Test that CC0011 is now available.",camcorderList.get(0).getIsAvailable());

		// Test case 2: Return an item that is not loaned out
		isReturned = ResourceCentre.doReturnCamcorder(camcorderList, "CC0011");
		assertFalse("Test that the return fails.", isReturned);
				
		// Test case 3: Return an item that does not exist 
		isReturned = ResourceCentre.doReturnCamcorder(camcorderList, "CC0013");
		assertFalse("Test the the return of CC0013 fails.", isReturned);	
	}
	
	@Test
	public void testDoReturnChromebook() {
		//fail("Not yet implemented");
		// write your code here
	}

	@After
	public void tearDown() throws Exception {
		cc1 = null;
		cc2 = null;
		cc3 = null;
		cb1 = null;
		cb2 = null;
		cb3 = null;
		camcorderList = null;
		chromebookList = null;

	}

}
