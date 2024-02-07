set search_path to post;

drop extension pg_trgm;
create extension pg_trgm with schema pg_catalog; -- \dx

select count(*) from post_index;

delete from post_index where 1=1;

select * from post_index where idx like '10%' order by idx;



select pid as process_id,
       usename as username,
       datname as database_name,
       client_addr as client_address,
       application_name,
       backend_start,
       state,
       state_change
from pg_stat_activity;


