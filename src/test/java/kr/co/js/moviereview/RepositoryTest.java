package kr.co.js.moviereview;

import kr.co.js.moviereview.Repository.MemberRepository;
import kr.co.js.moviereview.Repository.MovieImageRepository;
import kr.co.js.moviereview.Repository.MovieRepository;
import kr.co.js.moviereview.Repository.ReviewRepository;
import kr.co.js.moviereview.entity.Member;
import kr.co.js.moviereview.entity.Movie;
import kr.co.js.moviereview.entity.MovieImage;
import kr.co.js.moviereview.entity.Review;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.test.annotation.Commit;

import javax.transaction.Transactional;
import java.util.Arrays;
import java.util.List;
import java.util.Random;
import java.util.UUID;
import java.util.stream.IntStream;

@SpringBootTest
public class RepositoryTest {

    @Autowired
    private MovieRepository movieRepository;

    @Autowired
    private MovieImageRepository movieImageRepository;

    @Autowired
    private MemberRepository memberRepository;

    @Autowired
    private ReviewRepository reviewRepository;

    //@Test
    @Transactional
    @Commit
    public void insertMovies (){
        Random r = new Random();

        IntStream.rangeClosed(1,100).forEach(i ->{
            Movie movie = Movie.builder().title("Movie_" +i).build();
            movieRepository.save(movie);

            int count = r.nextInt(5);
            for(int j=0; j<count; i=j++){
                MovieImage movieImage = MovieImage.builder()
                        .uuid(UUID.randomUUID().toString())
                        .imgName("test_" + j + ".png")
                        .movie(movie)
                        .build();

                movieImageRepository.save(movieImage);
            }
        });
    }

    //Member 100개 삽입하는 테스트
   // @Test
    public void insertMember(){
        IntStream.rangeClosed(1,100).forEach(i ->{
            Member member = Member.builder()
                    .email("user"+i +"@gmail.com")
                    .pw("1234")
                    .nickname("js_"+i)
                    .build();
            memberRepository.save(member);
    });
    }

    //Review데이터를 200개 삽입하는 메서드
    //@Test
    public void reviewTest(){
        Random r = new Random();
        for(int i=1; i<=200; i++){
            Long mid = (long)(r.nextInt(100)+1);
            Long mno = (long)(r.nextInt(100)+1);

            Member member = Member.builder()
                    .mid(mid)
                    .build();

            Movie movie = Movie.builder()
                    .mno(mno)
                    .build();

            Review review = Review.builder()
                    .member(member)
                    .movie(movie)
                    .grade(r.nextInt(5)+1)
                    .text("리뷰_"+ i)
                    .build();
            reviewRepository.save(review);

        }
    }


    //영화 목록 가져오는 메서드
    //@Test
    public void testListPage(){
        PageRequest pageRequest = PageRequest.of(0,10,
                Sort.by("mno").descending());
        Page<Object[]> result = movieRepository.getListPage(pageRequest);
        for (Object[] objects : result.getContent()) {
            System.out.println(Arrays.toString(objects));
        }
    }

    //특정 영화에 대한 정보를 가져오는 메서드
   // @Test
    public void testGetMovie(){
        List<Object[]> result = movieRepository.getMovieWithAll(34L);
        for(Object[] r : result){
            System.out.println(Arrays.toString(r));
        }
    }

    // 특정 영화에 해당하는 모든 리뷰 가져오기
    @Test
    public void testGetReviews() {
        List<Review> list = reviewRepository.findByMovie(Movie.builder().mno(86L).build());

        for(Review r : list){
            //회원의 이메일 출력
            System.out.println(r.getMember().getEmail());

        }

    }

}

