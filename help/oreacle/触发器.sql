select * from users

select * from user_tables

create table login_log( loginname varchar2(30), logintime date);

CREATE SEQUENCE loginlog_sequence  --序列名
INCREMENT BY 1   -- 每次加几个  
START WITH 1       -- 从1开始计数  
NOMAXVALUE        -- 不设置最大值  
NOCYCLE               -- 一直累加，不循环  
CACHE 10;



Create or replace trigger biud_user_login

Before insert or update or delete

on users

Begin

  Insert into login_log(id,loginname,logintime)   Values(loginlog_sequence.nextval,user, sysdate);

  End;
  
  
  
insert into users (id,name) values (68,'lisi')

update  users set name='lisi1' 

delete users

select * from login_log;


--为主健生成自增序列号
CREATE SEQUENCE loginlog_sequence  --序列名
INCREMENT BY 1   -- 每次加几个  
START WITH 1       -- 从1开始计数  
NOMAXVALUE        -- 不设置最大值  
NOCYCLE               -- 一直累加，不循环  
CACHE 10;


create or replace trigger tri_user_login  --触发器

  before insert on login_log    --before表示在数据库动作之前触发器执行;

  for each row

begin

  select loginlog_sequence.nextval into :new.id from dual;

end;

insert into login_log(loginname,logintime) values(user,sysdate);


--创建login_log 表的复本
create table login_log_co as select * from login_log;

--利用触发器进行表和备份表之间的同步复制。

create or replace trigger user_login_copy
 after update or insert or delete on login_log     --after：表示在数据库动作之后触发器执行
 for each row
begin
   if inserting then
     insert into  login_log_co values (:new.id,:new.loginname,:new.logintime);
   elsif deleting then
     delete from login_log_co where id=:old.id;
   else
     update login_log_co set id=:new.id,loginname=:new.loginname,logintime=:new.logintime where id=:new.id;
   end if;
end;

--测试
insert into login_log(loginname,logintime) values(user,sysdate);

delete  login_log  where id = '13'

update  login_log set loginname = 'zhangsan' where id = '10'

select * from login_log_co

select * from login_log


--删除一条记录将记录存储到另一个表中
create table login_log_save as select * from login_log;

create or replace trigger user_delete_save
 after delete on login_log    --after：表示在数据库动作之后触发器执行
 for each row                 --for each row：对表的每一行触发器执行一次。如果没有这一选项，则只对整个表执行一次。
begin
     insert into  login_log_save values (:old.id,:old.loginname,:old.logintime); 
end;

--测试
delete  login_log  where id = '10'

delete login_log_save

drop  table login_log_save

select * from login_log_save



--查询当前用户的触发器
SELECT NAME FROM USER_SOURCE WHERE TYPE='TRIGGER' GROUP BY NAME 
--禁用触发器
alter trigger trigger_name disable;
--激活触发器
alter trigger USER_LOGIN_CO enable;
--禁用某个表上的触发器
alert table login_log diable all triggers;

select 'drop trigger ' || trigger_name || ';'  from user_triggers; 

select 'drop table '||table_name||' cascade constraints;' from user_tables;  
select 'drop trigger ' || trigger_name || ';'  from user_triggers;  
select 'drop sequence ' || sequence_name || ';'  from user_sequences; 


	drop trigger USER_LOGIN_CO;
	drop trigger DUPLICATE_LOGIN_COPY;
	drop trigger DUPLICATE_LOGIN;
	drop trigger USER_LOGIN_COPY;
	drop trigger TRI_USER_LOGIN;


--
create or replace trigger auth_secure 
before insert or update or delete
on tb_emp
begin
  --if user <>'SCOTT' then
  -- raise_application_error(-20001,'You don''t have access to modify this table.');
  --end if;
   
  IF(to_char(sysdate,'DY')='星期日') THEN
    RAISE_APPLICATION_ERROR(-20600,'不能在周末修改表tb_emp'); --第一个参数取值范围必须在20000 - 20999 之间
  END IF;
END;

select to_char(sysdate,'DY') from dual;

--创建触发器
CREATE OR REPLACE TRIGGER EMP_INFO
 AFTER INSERT OR UPDATE OR DELETE ON scott.EMP
 
 
DECLARE
 CURSOR CUR_EMP IS --定义游标
  SELECT DEPTNO, COUNT(EMPNO) AS TOTAL_EMP, SUM(SAL) AS TOTAL_SAL FROM scott.EMP GROUP BY DEPTNO;
  
  
BEGIN
 DELETE DEPT_SAL; --触发时首先删除映射表信息
 FOR V_EMP IN CUR_EMP LOOP
  --DBMS_OUTPUT.PUT_LINE(v_emp.deptno || v_emp.total_emp || v_emp.total_sal);
  --插入数据
  INSERT INTO DEPT_SAL
  VALUES
   (V_EMP.DEPTNO, V_EMP.TOTAL_EMP, V_EMP.TOTAL_SAL);
 END LOOP;
END;


