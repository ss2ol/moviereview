package kr.co.js.moviereview.Repository;

import kr.co.js.moviereview.entity.Member;
import kr.co.js.moviereview.entity.Movie;
import kr.co.js.moviereview.entity.Review;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ReviewRepository extends JpaRepository<Review, Long> {
   @EntityGraph(attributePaths = {"member"}, type = EntityGraph.EntityGraphType.FETCH)
   //영화 정보를 가지고 모든 영화의 모든 리뷰를 가져오는 메서드
    List<Review> findByMovie (Movie movie);
   //멤버가 지워질때 같이 데이터를 지우는 메서드
   void deleteByMember (Member member);

   //뮤비가 지워질때 같이 데이터를 지우는 메서드
    void deleteByMovie (Movie movie);


}
