package org.snomed.snowstorm.fhir.services;

import org.hl7.fhir.r4.model.*;
import org.junit.jupiter.api.Test;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;


class CodeSystemProviderValidateTest extends AbstractFHIRTest {
	
	@Test
	void testValidateCode() throws FHIROperationException {
		//Test recovery using code with version
		String url = "http://localhost:" + port + "/fhir/CodeSystem/$validate-code?version=http://snomed.info/sct/1234&code=" + sampleSCTID;
		Parameters p = get(url);
		String result = toString(getProperty(p, "result"));
		assertEquals("true", result);
		
		//Alternative URLs using coding saying the same thing
		url = "http://localhost:" + port + "/fhir/CodeSystem/$validate-code?version=http://snomed.info/sct/1234&coding=http://snomed.info/sct/1234|" + sampleSCTID;
		p = get(url);
		result = toString(getProperty(p, "result"));
		assertEquals("true", result);
		
		//Known not present
		url = "http://localhost:" + port + "/fhir/CodeSystem/$validate-code?version=http://snomed.info/sct/1234&code=1234501";
		p = get(url);
		result = toString(getProperty(p, "result"));
		assertEquals("false", result);
		
		//Also check the preferred term
		url = "http://localhost:" + port + "/fhir/CodeSystem/$validate-code?coding=http://snomed.info/sct/1234|" + sampleSCTID;
		url += "&display=Baked potato 1";
		p = get(url);
		result = toString(getProperty(p, "result"));
		assertEquals("true", result);
		String msg = toString(getProperty(p, "message"));
		assertNull(msg);  //Display is the PT so we don't expect any message
		
		url = "http://localhost:" + port + "/fhir/CodeSystem/$validate-code?coding=http://snomed.info/sct/1234|" + sampleSCTID;
		url += "&display=Baked potato 1 (substance)";
		p = get(url);
		result = toString(getProperty(p, "result"));
		assertEquals("true", result);
		msg = toString(getProperty(p, "message"));
		assertNotNull(msg);  //Display is not PT so we expect a message
		
		//Check for completely wrong display value
		url = "http://localhost:" + port + "/fhir/CodeSystem/$validate-code?coding=http://snomed.info/sct/1234|" + sampleSCTID;
		url += "&display=foo";
		p = get(url);
		result = toString(getProperty(p, "result"));
		assertEquals("false", result);
		//TODO However we do get the actual PT here, so check that
	}
	
}
