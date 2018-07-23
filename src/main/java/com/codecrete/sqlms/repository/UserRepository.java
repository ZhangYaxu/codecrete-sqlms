package com.codecrete.sqlms.repository;

import com.codecrete.domain.model.User;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

/**
 *
 */
@Repository
public interface UserRepository extends CrudRepository<User,Long> {

    /**
     * Lookup the corresponding @{code User} object for the specified email
     * address.
     *
     * @param email The unique user email address
     * @return The @{code User} object associated with the specified email
     */
    User getByEmail(String email);
}
