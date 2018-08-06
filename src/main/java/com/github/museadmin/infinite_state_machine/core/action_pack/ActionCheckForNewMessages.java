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
        JSONObject jsonObject = new JSONObject(file);
        // Test for malformed msg
        if (jsonObject.isNull("action")) {
          // Move to rejected if malformed
          rejectMsg(file);
          continue;
        }

        String action = jsonObject.get("action").toString();
        // Test if action is active -> continue
        if (active(action)) {
          continue;
        }

        // Write message to database
        // Move file to processed
        // Activate action
        activate("ActionNormalShutdown");
      }
    }
  }

  /**
   * Move a rejected message file to the rejected directory
   * @param file The semaphore file for the message
   */
  private void rejectMsg(File file) {

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
      queryProperty("msg_rejected"),
      File.separator,
      fileName
    );

    // Move the message file first to the rejected directory
    new File(in + ".msg").renameTo(
      new File(String.format("%s.msg", tgt))
    );

    // Move the semaphore file to the rejected directory
    file.renameTo(new File(String.format("%s.smp", tgt)));

    // Log the error
    LOGGER.error(
      String.format("Malformed message file: %s.msg", tgt)
    );
  }
}
