package kr.co.js.moviereview.entity;

import lombok.*;

import javax.persistence.*;

@Entity
@Embeddable
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Getter
@ToString(exclude = "movie") //연관 관계시 항상 주의
//movie과 함께 생성되고 수정되므로 별도의 생성 날짜와 수정날짜를 가질 필요 없음
public class MovieImage extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long inum;
    private String uuid;
    private String imgName;
    private String path;

    @ManyToOne(fetch = FetchType.LAZY) //무조건 lazy로
    private Movie movie;
}
