package com.alibaba.otter.canal.client;

import java.net.SocketAddress;
import java.util.List;

import com.alibaba.otter.canal.client.impl.ClusterCanalConnector;
import com.alibaba.otter.canal.client.impl.ClusterNodeAccessStrategy;
import com.alibaba.otter.canal.client.impl.SimpleCanalConnector;
import com.alibaba.otter.canal.client.impl.SimpleNodeAccessStrategy;
import com.alibaba.otter.canal.common.zookeeper.ZkClientx;

/**
 * canal connectors创建工具类
 * 
 * @author sasou <admin@php-gene.com> web:http://www.php-gene.com/
 * @version 1.0.0
 */
public class CanalConnectors {

    /**
     * 创建单链接的客户端链接
     *
     * @param address
     * @param destination
     * @param username
     * @param password
     * @return
     */
    public static CanalConnector newSingleConnector(SocketAddress address, String destination, String username,
                                                    String password) {
        SimpleCanalConnector canalConnector = new SimpleCanalConnector(address, username, password, destination);
        canalConnector.setSoTimeout(30 * 1000);
        return canalConnector;
    }

    /**
     * 创建带cluster模式的客户端链接，自动完成failover切换
     *
     * @param addresses
     * @param destination
     * @param username
     * @param password
     * @return
     */
    public static CanalConnector newClusterConnector(List<? extends SocketAddress> addresses, String destination,
                                                     String username, String password) {
        ClusterCanalConnector canalConnector = new ClusterCanalConnector(username,
            password,
            destination,
            new SimpleNodeAccessStrategy(addresses));
        canalConnector.setSoTimeout(30 * 1000);
        return canalConnector;
    }

    /**
     * 创建带cluster模式的客户端链接，自动完成failover切换，服务器列表自动扫描
     *
     * @param zkServers
     * @param destination
     * @param username
     * @param password
     * @return
     */
    public static CanalConnector newClusterConnector(String zkServers, String destination, String username,
                                                     String password) {
        ClusterCanalConnector canalConnector = new ClusterCanalConnector(username,
            password,
            destination,
            new ClusterNodeAccessStrategy(destination, ZkClientx.getZkClient(zkServers)));
        canalConnector.setSoTimeout(30 * 1000);
        return canalConnector;
    }
}