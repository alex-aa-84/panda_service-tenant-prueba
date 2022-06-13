package wwf.org.tenant.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import wwf.org.tenant.entity.EmailConfiguration;
import wwf.org.tenant.service.EmailConfigurationService;
import wwf.org.tenant.serviceApi.FormatMessage;

import javax.validation.Valid;
import java.util.List;

@CrossOrigin(origins = {"${settings.cors_origin}"}, maxAge = 3600,
        allowedHeaders={"Origin", "X-Requested-With", "Content-Type", "Accept", "x-client-key", "x-client-token", "x-client-secret", "Authorization"}, methods = {RequestMethod.GET, RequestMethod.POST, RequestMethod.OPTIONS, RequestMethod.HEAD, RequestMethod.DELETE, RequestMethod.PUT})
@RestController
@RequestMapping(value="/wwf/emails")
public class EmailConfigurationController {

    @Autowired
    private EmailConfigurationService emailConfigurationService;

    private FormatMessage formatMessage = new FormatMessage();

    @GetMapping
    public ResponseEntity<List<EmailConfiguration>> listEmailConfiguration(){
        List<EmailConfiguration> emails = emailConfigurationService.listAllEmailConfiguration();
        if(emails.isEmpty()){
            return ResponseEntity.noContent().build();
        }

        return ResponseEntity.ok(emails);
    }

    @GetMapping(value = "/{id}")
    public ResponseEntity<EmailConfiguration> getEmailConfiguration(@PathVariable("id") Long id){
        EmailConfiguration email = emailConfigurationService.getEmailConfiguration(id);
        if(null == email){
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(email);
    }

    @PostMapping()
    public ResponseEntity<EmailConfiguration> createEmailConfiguration(@Valid @RequestBody EmailConfiguration emailConfiguration, BindingResult result){
        EmailConfiguration emailConfigurationBD = emailConfigurationService.findByTenantId(emailConfiguration.getTenantId().getId());

        if (null != emailConfigurationBD){
            FieldError err = new FieldError("Error", "emailConfiguration", "email_configuracion_existente");
            result.addError(err);
        }

        if(result.hasErrors()){
            throw  new ResponseStatusException(HttpStatus.BAD_REQUEST, formatMessage.format(result));
        }

        return ResponseEntity.status(HttpStatus.CREATED).body(emailConfigurationService.createEmailConfiguration(emailConfiguration));
    }

    @PutMapping()
    public ResponseEntity<EmailConfiguration> updateEmailConfiguration(@Valid @RequestBody EmailConfiguration emailConfiguration, BindingResult result){
        if(result.hasErrors()){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, formatMessage.format(result));
        }

        EmailConfiguration emailConfigurationDB = emailConfigurationService.updateEmailConfiguration(emailConfiguration);
        if(null == emailConfigurationDB) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(emailConfigurationDB);
    }

    @DeleteMapping(value = "/{id}")
    public ResponseEntity<Boolean> deleteEmailConfiguration(@PathVariable("id") Long id){

        Boolean action = emailConfigurationService.deleteEmailConfiguration(id);

        if ( action){
            return ResponseEntity.ok(action);
        }else{
            return ResponseEntity.notFound().build();
        }

    }
}
