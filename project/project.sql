CREATE SEQUENCE BOARD_T_NO_SEQ;
CREATE SEQUENCE NEW_BOARD_T_NO_SEQ;
CREATE SEQUENCE REPLY_BOARD_T_NO_SEQ;

CREATE TABLE BOARD_T(
    NO NUMBER PRIMARY KEY, 
	TITLE VARCHAR2(100 BYTE), 
	NAME VARCHAR2(20 BYTE), 
	CONTENT CLOB, 
	REGDATE,
	READCOUNT, 
	PASSWORD VARCHAR2(128 BYTE), 
	NEW_NO NUMBER 
)

CREATE TABLE NEW_BOARD_T(
    NO NUMBER PRIMARY KEY, 
	NAME VARCHAR2(20 BYTE), 
	REGDATE DATE DEFAULT sysdate
)

CREATE TABLE REPLY_BOARD_T(
    RE_NO NUMBER, 
	NAME VARCHAR2(20 BYTE), 
	RE_CONTEXT CLOB, 
	REGDATE DATE DEFAULT sysdate, 
	SEQ_FIRST NUMBER, 
	SEQ_SECOND NUMBER, 
	CONSTRAINT REPLY_BOARD_T_RE_CONTEXT_NN CHECK (re_context IS NOT null) ENABLE, 
	CONSTRAINT REPLY_BOARD_T_RE_NO_FK FOREIGN KEY RE_NO
    REFERENCES BOARD_T NO ENABLE
)