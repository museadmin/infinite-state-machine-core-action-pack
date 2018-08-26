

DROP DATABASE IF EXISTS ism;
CREATE DATABASE IF NOT EXISTS ism;
USE ism;

CREATE TABLE dependencies (
dependency_id INTEGER NOT NULL PRIMARY KEY,  
dependency TEXT NOT NULL COMMENT 'Name of the dependency',
version TEXT NOT NULL COMMENT 'Version number of the dependency'
);


CREATE TABLE properties (
property TEXT NOT NULL COMMENT 'A property',
value TEXT COMMENT 'The value of the property'
);
INSERT INTO properties VALUES('msg_root', 'messages');
INSERT INTO properties VALUES('msg_in', 'msg_in');
INSERT INTO properties VALUES('msg_in_processed', 'msg_in_processed');
INSERT INTO properties VALUES('msg_out', 'msg_out');
INSERT INTO properties VALUES('msg_out_processed', 'msg_out_processed');
INSERT INTO properties VALUES('msg_rejected', 'msg_rejected');

CREATE TABLE states (
id INTEGER NOT NULL PRIMARY KEY,  
state BOOLEAN DEFAULT '0' COMMENT 'State is set or not',
state_name TEXT NOT NULL COMMENT 'Textual name',
note TEXT COMMENT 'Note explaining what this state is for'
);
INSERT INTO states VALUES(1,0,'READY_TO_RUN','We are ready to run');

CREATE TABLE actions (
id INTEGER NOT NULL PRIMARY KEY,  
action TEXT COMMENT 'The textual name. e.g. ActionConfirmReadyToRun',
run_phase TEXT NOT NULL COMMENT 'The run phase this action is valid in',
payload TEXT COMMENT 'Any payload required for action',
active BOOLEAN NOT NULL DEFAULT '0' COMMENT 'Is this action active or not?'
);
INSERT INTO actions VALUES(1,'ActionConfirmReadyToRun','STARTING','null',1);
INSERT INTO actions VALUES(2,'ActionNormalShutdown','ALL','',0);
INSERT INTO actions VALUES(3,'ActionEmergencyShutdown','ALL','null',0);
INSERT INTO actions VALUES(4,'ActionCheckForNewMessages','RUNNING','null',1);
INSERT INTO actions VALUES(5,'ActionProcessInboundMessages','RUNNING','null',1);
INSERT INTO actions VALUES(6,'ActionBeforeCreateMessagingDirectories','STARTING','null',1);


CREATE TABLE messages (
id INTEGER NOT NULL PRIMARY KEY COMMENT 'Record ID in recipient messages table',
sender TEXT NOT NULL COMMENT 'Return address of sender',
sender_id INTEGER NOT NULL COMMENT 'Record ID in sender messages table',
recipient TEXT NOT NULL COMMENT 'Address of recipient',
action TEXT NOT NULL COMMENT 'Name of the action that handles this message',
payload TEXT COMMENT 'Json body of msg payload',
sent TIMESTAMP NOT NULL COMMENT 'Timestamp msg sent by sender',
received TIMESTAMP DEFAULT CURRENT_TIMESTAMP COMMENT 'Timestamp ism loaded message into database',
direction TEXT NOT NULL COMMENT 'In or outbound message',
processed BOOLEAN NOT NULL DEFAULT '0' COMMENT 'Has the message been processed'
);

CREATE TRIGGER new_msg_insert
BEFORE INSERT ON `messages`
FOR EACH ROW
SET NEW.`direction` = CASE WHEN NEW.direction IS NULL THEN 'inbound' ELSE NEW.direction END
;


CREATE TABLE phases (
id INTEGER NOT NULL PRIMARY KEY,  
state BOOLEAN DEFAULT '0' COMMENT 'phase is active or not',
phase_name TEXT NOT NULL COMMENT 'Textual name',
note TEXT COMMENT 'Note explaining what this phase is for'
);
INSERT INTO phases VALUES(1,0,'EMERGENCY_SHUTDOWN','Phase is emergency shutdown');
INSERT INTO phases VALUES(2,0,'NORMAL_SHUTDOWN','Phase is shutting down normally');
INSERT INTO phases VALUES(3,0,'RUNNING','Phase is running');
INSERT INTO phases VALUES(4,1,'STARTING','Phase is starting');
INSERT INTO phases VALUES(5,0,'STOPPED','Phase is stopped');

