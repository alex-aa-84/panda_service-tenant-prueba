package wwf.org.tenant.service;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import wwf.org.tenant.entity.Module;
import wwf.org.tenant.entity.Tenant;
import wwf.org.tenant.entity.TenantModule;
import wwf.org.tenant.repository.TenantModuleRepository;
import wwf.org.tenant.serviceApi.MD5Util;

import java.util.Date;
import java.util.List;

@Service
@RequiredArgsConstructor
public class TenantModuleServiceImpl implements TenantModuleService{

    @Autowired
    private TenantModuleRepository tenantModuleRepository;

    @Override
    public List<TenantModule> listAllTenantModule() {
        return tenantModuleRepository.findAll();
    }

    @Override
    public TenantModule getTenantModule(Long id) {
        return tenantModuleRepository.findById(id).orElse(null);
    }

    @Override
    public TenantModule createTenantModule(TenantModule tenantModule) {
        tenantModule.setStatus("CREATED");
        tenantModule.setCreation_date(new Date());
        tenantModule.setLast_update_date(new Date());
        String md5 = MD5Util.string2MD5(tenantModule.getTenant().getId().toString()+tenantModule.getModule().getId().toString() + "tnMd");
        tenantModule.setCtrlMd5(md5);
        return tenantModuleRepository.save(tenantModule);
    }

    @Override
    public TenantModule findByTenantIdAndModuleId(Long tenant_id, Long module_id) {
        return tenantModuleRepository.findByTenantIdAndModuleId(tenant_id, module_id);
    }

    @Override
    public List<TenantModule> findByTenant(Tenant tenant) {
        return tenantModuleRepository.findByTenant(tenant);
    }

    @Override
    public TenantModule updateTenantModule(TenantModule tenantModule) {
        TenantModule tenantModuleDB = getTenantModule(tenantModule.getId());
        if(null == tenantModuleDB){
            return null;
        }

        tenantModuleDB.setTenant(tenantModule.getTenant());
        tenantModuleDB.setModule(tenantModule.getModule());

        tenantModuleDB.setStatus(tenantModule.getStatus());
        tenantModuleDB.setLast_update_date(new Date());
        tenantModuleDB.setLast_update_by(tenantModule.getLast_update_by());
        String md5 = MD5Util.string2MD5(tenantModule.getTenant().getId()+ tenantModule.getModule().getId()+"");
        tenantModuleDB.setCtrlMd5(md5);
        return tenantModuleRepository.save(tenantModuleDB);
    }

    @Override
    public Boolean deleTenantModule(Long id) {
        TenantModule tenantModuleDB = getTenantModule(id);

        if(null == tenantModuleDB){
            return false;
        }

        tenantModuleRepository.deleteById(id);
        return true;
    }
}
