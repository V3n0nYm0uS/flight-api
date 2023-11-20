package fr.unilasalle.flight.api.repositories;

@Model
public class AvionRepository implements PanacheRepositoryBase<Avion,Long>{
    public List<Avion> findByOperator(String operatorParameter){
        return find("operator", operatorParameter).list();
    }
}