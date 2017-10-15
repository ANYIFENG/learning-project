package org.spring.springboot.zookeeper.master;

import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.CuratorFrameworkFactory;
import org.apache.curator.framework.recipes.leader.LeaderLatch;
import org.apache.curator.framework.recipes.leader.LeaderLatchListener;
import org.apache.curator.framework.recipes.leader.LeaderSelector;
import org.apache.curator.framework.recipes.leader.LeaderSelectorListenerAdapter;
import org.apache.curator.retry.ExponentialBackoffRetry;

import java.io.Closeable;
import java.io.IOException;
import java.util.UUID;
import java.util.concurrent.TimeUnit;


public class NodeLeaderSelector extends LeaderSelectorListenerAdapter implements Closeable {

    private static final String PATH = "/examples/master";


    private CuratorFramework client;
    private LeaderSelector leaderSelector;

    private String name;

    public void start(){

        name = UUID.randomUUID().toString();
        client = CuratorFrameworkFactory.newClient("192.168.56.101:2181", new ExponentialBackoffRetry(1000, 3));
        client.start();

        leaderSelector = new LeaderSelector(client, PATH, this);
        leaderSelector.autoRequeue();
        leaderSelector.start();



    }

    @Override
    public void close() throws IOException {

    }

    @Override
    public void takeLeadership(CuratorFramework client) throws Exception {



        System.out.println("take leadership...  " + name + "   -----  " + leaderSelector.getId());
        while (true) {
            Thread.sleep(1000);
        }
    }
}
