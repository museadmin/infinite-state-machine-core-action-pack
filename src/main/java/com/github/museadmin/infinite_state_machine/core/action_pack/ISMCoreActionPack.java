package com.github.museadmin.infinite_state_machine.core.action_pack;

import com.github.museadmin.infinite_state_machine.common.action.Action;
import com.github.museadmin.infinite_state_machine.common.action.ActionPack;

import java.util.ArrayList;

public class ISMCoreActionPack extends ActionPack {

  public ArrayList<Action> getActions() {
    return actions;
  }

  private ArrayList<Action> actions = new ArrayList<>();

  public ISMCoreActionPack(){
    actions.add(new ActionConfirmReadyToRun());
    actions.add(new ActionEmergencyShutdown());
    actions.add(new ActionNormalShutdown());
  }
}
