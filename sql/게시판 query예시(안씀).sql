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
 from (select rownum no, t1.* from bbs t1)t2     --게시판 테이블(행) 순번이 포함된 테이블
where title like '%6%'        -- 게시글 제목에 6이 포함된 글
    and no between 11 and 20; -- 게시판 페이지에서 보여질 글 갯수

--여기서 bbs_id 순번 x

