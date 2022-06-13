package wwf.org.tenant.service;

import wwf.org.tenant.entity.EmailConfiguration;

import java.util.List;

public interface EmailConfigurationService {

    public List<EmailConfiguration> listAllEmailConfiguration();
    public EmailConfiguration getEmailConfiguration(Long id);

    public EmailConfiguration createEmailConfiguration(EmailConfiguration email);
    public EmailConfiguration updateEmailConfiguration(EmailConfiguration email);

    public EmailConfiguration findByTenantId(Long tenant_id);

    public Boolean deleteEmailConfiguration(Long id);
}
