package wwf.org.tenant.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import wwf.org.tenant.entity.Country;
import wwf.org.tenant.entity.EmailConfiguration;

public interface EmailConfigurationRepository extends JpaRepository<EmailConfiguration, Long> {

    public EmailConfiguration findByTenantId(Long tenandId);

}
