package wwf.org.tenant.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import wwf.org.tenant.entity.Tenant;

import java.util.List;

public interface TenantRepository extends JpaRepository<Tenant, Long> {

    public Tenant findByTenant(String tenant);
    public Tenant findByDomain(String domain);

}
