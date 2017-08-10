select * from users

select * from user_tables

create table login_log( loginname varchar2(30), logintime date);

CREATE SEQUENCE loginlog_sequence  --������
INCREMENT BY 1   -- ÿ�μӼ���  
START WITH 1       -- ��1��ʼ����  
NOMAXVALUE        -- ���������ֵ  
NOCYCLE               -- һֱ�ۼӣ���ѭ��  
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


--Ϊ���������������к�
CREATE SEQUENCE loginlog_sequence  --������
INCREMENT BY 1   -- ÿ�μӼ���  
START WITH 1       -- ��1��ʼ����  
NOMAXVALUE        -- ���������ֵ  
NOCYCLE               -- һֱ�ۼӣ���ѭ��  
CACHE 10;


create or replace trigger tri_user_login  --������

  before insert on login_log    --before��ʾ�����ݿ⶯��֮ǰ������ִ��;

  for each row

begin

  select loginlog_sequence.nextval into :new.id from dual;

end;

insert into login_log(loginname,logintime) values(user,sysdate);


--����login_log ��ĸ���
create table login_log_co as select * from login_log;

--���ô��������б�ͱ��ݱ�֮���ͬ�����ơ�

create or replace trigger user_login_copy
 after update or insert or delete on login_log     --after����ʾ�����ݿ⶯��֮�󴥷���ִ��
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

--����
insert into login_log(loginname,logintime) values(user,sysdate);

delete  login_log  where id = '13'

update  login_log set loginname = 'zhangsan' where id = '10'

select * from login_log_co

select * from login_log


--ɾ��һ����¼����¼�洢����һ������
create table login_log_save as select * from login_log;

create or replace trigger user_delete_save
 after delete on login_log    --after����ʾ�����ݿ⶯��֮�󴥷���ִ��
 for each row                 --for each row���Ա��ÿһ�д�����ִ��һ�Ρ����û����һѡ���ֻ��������ִ��һ�Ρ�
begin
     insert into  login_log_save values (:old.id,:old.loginname,:old.logintime); 
end;

--����
delete  login_log  where id = '10'

delete login_log_save

drop  table login_log_save

select * from login_log_save



--��ѯ��ǰ�û��Ĵ�����
SELECT NAME FROM USER_SOURCE WHERE TYPE='TRIGGER' GROUP BY NAME 
--���ô�����
alter trigger trigger_name disable;
--�������
alter trigger USER_LOGIN_CO enable;
--����ĳ�����ϵĴ�����
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
   
  IF(to_char(sysdate,'DY')='������') THEN
    RAISE_APPLICATION_ERROR(-20600,'��������ĩ�޸ı�tb_emp'); --��һ������ȡֵ��Χ������20000 - 20999 ֮��
  END IF;
END;

select to_char(sysdate,'DY') from dual;

--����������
CREATE OR REPLACE TRIGGER EMP_INFO
 AFTER INSERT OR UPDATE OR DELETE ON scott.EMP
 
 
DECLARE
 CURSOR CUR_EMP IS --�����α�
  SELECT DEPTNO, COUNT(EMPNO) AS TOTAL_EMP, SUM(SAL) AS TOTAL_SAL FROM scott.EMP GROUP BY DEPTNO;
  
  
BEGIN
 DELETE DEPT_SAL; --����ʱ����ɾ��ӳ�����Ϣ
 FOR V_EMP IN CUR_EMP LOOP
  --DBMS_OUTPUT.PUT_LINE(v_emp.deptno || v_emp.total_emp || v_emp.total_sal);
  --��������
  INSERT INTO DEPT_SAL
  VALUES
   (V_EMP.DEPTNO, V_EMP.TOTAL_EMP, V_EMP.TOTAL_SAL);
 END LOOP;
END;


