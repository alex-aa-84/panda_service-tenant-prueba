package wwf.org.tenant.service;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import wwf.org.tenant.entity.Tenant;
import wwf.org.tenant.repository.TenantRepository;
import wwf.org.tenant.serviceApi.MD5Util;

import java.util.Date;
import java.util.List;

@Service
@RequiredArgsConstructor
public class TenantServiceImpl implements TenantService{

    @Autowired
    private TenantRepository tenantRepository;
    @Override
    public List<Tenant> listAllTenant() {
        return tenantRepository.findAll();
    }

    @Override
    public Tenant getTenant(Long id) {
        return tenantRepository.findById(id).orElse(null);
    }

    @Override
    public Tenant createTenant(Tenant tenant) {
        tenant.setStatus("CREATED");
        tenant.setCreation_date(new Date());
        tenant.setLast_update_date(new Date());
        return tenantRepository.save(tenant);
    }

    @Override
    public Tenant findByTenant(String tenant) {
        return tenantRepository.findByTenant(tenant);
    }

    @Override
    public Tenant findByDomain(String domain) {
        return tenantRepository.findByDomain(domain);
    }

    @Override
    public Tenant updateTenant(Tenant tenant) {
        Tenant tenantDB = getTenant(tenant.getId());
        if(null == tenantDB){
            return null;
        }

        tenantDB.setTenant(tenant.getTenant());
        tenantDB.setClientId(tenant.getClientId());
        tenantDB.setDomain(tenant.getDomain());
        tenantDB.setOrganization(tenant.getOrganization());
        tenantDB.setDepartmentWwf(tenant.getDepartmentWwf());
        tenantDB.setAdministrativeUnit(tenant.getAdministrativeUnit());
        tenantDB.setCountry(tenant.getCountry());

        tenantDB.setStatus(tenant.getStatus());
        tenantDB.setLast_update_date(new Date());
        tenantDB.setLast_update_by(tenant.getLast_update_by());
        return tenantRepository.save(tenantDB);
    }

    @Override
    public Boolean deleTenant(Long id) {
        Tenant tenantDB = getTenant(id);

        if(null == tenantDB){
            return false;
        }

        tenantRepository.deleteById(id);
        return true;
    }
}
