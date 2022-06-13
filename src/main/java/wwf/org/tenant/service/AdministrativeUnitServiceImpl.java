package wwf.org.tenant.service;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import wwf.org.tenant.entity.AdministrativeUnit;
import wwf.org.tenant.repository.AdministrativeUnitRepository;

import java.util.Date;
import java.util.List;

@Service
@RequiredArgsConstructor
public class AdministrativeUnitServiceImpl implements AdministrativeUnitService{

    @Autowired
    private AdministrativeUnitRepository administrativeUnitRepository;

    @Override
    public List<AdministrativeUnit> listAllAdministrativeUnit() {
        return administrativeUnitRepository.findAll();
    }

    @Override
    public AdministrativeUnit getAdministrativeUnit(Long id) {
        return administrativeUnitRepository.findById(id).orElse(null);
    }

    @Override
    public AdministrativeUnit findByAdministrativeUnit(String administrative_unit) {
        return administrativeUnitRepository.findByAdministrativeUnit(administrative_unit);
    }

    @Override
    public AdministrativeUnit createAdministrativeUnit(AdministrativeUnit administrativeUnit) {
        administrativeUnit.setStatus("CREATED");
        administrativeUnit.setCreation_date(new Date());
        administrativeUnit.setLast_update_date(new Date());

        return administrativeUnitRepository.save(administrativeUnit);
    }

    @Override
    public AdministrativeUnit updateAdministrativeUnit(AdministrativeUnit administrativeUnit) {
        AdministrativeUnit administrativeUnitDB = getAdministrativeUnit(administrativeUnit.getId());

        if(null == administrativeUnitDB){
            return null;
        }

        administrativeUnitDB.setAdministrativeUnit(administrativeUnit.getAdministrativeUnit());
        administrativeUnitDB.setDescription(administrativeUnit.getDescription());
        administrativeUnitDB.setStatus(administrativeUnit.getStatus());
        administrativeUnitDB.setLast_update_date(new Date());
        administrativeUnitDB.setLast_update_by(administrativeUnit.getLast_update_by());

        return administrativeUnitRepository.save(administrativeUnitDB);

    }

    @Override
    public Boolean deleteAdministrativeUnit(Long id) {
        AdministrativeUnit administrativeUnitDB = getAdministrativeUnit(id);

        if(null == administrativeUnitDB){
            return false;
        }

        administrativeUnitRepository.deleteById(id);
        return true;

    }
}
