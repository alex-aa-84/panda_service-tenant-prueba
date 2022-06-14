package wwf.org.tenant.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import wwf.org.tenant.entity.AdministrativeUnit;
import wwf.org.tenant.service.AdministrativeUnitService;
import wwf.org.tenant.serviceApi.FormatMessage;

import javax.validation.Valid;
import java.util.List;

@CrossOrigin(origins = {"${settings.cors_origin}", "${settings.cors_origin_pro}"}, maxAge = 3600,
        allowedHeaders={"Origin", "X-Requested-With", "Content-Type", "Accept", "x-client-key", "x-client-token", "x-client-secret", "Authorization"}, methods = {RequestMethod.GET, RequestMethod.POST, RequestMethod.OPTIONS, RequestMethod.HEAD, RequestMethod.DELETE, RequestMethod.PUT})
@RestController
@RequestMapping(value="/wwf/adminunits")
public class AdministrativeUnitController {

    @Autowired
    private AdministrativeUnitService administrativeUnitService;

    private FormatMessage formatMessage = new FormatMessage();

    @GetMapping
    public ResponseEntity<List<AdministrativeUnit>> listAdministrativeUnit(){
        List<AdministrativeUnit> adminunits = administrativeUnitService.listAllAdministrativeUnit();
        if(adminunits.isEmpty()){
            return ResponseEntity.noContent().build();
        }

        return ResponseEntity.ok(adminunits);
    }

    @GetMapping(value = "/{id}")
    public ResponseEntity<AdministrativeUnit> getAdministrativeUnit(@PathVariable("id") Long id){
        AdministrativeUnit adminunit = administrativeUnitService.getAdministrativeUnit(id);
        if(null == adminunit){
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(adminunit);
    }

    @PostMapping()
    public ResponseEntity<AdministrativeUnit> createAdministrativeUnit(@Valid @RequestBody AdministrativeUnit administrativeUnit, BindingResult result){

        AdministrativeUnit administrativeUnitBD = administrativeUnitService.findByAdministrativeUnit(administrativeUnit.getAdministrativeUnit());

        if (null != administrativeUnitBD){
            FieldError err = new FieldError("Error", "administrativeUnit", "unidad_administrativa_existente");
            result.addError(err);
        }

        if(result.hasErrors()){
            throw  new ResponseStatusException(HttpStatus.BAD_REQUEST, formatMessage.format(result));
        }

        return ResponseEntity.status(HttpStatus.CREATED).body(administrativeUnitService.createAdministrativeUnit(administrativeUnit));
    }

    @PutMapping()
    public ResponseEntity<AdministrativeUnit> updateAdministrativeUnit(@Valid @RequestBody AdministrativeUnit administrativeUnit, BindingResult result){

        if(result.hasErrors()){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, formatMessage.format(result));
        }

        AdministrativeUnit administrativeUnitUpdate = administrativeUnitService.updateAdministrativeUnit(administrativeUnit);
        if(null == administrativeUnitUpdate){
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(administrativeUnitUpdate);
    }

    @DeleteMapping(value = "/{id}")
    public ResponseEntity<Boolean> deleteAdministrativeUnit(@PathVariable("id") Long id){

        Boolean action = administrativeUnitService.deleteAdministrativeUnit(id);

        if ( action){
            return ResponseEntity.ok(action);
        }else{
            return ResponseEntity.notFound().build();
        }

    }
}
