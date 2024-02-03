package ma.enset.examenjdcxfx1.dao;

import java.util.List;

public interface DAO<T , U> {
    void ajouter(T t);
    void supprimer(U u);
    List<T> afficherListe();
    void update(T o);
    T getByID(U id);


}