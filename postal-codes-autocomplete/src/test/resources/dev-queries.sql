set search_path to public,post;

select count(*) from post_index;

delete from post_index where 1=1;

select * from post_index where idx like '10%' order by idx;



