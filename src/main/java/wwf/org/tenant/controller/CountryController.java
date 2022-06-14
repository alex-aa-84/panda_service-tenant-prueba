package wwf.org.tenant.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import wwf.org.tenant.entity.Country;
import wwf.org.tenant.service.CountryService;
import wwf.org.tenant.serviceApi.FormatMessage;

import javax.validation.Valid;
import java.util.List;

@CrossOrigin(origins = {"${settings.cors_origin}", "${settings.cors_origin_pro}"}, maxAge = 3600,
        allowedHeaders={"Origin", "X-Requested-With", "Content-Type", "Accept", "x-client-key", "x-client-token", "x-client-secret", "Authorization"}, methods = {RequestMethod.GET, RequestMethod.POST, RequestMethod.OPTIONS, RequestMethod.HEAD, RequestMethod.DELETE, RequestMethod.PUT})
@RestController
@RequestMapping(value="/wwf/countrys")
public class CountryController { 

    @Autowired
    private CountryService countryService;

    private FormatMessage formatMessage = new FormatMessage();

    @GetMapping
    public ResponseEntity<List<Country>> listCountry(){
        List<Country> countrys = countryService.listAllCountry();
        if(countrys.isEmpty()){
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(countrys);
    }

    @GetMapping(value = "/{id}")
    public ResponseEntity<Country> getCountry(@PathVariable("id") Long id){
        Country country = countryService.getCountry(id);
        if(null == country){
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(country);
    }

    @PostMapping()
    public ResponseEntity<Country> createCountry(@Valid @RequestBody Country country, BindingResult result){
        Country countryBD = countryService.findByCountry(country.getCountry());

        if (null != countryBD){
            FieldError err = new FieldError("Error", "country", "pais_existente");
            result.addError(err);
        }

        if(result.hasErrors()){
            throw  new ResponseStatusException(HttpStatus.BAD_REQUEST, formatMessage.format(result));
        }

        return ResponseEntity.status(HttpStatus.CREATED).body(countryService.createCountry(country));
    }

    @PutMapping()
    public ResponseEntity<Country> updateCountry(@Valid @RequestBody Country country, BindingResult result){

        if(result.hasErrors()){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, formatMessage.format(result));
        }

        Country countryDB = countryService.updateCountry(country);
        if(null == countryDB){
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(countryDB);
    }

    @DeleteMapping(value = "/{id}")
    public ResponseEntity<Boolean> deleteCountry(@PathVariable("id") Long id){

        Boolean action = countryService.deleteCountry(id);

        if ( action){
            return ResponseEntity.ok(action);
        }else{
            return ResponseEntity.notFound().build();
        }

    }

}
