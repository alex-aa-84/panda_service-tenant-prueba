package wwf.org.tenant.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import wwf.org.tenant.entity.Tenant;
import wwf.org.tenant.service.TenantService;
import wwf.org.tenant.serviceApi.FormatMessage;

import javax.validation.Valid;
import java.util.List;
import java.util.Optional;

@CrossOrigin(origins = {"${settings.cors_origin}", "${settings.cors_origin_pro}"}, maxAge = 3600,
        allowedHeaders={"Origin", "X-Requested-With", "Content-Type", "Accept", "x-client-key", "x-client-token", "x-client-secret", "Authorization"}, methods = {RequestMethod.GET, RequestMethod.POST, RequestMethod.OPTIONS, RequestMethod.HEAD, RequestMethod.DELETE, RequestMethod.PUT})
@RestController
@RequestMapping(value="/wwf/tenants")
public class TenantController {

    @Autowired
    private TenantService tenantService;

    private FormatMessage formatMessage = new FormatMessage();

    @GetMapping
    public ResponseEntity<List<Tenant>> listTenant(@RequestHeader HttpHeaders headers){
        Optional.ofNullable(headers.get(HttpHeaders.AUTHORIZATION)).ifPresent(
                h -> System.out.println("AUTHORIZATION HEADER: " + h.get(0))
        );

        List<Tenant> tenants = tenantService.listAllTenant();
        if(tenants.isEmpty()){
            return ResponseEntity.noContent().build();
        }

        return ResponseEntity.ok(tenants);
    }

    @GetMapping(value = "/{id}")
    public ResponseEntity<Tenant> getTenant(@PathVariable("id") Long id){
        Tenant tenant = tenantService.getTenant(id);
        if(null == tenant){
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(tenant);
    }

    @GetMapping(value = "/tenant/{tenant}")
    public ResponseEntity<Tenant> getByTenant(@PathVariable("tenant") String tenant){
        Tenant tenantR = tenantService.findByTenant(tenant);
        if(null == tenantR){
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(tenantR);
    }

    @GetMapping(value = "/domain/{domain}")
    public ResponseEntity<Tenant> getByDomain(@PathVariable("domain") String domain){
        Tenant tenantR = tenantService.findByDomain(domain);
        if(null == tenantR){
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(tenantR);
    }

    @PostMapping()
    public ResponseEntity<Tenant> createTenant(@Valid @RequestBody Tenant tenant, BindingResult result){

        Tenant tenantBD = tenantService.findByTenant(tenant.getTenant());

        if (null != tenantBD){
            FieldError err = new FieldError("Error", "inquilino", "inquilino_existente");
            result.addError(err);
        }

        if(result.hasErrors()){
            throw  new ResponseStatusException(HttpStatus.BAD_REQUEST, formatMessage.format(result));
        }

        return ResponseEntity.status(HttpStatus.CREATED).body(tenantService.createTenant(tenant));

    }

    @PutMapping()
    public ResponseEntity<Tenant> updateTenant(@Valid @RequestBody Tenant tenant, BindingResult result){

        if(result.hasErrors()){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, formatMessage.format(result));
        }

        Tenant tenantDB = tenantService.updateTenant(tenant);
        if(null == tenantDB){
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(tenantDB);
    }

    @DeleteMapping(value = "/{id}")
    public ResponseEntity<Boolean> deleteTenant(@PathVariable("id") Long id){

        Boolean action = tenantService.deleTenant(id);

        if ( action){
            return ResponseEntity.ok(action);
        }else{
            return ResponseEntity.notFound().build();
        }

    }
}
