package com.tech.identity.management.exception;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {

	private static final Logger _logger = LoggerFactory.getLogger(GlobalExceptionHandler.class);

	private static ErrorResponse constractErrorResponse(Optional<HttpStatus> errorCode, Optional<String> id,
			Optional<String> message, Optional<List<ErrorDetails>> errors) {
		ErrorResponse errorResponse = new ErrorResponse();
		String errorCodeText = "";
		if (errorCode.isPresent()) {
			errorCodeText = new StringBuilder(String.valueOf(errorCode.get().value())).append(" ")
					.append(errorCode.get().getReasonPhrase()).toString();
		}

		errorResponse.setCode(errorCodeText);
		errorResponse.setId(id.isPresent() ? id.get() : null);
		errorResponse.setMessage(message.isPresent() ? message.get() : null);
		errorResponse.setErrors(errors.isPresent() ? errors.get() : null);

		return errorResponse;
	}

	@ExceptionHandler({ RuntimeException.class })
	public ResponseEntity<ErrorResponse> handleRunTimeException(RuntimeException exception) {
		List<ErrorDetails> obErrors = new ArrayList<>();
		ErrorDetails oberror = new ErrorDetails().errorCode(ErrorKeyEnum.INTERNAL_ERROR)
				.message(exception.getMessage());
		obErrors.add(oberror);

		ErrorResponse errorResponse = constractErrorResponse(Optional.of(HttpStatus.INTERNAL_SERVER_ERROR),
				Optional.empty(), Optional.of("Internal Server Error"), Optional.of(obErrors));
		_logger.info(exception.toString());
		_logger.info(exception.getMessage());
		return new ResponseEntity<ErrorResponse>(errorResponse, HttpStatus.INTERNAL_SERVER_ERROR);
	}

	@ExceptionHandler({ HttpMessageNotReadableException.class })
	public ResponseEntity<ErrorResponse> handleHttpMessageNotReadable(HttpMessageNotReadableException exception) {
		List<ErrorDetails> obErrors = new ArrayList<>();
		ErrorDetails oberror = new ErrorDetails().errorCode(ErrorKeyEnum.INVALID_INPUT)
				.message("Malformed Json Request");
		obErrors.add(oberror);

		ErrorResponse errorResponse = constractErrorResponse(Optional.of(HttpStatus.BAD_REQUEST), Optional.empty(),
				Optional.of("Invalid request parameters"), Optional.of(obErrors));
		_logger.info(exception.toString());
		_logger.info(exception.getMessage());
		return new ResponseEntity<ErrorResponse>(errorResponse, HttpStatus.BAD_REQUEST);
	}

	@ExceptionHandler({ MethodArgumentNotValidException.class })
	public ResponseEntity<ErrorResponse> handleMethodArgumentNotValid(MethodArgumentNotValidException exception) {
		List<ErrorDetails> obErrors = new ArrayList<>();

		String messageKey = exception.getBindingResult().getFieldError().getDefaultMessage();
		String message = "Missing one or more mandatory fields in the request";
		ErrorDetails oberror = new ErrorDetails().errorCode(ErrorKeyEnum.FIELD_MISSING_ERROR).message(messageKey);
		obErrors.add(oberror);

		ErrorResponse errorResponse = constractErrorResponse(Optional.of(HttpStatus.BAD_REQUEST), Optional.empty(),
				Optional.of(message), Optional.of(obErrors));
		_logger.info(exception.toString());
		_logger.info(exception.getMessage());
		return new ResponseEntity<ErrorResponse>(errorResponse, HttpStatus.BAD_REQUEST);
	}

	@ExceptionHandler({UsernameNotFoundException.class})
	public ResponseEntity<ErrorResponse> handleUserNotFoundException(UsernameNotFoundException exception) {
		List<ErrorDetails> obErrors = new ArrayList<>();
		
		ErrorDetails oberror = new ErrorDetails().errorCode(ErrorKeyEnum.USER_NOT_FOUND)
				.message(exception.getMessage());
		obErrors.add(oberror);

		ErrorResponse errorResponse = constractErrorResponse(Optional.of(HttpStatus.UNAUTHORIZED),
				Optional.empty(), Optional.of("User Not Found"), Optional.of(obErrors));
		_logger.info(exception.toString());
		_logger.info(exception.getMessage());
		return new ResponseEntity<ErrorResponse>(errorResponse, HttpStatus.UNAUTHORIZED);
	}
	
	@ExceptionHandler({UserNotActiveException.class})
	public ResponseEntity<ErrorResponse> handleUserNotActiveException(UserNotActiveException exception) {
		List<ErrorDetails> obErrors = new ArrayList<>();
		
		ErrorDetails oberror = new ErrorDetails().errorCode(ErrorKeyEnum.USER_NOT_FOUND)
				.message(exception.getMessage());
		obErrors.add(oberror);

		ErrorResponse errorResponse = constractErrorResponse(Optional.of(HttpStatus.UNAUTHORIZED),
				Optional.empty(), Optional.of("User Not Active"), Optional.of(obErrors));
		_logger.info(exception.toString());
		_logger.info(exception.getMessage());
		return new ResponseEntity<ErrorResponse>(errorResponse, HttpStatus.UNAUTHORIZED);
	}

}
