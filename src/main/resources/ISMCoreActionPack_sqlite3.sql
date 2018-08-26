PRAGMA foreign_keys=OFF;
BEGIN TRANSACTION;

CREATE TABLE dependencies (
dependency_id INTEGER NOT NULL PRIMARY KEY,  
dependency TEXT NOT NULL, -- Name of the dependency 
version TEXT NOT NULL -- Version number of the dependency 
);

CREATE TABLE properties (
property TEXT NOT NULL, -- A property 
value TEXT -- The value of the property 
);
INSERT INTO properties VALUES('msg_root', 'messages');
INSERT INTO properties VALUES('msg_in', 'msg_in');
INSERT INTO properties VALUES('msg_in_processed', 'msg_in_processed');
INSERT INTO properties VALUES('msg_out', 'msg_out');
INSERT INTO properties VALUES('msg_out_processed', 'msg_out_processed');
INSERT INTO properties VALUES('msg_rejected', 'msg_rejected');

CREATE TABLE states (
id INTEGER NOT NULL PRIMARY KEY,  
state BOOLEAN DEFAULT '0', -- State is set or not 
state_name TEXT NOT NULL, -- Textual name 
note TEXT -- Note explaining what this state is for 
);
INSERT INTO states VALUES(1,0,'READY_TO_RUN','We are ready to run');

CREATE TABLE actions (
id INTEGER NOT NULL PRIMARY KEY,  
action TEXT, -- The textual name. e.g. ActionConfirmReadyToRun 
run_phase TEXT NOT NULL, -- The run phase this action is valid in
payload TEXT, -- Any payload required for action 
active BOOLEAN NOT NULL DEFAULT '0' -- Is this action active or not? 
);
INSERT INTO actions VALUES(1,'ActionConfirmReadyToRun','STARTING','null',1);
INSERT INTO actions VALUES(2,'ActionNormalShutdown','ALL','',0);
INSERT INTO actions VALUES(3,'ActionEmergencyShutdown','ALL','null',0);
INSERT INTO actions VALUES(4,'ActionCheckForNewMessages','RUNNING','null',1);
INSERT INTO actions VALUES(5,'ActionProcessInboundMessages','RUNNING','null',1);
INSERT INTO actions VALUES(6,'ActionBeforeCreateMessagingDirectories','STARTING','null',1);

CREATE TABLE messages (
id INTEGER NOT NULL PRIMARY KEY, -- Record ID in recipient messages table 
sender TEXT NOT NULL, -- Return address of sender 
sender_id INTEGER NOT NULL, -- Record ID in sender messages table 
recipient TEXT NOT NULL, -- Address of recipient 
action TEXT NOT NULL, -- Name of the action that handles this message 
payload TEXT, -- Json body of msg payload 
sent TEXT NOT NULL, -- Timestamp msg sent by sender 
received TEXT NOT NULL DEFAULT (strftime('%s', 'now')), -- Timestamp ism loaded message into database 
direction TEXT NOT NULL DEFAULT 'inbound', -- In or outbound message 
processed BOOLEAN NOT NULL DEFAULT '0' -- Has the message been processed 
);

CREATE TABLE phases (
id INTEGER NOT NULL PRIMARY KEY,  
state BOOLEAN DEFAULT '0', -- phase is active or not 
phase_name TEXT NOT NULL, -- Textual name 
note TEXT -- Note explaining what this phase is for 
);
INSERT INTO phases VALUES(1,0,'EMERGENCY_SHUTDOWN','Phase is emergency shutdown');
INSERT INTO phases VALUES(2,0,'NORMAL_SHUTDOWN','Phase is shutting down normally');
INSERT INTO phases VALUES(3,0,'RUNNING','Phase is running');
INSERT INTO phases VALUES(4,1,'STARTING','Phase is starting');
INSERT INTO phases VALUES(5,0,'STOPPED','Phase is stopped');

COMMIT;
