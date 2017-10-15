package org.spring.springboot.zookeeper.master;

import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.CuratorFrameworkFactory;
import org.apache.curator.framework.recipes.leader.LeaderLatch;
import org.apache.curator.framework.recipes.leader.LeaderLatchListener;
import org.apache.curator.retry.ExponentialBackoffRetry;

import java.util.UUID;
import java.util.concurrent.TimeUnit;


public class NodeLeaderLatch {

    private static final String PATH = "/examples/leader";

    private LeaderLatch leaderLatch;
    private CuratorFramework client;

    public void start(){
        client = CuratorFrameworkFactory.newClient("192.168.56.101:2181", new ExponentialBackoffRetry(1000, 3));


        client.start();

        setLeaderLatch(PATH);
    }



    /*
        *  Leader Latch
        *  isLeader 中的方法会在实例被选为主节点后被执行, 而notLeader中的不会被执行
        *  如果主节点被失效, 会进行重新选主
        * */
    public void setLeaderLatch(String path) {
        try {
            //String id = "client#" + InetAddress.getLocalHost().getHostAddress();
            String id = UUID.randomUUID().toString();
            leaderLatch = new LeaderLatch(client, path, id);
            LeaderLatchListener leaderLatchListener = new LeaderLatchListener() {
                @Override
                public void isLeader() {
                    System.out.println("[LeaderLatch]我是主节点, id=" + leaderLatch.getId());
                }

                @Override
                public void notLeader() {
                    System.out.println("[LeaderLatch]我不是主节点, id=" + leaderLatch.getId());
                }
            };
            leaderLatch.addListener(leaderLatchListener);
            leaderLatch.start();
        } catch (Exception e) {
            System.out.println("c创建LeaderLatch失败, path=" + path);
        }
    }

    /*
    *   判断实例是否是主节点
    * */
    public boolean hasLeadershipByLeaderLatch() {
        return leaderLatch.hasLeadership();
    }

    /*
    *   阻塞直到获得领导权
    * */
    public void awaitByLeaderLatch() {
        try {
            leaderLatch.await();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /*
    *   尝试获得领导权并超时
    * */
    public boolean awaitByLeaderLatch(long timeout, TimeUnit unit) {
        boolean hasLeadership = false;
        try {
            hasLeadership = leaderLatch.await(timeout, unit);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return  hasLeadership;
    }

    public void releaseLeader()throws Exception{
        leaderLatch.close();

    }
}
