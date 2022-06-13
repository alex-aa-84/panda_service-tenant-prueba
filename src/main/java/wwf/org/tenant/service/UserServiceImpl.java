package wwf.org.tenant.service;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import wwf.org.tenant.entity.TenantModule;
import wwf.org.tenant.entity.User;
import wwf.org.tenant.repository.UserRepository;

import java.util.Date;
import java.util.List;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService{

    @Autowired
    private UserRepository userRepository;

    @Override
    public List<User> listAllUser() {
        return userRepository.findAll();
    }

    @Override
    public User getUser(Long id) {
        return userRepository.findById(id).orElse(null);
    }

    @Override
    public User findByOid(String oid) {
        return userRepository.findByOid(oid);
    }

    @Override
    public User createUser(User user) {
        user.setStatus("CREATED");
        user.setCreation_date(new Date());
        user.setLast_update_date(new Date());
        return userRepository.save(user);
    }

    @Override
    public User updateUser(User user) {
        User userDB = getUser(user.getId());
        if(null == userDB){
            return null;
        }

        userDB.setTenant(user.getTenant());
        userDB.setOid(user.getOid());
        userDB.setUserPrincipalName(user.getUserPrincipalName());
        userDB.setDisplayName(user.getDisplayName());
        userDB.setSurname(user.getSurname());
        userDB.setGivenName(user.getGivenName());
        userDB.setJobTitle(user.getJobTitle());
        userDB.setMail(user.getMail());
        userDB.setMobilePhone(user.getMobilePhone());
        userDB.setOfficeLocation(user.getOfficeLocation());
        userDB.setPreferredLanguage(user.getPreferredLanguage());

        userDB.setPermissionsTenant(user.getPermissionsTenant());

        userDB.setStatus(user.getStatus());
        userDB.setLast_update_date(new Date());
        userDB.setLast_update_by(user.getLast_update_by());

        return userRepository.save(userDB);
    }

    @Override
    public Boolean deleteUser(Long id) {
        User userDB = getUser(id);

        if(null == userDB){
            return false;
        }

        userRepository.deleteById(id);
        return true;
    }
}
