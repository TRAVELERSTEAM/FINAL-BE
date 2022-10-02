INSERT INTO member (member_id, username)
VALUES (1, '김현준');
INSERT INTO member (member_id, username)
VALUES (2, '이민형');
INSERT INTO member (member_id, username)
VALUES (3, '정성훈');
INSERT INTO member (member_id, username)
VALUES (4, '이남대');
INSERT INTO member (member_id, username)
VALUES (7, '김윤겸');
INSERT INTO member (member_id, username)
VALUES (8, '이중권');
INSERT INTO member (member_id, username, authority)
VALUES (5, '관리자', 'ROLE_ADMIN');
insert into member (member_id, created_at, modified_at, area, authority, birth, email, gender, group_trip, password,
                    recommend, recommend_code, tel, theme, username)
values (6, now(), now(), '서울', 'ROLE_ADMIN', now(), '123@123.com', 'MALE', 'none', '1234', 'none', 'none', '123-123',
        'none', '어드민');

INSERT INTO product (product_id, target) VALUES (1, '괌');
INSERT INTO product (product_id, target) VALUES (2, '하와이');
INSERT INTO product (product_id, target) VALUES (3, '동아시아');
INSERT INTO product (product_id, target) VALUES (4, '유럽');

INSERT INTO travel_place VALUES (1, 1, 1);
INSERT INTO travel_place VALUES (2, 1, 2);
INSERT INTO travel_place VALUES (3, 1, 3);
INSERT INTO travel_place VALUES (4, 1, 4);
INSERT INTO travel_place VALUES (5, 2, 1);
INSERT INTO travel_place VALUES (6, 2, 2);
INSERT INTO travel_place VALUES (7, 2, 3);
INSERT INTO travel_place VALUES (8, 2, 4);
INSERT INTO travel_place VALUES (9, 3, 1);
INSERT INTO travel_place VALUES (10, 3, 2);
INSERT INTO travel_place VALUES (11, 3, 3);
INSERT INTO travel_place VALUES (12, 3, 4);
INSERT INTO travel_place VALUES (13, 4, 1);
INSERT INTO travel_place VALUES (14, 4, 2);
INSERT INTO travel_place VALUES (15, 4, 3);
INSERT INTO travel_place VALUES (16, 4, 4);
INSERT INTO travel_place VALUES (17, 7, 1);
INSERT INTO travel_place VALUES (18, 7, 2);
INSERT INTO travel_place VALUES (19, 7, 3);
INSERT INTO travel_place VALUES (20, 7, 4);
INSERT INTO travel_place VALUES (21, 8, 1);
INSERT INTO travel_place VALUES (22, 8, 2);
INSERT INTO travel_place VALUES (23, 8, 3);
INSERT INTO travel_place VALUES (24, 8, 4);

