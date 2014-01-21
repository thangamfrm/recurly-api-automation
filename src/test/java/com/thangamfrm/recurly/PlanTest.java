package com.thangamfrm.recurly;

import java.math.BigInteger;

import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import com.thangamfrm.recurly.jaxb.Plan;
import com.thangamfrm.recurly.jaxb.UnitAmountInCents;
import com.thangamfrm.recurly.rest.PlanClient;

public class PlanTest extends BaseTest {

	protected PlanClient client;

	@BeforeClass
	protected void setUp() {
		super.setUp();
		client = applicationContext.getBean("planClient", PlanClient.class);
	}

	@Test
	public void testPlan() {
		String planName = "Plan-" + System.currentTimeMillis();

		// Create
		Plan plan = new Plan();
		plan.setName(planName);
		plan.setPlanCode(planName);
		plan.setAccountingCode("somecode");
		plan.setPlanIntervalUnit("months");
		plan.setPlanIntervalLength(BigInteger.ONE);
		UnitAmountInCents unitAmount = new UnitAmountInCents();
		unitAmount.setUSD(new BigInteger("50"));
		plan.setUnitAmountInCents(unitAmount);

		Plan createdPlan = client.createPlan(plan);
		assertEquals(planName, createdPlan.getName(), "Creat Plan name didn't match!");

		// Read
		Plan readPlan = client.getPlan(planName);
		assertEquals(planName, readPlan.getName(), "Read Plan name didn't match!");

		// Update
		plan.setName(planName + "star");
		Plan updatePlan = client.updatePlan(plan);
		assertEquals(planName + "star", updatePlan.getName(), "updated Plan name didn't match!!");

		// Delete
		assertTrue(client.deletePlan(planName), "Delete plan failed! Plan Code: " + planName);

		try {
			// Read After Delete
			readPlan = client.getPlan(planName);
			fail("Read succeeded after Delete! Plan Code: " + planName);
		} catch (Exception e) {
			// ignore! record has been deleted
		}
	}
}
