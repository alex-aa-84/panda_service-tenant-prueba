package wwf.org.tenant.service;

import wwf.org.tenant.entity.AdministrativeUnit;

import java.util.List;

public interface AdministrativeUnitService {

    public List<AdministrativeUnit> listAllAdministrativeUnit();
    public AdministrativeUnit getAdministrativeUnit(Long id);

    public AdministrativeUnit findByAdministrativeUnit(String administrative_unit);
    public AdministrativeUnit createAdministrativeUnit(AdministrativeUnit administrativeUnit);
    public AdministrativeUnit updateAdministrativeUnit(AdministrativeUnit administrativeUnit);

    public Boolean deleteAdministrativeUnit(Long id);
}
