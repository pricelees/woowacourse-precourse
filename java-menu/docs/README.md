# 우아한테크코스 프리코스 최종 코딩테스트

# 미션 - 점심 메뉴 추천 

---

## 기능 요구 사항

- [x] 코치들은 월요일부터 금요일까지 점심 식사를 같이 한다.
- [x] 다음의 과정으로 메뉴를 추천한다.
  - 월요일에 추천할 '카테고리'를 무작위로 정한다.
  - 각 코치가 월요일에 먹을 메뉴를 추천한다.
  - 화,수,목,금요일에 대해 이 과정을 반복한다.
  - **즉, 한 코치의 메뉴를 한번에 추천하는 것이 아닌, 매일 모든 코치의 메뉴를 추천**

- [x] 코치의 이름은 "최소 2글자 ~ 최대 4글자"이다.
- [x] 코치는 최소 2명, 최대 5명까지 함께 식사한다.
  - 코치 이름의 입력은 쉼표를 기준으로 입력받는다.
  
- [x] 코치가 못 먹는 메뉴를 입력받는다.
  - 최소 0개 , 최대 2개의 메뉴를 선택 가능하다.
  - 쉼표로 구분하여 입력한다.

- [x] 한 주에 같은 카테고리는 최대 2회까지만 고를 수 있다.
- [x] 각 코치에게 한 주에 중복되지 않는 메뉴를 추천해야한다.!!!
  - 서로 다른 두 코치의 메뉴가 동일한 것은 허용된다!!