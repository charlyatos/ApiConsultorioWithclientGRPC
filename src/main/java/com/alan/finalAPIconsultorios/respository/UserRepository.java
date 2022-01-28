package com.alan.finalAPIconsultorios.respository;

import com.alan.finalAPIconsultorios.models.User;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository
        extends GenericRepository<User,Long> {

    //Specific QUERYS
    /*
    * List<User> findByName(String name);
    *
    *
    *
    * */

}
