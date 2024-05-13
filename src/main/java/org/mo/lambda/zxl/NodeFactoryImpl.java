package org.mo.lambda.zxl;

import lombok.RequiredArgsConstructor;
import org.springframework.lang.Nullable;

import java.util.*;

/**
 * @author WindShadow
 * @version 2024-05-13.
 */

@RequiredArgsConstructor
public class NodeFactoryImpl implements NodeFactory {

    private final ConsanguinityMapper mapper;

    @Override
    @Nullable
    public Node build(String id) {

        Inner inner = new Inner();
        return inner.build(id, -1);
    }

    private EdsFile findEdsFile(String id) {
        // TODO
        return new EdsFile();
    }

    private class Inner {

        private Map<String, Node> proccessing = new HashMap<>();

        /**
         *
         * @param id
         * @param flag 初始=-1  往前往后=0 往前=1 往后=2
         * @return
         */
        private Node build(String id, int flag) {

            EdsFile edsFile = findEdsFile(id);
            return proccessing.computeIfAbsent(id, k -> {

                String type = edsFile.getType();
                if (type.equals("加工")) {

                    if (flag == -1) {
                        return doBuildNode(edsFile, 0);
                    } else {
                        return doBuildNode(edsFile, flag);
                    }
                } else if (type.equals("源头")) {
                    if (flag == -1) {
                        return doBuildNode(edsFile, 2);
                    } else {
                        return doBuildNode(edsFile, flag);
                    }
                } else if (type.equals("收到")) {
                    if (flag == -1) {
                        return doBuildNode(edsFile, 0);
                    } else {
                        return doBuildNode(edsFile, flag);
                    }
                }
                throw new RuntimeException();
            });
        }

        private Node doBuildNode(EdsFile edsFile, int flag) {

            String id = edsFile.getId();
            // 构建node
            Node node = new Node();
            node.setId(id);
            node.setName(edsFile.getName());

            // 往前
            if (flag == 0 || flag == 1) {

                node.setIn(buildInNode(id));
            }
            // 往后
            if (flag == 0 || flag == 2) {

                node.setOut(buildOutNode(id));
            }
            return node;
        }

//        private Node buildProcess(EdsFile edsFile, int flag) {
//
//            String id = edsFile.getId();
//            if (proccessing.contains(id)) return null;
//            // 记录ID
//            proccessing.add(id);
//
//            // 构建node
//            Node node = new Node();
//            node.setId(id);
//
//            // 往前
//            if (flag == 0 || flag == 1) {
//
//                node.setIn(buildInNode(id, flag));
//            }
//            // 往后
//            if (flag == 0 || flag == 2) {
//
//                node.setOut(buildInNode(id, flag));
//            }
//            return node;
//        }
//
//        private Node buildReceive(EdsFile edsFile, int flag) {
//
//
//        }
//
//        private Node buildLocal(EdsFile edsFile, int flag) {
//
//
//        }

        // ~
        // ==================================

        private Set<Node> buildInNode(String id) {

            List<Consanguinity> inCons = mapper.selectIn(id);
            Set<Node> in = new HashSet<>();
            for (Consanguinity con : inCons) {
                // in :: target 是自己，找 source
                String sourceId = con.getSourceId();
                Node sourceNode = build(sourceId, 1);
                in.add(sourceNode);
            }
            return in;
        }

        private Set<Node> buildOutNode(String id) {

            Set<Node> out = new HashSet<>();
            List<Consanguinity> outCons = mapper.selectOut(id);
            for (Consanguinity con : outCons) {

                // out :: source 是自己，找 target

                String targetId = con.getTargetId();
                Node targetNode = build(targetId, 2);
                out.add(targetNode);
            }
            return out;
        }
    }
}
