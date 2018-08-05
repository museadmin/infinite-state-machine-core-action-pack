package com.github.museadmin.infinite_state_machine.support;

import org.json.JSONObject;

import java.io.File;

/**
 * Express a message and provide methods for importing
 * from an inbound file and writing to database messages table.
 * Also for loading an outbound message from the database and
 * writing it out to file in the outbound messaging directory
 */
public class Message {

  private String sender;
  private String senderId;
  private String action;
  private JSONObject payload;
  private String sent;
  private String received;

  public Message(File msgFile) {

  }
}
