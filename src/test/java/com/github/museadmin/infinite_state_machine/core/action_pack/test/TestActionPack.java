package com.github.museadmin.infinite_state_machine.core.action_pack.test;

import com.github.museadmin.infinite_state_machine.core.InfiniteStateMachine;
import com.github.museadmin.infinite_state_machine.core.action_pack.ISMCoreActionPack;
import org.junit.Before;
import org.junit.Test;

public class TestActionPack {

  private InfiniteStateMachine infiniteStateMachine;
  private ISMCoreActionPack ismCoreAp;

  @Before
  public void setup(){
    infiniteStateMachine = new InfiniteStateMachine();
  }

  @Test
  public void testActionPackIsImportedByDefaultISMConstructor() {

  }

  @Test
  public void testActionPackIsImportedByAlternativeISMConstructor() {

  }
}
