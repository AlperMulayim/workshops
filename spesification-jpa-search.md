`````java
package com.alper.leasesoftprov2.leasesoft.buildings.filters;

import com.alper.leasesoftprov2.leasesoft.buildings.Building;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class BuildingSpecification {

    public Specification<Building> filterBy(BuildingFilter filter){
        return Specification
                .where(hasBeds(filter.getBeds()))
                .and(hasBaths(filter.getBaths()))
                .and(inCity(filter.getCity()))
                .and(priceFrom(filter.getPriceFrom()))
                .and(priceTo(filter.getPriceTo()))
                .and(sizeTo(filter.getSizeTo()))
                .and(sizeFrom(filter.getSizeFrom()));
    }

    Specification<Building> hasBeds(Optional<Integer> beds){
        return (((root, query, criteriaBuilder) -> beds != null && beds.isPresent() ?
                criteriaBuilder.equal(root.get("bedrooms"),beds.get()):
                criteriaBuilder.conjunction()));
    }

    Specification<Building> hasBaths(Optional<Integer> baths){
        return (((root, query, criteriaBuilder) -> baths!=null && baths.isPresent() ?
                criteriaBuilder.equal(root.get("bathrooms"),baths.get()):
                criteriaBuilder.conjunction() ));
    }

    Specification<Building> inCity(Optional<String> city){
        return (((root, query, criteriaBuilder) ->  city!=null &&  city.isPresent() && !city.get().isEmpty() ?
                criteriaBuilder.equal(root.get("city"),city.get()):
                criteriaBuilder.conjunction()));
    }

    Specification<Building> priceFrom(Optional<Double> priceFrom){
        return (((root, query, criteriaBuilder) -> priceFrom !=null &&  priceFrom.isPresent() ?
                criteriaBuilder.greaterThanOrEqualTo(root.get("listing").get("price"),priceFrom.get()) :
                criteriaBuilder.conjunction()));
    }

    Specification<Building> priceTo(Optional<Double> priceTo){
        return (((root, query, criteriaBuilder) -> priceTo !=null  && priceTo.isPresent() ?
                criteriaBuilder.lessThanOrEqualTo(root.get("listing").get("price"),priceTo.get()) :
                criteriaBuilder.conjunction()));
    }

    Specification<Building> sizeTo(Optional<Double> sizeTo){
        return (((root, query, criteriaBuilder) -> sizeTo !=null  && sizeTo.isPresent() ?
                criteriaBuilder.lessThanOrEqualTo(root.get("size"),sizeTo.get()) :
                criteriaBuilder.conjunction()));
    }

    Specification<Building> sizeFrom(Optional<Double> sizeFrom){
        return (((root, query, criteriaBuilder) -> sizeFrom !=null  && sizeFrom.isPresent() ?
                criteriaBuilder.greaterThanOrEqualTo(root.get("size"),sizeFrom.get()) :
                criteriaBuilder.conjunction()));
    }
}
`````


`````java
@Repository
public interface BuildingRepository  extends JpaRepository<Building,Integer> {
    List<Building> findAllByBedrooms(Integer bedrooms);
    List<Building> findAll(@Nullable Specification<Building> specification);
}
`````

``````java
@Service
public class BuildingService {

    @Autowired
    private BuildingRepository repository;

    @Autowired
    private BuildingMapper mapper;

    @Autowired
    private BuildingSpecification buildingSpecification;

    List<BuildingDto> getBuildings(BuildingFilter filter)  {
        Specification<Building> specification = buildingSpecification.filterBy(filter);
        return repository.findAll(specification).stream().map(building -> {
            try {
                return mapper.toDTO(building);
            } catch (NotSupportedException e) {
                throw new RuntimeException(e);
            }
        }).collect(Collectors.toList());
    }
}
``````
