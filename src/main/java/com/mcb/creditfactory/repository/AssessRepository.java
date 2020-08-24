package com.mcb.creditfactory.repository;

import com.mcb.creditfactory.model.Assess;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

/**
 * Created by @author Vladimir Poddubchak @date 17.08.2020.
 */

@Repository
public interface AssessRepository extends CrudRepository<Assess,Long> {

    @Query(value = "SELECT * " +
                        "FROM ASSESS a " +
                        "WHERE a.collateral_id =:uuid " +
                        "ORDER BY assess_date DESC",nativeQuery = true)
    List<Assess> findAllByCollateralId(@Param("uuid") UUID uuid);

//    @Query(value = "WITH assesses AS(" +
//                        "SELECT * " +
//                        "FROM ASSESS a " +
//                        "WHERE a.collateral_id =:uuid) " +
//                    "SELECT * " +
//                    "FROM assesses " +
//                    "WHERE assess_date = (" +
//                        "SELECT MAX(assess_date) " +
//                        "FROM assesses)",nativeQuery = true)
@Query(value = "SELECT * " +
        "FROM ASSESS a " +
        "WHERE a.collateral_id =:uuid " +
        "ORDER BY assess_date DESC LIMIT 1",nativeQuery = true)
    Assess findActualAssessByCollateralId(@Param("uuid") UUID uuid);
}
