package kr.co.js.moviereview.Repository;

import kr.co.js.moviereview.entity.MovieImage;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MovieImageRepository extends JpaRepository<MovieImage, Long> {

}
