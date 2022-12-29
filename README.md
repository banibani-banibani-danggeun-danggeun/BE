# 항해언니, Hanghae Sisters(Back-End)
> **성형에 대해 고민하고 있으신가요?**  
> 경험과 정보를 공유하고 고민을 덜어가세요!!

* [[Notion: 항해언니 프로젝트 정보]](https://www.notion.so/d2dffd18ee9d4d8e958185e288c08a2e)
* [[프로젝트 시연영상 보러가기]](https://youtu.be/gjkK6H2Z5v4)
* [[Front-End Repository]](https://github.com/HanghaeSisters/Front)  
  
## 📆 개발 기간  
2022년 12월 16일 ~ 2022년 12월 22일   
<p>

  
## 👯 팀원  
**김규민(팀장)**  
<p>
  
[<img src="https://img.shields.io/badge/Github-181717?style=flat-square&logo=Github&logoColor=white">](https://github.com/starMinK)
<img src="https://img.shields.io/badge/SpringBoot-6DB33F?style=flat-square&logo=springboot&logoColor=white"/>
</p>

**김소라**
<p>
  
[<img src="https://img.shields.io/badge/Github-181717?style=flat-square&logo=Github&logoColor=white">](https://github.com/dev-rara)
<img src="https://img.shields.io/badge/SpringBoot-6DB33F?style=flat-square&logo=springboot&logoColor=white"/>
</p>

**정첨백**
<p>
  
[<img src="https://img.shields.io/badge/Github-181717?style=flat-square&logo=Github&logoColor=white">](https://github.com/civilcoy)
<img src="https://img.shields.io/badge/SpringBoot-6DB33F?style=flat-square&logo=springboot&logoColor=white"/>
</p>  
  
## 🛠️ 기술 스택
  
|종류|기술|
|:----:|:----:|
|Language|<img src="https://img.shields.io/badge/Java-007396?style=flat-square&logo=java&logoColor=white"/>|
|Build|<img src="https://img.shields.io/badge/Gralde-02303A?style=flat-square&logo=Gradle&logoColor=white"/>|
|FrameWork|<img src="https://img.shields.io/badge/SpringBoot-6DB33F?style=flat-square&logo=springboot&logoColor=white"/>|
|DB|<img src="https://img.shields.io/badge/MySQL-4479A1?style=flat-square&logo=MySQL&logoColor=white"/><br><img src="https://img.shields.io/badge/Amazon RDS-527FFF?style=flat-square&logo=Amazon RDS&logoColor=white"/>|
|Server|<img src="https://img.shields.io/badge/Amazon EC2-FF9900?style=flat-square&logo=Amazon EC2&logoColor=white"/>  

## 📌 API 명세서
<img width="800" alt="api1" src="https://blog.kakaocdn.net/dn/7tj8U/btrUh2KTyIc/v0XwD73QkEp9BNIAuZaEOK/img.png">  
<img width="800" alt="api2" src="https://blog.kakaocdn.net/dn/r7Qoa/btrUl5UdiI4/Yadm2FZ1N3KmpmQkPyH7kK/img.png">  
<img width="800" alt="api3" src="https://blog.kakaocdn.net/dn/NHU0R/btrUh3bZCqZ/KJ8SzuNFb7sKNlqHbgt7F1/img.png">  

## 🗺 ERD
<img width="800" alt="메인페이지2" src="https://user-images.githubusercontent.com/65327103/209082821-ecdf919c-601a-4c36-b2d3-5d205b0414f6.png">  

## 🧩아키텍쳐
<img width="800" alt="메인페이지2" src="https://blog.kakaocdn.net/dn/bOCbtg/btrUneCW2D2/BZyYOomTIzYOHSm4bmkEK1/img.png">  

## 💡 Trouble Shooting
<details>
<summary>1. 거짓된 병원 정보를 입력할 수 있었던 문제</summary>
<br>
<div markdown="1">
<b>오픈 api를 사용하여 전국의 병‧의원 중 진료과목에 성형외과가 있는 기관명을 받아와 조회하는 방식으로 해결</b>   
	
* Scheduler
	
```java
	@Component
	@EnableScheduling
	@RequiredArgsConstructor
	public class HospitalScheduler {

		private final HospitalService hospitalService;

		@Transactional
		@Scheduled(cron = "0 0 1 * * *")
		public void hospitalDataScheduling() {
			hospitalService.saveHospitalApiData();
		}
	}
```  
	
* Service
	
```java
	@Service
	@Slf4j
	@RequiredArgsConstructor
	@ToString
	public class HospitalService {

	    private final HospitalRepository hospitalRepository;

	    @Value("${hospital-url}")
	    private String hospitalUrl;

	    @Value("${hospital-key}")
	    private String hospitalKey;

	    private  Hospital getTagValue(String tag, Element eElement) {
		NodeList nlList = eElement.getElementsByTagName(tag).item(0).getChildNodes();
		Node nValue = nlList.item(0);
		if (nValue == null)
		    return null;
		return new Hospital(nValue.getNodeValue());
	    }

	    public void saveHospitalApiData(){
		try{
		    StringBuilder urlBuilder = new StringBuilder(hospitalUrl); /*URL*/
		    urlBuilder.append("?" + URLEncoder.encode("serviceKey", "UTF-8") + "="+hospitalKey); /*Service Key*/
		    urlBuilder.append("&" + URLEncoder.encode("QD", "UTF-8") + "=" + URLEncoder.encode("D010", "UTF-8")); /*CODE_MST의'D000' 참조(D001~D029)*/
		    urlBuilder.append("&" + URLEncoder.encode("numOfRows","UTF-8") + "=" + URLEncoder.encode("4728", "UTF-8")); /*목록 건수*/
		    String url = urlBuilder.toString();

		    Document documentInfo = DocumentBuilderFactory
			    .newInstance()
			    .newDocumentBuilder()
			    .parse(url);

		    documentInfo.getDocumentElement().normalize();

		    NodeList nodeList = documentInfo.getElementsByTagName("item");

		    for (int temp = 0; temp < nodeList.getLength(); temp++) {
			Node node = nodeList.item(temp);
			if (node.getNodeType() == Node.ELEMENT_NODE) {
			    Element element = (Element) node;

			    Hospital hospitalData = getTagValue("dutyName", element);
			    log.info(":::" + hospitalData + ":::");
			    hospitalRepository.save(hospitalData);
			}
		    }
		} catch(Exception e) {
		    e.printStackTrace();
		    log.error("hospital data not saved");
		    throw new CustomException(ErrorCode.FAILED_SAVE_DATA);
		}
	    }
	}
```
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
