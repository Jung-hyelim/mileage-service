-- 리뷰1 : user_1 의 place_id_uuid_1 리뷰작성, 내용 + 사진 + 첫리뷰
insert into first_event_check (event_type, event_key) values ('REVIEW', 'place_id_uuid_1');
insert into mileage (user_id, event_type, event_key, is_deleted) values ('user_1', 'REVIEW', 'place_id_uuid_1', false);
insert into mileage_detail (mileage_id, point_type, point) values (1, 'CONTENT', 1);
insert into mileage_detail (mileage_id, point_type, point) values (1, 'PHOTO', 1);
insert into mileage_detail (mileage_id, point_type, point) values (1, 'FIRST_PLACE', 1);
insert into mileage_history (mileage_id, action, changed_point) values (1, 'ADD', 3);

-- 리뷰2 : user_2 의 place_id_uuid_1 리뷰작성, 내용 + 사진
insert into mileage (user_id, event_type, event_key, is_deleted) values ('user_2', 'REVIEW', 'place_id_uuid_1', false);
insert into mileage_detail (mileage_id, point_type, point) values (2, 'CONTENT', 1);
insert into mileage_detail (mileage_id, point_type, point) values (2, 'PHOTO', 1);
insert into mileage_history (mileage_id, action, changed_point) values (2, 'ADD', 2);

-- 리뷰3 : user_3 의 place_id_uuid_1 리뷰작성, 내용
insert into mileage (user_id, event_type, event_key, is_deleted) values ('user_3', 'REVIEW', 'place_id_uuid_1', false);
insert into mileage_detail (mileage_id, point_type, point) values (3, 'CONTENT', 1);
insert into mileage_history (mileage_id, action, changed_point) values (3, 'ADD', 1);

-- 리뷰4 : user_1 의 place_id_uuid_2 리뷰작성, 내용 + 첫리뷰
insert into first_event_check (event_type, event_key) values ('REVIEW', 'place_id_uuid_2');
insert into mileage (user_id, event_type, event_key, is_deleted) values ('user_1', 'REVIEW', 'place_id_uuid_2', false);
insert into mileage_detail (mileage_id, point_type, point) values (4, 'CONTENT', 1);
insert into mileage_detail (mileage_id, point_type, point) values (4, 'FIRST_PLACE', 1);
insert into mileage_history (mileage_id, action, changed_point) values (4, 'ADD', 2);

-- 리뷰5 : user_2 의 place_id_uuid_2 리뷰작성, 사진
insert into mileage (user_id, event_type, event_key, is_deleted) values ('user_2', 'REVIEW', 'place_id_uuid_2', false);
insert into mileage_detail (mileage_id, point_type, point) values (5, 'PHOTO', 1);
insert into mileage_history (mileage_id, action, changed_point) values (5, 'ADD', 1);