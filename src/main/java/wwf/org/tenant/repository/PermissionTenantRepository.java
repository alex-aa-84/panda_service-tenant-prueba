package wwf.org.tenant.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import wwf.org.tenant.entity.PermissionTenant;

public interface PermissionTenantRepository extends JpaRepository<PermissionTenant, Long> {

    public PermissionTenant findByPermissions(String permissions);
}
