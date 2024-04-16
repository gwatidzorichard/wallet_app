package io.basaltx.walletapp.exception;

import lombok.extern.slf4j.Slf4j;
import org.hibernate.JDBCException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.context.request.ServletWebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@Slf4j
@RestControllerAdvice
public class ApiExceptionHandler extends ResponseEntityExceptionHandler {

    @ResponseBody
    @ExceptionHandler(value = ResourceNotFoundException.class)
    public ResponseEntity<WalletErrorResp> handleResourceNotFoundException(ResourceNotFoundException rnfe, ServletWebRequest request) {
        log.error("Exception: ResourceNotFound - ", rnfe);
        WalletErrorResp apiErrorResp = new WalletErrorResp(HttpStatus.NOT_FOUND.value(), HttpStatus.NOT_FOUND.getReasonPhrase(), rnfe.getMessage(), request.getRequest().getRequestURI());
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(apiErrorResp);
    }
    @ResponseBody
    @ExceptionHandler(value = AccountCreationException.class)
    public ResponseEntity<WalletErrorResp> handleAccountCreationException(AccountCreationException ace, ServletWebRequest request) {
        log.error("Exception: AccountCreationException - ", ace);
        WalletErrorResp apiErrorResp = new WalletErrorResp(HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), ace.getMessage(), request.getRequest().getRequestURI());
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(apiErrorResp);
    }
    @ResponseBody
    @ExceptionHandler(value = AccountNotActiveException.class)
    public ResponseEntity<WalletErrorResp> handleAccountNotActiveException(AccountNotActiveException anae, ServletWebRequest request) {
        log.error("Exception: AccountNotActiveException - ", anae);
        WalletErrorResp apiErrorResp = new WalletErrorResp(HttpStatus.LOCKED.value(), HttpStatus.LOCKED.getReasonPhrase(), anae.getMessage(), request.getRequest().getRequestURI());
        return ResponseEntity.status(HttpStatus.LOCKED).body(apiErrorResp);
    }
    @ResponseBody
    @ExceptionHandler(value = AccountUpdateException.class)
    public ResponseEntity<WalletErrorResp> handleAccountUpdateException(AccountUpdateException aue, ServletWebRequest request) {
        log.error("Exception: AccountUpdateException - ", aue);
        WalletErrorResp apiErrorResp = new WalletErrorResp(HttpStatus.NOT_MODIFIED.value(), HttpStatus.NOT_MODIFIED.getReasonPhrase(), aue.getMessage(), request.getRequest().getRequestURI());
        return ResponseEntity.status(HttpStatus.NOT_MODIFIED).body(apiErrorResp);
    }
    @ResponseBody
    @ExceptionHandler(value = InsufficientFundsException.class)
    public ResponseEntity<WalletErrorResp> handleInsufficientFundsException(InsufficientFundsException ife, ServletWebRequest request) {
        log.error("Exception: InsufficientFundsException - ", ife);
        WalletErrorResp apiErrorResp = new WalletErrorResp(HttpStatus.REQUESTED_RANGE_NOT_SATISFIABLE.value(), HttpStatus.REQUESTED_RANGE_NOT_SATISFIABLE.getReasonPhrase(), ife.getMessage(), request.getRequest().getRequestURI());
        return ResponseEntity.status(HttpStatus.REQUESTED_RANGE_NOT_SATISFIABLE).body(apiErrorResp);
    }
    @ResponseBody
    @ExceptionHandler(value = HttpClientErrorException.NotFound.class)
    public ResponseEntity<WalletErrorResp> handleNotFoundException(HttpClientErrorException.NotFound nfe, ServletWebRequest request) {
        log.error("Exception: NotFound - ", nfe);
        WalletErrorResp apiErrorResp = new WalletErrorResp(HttpStatus.NOT_FOUND.value(), HttpStatus.NOT_FOUND.getReasonPhrase(), nfe.getMessage(), request.getRequest().getRequestURI());
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(apiErrorResp);
    }
    @ResponseBody
    @ExceptionHandler(value = HttpClientErrorException.Forbidden.class)
    public ResponseEntity<WalletErrorResp> handleForbiddenException(HttpClientErrorException.Forbidden fe, ServletWebRequest request) {
        log.error("Exception: Forbidden - ", fe);
        WalletErrorResp apiErrorResp = new WalletErrorResp(HttpStatus.FORBIDDEN.value(), HttpStatus.FORBIDDEN.getReasonPhrase(), fe.getMessage(), request.getRequest().getRequestURI());
        return ResponseEntity.status(HttpStatus.FORBIDDEN).body(apiErrorResp);
    }
    @ResponseBody
    @ExceptionHandler(value = HttpClientErrorException.Unauthorized.class)
    public ResponseEntity<WalletErrorResp> handleUnauthorizedException(HttpClientErrorException.Unauthorized ue, ServletWebRequest request) {
        log.error("Exception: Unauthorized - ", ue);
        WalletErrorResp apiErrorResp = new WalletErrorResp(HttpStatus.UNAUTHORIZED.value(), HttpStatus.UNAUTHORIZED.getReasonPhrase(), ue.getMessage(), request.getRequest().getRequestURI());
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(apiErrorResp);
    }
    @ResponseBody
    @ExceptionHandler(value = UserAlreadyExistsException.class)
    public ResponseEntity<WalletErrorResp> handleUserAlreadyExistsException(UserAlreadyExistsException  uae, ServletWebRequest request) {
        log.error("Exception: UserAlreadyExistsException - ", uae);
        WalletErrorResp apiErrorResp = new WalletErrorResp(HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), uae.getMessage(), request.getRequest().getRequestURI());
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(apiErrorResp);
    }
    @ResponseBody
    @ExceptionHandler(value = JDBCException.class)
    public ResponseEntity<WalletErrorResp> handleJDBCException(JDBCException  je, ServletWebRequest request) {
        log.error("Exception: UserAlreadyExistsException - ", je);
        WalletErrorResp apiErrorResp = new WalletErrorResp(HttpStatus.INTERNAL_SERVER_ERROR.value(), HttpStatus.INTERNAL_SERVER_ERROR.getReasonPhrase(), je.getMessage(), request.getRequest().getRequestURI());
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(apiErrorResp);
    }
    @ResponseBody
    @ExceptionHandler(value = Exception.class)
    public ResponseEntity<WalletErrorResp> handleException(Exception e, ServletWebRequest request) {
        log.error("Exception: ", e);
        WalletErrorResp apiErrorResp = new WalletErrorResp(HttpStatus.INTERNAL_SERVER_ERROR.value(), HttpStatus.INTERNAL_SERVER_ERROR.getReasonPhrase(), e.getMessage(), request.getRequest().getRequestURI());
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(apiErrorResp);
    }


}
