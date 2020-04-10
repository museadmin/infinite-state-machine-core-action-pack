package com.github.museadmin.infinite_state_machine.core.action_pack;


import com.github.museadmin.infinite_state_machine.common.action.Action;
import com.google.common.io.Files;
import org.json.JSONObject;

import java.io.File;

/**
 * Check if any new messages have arrived in the in directory.
 * Each message file should have a semaphore file of the same name
 * but with a .smp extension, that is created after the message
 * file write is complete.
 */
public class ActionCheckForNewMessages extends Action {

  /**
   * The principal method for execution of the action
   */
  public void execute() {

    if (active()) {

      // List any semaphore files in the inbound dir
      File in = new File(queryProperty("msg_in"));
      File[] fileList = in.listFiles(
        (dir, name) -> name.toLowerCase().endsWith(".smp")
      );

      for (File file : fileList) {

        // Convert file to JSONObject
        JSONObject message = getJsonObjectFromFile(
          file.getAbsolutePath().replace(".smp", ".msg")
        );
        // Test for malformed msg
        if (
          message.isNull("action") ||
            message.get("action").equals("") ||
            message.isNull("sender") ||
            message.get("sender").equals("") ||
            message.isNull("sender_id") ||
            message.get("sender_id").equals("") ||
            message.isNull("recipient") ||
            message.get("recipient").equals("") ||
            message.isNull("sent") ||
            message.get("sent").equals("")
        ) {
          moveMsg(file, "msg_rejected");
          continue;
        }
        // Insert into DB and move to processed
        insertMessage(message);
        moveMsg(file, "msg_in_processed");

        activate("ActionProcessInboundMessages");
      }
    }
  }

  /**
   * Move a message file to the in_processed dir or rejected dir
   *
   * @param file The semaphore file for the message
   */
  private void moveMsg(File file, String target) {

    // Get the name of the file
    String fileName = Files.getNameWithoutExtension(
      file.getAbsolutePath()
    );

    // Source and target for moves
    String in = String.format("%s%s%s",
      queryProperty("msg_in"),
      File.separator,
      fileName
    );
    String tgt = String.format("%s%s%s",
      queryProperty(target),
      File.separator,
      fileName
    );

    // Move the message file first to the directory
    new File(in + ".msg").renameTo(
      new File(String.format("%s.msg", tgt))
    );

    // Move the semaphore file to the directory
    file.renameTo(
      new File(String.format("%s.smp", tgt))
    );

    if (target.equals("msg_rejected")) {
      LOGGER.error(
        String.format("Malformed message file: %s.msg", tgt)
      );
    }
  }
}
