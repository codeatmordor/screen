package com.gk.screenshot.dal;

import java.util.Set;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface ScreenRepository extends JpaRepository<Screen,String>{
	
	@Query("SELECT s FROM Screen s  WHERE s.status=(:status)")
    Set<Screen> findAllForStatus(@Param("status") final String status);

}
