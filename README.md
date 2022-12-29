# 바니바니 당근 마켓(Back-End) readME 수정중
> **당근 마켓 클론 코딩 프로젝트**  
> 바니바니 당근마켓. 중고 거래, 이웃과 함께해요. 가깝고 따뜻한 당신의 근처를 만들어요.

* [[Notion: 항해언니 프로젝트 정보]](https://royal-storm-069.notion.site/6-21181e915241465c92495075706dbe3c)
* [[프로젝트 시연영상 보러가기]](https://youtu.be/gjkK6H2Z5v4)
* [[Front-End Repository]](https://github.com/banibani-banibani-danggeun-danggeun/FE_new)  
  
## 📆 개발 기간  
2022년 12월 23일 ~ 2022년 12월 29일   
<p>

  
## 👥 팀 소개
#### `Frontend`
 <a href="https://github.com/JIEUN24" target="_blank"><img height="40"  src="https://img.shields.io/static/v1?label=React&message=최신영 &color=61dafb&style=for-the-badge&>"/></a>
 <a href="https://github.com/GYMMX" target="_blank"><img height="40"  src="https://img.shields.io/static/v1?label=React&message=김세연 &color=61dafb&style=for-the-badge&>"/></a>
 <a href="https://github.com/GYMMX" target="_blank"><img height="40"  src="https://img.shields.io/static/v1?label=React&message=김하영 &color=61dafb&style=for-the-badge&>"/></a>

#### `Backend`
<a href="https://github.com/ksanacloud" target="_blank"><img height="40"  src="https://img.shields.io/static/v1?label=Spring&message=김규민 &color=08CE5D&style=for-the-badge&>"/></a>
<a href="https://github.com/EunheaSong" target="_blank"><img height="40"  src="https://img.shields.io/static/v1?label=Spring&message=유종열 &color=08CE5D&style=for-the-badge&>"/></a>
<a href="https://github.com/hyun-woong" target="_blank"><img height="40"  src="https://img.shields.io/static/v1?label=Spring&message=심현웅 &color=08CE5D&style=for-the-badge&>"/></a>
  
## 🛠️ 기술 스택
  
|종류|기술|
|:----:|:----:|
|Language|<img src="https://img.shields.io/badge/Java-007396?style=flat-square&logo=java&logoColor=white"/>|
|Build|<img src="https://img.shields.io/badge/Gralde-02303A?style=flat-square&logo=Gradle&logoColor=white"/>|
|FrameWork|<img src="https://img.shields.io/badge/SpringBoot-6DB33F?style=flat-square&logo=springboot&logoColor=white"/>|
|DB|<img src="https://img.shields.io/badge/MySQL-4479A1?style=flat-square&logo=MySQL&logoColor=white"/><br><img src="https://img.shields.io/badge/Amazon RDS-527FFF?style=flat-square&logo=Amazon RDS&logoColor=white"/>|
|Server|<img src="https://img.shields.io/badge/Amazon EC2-FF9900?style=flat-square&logo=Amazon EC2&logoColor=white"/>  

## 📌 API 명세서 (노션 페이지에서 선명하게 조회 가능)
<img width="800" alt="api1" src="https://blog.kakaocdn.net/dn/bYcpJq/btrUViSu5V3/RLAlJCsvfkKqYZk5izKQL0/img.png">  
<img width="800" alt="api2" src="https://blog.kakaocdn.net/dn/bQMZMc/btrUV4NhRnu/bVkcjh31Tv5V0VHYe8RXx1/img.png">  
<img width="800" alt="api3" src="https://blog.kakaocdn.net/dn/EzZHZ/btrUTJbNH97/Ry7iBWzelmvDX3U8rUxmC1/img.png">  
<img width="800" alt="api3" src="https://blog.kakaocdn.net/dn/EifTa/btrUVbFUujz/8KdMujorfyUhy87XI5i3x1/img.png">

## 📏 와이어프레임

![와이어프레임](https://cdn.discordapp.com/attachments/1037267111585792020/1055867403344543754/IMB_XZKGyK.gif)

## 🗺 ERD
<img width="800" alt="메인페이지2" src="https://user-images.githubusercontent.com/65327103/209082821-ecdf919c-601a-4c36-b2d3-5d205b0414f6.png">  

## 🧩아키텍쳐
<img width="800" alt="메인페이지2" src="https://blog.kakaocdn.net/dn/bOCbtg/btrUneCW2D2/BZyYOomTIzYOHSm4bmkEK1/img.png">  

## 💡 Trouble Shooting
<details>
<summary>1. 채팅 CORS 에러</summary>
<br>
<div markdown="1">
<b>오픈 api를 사용하여 전국의 병‧의원 중 진료과목에 성형외과가 있는 기관명을 받아와 조회하는 방식으로 해결</b>   
 - "Invalid SockJS path '/chat/room' - required to have 3 path segments" 에러 메시지  
 - registerStompEndpoints 메소드에서 endpoint 설정, CORS 정책을 위한 출저 허용, SockJS 설정  
 - 프론트에서 API에 따라 요청을 보낼 때 CORS 문제, url에 endpoint를 추가하여 요청하면 404에러  
 - 특정 채팅방 조회는 404, 전체 방조회는 CORS    
 - 원인 response에 access-control-allow-origin응답이 없었음  
 - spring security를 이용해 해결  
</div>
</details>

<details>
<summary>2. 해당 게시글에서 작성한 댓글이 아님에도 댓글 아이디값만으로 모든 댓글의 수정이 가능했던 문제</summary>
<br>
<div markdown="2">
<b>게시글과 댓글의 존재 여부는 확인했으나 해당 댓글이 그 게시글에 등록된 댓글인지 확인지 않았기 때문에 발생한 문제로,&nbsp;댓글이 가진 게시글ID가 해당 게시글의 ID와 같은지 확인 하는 코드를 추가하여 해결</b> 

```java
//추가한 코드
if (!comment.getPostId().equals(postId)) {
	throw new CustomException(ErrorCode.MISMATCH_COMMENT);
}
``` 


</div>
</details>

<details>
<summary>3. 포스트에서 예외처리가 부족하여 어떤 에러가 나는지 확인이 불가능했던 문제</summary>
<br>
<div markdown="3">
<b>requestbody에서 값이 들어오지 않았을때의 예외 처리가 부족하여 PostDto에 @NotBalnk를 추가하는 방식으로 해결</b>  
	
<b>수정 전 코드</b>  
	
  ```java
  @Getter
  public class PostRequestDto {
    private String title;
    private String category;
    private String imageBefore;
    private String imageAfter;
    private String content;
    private int price;
    private String hospitalAddress;
    private String doctor;
  }
  ```
  
  <b>수정 후 코드</b>  
   ```java
  public class PostDto {

	public record RequestDto(@NotBlank(message = "제목이 입력되지 않았습니다.") String title,
							 @NotBlank(message = "카테고리가 입력되지 않았습니다.") String category,
							 @NotBlank(message = "성형 전 이미지가 필요합니다.") String imageBefore,
							 @NotBlank(message = "성형 후 이미지가 필요합니다.") String imageAfter,
							 @NotBlank(message = "본문 내용이 입력되지 않았습니다.") String content,
							 @NotNull(message = "금액이 입력되지 않았습니다.") Integer price,
							 @NotBlank(message = "병원 주소가 입력되지 않았습니다.") String hospitalAddress,
							 @NotBlank(message = "의사 정보가 입력되지 않았습니다.") String doctor) {
	  }
  }
  ```

</div>
</details>

<details>
<summary>4. Jwt Util 클래스 관련 에러가 발생한 문제</summary>
<br>
<div markdown="4">
<b> application properties와 jwt Util 클래스에 jwt secret key를 같은 이름으로 매치시켜주지 못해 발생한 에러였다. </b> 
<br>
<b> 정말 간단히 해결할 수 있었던 거지만 왜 에러가 나는지 감이 안잡혀서 생각보다 오랜 시간을 소비했던 문제였었다.</b>
<br>
<br>

```java
  public JwtUtil(@Value("${jwt.secret}") String secretKey) {
  
}
```

```java
  jwt.secret.key=7ZWt7ZW0OTntmZTsnbTtjIXtl.....=
}
```
<br>
<b>위와 아래의 jwt secret 부분을, 한 곳엔 key를 붙여놨었고 한 곳엔 빼놔서 발생했던 문제라 통일해주고 오류를 해결할 수 있었다.</b>

</div>
</details>
