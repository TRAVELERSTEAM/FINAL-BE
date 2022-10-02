insert into member (member_id, created_at, modified_at, area, authority, birth, email, gender, group_trip, password,
                    recommend, recommend_code, tel, theme, username)
values (1, now(), now(), '서울', 'ROLE_ADMIN', now(), '123@123.com', 'MALE', 'none', '1234', 'none', 'none', '123-123',
        'none', '정성훈');

insert into notify (d_type, notify_id, created_at, modified_at, title, content, notify_type, sequence, member_id)
values ('notice', 1, now(), now(), '여행 시 규칙', '그런건 없어용~', 'NOTICE', 1, 1);
insert into notify (d_type, notify_id, created_at, modified_at, title, content, notify_type, sequence, member_id)
values ('notice', 2, now(), now(), '환불 규정', '여행 출발 일주일 전에 환불 시 75퍼 차감된 금액을 돌려드립니다.', 'NOTICE', 2, 1);
insert into notify (d_type, notify_id, created_at, modified_at, title, content, notify_type, sequence, member_id)
values ('notice', 3, now(), now(), '안전 수칙', '그런건 없어용~', 'NOTICE', 3, 1);
insert into notify (d_type, notify_id, created_at, modified_at, title, content, notify_type, sequence, member_id)
values ('notice', 4, now(), now(), '전화 상담에 대한 안내문', '그런건 없어용~', 'NOTICE', 4, 1);
insert into notify (d_type, notify_id, created_at, modified_at, title, content, notify_type, sequence, member_id)
values ('notice', 5, now(), now(), '배고프다', '그런건 없어용~', 'NOTICE', 5, 1);
insert into notify (d_type, notify_id, created_at, modified_at, title, content, notify_type, sequence, member_id)
values ('notice', 6, now(), now(), '백신 및 안내 규정', '그런건 없어용~', 'NOTICE', 6, 1);
insert into notify (d_type, notify_id, created_at, modified_at, title, content, notify_type, sequence, member_id)
values ('notice', 7, now(), now(), '입금 계좌 안내', '그런건 없어용~', 'NOTICE', 7, 1);
insert into notify (d_type, notify_id, created_at, modified_at, title, content, notify_type, sequence, member_id)
values ('notice', 8, now(), now(), '여행 예약 안내', '그런건 없어용~', 'NOTICE', 8, 1);
insert into notify (d_type, notify_id, created_at, modified_at, title, content, notify_type, sequence, member_id)
values ('notice', 9, now(), now(), '찾아오시는 길', '그런건 없어용~', 'NOTICE', 9, 1);
insert into notify (d_type, notify_id, created_at, modified_at, title, content, notify_type, sequence, member_id)
values ('notice', 10, now(), now(), '러시아 여행 출국 금지 관련 안내', '그런건 없어용~', 'NOTICE', 10, 1);
insert into notify (d_type, notify_id, created_at, modified_at, title, content, notify_type, sequence, member_id)
values ('notice', 11, now(), now(), '취업하고 싶다', '그런건 없어용~', 'NOTICE', 11, 1);
