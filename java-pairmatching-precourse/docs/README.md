# 우아한테크코스 프리코스

# 미션 - 페어매칭관리 애플리케이션

---

## 기능 요구 사항

### 도메인
- [x] 백엔드 과정과 프론트엔드 과정이 있음.
- [x] 각 과정은 5단계로 나누어 진행이 되며, 이 단계를 '레벨' 이라고 한다.
- [x] 미션을 수행하며 각 레벨에서 전달하고자 하는 내용을 학습하는데, 이 과정을 페어 프로그래밍으로 진행한다.
- [x] 미션을 시작하기 전 페어를 매칭한다.

### 페어 매칭 기능
- [x] 조건

        미션을 함께 수행할 페어를 "두 명"씩 매칭한다.
        페어 매칭 대상이 홀수인 경우, 한 페어는 3인으로 구성한다.
        같은 레벨에서 이미 페어를 맺은 크루와는 다시 페어로 매칭될 수 없다.

- [x] 구현 방법
        
        1. 크루들의 이름 목록을 List<String>으로 저장한다.
        2. 크루 목록의 순서를 랜덤으로 섞는다.
            -> Randoms.shuffle 메서드 이용
        3. 페어 매칭 대상이 홀수인 경우, 마지막 남은 크루는 마지막 페어에 포함시킨다.
        4. 같은 레벨에서 이미 페어로 만난적이 있는 크루끼리 매칭된다면, 다시 처음부터 진행한다.
        5. 3회 재시도까지 매칭이 되지 않거나, 매칭을 할 수 있는 경우의 수가 없으면 에러 메시지를 출력한다.

- [x] 페어 재매칭 시도

        1. 안내 문구를 출력 후 매칭을 진행한다.
        2. 아니오를 선택할 경우, 코스, 레벨, 미션을 다시 선택한다.

- [x] 페어 조회 기능


        1. 과정, 레벨, 미션을 선택하면 해당 미션의 페어 정보를 출력한다.
        2. 매칭 이력이 없으면, "[ERROR] 매칭 이력이 없습니다."라고 안내한다.