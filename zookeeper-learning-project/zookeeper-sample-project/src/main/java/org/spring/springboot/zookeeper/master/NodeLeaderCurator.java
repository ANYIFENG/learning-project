package org.spring.springboot.zookeeper.master;

import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.CuratorFrameworkFactory;
import org.apache.curator.framework.api.CuratorEvent;
import org.apache.curator.framework.api.CuratorEventType;
import org.apache.curator.framework.api.CuratorListener;
import org.apache.curator.framework.recipes.cache.ChildData;
import org.apache.curator.framework.recipes.cache.NodeCache;
import org.apache.curator.framework.recipes.cache.NodeCacheListener;
import org.apache.curator.retry.ExponentialBackoffRetry;
import org.apache.zookeeper.*;
import org.apache.zookeeper.Watcher.Event.EventType;
import org.apache.zookeeper.ZooDefs.Ids;
import org.apache.zookeeper.data.Stat;

import java.util.UUID;

public class NodeLeaderCurator  {

    private static final String PATH = "/curator/master";
    private CuratorFramework client;
    private String zNodeName = UUID.randomUUID().toString();


    public void start() throws Exception{
        client = CuratorFrameworkFactory.newClient("192.168.56.101:2181", new ExponentialBackoffRetry(1000, 3));
        client.start();


        try {
            client.create().creatingParentsIfNeeded().withMode(CreateMode.EPHEMERAL).forPath(PATH, zNodeName.getBytes());
            System.out.println("成为 master节点是：" + new String(client.getData().forPath(PATH)));
        }catch (KeeperException.NodeExistsException e){
            //e.printStackTrace();
            System.out.println("master节点是：" + new String(client.getData().forPath(PATH)));
        }



        final NodeCache cache = new NodeCache(client, PATH);
        NodeCacheListener listener = () -> {

            System.out.println("listener....");
           /* ChildData data = cache.getCurrentData();
            if (null != data) {
                System.out.println("节点数据：" + new String(cache.getCurrentData().getData()));
            } else {
                System.out.println("节点被删除!");
                client.create().creatingParentsIfNeeded().withMode(CreateMode.EPHEMERAL).forPath(PATH, zNodeName.getBytes());

            }*/

           Stat stat = client.checkExists().forPath(PATH);
           if (stat == null){
               client.create().creatingParentsIfNeeded().withMode(CreateMode.EPHEMERAL).forPath(PATH, zNodeName.getBytes());
               System.out.println("成为 master节点是：" + new String(client.getData().forPath(PATH)));
           }else {
               System.out.println("master节点是：" + new String(client.getData().forPath(PATH)));
           }

        };
        cache.getListenable().addListener(listener);
        cache.start();







    }

   /* @Override
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

    }*/

}
