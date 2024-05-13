package org.mo.lambda.zxl;

import java.util.ArrayList;
import java.util.List;

/**
 * @author WindShadow
 * @version 2024-05-13.
 */
public class NodeService {

    private NodeFactory factory;


    public List<Node> search(String keywords) {

        // 搜索到两个文件
        List<String> ids = new ArrayList<>(2);

        List<Node> nodes = new ArrayList<>();
        for (String id : ids) {
            nodes.add(factory.build(id));
        }

        return nodes;
    }
}
