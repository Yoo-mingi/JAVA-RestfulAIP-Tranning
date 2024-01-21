insert into users(id,join_date,name,password,ssn) values (90001,now(),'user1','test111','111111-111111');
insert into users(id,join_date,name,password,ssn) values (90002,now(),'user2','test222','22222-1221');
insert into users(id,join_date,name,password,ssn) values (90003,now(),'user3','test333','113331-113331');

insert into post(description, user_id) values ( 'my first post', 90001 );
insert into post(description, user_id) values ( 'my second post', 90002 );