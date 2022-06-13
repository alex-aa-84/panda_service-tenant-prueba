package wwf.org.tenant.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import wwf.org.tenant.entity.Module;
import wwf.org.tenant.service.ModuleService;
import wwf.org.tenant.serviceApi.FormatMessage;

import javax.validation.Valid;
import java.util.List;

@CrossOrigin(origins = {"${settings.cors_origin}"}, maxAge = 3600,
        allowedHeaders={"Origin", "X-Requested-With", "Content-Type", "Accept", "x-client-key", "x-client-token", "x-client-secret", "Authorization"}, methods = {RequestMethod.GET, RequestMethod.POST, RequestMethod.OPTIONS, RequestMethod.HEAD, RequestMethod.DELETE, RequestMethod.PUT})
@RestController
@RequestMapping(value="/wwf/modules")
public class ModuleController {

    @Autowired
    private ModuleService moduleService;

    private FormatMessage formatMessage = new FormatMessage();

    @GetMapping
    public ResponseEntity<List<Module>> listModule(){
        List<Module> modules = moduleService.listAllModule();
        if(modules.isEmpty()){
            return ResponseEntity.noContent().build();
        }

        return ResponseEntity.ok(modules);
    }

    @GetMapping(value = "/{id}")
    public ResponseEntity<Module> getModule(@PathVariable("id") Long id){
        Module module = moduleService.getModule(id);
        if(null == module){
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(module);
    }

    @PostMapping()
    public ResponseEntity<Module> createModule(@Valid @RequestBody Module module, BindingResult result){
        Module moduleBD = moduleService.findByModule(module.getModule());

        if (null != moduleBD){
            FieldError err = new FieldError("Error", "module", "Modulo existente en la BD");
            result.addError(err);
        }

        if(result.hasErrors()){
            throw  new ResponseStatusException(HttpStatus.BAD_REQUEST, formatMessage.format(result));
        }

        return ResponseEntity.status(HttpStatus.CREATED).body(moduleService.createModule(module));
    }

    @PutMapping()
    public ResponseEntity<Module> updateModule(@Valid @RequestBody Module module, BindingResult result){
        if(result.hasErrors()){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, formatMessage.format(result));
        }

        Module moduleDB = moduleService.updateModule(module);
        if(null == moduleDB) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(moduleDB);
    }

    @DeleteMapping(value = "/{id}")
    public ResponseEntity<Boolean> deleteModule(@PathVariable("id") Long id){

        Boolean action = moduleService.deleteModule(id);

        if ( action){
            return ResponseEntity.ok(action);
        }else{
            return ResponseEntity.notFound().build();
        }

    }

}
