package org.tokio.spring.servicesweb.controller;

import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.tokio.spring.servicesweb.core.constans.ErrorCode;
import org.tokio.spring.servicesweb.core.exception.ProductNotFoundException;
import org.tokio.spring.servicesweb.core.response.ResponseError;
import org.tokio.spring.servicesweb.dto.ErrorDTO;
import org.tokio.spring.servicesweb.dto.ProductDTO;
import org.tokio.spring.servicesweb.services.ProductService;

import java.util.Set;

@RestController
@Slf4j
@RequiredArgsConstructor
public class ProductController {

    private final ProductService productService;

    @GetMapping("/products")
    public ResponseEntity<Set<ProductDTO>> getALlProductsHandler(@RequestParam(name = "category", defaultValue = "") String category) {
        return ResponseEntity.status(HttpStatus.OK).body(productService.getProductsByCategory(category));
    }

    @GetMapping("/products/{id}")
    public ResponseEntity<ProductDTO> getProductByIdHandler(@PathVariable(value =  "id") long id) throws IllegalArgumentException, ProductNotFoundException {
        final ProductDTO productDTO = productService.findById(id).orElseThrow(() -> new ProductNotFoundException(id));
        return ResponseEntity.status(HttpStatus.OK).body(productDTO);
    }

    @PostMapping("/products")
    public ResponseEntity<ProductDTO> createProductHandler(@RequestBody ProductDTO productDTO) {
        ProductDTO addProductDTO = productService.addProduct(productDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(addProductDTO);
    }

    @GetMapping(value = "/products/internal-exception",produces = "application/json")
    @ResponseBody
    public ResponseEntity<String> internalExceptionHandler() {
        throw new IllegalArgumentException();
    }

    @GetMapping(value = "/products/not-found-exception",produces = "application/json")
    @ResponseBody
    public ResponseEntity<String> notFoundExceptionHandler() {
        throw new ProductNotFoundException("bad product request");
    }

    @ExceptionHandler(ProductNotFoundException.class)
    @ResponseStatus(code = HttpStatus.NOT_FOUND) // opcional, indica que el tipo de error de la excecpion
    //@ResponseStatus(code = HttpStatus.NOT_FOUND,reason = "Product not found") // envuelve el error en una respuesta de error de spring e ingnora el body del metodo
    //@ResponseBody // Opcional al usarlo dentro de un rest controller
    public ResponseEntity<ResponseError<ErrorDTO>> productNotFound(
            ProductNotFoundException pnfe, HttpServletRequest request
    ) {
        log.error(pnfe.getMessage(),pnfe);
        final ResponseError<ErrorDTO> responseError = ErrorDTO.errorResponse(
                ErrorCode.NOT_FOUND,
                "Error in request %s, because: %s, message: %s".formatted(
                        request.getRequestURI(),
                        ErrorCode.NOT_FOUND_MESSAGE,
                        pnfe.getMessage()));
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(responseError);
    }
}
