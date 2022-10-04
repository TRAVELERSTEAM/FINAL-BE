INSERT INTO member (member_id, username)
VALUES (2, '김현준');
INSERT INTO member (member_id, username)
VALUES (3, '이민형');
INSERT INTO member (member_id, username)
VALUES (4, '정성훈');
INSERT INTO member (member_id, username)
VALUES (5, '이남대');
INSERT INTO member (member_id, username)
VALUES (6, '김윤겸');
INSERT INTO member (member_id, username)
VALUES (7, '이중권');

INSERT INTO product (product_id, target) VALUES (1, '괌');
INSERT INTO product (product_id, target) VALUES (2, '하와이');
INSERT INTO product (product_id, target) VALUES (3, '동아시아');
INSERT INTO product (product_id, target) VALUES (4, '유럽');

INSERT INTO travel_place VALUES (1, 2, 1);
INSERT INTO travel_place VALUES (2, 2, 2);
INSERT INTO travel_place VALUES (3, 2, 3);
INSERT INTO travel_place VALUES (4, 2, 4);
INSERT INTO travel_place VALUES (5, 3, 1);
INSERT INTO travel_place VALUES (6, 3, 2);
INSERT INTO travel_place VALUES (7, 3, 3);
INSERT INTO travel_place VALUES (8, 3, 4);
INSERT INTO travel_place VALUES (9, 4, 1);
INSERT INTO travel_place VALUES (10, 4, 2);
INSERT INTO travel_place VALUES (11, 4, 3);
INSERT INTO travel_place VALUES (12, 4, 4);
INSERT INTO travel_place VALUES (13, 5, 1);
INSERT INTO travel_place VALUES (14, 5, 2);
INSERT INTO travel_place VALUES (15, 5, 3);
INSERT INTO travel_place VALUES (16, 5, 4);

INSERT INTO review VALUES (1, 1, 1, '1번 후기', '김현준의 괌 여행 후기', now(), now());
INSERT INTO review VALUES (2, 1, 2, '2번 후기', '김현준의 하와이 여행 후기', now(), now());
INSERT INTO review VALUES (3, 1, 3, '3번 후기', '김현준의 동아시아 여행 후기.', now(), now());
INSERT INTO review VALUES (4, 1, 4, '4번 후기', '김현준의 유럽 여행 후기', now(), now());
INSERT INTO review VALUES (5, 2, 1, '5번 후기', '이민형의 괌 여행 후기', now(), now());
INSERT INTO review VALUES (6, 2, 2, '6번 후기', '이민형의 하와이 여행 후기', now(), now());
INSERT INTO review VALUES (7, 2, 3, '7번 후기', '이민형의 동아시아 여행 후기', now(), now());
INSERT INTO review VALUES (8, 2, 4, '8번 후기', '이민형의 유럽 여행 후기', now(), now());
INSERT INTO review VALUES (9, 4, 1,  '9번 후기', '이남대의 괌 여행 후기', now(), now());
INSERT INTO review VALUES (10, 4, 2, '10번 후기', '이남대의 하와이 여행 후기', now(), now());
INSERT INTO review VALUES (11, 4, 3, '11번 후기', '이남대의 동아시아 여행 후기', now(), now());
INSERT INTO review VALUES (12, 4, 4, '12번 후기', '이남대의 유럽 여행 후기', now(), now());
INSERT INTO review VALUES (13, 3, 1, '13번 후기', '정성훈의 괌 여행 후기', now(), now());
INSERT INTO review VALUES (14, 3, 2, '14번 후기', '정성훈의 하와이 여행 후기', now(), now());
INSERT INTO review VALUES (15, 3, 3, '15번 후기', '정성훈의 동아시아 여행 후기', now(), now());
INSERT INTO review VALUES (16, 3, 4, '16번 후기', '정성훈의 유럽 여행 후기', now(), now());
INSERT INTO review VALUES (17, 7, 1, '17번 후기', '김윤겸의 괌 여행 후기', now(), now());
INSERT INTO review VALUES (18, 7, 2, '18번 후기', '김윤겸의 하와이 여행 후기', now(), now());
INSERT INTO review VALUES (19, 7, 3, '19번 후기', '김윤겸의 동아시아 여행 후기', now(), now());
INSERT INTO review VALUES (20, 7, 4, '20번 후기', '김윤겸의 유럽 여행 후기', now(), now());
INSERT INTO review VALUES (21, 8, 1, '21번 후기', '이중권의 괌 여행 후기', now(), now());
INSERT INTO review VALUES (22, 8, 2, '22번 후기', '이중권의 하와이 여행 후기', now(), now());
INSERT INTO review VALUES (23, 8, 3, '23번 후기', '이중권의 동아시아 여행 후기', now(), now());
INSERT INTO review VALUES (24, 8, 4, '24번 후기', '이중권의 유럽 여행 후기', now(), now());



insert into notify values('notice', 1, now(), now(), 1, 1, 'NOTICE', 1, 1);
insert into notify values('notice', 2, now(), now(), 2, 2, 'NOTICE', 2, 1);
insert into notify values('notice', 3, now(), now(), 3, 3, 'NOTICE', 3, 1);
insert into notify values('notice', 4, now(), now(), 4, 4, 'NOTICE', 4, 1);
insert into notify values('notice', 5, now(), now(), 5, 5, 'NOTICE', 5, 1);
insert into notify values('notice', 6, now(), now(), 6, 6, 'NOTICE', 6, 1);
insert into notify values('notice', 7, now(), now(), 7, 7, 'NOTICE', 7, 1);
insert into notify values('notice', 8, now(), now(), 8, 8, 'NOTICE', 8, 1);
insert into notify values('notice', 9, now(), now(), 9, 9, 'NOTICE', 9, 1);
insert into notify values('notice', 10, now(), now(), 10, 10, 'NOTICE', 10, 1);
insert into notify values('notice', 11, now(), now(), 11, 11, 'NOTICE', 11, 1);
insert into notify values('notice', 12, now(), now(), 12, 12, 'NOTICE', 12, 1);

insert into product (product_id) values(1);
insert into product (product_id) values(2);
insert into product (product_id) values(3);
insert into product (product_id) values(4);
insert into product (product_id) values(5);

insert into travel_place values(1, 1, 1);
insert into travel_place values(2, 1, 2);
insert into travel_place values(3, 1, 3);
insert into travel_place values(4, 1, 4);
insert into travel_place values(5, 1, 5);