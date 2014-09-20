
    create table acct_group (
		id bigint not null auto_increment,
        name varchar(255) not null unique,
        primary key (id)
    )ENGINE=InnoDB;

    create table acct_group_permission (
        group_id bigint not null,
        permission varchar(255) not null,
        foreign key(group_id) references acct_group(id)
    )ENGINE=InnoDB;

    create table acct_user (
         id bigint not null auto_increment,
        email varchar(255),
        login_name varchar(255) not null unique,
        name varchar(255),
        password varchar(255),
        primary key (id)
    )ENGINE=InnoDB;

    create table acct_user_group (
        user_id bigint not null,
        group_id bigint not null,
     	primary key(user_id,group_id),
        foreign key(user_id) references acct_user(id),
        foreign key(group_id) references acct_group(id)
    )ENGINE=InnoDB;

    create table task_message(
         id bigint not null auto_increment,
        user_id bigint not null,
        event_type  varchar(255),
        trigger_time varchar(255),
        task_priority bigint,
        task_status varchar(255),
        input_paras varchar(255),
        primary key (id)
        
    )ENGINE=InnoDB;
    
    ALTER TABLE `dataapp`.`task_message` 
ADD COLUMN `run_id` BIGINT(20) NOT NULL DEFAULT 0 AFTER `input_paras`,
ADD COLUMN `start_time` DATETIME NULL DEFAULT NULL AFTER `run_id`,
ADD COLUMN `end_time` DATETIME NULL DEFAULT NULL AFTER `start_time`,
ADD COLUMN `execution_time` BIGINT(20) NULL DEFAULT 0 AFTER `end_time`;

     create table task_log(
         id bigint not null auto_increment,
        task_id bigint not null,
        log_type  varchar(255),
        log_time  date,
        log_desc varchar(255),
        foreign key(task_id) references task_message(id),
        primary key (id)
        
    )ENGINE=InnoDB;

    ALTER TABLE `dataapp`.`task_log` 
ADD COLUMN `run_id` BIGINT(20) NOT NULL DEFAULT 0 AFTER `log_desc`;

    ALTER TABLE `dataapp`.`task_log` 
CHANGE COLUMN `log_time` `log_time` DATETIME NULL DEFAULT NULL ;

    create table DM_COMPANY(
    COMPANY_GUID varchar(255),
    CMY_NAME varchar(255),
    CMY_DES  varchar(255),
    CMY_STATUS varchar(255),
    EXPIRATION_DATE  date,
    CREATED_BY varchar(255),
    CHANGED_ON date,
    CHANGED_BY varchar(255),
    COMMENTS varchar(255),
    primary key (COMPANY_GUID)
    
    )ENGINE=InnoDB;
    
    create table DM_EVENT(
     EVENT_ID varchar(255),
     EVENT_NAME varchar(255),
     EVENT_DES varchar(255),
     COMMENTS varchar(255),
     primary key (EVENT_ID)
    )ENGINE=InnoDB;
    
   create table  DM_COMPANY_EVENT(
    COMPANY_GUID varchar(255) not null,
    EVENT_ID varchar(255) not null,
    EVENT_STATUS varchar(255),
    COMMENTS varchar(255),
    primary key(COMPANY_GUID,EVENT_ID),
    foreign key(COMPANY_GUID) references DM_COMPANY(COMPANY_GUID),
    foreign key(EVENT_ID) references DM_EVENT(EVENT_ID)
    )ENGINE=InnoDB;
    
      create table DM_AGENT(
	     AGENT_GUID varchar(255),
	     COMPANY_GUID varchar(255),
	     AGENT_DES varchar(255),
	     AGENT_STATUS varchar(255),
	     LAST_CONN_DATE date,
	     COMMENTS varchar(255),
	     primary key (AGENT_GUID)
    )ENGINE=InnoDB;
  --added by shangyt at 12:17 2014/07/03  
    ALTER TABLE `dataapp`.`dm_agent` 
