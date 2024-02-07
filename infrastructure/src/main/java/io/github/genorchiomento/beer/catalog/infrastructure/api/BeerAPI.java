package io.github.genorchiomento.beer.catalog.infrastructure.api;

import io.github.genorchiomento.beer.catalog.domain.pagination.Pagination;
import io.github.genorchiomento.beer.catalog.infrastructure.beer.model.BeerApiOutput;
import io.github.genorchiomento.beer.catalog.infrastructure.beer.model.CreateBeerApiInput;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RequestMapping(value = "beers")
@Tag(name = "Beers")
public interface BeerAPI {

    @PostMapping(
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    @Operation(summary = "Create a new Beer")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Created Successfully"),
            @ApiResponse(responseCode = "422", description = "A validation error was thrown"),
            @ApiResponse(responseCode = "500", description = "An internal server error was thrown"),

    })
    ResponseEntity<?> createBeer(@RequestBody CreateBeerApiInput input);

    @GetMapping
    @Operation(summary = "List all beers paginated")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Listed Successfully"),
            @ApiResponse(responseCode = "422", description = "A invalid parameter was received"),
            @ApiResponse(responseCode = "500", description = "An internal server error was thrown"),

    })
    Pagination<?> listBeers(
            @RequestParam(name = "search", required = false, defaultValue = "") final String search,
            @RequestParam(name = "page", required = false, defaultValue = "0") final int page,
            @RequestParam(name = "perPage", required = false, defaultValue = "10") final int perPage,
            @RequestParam(name = "sort", required = false, defaultValue = "name") final String sort,
            @RequestParam(name = "dir", required = false, defaultValue = "asc") final String direction
    );

    @GetMapping(
            value = "{id}",
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    @Operation(summary = "Get a beer by it's identifier")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Beer retrieved Successfully"),
            @ApiResponse(responseCode = "404", description = "Beer was not found"),
            @ApiResponse(responseCode = "500", description = "An internal server error was thrown"),

    })
    BeerApiOutput getById(
            @PathVariable(name = "id") String id
    );

    @PutMapping(
            value = "{id}",
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    @Operation(summary = "Update a beer by it's identifier")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Beer updated Successfully"),
            @ApiResponse(responseCode = "404", description = "Beer was not found"),
            @ApiResponse(responseCode = "500", description = "An internal server error was thrown"),

    })
    ResponseEntity<?> updateById(
            @PathVariable(name = "id") String id,
            @RequestBody CreateBeerApiInput input
    );

}
