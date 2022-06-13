package wwf.org.tenant.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import wwf.org.tenant.entity.AdministrativeUnit;

public interface AdministrativeUnitRepository extends JpaRepository<AdministrativeUnit, Long> {
    public AdministrativeUnit findByAdministrativeUnit(String administrative_unit);
}