INSERT INTO review VALUES (1, 1, 1, 'x', '김현준의 괌 여행 후기', now(), now());
INSERT INTO review VALUES (2, 1, 2, 'x', '김현준의 하와이 여행 후기', now(), now());
INSERT INTO review VALUES (3, 1, 3, 'x', '김현준의 동아시아 여행 후기.', now(), now());
INSERT INTO review VALUES (4, 1, 4, 'x', '김현준의 유럽 여행 후기', now(), now());
INSERT INTO review VALUES (5, 2, 1, 'x', '이민형의 괌 여행 후기', now(), now());
INSERT INTO review VALUES (6, 2, 2, 'x', '이민형의 하와이 여행 후기', now(), now());
INSERT INTO review VALUES (7, 2, 3, 'x', '이민형의 동아시아 여행 후기', now(), now());
INSERT INTO review VALUES (8, 2, 4, 'x', '이민형의 유럽 여행 후기', now(), now());
INSERT INTO review VALUES (9, 4, 1,  'x', '이남대의 괌 여행 후기', now(), now());
INSERT INTO review VALUES (10, 4, 2, 'x', '이남대의 하와이 여행 후기', now(), now());
INSERT INTO review VALUES (11, 4, 3, 'x', '이남대의 동아시아 여행 후기', now(), now());
INSERT INTO review VALUES (12, 4, 4, 'x', '이남대의 유럽 여행 후기', now(), now());
INSERT INTO review VALUES (13, 3, 1, 'x', '정성훈의 괌 여행 후기', now(), now());
INSERT INTO review VALUES (14, 3, 2, 'x', '정성훈의 하와이 여행 후기', now(), now());
INSERT INTO review VALUES (15, 3, 3, 'x', '정성훈의 동아시아 여행 후기', now(), now());
INSERT INTO review VALUES (16, 3, 4, 'x', '정성훈의 유럽 여행 후기', now(), now());
INSERT INTO review VALUES (17, 7, 1, 'x', '김윤겸의 괌 여행 후기', now(), now());
INSERT INTO review VALUES (18, 7, 2, 'x', '김윤겸의 하와이 여행 후기', now(), now());
INSERT INTO review VALUES (19, 7, 3, 'x', '김윤겸의 동아시아 여행 후기', now(), now());
INSERT INTO review VALUES (20, 7, 4, 'x', '김윤겸의 유럽 여행 후기', now(), now());
INSERT INTO review VALUES (21, 8, 1, 'x', '이중권의 괌 여행 후기', now(), now());
INSERT INTO review VALUES (22, 8, 2, 'x', '이중권의 하와이 여행 후기', now(), now());
INSERT INTO review VALUES (23, 8, 3, 'x', '이중권의 동아시아 여행 후기', now(), now());
INSERT INTO review VALUES (24, 8, 4, 'x', '이중권의 유럽 여행 후기', now(), now());



insert into notify (d_type, notify_id, created_at, modified_at, title, content, notify_type, sequence, member_id)
values ('notice', 1, now(), now(), '여행 시 규칙', '그런건 없어용~', 'NOTICE', 1, 6);
insert into notify (d_type, notify_id, created_at, modified_at, title, content, notify_type, sequence, member_id)
values ('notice', 2, now(), now(), '환불 규정', '여행 출발 일주일 전에 환불 시 75퍼 차감된 금액을 돌려드립니다.', 'NOTICE', 2, 6);
insert into notify (d_type, notify_id, created_at, modified_at, title, content, notify_type, sequence, member_id)
values ('notice', 3, now(), now(), '안전 수칙', '그런건 없어용~', 'NOTICE', 3, 6);
insert into notify (d_type, notify_id, created_at, modified_at, title, content, notify_type, sequence, member_id)
values ('notice', 4, now(), now(), '전화 상담에 대한 안내문', '그런건 없어용~', 'NOTICE', 4, 6);
insert into notify (d_type, notify_id, created_at, modified_at, title, content, notify_type, sequence, member_id)
values ('notice', 5, now(), now(), '배고프다', '그런건 없어용~', 'NOTICE', 5, 6);
insert into notify (d_type, notify_id, created_at, modified_at, title, content, notify_type, sequence, member_id)
values ('notice', 6, now(), now(), '백신 및 안내 규정', '그런건 없어용~', 'NOTICE', 6, 6);
insert into notify (d_type, notify_id, created_at, modified_at, title, content, notify_type, sequence, member_id)
values ('notice', 7, now(), now(), '입금 계좌 안내', '그런건 없어용~', 'NOTICE', 7, 6);
insert into notify (d_type, notify_id, created_at, modified_at, title, content, notify_type, sequence, member_id)
values ('notice', 8, now(), now(), '여행 예약 안내', '그런건 없어용~', 'NOTICE', 8, 6);
insert into notify (d_type, notify_id, created_at, modified_at, title, content, notify_type, sequence, member_id)
values ('notice', 9, now(), now(), '찾아오시는 길', '그런건 없어용~', 'NOTICE', 9, 6);
insert into notify (d_type, notify_id, created_at, modified_at, title, content, notify_type, sequence, member_id)
values ('notice', 10, now(), now(), '러시아 여행 출국 금지 관련 안내', '그런건 없어용~', 'NOTICE', 10, 6);
insert into notify (d_type, notify_id, created_at, modified_at, title, content, notify_type, sequence, member_id)
values ('notice', 11, now(), now(), '취업하고 싶다', '그런건 없어용~', 'NOTICE', 11, 6);
