package com.heitor.minhaApi.repostirory;


import com.heitor.minhaApi.entity.IdentytiProviderSSO;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface IdentytiProviderSSORepository extends JpaRepository<IdentytiProviderSSO, Integer> {
    Optional<IdentytiProviderSSO> findByAlias(String aliasProvider);
}
