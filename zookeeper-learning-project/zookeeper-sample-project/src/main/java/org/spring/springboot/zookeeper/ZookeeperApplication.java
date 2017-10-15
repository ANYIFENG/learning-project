/*
 * Copyright 2012-2015 the original author or authors.
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

package org.spring.springboot.zookeeper;


import org.spring.springboot.zookeeper.master.NodeLeaderCurator;
import org.spring.springboot.zookeeper.master.NodeLeaderLatch;
import org.spring.springboot.zookeeper.master.NodeLeaderSelector;
import org.spring.springboot.zookeeper.master.NodeLeaderZookeeper;

import java.util.UUID;

public class ZookeeperApplication {

	public static void main(String[] args) throws Throwable {
		String hostPort = "192.168.56.101:2181";
		String zNode = "/master";
		String name = UUID.randomUUID().toString();
		new NodeLeaderZookeeper(hostPort, zNode, name);



		/*NodeLeaderLatch nodeLeaderLatch = new NodeLeaderLatch();
		nodeLeaderLatch.start();*/
		//nodeLeaderLatch.awaitByLeaderLatch();


		/*NodeLeaderSelector nodeLeaderSelector = new NodeLeaderSelector();
		nodeLeaderSelector.start();*/

		/*NodeLeaderCurator nodeLeaderCurator = new NodeLeaderCurator();
		nodeLeaderCurator.start();*/

		while (true){
			Thread.sleep(1000);
			//System.out.println(nodeLeaderLatch.hasLeadershipByLeaderLatch());
			//nodeLeaderLatch.releaseLeader();
		}


	}

}
