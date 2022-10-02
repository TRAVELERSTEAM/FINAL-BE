package com.travelers.biz.repository.review;

import com.travelers.biz.domain.Review;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ReviewRepository extends JpaRepository<Review, Long>, ReviewRepositoryQuery {
}
