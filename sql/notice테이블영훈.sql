---------
--��������
---------
drop TABLE notice;
drop sequence notice_id_seq;
-- ���̺� ����
create table notice(
id    number(8),
title       varchar2(100),
content     clob,
author      varchar2(12), -- �ۼ���
hit         number(5) default 0, -- ��ȸ��
cdate       timestamp default systimestamp,
udate       timestamp default systimestamp
);
--�⺻Ű����
alter table notice add Constraint notice_id_pk primary key (id);

--�������� not null
alter table notice modify title constraint notice_title_nn not null;
alter table notice modify content constraint notice_content_nn not null;
--alter table notice modify author constraint notice_author_nn not null;

--������ ����
create sequence notice_id_seq
start with 1
increment by 1
minvalue 0
maxvalue 99999999
nocycle;

commit;

select * from notice;

select id, title, content
from notice
where id = 1;
