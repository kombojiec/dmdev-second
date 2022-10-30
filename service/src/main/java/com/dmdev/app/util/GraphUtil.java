package com.dmdev.app.util;

import com.dmdev.app.entity.Client;
import com.dmdev.app.entity.Order;
import org.hibernate.Session;
import org.hibernate.graph.RootGraph;

public class GraphUtil {

    public static RootGraph<Client> getClientGraph(Session session) {
        var clientGraph = session.createEntityGraph(Client.class);
        clientGraph.addAttributeNodes("orders");
        var orderSubGraph = clientGraph.addSubgraph("orders", Order.class);
        orderSubGraph.addAttributeNodes("book");
        return clientGraph;
    }

}
