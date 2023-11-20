package fr.unilasalle.flight.api.repositories;

@Model
public class PlaneRepository implements PanacheRepositoryBase<Plane,Long>{
    public List<Plane> findByOperator(String operatorParameter){
        return find("operator", operatorParameter).list();
    }
}