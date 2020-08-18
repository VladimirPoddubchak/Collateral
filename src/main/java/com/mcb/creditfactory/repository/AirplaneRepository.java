package com.mcb.creditfactory.repository;

import com.mcb.creditfactory.model.Airplane;
import com.mcb.creditfactory.model.Assess;
import org.springframework.data.repository.CrudRepository;

import java.util.UUID;

/**
 * Created by @author Vladimir Poddubchak @date 17.08.2020.
 */

public interface AirplaneRepository extends CrudRepository<Airplane, UUID> {
}
