
package pae.jogja.project.ktp;

import org.springframework.data.repository.CrudRepository;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import pae.jogja.project.ktp.Data;

/**
 *
 * @author user
 */
@Repository
public interface DataRepository extends CrudRepository<Data, Long> {
    
}