ADD COLUMN `Server_url` VARCHAR(255) NULL AFTER `COMPANY_GUID`;

    create table DM_SYSTEM(
	    SYSTEM_GUID varchar(255),
	    COMPANY_GUID varchar(255),
	    AGENT_GUID varchar(255),
	    SYSTEM_DES varchar(255),
	    COMMENTS varchar(255),
	    primary key (SYSTEM_GUID)
    )ENGINE=InnoDB;
    
    create table DM_ACCOUNT_SYS_SAPUSER(
    	USER_GUID varchar(255),
	    COMPANY_GUID varchar(255),
	    ACCOUNT_ID bigint,
	    SYSTEM_GUID varchar(255),
	    SAP_USER_ID varchar(255),
	    LANGUAGE varchar(255),
	    COMMENTS varchar(255),
	    primary key (USER_GUID),
	    foreign key(ACCOUNT_ID) references acct_user(id)
    )ENGINE=InnoDB;
    
    
     create table RAY_BAPI_FIELD (
        TABNAME varchar(30) not null,
        FIELDNAME varchar(30) not null,
        AS4LOCAL varchar(1) not null,
        AS4VERS integer not null,
        POSITION integer not null,
        KEYFLAG varchar(1),
        MANDATORY varchar(1),
        ROLLNAME varchar(30),
        CHECKTABLE varchar(30),
        ADMINFIELD varchar(1),
        INTTYPE varchar(1),
        INTLEN integer,
        REFTABLE varchar(30),
        PRECFIELD varchar(30),
        REFFIELD varchar(30),
        CONROUT varchar(10),
        NOTNULL varchar(1),
        DATATYPE varchar(4),
        LENG integer,
        DECIMALS integer,
        DOMNAME varchar(30),
        SHLPORIGIN varchar(1),
        TABLETYPE varchar(1),
        DEPTH integer,
        COMPTYPE varchar(1),
        REFTYPE varchar(1),
        LANGUFLAG varchar(1),
        DBPOSITION integer,
        ANONYMOUS varchar(1),
        OUTPUTSTYLE integer,
        primary key (TABNAME, FIELDNAME, AS4LOCAL, AS4VERS, POSITION)
    )ENGINE=InnoDB;
    
    ALTER TABLE `dataapp`.`ray_bapi_field` 
CHANGE COLUMN `AS4VERS` `AS4VERS` VARCHAR(4) NOT NULL ,
CHANGE COLUMN `POSITION` `POSITION` VARCHAR(4) NOT NULL ,
CHANGE COLUMN `INTLEN` `INTLEN` VARCHAR(6) NULL DEFAULT NULL ,
CHANGE COLUMN `LENG` `LENG` VARCHAR(6) NULL DEFAULT NULL ,
CHANGE COLUMN `DECIMALS` `DECIMALS` VARCHAR(6) NULL DEFAULT NULL ,
CHANGE COLUMN `DEPTH` `DEPTH` VARCHAR(2) NULL DEFAULT NULL ,
CHANGE COLUMN `DBPOSITION` `DBPOSITION` VARCHAR(4) NULL DEFAULT NULL ,
CHANGE COLUMN `OUTPUTSTYLE` `OUTPUTSTYLE` VARCHAR(2) NULL DEFAULT NULL ;

    
     create table RAY_BAPI_PARAMETER (
        FUNCNAME varchar(30) not null,
        R3STATE varchar(1) not null,
        PARAMETER varchar(30) not null,
        PARAMTYPE varchar(1) not null,
        STRUCTURE varchar(132),
        DEFAULTVAL varchar(21),
        REFERENCE varchar(1),
        PPOSITION integer,
        OPTIONAL varchar(1),
        TYPE varchar(1),
        CLASS varchar(1),
        REF_CLASS varchar(1),
        LINE_OF varchar(1),
        TABLE_OF varchar(1),
        RESFLAG1 varchar(1),
        RESFLAG2 varchar(1),
        RESFLAG3 varchar(1),
        RESFLAG4 varchar(1),
        RESFLAG5 varchar(1),
        primary key (FUNCNAME, R3STATE, PARAMETER, PARAMTYPE)
    )ENGINE=InnoDB;

    ALTER TABLE `dataapp`.`ray_bapi_parameter` 
CHANGE COLUMN `PPOSITION` `PPOSITION` VARCHAR(10) NULL DEFAULT NULL ;
    
      create table RAY_EVENT (
        EVENT_ID integer not null auto_increment,
        EVENT_NUMBER varchar(5),
        EVENT_NAME varchar(50),
        FUNCNAME varchar(50),
        primary key (EVENT_ID)
    ) ENGINE=InnoDB

