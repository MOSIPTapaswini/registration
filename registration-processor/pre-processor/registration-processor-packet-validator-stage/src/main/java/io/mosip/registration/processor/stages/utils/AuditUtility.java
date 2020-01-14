package io.mosip.registration.processor.stages.utils;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

import io.mosip.kernel.core.logger.spi.Logger;
import io.mosip.registration.processor.core.constant.LoggerFileConstant;
import io.mosip.registration.processor.core.constant.PacketFiles;
import io.mosip.registration.processor.core.logger.RegProcessorLogger;
import io.mosip.registration.processor.core.packet.dto.AuditDTO;
import io.mosip.registration.processor.core.packet.dto.AuditRequestDTO;
import io.mosip.registration.processor.core.packet.dto.AuditRespDTO;
import io.mosip.registration.processor.stages.dto.RestRequestDTO;
import io.mosip.registration.processor.stages.helper.RestHelper;
import io.mosip.registration.processor.stages.packet.validator.PacketValidateProcessor;

/**
 * @author Tapaswini Behera M1043226
 *
 */

@Component
public class AuditUtility {

	/** The reg proc logger. */
	private static Logger regProcLogger = RegProcessorLogger.getLogger(PacketValidateProcessor.class);

	@Autowired
	private RestHelper restHelper;

	@Autowired
	private Environment env;

	@Async
	public void saveAuditDetails(List<AuditDTO> auditDTOs) {
		try {
			regProcLogger.debug(LoggerFileConstant.SESSIONID.toString(), LoggerFileConstant.REGISTRATIONID.toString(),
					"", "AuditUtility::saveAuditDetails()::entry");

			auditDTOs.parallelStream().forEach(audit -> {
				RestRequestDTO request = buildRequest(audit);
				restHelper.requestAsync(request);
			});

		} catch (Exception e) {
			regProcLogger.error(LoggerFileConstant.SESSIONID.toString(), LoggerFileConstant.REGISTRATIONID.toString(),
					"", "AuditUtility::saveAuditDetails()::error");
		}

	}

	/**
	 * Builds the request.
	 *
	 * @param restService
	 *            the rest service
	 * @param requestBody
	 *            the request body
	 * @param returnType
	 *            the return type
	 * @return the rest request DTO
	 * @throws IDDataValidationException
	 *             the ID data validation exception
	 */
	public RestRequestDTO buildRequest(Object requestBody) {
		regProcLogger.debug(LoggerFileConstant.SESSIONID.toString(), LoggerFileConstant.REGISTRATIONID.toString(), "",
				"AuditUtility::buildRequest()::entry" + requestBody);
		AuditRequestDTO auditRequest = new AuditRequestDTO();
		auditRequest.setRequest((AuditDTO) requestBody);
		auditRequest.setId("String");
		auditRequest.setVersion("1.0");
		auditRequest.setRequesttime(LocalDateTime.now());
		RestRequestDTO request = new RestRequestDTO();

		request.setUri(env.getProperty(PacketFiles.AUDIT.name()));
		request.setHttpMethod(HttpMethod.POST);
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);
		request.setHeaders(headers);

		request.setRequestBody(auditRequest);
		request.setResponseType(AuditRespDTO.class);

		return request;
	}
}
