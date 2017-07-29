/*
 * Copyright 2017 Splunk, Inc..
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.splunk.logging;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

/**
 * Periodically delegates polling for acks to the AckManager. Just a simple periodic scheduler.
 * @author ghendrey
 */
class PollScheduler {

  private ScheduledExecutorService scheduler;
  private boolean started;

  public synchronized void start(Runnable poller){
    if(started){
      throw new IllegalStateException("PollController already started");
    }
    this.scheduler = Executors.newScheduledThreadPool(1);
    scheduler.scheduleAtFixedRate(poller, 0, 1, TimeUnit.SECONDS);
    this.started = true;
    System.out.println("STARTED POLLING");

  }

  synchronized boolean isStarted() {
    return started;
  }

  public synchronized void stop() {
    System.out.println("SHUTTING DOWN POLLER");
    if(null != scheduler){    
      //scheduler.shutdown();
      scheduler.shutdownNow();
    }
    scheduler = null;
  }
  
}
