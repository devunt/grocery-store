package kr.devunt.grocerystore.controller

import org.springframework.dao.EmptyResultDataAccessException
import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.ResponseStatus
import org.springframework.web.bind.annotation.RestControllerAdvice

@RestControllerAdvice
class GroceryStoreExceptionHandler {
    @ExceptionHandler(EmptyResultDataAccessException::class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    fun handleEmptyResultDataAccessException() {
    }
}
