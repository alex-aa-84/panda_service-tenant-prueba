package wwf.org.tenant.service;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import wwf.org.tenant.entity.Country;
import wwf.org.tenant.repository.CountryRepository;

import java.util.Date;
import java.util.List;

@Service
@RequiredArgsConstructor
public class CountryServiceImpl implements CountryService{

    @Autowired
    private CountryRepository countryRepository;

    @Override
    public List<Country> listAllCountry() {
        return countryRepository.findAll();
    }

    @Override
    public Country getCountry(Long id) {
        return countryRepository.findById(id).orElse(null);
    }

    @Override
    public Country createCountry(Country country) {
        country.setStatus("CREATED");
        country.setCreation_date(new Date());
        country.setLast_update_date(new Date());

        return countryRepository.save(country);
    }

    @Override
    public Country updateCountry(Country country) {
        Country countryDB = getCountry(country.getId());
        if(null == countryDB){
            return null;
        }

        countryDB.setCountry(country.getCountry());
        countryDB.setDescription(country.getDescription());
        countryDB.setStatus(country.getStatus());
        countryDB.setLast_update_date(new Date());
        countryDB.setLast_update_by(country.getLast_update_by());

        return countryRepository.save(countryDB);
    }

    @Override
    public Country findByCountry(String country) {
        return countryRepository.findByCountry(country);
    }

    @Override
    public Boolean deleteCountry(Long id) {
        Country countryDB = getCountry(id);

        if(null == countryDB){
            return false;
        }

        countryRepository.deleteById(id);
        return true;
    }
}
