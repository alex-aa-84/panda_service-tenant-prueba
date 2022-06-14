package wwf.org.tenant.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import wwf.org.tenant.entity.Tenant;
import wwf.org.tenant.entity.TenantModule;
import wwf.org.tenant.service.TenantModuleService;
import wwf.org.tenant.service.TenantService;
import wwf.org.tenant.serviceApi.FormatMessage;

import javax.validation.Valid;
import java.util.List;

@CrossOrigin(origins = {"${settings.cors_origin}", "${settings.cors_origin_pro}"}, maxAge = 3600,
        allowedHeaders={"Origin", "X-Requested-With", "Content-Type", "Accept", "x-client-key", "x-client-token", "x-client-secret", "Authorization"}, methods = {RequestMethod.GET, RequestMethod.POST, RequestMethod.OPTIONS, RequestMethod.HEAD, RequestMethod.DELETE, RequestMethod.PUT})
@RestController
@RequestMapping(value="/wwf/tenantmodules")
public class TenantModuleController {

    @Autowired
    private TenantModuleService tenantModuleService;
    @Autowired
    private TenantService tenantService;
    private FormatMessage formatMessage = new FormatMessage();

    @GetMapping
    public ResponseEntity<List<TenantModule>> listTenantModules(){
        List<TenantModule> tenantmodules = tenantModuleService.listAllTenantModule();
        if(tenantmodules.isEmpty()){
            return ResponseEntity.noContent().build();
        }

        return ResponseEntity.ok(tenantmodules);
    }

    @GetMapping(value = "/{id}")
    public ResponseEntity<TenantModule> getTenantModule(@PathVariable("id") Long id){
        TenantModule tenantmodule = tenantModuleService.getTenantModule(id);
        if(null == tenantmodule){
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(tenantmodule);
    }

    @GetMapping(value = "/tenant/{tenant}")
    public ResponseEntity<List<TenantModule>> getTenantModuleTenant(@PathVariable("tenant") String tenant){
        Tenant tenantDB = tenantService.findByTenant(tenant);
        List<TenantModule> tenantmodules = tenantModuleService.findByTenant(tenantDB);
        if(null == tenantmodules){
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(tenantmodules);
    }

    @PostMapping()
    public ResponseEntity<TenantModule> createTenantModule(@Valid @RequestBody TenantModule tenantModule, BindingResult result){
        TenantModule tenantModuleBD = tenantModuleService.findByTenantIdAndModuleId(tenantModule.getTenant().getId(), tenantModule.getModule().getId());

        if (null != tenantModuleBD){
            FieldError err = new FieldError("Error", "module", "modulo_existente");
            result.addError(err);
        }

        if(result.hasErrors()){
            throw  new ResponseStatusException(HttpStatus.BAD_REQUEST, formatMessage.format(result));
        }

        return ResponseEntity.status(HttpStatus.CREATED).body(tenantModuleService.createTenantModule(tenantModule));
    }

    @PutMapping()
    public ResponseEntity<TenantModule> updateTenantModule(@Valid @RequestBody TenantModule tenantModule, BindingResult result){
        if(result.hasErrors()){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, formatMessage.format(result));
        }

        TenantModule tenantModuleDB = tenantModuleService.updateTenantModule(tenantModule);
        if(null == tenantModuleDB) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(tenantModuleDB);
    }

    @DeleteMapping(value = "/{id}")
    public ResponseEntity<Boolean> deleteTenantModule(@PathVariable("id") Long id){

        Boolean action = tenantModuleService.deleTenantModule(id);

        if ( action){
            return ResponseEntity.ok(action);
        }else{
            return ResponseEntity.notFound().build();
        }

    }
}
