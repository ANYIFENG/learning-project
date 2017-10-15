package org.spring.springboot.zookeeper.master;

import org.apache.zookeeper.CreateMode;
import org.apache.zookeeper.KeeperException;
import org.apache.zookeeper.WatchedEvent;
import org.apache.zookeeper.Watcher;
import org.apache.zookeeper.Watcher.Event.EventType;
import org.apache.zookeeper.ZooDefs.Ids;
import org.apache.zookeeper.ZooKeeper;

public class NodeLeaderZookeeper implements Watcher {
    private ZooKeeper zk;
    private String hostPort;
    private String zNode;
    private String name;


    public NodeLeaderZookeeper(String hostPort, String zNode, String name) throws Throwable {
        this.hostPort = hostPort;
        this.zNode = zNode;
        this.name = name;

        zk = new ZooKeeper(hostPort, 3000, this);
        try {
            //每个客户端都创建同一个节点，如果创建成功，则该客户端是master
            zk.create(zNode, name.getBytes(), Ids.OPEN_ACL_UNSAFE, CreateMode.EPHEMERAL);
            System.out.println("master节点是:" + name);
        } catch (KeeperException.NodeExistsException e) {
            //如果抛出节点存在的异常，则master已经存在，在该节点上添加watcher
            System.out.println("master节点是：" + new String(zk.getData(zNode, false, null)));
            zk.exists(zNode, true);
        }

    }

    @Override
    public void process(WatchedEvent event) {
        try {

            System.out.println(event);
            if (event.getType() == EventType.NodeDeleted) {
                try {
                    zk.create(zNode, name.getBytes(), Ids.OPEN_ACL_UNSAFE, CreateMode.EPHEMERAL);
                    System.out.println("master节点是：" + name);
                } catch (KeeperException.NodeExistsException e) {
                    System.out.println("master节点是：" + new String(zk.getData(zNode, false, null)));
                    zk.exists(zNode, true);
                }
            }

        } catch (KeeperException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (InterruptedException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

    }

}
