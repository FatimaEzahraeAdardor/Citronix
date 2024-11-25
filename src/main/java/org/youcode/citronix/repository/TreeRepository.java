package org.youcode.citronix.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.youcode.citronix.domain.Farm;
import org.youcode.citronix.domain.Tree;

import java.util.UUID;

@Repository
public interface TreeRepository extends JpaRepository<Tree, UUID> {
}
