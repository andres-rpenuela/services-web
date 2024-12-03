package org.tokio.spring.servicesweb.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.tokio.spring.servicesweb.core.constans.ErrorCode;
import org.tokio.spring.servicesweb.core.exception.ProductNotFoundException;
import org.tokio.spring.servicesweb.core.response.ResponseError;
import org.tokio.spring.servicesweb.dto.ErrorDTO;
import org.tokio.spring.servicesweb.dto.ProductDTO;
import org.tokio.spring.servicesweb.services.ProductService;

import java.io.IOException;
import java.util.Set;

/**
 * Swagger::
 *
 * http://localhost:8080/swagger-ui/index.html
 * http://localhost:8080/v3/api-docs
 */
@RestController
@Slf4j
@RequiredArgsConstructor
@Tag(name = "Productos", description = "Gestión de productos")
public class ProductController {

    private final ProductService productService;

    @GetMapping("/products")
    @Operation(summary = "Listado de productos")
    @ApiResponses(value={
            @ApiResponse(responseCode = "200", description = "Listado de productos",content =
                @Content(array = @ArraySchema(schema = @Schema(implementation = ProductDTO.class)))
            )})
    @Parameter(name = "category",description = "Filter by category of list. This param is optional.")
    public ResponseEntity<Set<ProductDTO>> getALlProductsHandler(@RequestParam(name = "category", defaultValue = "") String category) {
        return ResponseEntity.status(HttpStatus.OK).body(productService.getProductsByCategory(category));
    }

    @GetMapping(value="/products/{id}",produces = "application/json")
    public ResponseEntity<ProductDTO> getProductByIdHandler(@PathVariable(value =  "id") long id) throws IllegalArgumentException, ProductNotFoundException {
        final ProductDTO productDTO = productService.findById(id).orElseThrow(() -> new ProductNotFoundException(id));
        return ResponseEntity.status(HttpStatus.OK).body(productDTO);
    }

    @PostMapping(value="/products",produces = "application/json",consumes = "application/json")
    public ResponseEntity<ProductDTO> createProductHandler(@RequestBody ProductDTO productDTO) {
        ProductDTO addProductDTO = productService.addProduct(productDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(addProductDTO);
    }

    @PostMapping(value = "/products/full",produces = "application/json",consumes = {"multipart/form-data","application/octet-stream"})
    public ResponseEntity<ProductDTO> createProductFullHandler(@RequestParam(name="img") MultipartFile multipartFile,
                                                               @RequestParam(name="description", required=false) String description,
                                                               @RequestPart(name = "productDTO") ProductDTO productDTO) throws IOException {
        ProductDTO addProductDTO = productService.addProduct(productDTO,multipartFile,description);
        return ResponseEntity.status(HttpStatus.CREATED).body(addProductDTO);
    }

    @PutMapping(value = "/products/{id}", produces = "application/json",consumes = "application/json")
    public ResponseEntity<ProductDTO> updateProductHandler(@PathVariable(name="id") String id, @RequestBody ProductDTO productDTO) {
        ProductDTO updateProduct =  productService.updateProduct(Long.parseLong(id),productDTO);
        return ResponseEntity.status(HttpStatus.OK).body(updateProduct);
    }

    @DeleteMapping(value="/products/{id}", produces = "application/json")
    public ResponseEntity<ResponseError<ErrorDTO>> deleteProductHandler(@PathVariable(name="id") Long id) {
        productService.deleteProductById(id);
        return ResponseEntity.status(HttpStatus.OK).body(ErrorDTO.noErrorResponse());
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
