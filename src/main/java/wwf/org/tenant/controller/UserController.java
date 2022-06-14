package wwf.org.tenant.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import wwf.org.tenant.entity.User;
import wwf.org.tenant.service.UserService;
import wwf.org.tenant.serviceApi.FormatMessage;

import javax.validation.Valid;
import java.util.List;

@CrossOrigin(origins = {"${settings.cors_origin}", "${settings.cors_origin_pro}"}, maxAge = 3600,
        allowedHeaders={"Origin", "X-Requested-With", "Content-Type", "Accept", "x-client-key", "x-client-token", "x-client-secret", "Authorization"}, methods = {RequestMethod.GET, RequestMethod.POST, RequestMethod.OPTIONS, RequestMethod.HEAD, RequestMethod.DELETE, RequestMethod.PUT})
@RestController
@RequestMapping(value="/wwf/users")
public class UserController {

    @Autowired
    private UserService userService;

    private FormatMessage formatMessage = new FormatMessage();

    @GetMapping
    public ResponseEntity<List<User>> listUsers(){
        List<User> users = userService.listAllUser();
        if(users.isEmpty()){
            return ResponseEntity.noContent().build();
        }

        return ResponseEntity.ok(users);
    }

    @GetMapping(value = "/{id}")
    public ResponseEntity<User> getUser(@PathVariable("id") Long id){
        User user = userService.getUser(id);
        if(null == user){
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(user);
    }

    @GetMapping(value = "/oid/{oid}")
    public ResponseEntity<User> getByTenant(@PathVariable("oid") String oid){
        User userR = userService.findByOid(oid);
        if(null == userR){
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(userR);
    }

    @PostMapping()
    public ResponseEntity<User> createUser(@Valid @RequestBody User user, BindingResult result){
        User userBD = userService.findByOid(user.getOid());

        if (null != userBD){
            FieldError err = new FieldError("Error", "users", "usuario_existente");
            result.addError(err);
        }

        if(result.hasErrors()){
            throw  new ResponseStatusException(HttpStatus.BAD_REQUEST, formatMessage.format(result));
        }

        return ResponseEntity.status(HttpStatus.CREATED).body(userService.createUser(user));
    }

    @PutMapping()
    public ResponseEntity<User> updateUser(@Valid @RequestBody User user, BindingResult result){
        if(result.hasErrors()){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, formatMessage.format(result));
        }

        User userDB = userService.updateUser(user);
        if(null == userDB) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(userDB);
    }

    @DeleteMapping(value = "/{id}")
    public ResponseEntity<Boolean> deleteUser(@PathVariable("id") Long id){

        Boolean action = userService.deleteUser(id);

        if ( action){
            return ResponseEntity.ok(action);
        }else{
            return ResponseEntity.notFound().build();
        }

    }
}
