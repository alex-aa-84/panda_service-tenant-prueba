package wwf.org.tenant.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import wwf.org.tenant.entity.AdministrativeUnit;
import wwf.org.tenant.entity.Tenant;
import wwf.org.tenant.entity.TenantModule;
import wwf.org.tenant.entity.User;

import java.util.List;

public interface TenantModuleRepository extends JpaRepository<TenantModule, Long> {

    public TenantModule findByTenantIdAndModuleId(Long tenant_id, Long module_id);
    public List<TenantModule> findByTenant(Tenant tenant);

}
