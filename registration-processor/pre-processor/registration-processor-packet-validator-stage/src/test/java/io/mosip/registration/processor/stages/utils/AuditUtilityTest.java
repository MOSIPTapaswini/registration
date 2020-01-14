package io.mosip.registration.processor.stages.utils;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;
import org.powermock.utils.IOUtils;
import org.springframework.core.env.Environment;

import com.fasterxml.jackson.databind.ObjectMapper;

import io.mosip.registration.processor.core.packet.dto.AuditDTO;
import io.mosip.registration.processor.packet.storage.utils.Utilities;
import io.mosip.registration.processor.stages.Exception.RestServiceException;
import io.mosip.registration.processor.stages.helper.RestHelperImpl;


/**
 * The Class AuditUtilityTest.
 */
@RunWith(PowerMockRunner.class)
@PrepareForTest({ IOUtils.class, Utilities.class })
public class AuditUtilityTest {

	@Mock
	RestHelperImpl restHelper;
	
	@InjectMocks
	AuditUtility auditUtility;
	
	@Mock
	ObjectMapper mapper;
	
	@Mock
	Environment env;
	
	
	@Test
	public void saveAuditDetailSuccessTest() throws Exception {	
		AuditDTO audit =  new AuditDTO();
		List<AuditDTO> regClientAuditDTOs= new ArrayList<>();
		regClientAuditDTOs.add(audit);
		auditUtility.saveAuditDetails(regClientAuditDTOs);
	}
	
//	@Test(expected=RestServiceException.class)
//	public void saveAuditDetailFailureTest() throws Exception {	
//		List<AuditDTO> regClientAuditDTOs= null;
//		auditUtility.saveAuditDetails(regClientAuditDTOs);
//	}
	

}
