package com.bookmyshow.repository;

import com.bookmyshow.entity.Show;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ShowRepository extends JpaRepository<Show, Long> {

}