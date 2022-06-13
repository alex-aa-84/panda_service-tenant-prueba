package wwf.org.tenant.service;

import wwf.org.tenant.entity.Module;

import java.util.List;

public interface ModuleService {

    public List<Module> listAllModule();
    public Module getModule(Long id);

    public Module createModule(Module module);

    public Module findByModule(String module);

    public Module findByRouterLink(String routerLink);
    public Module findByServiceUrl(String serviceUrl);

    public Module updateModule(Module module);

    public Boolean deleteModule(Long id);
}
