package wwf.org.tenant.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import wwf.org.tenant.entity.PermissionTenant;
import wwf.org.tenant.service.PermissionTenantService;
import wwf.org.tenant.serviceApi.FormatMessage;

import javax.validation.Valid;
import java.util.List;

@CrossOrigin(origins = {"${settings.cors_origin}", "${settings.cors_origin_pro}"}, maxAge = 3600,
        allowedHeaders={"Origin", "X-Requested-With", "Content-Type", "Accept", "x-client-key", "x-client-token", "x-client-secret", "Authorization"}, methods = {RequestMethod.GET, RequestMethod.POST, RequestMethod.OPTIONS, RequestMethod.HEAD, RequestMethod.DELETE, RequestMethod.PUT})
@RestController
@RequestMapping(value="/wwf/permissions")
public class PermissionTenantController {

    @Autowired
    private PermissionTenantService permissionTenantService;

    private FormatMessage formatMessage = new FormatMessage();

    @GetMapping
    public ResponseEntity<List<PermissionTenant>> listPermissionTenant(){
        List<PermissionTenant> permissions = permissionTenantService.listAllPermissionTenant();
        if(permissions.isEmpty()){
            return ResponseEntity.noContent().build();
        }

        return ResponseEntity.ok(permissions);
    }

    @GetMapping(value = "/{id}")
    public ResponseEntity<PermissionTenant> getPermissionTenant(@PathVariable("id") Long id){
        PermissionTenant permission = permissionTenantService.getPermissionTenant(id);
        if(null == permission){
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(permission);
    }

    @PostMapping()
    public ResponseEntity<PermissionTenant> createPermissionTenant(@Valid @RequestBody PermissionTenant permissionTenant, BindingResult result){
        PermissionTenant permissionBD = permissionTenantService.findByPermissions(permissionTenant.getPermissions());

        if (null != permissionBD){
            FieldError err = new FieldError("Error", "permissionTenant", "permiso_existente");
            result.addError(err);
        }

        if(result.hasErrors()){
            throw  new ResponseStatusException(HttpStatus.BAD_REQUEST, formatMessage.format(result));
        }

        return ResponseEntity.status(HttpStatus.CREATED).body(permissionTenantService.createPermissionTenant(permissionTenant));
    }

    @PutMapping()
    public ResponseEntity<PermissionTenant> updatePermissionTenant(@Valid @RequestBody PermissionTenant permissionTenant, BindingResult result){
        if(result.hasErrors()){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, formatMessage.format(result));
        }

        PermissionTenant permissionTenantDB = permissionTenantService.updatePermissionTenant(permissionTenant);
        if(null == permissionTenantDB) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(permissionTenantDB);
    }

    @DeleteMapping(value = "/{id}")
    public ResponseEntity<Boolean> deletePermissionTenant(@PathVariable("id") Long id){

        Boolean action = permissionTenantService.deletePermissionTenant(id);

        if ( action){
            return ResponseEntity.ok(action);
        }else{
            return ResponseEntity.notFound().build();
        }

    }
}
