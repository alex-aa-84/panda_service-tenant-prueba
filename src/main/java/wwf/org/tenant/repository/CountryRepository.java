package wwf.org.tenant.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import wwf.org.tenant.entity.AdministrativeUnit;
import wwf.org.tenant.entity.Country;

public interface CountryRepository extends JpaRepository<Country, Long> {

    public Country findByCountry(String country);

}
