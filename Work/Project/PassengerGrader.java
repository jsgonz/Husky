import java.util.ArrayList;

import junit.framework.Assert;

import static org.junit.Assert.*;

import org.junit.Test;


public class PassengerGrader 
{	
	/*
	 * Note the timeout mentioned on each of these tests.  This timeout will be enforced in the Autograded version
	 * as well, and is clearly too small to allow console input within the objects.  This is very intentional.
	 * (For clarification, "100" = 100 ms.)
	 */

	@Test(timeout = 200)
	public void constructorWithNullsTest() 
	{
		@SuppressWarnings("unused")
		Passenger p;
		
		try
		{
			p = new Passenger(null, "Horton");
			fail("Didn't catch a null parameter!");
		}
		catch(RuntimeException e) {	}
		
		try
		{
			p = new Passenger("Joshua", null);
			fail("Didn't catch a null parameter!");
		}
		catch(RuntimeException e) {	}
	}
	
	@Test(timeout = 100)
	public void constructorAndGetterTest() 
	{
		Passenger p = new Passenger("Joshua", "Horton");
		
		assertEquals("First name getter doesn't match the one provided!", "Joshua", p.getFirstName());
		assertEquals("Last name getter doesn't match the one provided!", "Horton", p.getLastName());
		assertEquals("Booked flight list isn't empty by default!", new ArrayList<Flight>(), p.getBookedFlights());
		assertEquals("Standby flight list isn't empty by default!", new ArrayList<Flight>(), p.getStandbyFlights());
		assertEquals("Alerts list isn't empty by default!", new ArrayList<String>(), p.getAlerts());
	}
	
	/*
	 * If having trouble getting this test "green," I'd advise looking at the equivalent test from
	 * FlightTests.java first.  Getting that one "green" first is necessary for getting this one green.
	 * 
	 * As might be noticed here, this test is designed to ensure you're utilizing the objects and their references
	 * to the fullest.
	 */
	@Test(timeout = 100)
	public void singlePassengerAddTest()
	{
		Passenger p = new Passenger("Joshua", "Horton");
		Flight f = new Flight("MCO", "MIA", 800, 930, 8);
		
		assertEquals("Flight should have been successfully booked; was not.", true, p.bookFlight(f));
		
		ArrayList<Flight> fList = p.getBookedFlights();
		
		if(fList.size() != 1)
		{
			fail("The passenger supposedly booked a flight, but it doesn't show on the flight list!");
		}
		
		if(f != fList.get(0))
		{
			fail("The passenger seems to have booked a flight, but it's not the one it was told to book!  (Did you make a duplicate instead of adding the flight itself?");
		}
		
		ArrayList<Passenger> pList = f.getBookedPassengers();
		
		if(pList.size() != 1)
		{
			fail("No one is booked on the flight, despite a passenger booking it!");
		}
		
		if(p != pList.get(0))
		{
			fail("The passenger is not booked on the flight!  (Did you make a duplicate instead of adding the passenger itself?");
		}
	}
	
	@Test(timeout = 200)
	public void singlePassengerCancelBookingTest()
	{
		Passenger p1 = new Passenger("Joshua", "Horton");
		Passenger p2 = new Passenger("Clark", "Kent");
		Flight f = new Flight("MCO", "MIA", 800, 930, 8);
		
		p1.bookFlight(f);
		p2.bookFlight(f);
		
		p1.cancelFlight(f);
		
		Assert.assertEquals("Cancelling passenger's flight should be cancelled!", 0, p1.getBookedFlights().size());
		Assert.assertEquals("The other passenger's flight shouldn't be cancelled!", 1, p2.getBookedFlights().size());
	}
	
	@Test(timeout = 200)
	public void singlePassengerCancelStandbyTest()
	{
		Passenger p1 = new Passenger("Joshua", "Horton");
		Passenger p2 = new Passenger("Clark", "Kent");
		Flight f = new Flight("MCO", "MIA", 800, 930, 8);
		
		p1.addStandbyFlight(f);
		p2.addStandbyFlight(f);
		
		p2.cancelFlight(f);
		
		Assert.assertEquals("Cancelling passenger's flight should be cancelled!", 0, p2.getStandbyFlights().size());
		Assert.assertEquals("The other passenger's flight shouldn't be cancelled!", 1, p1.getStandbyFlights().size());
	}
	
	@Test(timeout = 100)
	public void nullBookingTest()
	{
		Passenger p = new Passenger("Joshua", "Horton");
		
		try
		{
			p.bookFlight(null);
			fail("Passenger succeeded in booking a null flight!");
		}
		catch(RuntimeException e) { }
		
		try
		{
			p.addStandbyFlight(null);
			fail("Passenger succeeded in adding themselves to a null flight's standby list!");
		}
		catch(RuntimeException e) { }
	}

}