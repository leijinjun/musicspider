package musicspider;

import static org.junit.Assert.*;

import java.io.File;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

public class ImageTest {

	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
		System.out.println("test starting....");
	}

	@AfterClass
	public static void tearDownAfterClass() throws Exception {
	}

	@Before
	public void setUp() throws Exception {
	}

	@After
	public void tearDown() throws Exception {
		System.out.println("test end...");
	}

	@Test
	public void testComperssImage() {
		int success = Image.comperssImage(new File("d:/04.png"), new File("d:/copy.png"), 0.75f);
		assertEquals(success, 1);
	}
	@Test
	public void testRorateImage(){
		int success = Image.rorateImage(new File("d:/01-90.jpg"), new File("d:/copy.jpg"), 0);
		assertEquals(success, 1);
	}

}
