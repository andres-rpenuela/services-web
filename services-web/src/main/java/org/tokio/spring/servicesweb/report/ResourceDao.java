package org.tokio.spring.servicesweb.report;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import org.tokio.spring.servicesweb.domain.Resource;

@Repository
public interface ResourceDao extends CrudRepository<Resource,Long> {
}
