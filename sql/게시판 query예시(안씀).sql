select * from bbs;
delete from bbs;
commit;

select * from bbs where title like '%6%' or bcontent like '%2%';

select *
    from bbs where bbs_id = 533;

select *
    from bbs where title like '%233%';

select *
 from (select rownum as "no", t1.* from bbs t1)
 where rownum = 1;

select t2.*
 from (select row_number() over(order by bbs_id) no, t1.* from bbs t1)t2
where no between 11 and 20;

select t2.*
 from (select rownum no, t1.* from bbs t1)t2
where no between 21 and 30;

select t2.*
 from (select rownum no, t1.* from bbs t1)t2     --�Խ��� ���̺�(��) ������ ���Ե� ���̺�
where title like '%6%'        -- �Խñ� ���� 6�� ���Ե� ��
    and no between 11 and 20; -- �Խ��� ���������� ������ �� ����

--���⼭ bbs_id ���� x

