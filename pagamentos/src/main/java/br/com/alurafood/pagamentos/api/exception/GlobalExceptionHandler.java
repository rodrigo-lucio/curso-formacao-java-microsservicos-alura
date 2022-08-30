package br.com.alurafood.pagamentos.api.exception;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Set;

import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;

import org.springframework.beans.TypeMismatchException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.HttpMediaTypeNotSupportedException;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.ServletWebRequest;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@RestControllerAdvice
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler {

    /*
     * Retorna 400 quando o corpo do JSON esta inválido
     */
    @Override
    protected ResponseEntity<Object> handleHttpMessageNotReadable(HttpMessageNotReadableException ex,
                                                                  HttpHeaders headers, HttpStatus status, WebRequest request) {
        return customHandleExceptionInternal(ex, headers, status, request,
                new Mensagem(MensagemErro.CORPO_MENSAGEM_INVALIDO.toString(), ex.toString()));
    }

    /*
     * Retorna 400 quando faltam campos obrigatórios no JSON
     */
    @ExceptionHandler({ConstraintViolationException.class})
    public ResponseEntity<?> handleEmptyResultDataAccessException(ConstraintViolationException ex,
                                                                  WebRequest request) {
        Erro erros = criaErroMensagensCamposInvalidos(ex.getConstraintViolations(), HttpStatus.BAD_REQUEST.value());
        return super.handleExceptionInternal(ex, erros, new HttpHeaders(), HttpStatus.BAD_REQUEST, request);
    }

    /*
     * Retorna 415 quando o Content-Type é diferente de application/json
     */
    @Override
    protected ResponseEntity<Object> handleHttpMediaTypeNotSupported(HttpMediaTypeNotSupportedException ex,
                                                                     HttpHeaders headers, HttpStatus status, WebRequest request) {
        return customHandleExceptionInternal(ex, headers, status, request,
                new Mensagem(MensagemErro.TIPO_MIDIA_NAO_SUPORTADO.toString(), ex.toString()));
    }

    /*
     * Retorna 405 quando não existe um método no endpoint
     */
    @Override
    protected ResponseEntity<Object> handleHttpRequestMethodNotSupported(HttpRequestMethodNotSupportedException ex,
                                                                         HttpHeaders headers, HttpStatus status, WebRequest request) {
        return customHandleExceptionInternal(ex, headers, status, request,
                new Mensagem(MensagemErro.METODO_NAO_PERMITIDO.toString(), ex.toString()));
    }

    /*
     * Retorna 400 quando o query param esta inválido
     */
    @Override
    protected ResponseEntity<Object> handleMissingServletRequestParameter(MissingServletRequestParameterException ex,
                                                                          HttpHeaders headers, HttpStatus status, WebRequest request) {
        return customHandleExceptionInternal(ex, headers, status, request,
                new Mensagem(MensagemErro.PARAMETRO_INVALIDO.toString(), ex.toString()));
    }

    /*
     * Retorna 400 quando não consegue converter o queryparam em objeto
     */
    @Override
    protected ResponseEntity<Object> handleTypeMismatch(TypeMismatchException ex, HttpHeaders headers,
                                                        HttpStatus status, WebRequest request) {
        return customHandleExceptionInternal(ex, headers, status, request,
                new Mensagem(MensagemErro.PARAMETRO_INVALIDO.toString(), ex.toString()));
    }

    /*
     * Retorna 404 quando não encontra um recurso para atualizar/remover/buscar pelo
     * id
     */
    @ExceptionHandler({EmptyResultDataAccessException.class})
    public ResponseEntity<?> handleEmptyResultDataAccessException(EmptyResultDataAccessException ex,
                                                                  WebRequest request) {
        String uriRecurso = ((ServletWebRequest) request).getRequest().getRequestURI().toString();
        return customHandleExceptionInternal(ex, new HttpHeaders(), HttpStatus.NOT_FOUND, request, new Mensagem(
                String.format(MensagemErro.RECURSO_NAO_ENCONTRADO_PARA.toString(), uriRecurso), ex.toString()));
    }

    /*
     * Retorna 409 quando um recurso ja esta cadastrado
     */
    @ExceptionHandler({RecursoJaCadastradoException.class})
    public ResponseEntity<Object> handleRecursoJaCadastradoExceptionException(RecursoJaCadastradoException ex) {
        return customHandleExceptionInternal(ex, new HttpHeaders(), HttpStatus.CONFLICT, null, ex.getMensagem());
    }

    /*
     * Retorna 400 quando possui erro em algum service
     */
    @ExceptionHandler({ServicoException.class})
    public ResponseEntity<Object> handleServiceException(ServicoException ex) {
        return customHandleExceptionInternal(ex, new HttpHeaders(), HttpStatus.BAD_REQUEST, null, ex.getMensagem());
    }

    /*
     * Retorno padrão
     */
    private ResponseEntity<Object> customHandleExceptionInternal(Exception ex, HttpHeaders headers, HttpStatus status,
                                                                 WebRequest request, Mensagem mensagem) {
        Erro erro = new Erro(Arrays.asList(mensagem));
        return super.handleExceptionInternal(ex, erro, headers, status, request);
    }

    private Erro criaErroMensagensCamposInvalidos(Set<ConstraintViolation<?>> constraintViolations, int codStatus) {
        List<Mensagem> mensagens = new ArrayList<>();
        constraintViolations.forEach(fieldError -> {
            String mensagemUsuario = String.format("Campo '%s' %s", fieldError.getPropertyPath(),
                    fieldError.getMessage());
            mensagens.add(new Mensagem(mensagemUsuario, fieldError.toString()));
        });
        Erro erro = new Erro(mensagens);
        return erro;
    }

}
