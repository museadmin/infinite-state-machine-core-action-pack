package com.github.museadmin.infinite_state_machine.core.action_pack;


import com.github.museadmin.infinite_state_machine.action.Action;
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
            message.get("action") == "" ||
            message.isNull("sender") ||
            message.get("sender") == "" ||
            message.isNull("sender_id") ||
            message.get("sender_id") == "" ||
            message.isNull("recipient") ||
            message.get("recipient") == "" ||
            message.isNull("sent") ||
            message.get("sent") == ""
        ) {
          moveMsg(file, "msg_rejected");
          continue;
        }

        String action = message.get("action").toString();
        // Test if action is active -> continue
        if (active(action)) {
          continue;
        }

        insertMessage(message);

        moveMsg(file, "msg_in_processed");
        activate(action);
      }
    }
  }

  /**
   * Move a processed message file to the in_processed directory
   * @param file The semaphore file for the message
   */
  private void moveMsg(File file, String target) {

    // Get the name of the file
    String fileName = Files.getNameWithoutExtension(
      file.getAbsolutePath()
    );

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

    // Move the message file first to the rejected directory
    new File(in + ".msg").renameTo(
      new File(String.format("%s.msg", tgt))
    );

    // Move the semaphore file to the rejected directory
    file.renameTo(new File(String.format("%s.smp", tgt)));

    if (target.equals("msg_rejected")) {
      LOGGER.error(
        String.format("Malformed message file: %s.msg", tgt)
      );
    }
  }
}
