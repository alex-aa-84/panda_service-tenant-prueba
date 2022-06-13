package wwf.org.tenant.service;

import wwf.org.tenant.entity.Tenant;
import wwf.org.tenant.entity.TenantModule;

import java.util.List;

public interface TenantModuleService {

    public List<TenantModule> listAllTenantModule();
    public TenantModule getTenantModule(Long id);

    public TenantModule createTenantModule(TenantModule tenantModule);

    public TenantModule findByTenantIdAndModuleId(Long tenant_id, Long module_id);
    public List<TenantModule> findByTenant(Tenant tenant);
    public TenantModule updateTenantModule(TenantModule tenantModule);

    public Boolean deleTenantModule(Long id);
}
